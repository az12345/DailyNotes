package com.carpediemsolution.dailynotes;

import android.test.ActivityInstrumentationTestCase2;

import com.carpediemsolution.dailynotes.itemslist.MainActivity;


/**
 * Created by Юлия on 31.05.2017.
 */
public class ActivityInstrumentationTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public ActivityInstrumentationTest() {
        super(MainActivity.class);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
