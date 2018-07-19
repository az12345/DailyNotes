package com.carpediemsolution.dailynotes.espresso;

import android.support.annotation.NonNull;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.carpediemsolution.dailynotes.R;
import com.carpediemsolution.dailynotes.itemslist.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private static final String TEST ="test";

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void searchTextViewTest() throws Exception {
        onView(ViewMatchers.withId(R.id.search))
                .perform(ViewActions.click())
                .perform(ViewActions.swipeDown());
        //todo .check(matches(withText(App.getAppContext().getResources().getString(R.string.app_name))));
    }

    @Test
    public void startAddTaskActivityAndReturnResultOK() throws Exception {

        // main activity button click
        onView(withId(R.id.fab_add)).perform(click());

        // add task activity, text check
        // вводится текст
        onView(withId(R.id.new_task)).perform(typeText(TEST));
        // текст отображается
        onView(withText(TEST)).check(matches(isDisplayed()));
        // переход на главную активити
        onView(withId(R.id.fab_write)).perform(click());

        // текст д отобразиться в первом элементе recyclerview
        onView(withId(R.id.recycler_view))
                .perform(scrollToPosition(0))
                .check(matches(atPositionOnView(0, withText(TEST), R.id.task_item_text_view)));

    }

    public static Matcher<View> atPositionOnView(final int position, final Matcher<View> itemMatcher,
                                                 @NonNull final int targetViewId) {

        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has view id " + itemMatcher + " at position " + position);
            }

            @Override
            public boolean matchesSafely(final RecyclerView recyclerView) {
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                View targetView = viewHolder.itemView.findViewById(targetViewId);
                return itemMatcher.matches(targetView);
            }
        };
    }





   /* base methods:
   activityTestRule.getActivity().getResources()
                    .getString(R.string.item_element_text)
                    + String.valueOf(ITEM_BELOW_THE_FOLD);
     pressBack();

   .perform(clearText(), typeTextIntoFocusedView("Dogan"))
    onView(withHint("hint"));*/
}
