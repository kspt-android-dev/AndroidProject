package max.myminesweeper;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.PositionAssertions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class InstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityRotationTest() throws RemoteException {
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).setOrientationNatural();
        Espresso.onView(ViewMatchers.withId(R.id.button_reset)).perform(ViewActions.click());
        checkMainActivityComponents();

        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).setOrientationLeft();
        Espresso.onView(ViewMatchers.withId(R.id.button_reset)).perform(ViewActions.click());
        checkMainActivityComponents();

        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).setOrientationNatural();
    }

    private void checkMainActivityComponents() {
        Espresso.onView(ViewMatchers.withId(R.id.button_reset))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(PositionAssertions.isCompletelyLeftOf(ViewMatchers.withId(R.id.button_settings)));
        Espresso.onView(ViewMatchers.withId(R.id.button_settings))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(PositionAssertions.isCompletelyRightOf(ViewMatchers.withId(R.id.button_reset)))
                .check(PositionAssertions.isCompletelyAbove(ViewMatchers.withId(R.id.frame_layout)));
        Espresso.onView(ViewMatchers.withId(R.id.frame_layout))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .check(PositionAssertions.isCompletelyBelow(ViewMatchers.withId(R.id.button_settings)));
    }

    @Test
    public void settingsActivityRotationTest() throws RemoteException {
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).setOrientationNatural();
        Espresso.onView(ViewMatchers.withId(R.id.button_settings)).perform(ViewActions.click());
        checkSettingActivityComponents();

        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).setOrientationLeft();
        checkSettingActivityComponents();

        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).setOrientationNatural();
    }

    private void checkSettingActivityComponents() {
        Espresso.onView(ViewMatchers.withText("Watch instruction")).check(ViewAssertions.matches(ViewMatchers.isEnabled()));
        Espresso.onView(ViewMatchers.withText("WIDTH")).check(ViewAssertions.matches(ViewMatchers.isEnabled()));
        Espresso.onView(ViewMatchers.withText("HEIGHT")).check(ViewAssertions.matches(ViewMatchers.isEnabled()));
        Espresso.onView(ViewMatchers.withText("NUMBER OF MINES")).check(ViewAssertions.matches(ViewMatchers.isEnabled()));
    }
}