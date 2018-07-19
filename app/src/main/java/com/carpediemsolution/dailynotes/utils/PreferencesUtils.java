package com.carpediemsolution.dailynotes.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.carpediemsolution.dailynotes.app.App;

import static android.content.Context.MODE_PRIVATE;

public class PreferencesUtils {

    private static final String USER_PREFS = "user_prefs";
    public static final String SORT = "sort";

    private PreferencesUtils() {

    }

    private static SharedPreferences sPref;

    public static void saveData(String key, String value) {
        if (sPref == null)
            sPref = App.getAppContext()
                    .getSharedPreferences(USER_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(key, value);
        ed.apply();
    }

    public static String getData(String key) {
        if (sPref == null)
            sPref = App.getAppContext()
                    .getSharedPreferences(USER_PREFS, MODE_PRIVATE);
        return sPref.getString(key, "");
    }

    public static void saveBoolData(String key, boolean value) {
        if (sPref == null)
            sPref = App.getAppContext()
                    .getSharedPreferences(USER_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putBoolean(key, value);
        ed.apply();
    }

    public static boolean getBoolData(String key) {
        if (sPref == null)
            sPref = App.getAppContext()
                    .getSharedPreferences(USER_PREFS, MODE_PRIVATE);
        return sPref.getBoolean(key, false);
    }

    public static void deleteAllData() {
        if (sPref == null)
            sPref = App.getAppContext().getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
        sPref.edit().clear().apply();
    }

}
