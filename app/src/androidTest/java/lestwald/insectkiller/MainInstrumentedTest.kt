package lestwald.insectkiller

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId

@RunWith(AndroidJUnit4::class)
class MainInstrumentedTest {
    @get:Rule
    val mainActivityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainScreen() {
        onView(withId(R.id.logo)).check(matches(isDisplayed()))
        onView(withId(R.id.startButton)).check(matches(isDisplayed()))
        onView(withId(R.id.settingsButton)).check(matches(isDisplayed()))
        onView(withId(R.id.leaderboardButton)).check(matches(isDisplayed()))
    }

    @Test
    fun clickStartButton() {
        onView(withId(R.id.startButton)).perform(click())
        onView(withId(R.id.top_bar)).check(matches(isDisplayed()))
        onView(withId(R.id.pause_button)).check(matches(isDisplayed()))
        onView(withId(R.id.health_bar)).check(matches(isDisplayed()))
        onView(withId(R.id.score)).check(matches(isDisplayed()))
        onView(withId(R.id.game_field)).check(matches(isDisplayed()))
        onView(withId(R.id.pizza)).check(matches(isDisplayed()))
    }

    @Test
    fun clickLeaderboardButton() {
        onView(withId(R.id.leaderboardButton)).perform(click())
        onView(withId(R.id.tabLayout)).check(matches(isDisplayed()))
        onView(withId(R.id.viewPager)).check(matches(isDisplayed()))
    }

    @Test()
    fun clickPause() {
        onView(withId(R.id.startButton)).perform(click())
        onView(withId(R.id.pause_button)).perform(click())
    }
}