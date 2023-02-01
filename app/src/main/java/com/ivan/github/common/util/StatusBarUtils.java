package com.ivan.github.common.util;

import android.app.Activity;
import android.view.Window;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;

/**
 * com.ivan.github.common.util
 * <p>
 *
 * @author Ivan J. Lee on 2023-02-01 23:22
 * @since v1.0
 */
public class StatusBarUtils {

    public static void setStatusBarColorRes(Activity activity, @ColorRes int colorId) {
        Window window = activity.getWindow();
        window.setStatusBarColor(activity.getResources().getColor(colorId));
    }

    public static void setStatusBarColor(Activity activity, @ColorInt int color) {
        Window window = activity.getWindow();
        window.setStatusBarColor(color);
    }

}
