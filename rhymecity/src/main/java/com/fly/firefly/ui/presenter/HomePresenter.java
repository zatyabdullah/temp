package com.fly.firefly.ui.presenter;

import android.util.Log;

import com.fly.firefly.rhymes.RhymesRequestedEvent;
import com.fly.firefly.ui.object.AirportObj;
import com.fly.firefly.ui.object.DeviceInfoSuccess;
import com.fly.firefly.ui.object.DeviceInformation;
import com.google.gson.Gson;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomePresenter {

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

        //Log.e("Signature", event.getSignature());

        ArrayList<AirportObj> customObjList = new ArrayList<>();
        //Log.e("x",customObjList.get(1).getLocation());
        customObjList = (ArrayList) event.getObj().getDataMarket();
        //Log.e("XxxXxXx", customObjList.get(1).getLocationcode());
        //Log.e("XxxXxXx", customObjList.toString());

        JSONArray json = new JSONArray(customObjList);

       // Gson gson = new Gson();
       // String countryList = gson.toJson(json);

       /// AirportObj m = gson.fromJson(countryList, AirportObj.class);
       // Log.e("x",m.getLocation());


        //logs = gson.fromJson(json, new TypeToken<List<AirportObj>>(){}.getType());
        /*iterate obj data - using loop*/
        for (int i = 0; i < json.length(); i++) {
            /*create json object*/
            JSONObject row = (JSONObject) json.opt(i);
            Log.e("x", row.optString("location"));
            /*insert data into db - function located at MainFragmentActivity*/
       }

       /* Gson gson = new Gson();
        String[] gsonDepartmentList = new String[customObjList.size()];
        String gsonHtmlList = gson.toJson(customObjList);

        for (int i = 0; i < json.length(); i++) {

            JSONObject row = (JSONObject) json.opt(i);

        }*/

        /*Set set = customObjList.entrySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            gsonDepartmentList[Integer.parseInt(me.getKey().toString()) - 1] = gson.toJson(me.getValue());
        }*/

      //  Log.e("gsonHtmlList",gsonHtmlList);


        //Log.e("XxxXxXx", customObjList.get(1).toString());

        //Log.e("XxxXxXx", (ArrayList) event.getObj().getDataMarket().get(1).toString());

       // ArrayList<AirportObj> air = event.getObj().getDataMarket();
        //Log.e("XxxXxXx", stringToArray("TEST", AirportObj[].class).get(1).getLocation());

       // Gson gson = new GsonBuilder().create();
       // gson.toJson(event);

        //System.out.println(gson);

        /*Save Session And Redirect To Homepage*/
        view2.loadingSuccess(event);
    }

    public static <T> List<T> stringToArray(String s, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(s, clazz);
        return Arrays.asList(arr); //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
    }

}
