package com.bhatt.milan.kisanhubdemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeoLocation {

    @SerializedName("farms")
    @Expose
    private List<Farms> farms = null;
    @SerializedName("fields")
    @Expose
    private List<Field> fields = null;

    public List<Farms> getFarms() {
        return farms;
    }

    public void setFarms(List<Farms> farms) {
        this.farms = farms;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
