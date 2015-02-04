package com.localhop.prefs;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;
import com.localhop.R;

public class PrefsActivity extends PreferenceActivity {

    @Override protected void onCreate(@Nullable final Bundle savedInstanceState) {
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, PrefsFragment.newInstance())
                .commit();
        super.onCreate(savedInstanceState);
    }

    public static class PrefsFragment extends PreferenceFragment {
        public Preference mTestTheRestPref;

        @NonNull public static PrefsFragment newInstance() {
            return new PrefsFragment();
        }


        @Override public void onCreate(@Nullable final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            mTestTheRestPref = findPreference(Prefs.TEST_THE_REST);
            mTestTheRestPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override public boolean onPreferenceClick(final Preference preference) {
                    Toast.makeText(getActivity(), "Lol", Toast.LENGTH_LONG).show();
                    return true;
                }
            });
        }
    }

}
