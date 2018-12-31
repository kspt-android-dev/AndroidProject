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
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

//Тестирование главного меню
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityInstrumentalTest {

    @Rule
    public ActivityTestRule<MainActivity> myMainActivity = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    @SmallTest
    public void testNewGame(){
        onView(withId(R.id.newGame)).perform(ViewActions.click());
    }

    @Test
    @SmallTest
    public void testAbout(){
        onView(withId(R.id.aboutButton)).perform(ViewActions.click());
       onView(ViewMatchers.withId(R.id.textbout)).check(matches(withText("Игра эрудит!!Версия игры: 1.0 Автор: Архиреев Дмитрий P.S ОЧЕНЬ УСТАЛ")));
    }
}
