package com.localhop.prefs;

import android.app.Activity;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;
import com.localhop.R;
import com.localhop.network.HttpRequest;
import com.localhop.network.HttpServerRequest;
import org.json.JSONException;
import org.json.JSONObject;

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
                    newGetRequest().execute(HttpServerRequest.DUMMY_URL);
                    return true;
                }
            });
        }

        private HttpServerRequest<Activity, String> newGetRequest() {
            return new HttpServerRequest<Activity, String>(getActivity(), HttpRequest.GET) {
                @Override protected String onResponse(final String response) {
                    try {
                        return new JSONObject(response).getString("ip");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return "ERROR: BAD THINGS HAPPENED";
                    }
                }

                @Override protected void onPostExecute(String message) {
                    super.onPostExecute(message);
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                }
            };
        }
    }

}
