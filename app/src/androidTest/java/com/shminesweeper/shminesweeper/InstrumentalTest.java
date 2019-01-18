package com.shminesweeper.shminesweeper;

import android.os.RemoteException;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.PositionAssertions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.uiautomator.UiDevice;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class InstrumentalTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityRotationTest() throws RemoteException {
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).setOrientationNatural();

        checkMainActivityComponents();

        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).setOrientationLeft();

        checkMainActivityComponents();

    }

    private void checkMainActivityComponents() {
        Espresso.onView(ViewMatchers.withId(R.id.reload_button)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(PositionAssertions.isCompletelyLeftOf(ViewMatchers.withId(R.id.settings_button)));
        Espresso.onView(ViewMatchers.withId(R.id.settings_button)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(PositionAssertions.isCompletelyLeftOf(ViewMatchers.withId(R.id.flag_button)))
                .check(PositionAssertions.isCompletelyRightOf(ViewMatchers.withId(R.id.reload_button)));
        Espresso.onView(ViewMatchers.withId(R.id.flag_button)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(PositionAssertions.isCompletelyLeftOf(ViewMatchers.withId(R.id.touch_button)))
                .check(PositionAssertions.isCompletelyRightOf(ViewMatchers.withId(R.id.settings_button)));
        Espresso.onView(ViewMatchers.withId(R.id.touch_button)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(PositionAssertions.isCompletelyLeftOf(ViewMatchers.withId(R.id.question_button)))
                .check(PositionAssertions.isCompletelyRightOf(ViewMatchers.withId(R.id.flag_button)));
        Espresso.onView(ViewMatchers.withId(R.id.question_button)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(PositionAssertions.isCompletelyRightOf(ViewMatchers.withId(R.id.touch_button)));
       Espresso.onView(ViewMatchers.withId(R.id.frameLayout)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(PositionAssertions.isCompletelyAbove(ViewMatchers.withId(R.id.flag_button)));
    }

    @Test
    public void settingsActivityRotationTest() throws RemoteException {
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).setOrientationNatural();

        Espresso.onView(ViewMatchers.withId(R.id.settings_button)).perform(ViewActions.click());

        checkSettingActivityComponents();

        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).setOrientationLeft();

        checkSettingActivityComponents();

    }

    private void checkSettingActivityComponents() {
        Espresso.onView(ViewMatchers.withText("Watch instruction")).check(ViewAssertions.matches(ViewMatchers.isEnabled()));
        Espresso.onView(ViewMatchers.withText("Width")).check(ViewAssertions.matches(ViewMatchers.isEnabled()));
        Espresso.onView(ViewMatchers.withText("Height")).check(ViewAssertions.matches(ViewMatchers.isEnabled()));
        Espresso.onView(ViewMatchers.withText("Number of mines")).check(ViewAssertions.matches(ViewMatchers.isEnabled()));
        Espresso.onView(ViewMatchers.withText("Mode")).check(ViewAssertions.matches(ViewMatchers.isEnabled()));
    }



}
