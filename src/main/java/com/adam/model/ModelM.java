package com.adam.model;

import java.util.List;

public abstract interface ModelM extends BaseM {
    public abstract void setPackages(List paramList);

    public abstract List getPackages();

    public abstract void addPackage(PackageM paramPackageM);

    public abstract void setDomains(List paramList);

    public abstract List getDomains();

    public abstract void addDomain(DomainM paramDomainM);
}