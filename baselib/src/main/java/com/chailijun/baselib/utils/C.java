package com.chailijun.baselib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class C {
    private static Context sContext;

    public static void setContext(Context c) {
        sContext = c;
    }

    public static Context get() {
        return sContext;
    }

    public static String getPreference(String name, String defValue) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(sContext);
        return prefs.getString(name, defValue);
    }

    public static Boolean getPreference(String name, boolean defValue) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(sContext);
        return prefs.getBoolean(name, defValue);
    }

    public static void setPreference(String name, String value) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(sContext);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(name, value);
        editor.commit();
    }

    public static void setPreference(String name, long value) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(sContext);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(name, value);
        editor.commit();
    }

    public static void setPreference(String name, boolean value) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(sContext);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(name, value);
        editor.commit();
    }

    public static Long getPreference(String name, long defValue) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(sContext);
        return prefs.getLong(name, defValue);
    }

    public static void setPreference(String name, int value) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(sContext);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(name, value);
        editor.commit();
    }

    public static int getPreference(String name, int defValue) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(sContext);
        return prefs.getInt(name, defValue);
    }

    public static void removePreference(String name) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(sContext);
        if (prefs.contains(name)) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove(name);
            editor.commit();
        }
    }
}
