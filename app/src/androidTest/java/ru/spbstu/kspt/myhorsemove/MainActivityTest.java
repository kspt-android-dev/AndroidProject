package ru.spbstu.kspt.myhorsemove;

import android.content.pm.ActivityInfo;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class MainActivityTest {

    @Rule
    public final ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void onClickStart() {
        onView(withId(R.id.buttonStart)).perform(click());
    }

    @Test
    public void onClickStartLand() {
        mActivityRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        onView(withId(R.id.buttonStart)).perform(click());
    }

    @Test
    public void onClickAbout() {
        onView(withId(R.id.buttonAbout)).perform(click());
    }

    @Test
    public void onClickReset() {
        onView(withId(R.id.buttonReset)).perform(click());
    }

    @Test
    public void onClickExit() {
        onView(withId(R.id.buttonExit)).perform(click());
    }
}