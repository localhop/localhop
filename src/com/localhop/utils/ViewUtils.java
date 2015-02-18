package com.localhop.utils;

import android.support.annotation.NonNull;
import android.view.View;

public final class ViewUtils {

    private ViewUtils() {}

    public static <V> V findViewById(@NonNull final View view, final int id) {
        //noinspection unchecked
        return (V) view.findViewById(id);
    }

}
