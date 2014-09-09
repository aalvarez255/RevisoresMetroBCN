package com.aaps.revisoresmetrobcn;

/**
 * Created by adrian on 20/08/14.
 */

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class listAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;

    public listAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_item, null);

        TextView title = (TextView)vi.findViewById(R.id.estacion); // title
        TextView artist = (TextView)vi.findViewById(R.id.linea); // artist name
        TextView duration = (TextView)vi.findViewById(R.id.time); // duration
        TextView date = (TextView)vi.findViewById(R.id.date); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image

        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);

        // Setting all values in listview
        title.setText(song.get(lastAlerts.KEY_TITLE));
        artist.setText(song.get(lastAlerts.KEY_ARTIST));
        duration.setText(song.get(lastAlerts.KEY_DURATION));
        date.setText(song.get(lastAlerts.KEY_DATE));

        String thumb = song.get(lastAlerts.KEY_THUMB_URL);
        if (thumb.equals("Línea L1")) {
            thumb_image.setBackgroundResource(R.drawable.l1);
        }
        else if (thumb.equals("Línea L2")) {
            thumb_image.setBackgroundResource(R.drawable.l2);
        }
        else if (thumb.equals("Línea L3")) {
            thumb_image.setBackgroundResource(R.drawable.l3);
        }
        else if (thumb.equals("Línea L4")) {
            thumb_image.setBackgroundResource(R.drawable.l4);
        }
        else if (thumb.equals("Línea L5")) {
            thumb_image.setBackgroundResource(R.drawable.l5);
        }
        else if (thumb.equals("Línea L6")) {
            thumb_image.setBackgroundResource(R.drawable.l6);
        }
        else if (thumb.equals("Línea L7")) {
            thumb_image.setBackgroundResource(R.drawable.l7);
        }
        else if (thumb.equals("Línea L8")) {
            thumb_image.setBackgroundResource(R.drawable.l8);
        }
        else if (thumb.equals("Línea L9")) {
            thumb_image.setBackgroundResource(R.drawable.l9);
        }
        else if (thumb.equals("Línea L10")) {
            thumb_image.setBackgroundResource(R.drawable.l10);
        }
        else if (thumb.equals("Línea L11")) {
            thumb_image.setBackgroundResource(R.drawable.l11);
        }
        return vi;
    }
}