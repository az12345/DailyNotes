package com.carpediemsolution.dailynotes.newnote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.carpediemsolution.dailynotes.base.baseview.BaseActivity;

public class AddNoteActivity extends BaseActivity {

    //todo layout
    public static Intent newInstance(Activity activity){
        return new Intent(activity, AddNoteActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
