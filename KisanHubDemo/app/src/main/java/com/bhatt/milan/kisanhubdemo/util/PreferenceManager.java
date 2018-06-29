package com.bhatt.milan.kisanhubdemo.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    public static final String PREFERENCE_UK_SAVED = "preference_uk_saved";
    public static final String PREFERENCE_ENGLAND_SAVED = "preference_england_saved";
    public static final String PREFERENCE_WALES_SAVED = "preference_wales_saved";
    public static final String PREFERENCE_SCOTLAND_SAVED = "preference_scotland_saved";
    private Context context;

    public PreferenceManager(Context context) {
        this.context = context;
    }

    private SharedPreferences getSharedPreference() {
        return context.getSharedPreferences("com.bhatt.milan.kisanhubdemo.PREFERENCE",Context.MODE_PRIVATE);
    }

    public void saveStringPreference(String key, String value) {
        getSharedPreference().edit().putString(key,value).apply();
    }

    public String getStringPreference(String key) {
        return getSharedPreference().getString(key,"");
    }

    public void saveBooleanPreference(String key, boolean value) {
        getSharedPreference().edit().putBoolean(key,value).apply();
    }

    public boolean getBooleanPreference(String key) {
        return getSharedPreference().getBoolean(key,false);
    }
}
