package com.example.danila.minerandroid;

import android.support.test.espresso.action.KeyEventAction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withInputType;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {

    @Rule
    public final ActivityTestRule<A_MenuActivity> activityActivityTestRule = new ActivityTestRule<>(A_MenuActivity.class);

    @Test
    public void testUI() {
        onView(withId(R.id.play_button))
                .check(matches(isDisplayed()));
        onView(withId(R.id.records_button))
                .check(matches(isDisplayed()));
        onView(withId(R.id.exit_button))
                .check(matches(isDisplayed()));

        onView(withId(R.id.play_button))
                .perform(click());
        onView(withInputType(KeyEvent.KEYCODE_BACK)).perform(click());


    }
}