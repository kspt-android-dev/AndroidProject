package com.dreamteam.monopoly
import org.junit.Rule
import org.junit.Test


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import android.support.test.uiautomator.UiDevice
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation


class AmountOfPlayersActivityTest {
    @get:Rule
    public val mActivityRule: ActivityTestRule<AmountOfPlayersActivity>  = ActivityTestRule(AmountOfPlayersActivity::class.java)



    @Test
    @Throws(Exception::class)
    fun onClick() {
        val device = UiDevice.getInstance(getInstrumentation())

        onView(withId(R.id.Namespace)).perform(typeText("TheBestPlayer")).perform(closeSoftKeyboard())
        Thread.sleep(250);
        onView(withId(R.id.buttonEnter)).perform(click())
        onView(withId(R.id.Namespace)).perform(typeText("WillBeDeleted")).perform(closeSoftKeyboard())
        Thread.sleep(250);
        onView(withId(R.id.buttonEnter)).perform(click())
        onView(withId(R.id.Namespace)).perform(typeText("TheWorstPlayer")).perform(closeSoftKeyboard())
        Thread.sleep(250);
        onView(withId(R.id.buttonEnter)).perform(click())
        onView(withId(R.id.aiButton1)).perform(click())
        onView(withId(R.id.aiButton1)).check(matches(withText("AI: ON")))
        onView(withId(R.id.PlayerName1)).check(matches(withText("TheBestPlayer")))
        onView(withId(R.id.PlayerName2)).perform(click())
        onView(withId(R.id.Namespace)).perform(typeText("CheckRotation")).perform(closeSoftKeyboard())
        device.setOrientationLeft();
        onView(withId(R.id.buttonDelete)).perform(click())
        onView(withId(R.id.PlayerName2)).check(matches(withText("TheWorstPlayer")))
        onView(withId(R.id.buttonEnter)).perform(click())


    }



}