package name.mizunotlt.eruditkurs;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


//Тестирование главного меню
@RunWith(AndroidJUnit4.class)
@LargeTest
public class StartGameActivityInstrumentalTest {

        @Rule
        public ActivityTestRule<GameStart> myStartGameActivity = new ActivityTestRule<>(GameStart.class);

        @Before
        public void setUp() throws Exception {
        }

        @Test
        @SmallTest
        public void testStartGame(){
            onView(ViewMatchers.withId(R.id.firstPlayer)).perform(ViewActions.replaceText("Игрок 1"));
            onView(ViewMatchers.withId(R.id.secondPlayer)).perform(ViewActions.replaceText("Игрок 2"));
            onView(ViewMatchers.withId(R.id.startGame)).perform(ViewActions.click());
            onView(ViewMatchers.withId(R.id.startGame)).perform(ViewActions.click());
            onView(ViewMatchers.withId(R.id.namePlayer)).check(matches(withText("Игрок 1\n0")));
        }

}


