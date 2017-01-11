package com.trs.commodity.model;

public class CountryDict {
    private String countryCode;

    private String countryName;

    private String countryChnName;

    private String areaCode;

    private String area;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode == null ? null : countryCode.trim();
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName == null ? null : countryName.trim();
    }

    public String getCountryChnName() {
        return countryChnName;
    }

    public void setCountryChnName(String countryChnName) {
        this.countryChnName = countryChnName == null ? null : countryChnName.trim();
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }
}