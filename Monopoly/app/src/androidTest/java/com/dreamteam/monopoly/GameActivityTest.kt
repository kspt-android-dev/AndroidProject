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


import com.dreamteam.monopoly.game.player.PlayerType
import org.hamcrest.Matchers.not
import android.widget.TextView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAssertion
import org.hamcrest.Matcher
import java.lang.IllegalStateException


class GameActivityTest {
    @get:Rule
    val mActivityRule: ActivityTestRule<GameActivity> = ActivityTestRule(GameActivity::class.java, false, false)


    @Test
    @Throws(Exception::class)
    fun onClick() {
        val device = UiDevice.getInstance(getInstrumentation())
        val testsellnumber = 25
        val shortDelay = 2000L  // delays need for visualising
        val longDelay = 10000L

        val intent = Intent()
        val map: HashMap<PlayerType, ArrayList<String>> = HashMap()
        val players: ArrayList<String> = ArrayList(2)
        players.add("Alexander")
        players.add("Aleksei")
        map[PlayerType.PERSON] = players
        intent.putExtra("Map", map)
        mActivityRule.launchActivity(intent)

        val ga = mActivityRule.activity

        onView(withId(R.id.buttonThrowCubes)).perform(click())
        onView(withId(R.id.buttonThrowCubes)).check(ViewAssertions.matches(not(isDisplayed())))
        Thread.sleep(shortDelay)
        if (ga.findViewById<Button>(R.id.DialogView).visibility == View.VISIBLE) {
            onView(withId(R.id.YesButton)).perform(click())
            onView(withId(R.id.DialogView)).check(ViewAssertions.matches(not(isDisplayed())))
        }
        Thread.sleep(shortDelay)
        onView(withId(R.id.buttonThrowCubes)).perform(click())
        onView(withId(R.id.buttonThrowCubes)).check(ViewAssertions.matches(not(isDisplayed())))
        Thread.sleep(shortDelay)
        if (ga.findViewById<Button>(R.id.DialogView).visibility == View.VISIBLE) {
            onView(withId(R.id.YesButton)).perform(click())
            onView(withId(R.id.DialogView)).check(ViewAssertions.matches(not(isDisplayed())))
        }
        Thread.sleep(shortDelay)
        onView(withId(R.id.buttonThrowCubes)).perform(click())
        onView(withId(R.id.buttonThrowCubes)).check(ViewAssertions.matches(not(isDisplayed())))
        Thread.sleep(shortDelay)
        if (ga.findViewById<Button>(R.id.DialogView).visibility == View.VISIBLE) {
            onView(withId(R.id.NoButton)).perform(click())
            onView(withId(R.id.DialogView)).check(ViewAssertions.matches(not(isDisplayed())))
        }
        Thread.sleep(shortDelay)
        onView(withId(R.id.buttonThrowCubes)).perform(click())
        onView(withId(R.id.buttonThrowCubes)).check(ViewAssertions.matches(not(isDisplayed())))
        Thread.sleep(shortDelay)
        if (ga.findViewById<Button>(R.id.DialogView).visibility == View.VISIBLE) {
            onView(withId(R.id.NoButton)).perform(click())
            onView(withId(R.id.DialogView)).check(ViewAssertions.matches(not(isDisplayed())))
        }
        Thread.sleep(shortDelay)
        onView(withId(R.id.scrollview2)).perform(swipeUp())
        onView(withId(R.id.scrollview1)).perform(swipeLeft())
        onView(withId(R.id.cell25)).perform(click())
        Thread.sleep(shortDelay)

        onView(withId(R.id.cellName)).check(ViewAssertions.matches(withText("Name: " + ga.getGameManager().mainBoard.gameWay[testsellnumber-1].info.name)))
        //if (getText(withId(R.id.cellName)) != ("Name: " + ga.getGameManager().mainBoard.gameWay[testsellnumber - 1].info.name))
          //  throw IllegalStateException()  // -1 because array size start from
        Thread.sleep(shortDelay)
        device.setOrientationLeft()
        Thread.sleep(longDelay)   //long delay here to check rotation save
        device.setOrientationNatural()
        Thread.sleep(shortDelay)
        onView(withId(R.id.buttonSuicide)).perform(click())
        Thread.sleep(shortDelay)
    }

    fun getText(matcher: Matcher<View>): String {
        val stringHolder = ArrayList<String>()
        onView(matcher).perform(object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isAssignableFrom(TextView::class.java)
            }

            override fun getDescription(): String {
                return "getting text from a TextView"
            }

            override fun perform(uiController: UiController, view: View) {
                val tv = view as TextView
                stringHolder.add(tv.text.toString())
            }
        })
        return stringHolder[0]
    }
}
