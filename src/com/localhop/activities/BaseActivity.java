package com.localhop.activities;

import android.app.Activity;
import android.content.Intent;
import com.localhop.utils.ActivityUtils;

public abstract class BaseActivity extends Activity {

    public <V> V findView(final int id) {
        return ActivityUtils.findViewById(this, id);
    }

    protected boolean startActivityFromClass(Class<? extends Activity> cls) {
        return ActivityUtils.startActivityFromClass(this, cls);
    }

}