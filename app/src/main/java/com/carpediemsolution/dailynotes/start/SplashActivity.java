package com.carpediemsolution.dailynotes.start;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.carpediemsolution.dailynotes.R;
import com.carpediemsolution.dailynotes.itemslist.MainActivity;

import java.lang.ref.WeakReference;


public class SplashActivity extends AppCompatActivity {

    private static final int START_APP = 0;
    private final StartAppHandler handler = new StartAppHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler.postDelayed(() -> handler.sendEmptyMessage(START_APP), 1000);
    }

    private static class StartAppHandler extends Handler {
        private final WeakReference<SplashActivity> activity;

        StartAppHandler(SplashActivity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            SplashActivity activity = this.activity.get();
            if (activity != null) {
                if (msg.what == START_APP) {
                        activity.startActivity(MainActivity.newInstance(activity));
                    activity.finish();
                }
            }
        }
    }
}
