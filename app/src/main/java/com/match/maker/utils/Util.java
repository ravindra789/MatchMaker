package com.match.maker.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by ravindra on 16,January,2019
 */
public class Util {

    private static Util instance;
    private Context mContext;
    private LoaderDialogFragment loaderDialogFragment;

    public static Util getInstance() {
        if (instance == null) {
            synchronized (Util.class) {
                if (instance == null) {
                    instance = new Util();
                }
            }
        }
        return instance;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void showLoadingDialog(FragmentActivity fragmentActivity) {
        FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
        Fragment prev = fragmentActivity.getSupportFragmentManager().findFragmentByTag("loaderDialogFragment");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        loaderDialogFragment = new LoaderDialogFragment();
        loaderDialogFragment.show(fragmentActivity.getSupportFragmentManager(), "loaderDialogFragment");
    }

    public void dismissLoadingDialog() {
        if (loaderDialogFragment != null) {
            loaderDialogFragment.dismissAllowingStateLoss();
        }

    }

}
