package com.app.pointme.pointme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.app.pointme.pointme.databackend.DbHelper;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

public class ActivityFaceBook extends PmBaseActivity {
    CallbackManager callbackManager;
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String GENDER = "gender";


    @Override
    public String getHeading() {
        return "Facebook Login";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_book);
        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, fbCallback);
//        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
    }

    @Override
    protected void onBackendConnected(DbHelper dbHelper) {

    }

    FacebookCallback<LoginResult> fbCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            // App code
            getUserProfile(loginResult);
        }

        @Override
        public void onCancel() {
            // App code
            setResult(RESULT_CANCELED);
            finish();
        }

        @Override
        public void onError(FacebookException exception) {
            // App code
            showApologies();
            setResult(RESULT_CANCELED);
            finish();
        }
    };

    public void getUserProfile(LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                        Log.v("LoginActivity", response.toString());
                        Log.v("LoginActivity", object.toString());
                        finishFacebookLogin(object);
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender, birthday, picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void finishFacebookLogin(JSONObject jsonObject) {
        // request the api to do facebook login
        // and then finish the activity stating the result ok

        showProgress(getTag());
     /*   GpRequestFactory.requestSocialLogin(this, getTag(), gpBaseActivity)
                .setEmail(jsonObject.optString("email"))
                .setName(jsonObject.optString("name"))
                .setFacebookId(jsonObject.optString("id"))
                .setProfilePic(jsonObject.optJSONObject("picture").optJSONObject("data").optString("url"))
                .execute();
     */
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

  /*  @Override
    public void onResponse(GpUser gpUser, Object tag) {
//        setCurrentUser(gpUser);
        AccountManager accountManager = AccountManager.get(this);
        Account account = new Account(gpUser.getName(), "com.goparties.account.USERACCOUNT");
        boolean success = accountManager.addAccountExplicitly(account, "password", null);
        if (success) {
            Log.d("accountsetting", "Account created");
        } else {
            Log.d("accountsetting", "Account creation failed. Look at previous logs to investigate");
        }

        launch(GpUtil.HOME_ACTIVITY).clearStack().launchActivity();
    }

    @Override
    public void onError(GpException e, Object tag) {
        showApologies();
    }

    @Override
    public void onFinish(Object tag) {
        hideProgress();
    }*/
}
