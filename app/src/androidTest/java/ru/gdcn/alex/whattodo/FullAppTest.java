package ru.gdcn.alex.whattodo;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.gdcn.alex.whattodo.R;
import ru.gdcn.alex.whattodo.main.MainActivity;

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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FullAppTest {

    @Rule
    public final ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        creationActivityTest();
        Matcher<View> matcher = childAtPosition(withId(R.id.notes_fragment_recycler_view), 1);
        onView(matcher).perform(swipeLeft());
        pressBackUnconditionally();
        Assert.assertTrue(mActivityTestRule.getActivity().isFinishing());
    }

    private void creationActivityTest() {
        onView(ViewMatchers.withId(R.id.main_fab_create)).perform(click());

        onView(ViewMatchers.withId(R.id.creation_note_header))
                .check(matches(isDisplayed()))
                .check(matches(withText("")))
                .perform(typeText("AutoTest"));

        onView(ViewMatchers.withId(R.id.creation_note_fragment_text))
                .check(matches(isDisplayed()))
                .check(matches(withText("")))
                .perform(typeText("Created by AutoTest.\n1\n2\n3"));

        onView(ViewMatchers.withId(R.id.creation_bottom_menu_list))
                .perform(click());

        onView(childAtPosition(childAtPosition(withId(R.id.creation_list_fragment_recycler), 0), 0))
                .check(matches(isNotChecked()))
                .perform(click())
                .check(matches(isChecked()));

        onView(ViewMatchers.withId(R.id.creation_bottom_menu_note))
                .perform(click());

        onView(ViewMatchers.withId(R.id.creation_note_fragment_text))
                .check(matches(isDisplayed()))
                .check(matches(withText("Created by AutoTest.\n1\n2\n3")));

        onView(ViewMatchers.withId(R.id.creation_bottom_menu_list))
                .perform(click());

        onView(childAtPosition(childAtPosition(withId(R.id.creation_list_fragment_recycler), 0), 0))
                .check(matches(isNotChecked()));

        onView(childAtPosition(withId(R.id.creation_list_fragment_recycler), 4))
                .perform(click());

        onView(childAtPosition(childAtPosition(withId(R.id.creation_list_fragment_recycler), 4), 1))
                .check(matches(withText("")));

        onView(ViewMatchers.withId(R.id.creation_bottom_menu_note))
                .perform(click());

        onView(ViewMatchers.withId(R.id.creation_note_fragment_text))
                .check(matches(isDisplayed()))
                .check(matches(withText("Created by AutoTest.\n1\n2\n3\n")));

        closeSoftKeyboard();
        pressBackUnconditionally();
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
