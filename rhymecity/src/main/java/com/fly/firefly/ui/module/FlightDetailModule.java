package com.fly.firefly.ui.module;

import com.fly.firefly.AppModule;
import com.fly.firefly.ui.fragment.BookingFlight.FlightDetailFragment;
import com.fly.firefly.ui.presenter.BF_FlightDetailPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = FlightDetailFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class FlightDetailModule {

    private final BF_FlightDetailPresenter.FlightDetailView flightDetailViewView;

    public FlightDetailModule(BF_FlightDetailPresenter.FlightDetailView flightDetailViewView) {
        this.flightDetailViewView = flightDetailViewView;
    }

    @Provides
    @Singleton
    BF_FlightDetailPresenter provideFlightDetailPresenter(Bus bus) {
        return new BF_FlightDetailPresenter(flightDetailViewView, bus);
    }
}
