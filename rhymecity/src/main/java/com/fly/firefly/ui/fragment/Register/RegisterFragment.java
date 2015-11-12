package com.fly.firefly.ui.fragment.Register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fly.firefly.FireFlyApplication;
import com.fly.firefly.R;
import com.fly.firefly.api.obj.RegisterReceive;
import com.fly.firefly.base.BaseFragment;
import com.fly.firefly.ui.activity.FragmentContainerActivity;
import com.fly.firefly.ui.activity.Login.LoginActivity;
import com.fly.firefly.ui.activity.Picker.CountryListDialogFragment;
import com.fly.firefly.ui.activity.Picker.DatePickerFragment;
import com.fly.firefly.ui.module.RegisterModule;
import com.fly.firefly.ui.object.DatePickerObj;
import com.fly.firefly.ui.object.RegisterObj;
import com.fly.firefly.ui.presenter.RegisterPresenter;
import com.fly.firefly.utils.DropDownItem;
import com.fly.firefly.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RegisterFragment extends BaseFragment implements RegisterPresenter.RegisterView {

    @Inject
    RegisterPresenter presenter;

    @InjectView(R.id.registerIndicator1) LinearLayout indicator1;
    @InjectView(R.id.registerIndicator2) LinearLayout indicator2;
    @InjectView(R.id.registerIndicator3) LinearLayout indicator3;

    @InjectView(R.id.registerBasicInfoBlock) LinearLayout registerPersonalInfoBlock;
    @InjectView(R.id.registerAddressInfoBlock) LinearLayout registerAddressBlock;
    @InjectView(R.id.registerContactInfoBlock) LinearLayout registerContactBlock;

    @InjectView(R.id.imageViewRegisterIndicator) ImageView imageRegisterIndicator;
    @InjectView(R.id.editTextCountry) TextView editTextCountry;
    @InjectView(R.id.editTextState) TextView editTextState;
    @InjectView(R.id.txtUsername) EditText txtUsername;
    @InjectView(R.id.txtPassword) EditText txtPassword;
    @InjectView(R.id.txtConfirmPassword) EditText txtConfirmPassword;
    @InjectView(R.id.txtFirstName) EditText txtFirstName;
    @InjectView(R.id.txtRegisterDatePicker) TextView txtRegisterDatePicker;
    @InjectView(R.id.txtLastName)EditText txtLastName;
    @InjectView(R.id.txtAddressLine1) EditText txtAddressLine1;
    @InjectView(R.id.txtAddressLine2)EditText txtAddressLine2;
    @InjectView(R.id.editTextPostcode)EditText editTextPostcode;
    @InjectView(R.id.editTextMobilePhone) EditText editTextMobilePhone;
    @InjectView(R.id.editTextAlternatePhone) EditText editTextAlternatePhone;
    @InjectView(R.id.editTextFax) EditText editTextFax;

    @InjectView(R.id.registerContinueButton) Button registerContinueButton;
    @InjectView(R.id.txtCity) EditText txtCity;
    @InjectView(R.id.txtCity) TextView txtTitle;



    private int currentPage;
    private ArrayList<DropDownItem> countrys;
    private ArrayList<DropDownItem> state;
    private int day;
    private int month;
    private int year;
    private int fragmentContainerId;
    private SharedPrefManager pref;

    public static RegisterFragment newInstance() {

        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

        // new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FireFlyApplication.get(getActivity()).createScopedGraph(new RegisterModule(this)).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.register, container, false);
        ButterKnife.inject(this, view);


        pref = new SharedPrefManager(getActivity());
        countrys = new ArrayList<DropDownItem>();
        state = new ArrayList<DropDownItem>();


        /*Add Country To DropDown List*/
        ArrayList<String> countries = new ArrayList<String>();
        countries = BaseFragment.getCountry();
        int b = 1;
        for (String country : countries) {

                DropDownItem itemCountry = new DropDownItem();
                itemCountry.setText(country);
                itemCountry.setCode(Integer.toString(b));
                itemCountry.setTag("Country");
                countrys.add(itemCountry);
         }


        for(int x = 0 ; x < 20 ; x++) {
            DropDownItem itemCountry = new DropDownItem();
            itemCountry.setText(Integer.toString(x)+"State");
            itemCountry.setCode(Integer.toString(x));
            itemCountry.setTag("State");
            state.add(itemCountry);
        }

         /*Switch register info block*/
        editTextCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCountrySelector(getActivity(),countrys);
            }
        });

        editTextState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCountrySelector(getActivity(),state);
            }
        });

        txtRegisterDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(view);
            }
        });

        /*Initial indicator*/
        imageRegisterIndicator.setBackgroundResource(R.drawable.register_account_focus);
        currentPage = 1;

        /*Switch register info block*/
        indicator1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            showHiddenBlock(1);

            }
        });

        indicator2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            showHiddenBlock(2);

            }
        });

        indicator3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            showHiddenBlock(3);

            }
        });

        registerContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHiddenBlock(currentPage+1);
            }
        });

        /*Onclick Register - > Validate -> Send to server*/
        registerContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, String> init = pref.getSignatureFromLocalStorage();
                String signatureFromLocal = init.get(SharedPrefManager.SIGNATURE);

                RegisterObj regObj = new RegisterObj();
                /*regObj.setRegisterUsername(txtUsername.getText().toString());
                regObj.setRegisterFirstname(txtFirstName.getText().toString());
                regObj.setRegisterLastname(txtLastName.getText().toString());
                regObj.setRegisterDOB(txtRegisterDatePicker.getText().toString());
                regObj.setRegisterAddressLine1(txtAddressLine1.getText().toString());
                regObj.setRegisterAddressLine2(txtAddressLine2.getText().toString());
                regObj.setRegisterPassword(txtPassword.getText().toString());
                regObj.setRegisterConfirmPassword(txtConfirmPassword.getText().toString());
                regObj.setRegisterAlternatePhone(editTextAlternatePhone.getText().toString());
                regObj.setRegisterMobilePhone(editTextMobilePhone.getText().toString());
                regObj.setRegisterCountry(editTextCountry.getText().toString());
                regObj.setRegisterState(editTextState.getText().toString());
                regObj.setRegisterFax(editTextFax.getText().toString());*/

                /*regObj.setUsername(txtUsername.getText().toString());
                regObj.setFirst_name(txtFirstName.getText().toString());
                regObj.setLast_name(txtLastName.getText().toString());
                regObj.setDob(txtRegisterDatePicker.getText().toString());
                regObj.setAddress(txtAddressLine1.getText().toString());
                //regObj.setRegisterAddressLine2(txtAddressLine2.getText().toString());
                regObj.setPassword(txtPassword.getText().toString());
                regObj.setConfirm_password(txtConfirmPassword.getText().toString());
                regObj.setAlternate_phone(editTextAlternatePhone.getText().toString());
                regObj.setMobile_phone(editTextMobilePhone.getText().toString());
                regObj.setCountry(editTextCountry.getText().toString());
                regObj.setState(editTextState.getText().toString());
                regObj.setFax(editTextFax.getText().toString());*/

                regObj.setUsername(txtUsername.getText().toString());
                regObj.setFirst_name(txtFirstName.getText().toString());
                regObj.setLast_name(txtLastName.getText().toString());
                regObj.setPassword(txtPassword.getText().toString());
                regObj.setTitle(txtTitle.getText().toString());
                regObj.setDob(txtRegisterDatePicker.getText().toString());
                Log.e("Date",txtRegisterDatePicker.getText().toString());
                regObj.setAddress_1(txtAddressLine1.getText().toString());
                regObj.setAddress_2(txtAddressLine2.getText().toString());
                regObj.setAddress_3(txtAddressLine2.getText().toString());
                //regObj.setRegisterAddressLine2(txtAddressLine2.getText().toString());
                regObj.setAlternate_phone(editTextAlternatePhone.getText().toString());
                regObj.setMobile_phone(editTextMobilePhone.getText().toString());
                regObj.setCountry("MY");
                regObj.setState("SL");
                regObj.setCity(txtCity.getText().toString());
                regObj.setPostcode(editTextPostcode.getText().toString());
                regObj.setFax(editTextFax.getText().toString());
                regObj.setSignature(signatureFromLocal);

                /*regObj.setUsername("ateh_imal900@yahoo.com");
                regObj.setFirst_name("Imala");
                regObj.setLast_name("Pashaa");
                regObj.setPassword("12345678Ab");
                regObj.setTitle("MR");
                regObj.setDob("2015-01-20");
                regObj.setAddress_1("hehehe1");
                regObj.setAddress_2("hehehe2");
                regObj.setAddress_3("hehehe3");
                //regObj.setRegisterAddressLine2(txtAddressLine2.getText().toString());
                regObj.setAlternate_phone("0193321122");
                regObj.setMobile_phone("01933212122");
                regObj.setCountry("MY");
                regObj.setState("SL");
                regObj.setCity("Klang");
                regObj.setPostcode("42000");
                regObj.setFax("01291231232");
                regObj.setSignature(signatureFromLocal);*/

               /* Log.e("RegisterAddress1", regObj.getRegisterAddressLine1());
                Log.e("RegisterAddress2",regObj.getRegisterAddressLine2());
                Log.e("Phone2",regObj.getRegisterAlternatePhone());
                Log.e("ConfirmPassword",regObj.getRegisterConfirmPassword());
                Log.e("Country",regObj.getRegisterCountry());
                Log.e("Dob",regObj.getRegisterDOB());
                Log.e("Fax",regObj.getRegisterFax());
                Log.e("FirstName",regObj.getRegisterFirstname());
                Log.e("LastName",regObj.getRegisterLastname());
                Log.e("Fax",regObj.getRegisterFax());
                Log.e("Phone1",regObj.getRegisterMobilePhone());
                Log.e("State",regObj.getRegisterState()); */

                //presenter.onRequestRegister(regObj);
            }
        });
        return view;
    }

    /*Date Picker -> need to move to main activity*/
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.setTargetFragment(RegisterFragment.this, 0);
        newFragment.show(getFragmentManager(), "datePicker");
    }


    /*Country selector - > need to move to main activity*/
    public void showCountrySelector(Activity act,ArrayList constParam)
    {
        if(act != null) {
            try {

                android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                CountryListDialogFragment countryListDialogFragment = CountryListDialogFragment.newInstance(constParam);
                countryListDialogFragment.setTargetFragment(RegisterFragment.this, 0);
                countryListDialogFragment.show(fm, "countryListDialogFragment");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

   @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       Log.e("enter here","ok");
       if (resultCode != Activity.RESULT_OK) {
           return;
        } else {
           if (requestCode == 1) {
               // if (requestCode == 1) {
               Log.e("requestCode", "1");
               DropDownItem selectedCountry = data.getParcelableExtra(CountryListDialogFragment.EXTRA_COUNTRY);

               if (selectedCountry.getTag() == "Country") {
                   editTextCountry.setText(selectedCountry.getText());
               } else {
                   editTextState.setText(selectedCountry.getText());
               }

               // } else if (requestCode == 0) {
               //    Log.e("requestCode","0");
               //controllerMessages.setVisibility(LinearLayout.GONE);
               // }
           }else{

               DatePickerObj date = (DatePickerObj)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
               String month =  getMonthAlphabet(date.getMonth());

               Log.e("Date Picker",Integer.toString(date.getMonth()));
               txtRegisterDatePicker.setText(date.getDay()+" "+month+" "+date.getYear());
        }
       }
    }

    /*Change Field Block*/
    public void showHiddenBlock(int page)
    {
        if(page == 1){
            imageRegisterIndicator.setBackgroundResource(R.drawable.register_account_focus);
            resetRegisterFieldBlockVisibility();
            registerPersonalInfoBlock.setVisibility(View.VISIBLE);
            currentPage = 1;
        }
        else if(page == 2){
            imageRegisterIndicator.setBackgroundResource(R.drawable.register_address_focus);
            resetRegisterFieldBlockVisibility();
            registerAddressBlock.setVisibility(View.VISIBLE);
            currentPage = 2;
        }
        else if(page == 3){
            imageRegisterIndicator.setBackgroundResource(R.drawable.register_contact_focus);
            resetRegisterFieldBlockVisibility();
            registerContactBlock.setVisibility(View.VISIBLE);
            currentPage = 3;
        }
        else if(page == 4){
            Log.e("READY FOR VALIDATION","TRUE");
        }
        else {
            getActivity().finish();
        }

    }

    /*Set all block visibility to - GONE*/
    public void resetRegisterFieldBlockVisibility(){
        registerPersonalInfoBlock.setVisibility(View.GONE);
        registerAddressBlock.setVisibility(View.GONE);
        registerContactBlock.setVisibility(View.GONE);
    }

    @Override
    public void onSuccessRegister(RegisterReceive obj) {
        Log.e("Here", obj.getStatus());

        if (obj.getStatus() == "success") {

            pref.setSignatureToLocalStorage(obj.getUserInfo().getSignature());

            HashMap<String, String> init = pref.getSignatureFromLocalStorage();
            String signatureFromLocal = init.get(SharedPrefManager.SIGNATURE);

            Log.e("SignatureFromLocal", signatureFromLocal);
            Log.e("SignatureFromServer", obj.getUserInfo().getSignature());

            Intent home = new Intent(getActivity(), LoginActivity.class);
            getActivity().startActivity(home);
            getActivity().finish();

        } else {
            Log.e("Messages", obj.getUserObj().getMessage());
        }
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
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    public void registerBackFunction()
    {
            showHiddenBlock(currentPage-1);

    }

}
