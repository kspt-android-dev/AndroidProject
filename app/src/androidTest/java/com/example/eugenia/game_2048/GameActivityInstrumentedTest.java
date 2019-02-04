package com.example.eugenia.game_2048;


import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;

@RunWith(AndroidJUnit4.class)
public class GameActivityInstrumentedTest {
    @Rule
    public ActivityTestRule<GameActivity> activityRule = new ActivityTestRule<>(GameActivity.class);
    @Test
    public void showScore() throws RemoteException {
        Espresso.onView(ViewMatchers.withId(R.id.scoreView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.scoreView))
                .check(ViewAssertions.matches(ViewMatchers.withText("Счет: 0")));
        activityRule.getActivity().score=2;
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).setOrientationLeft();
        Espresso.onView(ViewMatchers.withId(R.id.scoreView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.scoreView))
                .check(ViewAssertions.matches(ViewMatchers.withText("Счет: 2")));

    }
    @Test
    public void testDialog() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                activityRule.getActivity().showEndGame();
            }
        });
        onView(withText(R.string.dialogTitle)).check(matches(isDisplayed()));

}
}
