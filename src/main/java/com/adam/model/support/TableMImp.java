package com.adam.model.support;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.adam.model.ColumnM;
import com.adam.model.KeyM;
import com.adam.model.PackageM;
import com.adam.model.ReferenceM;
import com.adam.model.TableM;

public class TableMImp extends BaseMImp
  implements TableM
{
  List columns = new ArrayList();

  List keys = new ArrayList();

  List references = new ArrayList();

  PackageM pack = null;

  public List getColumns() {
    return this.columns;
  }

  public void setColumns(List columns) {
    this.columns = columns;
  }

  public List getKeys() {
    return this.keys;
  }

  public void setKeys(List keys) {
    this.keys = keys;
  }

  public void addColumn(ColumnM column) {
    this.columns.add(column);
  }

  public void addKey(KeyM key) {
    this.keys.add(key);
  }

  public KeyM getPrimaryKey() {
    Iterator ks = this.keys.iterator();
    while (ks.hasNext()) {
      KeyM k = (KeyM)ks.next();
      if (k.isPrimaryKey()) {
        return k;
      }
    }
    return null;
  }

  public List getAKeys() {
    List akeys = new ArrayList();
    Iterator it = this.keys.iterator();
    while (it.hasNext()) {
      KeyM k = (KeyM)it.next();
      if (!k.isPrimaryKey()) {
        akeys.add(k);
      }
    }
    return akeys;
  }

  public String getRef() {
    throw new RuntimeException(" can not invoke ! ");
  }

  public void setRef(String ref) {
    throw new RuntimeException(" can not invoke ! ");
  }

  public PackageM getPackage() {
    return this.pack;
  }

  public void setPackage(PackageM pack) {
    this.pack = pack;
  }

  public List getReferences() {
    return this.references;
  }

  public void setReferences(List references) {
    this.references = references;
  }

  public void addReference(ReferenceM reference) {
    this.references.add(reference);
  }

  public List getIdColumns() {
    return getPrimaryKey().getColumns();
  }

  public boolean isNullAble(ColumnM column) {
    return isNullAble(column.getId());
  }

  public boolean isNullAble(String columnId) {
    Iterator it = this.keys.iterator();
    boolean nullAble = true;
    while (it.hasNext()) {
      KeyM key = (KeyM)it.next();
      nullAble = !key.existColumn(columnId);
      if (!nullAble) {
        break;
      }
    }
    return nullAble;
  }

  public boolean isPrimaryKey(ColumnM column)
  {
    return isPrimaryKey(column.getId());
  }

  public boolean isPrimaryKey(String columnId)
  {
    KeyM k = getPrimaryKey();
    if (k != null) {
      return k.existColumn(columnId);
    }
    return false;
  }
}