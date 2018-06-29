package com.bhatt.milan.kisanhubdemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Field {

    @SerializedName("geometry")
    @Expose
    private Field_Geometry geometry;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("properties")
    @Expose
    private Field_Properties properties;

    public Field_Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Field_Geometry geometry) {
        this.geometry = geometry;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Field_Properties getProperties() {
        return properties;
    }

    public void setProperties(Field_Properties properties) {
        this.properties = properties;
    }
}
