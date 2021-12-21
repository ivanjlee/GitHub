package com.ivan.github.init;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.ivan.github.core.BaseConfig;

import java.util.Collections;
import java.util.List;

public class BaseConfigInit extends AbsInitializer<Void> {

    public static final String TAG = "BaseConfigInit";

    @NonNull
    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    public Void onCreate(Context context) {
        BaseConfig.init(context.getApplicationContext());
        return null;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
