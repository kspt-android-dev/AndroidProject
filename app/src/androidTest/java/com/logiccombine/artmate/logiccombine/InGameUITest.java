package com.logiccombine.artmate.logiccombine;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class InGameUITest {

    @Test
    public void updateAndReadScore(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.logiccombine.artmate.logiccombine", appContext.getPackageName());
    }
}