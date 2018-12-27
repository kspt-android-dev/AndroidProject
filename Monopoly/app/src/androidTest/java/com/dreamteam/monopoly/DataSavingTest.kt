package com.dreamteam.monopoly

import org.junit.Rule
import org.junit.Test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.rule.ActivityTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import android.support.test.uiautomator.UiDevice
import android.content.Intent
import android.view.View
import android.widget.Button
import androidx.test.espresso.assertion.ViewAssertions
import com.dreamteam.monopoly.game.data.ValuesData.playersMap


import com.dreamteam.monopoly.game.player.PlayerType
import com.dreamteam.monopoly.helpers.DelayType
import com.dreamteam.monopoly.helpers.delay
import org.hamcrest.Matchers

class DataSavingTest {
    @get:Rule
    val mActivityRule: ActivityTestRule<GameActivity> = ActivityTestRule(GameActivity::class.java, false, false)
    private val isHumanCheck = false

    @Test
    @Throws(Exception::class)
    fun onClick() {
        val device = UiDevice.getInstance(getInstrumentation())

        val testSellNumber = 2
        var isBtnBuyVisible = true

        val intent = Intent()
        val map: HashMap<PlayerType, ArrayList<String>> = HashMap()
        val players: ArrayList<String> = ArrayList(2)
        players.add("Alexander")
        players.add("Aleksei")
        map[PlayerType.PERSON] = players
        intent.putExtra(playersMap, map)
        mActivityRule.launchActivity(intent)

        val ga = mActivityRule.activity

        fun checkSave() {
            onView(withId(R.id.cell2)).perform(click())
            delay(isHumanCheck, DelayType.SHORT)
            device.setOrientationLeft()
            delay(isHumanCheck, DelayType.SHORT)
            onView(withId(R.id.cellName)).check(ViewAssertions.matches(withText("Name: " + ga.getGameManager().mainBoard.gameWay[testSellNumber - 1].info.name)))
            onView(withId(R.id.DialogView)).check(ViewAssertions.matches(isDisplayed()))
            delay(isHumanCheck, DelayType.SHORT)
            onView(withId(R.id.buttonSuicide)).perform(click())
            delay(isHumanCheck, DelayType.SHORT)
            device.setOrientationNatural()
            onView(withId(R.id.DialogView)).check(ViewAssertions.matches(Matchers.not(isDisplayed())))
        }

        while (!isBtnBuyVisible) {
            onView(withId(R.id.buttonThrowCubes)).perform(click())
            delay(isHumanCheck, DelayType.SHORT)
            if (ga.findViewById<Button>(R.id.DialogView).visibility == View.VISIBLE) {
                onView(withId(R.id.YesButton)).perform(click())
                isBtnBuyVisible = true
                checkSave()
            }
            delay(true, DelayType.SHORT)
            if (!isBtnBuyVisible) {
                onView(withId(R.id.buttonThrowCubes)).perform(click())
                delay(isHumanCheck, DelayType.SHORT)
                if (ga.findViewById<Button>(R.id.DialogView).visibility == View.VISIBLE) {
                    onView(withId(R.id.YesButton)).perform(click())
                    isBtnBuyVisible = true
                    checkSave()
                }
                delay(isHumanCheck, DelayType.SHORT)
            }
        }
    }
}
