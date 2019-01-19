package com.match.maker.preferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.match.maker.utils.WSConstants;

/**
 * Created by ravindra on 16,January,2019
 */
public class CommonPreferences {

    private static CommonPreferences instance;
    private Context mContext;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private boolean isFirstTimeLogin = true;

    public CommonPreferences() {
    }

    public static CommonPreferences getInstance() {
        if (instance == null) {
            synchronized (CommonPreferences.class) {
                if (instance == null) {
                    instance = new CommonPreferences();
                }
            }
        }

        return instance;
    }

    public void load(Context context) {
        this.mContext = context;
        pref = mContext.getSharedPreferences(WSConstants.Preferences.PREF_NAME, Activity.MODE_PRIVATE);
        editor = pref.edit();

        isFirstTimeLogin = pref.getBoolean(WSConstants.Preferences.PROPERTY_FIRST_TIME, true);



    }


    //first time login status
    public boolean isFirstTimeLogin() {
        return this.isFirstTimeLogin;
    }

    public void setFirstTimeLogin(boolean isFirstTimeLogin) {
        editor.putBoolean(WSConstants.Preferences.PROPERTY_FIRST_TIME, isFirstTimeLogin);
        editor.commit();
        this.isFirstTimeLogin = isFirstTimeLogin;
    }


}
