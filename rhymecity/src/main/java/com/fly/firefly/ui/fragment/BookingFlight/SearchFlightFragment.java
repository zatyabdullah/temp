package com.fly.firefly.ui.fragment.BookingFlight;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fly.firefly.FireFlyApplication;
import com.fly.firefly.R;
import com.fly.firefly.ui.activity.BookingFlight.FlightDetailActivity;
import com.fly.firefly.ui.activity.FragmentContainerActivity;
import com.fly.firefly.ui.activity.Register.RegisterActivity;
import com.fly.firefly.ui.module.SearchFlightModule;
import com.fly.firefly.ui.presenter.BF_SearchFlightPresenter;
import com.fly.firefly.utils.Utils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SearchFlightFragment extends Fragment implements BF_SearchFlightPresenter.SearchFlightView {

    @Inject
    BF_SearchFlightPresenter presenter;

    @InjectView(R.id.btnReturn) LinearLayout btnReturn;
    @InjectView(R.id.btnOneWay) LinearLayout btnOneWay;
    @InjectView(R.id.returnDateBlock) LinearLayout returnDateBlock;

    @InjectView(R.id.btnAdultIncrease) ImageView btnAdultIncrease;
    @InjectView(R.id.btnAdultDecrease) ImageView btnAdultDecrease;

    @InjectView(R.id.btnChildIncrease) ImageView btnChildIncrease;
    @InjectView(R.id.btnChildDecrease) ImageView btnChildDecrease;

    @InjectView(R.id.btnInfantIncrease) ImageView btnInfantIncrease;
    @InjectView(R.id.btnInfantDecrease) ImageView btnInfantDecrease;

    @InjectView(R.id.txtAdultTotal) TextView txtAdultTotal;
    @InjectView(R.id.txtChildTotal) TextView txtChildTotal;
    @InjectView(R.id.txtInfantTotal) TextView txtInfantTotal;

    @InjectView(R.id.btnSearchFlight) Button btnSearchFlight;

    private final String RETURN = "RETURN";
    private final String ONEWAY = "ONEWAY";
    private final String ADULT = "ADULT";
    private final String CHILDREN = "CHILDREN";
    private final String INFANT = "INFANT";

    private int totalAdult = 1;
    private int totalChildren = 0;
    private int totalInfant = 0;

    private int fragmentContainerId;
    private boolean blockAdult = false;
    private boolean blockAdultNumber = false;

    private boolean blockChild = false;
    private boolean blockChildNumber = false;

    private boolean blockInfant = false;
    private boolean blockInfantNumber = false;

    public static SearchFlightFragment newInstance() {

        SearchFlightFragment fragment = new SearchFlightFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

        // new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FireFlyApplication.get(getActivity()).createScopedGraph(new SearchFlightModule(this)).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search_flight, container, false);
        ButterKnife.inject(this, view);

        /*Initial*/
        btnOneWay.setBackgroundColor(getResources().getColor(R.color.grey));
        txtAdultTotal.setText("1");
        txtChildTotal.setText("0");
        //txtInfantTotal.setText("0");

        /*Return Button Clicked*/
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchWay(RETURN);
            }
        });

        /*One Way Button Clicked*/
        btnOneWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchWay(ONEWAY);
            }
        });

        /*Add & Remove ADULT Button */
        btnAdultIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!blockAdult){
                   totalAdult++;
                   setPassengerTotal(ADULT);
               }
            }
        });

        btnAdultDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(totalAdult == 1){
                    blockAdultNumber = true;
                }
                if(!blockAdultNumber){
                    totalAdult--;
                    setPassengerTotal(ADULT);
                }
            }
        });

        /* END ADULT*/

        /*Add & Remove CHILDREN Button */
        btnChildIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!blockChild){
                    totalChildren++;
                    setPassengerTotal(CHILDREN);
                }
            }
        });

        btnChildDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(totalChildren == 1){
                    blockChildNumber = true;
                }

                if(!blockChildNumber){
                    totalChildren--;
                    setPassengerTotal(CHILDREN);
                }
            }
        });

        /* END - CHILDREN */

        /*Add & Remove INFANT Button */
        btnInfantIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!blockInfant){
                    totalInfant++;
                    setPassengerTotal(INFANT);
                }
            }
        });

        btnInfantDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(totalInfant == 1){
                    blockInfantNumber = true;
                }

                if(!blockInfantNumber){
                    totalInfant--;
                    setPassengerTotal(INFANT);
                }
            }
        });

        /* END - INFANT */

        btnSearchFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goFlightDetailPage();
                /*FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_activity_fragment_container, BF_FlightDetailFragment.newInstance(), "FLIGHT_DETAIL");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/
            }
        });



        return view;
    }

    /*Public-Inner Func*/
    public void goRegisterPage()
    {
        Intent loginPage = new Intent(getActivity(), RegisterActivity.class);
        getActivity().startActivity(loginPage);
    }

    public void goFlightDetailPage()
    {
        Intent flightDetail = new Intent(getActivity(), FlightDetailActivity.class);
        getActivity().startActivity(flightDetail);
    }

    //Switch Flight Type
    public void switchWay(String way)
    {
        if(way == RETURN) {
            returnDateBlock.setVisibility(View.VISIBLE);
            btnReturn.setBackgroundColor(getResources().getColor(R.color.white));
            btnOneWay.setBackgroundColor(getResources().getColor(R.color.grey));
        }else {
            returnDateBlock.setVisibility(View.GONE);
            btnReturn.setBackgroundColor(getResources().getColor(R.color.grey));
            btnOneWay.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }

    public void setPassengerTotal(String passenger) {

        if (passenger == ADULT) {
            if(totalAdult < 10 && totalAdult > 0) {
                txtAdultTotal.setText(Integer.toString(totalAdult));
                blockAdult = false;
                blockAdultNumber = false;
            }
            else if(totalAdult == 9) {
                Utils.toastNotification(getActivity(), "Limit is 9");
                blockAdult = true;
            }
            else
            {
                blockAdultNumber = true;
            }

        }
        else if(passenger == CHILDREN)
        {
            if(totalChildren < 10 && totalChildren > 0) {
                txtChildTotal.setText(Integer.toString(totalChildren));
                blockChild = false;
                blockChildNumber = false;
            }
            else if(totalChildren == 9) {
                Utils.toastNotification(getActivity(), "Limit is 9");
                blockChild = true;
            }
            else
            {
                blockChildNumber = true;
            }
        }
        else if(passenger == INFANT)
        {
            if(totalInfant < 10 && totalInfant > 0) {
                txtInfantTotal.setText(Integer.toString(totalInfant));
                blockInfant = false;
                blockInfantNumber = false;
            }
            else if(totalInfant == 9) {
                Utils.toastNotification(getActivity(), "Limit is 9");
                blockInfant = true;
            }
            else
            {
                blockInfantNumber = true;
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
        Log.e("RESUME","TRUE");
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }
}
