package com.fly.firefly.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fly.firefly.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FlightDetailAdapter extends BaseAdapter {

    private final Context context;
    private final List<String> obj;

    public FlightDetailAdapter(Context context, List<String> obj) {
        this.context = context;
        this.obj = obj;
    }

    @Override
    public int getCount() {
        return obj == null ? 0 : obj.size();
    }

    @Override
    public Object getItem(int position) {
        return obj == null ? null : obj.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder {
        @InjectView(R.id.txtFlightNo) TextView txtFlightNo;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder vh;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.flight_detail_list, parent, false);
            vh = new ViewHolder();
            ButterKnife.inject(vh, view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

       // vh.txtFlightNo.setText(obj.get(position));

        return view;
    }
}
