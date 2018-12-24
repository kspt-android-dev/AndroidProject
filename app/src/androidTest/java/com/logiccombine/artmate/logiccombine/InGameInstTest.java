package com.logiccombine.artmate.logiccombine;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import org.junit.Test;

import static org.junit.Assert.*;

public class InGameInstTest {

    @Test
    public void updateAndReadScore(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.logiccombine.artmate.logiccombine", appContext.getPackageName());
    }

}