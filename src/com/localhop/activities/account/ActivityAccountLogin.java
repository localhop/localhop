package com.localhop.activities.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import com.localhop.R;

public class ActivityAccountLogin extends Activity {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_login);

        final Button newAccount = findView(R.id.b_new_account);
        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(final View v) {
                startActivityFromClass(ActivityAccountName.class);
            }
        });
    }

    public <V extends View> V findView(final int resId) {
        //noinspection unchecked
        return (V) findViewById(resId);
    }

    private boolean startActivityFromClass(Class<? extends Activity> cls) {
        final Intent i = new Intent(this, cls);
        startActivity(i);
        return true;
    }

}

