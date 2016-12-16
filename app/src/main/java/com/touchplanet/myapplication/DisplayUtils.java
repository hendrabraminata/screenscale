package com.touchplanet.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static android.content.Context.MODE_PRIVATE;


public final class DisplayUtils {

    /*============================================================================================*/
    // Member

    public static final String DISPLAY_OUTPUT_MANAGER = "android.os.DisplayOutputManager";
    public static final String PREF = "PREF";
    public static final String OVER_SCAN_TOP = "OVER_SCAN_TOP";
    public static final String OVER_SCAN_BOTTOM = "OVER_SCAN_BOTTOM";
    public static final String OVER_SCAN_LEFT = "OVER_SCAN_LEFT";
    public static final String OVER_SCAN_RIGHT = "OVER_SCAN_RIGHT";
    public static final String DISPLAY = "DISPLAY";

    public static final int DISPLAY_OVERSCAN_LEFT = 2;
    public static final int DISPLAY_OVERSCAN_RIGHT = 3;
    public static final int DISPLAY_OVERSCAN_TOP = 4;
    public static final int DISPLAY_OVERSCAN_BOTTOM = 5;

    public static final int MAIN_DISPLAY = 0;
    public static final int AUX_DISPLAY = 1;

    /*--------------------------------------------------------------------------------------------*/


    /*============================================================================================*/
    // Other function


    public static Rect getOverScan(int display) {
        if (display < 0 || getDisplayNumber() == null || display > getDisplayNumber() - 1)
            return null;

        try {
            Class<?> classDisplayOutputManager = Class.forName(DISPLAY_OUTPUT_MANAGER);
            Method method = classDisplayOutputManager.getDeclaredMethod("getOverScan", int.class);
            Object displayOutputManager = classDisplayOutputManager.newInstance();

            return (Rect) method.invoke(displayOutputManager, display);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Integer getDisplayNumber() {
        try {
            Class<?> classDisplayOutputManager = Class.forName(DISPLAY_OUTPUT_MANAGER);
            Method method = classDisplayOutputManager.getDeclaredMethod("getDisplayNumber");
            Object displayOutputManager = classDisplayOutputManager.newInstance();

            return (Integer) method.invoke(displayOutputManager);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void setOverScan(int direction, int value, int display) {
        if (display < 0 || getDisplayNumber() == null || display > getDisplayNumber() - 1)
            return;

        try {
            Class<?> classDisplayOutputManager = Class.forName(DISPLAY_OUTPUT_MANAGER);
            Method method = classDisplayOutputManager.getDeclaredMethod("setOverScan", int.class, int.class, int.class);
            Object displayOutputManager = classDisplayOutputManager.newInstance();

            method.invoke(displayOutputManager, display, direction, value);

        } catch (ClassNotFoundException | InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static Rect initOverScan(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF, MODE_PRIVATE);
        int display = prefs.getInt(DISPLAY, MAIN_DISPLAY);

        Rect overscan = getOverScan(display);

        overscan.right = prefs.getInt(OVER_SCAN_RIGHT, overscan.right);
        overscan.left = prefs.getInt(OVER_SCAN_LEFT, overscan.left);
        overscan.top = prefs.getInt(OVER_SCAN_TOP, overscan.top);
        overscan.bottom = prefs.getInt(OVER_SCAN_BOTTOM, overscan.bottom);

        setOverScan(DISPLAY_OVERSCAN_LEFT, overscan.left, display);
        setOverScan(DISPLAY_OVERSCAN_RIGHT, overscan.right, display);
        setOverScan(DISPLAY_OVERSCAN_TOP, overscan.top, display);
        setOverScan(DISPLAY_OVERSCAN_BOTTOM, overscan.bottom, display);

        return overscan;
    }

    /*--------------------------------------------------------------------------------------------*/
}
