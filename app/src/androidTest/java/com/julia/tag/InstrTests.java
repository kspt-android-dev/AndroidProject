package com.julia.tag;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class InstrTests {

    private static final String TEST_NAME = "TEST NAME";

    @Rule
    public final ActivityTestRule<MenuActivity> mActivityTestRule = new ActivityTestRule<>(MenuActivity.class);

    @Test
    public void menuActivityTest() {

        onView(withId(R.id.activity_menu_title))
                .check(matches(isDisplayed()))
                .check(matches(withText(
                        mActivityTestRule.getActivity().getResources().getString(R.string.title_game)
                )));

        onView(withId(R.id.activity_menu_play))
                .check(matches(isDisplayed()))
                .check(matches(withText(
                        mActivityTestRule.getActivity().getResources().getString(R.string.menu_btn_play)
                )));

        onView(withId(R.id.activity_menu_records))
                .check(matches(isDisplayed()))
                .check(matches(withText(
                        mActivityTestRule.getActivity().getResources().getString(R.string.menu_btn_records)
                )));

        onView(withId(R.id.activity_menu_exit))
                .check(matches(isDisplayed()))
                .check(matches(withText(
                        mActivityTestRule.getActivity().getResources().getString(R.string.menu_btn_exit)
                )))
                .perform(click());

        Assert.assertTrue(mActivityTestRule.getActivity().isFinishing());
    }

    @Test
    public void recordsActivityTest() {

        onView(withId(R.id.activity_menu_records))
                .perform(click());

        onView(withId(R.id.activity_records_title))
                .check(matches(isDisplayed()))
                .check(matches(withText(
                        mActivityTestRule.getActivity().getResources().getString(R.string.title_records)
                )));

        onView(withId(R.id.activity_records_btn_menu))
                .check(matches(isDisplayed()));

        onView(withId(R.id.activity_records_btn_delete_all))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withText(
                mActivityTestRule.getActivity().getResources().getString(R.string.records_dialog_delete_message)
        )).check(matches(isDisplayed()));
        onView(withText(
                mActivityTestRule.getActivity().getResources().getString(R.string.dialog_no)
        )).check(matches(isDisplayed()));
        onView(withText(
                mActivityTestRule.getActivity().getResources().getString(R.string.dialog_yes)
        )).check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.activity_records_btn_menu))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.activity_menu_exit))
                .check(matches(isDisplayed()))
                .check(matches(withText(
                        mActivityTestRule.getActivity().getResources().getString(R.string.menu_btn_exit)
                )))
                .perform(click());

        Assert.assertTrue(mActivityTestRule.getActivity().isFinishing());
    }

    @Test
    public void gameActivityTest() {

        clearRecordsBoard();

        onView(withId(R.id.activity_menu_play))
                .check(matches(isDisplayed()))
                .check(matches(withText(
                        mActivityTestRule.getActivity().getResources().getString(R.string.menu_btn_play)
                )))
                .perform(click());

        onView(withId(R.id.activity_game_btn_menu))
                .check(matches(isDisplayed()));

        onView(withId(R.id.activity_game_btn_info))
                .check(matches(isDisplayed()))
                .perform(click());
        onView(withText(
                mActivityTestRule.getActivity().getResources().getString(R.string.game_dialog_info_title)
        )).check(matches(isDisplayed()));
        onView(withText(android.R.string.ok))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.activity_game_btn_restart))
                .check(matches(isDisplayed()))
                .perform(click());
        onView(withText(
                mActivityTestRule.getActivity().getResources().getString(R.string.dialog_restart_message)
        )).check(matches(isDisplayed()));
        onView(withText(
                mActivityTestRule.getActivity().getResources().getString(R.string.dialog_no)
        )).check(matches(isDisplayed()));
        onView(withText(
                mActivityTestRule.getActivity().getResources().getString(R.string.dialog_yes)
        )).check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.activity_game_counter))
                .check(matches(isDisplayed()))
                .check(matches(withText(String.valueOf(0))))
                .perform(click());
        onView(withId(R.id.activity_game_btn_save))
                .check(matches(isDisplayed()))
                .perform(click());
        onView(withText(
                mActivityTestRule.getActivity().getResources().getString(R.string.dialog_win_title)
        )).check(matches(isDisplayed()));
        onView(withText(
                mActivityTestRule.getActivity().getResources().getString(R.string.dialog_win)
        )).check(matches(isDisplayed()));
        onView(withId(R.id.record_dialog_save_edit_text))
                .check(matches(isDisplayed()))
                .check(matches(withText("")))
                .perform(typeText(TEST_NAME))
                .check(matches(withText(TEST_NAME)));
        onView(withText(
                mActivityTestRule.getActivity().getResources().getString(R.string.dialog_save)
        )).check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.activity_game_btn_save))
                .check(matches(isDisplayed()))
                .perform(click());
        onView(withText(
                mActivityTestRule.getActivity().getResources().getString(R.string.game_dialog_save_no_active)
        )).check(matches(isDisplayed()));
        onView(withText(android.R.string.ok))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.activity_game_btn_menu))
                .perform(click());

        checkSavedRecord();

        onView(withId(R.id.activity_menu_exit))
                .check(matches(isDisplayed()))
                .check(matches(withText(
                        mActivityTestRule.getActivity().getResources().getString(R.string.menu_btn_exit)
                )))
                .perform(click());

        Assert.assertTrue(mActivityTestRule.getActivity().isFinishing());
    }

    private void clearRecordsBoard() {
        onView(withId(R.id.activity_menu_records))
                .perform(click());

        onView(withId(R.id.activity_records_btn_delete_all))
                .check(matches(isDisplayed()))
                .perform(click());
        onView(withText(
                mActivityTestRule.getActivity().getResources().getString(R.string.records_dialog_delete_message)
        )).check(matches(isDisplayed()));
        onView(withText(
                mActivityTestRule.getActivity().getResources().getString(R.string.dialog_no)
        )).check(matches(isDisplayed()));
        onView(withText(
                mActivityTestRule.getActivity().getResources().getString(R.string.dialog_yes)
        )).check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.activity_records_btn_menu))
                .perform(click());
    }

    private void checkSavedRecord() {
        onView(withId(R.id.activity_menu_records))
                .perform(click());

        onView(withText(TEST_NAME))
                .check(matches(isDisplayed()));

        onView(withId(R.id.activity_records_btn_menu))
                .perform(click());
    }

    /*
    Правилность перемещения фишек была протестирована вручную. Также функция проверающая можно ли
    сдвинуть фишку была протестирована юнит тестами и работает правильно.
    */
}
