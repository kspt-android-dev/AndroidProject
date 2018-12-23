package ru.spbstu.kspt.myhorsemove;

import android.content.pm.ActivityInfo;
import android.view.View;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.CoordinatesProvider;
import androidx.test.espresso.action.GeneralClickAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Tap;
import androidx.test.rule.ActivityTestRule;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class FieldActivityTest {

    private FieldActivity testFieldActivity;

    @Rule
    public ActivityTestRule<FieldActivity> myFieldTest = new ActivityTestRule<>(FieldActivity.class);

    @Before
    public void setUp() throws Exception {
        testFieldActivity = myFieldTest.getActivity();
    }

    @Test
    public void testButtonAndText() throws Exception {
        onView(withId(R.id.textView1)).check(matches(isDisplayed()));
        onView(withId(R.id.textView2)).check(matches(isDisplayed()));
        onView(withId(R.id.button2)).check(matches(isEnabled()));
        onView(withId(R.id.button2)).check(matches(isClickable()));
    }

    @Test
    public void testGameWin() throws Exception {
        onView(withId(R.id.FV2)).perform(clickBoard(400, 470));
        onView(withId(R.id.FV2)).perform(clickBoard(470, 610));
        onView(withId(R.id.FV2)).perform(clickBoard(610, 540));
        onView(withId(R.id.FV2)).perform(clickBoard(540, 400));
        onView(withId(R.id.FV2)).perform(clickBoard(610, 260));
        onView(withId(R.id.FV2)).perform(clickBoard(540, 120));
        onView(withId(R.id.FV2)).perform(clickBoard(400, 190));
        onView(withId(R.id.FV2)).perform(clickBoard(470, 330));
        onView(withId(R.id.FV2)).perform(clickBoard(330, 260));
        onView(withId(R.id.FV2)).perform(clickBoard(260, 120));
        onView(withId(R.id.FV2)).perform(clickBoard(120, 190));
        onView(withId(R.id.FV2)).perform(clickBoard(190, 330));
        onView(withId(R.id.FV2)).perform(clickBoard(120, 470));
        onView(withId(R.id.FV2)).perform(clickBoard(190, 610));
        onView(withId(R.id.FV2)).perform(clickBoard(330, 540));
        onView(withId(R.id.FV2)).perform(clickBoard(260, 400));
        onView(withId(R.id.FV2)).perform(clickBoard(190, 540));
        onView(withId(R.id.FV2)).perform(clickBoard(330, 610));
        onView(withId(R.id.FV2)).perform(clickBoard(260, 470));
        onView(withId(R.id.FV2)).perform(clickBoard(120, 400));
        onView(withId(R.id.FV2)).perform(clickBoard(190, 260));
        onView(withId(R.id.FV2)).perform(clickBoard(120, 120));
        onView(withId(R.id.FV2)).perform(clickBoard(260, 190));
        onView(withId(R.id.FV2)).perform(clickBoard(330, 330));
        onView(withId(R.id.FV2)).perform(clickBoard(470, 260));
        onView(withId(R.id.FV2)).perform(clickBoard(400, 120));
        onView(withId(R.id.FV2)).perform(clickBoard(540, 190));
        onView(withId(R.id.FV2)).perform(clickBoard(610, 330));
        onView(withId(R.id.FV2)).perform(clickBoard(540, 470));
        onView(withId(R.id.FV2)).perform(clickBoard(610, 610));
        onView(withId(R.id.FV2)).perform(clickBoard(470, 540));
        onView(withId(R.id.FV2)).perform(clickBoard(400, 400));
        onView(withId(R.id.FV2)).perform(clickBoard(260, 330));
        onView(withId(R.id.FV2)).perform(clickBoard(330, 190));
        onView(withId(R.id.FV2)).perform(clickBoard(190, 120));
        onView(withId(R.id.FV2)).perform(clickBoard(120, 260));
        onView(withId(R.id.FV2)).perform(clickBoard(190, 400));
        onView(withId(R.id.FV2)).perform(clickBoard(120, 540));
        onView(withId(R.id.FV2)).perform(clickBoard(260, 610));
        onView(withId(R.id.FV2)).perform(clickBoard(330, 470));
        onView(withId(R.id.FV2)).perform(clickBoard(470, 400));
        onView(withId(R.id.FV2)).perform(clickBoard(400, 540));
        onView(withId(R.id.FV2)).perform(clickBoard(540, 610));
        onView(withId(R.id.FV2)).perform(clickBoard(610, 470));
        onView(withId(R.id.FV2)).perform(clickBoard(540, 330));
        onView(withId(R.id.FV2)).perform(clickBoard(610, 190));
        onView(withId(R.id.FV2)).perform(clickBoard(470, 120));
        onView(withId(R.id.FV2)).perform(clickBoard(400, 260));
        onView(withId(R.id.FV2)).perform(clickBoard(330, 400));
        onView(withId(R.id.FV2)).perform(clickBoard(260, 540));
        onView(withId(R.id.FV2)).perform(clickBoard(120, 610));
        onView(withId(R.id.FV2)).perform(clickBoard(190, 470));
        onView(withId(R.id.FV2)).perform(clickBoard(120, 330));
        onView(withId(R.id.FV2)).perform(clickBoard(190, 190));
        onView(withId(R.id.FV2)).perform(clickBoard(330, 120));
        onView(withId(R.id.FV2)).perform(clickBoard(260, 260));
        onView(withId(R.id.FV2)).perform(clickBoard(400, 330));
        onView(withId(R.id.FV2)).perform(clickBoard(470, 190));
        onView(withId(R.id.FV2)).perform(clickBoard(610, 120));
        onView(withId(R.id.FV2)).perform(clickBoard(540, 260));
        onView(withId(R.id.FV2)).perform(clickBoard(610, 400));
        onView(withId(R.id.FV2)).perform(clickBoard(470, 470));
        onView(withId(R.id.FV2)).perform(clickBoard(400, 610));
        onView(withId(R.id.FV2)).perform(clickBoard(540, 540));

    }

    @Test
    public void testGameLose() throws Exception {
        onView(withId(R.id.FV2)).perform(clickBoard(470, 330));
        onView(withId(R.id.FV2)).perform(clickBoard(330, 260));
        onView(withId(R.id.FV2)).perform(clickBoard(470, 190));
        onView(withId(R.id.FV2)).perform(clickBoard(330, 120));
        onView(withId(R.id.FV2)).perform(clickBoard(400, 260));
        onView(withId(R.id.FV2)).perform(clickBoard(470, 120));
        onView(withId(R.id.FV2)).perform(clickBoard(540, 260));
        onView(withId(R.id.FV2)).perform(clickBoard(610, 120));
    }

    @Test
    public void testGameStart() throws Exception {
        onView(withId(R.id.FV2)).perform(clickBoard(470, 330));
        onView(withId(R.id.FV2)).perform(clickBoard(330, 260));
        onView(withId(R.id.FV2)).perform(clickBoard(470, 190));
        onView(withId(R.id.button2)).perform(click());
        onView(withId(R.id.FV2)).perform(clickBoard(610, 120));
        onView(withId(R.id.FV2)).perform(clickBoard(540, 260));
    }

    @Test
    public void testGameOrientation() throws Exception {
        onView(withId(R.id.FV2)).perform(clickBoard(610, 610));
        onView(withId(R.id.FV2)).perform(clickBoard(540, 470));
        onView(withId(R.id.FV2)).perform(clickBoard(610, 330));
        onView(withId(R.id.FV2)).perform(clickBoard(470, 400));
        myFieldTest.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        onView(withId(R.id.FV2)).perform(clickBoard(610, 470));
        onView(withId(R.id.FV2)).perform(clickBoard(470, 540));
        onView(withId(R.id.FV2)).perform(clickBoard(540, 400));
        onView(withId(R.id.FV2)).perform(clickBoard(400, 470));
        myFieldTest.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        onView(withId(R.id.FV2)).perform(clickBoard(540, 540));
        onView(withId(R.id.FV2)).perform(clickBoard(400, 610));
        onView(withId(R.id.FV2)).perform(clickBoard(470, 470));
        onView(withId(R.id.FV2)).perform(clickBoard(540, 610));
        onView(withId(R.id.FV2)).perform(clickBoard(400, 540));
        onView(withId(R.id.FV2)).perform(clickBoard(260, 610));
        onView(withId(R.id.FV2)).perform(clickBoard(330, 470));
        onView(withId(R.id.FV2)).perform(clickBoard(190, 540));
        myFieldTest.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        onView(withId(R.id.FV2)).perform(clickBoard(260, 400));
        onView(withId(R.id.FV2)).perform(clickBoard(330, 540));
        onView(withId(R.id.FV2)).perform(clickBoard(470, 610));
        onView(withId(R.id.FV2)).perform(clickBoard(610, 540));
    }

    //Для тестирования использую готовую функцию:
    private ViewAction clickBoard(final int x, final int y ){
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