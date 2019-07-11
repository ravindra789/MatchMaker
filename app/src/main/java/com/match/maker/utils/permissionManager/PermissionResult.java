package com.match.maker.utils.permissionManager;

/**
 * Created by RavindraP on 09,July,2019
 */
public interface PermissionResult {

    void permissionGranted();

    void permissionDenied();

    void permissionForeverDenied();

}
