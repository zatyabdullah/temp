package com.fly.firefly.ui.object;

import java.util.ArrayList;

/**
 * Created by Dell on 11/9/2015.
 */
public class DeviceInfoSuccess{

    private final DeviceInfoSuccess userObj;

    private String dataVersion;
    private ArrayList dataTitle;
    private ArrayList dataMarket;
    private String titleCode;
    private String titleName;
    private String status;
    private String signature;

    private String location;

    //ArrayList<Airport> places;
    //= new ArrayList<Airport>();

    public DeviceInfoSuccess(DeviceInfoSuccess param_obj){
        this.userObj = param_obj;
    }

    //public ArrayList<Airport> getPlaces() {
    //    return places;
   // }

   // public void setPlaces(ArrayList<Airport> places) {
    //    this.places = places;
    //}
   public String getSignature() {
       return signature;
   }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTitleCode() {
        return titleCode;
    }

    public void setTitleCode(String titleCode) {
        this.titleCode = titleCode;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    public ArrayList getDataTitle() {
        return dataTitle;
    }

    public void setDataTitle(ArrayList dataTitle) {
        this.dataTitle = dataTitle;
    }

    public ArrayList getDataMarket() {
        return dataMarket;
    }

    public void setDataMarket(ArrayList dataMarket) {
        this.dataMarket = dataMarket;
    }

    public DeviceInfoSuccess getObj() {
        return userObj;
    }


}


