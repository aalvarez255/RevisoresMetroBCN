package com.aaps.revisoresmetrobcn;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.net.InetAddress;

/**
 * Created by adrian on 17/08/14.
 */
public class SendAlert extends AsyncTask<Context, Void, Boolean> {


    @Override
    protected Boolean doInBackground(Context... contexts) {

        Context context = contexts[0];
        ConnectivityManager conMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        if (i == null)
            return false;
        if (!i.isConnected())
            return false;
        if (!i.isAvailable())
            return false;


        boolean reachable = false;
        try {

            reachable = InetAddress.getByName("192.168.1.34").isReachable(2000);
            Log.v("TEST", reachable + "");


        } catch (Exception e) {
            Log.v("TEST",e.getMessage());
            e.printStackTrace();
        }

        if (!reachable) return false;



        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}
