package ru.alexandra.forum;


import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBackUnconditionally;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestApp {

    @Rule
    public final ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void testUI(){
        onView(withId(R.id.login_email))
                .check(matches(isDisplayed()))
                .check(matches(withText("")))
                .perform(typeText("admin"))
                .check(matches(withText("admin")));

        onView(withId(R.id.login_sign_in))
                .perform(click());

        onView(withId(R.id.main_menu_add))
                .perform(click());

        onView(withId(R.id.creation_header))
                .check(matches(isDisplayed()))
                .check(matches(withHint("Название")))
                .perform(typeText("Header"))
                .check(matches(withText("Header")));

        onView(withId(R.id.creation_text))
                .check(matches(isDisplayed()))
                .check(matches(withHint("Текст")))
                .perform(typeText("Text"))
                .check(matches(withText("Text")));
    }
}
