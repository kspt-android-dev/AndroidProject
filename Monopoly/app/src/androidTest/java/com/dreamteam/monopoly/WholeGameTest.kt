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





class WholeGameTest {

    @get:Rule
    val mActivityRule: ActivityTestRule<MainActivity>  = ActivityTestRule(MainActivity::class.java)

    @Test
    @Throws(Exception::class)
    fun testGame() {
        val device = UiDevice.getInstance(getInstrumentation())
        Thread.sleep(1000)
        onView(withId(R.id.buttonPlay)).perform(click())
        Thread.sleep(1000)



        onView(withId(R.id.Namespace)).perform(typeText("TheBestPlayer")).perform(closeSoftKeyboard())
        Thread.sleep(250)
        onView(withId(R.id.buttonEnter)).perform(click())
        onView(withId(R.id.Namespace)).perform(typeText("WillBeDeleted")).perform(closeSoftKeyboard())
        Thread.sleep(250)
        onView(withId(R.id.buttonEnter)).perform(click())
        onView(withId(R.id.Namespace)).perform(typeText("TheWorstPlayer")).perform(closeSoftKeyboard())
        Thread.sleep(250)
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
        val ga = getActivityInstance()

        onView(withId(R.id.buttonThrowCubes)).perform(click())
        Thread.sleep(1500)
        if (ga.findViewById<Button>(R.id.YesButton).visibility == View.VISIBLE)
        {
            onView(withId(R.id.YesButton)).perform(click())
        }
        Thread.sleep(1500)
        onView(withId(R.id.buttonThrowCubes)).perform(click())
        Thread.sleep(1500)
        if (ga.findViewById<Button>(R.id.YesButton).visibility == View.VISIBLE)
        {
            onView(withId(R.id.YesButton)).perform(click())
        }
        Thread.sleep(1500)
        onView(withId(R.id.buttonThrowCubes)).perform(click())
        Thread.sleep(1500)
        if (ga.findViewById<Button>(R.id.NoButton).visibility == View.VISIBLE)
        {
            onView(withId(R.id.NoButton)).perform(click())
        }
        Thread.sleep(1500)
        onView(withId(R.id.buttonThrowCubes)).perform(click())
        Thread.sleep(1500)
        if (ga.findViewById<Button>(R.id.NoButton).visibility == View.VISIBLE)
        {
            onView(withId(R.id.NoButton)).perform(click())
        }
        Thread.sleep(1500)
        onView(withId(R.id.cell32)).perform(click())
        Thread.sleep(1500)
        onView(withId(R.id.cell25)).perform(click())
        Thread.sleep(1500)
        device.setOrientationLeft()
        Thread.sleep(10000)
        device.setOrientationNatural()
        Thread.sleep(1500)
        onView(withId(R.id.buttonSuicide)).perform(click())
        Thread.sleep(3000)
        onView(withId(R.id.buttonSuicide)).perform(click())
        Thread.sleep(5000)

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
}