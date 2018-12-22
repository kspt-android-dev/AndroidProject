package name.mizunotlt.eruditkurs;

import android.view.View;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.CoordinatesProvider;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

//Тестирование активити игры
@RunWith(AndroidJUnit4.class)
@LargeTest
public class EruditKursInstrumentedTest {
    /*
    @Rule
    public ActivityTestRule<GameStart> myStartGame = new ActivityTestRule<>(GameStart.class);
    */
    private Game testGameActivity;
    @Rule
    public  ActivityTestRule<Game> myGameTest = new ActivityTestRule<>(Game.class);

    @Before
    public void setUp() throws Exception {
        testGameActivity = myGameTest.getActivity();
        testGameActivity.player1 = new Player("ГОША");
        testGameActivity.player2 = new Player("ДИМА");
        testGameActivity.gameField.getGames().setPlayers(testGameActivity.player1,testGameActivity.player2);
    }

    @Test
    @SmallTest
    public void testStartGameAndAddWord(){

        onView(ViewMatchers.withId(R.id.startGame)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.nextTurn)).perform(ViewActions.click());
        setupLetter();
        onView(ViewMatchers.withId(R.id.nextTurn)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.frameGame)).perform(clickOnBouard(100, 1900));
        onView(ViewMatchers.withId(R.id.frameGame)).perform(clickOnBouard(20, 100));
        onView(ViewMatchers.withId(R.id.frameGame)).perform(clickOnBouard(200, 1900));
        onView(ViewMatchers.withId(R.id.frameGame)).perform(clickOnBouard(520, 590));
        onView(ViewMatchers.withId(R.id.newWord)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.namePlayer)).check(matches(withText("")));
        onView(ViewMatchers.withId(R.id.frameGame)).perform(clickOnBouard(200, 1900));
        onView(ViewMatchers.withId(R.id.frameGame)).perform(clickOnBouard(520, 590));
        onView(ViewMatchers.withId(R.id.frameGame)).perform(clickOnBouard(100, 1900));
        onView(ViewMatchers.withId(R.id.frameGame)).perform(clickOnBouard(450, 590));
        onView(ViewMatchers.withId(R.id.frameGame)).perform(clickOnBouard(300, 1900));
        onView(ViewMatchers.withId(R.id.frameGame)).perform(clickOnBouard(590, 590));
        onView(ViewMatchers.withId(R.id.frameGame)).perform(clickOnBouard(400, 1900));
        onView(ViewMatchers.withId(R.id.frameGame)).perform(clickOnBouard(660, 590));
        onView(ViewMatchers.withId(R.id.newWord)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.namePlayer)).check(matches(withText("БАЙК")));

    }
    @Test
    @SmallTest
    public void  nextTurnTest(){
        onView(ViewMatchers.withId(R.id.startGame)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.namePlayer)).check(matches(withText("ГОША\n0")));
        onView(ViewMatchers.withId(R.id.nextTurn)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.namePlayer)).check(matches(withText("ДИМА\n0")));
    }

    @Test
    @SmallTest
    public void  recycleTest(){
        onView(ViewMatchers.withId(R.id.startGame)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.namePlayer)).check(matches(withText("ГОША\n0")));
        onView(ViewMatchers.withId(R.id.nextTurn)).perform(ViewActions.click());
        setupLetter();
        onView(ViewMatchers.withId(R.id.nextTurn)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.frameGame)).perform(clickOnBouard(100, 1900));
        onView(ViewMatchers.withId(R.id.recylce)).perform(ViewActions.click());
    }

    //Тест составления и проверки слова
    private void setupLetter(){
        List<Character> testList1 = new ArrayList<>();
        testList1.add('Б');
        testList1.add('А');
        testList1.add('Й');
        testList1.add('К');
        testList1.add('А');
        testList1.add('А');
        testList1.add('Ц');
        testGameActivity.gameField.getGames().getFirstPlayer().setListLetter(testList1);
    }
    //Для тестирования
    private ViewAction clickOnBouard(final int x, final int y ){
        return new GeneralClickAction(
                Tap.SINGLE,
                new CoordinatesProvider() {
                    @Override
                    public float[] calculateCoordinates( View view ){
                        final int[] screenPos = new int[2];
                        view.getLocationOnScreen(screenPos);
                        final float screenX = screenPos[0] + x;
                        final float screenY = screenPos[1] + y;
                        return new float[]{screenX, screenY};
                    }
                },
                Press.FINGER);
    }
}
