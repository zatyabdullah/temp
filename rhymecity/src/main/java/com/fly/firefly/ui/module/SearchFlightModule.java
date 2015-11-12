package com.fly.firefly.ui.module;

import com.fly.firefly.AppModule;
import com.fly.firefly.ui.fragment.BookingFlight.SearchFlightFragment;
import com.fly.firefly.ui.presenter.BF_SearchFlightPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = SearchFlightFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class SearchFlightModule {

    private final BF_SearchFlightPresenter.SearchFlightView searchFlightView;

    public SearchFlightModule(BF_SearchFlightPresenter.SearchFlightView searchFlightView) {
        this.searchFlightView = searchFlightView;
    }

    @Provides
    @Singleton
    BF_SearchFlightPresenter provideSearchFlightPresenter(Bus bus) {
        return new BF_SearchFlightPresenter(searchFlightView, bus);
    }
}
