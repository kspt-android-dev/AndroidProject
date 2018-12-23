package com.dreamteam.monopoly

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


/*import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
//import android.support.v4.content.MimeTypeFilter.matches*/
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule



class AmountOfPlayersActivityTest {
    @get:Rule
    public val mActivityRule: ActivityTestRule<AmountOfPlayersActivity>  = ActivityTestRule(AmountOfPlayersActivity::class.java)


    @Before
    @Throws(Exception::class)
    fun setUp() {
    }

    @Test
    @Throws(Exception::class)
    fun onClick() {
        onView(withId(R.id.Namespace)).perform(typeText("TheBestPlayer"))
        onView(withId(R.id.buttonEnter)).perform(click())
        onView(withId(R.id.Namespace)).perform(typeText("TheWorstPlayer"))
        onView(withId(R.id.buttonEnter)).perform(click())
        onView(withId(R.id.PlayerName1)).check(matches(withText("TheBestPlayer")))
        onView(withId(R.id.PlayerName2)).check(matches(withText("TheWorstPlayer")))
    }

}