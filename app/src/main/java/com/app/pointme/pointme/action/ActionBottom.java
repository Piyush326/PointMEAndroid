package com.app.pointme.pointme.action;

import android.view.View;

import com.app.pointme.pointme.ActivityUserProfile;
import com.app.pointme.pointme.FragmentPingPoint;
import com.app.pointme.pointme.R;

/**
 * Created by goparties on 29/1/16.
 */
public class ActionBottom extends PmBaseAction {

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goto_profile:
                pmBaseActivity.launch(ActivityUserProfile.class).launchActivity();
                break;
            case R.id.home_ping_setting:
                FragmentPingPoint fragmentPingPoint = new FragmentPingPoint();
                fragmentPingPoint.show(pmBaseActivity.getFragmentManager(), "");
                break;
        }

    }
}
