package com.fly.firefly.ui.module;

import com.fly.firefly.AppModule;
import com.fly.firefly.ui.fragment.SearchFragment;
import com.fly.firefly.ui.presenter.SearchPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = SearchFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class SearchModule {

    private final SearchPresenter.SearchView searchView;

    public SearchModule(SearchPresenter.SearchView searchView) {
        this.searchView = searchView;
    }

    @Provides
    @Singleton
    SearchPresenter provideSearchPresenter(Bus bus) {
        return new SearchPresenter(searchView, bus);
    }
}
