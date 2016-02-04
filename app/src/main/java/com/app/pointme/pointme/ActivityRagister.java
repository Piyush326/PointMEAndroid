package com.app.pointme.pointme;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.app.pointme.pointme.databackend.DbHelper;

import butterknife.Bind;
import butterknife.OnClick;

public class ActivityRagister extends PmBaseActivity {
    @Bind(R.id.register_username)
    EditText userName;

    @Bind(R.id.register_phone)
    EditText phone;


    @Bind(R.id.ragister_email_id)
    EditText email;


    @Bind(R.id.ragister_password)
    EditText password;

    @Bind(R.id.ragister_user_image)
    ImageView userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ragister);
        if (BuildConfig.DEBUG) {
            userName.setText("Pookie piyush");
            email.setText("piyush.gupta@gmail.com");
            phone.setText("8750913537");
            password.setText("qwerty");
        }
    }

    @Override
    public String getHeading() {
        return getString(R.string.title_activity_ragister);
    }

    @Override
    public boolean hasParent() {
        return true;
    }

    @Override
    protected void onBackendConnected(DbHelper dbHelper) {

    }

    @OnClick({R.id.ragister_done})
    public void ragisterClick(View view) {
        launch(ActivityHome.class).launchActivity();
    }

    @OnClick({R.id.ragister_user_image})
    public void imageClicked(View view) {
        pickGalleryImage(userImage);
    }
}
