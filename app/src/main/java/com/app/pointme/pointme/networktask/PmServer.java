package com.app.pointme.pointme.networktask;

import android.content.Context;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by goparties on 30/1/16.
 */
public class PmServer extends Server {

    Context context;
    private static PmServer PmServer;

    private PmServer(Context context) {
        super(context.getApplicationContext());
        setContext(context);
    }

    /**
     * Initialize the PmServer
     *
     * @param context the application context
     */
    public static PmServer initialize(Context context) {
        if (PmServer == null) {
            PmServer = new PmServer(context.getApplicationContext());
        }
        return PmServer;
    }

    public static PmServer getInstance() {
        return PmServer;
    }

    public void setContext(Context context) {
        this.context = context.getApplicationContext();
    }

    public ImageLoader getImageLoader() {
        return imageLoader();
    }

//    public void addGpRequest(GpRequest gpRequest) {
//        getRequestQueue().add(gpRequest);
//    }

    public void cancel(Object tag) {
        getRequestQueue().cancelAll(tag);
    }

}

