package com.localhop.activities.account;

import android.app.Activity;
import android.content.Context;
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

public class ActivityAccountRegister extends BaseActivity {

    private final int ERR_INVALID_PHONE = 1<<1;
    private final int ERR_MISSING_FIELD = 1<<2;
    private final int ERR_PASS_NO_MATCH = 1<<3;
    private Context context;
    EditText firstNameTextField;
    EditText lastNameTextField;
    EditText phoneTextField;
    EditText passTextField;
    EditText confirmPassTextField;
    Button registerButton;
    Button backToLoginButton;
    String firstName;
    String lastName;
    String phone;
    String password;
    String confirmPassword;

    /**
     * Ensure all fields set by the user are valid
     */
    private int validateFields() {
        int errors = 0;

        firstName = firstNameTextField.getText().toString();
        lastName = lastNameTextField.getText().toString();
        phone = phoneTextField.getText().toString();
        password = passTextField.getText().toString();
        confirmPassword = confirmPassTextField.getText().toString();

        if (firstName == null || firstName.isEmpty()
            ||  lastName == null || lastName.isEmpty()
            ||  phone == null || phone.isEmpty()
            ||  password == null || password.isEmpty()
            ||  confirmPassword == null || confirmPassword.isEmpty()) {
            errors |= ERR_MISSING_FIELD;
        }
        if (!password.equals(confirmPassword)) {
            errors |= ERR_PASS_NO_MATCH;
        }
        if (phone == null || phone.length() < 10) {
            errors |= ERR_INVALID_PHONE;
        }

        return errors;
    }

    private void toast(String message) {
        Toast.makeText(ActivityAccountRegister.this, message, Toast.LENGTH_LONG).show();
    }

    private void toastInputErrors(int errors) {
        String messages = "";
        if ((errors & ERR_INVALID_PHONE) != 0) {
            messages += "- Please enter a valid phone number\n";
        }
        if ((errors & ERR_MISSING_FIELD) != 0) {
            messages += "- Please fill in all fields\n";
        }
        if ((errors & ERR_PASS_NO_MATCH) != 0) {
            messages += "- Passwords do not match\n";
        }
        messages = messages.substring(0, messages.length()-1);
        toast(messages);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_register);
        context = getApplicationContext();

        firstNameTextField = findView(R.id.et_register_firstname);
        lastNameTextField = findView(R.id.et_register_lastname);
        phoneTextField = findView(R.id.et_register_phone);
        passTextField = findView(R.id.et_register_password);
        confirmPassTextField = findView(R.id.et_register_confirm_password);
        registerButton = findView(R.id.b_register);
        backToLoginButton = findView(R.id.b_back_to_login);

        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int error = validateFields();
                if (error == 0) {
                    List<NameValuePair> credentials = new ArrayList<NameValuePair>();
                    credentials.add(new BasicNameValuePair("firstName",
                            firstNameTextField.getText().toString()));
                    credentials.add(new BasicNameValuePair("lastName",
                            lastNameTextField.getText().toString()));
                    credentials.add(new BasicNameValuePair("phoneNumber",
                            phoneTextField.getText().toString()));
                    toast(phoneTextField.getText().toString());
                    credentials.add(new BasicNameValuePair("password",
                            passTextField.getText().toString()));
                    attemptRegister(credentials);
                } else {
                    toastInputErrors(error);
                }
            }
        });

        if (backToLoginButton != null) {
            backToLoginButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityFromClass(ActivityAccountLogin.class);
                }
            });
        }
    }

    private void attemptRegister(List<NameValuePair> credentials) {
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

                    if (!jresponse.getString("text").isEmpty() &&
                        jresponse.getString("text").equals("success")) {
                        startActivityFromClass(ActivityAccountLogin.class);
                    } else if (!jresponse.getString("error").isEmpty()){
                        toast(jresponse.getString("error"));
                    } else {
                        toast("An unexpected error occurred. Please try again later");
                    }
                }
                catch (JSONException err) {
                    System.out.println(err.getMessage());
                }
            }

            @Override
            protected void onCancelled() {}
        }.execute("http://24.124.60.119/user/");
    }
}

