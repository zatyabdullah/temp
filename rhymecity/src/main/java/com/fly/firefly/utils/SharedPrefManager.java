package com.fly.firefly.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class SharedPrefManager {
    private static final String PREF_NAME = "AndroidHivePref";
    public static final String SIGNATURE = "SIGNATURE";
    public static final String SELECTED = "SELECTED";
   // public static final String SELECTED = "SELECTED";



    int PRIVATE_MODE = 0;
    Context _context;
    private SharedPreferences _sharedPrefs;
    private Editor _prefsEditor;

    public SharedPrefManager(Context context) {
        this._context = context;
        _sharedPrefs = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        _prefsEditor = _sharedPrefs.edit();
    }

    /*Return Signature Value*/
    public HashMap<String, String> getSignatureFromLocalStorage() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(SIGNATURE, _sharedPrefs.getString(SIGNATURE, null));
        return init;
    }

    /*Return Selected Value*/
    public HashMap<String, String> getSelectedPopupSelection() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(SELECTED, _sharedPrefs.getString(SELECTED, null));
        return init;
    }

    /*Set Signature Value*/
    public void setSignatureToLocalStorage(String signature) {
        _prefsEditor.putString(SIGNATURE, signature);
        _prefsEditor.apply();
    }


    /*Set Airport Value*/
    public void setSelectedPopupSelection(String signature) {
        _prefsEditor.putString(SELECTED, signature);
        _prefsEditor.apply();
    }

    /*Clear Signature Value*/
    public void clearSignatureFromLocalStorage() {
        // Clearing Siganture
        _sharedPrefs.edit().remove(SIGNATURE).apply();
    }

    /*Clear Selected Value*/
    public void clearSelectedPopupSelection() {
        // Clearing Selected
        _sharedPrefs.edit().remove(SELECTED).apply();
    }

}