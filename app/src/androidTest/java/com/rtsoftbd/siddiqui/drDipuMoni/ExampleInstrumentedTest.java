/*
 * Copyright By Noor Nabiul Alam Siddiqui on Behalf of RTsoftBD
 *  www.fb.com/sazal.ns
 *  ------------------------------------
 *    Name:     DipuMoni
 *    Created at:  7/5/17 5:28 PM
 *    Updated at: 6/7/17 12:18 PM
 *  ------------------------------------
 */

package com.rtsoftbd.siddiqui.drDipuMoni;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.rtsoftbd.siddiqui.drDipuMoni", appContext.getPackageName());
    }
}
