package com.fly.firefly.ui.activity.BookingFlight;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import com.fly.firefly.MainFragmentActivity;
import com.fly.firefly.R;
import com.fly.firefly.ui.activity.FragmentContainerActivity;
import com.fly.firefly.ui.fragment.BookingFlight.FlightDetailFragment;

import butterknife.ButterKnife;

//import android.view.WindowManager;

public class FlightDetailActivity extends MainFragmentActivity implements FragmentContainerActivity {

    //implements ToolbarActivity, ProgressIndicatorActivity, FragmentContainerActivity {
    //@InjectView(R.id.main_activity_toolbar) Toolbar toolbar;
    //@InjectView(R.id.main_activity_progress_indicator) ProgressBar progressIndicator;

    //private FragmentManager fragmentManager;
    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        Bundle bundle = getIntent().getExtras();

        fragmentManager.beginTransaction().replace(R.id.main_content, FlightDetailFragment.newInstance(bundle)).commit();
        hideTitle();
    }

   /* private void goToSearchFragment() {
        fragmentManager.beginTransaction()
                .add(R.id.main_activity_fragment_container, SearchFragment.newInstance())
                .commit();
    }*/

    /*@Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }*/

    /*@Override
    public ProgressBar getProgressIndicator() {
        return progressIndicator;
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }*/

    @Override
    public int getFragmentContainerId() {
        return R.id.main_activity_fragment_container;
    }
}
