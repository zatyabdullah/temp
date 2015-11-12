package com.fly.firefly.ui.fragment.ManageFlight;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fly.firefly.R;
import com.fly.firefly.ui.activity.FragmentContainerActivity;
import com.fly.firefly.ui.presenter.LoginPresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class ManageFlight_ChangeContactFragment extends Fragment {

    @Inject
    LoginPresenter presenter;

    //@InjectView(R.id.search_edit_text) EditText searchEditText;
    //@InjectView(R.id.registerBtn) Button registerButton;


    //private ProgressBar progressIndicator;
    private int fragmentContainerId;

    public static ManageFlight_ChangeContactFragment newInstance() {

        ManageFlight_ChangeContactFragment fragment = new ManageFlight_ChangeContactFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

        // new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FireFlyApplication.get(getActivity()).createScopedGraph(new ManageFlightModule(this)).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.manage_flight_change_contact, container, false);
        ButterKnife.inject(this, view);


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentContainerId = ((FragmentContainerActivity) getActivity()).getFragmentContainerId();
    }

    @Override
    public void onResume() {
        super.onResume();
        // presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        // presenter.onPause();
    }


}
