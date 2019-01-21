package com.example.lixir.nim

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.matcher.ViewMatchers.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Rule
import android.support.test.rule.ActivityTestRule


class MainTest {

    @get:Rule
    val mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }


    @Test
    fun onClick(){
        onView(withId(R.id.info_button)).perform(click())
        onView(withId(R.id.info_leaner)).perform(click())
        onView(withId(R.id.rename_button)).perform(click())
        onView(withId(R.id.enter_name_player_1)).perform(clearText())
        onView(withId(R.id.enter_name_player_1)).perform(typeText("TestName1"))
        onView(withId(R.id.enter_name_player_2)).perform(clearText())
        onView(withId(R.id.enter_name_player_2)).perform(typeText("TestName2"))
        onView(withId(R.id.enter_name_bot)).perform(clearText())
        onView(withId(R.id.enter_name_bot)).perform(typeText("TestNameBot"))
        onView(withId(R.id.return_menu)).perform(click())
        onView(withId(R.id.duo_game_button)).perform(click())

//      onView(withId(R.id.rv_0)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))


    }

}