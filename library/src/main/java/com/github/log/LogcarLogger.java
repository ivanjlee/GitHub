package com.github.log;

import android.util.Log;
/**
 * LogcarLogger
 *
 * @author  Ivan on 2020-04-15
 * @version v1.0
 * @since   v1.0
 */
public class LogcarLogger implements ILogger {

    @Override
    public void log(int level, String tag, String msg, Throwable throwable) {
        switch (level) {
            case LogLevel.VERBOSE:
                Log.v(tag, msg, throwable);
                break;
            case LogLevel.DEBUG:
                Log.d(tag, msg, throwable);
                break;
            case LogLevel.INFO:
                Log.i(tag, msg, throwable);
                break;
            case LogLevel.WARN:
                Log.w(tag, msg, throwable);
                break;
            case LogLevel.ERROR:
                Log.e(tag, msg, throwable);
                break;
            case LogLevel.ASSERT:
                Log.wtf(tag, msg, throwable);
                break;
            default:
        }
    }

    @Override
    public boolean isLoggable(String tag, int level) {
        return true;
    }
}
