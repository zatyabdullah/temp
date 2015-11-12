package com.fly.firefly.ui.fragment.BookingFlight;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.fly.firefly.FireFlyApplication;
import com.fly.firefly.R;
import com.fly.firefly.ui.activity.BookingFlight.PersonalDetailActivity;
import com.fly.firefly.ui.activity.FragmentContainerActivity;
import com.fly.firefly.ui.adapter.FlightDetailAdapter;
import com.fly.firefly.ui.module.FlightDetailModule;
import com.fly.firefly.ui.presenter.BF_FlightDetailPresenter;
import com.fly.firefly.utils.ExpandAbleGridView;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FlightDetailFragment extends Fragment implements BF_FlightDetailPresenter.FlightDetailView {

    @Inject BF_FlightDetailPresenter presenter;
    @InjectView(R.id.btnPersonalDetail)Button btnPersonalDetail;
    @InjectView(R.id.flightDeparture)ExpandAbleGridView flightDeparture;
    @InjectView(R.id.flightArrival)ExpandAbleGridView flightArrival;
    //@InjectView(R.id.txtPort)TextView txtPort;

    private int fragmentContainerId;
    private FlightDetailAdapter xx1,xx2;

    public static FlightDetailFragment newInstance() {

        FlightDetailFragment fragment = new FlightDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FireFlyApplication.get(getActivity()).createScopedGraph(new FlightDetailModule(this)).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.flight_detail, container, false);
        ButterKnife.inject(this, view);

        List<String> obj1 = Arrays.asList( "sup2", "sup3","sup1", "sup2", "sup3","sup1");
        List<String> obj2 = Arrays.asList("sup2", "sup3", "sup1", "sup2", "sup3", "sup1");

        xx1 = new FlightDetailAdapter(getActivity(), obj1);
        xx2 = new FlightDetailAdapter(getActivity(), obj2);

        flightDeparture.setAdapter(xx1);
        flightArrival.setAdapter(xx2);

        btnPersonalDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goPersonalDetail();

            }
        });
        return view;
    }

    /*Inner Func*/
    public void goPersonalDetail()
    {
        Intent loginPage = new Intent(getActivity(), PersonalDetailActivity.class);
        getActivity().startActivity(loginPage);
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
}
