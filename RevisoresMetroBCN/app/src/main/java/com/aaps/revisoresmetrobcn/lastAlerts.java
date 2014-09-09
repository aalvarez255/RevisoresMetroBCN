package com.aaps.revisoresmetrobcn;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by adrian on 19/08/14.
 */
public class lastAlerts extends Activity {

    // All static variables
    static final String URL = "http://api.androidhive.info/music/music.xml";
    // XML node keys

    static final String KEY_TITLE = "estacion";
    static final String KEY_ARTIST = "linea";
    static final String KEY_DURATION = "time";
    static final String KEY_DATE = "date";
    static final String KEY_THUMB_URL = "thumb";

    ListView list;
    listAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.last_alerts);


        ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();


        JSONArray data;
        try {
            data = new downloadAlerts().execute(this).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        XMLParser parser = new XMLParser();
        String xml = parser.getXmlFromUrl(URL); // getting XML from URL
        Document doc = parser.getDomElement(xml); // getting DOM element

        NodeList nl = doc.getElementsByTagName(KEY_SONG);
        // looping through all song nodes <song>
        for (int i = 0; i < nl.getLength(); i++) {
            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
            Element e = (Element) nl.item(i);
            // adding each child node to HashMap key => value
            map.put(KEY_ID, parser.getValue(e, KEY_ID));
            map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
            map.put(KEY_ARTIST, parser.getValue(e, KEY_ARTIST));
            map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
            map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));

            // adding HashList to ArrayList
            songsList.add(map);
        }


        list=(ListView)findViewById(R.id.listView);

        // Getting adapter by passing xml data ArrayList
        adapter=new listAdapter(this, songsList);
        list.setAdapter(adapter);


    }
}
