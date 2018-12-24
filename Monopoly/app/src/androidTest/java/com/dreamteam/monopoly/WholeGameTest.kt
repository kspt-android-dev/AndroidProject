package com.dreamteam.monopoly

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.rule.ActivityTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import android.support.test.uiautomator.UiDevice
import android.view.View
import android.widget.Button
import androidx.test.espresso.assertion.ViewAssertions
import org.junit.Rule
import org.junit.Test
import androidx.test.runner.lifecycle.Stage
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import android.app.Activity
import android.widget.TextView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import com.dreamteam.monopoly.game.GameManager
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import java.lang.IllegalStateException


class WholeGameTest {

    @get:Rule
    val mActivityRule: ActivityTestRule<MainActivity>  = ActivityTestRule(MainActivity::class.java)

    @Test
    @Throws(Exception::class)
    fun testGame() {
        val device = UiDevice.getInstance(getInstrumentation())
        val testsellnumber = 25
        val shortDelay = 2000L  // delays need for visualising
        val longDelay = 10000L
        Thread.sleep(shortDelay)
        onView(withId(R.id.buttonPlay)).perform(click())
        Thread.sleep(shortDelay)
        onView(withId(R.id.Namespace)).perform(typeText("TheBestPlayer")).perform(closeSoftKeyboard())
        onView(withId(R.id.buttonEnter)).perform(click())
        onView(withId(R.id.Namespace)).perform(typeText("WillBeDeleted")).perform(closeSoftKeyboard())
        onView(withId(R.id.buttonEnter)).perform(click())
        onView(withId(R.id.Namespace)).perform(typeText("TheWorstPlayer")).perform(closeSoftKeyboard())
        onView(withId(R.id.buttonEnter)).perform(click())
        onView(withId(R.id.aiButton1)).perform(click())
        onView(withId(R.id.aiButton1)).check(ViewAssertions.matches(withText("AI: ON")))
        onView(withId(R.id.PlayerName1)).check(ViewAssertions.matches(withText("TheBestPlayer")))
        onView(withId(R.id.PlayerName2)).perform(click())
        onView(withId(R.id.Namespace)).perform(typeText("CheckRotation")).perform(closeSoftKeyboard())
        device.setOrientationLeft()
        onView(withId(R.id.aiButton1)).perform(click())
        onView(withId(R.id.buttonDelete)).perform(click())
        device.setOrientationNatural()
        onView(withId(R.id.PlayerName2)).check(ViewAssertions.matches(withText("TheWorstPlayer")))
        onView(withId(R.id.buttonEnter)).perform(click())
        onView(withId(R.id.buttonStart)).perform(click())
        val ga = getActivityInstance() as GameActivity

        onView(withId(R.id.buttonThrowCubes)).perform(click())
        onView(withId(R.id.buttonThrowCubes)).check(ViewAssertions.matches(Matchers.not(isDisplayed())))
        Thread.sleep(shortDelay)
        if (ga.findViewById<Button>(R.id.DialogView).visibility == View.VISIBLE) {
            onView(withId(R.id.YesButton)).perform(click())
            onView(withId(R.id.DialogView)).check(ViewAssertions.matches(Matchers.not(isDisplayed())))
        }
        Thread.sleep(shortDelay)
        onView(withId(R.id.buttonThrowCubes)).perform(click())
        onView(withId(R.id.buttonThrowCubes)).check(ViewAssertions.matches(Matchers.not(isDisplayed())))
        Thread.sleep(shortDelay)
        if (ga.findViewById<Button>(R.id.DialogView).visibility == View.VISIBLE) {
            onView(withId(R.id.YesButton)).perform(click())
            onView(withId(R.id.DialogView)).check(ViewAssertions.matches(Matchers.not(isDisplayed())))
        }
        Thread.sleep(shortDelay)
        onView(withId(R.id.buttonThrowCubes)).perform(click())
        onView(withId(R.id.buttonThrowCubes)).check(ViewAssertions.matches(Matchers.not(isDisplayed())))
        Thread.sleep(shortDelay)
        if (ga.findViewById<Button>(R.id.DialogView).visibility == View.VISIBLE) {
            onView(withId(R.id.NoButton)).perform(click())
            onView(withId(R.id.DialogView)).check(ViewAssertions.matches(Matchers.not(isDisplayed())))
        }
        Thread.sleep(shortDelay)
        onView(withId(R.id.buttonThrowCubes)).perform(click())
        onView(withId(R.id.buttonThrowCubes)).check(ViewAssertions.matches(Matchers.not(isDisplayed())))
        Thread.sleep(shortDelay)
        if (ga.findViewById<Button>(R.id.DialogView).visibility == View.VISIBLE) {
            onView(withId(R.id.NoButton)).perform(click())
            onView(withId(R.id.DialogView)).check(ViewAssertions.matches(Matchers.not(isDisplayed())))
        }
        Thread.sleep(shortDelay)
        onView(withId(R.id.cell25)).perform(click())
        Thread.sleep(shortDelay)
        if (getText(withId(R.id.cellName)) != ("Name: " + ga.getGameManager().mainBoard.gameWay[testsellnumber - 1].info.name))
            throw IllegalStateException()  // -1 because array size start from
        Thread.sleep(shortDelay)
        device.setOrientationLeft()
        Thread.sleep(longDelay)   //long delay here to check rotation save
        device.setOrientationNatural()
        Thread.sleep(shortDelay)
        onView(withId(R.id.buttonSuicide)).perform(click())
        onView(withId(R.id.buttonSuicide)).perform(click())
        Thread.sleep(shortDelay)
    }

    }

    private fun getActivityInstance(): Activity {
        val currentActivity = ArrayList<Activity>()

        getInstrumentation().runOnMainSync {
            val resumedActivity = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED)
            val it = resumedActivity.iterator()
            currentActivity.add(it.next())
        }

        return currentActivity[0]
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
