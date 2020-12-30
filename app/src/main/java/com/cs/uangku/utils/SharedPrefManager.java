package com.cs.uangku.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    public static final String pref_app = "uangku";
    public static final String pref_name = "name";
    public static final String pref_email = "user@gmail.com";
    public static final String pref_isLogin = "true";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(pref_app, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public String getPrefName(){
        return sp.getString(pref_name, "");
    }

    public String getPrefEmail(){
        return sp.getString(pref_email, "");
    }

    public boolean getPrefIsLogin(){
        return sp.getBoolean(String.valueOf(pref_isLogin), false);
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }
}
