package com.fly.firefly.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;


public class BaseFragment extends Fragment {

	protected com.fly.firefly.base.AQuery aq;
	protected SharedPreferences pref;

	/*Return month in alphabet*/
	public String getMonthAlphabet(int month) {
		return new DateFormatSymbols().getShortMonths()[month];
	}

	/*Get All Country From OS*/
	public static ArrayList<String> getCountry()
	{
		Locale[] locales = Locale.getAvailableLocales();
		ArrayList<String> countries = new ArrayList<String>();
		for (Locale locale : locales) {
			String country = locale.getDisplayCountry();
			String countryCode = locale.getCountry();

			if (country.trim().length()>0 && !countries.contains(country)) {
				countries.add(country+"-"+countryCode);
			}
		}
		Collections.sort(countries);
		return countries;
	}


	public static void initiateLoading(Activity act){

		ProgressDialog mProgressDialog = new ProgressDialog(act);
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setCancelable(true);
		mProgressDialog.setMessage("Loading...");

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		//lp.copyFrom(mProgressDialog.getWindow().getAttributes());
		lp.width = 50;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		mProgressDialog.getWindow().setAttributes(lp);

		mProgressDialog.show();


	}

	/*public static void showConnectionError(String test, Activity activity)
	{
        if(activity != null) {
            try {
                TextView txtUTC = (TextView) activity.findViewById(R.id.txtUTC);
                txtUTC.setText(test);

                FrameLayout mainFrame = (FrameLayout) activity.findViewById(R.id.utc_container);
                mainFrame.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}

	public static void baseInitiateLoading(Activity activity)
	{
        Log.e("Initiate Loading","TRUE");
		try
		{
			final FrameLayout mainFrame = (FrameLayout) activity.findViewById(R.id.container);
			mainFrame.setVisibility(View.VISIBLE);

			RelativeLayout mainFrameRelative = (RelativeLayout) activity.findViewById(R.id.mainFrameRelative);
			mainFrameRelative.setVisibility(View.VISIBLE);
			mainFrame.bringChildToFront(mainFrameRelative);
			mainFrame.invalidate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void baseRemoveLoading(Activity activity)
	{
		try
		{
			RelativeLayout mainFrameRelative = (RelativeLayout) activity.findViewById(R.id.mainFrameRelative);
			mainFrameRelative.setVisibility(View.GONE);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}*/

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		aq = new com.fly.firefly.base.AQuery(getActivity());
		pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
	}

	/*public void showUTCError(String msg)
	{
		Activity activity = getActivity();
		if (activity instanceof MainFragmentActivity)
		{
			MainFragmentActivity myactivity = (MainFragmentActivity) activity;
			myactivity.unableToConnectToServer(msg);
		}
	}*/

	public boolean isNetworkAvailable(Activity activity)
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

}
