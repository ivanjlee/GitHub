package com.github.utils;

import android.content.Context;
import android.content.res.Resources;

/**
 * some tools
 *
 * @author  Ivan at 2016-12-18  18:46
 * @version v0.1
 * @since   v0.1
 */

public class Utils {

    public static int px2dp(Context context, float px) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px/scale + 0.5F);
    }

    public static int px2dp(float px) {
        float scale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (px/scale + 0.5F);
    }

    public static int dp2px(float dp) {
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5F);
    }

    public static int dp2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5F);
    }

    public static int px2sp(Context context, float px) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / fontScale + 0.5f);
    }

    public static int px2sp(float px) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (px / fontScale + 0.5f);
    }

    public static int sp2px(Context context, float sp) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * fontScale + 0.5f);
    }

    public static int sp2px(float sp) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (sp * fontScale + 0.5f);
    }
}
