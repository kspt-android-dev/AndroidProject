package com.example.lixir.nim

import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.RootMatchers.isDialog
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import com.example.lixir.nim.R.id.*
import com.example.lixir.nim.R.string.turn_player
import com.example.lixir.nim.backend.Constants
import com.example.lixir.nim.backend.Constants.WIN
import org.junit.Rule
import org.junit.Test


class MainTest {

    @get:Rule
    val mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    private val TestName1 = "TestName1"
    private val TestName2 = "TestName2"
    private val TestNameBot = "TestNameBot"

    private fun makeClick(id: Int){
        onView(withId(id)).perform(click())
    }

    private fun makeClearText(id: Int){
        onView(withId(id)).perform(clearText())
    }
    private fun makeTypeText(id: Int, text: String){
        onView(withId(id)).perform(typeText(text))
    }

    private fun makeClickRV(id: Int, position: Int){
        onView(withId(id))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, click()))
    }

    private fun checkText(id: Int, text: String){
        onView(withId(id))
            .check(matches(withText(text)))
    }

    private fun makeClickDialog(id: Int){
        onView(withText(id))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
            .perform(click())
    }

    private fun checkTextDialog(text: String){
        onView(withText(text))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
    }

    private fun orientationLandscape(){
        mActivityRule.activity.requestedOrientation = SCREEN_ORIENTATION_LANDSCAPE
    }

    private fun orientationPortrait(){
        mActivityRule.activity.requestedOrientation = SCREEN_ORIENTATION_PORTRAIT
    }

    private fun makePressBack(){
        onView(isRoot()).perform(ViewActions.pressBack())
    }

    @Test
    fun onClickTest(){
        makeClick(info_button)
        makeClick(info_leaner)
        makeClick(rename_button)
        makeClearText(enter_name_player_1)
        makeTypeText(enter_name_player_1, "$TestName1\n")
        makeClearText(enter_name_player_2)
        makeTypeText(enter_name_player_2, "$TestName2\n")
        makeClearText(enter_name_bot)
        makeTypeText(enter_name_bot, "$TestNameBot\n")
        makeClick(return_menu)
        makeClick(duo_game_button)
        makeClickRV(rv_0, 0)
        checkText(current_player_text_view, "${mActivityRule.activity.applicationContext.getString(turn_player)} $TestName2")
        makeClickRV(rv_1, 0)
        checkText(current_player_text_view, "${mActivityRule.activity.applicationContext.getString(turn_player)} $TestName1")
        makeClickRV(rv_2, 0)
        makeClickRV(rv_3, 0)
        makeClickDialog(R.string.return_menu)
        makeClick(single_game_button)
        makeClickRV(rv_0, 0)
        makeClickRV(rv_2, 0)
        checkTextDialog("$WIN $TestNameBot")
        makeClickDialog(R.string.return_menu)
        makeClick(exit_button)
    }

    @Test
    fun orientationAndPressBackTest(){
        makeClick(info_button)
        orientationLandscape()
        orientationPortrait()
        makePressBack()
        makeClick(rename_button)
        makeClearText(enter_name_player_1)
        makeTypeText(enter_name_player_1, "$TestName1\n")
        makeClearText(enter_name_player_2)
        makeTypeText(enter_name_player_2, "$TestName2\n")
        orientationLandscape()
        makeClearText(enter_name_bot)
        makeTypeText(enter_name_bot, "$TestNameBot\n")
        orientationPortrait()
        makePressBack()
        makeClick(duo_game_button)
        makeClickRV(rv_0, 0)
        checkText(current_player_text_view, "${mActivityRule.activity.applicationContext.getString(turn_player)} $TestName2")
        orientationLandscape()
        makeClickRV(rv_1, 0)
        checkText(current_player_text_view, "${mActivityRule.activity.applicationContext.getString(turn_player)} $TestName1")
        orientationPortrait()
        makeClickRV(rv_2, 0)
        makePressBack()
        makeClick(single_game_button)
        orientationLandscape()
        orientationPortrait()
        makePressBack()
    }

    @Test
    fun rvTest(){
        makeClick(duo_game_button)
        for (i in (0 until Constants.MAIN_LIST[0]).reversed())
            makeClickRV(rv_0, i)
        for (i in (0 until Constants.MAIN_LIST[1]).reversed())
            makeClickRV(rv_1, i)
        for (i in (0 until Constants.MAIN_LIST[2]).reversed())
            makeClickRV(rv_2, i)
        for (i in (0 until Constants.MAIN_LIST[3]).reversed())
            makeClickRV(rv_3, i)
    }
}