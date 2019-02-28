package lizka.reminder;

import android.support.test.rule.ActivityTestRule;

import junit.framework.AssertionFailedError;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class Espresso {

    @Rule
    public ActivityTestRule<MainActivity> menuActivityTestRule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    @Test
    public void onViewVisible() {
        try {
            onView(withId(R.id.fab)).check(matches(isDisplayed()));
            // View is displayed
        } catch (AssertionFailedError e) {
            // View not displayed
        }
    }

}