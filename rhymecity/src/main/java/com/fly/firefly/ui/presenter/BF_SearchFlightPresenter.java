package com.fly.firefly.ui.presenter;

        import com.fly.firefly.rhymes.RhymesRequestedEvent;
        import com.squareup.otto.Bus;

public class BF_SearchFlightPresenter {

    public interface SearchFlightView {

    }

    private final SearchFlightView view;
    private final Bus bus;

    public BF_SearchFlightPresenter(SearchFlightView view, Bus bus) {
        this.view = view;
        this.bus = bus;
    }

    public void onResume() {
        bus.register(this);
    }

    public void onPause() {
        bus.unregister(this);
    }

    public void onRhymesForWordRequested(String word) {
        bus.post(new RhymesRequestedEvent(word));
    }
}
