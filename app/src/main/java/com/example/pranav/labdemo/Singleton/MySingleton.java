package com.example.pranav.labdemo.Singleton;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


/**
 * Created by prana on 03-05-2017.
 */

public class MySingleton {
    public static MySingleton mInstance;
    public RequestQueue requestQueue;
    public static Context mCtx;

    public MySingleton(Context context) {
        mCtx = context;
        requestQueue = getRequestQueue();

    }

    public RequestQueue getRequestQueue() {
        if(requestQueue == null)
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
            return requestQueue;
    }

    public static synchronized MySingleton getInstance(Context context){
        if(mInstance == null){
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    public<T> void addToRequestQue(Request<T> request){
        getRequestQueue().add(request);
    }
}
