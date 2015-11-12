package com.fly.firefly.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fly.firefly.FireFlyApplication;
import com.fly.firefly.R;
import com.fly.firefly.api.obj.tryObj;
import com.fly.firefly.ui.activity.FragmentContainerActivity;
import com.fly.firefly.ui.module.SearchModule;
import com.fly.firefly.ui.presenter.SearchPresenter;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.enums.SnackbarType;
import com.nispok.snackbar.listeners.ActionClickListener;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SearchFragment extends Fragment implements SearchPresenter.SearchView {

    @Inject
    SearchPresenter presenter;

    @InjectView(R.id.search_edit_text) EditText searchEditText;
    @InjectView(R.id.search_button) Button searchButton;

    private ProgressBar progressIndicator;
    private int fragmentContainerId;

    public static SearchFragment newInstance() {

        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

        // new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FireFlyApplication.get(getActivity()).createScopedGraph(new SearchModule(this)).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.inject(this, view);

        searchButton.setEnabled(false);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchButton.setEnabled(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //progressIndicator = ((ProgressIndicatorActivity) getActivity()).getProgressIndicator();
        fragmentContainerId = ((FragmentContainerActivity) getActivity()).getFragmentContainerId();
        //((ToolbarActivity) getActivity()).getToolbar().setTitle(getString(R.string.app_name));
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
        searchEditText.requestFocus();
        showKeyboard();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
        dismissKeyboard();
    }

    @Override
    public void showLoadingIndicator() {
       // progressIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
       // progressIndicator.setVisibility(View.GONE);
    }

    @Override
    public void showRetrieveRhymesError() {
        showError(getString(R.string.retrieve_rhymes_failed), true);
    }

    @Override
    public void showNoRhymesFoundError(String word) {
        showError(getString(R.string.no_rhymes_found_error, word), false);
    }

    /*Replace Search Fragment with Rhymes Fragment*//*
    @Override
    public void goToRhymesViewWithRhymes(String word, List<String> rhymeList) {
        getActivity().getFragmentManager()
                .beginTransaction()
                .replace(fragmentContainerId, RhymesFragment.newInstance(word, rhymeList))
                .addToBackStack(null)
                .commit();
    }*/

    /*Replace Search Fragment with Rhymes Fragment*/
    @Override
    public void displayUserInfo(tryObj obj) {
        getActivity().getFragmentManager()
                .beginTransaction()
                .replace(fragmentContainerId, RhymesFragment.newInstance(obj))
                .addToBackStack(null)
                .commit();
    }

    private void performSearch() {
        presenter.onRhymesForWordRequested(searchEditText.getText().toString());
    }

    private void dismissKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
    }

    private void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    private void showError(String message, boolean showRetry) {
        Snackbar snackbar = Snackbar.with(getActivity())
                .type(SnackbarType.MULTI_LINE)
                .duration(Snackbar.SnackbarDuration.LENGTH_LONG)
                .text(message);

        if (showRetry) {
            snackbar.actionColor(getResources().getColor(R.color.bright_green))
                    .actionLabel(getString(R.string.retry_caps))
                    .actionListener(new ActionClickListener() {
                        @Override
                        public void onActionClicked(Snackbar snackbar) {
                            presenter.onRhymesForWordRequested(searchEditText.getText().toString());
                        }
                    });
        }

        SnackbarManager.show(snackbar);
    }
}
