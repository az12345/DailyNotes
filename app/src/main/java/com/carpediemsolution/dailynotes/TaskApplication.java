package com.carpediemsolution.dailynotes;

import android.app.Application;
import com.carpediemsolution.dailynotes.dao.HelperFactory;

/**
 * Created by Юлия on 30.05.2017.
 */

public class TaskApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HelperFactory.setHelper(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();
        super.onTerminate();
    }
}
