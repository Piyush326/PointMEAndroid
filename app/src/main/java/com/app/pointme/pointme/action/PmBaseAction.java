package com.app.pointme.pointme.action;

import android.content.Context;
import android.view.View;

import com.app.pointme.pointme.PmBaseActivity;

/**
 * Created by goparties on 29/1/16.
 */
public class PmBaseAction implements View.OnClickListener {
    private ActionBottom actionBottom;
    PmBaseActivity pmBaseActivity;
    static PmBaseAction pmBaseAction;


    public static PmBaseAction getInstance() {
        if (pmBaseAction == null) {
            pmBaseAction = new PmBaseAction();
        }
        return pmBaseAction;
    }

    @Override
    public void onClick(View v) {

    }

    public void setContext(Context context) {
        pmBaseActivity = (PmBaseActivity) context;
        actionBottom = new ActionBottom();
        this.actionBottom.setContext(context);

    }
}
