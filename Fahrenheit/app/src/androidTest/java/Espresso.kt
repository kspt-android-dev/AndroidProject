import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.example.fahrenheit.MenuActivity
import com.example.fahrenheit.R
import org.junit.Rule
import org.junit.Test

class Espresso {

    @get:Rule
    val mActivityRule: ActivityTestRule<MenuActivity> = ActivityTestRule(MenuActivity::class.java)

    @Test
    fun mainTest() {
        /* MenuActivity */
        menuActivityTest()
        /* SettingsActivity */
        settingsActivityTest()
        /* ProgressActivity */
        progressActivityTest()
        /*MainActivity*/
        mainActivityTest()
    }

    private fun makeClick(id: Int) {
        onView(withId(id)).perform(click())
    }

    private fun isViewDisplayed(id: Int) {
        onView(withId(id))
            .check(matches(isDisplayed()))
    }

    private fun orientationLandscape() {
        mActivityRule.activity.requestedOrientation = SCREEN_ORIENTATION_LANDSCAPE
    }

    private fun orientationPortrait() {
        mActivityRule.activity.requestedOrientation = SCREEN_ORIENTATION_PORTRAIT
    }

    private fun makePressBack() {
        onView(isRoot()).perform(ViewActions.pressBack())
    }

    @Test
    fun settingsActivityTest() {
        makeClick(R.id.settings_btn)
        orientationLandscape()
        orientationPortrait()
        // Switch styles button
        isViewDisplayed(R.id.switch_style_btn)
        makeClick(R.id.switch_style_btn)
        // Switch music button
        isViewDisplayed(R.id.on_off_music_btn)
        makeClick(R.id.on_off_music_btn)
        // Switch censor button
        isViewDisplayed(R.id.on_off_censor_btn)
        makeClick(R.id.on_off_censor_btn)
        makePressBack()
    }

    @Test
    fun menuActivityTest() {
        orientationLandscape()
        orientationPortrait()
        isViewDisplayed(R.id.start_game_btn)
        isViewDisplayed(R.id.progress_game_btn)
        isViewDisplayed(R.id.settings_btn)
    }

    @Test
    fun progressActivityTest() {
        makeClick(R.id.progress_game_btn)
        orientationLandscape()
        orientationLandscape()
        isViewDisplayed(R.id.progress_count_view)
        makePressBack()
    }

    @Test
    fun mainActivityTest() {
        // TODO
    }
}