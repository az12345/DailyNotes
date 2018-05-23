package com.carpediemsolution.dailynotes.utils.view;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.carpediemsolution.dailynotes.R;
import com.carpediemsolution.dailynotes.taskslist.MainActivity;

/**
 * Created by Юлия on 28.05.2017.
 */

public class UserSettingActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(UserSettingActivity.this, MainActivity.class);
        startActivity(intent);
    }
}