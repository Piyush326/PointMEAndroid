package com.app.pointme.pointme;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app.pointme.pointme.databackend.DbHelper;
import com.app.pointme.pointme.networktask.UrlConnection;
import com.app.pointme.pointme.utils.FormField;
import com.app.pointme.pointme.utils.ValidationForm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

public class ActivityLogin extends PmBaseActivity {
    @Bind(R.id.login_phone)
    EditText phone;

    @Bind(R.id.login_password)
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        if (BuildConfig.DEBUG) {
            phone.setText("8750913537");
            password.setText("qwerty");
        }

        validationForm = ValidationForm.create();
        validationForm.add(FormField.create(phone, FormField.TYPE.SIMPLE));
        validationForm.add(FormField.create(password, FormField.TYPE.PASSWORD));

    }

    @Override
    protected void onBackendConnected(DbHelper dbHelper) {

    }

    @OnClick(R.id.login_login)
    public void loginClick(View v) {
        switch (v.getId()) {
            case R.id.login_login:
                if (!validationForm.validate())
                    return;
                new GetResponse().execute();
//
//                launch(ActivityHome.class).launchActivity();
                break;
        }
    }

    private class GetResponse extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            return new UrlConnection().getDeals();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(ActivityLogin.this, s, Toast.LENGTH_SHORT).show();
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray contacts = jsonObject.optJSONArray("contacts");
                Toast.makeText(ActivityLogin.this, "" + contacts.length(), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
