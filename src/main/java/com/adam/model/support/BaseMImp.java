package com.adam.model.support;

import com.adam.model.BaseM;

public class BaseMImp implements BaseM {
    private String id = "";

    private String objectId = "";

    private String name = "";

    private String code = "";
    
    private String comment = "";
    
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getObjectId() {
        return this.objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment=comment;
    }
}
