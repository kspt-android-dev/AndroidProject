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
        //тест проверяет составление корректного и неккоректного слова
        //Если слово составлено корректно до в текстэдит отобразиться само слово иначе вернется
        //Пустая строка и буквы скинутся до исходного состояния
        //Числа это координаты нажатия (Тестировалось на устройстве с экраном 1080x2160 точек
        //Поэтому такие числа
        onView(ViewMatchers.withId(R.id.startGame)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.nextTurn)).perform(ViewActions.click());
        setupLetter();
        onView(ViewMatchers.withId(R.id.nextTurn)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.frameGame)).perform(clickOnBoard(100, 2000));
        onView(ViewMatchers.withId(R.id.frameGame)).perform(clickOnBoard(20, 100));
        onView(ViewMatchers.withId(R.id.frameGame)).perform(clickOnBoard(200, 2000));
        onView(ViewMatchers.withId(R.id.frameGame)).perform(clickOnBoard(520, 590));
        onView(ViewMatchers.withId(R.id.newWord)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.namePlayer)).check(matches(withText("")));
        onView(ViewMatchers.withId(R.id.frameGame)).perform(clickOnBoard(100, 2000));
        onView(ViewMatchers.withId(R.id.frameGame)).perform(clickOnBoard(450, 590));
        onView(ViewMatchers.withId(R.id.frameGame)).perform(clickOnBoard(210, 2000));
        onView(ViewMatchers.withId(R.id.frameGame)).perform(clickOnBoard(520, 590));
        onView(ViewMatchers.withId(R.id.frameGame)).perform(clickOnBoard(320, 2000));
        onView(ViewMatchers.withId(R.id.frameGame)).perform(clickOnBoard(590, 590));
        onView(ViewMatchers.withId(R.id.frameGame)).perform(clickOnBoard(430, 2000));
        onView(ViewMatchers.withId(R.id.frameGame)).perform(clickOnBoard(660, 590));
        onView(ViewMatchers.withId(R.id.newWord)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.namePlayer)).check(matches(withText("БАЙК")));

    }
    @Test
    @SmallTest
    public void  nextTurnTest(){
        //Тест проверка перехода хода
        onView(ViewMatchers.withId(R.id.startGame)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.namePlayer)).check(matches(withText("ГОША\n0")));
        onView(ViewMatchers.withId(R.id.nextTurn)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.namePlayer)).check(matches(withText("ДИМА\n0")));
    }

    @Test
    @SmallTest
    public void  recycleTest(){
        //Тест проверка удаления буквы выбираем первую букву и сбрасываем
        onView(ViewMatchers.withId(R.id.startGame)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.namePlayer)).check(matches(withText("ГОША\n0")));
        onView(ViewMatchers.withId(R.id.nextTurn)).perform(ViewActions.click());
        setupLetter();
        //char oldLetter = testGameActivity.gameField.getGames().getListCellLetter()
        onView(ViewMatchers.withId(R.id.nextTurn)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.frameGame)).perform(clickOnBoard(100, 2000));
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
    private ViewAction clickOnBoard(final int x, final int y ){
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
