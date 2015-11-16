package com.fly.firefly.ui.fragment.Login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fly.firefly.AnalyticsApplication;
import com.fly.firefly.FireFlyApplication;
import com.fly.firefly.R;
import com.fly.firefly.api.obj.LoginReceive;
import com.fly.firefly.base.BaseFragment;
import com.fly.firefly.ui.activity.BookingFlight.SearchFlightActivity;
import com.fly.firefly.ui.activity.FragmentContainerActivity;
import com.fly.firefly.ui.object.LoginRequest;
import com.fly.firefly.ui.activity.Register.RegisterActivity;
import com.fly.firefly.ui.module.LoginModule;
import com.fly.firefly.ui.presenter.LoginPresenter;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginFragment extends BaseFragment implements LoginPresenter.LoginView {

    private Tracker mTracker;
    @Inject
    LoginPresenter presenter;

    //@InjectView(R.id.search_edit_text) EditText searchEditText;
    @InjectView(R.id.registerBtn) Button registerButton;
    @InjectView(R.id.btnLogin) Button btnLogin;

    @InjectView(R.id.txtForgotPassword)
    TextView txtForgotPassword;

    @InjectView(R.id.txtLoginEmail)
    EditText txtLoginEmail;

    @InjectView(R.id.txtLoginPassword)
    EditText txtLoginPassword;

    AlertDialog dialog;

    private int fragmentContainerId;

    public static LoginFragment newInstance() {

        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FireFlyApplication.get(getActivity()).createScopedGraph(new LoginModule(this)).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login, container, false);
        ButterKnife.inject(this, view);

        // [START shared_tracker]
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        // [END shared_tracker]

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goRegisterPage();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goBookingPage();
                loginFromFragment(txtLoginEmail.getText().toString(), txtLoginPassword.getText().toString());
            }
        });

        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });

        return view;
    }


    public void loginFromFragment(String username,String password){
        LoginRequest data = new LoginRequest();

        /* Dummy Data For Testing - Imal */
        data.setUsername("zhariffadam00@me-tech.com.my");
        data.setPassword("P@$$w0rd");

        /*
            data.setUsername(username);
            data.setPassword(password);
        */

        presenter.loginFunction(data);
    }


    /*Public-Inner Func*/
    public void goRegisterPage()
    {
        Intent loginPage = new Intent(getActivity(), RegisterActivity.class);
        getActivity().startActivity(loginPage);
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Click register page")
                .build());
    }

    public void goBookingPage()
    {
        Intent loginPage = new Intent(getActivity(), SearchFlightActivity.class);
        getActivity().startActivity(loginPage);
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Click booking page")
                .build());
    }


    @Override
    public void loginSuccess(LoginReceive obj) {


        if (obj.getStatus() == "Success") {

            Log.e("username", "HERE");
            Log.e("obj", obj.getUserInfo().getUsername());

        }else{
            Log.e("obj", obj.getMessage());
            Log.e("Login", "Error");

        }



    }

    /*Popup Forgot Password*/
    public void forgotPassword(){

        LayoutInflater li = LayoutInflater.from(getActivity());
        final View myView = li.inflate(R.layout.forgot_password_screen, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(myView);

        dialog = builder.create();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //lp.height = 570;
        dialog.getWindow().setAttributes(lp);

        dialog.show();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentContainerId = ((FragmentContainerActivity) getActivity()).getFragmentContainerId();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
        Log.i("Page Name", "Setting screen name: " + "Login Page");
        mTracker.setScreenName("Login" + "B");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }
}
