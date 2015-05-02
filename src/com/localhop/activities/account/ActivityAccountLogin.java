package com.localhop.activities.account;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.localhop.R;
import com.localhop.activities.ActivityMain;
import com.localhop.activities.BaseActivity;
import com.localhop.network.HttpRequest;
import com.localhop.network.HttpServerRequest;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;

public class ActivityAccountLogin extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_login);

        final EditText phoneTextField = findView(R.id.et_login_phone);
        final EditText passTextField = findView(R.id.et_login_password);
        final Button registerButton = findView(R.id.b_new_account);
        final Button loginButton = findView(R.id.b_login);

        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                startActivityFromClass(ActivityAccountName.class);
            }
        });

        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                List<NameValuePair> credentials = new ArrayList<NameValuePair>();
                credentials.add(new BasicNameValuePair("phoneNumber",
                        phoneTextField.getText().toString()));
                credentials.add(new BasicNameValuePair("password",
                        passTextField.getText().toString()));
                attemptLogin(credentials);
            }
        });
    }

    private void toast(String message) {
        Toast.makeText(ActivityAccountLogin.this, message, Toast.LENGTH_LONG).show();
    }

    private void attemptLogin(List<NameValuePair> credentials) {
        new HttpServerRequest<Activity, String>(this, HttpRequest.POST, credentials) {
            @Override
            protected String onResponse(final String response) {
                return response;
            }

            @Override
            protected void onPostExecute(String response) {
                super.onPostExecute(response);
                try {
                    JSONObject jresponse = new JSONObject(new JSONTokener(response));

                    if (jresponse.getString("text").isEmpty()) {
                        toast(jresponse.getString("error"));
                    } else {
                        JSONObject user = jresponse.getJSONObject("text");
                        SharedPreferences preferences = getApplicationContext()
                                .getSharedPreferences(String.valueOf(R.string.localhop_pref), 0);
                        preferences.edit().putInt("userID", user.getInt("id"));
                        startActivityFromClass(ActivityMain.class);
                    }
                }
                catch (JSONException err) {
                    System.out.println(err.getMessage());
                }
            }

            @Override
            protected void onCancelled() {}
        }.execute("http://24.124.60.119/user/login");
    }
}

