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
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Optional;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Past;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class RegisterFragment extends BaseFragment implements RegisterPresenter.RegisterView,Validator.ValidationListener {


    // Validator Attributes
    private Validator mValidator;
    @Inject
    RegisterPresenter presenter;

    @InjectView(R.id.registerIndicator1) LinearLayout indicator1;
    @InjectView(R.id.registerIndicator2) LinearLayout indicator2;
    @InjectView(R.id.registerIndicator3) LinearLayout indicator3;

    @InjectView(R.id.registerBasicInfoBlock) LinearLayout registerPersonalInfoBlock;
    @InjectView(R.id.registerAddressInfoBlock) LinearLayout registerAddressBlock;
    @InjectView(R.id.registerContactInfoBlock) LinearLayout registerContactBlock;

    @InjectView(R.id.imageViewRegisterIndicator) ImageView imageRegisterIndicator;
    @Order(14) @NotEmpty @InjectView(R.id.editTextCountry) TextView editTextCountry;
    @InjectView(R.id.editTextState) TextView editTextState;

    @NotEmpty(sequence = 1)
    @Order(1)
    @InjectView(R.id.txtUsername) EditText txtUsername;

    @Order(2)
    @NotEmpty(sequence = 1)
    @Length(sequence = 2, min = 6, message = "Must at least 6 character")
    @Password(sequence =3,scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS,message = "Must have uppercase char,number and symbols") // Password validator
    @InjectView(R.id.txtPassword) EditText txtPassword;

    @Order(3)
    @NotEmpty(sequence = 1)
    @ConfirmPassword(sequence = 2)
    @InjectView(R.id.txtConfirmPassword) EditText txtConfirmPassword;

    @Order(4) @NotEmpty @InjectView(R.id.txtFirstName)
    EditText txtFirstName;

    @Order(5) @NotEmpty(sequence = 1) @Past(sequence = 2)
    @InjectView(R.id.txtRegisterDatePicker)
    TextView txtRegisterDatePicker;

    @Order(6) @NotEmpty(sequence = 1)@InjectView(R.id.txtLastName)
    EditText txtLastName;

    @Order(7) @NotEmpty(sequence = 1)@InjectView(R.id.txtAddressLine1)
    EditText txtAddressLine1;

    @Order(8) @Optional @InjectView(R.id.txtAddressLine2)
    EditText txtAddressLine2;

    @Order(9) @NotEmpty(sequence = 1) @Length(sequence = 2, min = 5,max = 7, message = "invalid postcode")@InjectView(R.id.editTextPostcode)
    EditText editTextPostcode;

    @Order(10) @NotEmpty(sequence = 1) @Length(sequence = 2, min = 6,max = 14, message = "invalid phone number")@InjectView(R.id.editTextMobilePhone) EditText editTextMobilePhone;
    @Order(11)@Optional @Length(sequence = 1, min = 6,max = 14, message = "invalid phone number")@InjectView(R.id.editTextAlternatePhone)
    EditText editTextAlternatePhone;

    @Order(12)@Optional @InjectView(R.id.editTextFax)
    EditText editTextFax;

    @InjectView(R.id.registerContinueButton)
    Button registerContinueButton;

    @Order(13)@NotEmpty @InjectView(R.id.txtCity)
    EditText txtCity;

    @Order(15)@NotEmpty @InjectView(R.id.txtTitle)
    TextView txtTitle;



    private int currentPage;
    private ArrayList<DropDownItem> countrys;
    private ArrayList<DropDownItem> state;
    private int day;
    private int month;
    private int year;
    private int fragmentContainerId;
    private SharedPrefManager pref;
    private int month_number;
    private DatePickerObj date;
    private ArrayList<DropDownItem> titleList;
    private String selectedTitle;
    String[] state_val;
    private String selectedState;
    private String selectedCountryCode;

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
        // Validator
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
        mValidator.setValidationMode(Validator.Mode.BURST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.register, container, false);
        ButterKnife.inject(this, view);


        pref = new SharedPrefManager(getActivity());
        countrys = new ArrayList<DropDownItem>();
        state = new ArrayList<DropDownItem>();
        titleList = new ArrayList<DropDownItem>();



        /*Add Country To DropDown List*/
        ArrayList<String> countries = new ArrayList<String>();
        countries = BaseFragment.getCountry();

        int b = 1;

        for (String country : countries) {
            int y = 1;

             /*Split data*/
            String[] parts = country.split("-");
            String state_name = parts[0];
            String state_code = parts[1];

            DropDownItem itemCountry = new DropDownItem();
            itemCountry.setText(state_name+" "+state_code);
            itemCountry.setCode(state_code);
            itemCountry.setTag("Country");
            itemCountry.setId(y);
            countrys.add(itemCountry);

            y++;

         }

        /* Get State From Local String*/
        state_val = getResources().getStringArray(R.array.state);
        for(int x = 0 ; x < state_val.length ; x++) {

            /*Split data*/
            String[] parts = state_val[x].split("-");
            String state_name = parts[0];
            String state_code = parts[1];

            DropDownItem itemCountry = new DropDownItem();
            itemCountry.setText(state_name);
            itemCountry.setCode(state_code);
            itemCountry.setTag("State");
            itemCountry.setId(x);
            state.add(itemCountry);
            titleList.add(itemCountry);

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
                showCountrySelector(getActivity(), state);
            }
        });

        txtRegisterDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(view);

            }
        });

        txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Log.e("Clicked", "Ok");
               popupSelection(titleList, getActivity(),txtTitle);
               //selectedTitle = getSelectedPopupSelection(getActivity());
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
                //Validate form
               // Log.e("selectedTitle",selectedTitle);
                mValidator.validate();

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
       Log.e("enter here", "ok");
       if (resultCode != Activity.RESULT_OK) {
           return;
        } else {
           if (requestCode == 1) {
               // if (requestCode == 1) {
               Log.e("requestCode", "1");
               DropDownItem selectedCountry = data.getParcelableExtra(CountryListDialogFragment.EXTRA_COUNTRY);

               if (selectedCountry.getTag() == "Country") {
                   editTextCountry.setText(selectedCountry.getText());
                   selectedCountryCode = selectedCountry.getCode();
               } else {
                   editTextState.setText(selectedCountry.getText());
                   selectedState = selectedCountry.getCode();
               }

               // } else if (requestCode == 0) {
               //    Log.e("requestCode","0");
               //controllerMessages.setVisibility(LinearLayout.GONE);
               // }
           }else{

               date = (DatePickerObj)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
               String month =  getMonthAlphabet(date.getMonth());
               month_number = date.getMonth();
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
        else if (page == 4) {
            Log.e("READY FOR VALIDATION", "TRUE");
            requestRegister();
        }
        else {
            getActivity().finish();
        }

    }

    public void requestRegister(){

        HashMap<String, String> init = pref.getSignatureFromLocalStorage();
        String signatureFromLocal = init.get(SharedPrefManager.SIGNATURE);

        RegisterObj regObj = new RegisterObj();

        //Reconstruct DOB
        String dob = date.getYear()+"-"+date.getMonth()+"-"+date.getDay();

        regObj.setUsername(txtUsername.getText().toString());
        regObj.setFirst_name(txtFirstName.getText().toString());
        regObj.setLast_name(txtLastName.getText().toString());
        regObj.setPassword(txtPassword.getText().toString());
        //regObj.setTitle(txtTitle.getText().toString());
        regObj.setTitle("MR");
        regObj.setDob(dob);
        regObj.setAddress_1(txtAddressLine1.getText().toString());
        regObj.setAddress_2(txtAddressLine2.getText().toString());
        regObj.setAddress_3(txtAddressLine2.getText().toString());
        regObj.setAlternate_phone(editTextAlternatePhone.getText().toString());
        regObj.setMobile_phone(editTextMobilePhone.getText().toString());
        regObj.setCountry(selectedState);
        regObj.setState(selectedCountryCode);
        //Log.e("State",selectedState);
        //Log.e("Country", selectedCountryCode);
        regObj.setCity(txtCity.getText().toString());
        regObj.setPostcode(editTextPostcode.getText().toString());
        regObj.setFax(editTextFax.getText().toString());
        regObj.setSignature("");

        presenter.onRequestRegister(regObj);
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

        if (obj.getStatus().equals("success")) {

            pref.setSignatureToLocalStorage(obj.getUserInfo().getSignature());

            //HashMap<String, String> init = pref.getSignatureFromLocalStorage();
           //String signatureFromLocal = init.get(SharedPrefManager.SIGNATURE);

            Intent home = new Intent(getActivity(), LoginActivity.class);
            getActivity().startActivity(home);
            getActivity().finish();

            Log.e("success", "True");

        }
        else if (obj.getStatus().equals("error")) {
            Crouton.makeText(getActivity(), obj.getMessage(), Style.ALERT).show();
            Log.e("ELSE if", "True");

        }else{

            Log.e("ELSE","True");
            /*Expired*/

            Crouton.makeText(getActivity(), obj.getMessage(), Style.ALERT).show();
            //pref.clearSignatureFromLocalStorage();

           // Intent home = new Intent(getActivity(), HomeActivity.class);
           // getActivity().startActivity(home);
           // getActivity().finish();

        }
    }

    //Validator Result//
    @Override
    public void onValidationSucceeded() {
        Log.e("Validation Success", "True");
        // Toast.makeText(getActivity(), "Login Success!", Toast.LENGTH_SHORT).show();
        //Crouton.makeText(getActivity(), "Registration Success", Style.CONFIRM).show();
        showHiddenBlock(currentPage + 1);
       // loginFromFragment(txtLoginEmail.getText().toString(), txtLoginPassword.getText().toString());


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
                Crouton.makeText(getActivity(), message,Style.ALERT).show();
            }
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
