package com.touchplanet.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;

import static com.touchplanet.myapplication.DisplayUtils.initOverScan;

public class BootUpReceiver extends BroadcastReceiver {

    /*============================================================================================*/
    // Override Method

    @Override
    public void onReceive(Context context, Intent intent) {
        Rect overscan = initOverScan(context);
    }

    /*--------------------------------------------------------------------------------------------*/

}
