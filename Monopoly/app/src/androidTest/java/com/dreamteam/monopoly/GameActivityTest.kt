package com.dreamteam.monopoly


import org.junit.Rule
import org.junit.Test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.ViewInteraction
import androidx.test.rule.ActivityTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import android.support.test.uiautomator.UiDevice
import com.dreamteam.monopoly.game.GameManager
import android.content.Intent
import android.util.Log
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteractionComponent
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers

import com.dreamteam.monopoly.game.player.PlayerType
import org.junit.Before



class GameActivityTest{
    @get:Rule
    public val mActivityRule: ActivityTestRule<GameActivity>  = ActivityTestRule(GameActivity::class.java,false,false)

    @Before
    fun setUp() {

    }

    @Test
    @Throws(Exception::class)
    fun onClick() {
        val device = UiDevice.getInstance(getInstrumentation())

        val intent = Intent()
        val map : HashMap<PlayerType,ArrayList<String>> = HashMap()
        val players: ArrayList<String> = ArrayList(2)
        players.add("Alexander")
        players.add("Aleksei")
        map[PlayerType.PERSON] = players
        intent.putExtra("Map", map)
        mActivityRule.launchActivity(intent)

        val ga = mActivityRule.activity
        val gm = GameManager(ga)

        onView(withId(R.id.buttonThrowCubes)).perform(click())
        Thread.sleep(1500)
        Log.d("TESTUI", (withId(R.id.DialogView).matches(isDisplayed())).toString())
        Espresso.onView(ViewMatchers.withId(R.id.DialogView)).check(matches(isDisplayed()))
        {
            onView(withId(R.id.YesButton)).perform(click())
        }
        Thread.sleep(1500)
        onView(withId(R.id.buttonThrowCubes)).perform(click())
        Thread.sleep(1500)
        if (withId(R.id.DialogView).matches(isDisplayed()))
        {
            onView(withId(R.id.YesButton)).perform(click())
        }
        Thread.sleep(1500)
        if (gm.getCurrentPlayer().cells != null)
        {
            val myId =ga.resources.getIdentifier("cell${gm.getCurrentPlayer().cells.last().id}", "id", ga.packageName)
            onView(withId(R.id.YesButton)).perform(click())
        }




    }
}
