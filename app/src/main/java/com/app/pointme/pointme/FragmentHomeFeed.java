package com.app.pointme.pointme;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.app.pointme.pointme.adapter.FeedAdapter;
import com.app.pointme.pointme.databackend.DbHelper;

import butterknife.Bind;

/**
 * Created by goparties on 30/1/16.
 */
public class FragmentHomeFeed extends PmBaseFragment {

    @Bind(R.id.home_feed_list)
    RecyclerView feedList;

    @Override
    public void onBackendConnected(DbHelper dbHelper) {
        feedList.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(pmBaseActivity);
        feedList.setLayoutManager(mLayoutManager);
        FeedAdapter feedAdapter = new FeedAdapter(pmBaseActivity);
        feedList.setAdapter(feedAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.content_home;
    }

    @Override
    public void onLayoutCreated() {

    }

}
