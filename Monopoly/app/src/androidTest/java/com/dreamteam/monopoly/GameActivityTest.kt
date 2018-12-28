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
import org.hamcrest.Matchers.not


class GameActivityTest {
    @get:Rule
    val mActivityRule: ActivityTestRule<GameActivity> = ActivityTestRule(GameActivity::class.java, false, false)


    @Test
    @Throws(Exception::class)
    fun onClick() {
        val device = UiDevice.getInstance(getInstrumentation())
        val isHumanCheck = true
        val testsellnumber = 25

        val intent = Intent()
        val map: HashMap<PlayerType, ArrayList<String>> = HashMap()
        val players: ArrayList<String> = ArrayList(2)
        players.add("Alexander")
        players.add("Aleksei")
        map[PlayerType.PERSON] = players
        intent.putExtra(playersMap, map)
        mActivityRule.launchActivity(intent)

        val ga = mActivityRule.activity

        onView(withId(R.id.buttonThrowCubes)).perform(click())
        onView(withId(R.id.buttonThrowCubes)).check(ViewAssertions.matches(not(isDisplayed())))
        delay(isHumanCheck, DelayType.SHORT)
        if (ga.findViewById<Button>(R.id.DialogView).visibility == View.VISIBLE) {
            onView(withId(R.id.YesButton)).perform(click())
            onView(withId(R.id.DialogView)).check(ViewAssertions.matches(not(isDisplayed())))
        }
        delay(isHumanCheck, DelayType.SHORT)
        onView(withId(R.id.buttonThrowCubes)).perform(click())
        onView(withId(R.id.buttonThrowCubes)).check(ViewAssertions.matches(not(isDisplayed())))
        delay(isHumanCheck, DelayType.SHORT)
        if (ga.findViewById<Button>(R.id.DialogView).visibility == View.VISIBLE) {
            onView(withId(R.id.YesButton)).perform(click())
            onView(withId(R.id.DialogView)).check(ViewAssertions.matches(not(isDisplayed())))
        }
        delay(isHumanCheck, DelayType.SHORT)
        onView(withId(R.id.buttonThrowCubes)).perform(click())
        onView(withId(R.id.buttonThrowCubes)).check(ViewAssertions.matches(not(isDisplayed())))
        delay(isHumanCheck, DelayType.SHORT)
        if (ga.findViewById<Button>(R.id.DialogView).visibility == View.VISIBLE) {
            onView(withId(R.id.NoButton)).perform(click())
            onView(withId(R.id.DialogView)).check(ViewAssertions.matches(not(isDisplayed())))
        }
        delay(isHumanCheck, DelayType.SHORT)
        onView(withId(R.id.buttonThrowCubes)).perform(click())
        onView(withId(R.id.buttonThrowCubes)).check(ViewAssertions.matches(not(isDisplayed())))
        delay(isHumanCheck, DelayType.SHORT)
        if (ga.findViewById<Button>(R.id.DialogView).visibility == View.VISIBLE) {
            onView(withId(R.id.NoButton)).perform(click())
            onView(withId(R.id.DialogView)).check(ViewAssertions.matches(not(isDisplayed())))
        }
        delay(isHumanCheck, DelayType.SHORT)
        onView(withId(R.id.scrollview2)).perform(swipeUp())
        onView(withId(R.id.scrollview1)).perform(swipeLeft())
        onView(withId(R.id.cell25)).perform(click())
        delay(isHumanCheck, DelayType.SHORT)

        onView(withId(R.id.cellName)).check(ViewAssertions.matches(withText("Name: " + ga.getGameManager().mainBoard.gameWay[testsellnumber - 1].info.name)))
        delay(isHumanCheck, DelayType.SHORT)
        device.setOrientationLeft()
        delay(isHumanCheck, DelayType.LONG)
        device.setOrientationNatural()
        delay(isHumanCheck, DelayType.SHORT)
        onView(withId(R.id.buttonSuicide)).perform(click())
        delay(isHumanCheck, DelayType.SHORT)
    }
}
