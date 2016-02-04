package com.app.pointme.pointme;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.app.pointme.pointme.adapter.SplaceImageAdapter;
import com.app.pointme.pointme.databackend.DbHelper;
import com.cepheuen.progresspageindicator.ProgressPageIndicator;

import butterknife.Bind;
import butterknife.OnClick;

public class ActivitySplace extends PmBaseActivity implements View.OnClickListener {
    @Bind(R.id.pager)
    ViewPager pager;

    @Bind(R.id.pageIndicator)
    ProgressPageIndicator pagerIndicator;

    SplaceImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splace);
        setData();
    }

    @Override
    protected void onBackendConnected(DbHelper dbHelper) {

    }

    private void setData() {
        adapter = new SplaceImageAdapter(ActivitySplace.this);
        pager.setAdapter(adapter);
        pagerIndicator.setViewPager(pager);
    }

    @OnClick({R.id.ragister, R.id.login, R.id.login_fb})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ragister:
                launch(ActivityRagister.class).launchActivity();
                break;

            case R.id.login:
                launch(ActivityLogin.class).launchActivity();
                break;

            case R.id.login_fb:
//                launch(ActivityFaceBook.class).launchActivity();
                break;
        }
    }


}
