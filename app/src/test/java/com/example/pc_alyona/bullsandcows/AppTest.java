package com.example.pc_alyona.bullsandcows;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AppTest {

    @Rule
    public final ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        chooseTest();
        playTest();
        rulesTest();
        settingsTest();
    }

    private void chooseTest() {
        onView(ViewMatchers.withId(R.id.startButton)).perform(click());
        onView(ViewMatchers.withId(R.id.chooseSeek))
                .check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.playButtonChoose))
                .check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.playButtonChoose))
                .perform(click());
    }

    private void playTest() {
        onView(ViewMatchers.withId(R.id.textInputGame))
                .check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.gameHistory))
                .check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.enterNumber))
                .perform(click());

        onView(ViewMatchers.withId(R.id.gameBack)).perform(click());
        onView(ViewMatchers.withId(R.id.backButtonChoose)).perform(click());

    }

    private void rulesTest() {

        onView(ViewMatchers.withId(R.id.rulesButton)).perform(click());

        onView(ViewMatchers.withId(R.id.rulesText))
                .check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.backRulButton))
                .check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.backRulButton)).perform(click());

    }

    private void settingsTest() {

        onView(ViewMatchers.withId(R.id.settingsButton)).perform(click());

        onView(ViewMatchers.withId(R.id.seekBarClick))
                .check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.seekBarMusic))
                .check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.backSetButton)).perform(click());
    }

}