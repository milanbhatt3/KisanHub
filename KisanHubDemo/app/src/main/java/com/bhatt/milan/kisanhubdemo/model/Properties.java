package com.bhatt.milan.kisanhubdemo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Properties {

    @SerializedName("farm_latitude")
    @Expose
    private Float farmLatitude;
    @SerializedName("permission")
    @Expose
    private Boolean permission;
    @SerializedName("farm_longitude")
    @Expose
    private Float farmLongitude;
    @SerializedName("farm_name")
    @Expose
    private String farmName;
    @SerializedName("farm_slug")
    @Expose
    private String farmSlug;
    @SerializedName("team_id")
    @Expose
    private Long teamId;
    @SerializedName("farm_location")
    @Expose
    private String farmLocation;
    @SerializedName("ownership")
    @Expose
    private Boolean ownership;
    @SerializedName("location_slug")
    @Expose
    private String locationSlug;

    public Float getFarmLatitude() {
        return farmLatitude;
    }

    public void setFarmLatitude(Float farmLatitude) {
        this.farmLatitude = farmLatitude;
    }

    public Boolean getPermission() {
        return permission;
    }

    public void setPermission(Boolean permission) {
        this.permission = permission;
    }

    public Float getFarmLongitude() {
        return farmLongitude;
    }

    public void setFarmLongitude(Float farmLongitude) {
        this.farmLongitude = farmLongitude;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
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

    public String getFarmLocation() {
        return farmLocation;
    }

    public void setFarmLocation(String farmLocation) {
        this.farmLocation = farmLocation;
    }

    public Boolean getOwnership() {
        return ownership;
    }

    public void setOwnership(Boolean ownership) {
        this.ownership = ownership;
    }

    public String getLocationSlug() {
        return locationSlug;
    }

    public void setLocationSlug(String locationSlug) {
        this.locationSlug = locationSlug;
    }
}
