package polytech.vladislava.sudoku;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withInputType;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class AppTest {

    @Rule
    public final ActivityTestRule<MainMenuActivity> activityActivityTestRule = new ActivityTestRule<>(MainMenuActivity.class);

    @Test
    public void testUI() {
        onView(withId(R.id.textView))
                .check(matches(isDisplayed()));

//        onView(withId(R.id.a_menu_ABOUT))
//                .perform(click());
//
//        сворачивает приложенеи вместо перехода на предыдущую активность
//        onView(withInputType(KeyEvent.KEYCODE_BACK))
//                .perform(click());

        onView(withId(R.id.a_menu_PLAY))
                .perform(click());

        onView(withId(R.id.a_game_sudokuGrid))
                .check(matches(isDisplayed()));

        try {
            for (int i = 0; i < 10; i++) {
                onView(withId(R.id.a_game_sudokuGrid))
                        .perform(click());
            }
        } catch (Exception e) {}

        onView(withId(R.id.a_game_check))
                .check(matches(isDisplayed()))
                .perform(click());

        try {
            onView(withText("Не все поля заполнены!")).inRoot(withDecorView(not(is(activityActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        } catch (Exception e) {
            onView(withText("There are some empty cells!")).inRoot(withDecorView(not(is(activityActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

        }
        onView(withId(R.id.a_game_chrono))
                .check(matches(isDisplayed()));

        onView(withId(R.id.a_game_help))
                .check(matches(isDisplayed()));


        for (int i = 0; i < 58; i++) {
            onView(withId(R.id.a_game_help))
                    .check(matches(isDisplayed()))
                    .perform(click());
        }

        onView(withId(R.id.a_game_check))
                .perform(click());


        try {
            onView(withText("Ready"))
                    .perform(click());
        } catch (Exception e) {
            onView(withText("Ввести"))
                    .perform(click());
        }
    }
}
