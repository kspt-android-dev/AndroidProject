package ru.spbstu.kspt.myhorsemove;

import android.content.pm.ActivityInfo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void onClickStart() throws Exception {
        onView(withId(R.id.buttonStart)).perform(click());
    }

    @Test
    public void onClickStartLand() throws Exception {
        mActivityRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        onView(withId(R.id.buttonStart)).perform(click());
    }

    @Test
    public void onClickAbout() throws Exception {
        onView(withId(R.id.buttonAbout)).perform(click());
    }

    @Test
    public void onClickReset() throws Exception {
        onView(withId(R.id.buttonReset)).perform(click());
    }

    @Test
    public void onClickExit() throws Exception {
        onView(withId(R.id.buttonExit)).perform(click());
    }
}