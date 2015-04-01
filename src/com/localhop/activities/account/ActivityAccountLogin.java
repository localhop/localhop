package com.localhop.activities.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.localhop.R;
import com.localhop.activities.BaseActivity;
import com.localhop.network.HttpRequest;
import com.localhop.network.HttpServerRequest;
import com.localhop.utils.ViewUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ActivityAccountLogin extends BaseActivity {

    private View mLoginView;
    private Button mLoginButton;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_login);

        /* Set up text fields */
        final EditText mEmailField = findView(R.id.et_login_email);
        final EditText mPasswordField = findView(R.id.et_login_password);

        final Button newAccount = findView(R.id.b_new_account);
        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(final View v) {
                startActivityFromClass(ActivityAccountName.class);
            }
        });

        /* Add click handler for the login button */
        mLoginButton = findView(R.id.b_login);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<NameValuePair> credentials = new ArrayList<NameValuePair>();
                credentials.add(new BasicNameValuePair("phoneNumber",mEmailField.getText().toString()));
                credentials.add(new BasicNameValuePair("password",   mPasswordField.getText().toString()));
                attemptLogin(credentials);
            }
        });
    }


    private void attemptLogin(List<NameValuePair> credentials) {
        new HttpServerRequest<Activity, String>(this, HttpRequest.POST, credentials) {
            @Override protected String onResponse(final String response) {
                return response;
            }
            @Override protected void onPostExecute(String response) {
                super.onPostExecute(response);
                Toast.makeText(ActivityAccountLogin.this, response, Toast.LENGTH_LONG).show();
            }
            @Override protected void onCancelled() {}
        }.execute("http://24.124.60.119/user/login");
    }
}

