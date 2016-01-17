package com.skoczo.helpers;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by skoczo on 17.01.16.
 */
public class UiHelpers {

    public static void showToast(final Activity activity, final String message, final int duration) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity.getApplicationContext(), message, duration).show();
            }
        });
    }

    public static void showToast(final Activity activity, final int message, final int duration) {
        showToast(activity, activity.getResources().getString(message), duration);
    }
}
