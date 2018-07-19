package com.carpediemsolution.dailynotes.app;

import android.app.Application;
import android.content.Context;

import com.carpediemsolution.dailynotes.dao.HelperFactory;

/**
 * Created by Юлия on 30.05.2017.
 */

public class App extends Application {

    private static Context appContext;

    public static Context getAppContext() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HelperFactory.setHelper(getApplicationContext());
        appContext = this;
    }

    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();
        super.onTerminate();
    }
}
