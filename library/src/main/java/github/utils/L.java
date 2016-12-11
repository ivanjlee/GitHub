package github.utils;

import android.util.Log;

import github.design.BuildConfig;

/**
 * A tool class for Logcat
 *
 * @author      Ivan at 2016-12-11  17:13
 * @version     v1.0
 * @since       v1.0
 */

public class L {

    public static final int VERBOSE = 2;
    public static final int DEBUG   = 3;
    public static final int INFO    = 4;
    public static final int WARN    = 5;
    public static final int ERROR   = 6;
    public static final int ASSERT  = 7;

    private L() {

    }

    private static boolean sLogVerboseEnable = false;
    private static boolean sLogDebugEnable = false;
    private static boolean sLogInfoEnable = false;
    private static boolean sLogWarnEnable = false;
    private static boolean sLogErrorEnable = false;
    private static boolean sLogAssertEnable = false;

    public static void setDebugLog(int level) {
        if (!BuildConfig.DEBUG) {
            return;
        }
        if (level < VERBOSE || level > ASSERT) {
            System.out.println("check your parameter please");
            return;
        }
        if (level == ASSERT) {
            sLogAssertEnable = true;
            sLogErrorEnable = true;
            sLogWarnEnable = true;
            sLogInfoEnable = true;
            sLogDebugEnable = true;
            sLogVerboseEnable = true;
        } else if (level == ERROR) {
            sLogErrorEnable = true;
            sLogWarnEnable = true;
            sLogInfoEnable = true;
            sLogDebugEnable = true;
            sLogVerboseEnable = true;
        } else if (level == WARN) {
            sLogWarnEnable = true;
            sLogInfoEnable = true;
            sLogDebugEnable = true;
            sLogVerboseEnable = true;
        } else if (level == INFO) {
            sLogInfoEnable = true;
            sLogDebugEnable = true;
            sLogVerboseEnable = true;
        }else if (level == DEBUG) {
            sLogDebugEnable = true;
            sLogAssertEnable = true;
        } if (level == VERBOSE){
            sLogAssertEnable = true;
        }
    }

    public static int v(String tag, String msg) {
        if (sLogVerboseEnable) {
            return Log.v(tag, msg);
        }
        return VERBOSE;
    }

    public static int v(String tag, String msg, Throwable tr) {
        if (sLogVerboseEnable) {
            return Log.v(tag, msg, tr);
        }
        return VERBOSE;
    }

    public static int d(String tag, String msg) {
        if (sLogDebugEnable) {
            return Log.d(tag, msg);
        }
        return DEBUG;
    }

    public static int d(String tag, String msg, Throwable tr) {
        if (sLogDebugEnable) {
            return Log.d(tag, msg, tr);
        }
        return DEBUG;
    }

    public static int i(String tag, String msg) {
        if (sLogInfoEnable) {
            return Log.i(tag, msg);
        }
        return INFO;
    }

    public static int i(String tag, String msg, Throwable tr) {
        if (sLogInfoEnable) {
            return Log.i(tag, msg, tr);
        }
        return INFO;
    }

    public static int w(String tag, String msg) {
        if (sLogWarnEnable) {
            return Log.w(tag, msg);
        }
        return INFO;
    }

    public static int w(String tag, String msg, Throwable tr) {
        if (sLogWarnEnable) {
            return Log.w(tag, msg, tr);
        }
        return WARN;
    }

    public static int w(String tag, Throwable tr) {
        if (sLogWarnEnable) {
            return Log.w(tag, tr);
        }
        return WARN;
    }

    public static int e(String tag, String msg) {
        if (sLogErrorEnable) {
            return Log.e(tag, msg);
        }
        return ERROR;
    }

    public static int e(String tag, String msg, Throwable tr) {
        if (sLogErrorEnable) {
            return Log.e(tag, msg, tr);
        }
        return ERROR;
    }

    public static int wtf(String tag, String msg) {
        if (sLogAssertEnable) {
            return Log.wtf(tag, msg);
        }
        return ASSERT;
    }

    public static int wtf(String tag, Throwable tr) {
        if (sLogAssertEnable) {
            return Log.wtf(tag, tr);
        }
        return ASSERT;
    }

    public static int wtf(String tag, String msg, Throwable tr) {
        if (sLogAssertEnable) {
            return Log.wtf(tag, msg, tr);
        }
        return ASSERT;
    }
}
