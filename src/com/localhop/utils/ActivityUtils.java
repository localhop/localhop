package com.localhop.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

public final class ActivityUtils {

    private ActivityUtils() {}

    public static <V> V findViewById(@NonNull final Activity activity, final int id) {
        //noinspection unchecked
        return (V) activity.findViewById(id);
    }

    public static boolean startActivityFromClass(@NonNull final Context context, @NonNull final Class<? extends Activity> cls) {
        final Intent i = new Intent(context, cls);
        context.startActivity(i);
        return true;
    }
}
