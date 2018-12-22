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
        onView(ViewMatchers.withId(R.id.newGame)).perform(ViewActions.click());
    }

    @Test
    @SmallTest
    public void testAbout(){
        onView(ViewMatchers.withId(R.id.aboutButton)).perform(ViewActions.click());
    }
}
