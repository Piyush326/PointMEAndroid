package com.app.pointme.pointme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.app.pointme.pointme.databackend.DbHelper;

import butterknife.OnClick;

public class ActivityHome extends PmBaseActivity {
    private FragmentHomeFeed fragmentHomeFeed;
    private FragmentPointMe fragmentPointMe;


    @Override
    public boolean hasParent() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void onBackendConnected(DbHelper dbHelper) {
        fragmentHomeFeed = new FragmentHomeFeed();
        fragmentPointMe = new FragmentPointMe();
        swichFragments(fragmentHomeFeed);
    }

    /*@Override
    protected void onBackendConnected(DbHelper dbHelper) {
        feedList.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        feedList.setLayoutManager(mLayoutManager);
        FeedAdapter feedAdapter = new FeedAdapter();
        feedList.setAdapter(feedAdapter);
    }*/

    private void swichFragments(Fragment fragment) {
        if (getSupportFragmentManager().findFragmentById(R.id.home_container) == fragment)
            return;
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        if (getSupportFragmentManager().findFragmentById(R.id.home_container) == null) {
            fragmentTransaction.add(R.id.home_container, fragmentHomeFeed);
        } else {
            fragmentTransaction.replace(R.id.home_container, fragment);
        }
        fragmentTransaction.commit();
    }

    @OnClick({R.id.goto_home, R.id.goto_point, R.id.goto_profile})
    public void handleBottomCLicks(View view) {
        switch (view.getId()) {
            case R.id.goto_home:
                setCurrentMode(HOME);
                swichFragments(fragmentHomeFeed);
                break;
            case R.id.goto_point:
                setCurrentMode(POINTME);
                swichFragments(fragmentPointMe);
                break;

            case R.id.goto_profile:
                launch(ActivityUserProfile.class).launchActivity();
                break;
        }
    }

  /*  private void init() {
        point = findViewById(R.id.point_me);
        home = findViewById(R.id.home);
        pingSetting = findViewById(R.id.home_ping_setting);
        pointLayout = findViewById(R.id.point_layout);
        homeContent = findViewById(R.id.home_content);

        profile.setOnClickListener(this);
        point.setOnClickListener(this);
        home.setOnClickListener(this);
        pingSetting.setOnClickListener(this);
    }*/


    /*@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile:
                launch(ActivityUserProfile.class).launchActivity();
                break;

            case R.id.point_me:
                setSelected(point);
                homeContent.setVisibility(View.GONE);
                pointLayout.setVisibility(View.VISIBLE);
                break;

            case R.id.home:
                setSelected(home);
                homeContent.setVisibility(View.VISIBLE);
                pointLayout.setVisibility(View.GONE);
                break;

            case R.id.home_ping_setting:
                FragmentPingPoint fragmentPingPoint = new FragmentPingPoint();
                fragmentPingPoint.show(getFragmentManager(), "");
                break;
        }
    }*/

 /*   private void setSelected(View view) {
        if (view == point) {
            setCurrentMode(POINTME);
            point.setBackgroundResource(R.color.colorPrimaryDark);
            home.setBackgroundColor(Color.TRANSPARENT);
        } else if (view == home) {
            setCurrentMode(HOME);
            point.setBackgroundColor(Color.TRANSPARENT);
            home.setBackgroundResource(R.color.colorPrimaryDark);
        }
    }*/
}
