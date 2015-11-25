package com.fly.firefly.ui.presenter;

import android.util.Log;

import com.fly.firefly.MainFragmentActivity;
import com.fly.firefly.rhymes.RhymesRequestedEvent;
import com.fly.firefly.ui.object.AirportObj;
import com.fly.firefly.ui.object.Country;
import com.fly.firefly.ui.object.DeviceInfoSuccess;
import com.fly.firefly.ui.object.DeviceInformation;
import com.fly.firefly.ui.object.TitleObj;
import com.fly.firefly.utils.SharedPrefManager;
import com.google.gson.Gson;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomePresenter {

    private SharedPrefManager pref;

    public interface HomeView {

    }

    public interface SplashScreen {
        void loadingSuccess(DeviceInfoSuccess obj);
        //void test();
    }

    private HomeView view;
    private SplashScreen view2;

    private final Bus bus;

    public HomePresenter(HomeView view, Bus bus) {
        this.view = view;
        this.bus = bus;
    }

    public HomePresenter(SplashScreen view, Bus bus) {
        this.view2 = view;
        this.bus = bus;
    }

   // public HomePresenter(SplashScreen view, Bus bus) {
    //    this.view2 = view;
    //    this.bus = bus;
    //}

    public void onResume() {
        bus.register(this);
    }

    public void onPause() {
        bus.unregister(this);
    }

    public void onRequestGoogleData() {
        bus.post(new RhymesRequestedEvent("adam"));
    }

    public void deviceInformation(DeviceInformation info) {
        bus.post(new DeviceInformation(info));
    }

    @Subscribe
    public void onSuccessSendDeviceInformation(DeviceInfoSuccess event) {

        pref = new SharedPrefManager(MainFragmentActivity.getContext());
        pref.clearTitle();

        /*Save Title Data to SharedPref*/
        ArrayList<TitleObj> dataTitle = new ArrayList<>();
        JSONArray jsonTitle = new JSONArray(event.getObj().getDataTitle());

        for (int i = 0; i < jsonTitle.length(); i++) {
            /*create json object*/
            JSONObject row = (JSONObject) jsonTitle.opt(i);
            TitleObj dataTitleObj = new TitleObj();
            dataTitleObj.setTitleCode(row.optString("titlecode"));
            dataTitleObj.setTitleName(row.optString("titlename"));

            dataTitle.add(dataTitleObj);
        }

        Gson gsonTitle = new Gson();
        String title = gsonTitle.toJson(dataTitle);
        Log.e("title",title);
        pref.setUserTitle(title);


         /*Save Country Data to SharedPref*/
        ArrayList<Country> dataCountry = new ArrayList<>();
        //dataTitle = (ArrayList) event.getObj().getDataTitle();
        JSONArray jsonCountry = new JSONArray(event.getObj().getDataCountry());

        for (int i = 0; i < jsonCountry.length(); i++) {
            /*create json object*/
            JSONObject row = (JSONObject) jsonCountry.opt(i);
            Country dataCountryObj = new Country();
            dataCountryObj.setCountryCode(row.optString("countrycode"));
            dataCountryObj.setCountryName(row.optString("countryname"));
            dataCountryObj.setCountryCallingCode(row.optString("countryname"));

            dataCountry.add(dataCountryObj);
        }

        Gson gsonCountry = new Gson();
        String country = gsonCountry.toJson(dataCountry);
        pref.setCountry(country);


        /*Save Flight Data to SharedPref*/
        ArrayList<AirportObj> dataFlight = new ArrayList<>();
        //dataFlight = (ArrayList) event.getObj().getDataMarket();
        JSONArray jsonFlight = new JSONArray(event.getObj().getDataMarket());


        for (int i = 0; i < jsonFlight.length(); i++) {
            JSONObject row = (JSONObject) jsonFlight.opt(i);

            AirportObj airportObj = new AirportObj();
            airportObj.setLocation(row.optString("location"));
            airportObj.setLocationcode(row.optString("locationcode"));
            airportObj.setTravellocation(row.optString("travellocation"));
            airportObj.setTravellocationcode(row.optString("travellocationcode"));
            airportObj.setStatus(row.optString("status"));
            airportObj.setMobilecheckin(row.optString("mobilecheckin"));
            dataFlight.add(airportObj);
       }

        Gson gsonFlight = new Gson();
        String flight = gsonFlight.toJson(dataFlight);
        pref.setFlight(flight);

        view2.loadingSuccess(event);

        /*Dummy Retrieve Data*/
    }

}
