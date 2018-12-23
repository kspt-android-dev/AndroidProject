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


import com.dreamteam.monopoly.game.player.PlayerType
import org.junit.Before



class GameActivityTest{
    @get:Rule
     val mActivityRule: ActivityTestRule<GameActivity>  = ActivityTestRule(GameActivity::class.java,false,false)


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


    }
}
