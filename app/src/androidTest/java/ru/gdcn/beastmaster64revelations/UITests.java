package ru.gdcn.beastmaster64revelations;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.gdcn.beastmaster64revelations.MainMenu.MainMenuActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@LargeTest
@RunWith(AndroidJUnit4.class)

public class UITests {

    @Rule
    public ActivityTestRule<MainMenuActivity> mActivityRule = new ActivityTestRule<>(
            MainMenuActivity.class);

    @Test
    public void onClick() throws Exception {
        onView(withId(R.id.main_menu_button_play)).perform(click());

        //Переходим в создание персонажа

        onView(withId(R.id.activity_character_creation_char_face_man)).perform(click());
        onView(withId(R.id.activity_character_creation_edit_char_name)).perform(replaceText("Тестирую!"));

        onView(withId(R.id.button_plus_str)).perform(click());

        onView(withId(R.id.button_plus_agi)).perform(click());
        onView(withId(R.id.button_plus_agi)).perform(click());
        onView(withId(R.id.button_plus_agi)).perform(click());
        onView(withId(R.id.button_plus_agi)).perform(click());
        onView(withId(R.id.button_plus_agi)).perform(click());

        onView(withId(R.id.button_plus_int)).perform(click());
        onView(withId(R.id.button_plus_int)).perform(click());
        onView(withId(R.id.button_plus_int)).perform(click());
        onView(withId(R.id.button_plus_int)).perform(click());
        onView(withId(R.id.button_plus_int)).perform(click());
        onView(withId(R.id.button_plus_int)).perform(click());
        onView(withId(R.id.button_plus_int)).perform(click());

        onView(withId(R.id.activity_character_creation_proceedButton)).perform(click());

        //Переходим в основное игровое активити

        //Докачиваем статы персонажа
        onView(withId(R.id.activity_in_location_menu_statistics)).perform(click());

        for (int i = 0; i < 10; i++) {
            onView(withId(R.id.fragment_stats_plus_int)).perform(click());
        }

        //Заходим смотрим на карту
        onView(withId(R.id.activity_in_location_menu_map)).perform(click());

        //Идём смотреть локацию
        onView(withId(R.id.activity_in_location_menu_loc)).perform(click());

        //Ходим по локациям стрелочками
        onView(withId(R.id.fragment_in_location_content_GoDown)).perform(scrollTo(), click());
        onView(withId(R.id.fragment_in_location_content_GoDown)).perform(scrollTo(), click());
        onView(withId(R.id.fragment_in_location_content_GoDown)).perform(scrollTo(), click());
        onView(withId(R.id.fragment_in_location_content_GoRight)).perform(scrollTo(), click());
        onView(withId(R.id.fragment_in_location_content_GoUp)).perform(scrollTo(), click());
        onView(withId(R.id.fragment_in_location_content_GoLeft)).perform(scrollTo(), click());

        //Нападаем на NPC
        onView(withId(R.id.fragment_in_location_content_NPCattack)).perform(scrollTo(), click());

        //Выходим из драки
        pressBack();
        pressBack();
        //Выходим в меню
        pressBack();

    }


}
