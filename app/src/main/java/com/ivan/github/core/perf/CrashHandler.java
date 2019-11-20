package com.ivan.github.core.perf;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.ivan.github.R;

/**
 * CrashHandler to handle crashes
 *
 * @author  Ivan on 2017/6/1 10:15.
 * @version v0.1
 * @since   v1.0
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    @SuppressLint("StaticFieldLeak")
    private static volatile CrashHandler sInstance;
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private Context mContext;
    private ICrashReporter mReporter;

    private static final String TAG = "CrashHandler";

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        if (sInstance == null) {
            synchronized (CrashHandler.class) {
                if (sInstance == null) {
                    sInstance = new CrashHandler();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context) {
        this.mContext = context;
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void setReporter(ICrashReporter mReporter) {
        this.mReporter = mReporter;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        mReporter.saveLog(t,e);
        mReporter.report();
        mReporter.sendNotification(e);
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(t, e);
        }
    }

    /**
     * send a notification to show a crash information
     */
    private void reportToLocal(Throwable e) {
        if (mReporter.isReportToLocal()) {

        }
    }
}