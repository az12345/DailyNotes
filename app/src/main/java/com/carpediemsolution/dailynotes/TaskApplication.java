package com.carpediemsolution.dailynotes;

import android.app.Application;
import android.content.Context;

import com.carpediemsolution.dailynotes.dao.HelperFactory;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Юлия on 30.05.2017.
 */

public class TaskApplication extends Application {
    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        HelperFactory.setHelper(getApplicationContext());
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher = LeakCanary.install(this);
    }

    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();
        super.onTerminate();
    }

    public static RefWatcher getRefWatcher(Context context) {
        TaskApplication application = (TaskApplication) context.getApplicationContext();
        return application.refWatcher;
    }
}
