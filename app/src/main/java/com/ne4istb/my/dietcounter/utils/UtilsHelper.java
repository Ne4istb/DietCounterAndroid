package com.ne4istb.my.dietcounter.utils;

import android.util.Log;

public class UtilsHelper {

    public static final boolean DEBUG = true;

    public static final String LOG_TAG = "DietCounterApp";

    public static final void printException(Throwable e) {
        if (DEBUG) {
            Log.e(LOG_TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    public static final void println(String line) {

        if (DEBUG) {
            Log.d(LOG_TAG, line);
        }
    }
}
