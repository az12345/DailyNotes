package com.carpediemsolution.dailynotes;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.carpediemsolution.dailynotes.itemslist.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void startAddTaskActivityAndReturnResultOK() throws Exception {

        // first activity, button click
        onView(withId(R.id.fab_add)).perform(click());

        // second activity, text check
        onView(withId(R.id.new_task)).check(matches(withText("")));
        onView(withId(R.id.fab_write)).perform(click());


        onView(withId(R.id.new_task)).check(matches(withText("text for tests!")));
        onView(withId(R.id.fab_write)).perform(click());

    }
}
