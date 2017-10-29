package projectetaxi.etaxi_v1;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Ashim Bazracharya on 10/21/2017.
 */

public class CustomSingleton {

    private static CustomSingleton mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private CustomSingleton(Context context) {

        mCtx = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {

        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }

        return requestQueue;
    }

    public static synchronized CustomSingleton getInstance(Context context) {

        if(mInstance == null) {

            mInstance = new CustomSingleton(context);
        }

        return mInstance;
    }

    public<T> void addToRequestQueue(Request<T> request) {

        requestQueue.add(request);
    }
}
