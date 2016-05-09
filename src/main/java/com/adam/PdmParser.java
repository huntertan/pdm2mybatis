package com.adam;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.QName;
import org.dom4j.io.SAXReader;

import com.adam.model.BaseM;
import com.adam.model.ColumnM;
import com.adam.model.DomainM;
import com.adam.model.JoinM;
import com.adam.model.KeyM;
import com.adam.model.ModelM;
import com.adam.model.PackageM;
import com.adam.model.ReferenceM;
import com.adam.model.TableM;
import com.adam.model.support.ColumnMImp;
import com.adam.model.support.DomainMImp;
import com.adam.model.support.JoinMImp;
import com.adam.model.support.KeyMImp;
import com.adam.model.support.ModelMImp;
import com.adam.model.support.PackageMImp;
import com.adam.model.support.ReferenceMImp;
import com.adam.model.support.TableMImp;

public class PdmParser implements PdmConst {
    static Logger logger = Logger.getLogger(PdmParser.class);

    static ParseHandler handler = ParseHandler.getInstance();

    File file = null;

    Document doc = null;

    Map qnames = new HashMap();

    Map shortcuts = new HashMap();

    public PdmParser() {
    }

    public PdmParser(File f) {
        this.file = f;
    }

    public ModelM parse(File file) {
        this.file = file;
        return parse();
    }

    public Document read() {
        try {
            SAXReader reader = new SAXReader();
            DocumentFactory df = new DocumentFactory();
            reader.setDocumentFactory(df);
            this.doc = reader.read(this.file);
            Iterator qns = df.getQNames().iterator();
            while (qns.hasNext()) {
                QName qn = (QName) qns.next();
                this.qnames.put(qn.getQualifiedName(), qn);
            }
        } catch (Exception e) {
            logger.error("parse file fail ! " + this.file.getAbsolutePath());
            e.printStackTrace();
        }
        return this.doc;
    }

    public ModelM parse() {
        Document doc = read();

        Element root = doc.getRootElement();

        ModelM model = new ModelMImp();

        Iterator packs = lookupPackages(root).iterator();
        while (packs.hasNext()) {
            Element child = (Element) packs.next();
            PackageM pack = parsePackage(child);
            model.addPackage(pack);
            handler.addPackage(pack);
            List elms = lookupTables(child, true);
            parseTables(elms, pack);
            parseReferences(child, pack);

            elms = lookupDomains(root);
            parseDomains(elms, model);
        }

        PackageM defPack = new PackageMImp(true);
        model.addPackage(defPack);
        handler.addPackage(defPack);
        List elms = lookupTables(root, false);
        parseTables(elms, defPack);

        elms = lookupDomains(root);
        parseDomains(elms, model);

        load(model);
        return model;
    }

    void parseDomains(List domainElms, ModelM model) {
        Iterator it = domainElms.iterator();
        while (it.hasNext()) {
            Element domainElm = (Element) it.next();
            DomainM domain = new DomainMImp();
            setBaseInfo(domainElm, domain);
            Object qname = this.qnames.get("a:Comment");
            if (qname != null) {
                String comment = domainElm.elementText((QName) qname);
                domain.setComment(comment);
            }
            String dataType = domainElm.elementText((QName) this.qnames.get("a:DataType"));
            if (dataType != null) {
                domain.setDataType(dataType);
            }
            model.addDomain(domain);
        }
    }

    void parseReferences(Element packElm, PackageM pack) {
        Iterator referenceElms = lookupReferences(packElm).iterator();
        while (referenceElms.hasNext()) {
            Element referenceElm = (Element) referenceElms.next();
            ReferenceM reference = parseReference(referenceElm);
            pack.addReference(reference);
        }
    }

    void load(ModelM model) {
        Iterator it = model.getPackages().iterator();
        while (it.hasNext()) {
            PackageM p = (PackageM) it.next();
            Iterator tables = p.getTables().iterator();
            while (tables.hasNext()) {
                TableM table = (TableM) tables.next();
                table.setPackage(p);
                Iterator columns = table.getColumns().iterator();
                while (columns.hasNext()) {
                    ColumnM column = (ColumnM) columns.next();
                    column.setPrimaryKey(table.isPrimaryKey(column));
                    column.setNullAble(table.isNullAble(column));
                    column.setTable(table);
                }

                Iterator references = p.getReferences().iterator();
                while (references.hasNext()) {
                    ReferenceM reference = (ReferenceM) references.next();
                    if (reference.getLeft().getId().equals(table.getId())) {
                        table.addReference(reference);
                    }
                }
            }
            Iterator refs = p.getReferences().iterator();
            while (refs.hasNext()) {
                ReferenceM r = (ReferenceM) refs.next();
                Iterator joins = r.getJoins().iterator();
                while (joins.hasNext()) {
                    JoinM join = (JoinM) joins.next();
                    join.setLeftTable(r.getLeft());
                    join.setRightTable(r.getRight());
                }
            }
        }
    }

    private void parseTables(List elms, PackageM pack) {
        Iterator tableElms = elms.iterator();
        while (tableElms.hasNext()) {
            Element tableElm = (Element) tableElms.next();
            TableM table = parseTable(tableElm);
            pack.addTable(table);

            Iterator columnElms = lookupColumns(tableElm).iterator();
            while (columnElms.hasNext()) {
                Element columnElm = (Element) columnElms.next();
                ColumnM column = parseColumn(columnElm);
                handler.addColumn(column);
                table.addColumn(column);
            }

            Element pkElm = lookupPrimaryKey(tableElm);
            if (pkElm != null) {
                Iterator keyElms = pkElm.elementIterator((QName) this.qnames.get("o:Key"));

                while (keyElms.hasNext()) {
                    Element keyElm = (Element) keyElms.next();
                    KeyM key = (KeyM) ProxyFactory.getInstance().getProxy(KeyMImp.class);
                    String ref = keyElm.attributeValue("Ref");
                    key.setRef(ref);
                    // System.out.println("ref:" + ref);
                    table.addKey(key);
                }

                keyElms = lookupKeys(tableElm).iterator();
                while (keyElms.hasNext()) {
                    Element keyElm = (Element) keyElms.next();
                    KeyM key = parseKey(keyElm);
                    handler.addKey(key);
                    table.addKey(key);
                }
            } else {
                logger.warn("can not find primary key in table " + table.getName() + "[" + table.getCode() + "]"
                        + " id=" + table.getId());
            }
        }
    }

    PackageM parsePackage(Element packElm) {
        PackageM pack = new PackageMImp();
        setBaseInfo(packElm, pack);
        return pack;
    }

    ReferenceM parseReference(Element referenceElm) {
        ReferenceM reference = new ReferenceMImp();
        setBaseInfo(referenceElm, reference);
        Iterator it = referenceElm.elementIterator((QName) this.qnames.get("a:ForeignKeyConstraintName"));

        Element constraintNameElm = null;
        if (it.hasNext()) {
            constraintNameElm = (Element) it.next();
        }
        if (constraintNameElm != null) {
            reference.setConstraintName(constraintNameElm.getText());
        }

        it = referenceElm.elementIterator((QName) this.qnames.get("a:Cardinality"));
        Element referenceTypeElm = null;
        if (it.hasNext()) {
            referenceTypeElm = (Element) it.next();
        }
        if (referenceTypeElm != null) {
            reference.setReferenceType(referenceTypeElm.getText());
        }

        TableM leftTable = getLeft(referenceElm);
        reference.setLeft(leftTable);
        TableM rightTable = getRight(referenceElm);
        reference.setRight(rightTable);

        boolean hasKey = true;
        it = referenceElm.elementIterator((QName) this.qnames.get("c:ParentKey"));
        if (it.hasNext()) {
            Element parentKeyElm = (Element) it.next();
            it = parentKeyElm.elementIterator((QName) this.qnames.get("o:Key"));
            if (it.hasNext()) {
                Element keyElm = (Element) it.next();
                KeyM parentKey = (KeyM) ProxyFactory.getInstance().getProxy(KeyMImp.class);
                parentKey.setRef(keyElm.attributeValue("Ref"));
                reference.setParentKey(parentKey);
            } else {
                hasKey = false;
            }
        } else {
            hasKey = false;
        }
        if (!hasKey) {
            throw new RuntimeException("can not find parent key [ " + reference.getCode() + " " + reference.getName()
                    + " ]");
        }

        Iterator joinsElms = referenceElm.elementIterator((QName) this.qnames.get("c:Joins"));

        Element joinsElm = null;
        if (joinsElms.hasNext()) {
            joinsElm = (Element) joinsElms.next();
        }
        if (joinsElm != null) {
            Iterator joinElms = joinsElm.elementIterator((QName) this.qnames.get("o:ReferenceJoin"));

            while (joinElms.hasNext()) {
                JoinM join = new JoinMImp();
                Element joinElm = (Element) joinElms.next();
                String objectId = joinElm.elementText((QName) this.qnames.get("a:ObjectID"));
                if (objectId != null) {
                    join.setObjectId(objectId);
                }

                Element leftElm = (Element) joinElm.elementIterator((QName) this.qnames.get("c:Object1")).next();

                Element leftColumn = (Element) leftElm.elementIterator((QName) this.qnames.get("o:Column")).next();

                Element rightElm = (Element) joinElm.elementIterator((QName) this.qnames.get("c:Object1")).next();

                Element rightColumn = (Element) rightElm.elementIterator((QName) this.qnames.get("o:Column")).next();

                ColumnM left = (ColumnM) ProxyFactory.getInstance().getProxy(ColumnMImp.class);
                left.setRef(leftColumn.attributeValue("Ref"));
                join.setLeft(left);
                ColumnM right = (ColumnM) ProxyFactory.getInstance().getProxy(ColumnMImp.class);

                right.setRef(rightColumn.attributeValue("Ref"));
                join.setRight(right);

                reference.addJoin(join);
            }
        }
        return reference;
    }

    public TableM getLeft(Element referenceElm) {
        Element leftObjElm = (Element) referenceElm.elementIterator((QName) this.qnames.get("c:Object1")).next();

        Iterator leftTableElms = leftObjElm.elementIterator((QName) this.qnames.get("o:Table"));

        if (leftTableElms.hasNext()) {
            Element leftTableElm = (Element) leftTableElms.next();
            TableM table = (TableM) ProxyFactory.getInstance().getProxy(TableMImp.class);
            table.setRef(leftTableElm.attributeValue("Ref"));
            return table;
        }

        leftTableElms = leftObjElm.elementIterator((QName) this.qnames.get("o:Shortcut"));
        if (leftTableElms.hasNext()) {
            Element leftTableElm = (Element) leftTableElms.next();
            String shortcutId = leftTableElm.attributeValue("Ref");
            leftTableElm = getTarget(shortcutId);
            TableM table = (TableM) ProxyFactory.getInstance().getProxy(TableMImp.class);
            table.setRef(leftTableElm.attributeValue("Id"));
            return table;
        }

        return null;
    }

    public TableM getRight(Element referenceElm) {
        Element leftObjElm = (Element) referenceElm.elementIterator((QName) this.qnames.get("c:Object1")).next();

        Iterator leftTableElms = leftObjElm.elementIterator((QName) this.qnames.get("o:Table"));

        if (leftTableElms.hasNext()) {
            Element leftTableElm = (Element) leftTableElms.next();
            TableM table = (TableM) ProxyFactory.getInstance().getProxy(TableMImp.class);
            table.setRef(leftTableElm.attributeValue("Ref"));
            return table;
        }

        leftTableElms = leftObjElm.elementIterator((QName) this.qnames.get("o:Shortcut"));
        if (leftTableElms.hasNext()) {
            Element leftTableElm = (Element) leftTableElms.next();
            String shortcutId = leftTableElm.attributeValue("Ref");
            leftTableElm = getTarget(shortcutId);
            TableM table = (TableM) ProxyFactory.getInstance().getProxy(TableMImp.class);
            table.setRef(leftTableElm.attributeValue("Id"));
            return table;
        }

        return null;
    }

    Element getTarget(String shortcutId) {
        Element root = this.doc.getRootElement();
        Element chortcutElm = lookupById(root, shortcutId, false);
        if (chortcutElm == null) {
            logger.error("can not find shortcut : " + shortcutId);
        }
        String targetId = chortcutElm.elementText((QName) this.qnames.get("a:TargetID"));
        Element targetElm = lookupById(root, targetId, true);
        if (targetElm == null) {
            logger.error("can not find shortcut target: " + targetId);
        }
        return targetElm;
    }

    Element lookupById(Element root, String id, boolean isObjId) {
        if (!isObjId) {
            if (id.equals(root.attributeValue("Id"))) {
                return root;
            }

        } else if (id.equals(root.elementText((QName) this.qnames.get("a:ObjectID")))) {
            return root;
        }

        Iterator it = root.elementIterator();
        while (it.hasNext()) {
            Element child = (Element) it.next();
            Element result = lookupById(child, id, isObjId);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    TableM parseTable(Element tableElm) {
        TableM table = new TableMImp();
        setBaseInfo(tableElm, table);
        return table;
    }

    ColumnM parseColumn(Element columnElm) {
        ColumnM column = new ColumnMImp();
        setBaseInfo(columnElm, column);
        Iterator children = columnElm.elementIterator((QName) this.qnames.get("a:DataType"));

        if (children.hasNext()) {
            String dataType = ((Element) children.next()).getText().trim();
            if (dataType.contains("(")) {
                dataType = dataType.substring(0, dataType.indexOf("("));
            }
            column.setDataType(dataType);
        }
        children = columnElm.elementIterator((QName) this.qnames.get("a:Column.Mandatory"));
        if (children.hasNext()) {
            column.setMandatory(Integer.valueOf(((Element) children.next()).getText()).intValue());
        }

        children = columnElm.elementIterator((QName) this.qnames.get("a:Length"));
        if (children.hasNext()) {
            column.setLength(Integer.valueOf(((Element) children.next()).getText()).intValue());
        }

        return column;
    }

    KeyM parseKey(Element keyElm) {
        KeyM key = new KeyMImp();
        setBaseInfo(keyElm, key);

        Element elm = (Element) keyElm.elementIterator((QName) this.qnames.get("c:Key.Columns")).next();

        Iterator columns = elm.elementIterator((QName) this.qnames.get("o:Column"));
        while (columns.hasNext()) {
            String ref = ((Element) columns.next()).attributeValue("Ref");
            ColumnM column = (ColumnM) ProxyFactory.getInstance().getProxy(ColumnMImp.class);
            column.setRef(ref);
            key.addColumn(column);
        }
        return key;
    }

    void setBaseInfo(Element elm, BaseM model) {
        model.setId(elm.attributeValue("Id"));

        Element objElm = (Element) elm.elementIterator((QName) this.qnames.get("a:ObjectID")).next();

        if (objElm != null) {
            model.setObjectId(objElm.getText());
        }
        Element nameElm = (Element) elm.elementIterator((QName) this.qnames.get("a:Name")).next();

        if (nameElm != null) {
            model.setName(nameElm.getText());
        }
        Element codeElm = (Element) elm.elementIterator((QName) this.qnames.get("a:Code")).next();

        if (codeElm != null)
            model.setCode(codeElm.getText());
        QName comment = (QName) this.qnames.get("a:Comment");
        if (comment != null) {
            Iterator commentIterator = elm.elementIterator((QName) this.qnames.get("a:Comment"));
            if (commentIterator != null && commentIterator.hasNext()) {
                Element commentElm = (Element) commentIterator.next();

                if (commentElm != null)
                    model.setComment(commentElm.getText());
            }
        }
    }

    List lookupPackages(Element elm) {
        List rs = new ArrayList();
        if ((eq("o:Package", elm)) && (elm.attribute("Id") != null)) {
            rs.add(elm);
        }
        Iterator children = elm.elementIterator();
        while (children.hasNext()) {
            rs.addAll(lookupPackages((Element) children.next()));
        }
        return rs;
    }

    List lookupReferences(Element elm) {
        List rs = new ArrayList();
        if ((eq("o:Reference", elm)) && (elm.attribute("Id") != null))
            rs.add(elm);
        Iterator children = elm.elementIterator();
        while (children.hasNext()) {
            Element child = (Element) children.next();
            if ((eq("o:Package", child)) && (child.attribute("Id") != null))
                continue;
            rs.addAll(lookupReferences(child));
        }
        return rs;
    }

    List lookupTables(Element elm, boolean includeSub) {
        if ((eq("o:Package", elm)) && (elm.attribute("Id") != null) && (!includeSub))
            return Collections.EMPTY_LIST;
        List rs = new ArrayList();
        if ((eq("o:Table", elm)) && (elm.attribute("Id") != null)) {
            rs.add(elm);
        }
        Iterator it = elm.elementIterator();
        while (it.hasNext()) {
            Element child = (Element) it.next();
            rs.addAll(lookupTables(child, includeSub));
        }
        return rs;
    }

    List lookupColumns(Element elm) {
        List rs = new ArrayList();
        if ((eq("o:Column", elm)) && (elm.attribute("Id") != null)) {
            rs.add(elm);
        }
        Iterator it = elm.elementIterator();
        while (it.hasNext()) {
            rs.addAll(lookupColumns((Element) it.next()));
        }
        return rs;
    }

    List lookupKeys(Element elm) {
        if (eq("c:PrimaryKey", elm))
            return Collections.EMPTY_LIST;
        List rs = new ArrayList();
        if ((eq("o:Key", elm)) && (elm.attribute("Id") != null)) {
            rs.add(elm);
        }
        Iterator it = elm.elementIterator();
        while (it.hasNext()) {
            rs.addAll(lookupKeys((Element) it.next()));
        }
        return rs;
    }

    Element lookupPrimaryKey(Element elm) {
        if (eq("c:PrimaryKey", elm)) {
            return elm;
        }
        Iterator children = elm.elementIterator();
        while (children.hasNext()) {
            Element ret = lookupPrimaryKey((Element) children.next());
            if (ret != null) {
                return ret;
            }
        }
        return null;
    }

    List lookupDomains(Element elm) {
        List rs = new ArrayList();
        if ((eq("o:PhysicalDomain", elm)) && (elm.attribute("Id") != null)) {
            rs.add(elm);
        }
        Iterator children = elm.elementIterator();
        while (children.hasNext()) {
            rs.addAll(lookupDomains((Element) children.next()));
        }
        return rs;
    }

    boolean eq(String qname, Element elm) {
        return elm.getQualifiedName().equals(qname);
    }

    boolean isEntity(Element elm) {
        return elm.attribute("Ref") == null;
    }

    boolean isShortcut(Element elm) {
        return elm.getQualifiedName().equals("o:Shortcut");
    }
}
