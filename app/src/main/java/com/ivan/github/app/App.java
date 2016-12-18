package com.ivan.github.app;

import android.app.Application;
import android.content.res.Resources;

/**
 * a class used to hold some constant values
 *
 * @author  Ivan at 2016-12-18  19:12
 * @version v0.1
 * @since   v0.1
 */

public class App {

    private static Application application;

    public static float screenWithPixels;
    public static float screenHeightPixels;

    static {
        screenWithPixels = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeightPixels = Resources.getSystem().getDisplayMetrics().heightPixels;
    }


    private App() {

    }

    static void init(Application app) {
        application = app;
    }

}
