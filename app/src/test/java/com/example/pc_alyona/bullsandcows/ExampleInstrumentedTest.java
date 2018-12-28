package com.example.pc_alyona.bullsandcows;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Pair;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.pc_alyona.bullsandcows", appContext.getPackageName());
    }

    @Test
    public void testGameState() {
        // Context of the app under test.
        GameState testGame0 = new GameState(10);
        testGame0.generateNumber();
        List<Integer> number = testGame0.getNumber();
        StringBuilder sb = new StringBuilder();
        for (Integer s : number) {
            sb.append(Integer.toString(s));
        }
        Pair<Integer, Integer> testPair = testGame0.checkNumber(sb.toString());
        assertTrue(testPair.first == 10);
    }

}
