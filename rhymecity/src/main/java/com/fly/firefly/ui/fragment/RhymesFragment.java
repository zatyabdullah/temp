package com.fly.firefly.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fly.firefly.R;
import com.fly.firefly.api.obj.tryObj;
import com.google.gson.Gson;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RhymesFragment extends Fragment {

    private static String WORD_KEY = "word";
    private static String RHYME_LIST_KEY = "rhyme_list";

    @InjectView(R.id.rhymes_list_view) ListView listView;

    private String word;
    private tryObj obj;
    private String jsonMyObject;
   /* public static RhymesFragment newInstance(String word, List<String> rhymeList) {
        RhymesFragment fragment = new RhymesFragment();

        Bundle args = new Bundle();
        args.putString(WORD_KEY, word);
        args.putSerializable(RHYME_LIST_KEY, (Serializable) rhymeList);
        fragment.setArguments(args);

        return fragment;
    }*/

    public static RhymesFragment newInstance(tryObj obj) {
        RhymesFragment fragment = new RhymesFragment();

        Bundle args = new Bundle();
        //args.putString(WORD_KEY, word);
        args.putString(RHYME_LIST_KEY, new Gson().toJson(obj));
        //args.putSerializable(RHYME_LIST_KEY, (Serializable) obj);
        //args.putParcelable(RHYME_LIST_KEY, (Parcelable) obj);
        fragment.setArguments(args);

        return fragment;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Bundle extras = getArguments();
        if (extras != null) {
            jsonMyObject = extras.getString(RHYME_LIST_KEY);
        }
        obj = new Gson().fromJson(jsonMyObject, tryObj.class);
        //obj = (tryObj) getArguments().getSerializable(RHYME_LIST_KEY);
        //obj = (tryObj) getArguments().getParcelable(RHYME_LIST_KEY);
        //obj = (tryObj) getArguments().getParcelable(RHYME_LIST_KEY);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rhymes, container, false);
        ButterKnife.inject(this, view);
        //Log.e("GET LOGIN ON RHYMES FRAGMENT",obj.getLogin());
        Log.e("GET LOGIN",obj.getLogin());
        //listView.setAdapter(new RhymesAdapter(getActivity(), rhymeList));
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       // ((ToolbarActivity) getActivity()).getToolbar().setTitle(getString(R.string.rhymes_with, word));
    }
}
