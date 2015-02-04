package com.localhop.prefs;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import com.localhop.R;

public final class Prefs {

    @NonNull static String TEST_THE_REST;

    private Prefs() {}

    public static void initPrefs(@NonNull final Context context) {
        final Resources r = context.getResources();

        TEST_THE_REST = r.getString(R.string.pref_test_rest);
    }
}
