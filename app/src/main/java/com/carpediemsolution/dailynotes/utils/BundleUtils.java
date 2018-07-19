package com.carpediemsolution.dailynotes.utils;

import android.app.Activity;
import android.os.Bundle;

public class BundleUtils {

    private BundleUtils() {

    }

    public static String getBundleString(Activity activity, String extras) {
        Bundle b = activity.getIntent().getExtras();
        if (b != null) {
            return activity.getIntent().getStringExtra(extras);
        } else return "";
    }

    public static Integer getBundleInt(Activity activity, String extras) {
        Bundle b = activity.getIntent().getExtras();
        if (b != null) {
            return activity.getIntent().getIntExtra(extras,0);
        } else return 0;
    }
}
