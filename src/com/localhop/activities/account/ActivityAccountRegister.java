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
    final EditText firstNameTextField = findView(R.id.et_register_firstname);
    final EditText lastNameTextField = findView(R.id.et_register_lastname);
    final EditText phoneTextField = findView(R.id.et_register_phone);
    final EditText passTextField = findView(R.id.et_register_password);
    final EditText confirmPassTextField = findView(R.id.et_register_confirm_password);
    final Button registerButton = findView(R.id.b_new_account);
    String firstName;
    String lastName;
    String phone;
    String password;
    String confirmPassword;

    /**
     * Ensure all fields set by the user are valid
     */
    private int validateFields() {
        int status = 0;

        firstName = firstNameTextField.getText().toString();
        lastName = lastNameTextField.getText().toString();
        phone = lastNameTextField.getText().toString();
        password = passTextField.getText().toString();
        confirmPassword = confirmPassTextField.getText().toString();

        if (firstName == null || firstName.isEmpty()
            ||  lastName == null || lastName.isEmpty()
            ||  phone == null || phone.isEmpty()
            ||  password == null || password.isEmpty()
            ||  confirmPassword == null || confirmPassword.isEmpty()) {
            status |= ERR_MISSING_FIELD;
        }
        if (!password.equals(confirmPassword)) {
            status |= ERR_PASS_NO_MATCH;
        }
        if (phone.length() < 10) {
            status |= ERR_INVALID_PHONE;
        }

        return status;
    }

    private void toast(String message) {
        Toast.makeText(ActivityAccountRegister.this, message, Toast.LENGTH_LONG).show();
    }

    private void toastInputErrors(int errors) {
        if ((errors & ERR_INVALID_PHONE) != 0) {
            toast("Please enter a valid phone number");
        }
        if ((errors & ERR_MISSING_FIELD) != 0) {
            toast("Please fill in all fields");
        }
        if ((errors & ERR_PASS_NO_MATCH) != 0) {
            toast("Passwords do not match");
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_login);
        context = getApplicationContext();

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
                    credentials.add(new BasicNameValuePair("password",
                            passTextField.getText().toString()));
                    attemptRegister(credentials);
                } else {
                    toastInputErrors(error);
                }
            }
        });
    }

    private void attemptRegister(List<NameValuePair> credentials) {
        toast("All fields are valid. Registering ...");
//        new HttpServerRequest<Activity, String>(this, HttpRequest.POST, credentials) {
//            @Override
//            protected String onResponse(final String response) {
//                return response;
//            }
//
//            @Override
//            protected void onPostExecute(String response) {
//                super.onPostExecute(response);
//                try {
//                    JSONObject jresponse = new JSONObject(new JSONTokener(response));
//
//                    if (jresponse.getString("text").isEmpty()) {
//                        toast(jresponse.getString("error"));
//                    } else {
//                        JSONObject user = jresponse.getJSONObject("text");
//                        SharedPreferences preferences = context.getSharedPreferences(
//                                getString(R.string.localhop_pref), Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = preferences.edit();
//                        editor.putInt(getString(R.string.user_id_key), user.getInt("id"));
//                        editor.commit();
//                        startActivityFromClass(ActivityMain.class);
//                    }
//                }
//                catch (JSONException err) {
//                    System.out.println(err.getMessage());
//                }
//            }
//
//            @Override
//            protected void onCancelled() {}
//        }.execute("http://24.124.60.119/user/login");
    }
}

