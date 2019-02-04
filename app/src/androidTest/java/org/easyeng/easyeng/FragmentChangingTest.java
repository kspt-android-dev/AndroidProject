package org.easyeng.easyeng;


import android.view.View;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class FragmentChangingTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule
            = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void bottomNavigation() {
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        //Click on puzzles
        Espresso.onView(withId(R.id.action_puzzles)).perform(ViewActions.click());
        View fragment_puzzles = mActivityRule.getActivity().findViewById(R.id.fragment_puzzles);
        View fragment_home = mActivityRule.getActivity().findViewById(R.id.fragment_home);
        View fragment_words = mActivityRule.getActivity().findViewById(R.id.fragment_words);

        Assert.assertNull(fragment_home);
        Assert.assertNotNull(fragment_puzzles);
        Assert.assertNull(fragment_words);

        //Click on home
        Espresso.onView(withId(R.id.action_home)).perform(ViewActions.click());
        fragment_puzzles = mActivityRule.getActivity().findViewById(R.id.fragment_puzzles);
        fragment_home = mActivityRule.getActivity().findViewById(R.id.fragment_home);
        fragment_words = mActivityRule.getActivity().findViewById(R.id.fragment_words);

        Assert.assertNotNull(fragment_home);
        Assert.assertNull(fragment_puzzles);
        Assert.assertNull(fragment_words);

        //Click on words
        Espresso.onView(withId(R.id.action_words)).perform(ViewActions.click());
        fragment_puzzles = mActivityRule.getActivity().findViewById(R.id.fragment_puzzles);
        fragment_home = mActivityRule.getActivity().findViewById(R.id.fragment_home);
        fragment_words = mActivityRule.getActivity().findViewById(R.id.fragment_words);

        Assert.assertNull(fragment_home);
        Assert.assertNull(fragment_puzzles);
        Assert.assertNotNull(fragment_words);
    }
}