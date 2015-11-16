package com.fly.firefly.ui.object;

/**
 * Created by Dell on 11/13/2015.
 */
public class Country {

    private String countryName;
    private String countryCode;

    public Country(String name,String code){
        countryName = name;
        countryCode = code;
    }

    public Country() {
    }
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

}
