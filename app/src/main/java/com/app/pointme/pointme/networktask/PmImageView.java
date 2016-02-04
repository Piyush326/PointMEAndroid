package com.app.pointme.pointme.networktask;

import android.content.Context;

import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by goparties on 30/1/16.
 */
public class PmImageView extends NetworkImageView {
    public PmImageView(Context context) {
        super(context);
    }

    public void setImageUrl(String url) {
        super.setImageUrl(url, PmServer.getInstance().getImageLoader());
    }
}
