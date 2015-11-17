package com.fly.firefly.api;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;

import com.fly.firefly.MainFragmentActivity;
import com.fly.firefly.api.obj.FailedConnectToServer;
import com.fly.firefly.api.obj.LoginReceive;
import com.fly.firefly.api.obj.RegisterReceive;
import com.fly.firefly.ui.object.DeviceInfoSuccess;
import com.fly.firefly.ui.object.DeviceInformation;
import com.fly.firefly.ui.object.LoginRequest;
import com.fly.firefly.ui.object.RegisterObj;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ApiRequestHandler {

    private final Bus bus;
    private final ApiService apiService;
    Activity context;
    ProgressDialog mProgressDialog;
    public ApiRequestHandler(Bus bus, ApiService apiService) {
        this.bus = bus;
        this.apiService = apiService;
    }


    @Subscribe
    public void onLoginRequest(final LoginRequest event) {

        Log.e("Username", event.getUsername());
        Log.e("Password", event.getPassword());

        initiateLoading();
        loading(true);


        apiService.onRequestToLogin(event, new Callback<LoginReceive>() {

            @Override
            public void success(LoginReceive rhymesResponse, Response response) {

               Log.e("Success","OK");
               bus.post(new LoginReceive(rhymesResponse));
               loading(false);
            }

            @Override
            public void failure(RetrofitError error) {

                bus.post(new FailedConnectToServer("Unable to connect to server"));
                loading(false);
            }

        });
    }

    /* Subscribe From HomePresenter - Send Device Information to server - ImalPasha */
    @Subscribe
    public void onDeviceInfo(final DeviceInformation event) {

        Log.e("getDeviceId", event.getDeviceId());
        Log.e("Password", event.getBrand());

        //initiateLoading();
        //loading(true);


        apiService.onSendDeviceInfo(event, new Callback<DeviceInfoSuccess>() {

            @Override
            public void success(DeviceInfoSuccess deviceResponse, Response response) {

               // Log.e("deviceResponse", deviceResponse.getDataMarket().toString());
               // bus.post(new AirportObj(deviceResponse.getDataMarket()));
                bus.post(new DeviceInfoSuccess(deviceResponse));

                // List<Airport> ee = deviceResponse.getDataMarket();
               // Log.e("TEST",ee.get(1).getLocationcode());
                //loading(false);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("Error",error.getMessage());
                //loading(false);
                //bus.post(new RhymesFailureEvent(rhymesResponse));
            }

        });
    }


    @Subscribe
    public void onRegisterRequest(final RegisterObj event) {

        initiateLoading();
        loading(true);


        apiService.onRegisterRequest(event, new Callback<RegisterReceive>() {

            @Override
            public void success(RegisterReceive rhymesResponse, Response response) {

                Log.e("Success","True");
                bus.post(new RegisterReceive(rhymesResponse));
                loading(false);
            }

            @Override
            public void failure(RetrofitError error) {

                Log.e("Failed","True");
                loading(false);
                //bus.post(new RhymesFailureEvent(rhymesResponse));
            }

        });
    }

    /* ------------------------- Loading ------------------------- */

    public void initiateLoading(){

        context = MainFragmentActivity.getContext();
        mProgressDialog = new ProgressDialog(context);

    }

    public void loading(Boolean load){

        if(load){

            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.show();

        }else
        {
            if(mProgressDialog.isShowing()){
                mProgressDialog.dismiss();
            }
        }

    }

}

//apiService.onRequestToLogin(event.getUsername(),event.getPassword(), new Callback<LoginRequest>() {
//apiService.onRequestToLogin(123,"zhariffadam@me-tech.com.my","P@$$w0rd", new Callback<LoginRequest>() {
//apiService.getFeed2("adam", new Callback<LoginRequest>() {