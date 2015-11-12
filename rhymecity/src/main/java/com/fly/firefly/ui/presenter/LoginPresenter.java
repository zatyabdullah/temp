package com.fly.firefly.ui.presenter;

import android.util.Log;

import com.fly.firefly.api.obj.LoginReceive;
import com.fly.firefly.ui.object.LoginRequest;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class LoginPresenter {

    public interface LoginView {

        void loginSuccess(LoginReceive obj);

    }

    private final LoginView view;
    private final Bus bus;

    public LoginPresenter(LoginView view, Bus bus) {
        this.view = view;
        this.bus = bus;
    }

    public void onResume() {
        bus.register(this);
    }

    public void onPause() {
        bus.unregister(this);
    }

    public void loginFunction(LoginRequest data) {
        Log.e("xxxx",data.getUsername());
        bus.post(new LoginRequest(data));
    }

    @Subscribe
    public void onUserSuccessLogin(LoginReceive event) {

        /*Save Session And Redirect To Homepage*/
        view.loginSuccess(event.getUserObj());
    }

}
