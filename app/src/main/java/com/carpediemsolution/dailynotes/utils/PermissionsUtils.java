package com.carpediemsolution.dailynotes.utils;

import android.Manifest;
import android.app.Activity;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

public class PermissionsUtils {

    private Activity activity;
    public static final int REQUEST_CODE_STORAGE = 100;

    public PermissionsUtils(Activity activity) {
        this.activity = activity;
    }

    public boolean isExternalStoragePermission() {

        if (isSTORAGEPermissionGot()) {
            return true;
        } else if (Build.VERSION.SDK_INT <= 23) {
            return true;
        } else {
            requestLOCATIONPermission();
            return false;
        }
    }


    private boolean isSTORAGEPermissionGot() {

        return (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED);
    }

    private void requestLOCATIONPermission() {
        String[] permissions = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,};

        ActivityCompat.requestPermissions(activity,
                permissions,
                REQUEST_CODE_STORAGE);
    }

}
