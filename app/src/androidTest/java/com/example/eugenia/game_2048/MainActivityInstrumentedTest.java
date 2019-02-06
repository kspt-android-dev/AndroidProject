package com.example.eugenia.game_2048;


import android.os.RemoteException;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import static androidx.test.espresso.Espresso.onView;


import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;

@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);
    @Test
    public void isItemShowed() {
        onView(withId(R.id.welcomeSign)).check(matches(isDisplayed()));
        onView(withId(R.id.startButton)).check(matches(isDisplayed()));
        onView(withId(R.id.exitButton)).check(matches(isDisplayed()));
        onView(withId(R.id.rulesButton)).check(matches(isDisplayed()));

    }
    @Test
    public void  rotation() throws RemoteException {
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).setOrientationLeft();
        Espresso.onView(ViewMatchers.withId(R.id.exitButton))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.pressBackUnconditionally();

        Assert.assertTrue(activityRule.getActivity().isFinishing());
    }
}
