package com.fly.firefly.ui.object;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Dell on 11/9/2015.
 */
public class AirportObj {


    private String location;
    private String locationcode;
    private String travellocation;
    private String travellocationcode;
    private final ArrayList<AirportObj> obj;


    public AirportObj(ArrayList dataObj ){
        this.obj = dataObj;
    }

    public ArrayList getObj() {
        return obj;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationcode() {
        return locationcode;
    }

    public void setLocationcode(String locationcode) {
        this.locationcode = locationcode;
    }

    public String getTravellocation() {
        return travellocation;
    }

    public void setTravellocation(String travellocation) {
        this.travellocation = travellocation;
    }

    public String getTravellocationcode() {
        return travellocationcode;
    }

    public void setTravellocationcode(String travellocationcode) {
        this.travellocationcode = travellocationcode;
    }

    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }


}
