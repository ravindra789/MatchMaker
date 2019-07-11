package com.match.maker.utils.permissionManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.match.maker.utils.WSConstants;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by RavindraP on 10,July,2019
 */
public class PermissionUtils {

    private static PermissionUtils instance;
    private Activity mActivity;
    private PermissionResult permissionResult;
    private String permissionsAsk[];


    public static PermissionUtils getInstance() {
        if (instance == null) {
            synchronized (PermissionUtils.class) {
                if (instance == null) {
                    instance = new PermissionUtils();
                }
            }
        }

        return instance;
    }

    public void setActivityContext(Activity activity) {
        mActivity = activity;
    }


    public boolean isSinglePermissionGranted(Context context, String permission) {
        return ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M) || (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED));
    }

    public boolean isMultiplePermissionsGranted(Context context, String permissions[]) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        boolean granted = true;
        for (String permission : permissions) {
            if (!(ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED))
                granted = false;
        }
        return granted;
    }

    private void internalRequestPermission(String[] permissionAsk) {
        String arrayPermissionNotGranted[];
        ArrayList<String> permissionsNotGranted = new ArrayList<>();

        for (int i = 0; i < permissionAsk.length; i++) {
            if (!isSinglePermissionGranted(mActivity, permissionAsk[i])) {
                permissionsNotGranted.add(permissionAsk[i]);
            }
        }

        if (permissionsNotGranted.isEmpty()) {
            if (permissionResult != null)
                permissionResult.permissionGranted();
        } else {
            arrayPermissionNotGranted = new String[permissionsNotGranted.size()];
            arrayPermissionNotGranted = permissionsNotGranted.toArray(arrayPermissionNotGranted);
            ActivityCompat.requestPermissions(mActivity, arrayPermissionNotGranted, WSConstants.AppUtils.KEY_PERMISSION);
        }
    }

    public void checkRequestedPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){

        if (requestCode != WSConstants.AppUtils.KEY_PERMISSION) {
            return;
        }

        List<String> permissionDenied = new LinkedList<>();
        boolean granted = true;
        for (int i = 0; i < grantResults.length; i++) {
            if (!(grantResults[i] == PackageManager.PERMISSION_GRANTED)) {
                granted = false;
                permissionDenied.add(permissions[i]);
            }
        }

        if (permissionResult != null) {
            if (granted) {
                permissionResult.permissionGranted();
            } else {
                for (String s : permissionDenied) {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(mActivity, s)) {
                        permissionResult.permissionForeverDenied();
                        return;
                    }
                }
                permissionResult.permissionDenied();
            }
        }

    }

    public void askCompactSinglePermission(String permission, PermissionResult permissionResult) {
        permissionsAsk = new String[]{permission};
        this.permissionResult = permissionResult;
        internalRequestPermission(permissionsAsk);
    }

    public void askCompactMultiplePermissions(String permissions[], PermissionResult permissionResult) {
        permissionsAsk = permissions;
        this.permissionResult = permissionResult;
        internalRequestPermission(permissionsAsk);
    }

    public void openSettingsApp(Context context) {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }
    
}
