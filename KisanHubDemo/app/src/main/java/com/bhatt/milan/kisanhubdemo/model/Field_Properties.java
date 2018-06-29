package com.bhatt.milan.kisanhubdemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Field_Properties {

    @SerializedName("permission")
    @Expose
    private Boolean permission;
    @SerializedName("soil_type")
    @Expose
    private String soilType;
    @SerializedName("field_id")
    @Expose
    private Long fieldId;
    @SerializedName("farm_slug")
    @Expose
    private String farmSlug;
    @SerializedName("team_id")
    @Expose
    private Long teamId;
    @SerializedName("ownership")
    @Expose
    private Boolean ownership;
    @SerializedName("team_name")
    @Expose
    private String teamName;
    @SerializedName("location_slug")
    @Expose
    private String locationSlug;
    @SerializedName("field_area")
    @Expose
    private Float fieldArea;
    @SerializedName("farm_name")
    @Expose
    private String farmName;
    @SerializedName("field_name")
    @Expose
    private String fieldName;
    @SerializedName("notes")
    @Expose
    private Object notes;
    @SerializedName("field_longitude")
    @Expose
    private Float fieldLongitude;
    @SerializedName("field_slug")
    @Expose
    private String fieldSlug;
    @SerializedName("workable_area")
    @Expose
    private Float workableArea;
    @SerializedName("field_latitude")
    @Expose
    private Float fieldLatitude;

    public Boolean getPermission() {
        return permission;
    }

    public void setPermission(Boolean permission) {
        this.permission = permission;
    }

    public String getSoilType() {
        return soilType;
    }

    public void setSoilType(String soilType) {
        this.soilType = soilType;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public String getFarmSlug() {
        return farmSlug;
    }

    public void setFarmSlug(String farmSlug) {
        this.farmSlug = farmSlug;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Boolean getOwnership() {
        return ownership;
    }

    public void setOwnership(Boolean ownership) {
        this.ownership = ownership;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getLocationSlug() {
        return locationSlug;
    }

    public void setLocationSlug(String locationSlug) {
        this.locationSlug = locationSlug;
    }

    public Float getFieldArea() {
        return fieldArea;
    }

    public void setFieldArea(Float fieldArea) {
        this.fieldArea = fieldArea;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getNotes() {
        return notes;
    }

    public void setNotes(Object notes) {
        this.notes = notes;
    }

    public Float getFieldLongitude() {
        return fieldLongitude;
    }

    public void setFieldLongitude(Float fieldLongitude) {
        this.fieldLongitude = fieldLongitude;
    }

    public String getFieldSlug() {
        return fieldSlug;
    }

    public void setFieldSlug(String fieldSlug) {
        this.fieldSlug = fieldSlug;
    }

    public Float getWorkableArea() {
        return workableArea;
    }

    public void setWorkableArea(Float workableArea) {
        this.workableArea = workableArea;
    }

    public Float getFieldLatitude() {
        return fieldLatitude;
    }

    public void setFieldLatitude(Float fieldLatitude) {
        this.fieldLatitude = fieldLatitude;
    }
}
