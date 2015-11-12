package com.fly.firefly.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import com.fly.firefly.R;

public class Touch extends Activity implements OnTouchListener {
    private static final String TAG = "Touch" ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ImageView view = (ImageView) findViewById(R.id.imageView);
        view.setOnTouchListener(this);
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
            // Handle touch events here...
        return true;
    }
}