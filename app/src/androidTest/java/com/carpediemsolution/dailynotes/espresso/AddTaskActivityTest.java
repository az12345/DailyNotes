package com.carpediemsolution.dailynotes.espresso;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.carpediemsolution.dailynotes.R;
import com.carpediemsolution.dailynotes.app.App;
import com.carpediemsolution.dailynotes.newtask.AddTaskActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
public class AddTaskActivityTest {

    @Rule
    public ActivityTestRule<AddTaskActivity> addTaskActivityActivityTestRule =
            new ActivityTestRule<>(AddTaskActivity.class);

    /**
     * проверяет, вызывается ли Toast если строка item пустая */
    @Test
    public void testEmptyString() {

        onView(ViewMatchers.withId(R.id.new_task)).check(matches(withText("")));
        onView(withId(R.id.fab_write)).perform(click());

        onView(withText(App.getAppContext().getResources().getString(R.string.insert_task))
        ).inRoot(withDecorView(not(is(addTaskActivityActivityTestRule.getActivity().getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

}
