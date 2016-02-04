package com.app.pointme.pointme;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.app.pointme.pointme.adapter.FeedAdapter;
import com.app.pointme.pointme.databackend.DbHelper;

import butterknife.Bind;

public class ActivityUserProfile extends PmBaseActivity {
    @Bind(R.id.profile_feed_list)
    RecyclerView feedList;

    @Override
    public boolean hasParent() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
    }

    @Override
    protected void onBackendConnected(DbHelper dbHelper) {
        feedList.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        feedList.setLayoutManager(mLayoutManager);
        FeedAdapter feedAdapter = new FeedAdapter(ActivityUserProfile.this);
        feedList.setAdapter(feedAdapter);
    }
}
