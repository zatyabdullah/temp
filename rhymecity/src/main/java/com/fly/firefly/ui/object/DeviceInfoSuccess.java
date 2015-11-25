package com.fly.firefly.ui.object;

import java.util.ArrayList;

/**
 * Created by Dell on 11/9/2015.
 */
public class DeviceInfoSuccess{

    private final DeviceInfoSuccess userObj;

    private String dataVersion;
    private ArrayList dataTitle;
    private ArrayList dataCountry;
    private ArrayList dataMarket;
    private String titleCode;
    private String titleName;
    private String status;
    private String signature;
    private String location;
    private String bannerDefault;
    private String bannerPromo;


    public String getBannerDefault() {
        return bannerDefault;
    }

    public void setBannerDefault(String bannerDefault) {
        this.bannerDefault = bannerDefault;
    }

    public String getBannerPromo() {
        return bannerPromo;
    }

    public void setBannerPromo(String bannerPromo) {
        this.bannerPromo = bannerPromo;
    }

    public DeviceInfoSuccess(DeviceInfoSuccess param_obj){
        this.userObj = param_obj;
    }


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


    public ArrayList getDataCountry() {
        return dataCountry;
    }

    public void setDataCountry(ArrayList dataCountry) {
        this.dataCountry = dataCountry;
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
//

    public DeviceInfoSuccess getObj() {
        return userObj;
    }


}


