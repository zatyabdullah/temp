package com.fly.firefly.ui.activity.PasswordExpired;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fly.firefly.AnalyticsApplication;
import com.fly.firefly.FireFlyApplication;
import com.fly.firefly.R;
import com.fly.firefly.base.BaseFragment;
import com.fly.firefly.ui.activity.FragmentContainerActivity;
import com.fly.firefly.ui.module.ChangePasswordModule;
import com.fly.firefly.ui.object.ChangePasswordRequest;
import com.fly.firefly.ui.presenter.ChangePasswordPresenter;
import com.fly.firefly.utils.SharedPrefManager;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class ChangePasswordFragment extends BaseFragment implements ChangePasswordPresenter.ChangePasswordView,Validator.ValidationListener  {

    // Validator Attributes
    private Validator mValidator;
    private Tracker mTracker;
    @Inject
    ChangePasswordPresenter presenter;

    //@InjectView(R.id.search_edit_text) EditText searchEditText;
    @Order(1)@NotEmpty(sequence = 1)@Email(sequence = 2)@InjectView(R.id.editTextemail) EditText editTextemail;
    @Order(2)@NotEmpty (sequence = 1)@InjectView(R.id.editTextforgotPasswordCurrent) EditText editTextPasswordCurrent;
    @Order(3)@NotEmpty (sequence = 1)@Password(sequence = 2) @InjectView(R.id.editTextforgotPasswordConfirm) EditText editTextPasswordConfirm;
    @Order(4)@NotEmpty(sequence = 1)@ConfirmPassword(sequence = 2) @InjectView(R.id.editTextforgotPasswordNew) EditText editTextPasswordNew;
    @InjectView(R.id.btnchangepassword) Button changepasswordButton;


    private AlertDialog dialog;
    private SharedPrefManager pref;

    private int fragmentContainerId;

    public static ChangePasswordFragment newInstance() {

        ChangePasswordFragment fragment = new ChangePasswordFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FireFlyApplication.get(getActivity()).createScopedGraph(new ChangePasswordModule(this)).inject(this);
        // Validator
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
        mValidator.setValidationMode(Validator.Mode.BURST);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_password_expired, container, false);
        ButterKnife.inject(this, view);

        pref = new SharedPrefManager(getActivity());

        // [START shared_tracker]
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        // [END shared_tracker]

        changepasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validate form
                // Log.e("selectedTitle",selectedTitle);

                mValidator.validate();

            }
        });

        return view;
    }

    //Validator Result//
    @Override
    public void onValidationSucceeded() {
        requestChangePassword(editTextemail.getText().toString(), editTextPasswordCurrent.getText().toString(), editTextPasswordNew.getText().toString());
        //Crouton.makeText(getActivity(), "Success", Style.CONFIRM).show();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();

             /* Split Error Message. Display first sequence only */
            String message = error.getCollatedErrorMessage(getActivity());
            String splitErrorMsg[] = message.split("\\r?\\n");

            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(splitErrorMsg[0]);
            } else {
                Crouton.makeText(getActivity(), message, Style.ALERT).show();
            }
        }
    }



    public void requestChangePassword(String username,String currentpassword ,String newpassword){
        ChangePasswordRequest data = new ChangePasswordRequest();
        data.setEmail(username);
        data.setNewPassword(newpassword);
        data.setCurrentPassword(currentpassword);
        presenter.changePassword(data);
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
        Log.i("Page Name", "Setting screen name: " + "Change password Page");
        mTracker.setScreenName("Login" + "B");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }
}
