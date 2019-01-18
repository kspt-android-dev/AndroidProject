package com.example.maha.bulls;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)
public class MainActivityTest{
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainScreen(){
        onView(withId(R.id.title)).check(matches(isDisplayed()));
        onView(withId(R.id.imageScore)).check(matches(isDisplayed()));
        onView(withId(R.id.imageSettings)).check(matches(isDisplayed()));
        onView(withId(R.id.imageStart)).check(matches(isDisplayed()));
    }

    @Test
    public void positions(){
        onView(withId(R.id.imageStart)).perform(click());;
        onView(withId(R.id.textView)).check(matches(withText("")));
        onView(withId(R.id.textView2)).check(matches(withText("")));
        onView(withId(R.id.textView3)).check(matches(withText("")));

        onView(withId(R.id.button0)).check(matches(isDisplayed()));
        onView(withId(R.id.button1)).check(matches(isDisplayed()));
        onView(withId(R.id.button2)).check(matches(isDisplayed()));
        onView(withId(R.id.button3)).check(matches(isDisplayed()));
        onView(withId(R.id.button4)).check(matches(isDisplayed()));
        onView(withId(R.id.button5)).check(matches(isDisplayed()));
        onView(withId(R.id.button6)).check(matches(isDisplayed()));
        onView(withId(R.id.button7)).check(matches(isDisplayed()));
        onView(withId(R.id.button8)).check(matches(isDisplayed()));
        onView(withId(R.id.button9)).check(matches(isDisplayed()));
        onView(withId(R.id.ok)).check(matches(isDisplayed()));
        onView(withId(R.id.del)).check(matches(isDisplayed()));

        onView(withId(R.id.button0)).perform(click());
        onView(withId(R.id.button1)).perform(click());
        onView(withId(R.id.button2)).perform(click());
        onView(withId(R.id.button3)).perform(click());
        onView(withId(R.id.del)).perform(click());
        onView(withId(R.id.del)).perform(click());
        onView(withId(R.id.del)).perform(click());
        onView(withId(R.id.del)).perform(click());
        onView(withId(R.id.button4)).perform(click());
        onView(withId(R.id.button5)).perform(click());
        onView(withId(R.id.button6)).perform(click());
        onView(withId(R.id.button7)).perform(click());
        onView(withId(R.id.del)).perform(click());
        onView(withId(R.id.del)).perform(click());
        onView(withId(R.id.button8)).perform(click());
        onView(withId(R.id.button9)).perform(click());
        onView(withId(R.id.ok)).perform(click());
    }
    @Test
    public void inputNumb(){
        onView(withId(R.id.imageStart)).perform(click());;
        onView(withId(R.id.button0)).perform(click());
        onView(withId(R.id.button1)).perform(click());
        onView(withId(R.id.button2)).perform(click());
        onView(withId(R.id.button3)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("0123")));
        onView(withId(R.id.del)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("012")));
        onView(withId(R.id.button3)).perform(click());
        onView(withId(R.id.ok)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("")));
    }
    @Test
    public void changeScreenOnClick2(){

        onView(withId(R.id.imageScore)).perform(click());
        onView(withId(R.id.textViewRule1)).check(matches(isDisplayed()));
    }
    @Test
    public void changeScreenOnClick3(){
        onView(withId(R.id.imageSettings)).perform(click());
    }
}