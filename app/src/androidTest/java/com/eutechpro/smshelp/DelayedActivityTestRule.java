package com.eutechpro.smshelp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.test.rule.ActivityTestRule;

public class DelayedActivityTestRule <T extends Activity> extends ActivityTestRule<T> {

    private final OnBeforeActivityLaunchedListener<T> onBeforeActivityLaunchedListener;

    public DelayedActivityTestRule(Class<T> activityClass, @NonNull OnBeforeActivityLaunchedListener<T> listener) {
        super(activityClass, true, false);
        onBeforeActivityLaunchedListener = listener;
    }

    @Override
    protected void afterActivityLaunched() {
        super.afterActivityLaunched();
    }

    @Override
    protected void beforeActivityLaunched() {
        super.beforeActivityLaunched();
        onBeforeActivityLaunchedListener.beforeActivityLaunched(getActivity());
    }

    public interface OnBeforeActivityLaunchedListener <T> {
        void beforeActivityLaunched(T activity);
    }
}