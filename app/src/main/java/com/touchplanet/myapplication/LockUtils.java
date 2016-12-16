package com.touchplanet.myapplication;

import java.io.IOException;

public class LockUtils {

    /*============================================================================================*/
    // Constructor

    private LockUtils() {
        throw new AssertionError("No instances.");
    }

    /*--------------------------------------------------------------------------------------------*/


    /*============================================================================================*/
    // Method

    public static void showSystemUi() {
        try {
            Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "pm enable com.android.systemui"});
            proc.waitFor();
            proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "am startservice -n com.android.systemui/.SystemUIService"});
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void hideSystemUi() {
        try {
            Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "service call activity 42 s16 com.android.systemui"});
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*--------------------------------------------------------------------------------------------*/

}
