package ru.polytech.course.pashnik.lines;


import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.polytech.course.pashnik.lines.Activities.MainActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UITests {

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clicks() {
        onView(withId(R.id.exit))
                .check(matches(isDisplayed()));
        onView(withId(R.id.recordds))
                .check(matches(isDisplayed()));
        onView(withId(R.id.startBtn))
                .check(matches(isDisplayed()));
    }

}
