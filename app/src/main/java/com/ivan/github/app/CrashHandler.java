package com.ivan.github.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import com.ivan.github.BuildConfig;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import github.net.NetworkUtils;

/**
 * CrashHandler to handle crashes
 *
 * @author  Ivan on 2017/6/1 10:15.
 * @version v0.1
 * @since   v1.0
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private volatile CrashHandler mInstance;
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private Context mContext;

    private static final String TAG = "CrashHandler";
    private static String LOG_PATH = App.getApplication().getExternalFilesDir(null) + "/crash";

    private CrashHandler() {
    }

    public CrashHandler getInstance() {
        if (mInstance == null) {
            synchronized (CrashHandler.class) {
                if (mInstance == null) {
                    mInstance = new CrashHandler();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        this.mContext = context;
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(t, e);
        }
        dumpLog(e);
        report(e);
    }

    /**
     * save log to sdcard
     */
    @SuppressLint("SimpleDateFormat")
    private void dumpLog(Throwable e) {
        @SuppressLint("SimpleDateFormat")
        String filename = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(System.currentTimeMillis()) + ".log";
        File file = new File(LOG_PATH, filename);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            FileWriter fw = new FileWriter(filename, true);
            fw.write("crash at: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
            fw.write(e.getMessage());
            fw.write("\nCause: \n");
            Throwable cause = e.getCause();
            while (cause != null) {
                fw.write(cause.toString());
            }
            fw.flush();
            fw.close();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    /**
     * report exception
     */
    private void report(Throwable e) {
        String detail = getDetails(e);
        // TODO: 2017/6/1 report details
    }

    /**
     * get crash details
     */
    @SuppressLint("SimpleDateFormat")
    private String getDetails(Throwable e) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("time: ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()))
                .append("version code: ").append(BuildConfig.VERSION_CODE).append('\n')
                .append("version name: ").append(BuildConfig.VERSION_NAME).append('\n')
                .append("flavor: ").append(BuildConfig.FLAVOR).append('\n')
                .append("os version: ").append(Build.VERSION.SDK_INT).append('\n')
                .append("vendor: ").append(Build.MODEL).append('\n')
                .append("brand: ").append(Build.BRAND).append('\n')
                .append("message: ").append(e.getMessage()).append('\n')
                .append("cause: ").append(e.getCause()).append('\n');
        return stringBuilder.toString();
    }
}
