package ru.gdcn.beastmaster64revelations;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.gdcn.beastmaster64revelations.GameActivities.MainMenu.MainMenuActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.*;


@LargeTest
@RunWith(AndroidJUnit4.class)

public class UITests {

    @Rule
    public ActivityTestRule<MainMenuActivity> mActivityRule = new ActivityTestRule<>(
            MainMenuActivity.class);

    @Test
    public void mainTest(){
        testMainMenu();
        testCreation();
        testGameActivity();
    }

    @Test
    public void testMainMenu() {
        onView(ViewMatchers.withId(R.id.activity_main_menu_mainFrame))
                .check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.main_menu_button_exit))
                .check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.main_menu_button_play))
                .check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.main_menu_button_settings))
                .check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.main_menu_text_title))
                .check(matches(isDisplayed()))
                .check(matches(withText("BM64:REV")));
        onView(withId(R.id.main_menu_button_play)).perform(click());
    }

    @Test
    public void testCreation() {
        //Переходим в создание персонажа
        onView(ViewMatchers.withId(R.id.activity_character_creation_char_name))
                .check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.activity_character_creation_edit_char_name))
                .check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.activity_character_creation_points))
                .check(matches(isDisplayed()))
                .check(matches(withId(R.id.activity_character_creation_points)));
        onView(ViewMatchers.withId(R.id.activity_character_creation_agilityText))
                .check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.activity_character_creation_intellectText))
                .check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.activity_character_creation_strengthText))
                .check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.activity_character_creation_proceedButton))
                .check(matches(isDisplayed()));
        onView(withId(R.id.activity_character_creation_char_face_man)).perform(click());
        onView(withId(R.id.activity_character_creation_edit_char_name)).perform(replaceText("Тестирую!"))
                .check(matches(withText("Тестирую!")));
        onView(withId(R.id.activity_character_creation_char_name))
                .check(matches(withText("Тестирую!")));

        onView(withId(R.id.button_plus_str)).perform(click());
        onView(withId(R.id.button_plus_agi)).perform(click());
        onView(withId(R.id.button_plus_agi)).perform(click());

        onView(ViewMatchers.withId(R.id.activity_character_creation_points))
                .check(matches(isDisplayed()))
                .check(matches(withId(R.id.activity_character_creation_points)));
        onView(withId(R.id.activity_character_creation_proceedButton)).perform(click());
    }

    @Test
    public void testGameActivity(){
        //Переходим в основное игровое активити
        //Проверки отображений вьюшек
        onView(withId(R.id.activity_in_location_backgroundFrame))
                .check(matches(isDisplayed()));
        onView(withId(R.id.activity_in_location_fragHolder))
                .check(matches(isDisplayed()));
        onView(withId(R.id.activity_in_location_menu_loc))
                .check(matches(isDisplayed()));
        onView(withId(R.id.activity_in_location_menu_map))
                .check(matches(isDisplayed()));
        onView(withId(R.id.activity_in_location_menu_statistics))
                .check(matches(isDisplayed()));
        onView(withId(R.id.activity_in_location_menu_toMenu))
                .check(matches(isDisplayed()));

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

    }


}
