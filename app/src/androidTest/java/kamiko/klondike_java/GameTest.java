package kamiko.klondike_java;

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
public class GameTest {
    @Rule
    public ActivityTestRule<StartActivity> mActivityRule = new ActivityTestRule<>(StartActivity.class);

    @Test
    public void screenStart() {
        onView(withId(R.id.fon)).check(matches(isDisplayed()));
        onView(withId(R.id.newGame)).check(matches(isDisplayed()));
        onView(withId(R.id.rules)).check(matches(isDisplayed()));
        onView(withId(R.id.quit)).check(matches(isDisplayed()));
    }

    @Test
    public void clickRules() {
        onView(withId(R.id.rules)).perform(click());
        onView(withId(R.id.back)).check(matches(isDisplayed()));
        onView(withId(R.id.newGame)).check(matches(isDisplayed()));
        onView(withId(R.id.title)).check(matches(isDisplayed()));
        onView(withId(R.id.text)).check(matches(isDisplayed()));
        onView(withId(R.id.back)).perform(click());
        onView(withId(R.id.rules)).perform(click());
        onView(withId(R.id.newGame)).perform(click());
    }

    @Test
    public void clickNewGameStart() {
        onView(withId(R.id.newGame)).perform(click());
        onView(withId(R.id.newGame)).check(matches(isDisplayed()));
        onView(withId(R.id.steps)).check(matches(isDisplayed()));
        onView(withId(R.id.chronometer)).check(matches(isDisplayed()));
        onView(withId(R.id.counter)).check(matches(isDisplayed()));

        for (int i = 1; i < 65; i++) { // проверяем все наши ImageView с id , присвоенными в коде
            onView(withId(i)).check(matches(isDisplayed()));
        }

        // Для тестирования drag & touch использовались ручные тесты с использованием логирования
    }


}

