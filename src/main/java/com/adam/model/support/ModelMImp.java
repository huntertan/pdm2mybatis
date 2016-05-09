package com.adam.model.support;

import java.util.ArrayList;
import java.util.List;

import com.adam.model.DomainM;
import com.adam.model.ModelM;
import com.adam.model.PackageM;

public class ModelMImp extends BaseMImp
  implements ModelM
{
  List packages = new ArrayList();

  List domains = new ArrayList();

  public List getDomains() {
    return this.domains;
  }

  public void setDomains(List domains) {
    this.domains = domains;
  }

  public List getPackages() {
    return this.packages;
  }

  public void setPackages(List packages) {
    this.packages = packages;
  }

  public void addPackage(PackageM pack) {
    this.packages.add(pack);
  }

  public void addDomain(DomainM domain) {
    this.domains.add(domain);
  }
}