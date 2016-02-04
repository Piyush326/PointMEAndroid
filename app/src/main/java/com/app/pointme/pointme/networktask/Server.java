package com.app.pointme.pointme.networktask;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * The class manages the request queue and image loader mechanism
 * Created by GoParties on 29/11/15.
 */
abstract public class Server {

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    public Server(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(requestQueue, new LruBitmapCache(context));
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public ImageLoader imageLoader() {
        return imageLoader;
    }

}
