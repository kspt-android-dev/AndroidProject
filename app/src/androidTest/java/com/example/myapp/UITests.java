package com.example.myapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.*;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class UITests {

    @Rule
    public final ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainTest(){
        Espresso.onView(ViewMatchers.withId(R.id.play))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.countWinX))
                .check(ViewAssertions.matches(ViewMatchers.withText("0")));

        Espresso.onView(ViewMatchers.withId(R.id.countWinO))
                .check(ViewAssertions.matches(ViewMatchers.withText("0")));

        Espresso.onView(ViewMatchers.withId(R.id.imageView7))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.imageView8))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.frameLayout))
                .perform(ViewActions.click());

        Espresso.pressBackUnconditionally();
        Espresso.pressBackUnconditionally();
        Assert.assertTrue(mainActivityActivityTestRule.getActivity().isFinishing());
    }

}
