package com.adam.model.support;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.adam.model.ColumnM;
import com.adam.model.KeyM;
import com.adam.model.KeyType;

public class KeyMImp extends BaseMImp
  implements KeyM
{
  List columns = new ArrayList();

  KeyType type = new KeyType(1);

  public List getColumns()
  {
    return this.columns;
  }

  public void setColumns(List columns) {
    this.columns = columns;
  }

  public KeyType getType() {
    return this.type;
  }

  public void setType(KeyType type)
  {
  }

  public void addColumn(ColumnM column) {
    this.columns.add(column);
  }

  public String getRef() {
    throw new RuntimeException(" can not invoke ! ");
  }

  public void setRef(String ref) {
    throw new RuntimeException(" can not invoke ! ");
  }

  public boolean isPrimaryKey() {
    return true;
  }

  public boolean existColumn(String id) {
    boolean exist = false;
    Iterator it = this.columns.iterator();
    while ((it != null) && (it.hasNext())) {
      exist = id.equals(((ColumnM)it.next()).getId());
      if (exist) {
        break;
      }
    }
    return exist;
  }
}