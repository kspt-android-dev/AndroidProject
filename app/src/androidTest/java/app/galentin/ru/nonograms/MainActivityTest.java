package app.galentin.ru.nonograms;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void screenStart() {
        onView(withId(R.id.playButton)).check(matches(isDisplayed()));
        onView(withId(R.id.rulesButton)).check(matches(isDisplayed()));
        onView(withId(R.id.gameName)).check(matches(isDisplayed()));
    }

    @Test
    public void clickRules() {
        onView(withId(R.id.rulesButton)).perform(click());
        onView(withId(R.id.headline_rules)).check(matches(isDisplayed()));
        onView(withId(R.id.rules_text)).check(matches(isDisplayed()));
    }

    @Test
    public void clickPlay() {
        onView(withId(R.id.playButton)).perform(click());
        onView(withId(R.id.button10x10)).check(matches(isDisplayed()));
        onView(withId(R.id.button15x15)).check(matches(isDisplayed()));
        onView(withId(R.id.button10x10)).perform(click());
        onView(withId(R.id.color1)).check(matches(isDisplayed()));
        onView(withId(R.id.color2)).check(matches(isDisplayed()));
        onView(withId(R.id.blockCell)).check(matches(isDisplayed()));
        onView(withId(R.id.gridV)).check(matches(isDisplayed()));
    }
}