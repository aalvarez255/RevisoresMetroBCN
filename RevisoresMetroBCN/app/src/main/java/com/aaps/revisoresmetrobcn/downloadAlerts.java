package com.aaps.revisoresmetrobcn;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by adrian on 20/08/14.
 */
public class downloadAlerts extends AsyncTask<Context,Void,JSONArray> {
    @Override
    protected JSONArray doInBackground(Context... contexts) {
        return null;
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);
    }
}
