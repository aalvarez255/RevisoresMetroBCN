package com.aaps.revisoresmetrobcn;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;


public class MyActivity extends Activity {

    SharedPreferences config;
    SharedPreferences.Editor config_editor;
    Spinner spinner;
    MultiSpinner multiSpinner;
    Context context;
    Activity activity;

    ArrayList<String> estaciones;

    Switch vibracion;
    Switch sonido;


    private class Linea {
        int id;
        int size;
        String name;

        public Linea(int id, int size, String name) {
            this.id = id;
            this.size = size;
            this.name = name;
        }

        public int getId() { return this.id; }
        public int getSize() { return this.size; }
        public String getName() { return this.name; }
    }


    public void filtros(View v) {
        Switch switch1 = (Switch) findViewById(R.id.switch1);
        if (!switch1.isChecked()) {
            spinner.setEnabled(false);
            multiSpinner.setEnabled(false);
        } else {
            spinner.setEnabled(true);
            multiSpinner.setEnabled(true);
        }
    }

    public void setSonido(View v) {
        if (sonido.isChecked()) {
            config_editor.putBoolean("sonido",true);
        } else {
            config_editor.putBoolean("sonido",false);
        }
        config_editor.commit();
    }

    public void setVibracion(View v) {
        if (vibracion.isChecked()) {
            config_editor.putBoolean("vibracion",true);
        } else {
            config_editor.putBoolean("vibracion",false);
        }
        config_editor.commit();
    }

    public void validarConf(View v) {

        if (estaciones.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setTitle("Atención")
                    .setMessage("Si no selecciona ninguna estación no recibirá alertas de revisores. Desea continuar?")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Set<String> set = new HashSet<String>();
                            set.addAll(estaciones);
                            config_editor.putStringSet("estaciones", set);
                            config_editor.commit();
                            Intent intent = new Intent(MyActivity.this, configDone.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        else {
            Set<String> set = new HashSet<String>();
            set.addAll(estaciones);
            config_editor.putStringSet("estaciones", set);
            config_editor.commit();
            Intent intent = new Intent(MyActivity.this, configDone.class);
            startActivity(intent);
            finish();
        }
    }

    public boolean saveArray(boolean[] array, String arrayName) {
        config_editor.putInt(arrayName + "_size", array.length);
        for (int i = 0; i < array.length; i++)
            config_editor.putBoolean(arrayName + "_" + i, array[i]);
        return config_editor.commit();
    }

    public boolean[] loadArray(String arrayName, int size) {
        boolean array[] = new boolean[size];
        for (int i = 0; i < size; i++)
            array[i] = config.getBoolean(arrayName + "_" + i, false);
        return array;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my);

        config = this.getSharedPreferences(
                "com.aaps.revisoresmetrobcn;", Context.MODE_PRIVATE);
        config_editor = config.edit();

        boolean appIni = config.getBoolean("appIni",false);
        if (appIni) {
            Intent intent = new Intent(this, MainMenu.class);
            startActivity(intent);
            finish();
        }

        Set<String> hash = config.getStringSet("estaciones", new HashSet<String>());

        estaciones = new ArrayList<String>(hash);

        final Linea[] arrays_num = new Linea[11];
        arrays_num[0] = new Linea(R.array.linea1, 30, "linea1");
        arrays_num[1] = new Linea(R.array.linea2, 18, "linea2");
        arrays_num[2] = new Linea(R.array.linea3, 25, "linea3");
        arrays_num[3] = new Linea(R.array.linea4, 22, "linea4");
        arrays_num[4] = new Linea(R.array.linea5, 26, "linea5");
        arrays_num[5] = new Linea(R.array.linea6, 9, "linea6");
        arrays_num[6] = new Linea(R.array.linea7, 7, "linea7");
        arrays_num[7] = new Linea(R.array.linea8, 11, "linea8");
        arrays_num[8] = new Linea(R.array.linea9, 9, "linea9");
        arrays_num[9] = new Linea(R.array.linea10, 6, "linea10");
        arrays_num[10] = new Linea(R.array.linea11, 5, "linea11");

        vibracion = (Switch) findViewById(R.id.switch2);
        sonido = (Switch) findViewById(R.id.switch3);

        vibracion.setChecked(config.getBoolean("vibracion",true));
        sonido.setChecked(config.getBoolean("sonido",true));

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.lineas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selected = parentView.getItemAtPosition(position).toString();

                if (selected.equals("Línea L1")) {

                    boolean checks[] = loadArray("linea1", 30);


                    Resources res = getResources();
                    String[] linea1_array = res.getStringArray(R.array.linea1);
                    final List<String> linea1 = Arrays.asList(linea1_array);

                    if (!estaciones.isEmpty()) {
                        for (int i = 0; i < estaciones.size(); ++i) {
                            if (linea1.contains(estaciones.get(i))) {
                                checks[linea1.indexOf(estaciones.get(i))] = true;
                            }
                        }
                    }

                    saveArray(checks,"linea1");

                    multiSpinner.setItems(linea1, checks, "Estación",
                            new MultiSpinner.MultiSpinnerListener() {
                                @Override
                                public void onItemsSelected(boolean[] selected) {
                                    boolean[] old = loadArray("linea1", 30);
                                    saveArray(selected, "linea1");
                                    boolean[] xor;
                                    for (int i = 0; i < selected.length; ++i) {
                                        if (selected[i] != old[i]) {
                                            String estacion = linea1.get(i);
                                            if (!estaciones.contains(estacion))
                                                estaciones.add(estacion);
                                            else {
                                                if (old[i] == true) {
                                                    estaciones.remove(estaciones.indexOf(estacion));
                                                    for (int j = 0; j < 10; ++j) {

                                                        int num_act = arrays_num[j].getId();

                                                        Resources res = getResources();
                                                        String[] linea_array = res.getStringArray(num_act);
                                                        final List<String> linea = Arrays.asList(linea_array);
                                                        if (linea.contains(estacion)) {
                                                            boolean[] checks_estacion = loadArray(arrays_num[j].getName(), arrays_num[j].getSize());
                                                            checks_estacion[linea.indexOf(estacion)] = false;
                                                            saveArray(checks_estacion, arrays_num[j].getName());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    config_editor.putStringSet("estaciones",new HashSet<String>(estaciones));
                                    config_editor.commit();
                                }
                            }
                    );
                } else if (selected.equals("Línea L2")) {
                    boolean checks[] = loadArray("linea2", 18);

                    Resources res = getResources();
                    String[] linea1_array = res.getStringArray(R.array.linea2);
                    final List<String> linea2 = Arrays.asList(linea1_array);

                    if (!estaciones.isEmpty()) {
                        for (int i = 0; i < estaciones.size(); ++i) {
                            if (linea2.contains(estaciones.get(i))) {
                                checks[linea2.indexOf(estaciones.get(i))] = true;
                            }
                        }
                    }

                    saveArray(checks,"linea2");

                    multiSpinner.setItems(linea2, checks, "Estación",
                            new MultiSpinner.MultiSpinnerListener() {
                                @Override
                                public void onItemsSelected(boolean[] selected) {
                                    boolean[] old = loadArray("linea2", 18);
                                    saveArray(selected, "linea1");
                                    boolean[] xor;
                                    for (int i = 0; i < selected.length; ++i) {
                                        if (selected[i] != old[i]) {
                                            String estacion = linea2.get(i);
                                            if (!estaciones.contains(estacion))
                                                estaciones.add(estacion);
                                            else {
                                                if (old[i] == true) {
                                                    estaciones.remove(estaciones.indexOf(estacion));
                                                    for (int j = 0; j < 10; ++j) {

                                                        int num_act = arrays_num[j].getId();

                                                        Resources res = getResources();
                                                        String[] linea_array = res.getStringArray(num_act);
                                                        final List<String> linea = Arrays.asList(linea_array);
                                                        if (linea.contains(estacion)) {
                                                            boolean[] checks_estacion = loadArray(arrays_num[j].getName(), arrays_num[j].getSize());
                                                            checks_estacion[linea.indexOf(estacion)] = false;
                                                            saveArray(checks_estacion, arrays_num[j].getName());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    config_editor.putStringSet("estaciones",new HashSet<String>(estaciones));
                                    config_editor.commit();
                                }
                            }
                    );
                } else if (selected.equals("Línea L3")) {
                    boolean checks[] = loadArray("linea3", 25);

                    Resources res = getResources();
                    String[] linea1_array = res.getStringArray(R.array.linea3);
                    final List<String> linea3 = Arrays.asList(linea1_array);

                    if (!estaciones.isEmpty()) {
                        for (int i = 0; i < estaciones.size(); ++i) {
                            if (linea3.contains(estaciones.get(i))) {
                                checks[linea3.indexOf(estaciones.get(i))] = true;
                            }
                        }
                    }


                    saveArray(checks,"linea3");

                    multiSpinner.setItems(linea3, checks, "Estación",
                            new MultiSpinner.MultiSpinnerListener() {
                                @Override
                                public void onItemsSelected(boolean[] selected) {
                                    boolean[] old = loadArray("linea3", 25);
                                    saveArray(selected, "linea3");

                                    boolean[] xor;
                                    for (int i = 0; i < selected.length; ++i) {
                                        if (selected[i] != old[i]) {
                                            String estacion = linea3.get(i);
                                            if (!estaciones.contains(estacion))
                                                estaciones.add(estacion);
                                            else {
                                                if (old[i] == true) {
                                                    estaciones.remove(estaciones.indexOf(estacion));
                                                    for (int j = 0; j < 10; ++j) {

                                                        int num_act = arrays_num[j].getId();

                                                        Resources res = getResources();
                                                        String[] linea_array = res.getStringArray(num_act);
                                                        final List<String> linea = Arrays.asList(linea_array);
                                                        if (linea.contains(estacion)) {
                                                            boolean[] checks_estacion = loadArray(arrays_num[j].getName(), arrays_num[j].getSize());
                                                            checks_estacion[linea.indexOf(estacion)] = false;
                                                            saveArray(checks_estacion, arrays_num[j].getName());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    config_editor.putStringSet("estaciones",new HashSet<String>(estaciones));
                                    config_editor.commit();
                                }
                            }
                    );
                } else if (selected.equals("Línea L4")) {
                    boolean checks[] = loadArray("linea4", 22);

                    Resources res = getResources();
                    String[] linea1_array = res.getStringArray(R.array.linea4);
                    final List<String> linea4 = Arrays.asList(linea1_array);

                    if (!estaciones.isEmpty()) {
                        for (int i = 0; i < estaciones.size(); ++i) {
                            if (linea4.contains(estaciones.get(i))) {
                                checks[linea4.indexOf(estaciones.get(i))] = true;
                            }
                        }
                    }


                    saveArray(checks,"linea4");

                    multiSpinner.setItems(linea4, checks, "Estación",
                            new MultiSpinner.MultiSpinnerListener() {
                                @Override
                                public void onItemsSelected(boolean[] selected) {
                                    boolean[] old = loadArray("linea4", 22);
                                    saveArray(selected, "linea4");
                                    boolean[] xor;
                                    for (int i = 0; i < selected.length; ++i) {
                                        if (selected[i] != old[i]) {
                                            String estacion = linea4.get(i);
                                            if (!estaciones.contains(estacion))
                                                estaciones.add(estacion);
                                            else {
                                                if (old[i] == true) {
                                                    estaciones.remove(estaciones.indexOf(estacion));
                                                    for (int j = 0; j < 10; ++j) {

                                                        int num_act = arrays_num[j].getId();

                                                        Resources res = getResources();
                                                        String[] linea_array = res.getStringArray(num_act);
                                                        final List<String> linea = Arrays.asList(linea_array);
                                                        if (linea.contains(estacion)) {
                                                            boolean[] checks_estacion = loadArray(arrays_num[j].getName(), arrays_num[j].getSize());
                                                            checks_estacion[linea.indexOf(estacion)] = false;
                                                            saveArray(checks_estacion, arrays_num[j].getName());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    config_editor.putStringSet("estaciones",new HashSet<String>(estaciones));
                                    config_editor.commit();
                                }
                            }
                    );
                } else if (selected.equals("Línea L5")) {
                    boolean checks[] = loadArray("linea5", 26);

                    Resources res = getResources();
                    String[] linea1_array = res.getStringArray(R.array.linea5);
                    final List<String> linea5 = Arrays.asList(linea1_array);

                    if (!estaciones.isEmpty()) {
                        for (int i = 0; i < estaciones.size(); ++i) {
                            if (linea5.contains(estaciones.get(i))) {
                                checks[linea5.indexOf(estaciones.get(i))] = true;
                            }
                        }
                    }


                    saveArray(checks,"linea5");

                    multiSpinner.setItems(linea5, checks, "Estación",
                            new MultiSpinner.MultiSpinnerListener() {
                                @Override
                                public void onItemsSelected(boolean[] selected) {
                                    boolean[] old = loadArray("linea5", 26);
                                    saveArray(selected, "linea5");

                                    boolean[] xor;
                                    for (int i = 0; i < selected.length; ++i) {
                                        if (selected[i] != old[i]) {
                                            String estacion = linea5.get(i);
                                            if (!estaciones.contains(estacion))
                                                estaciones.add(estacion);
                                            else {
                                                if (old[i] == true) {
                                                    estaciones.remove(estaciones.indexOf(estacion));
                                                    for (int j = 0; j < 10; ++j) {

                                                        int num_act = arrays_num[j].getId();

                                                        Resources res = getResources();
                                                        String[] linea_array = res.getStringArray(num_act);
                                                        final List<String> linea = Arrays.asList(linea_array);
                                                        if (linea.contains(estacion)) {
                                                            boolean[] checks_estacion = loadArray(arrays_num[j].getName(), arrays_num[j].getSize());
                                                            checks_estacion[linea.indexOf(estacion)] = false;
                                                            saveArray(checks_estacion, arrays_num[j].getName());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    config_editor.putStringSet("estaciones",new HashSet<String>(estaciones));
                                    config_editor.commit();
                                }
                            }
                    );
                } else if (selected.equals("Línea L6")) {
                    boolean checks[] = loadArray("linea6", 9);

                    Resources res = getResources();
                    String[] linea1_array = res.getStringArray(R.array.linea6);
                    final List<String> linea6 = Arrays.asList(linea1_array);


                    if (!estaciones.isEmpty()) {
                        for (int i = 0; i < estaciones.size(); ++i) {
                            if (linea6.contains(estaciones.get(i))) {
                                checks[linea6.indexOf(estaciones.get(i))] = true;
                            }
                        }
                    }


                    saveArray(checks,"linea6");

                    multiSpinner.setItems(linea6, checks, "Estación",
                            new MultiSpinner.MultiSpinnerListener() {
                                @Override
                                public void onItemsSelected(boolean[] selected) {
                                    boolean[] old = loadArray("linea6", 9);
                                    saveArray(selected, "linea6");
                                    boolean[] xor;
                                    for (int i = 0; i < selected.length; ++i) {
                                        if (selected[i] != old[i]) {
                                            String estacion = linea6.get(i);
                                            if (!estaciones.contains(estacion))
                                                estaciones.add(estacion);
                                            else {
                                                if (old[i] == true) {
                                                    estaciones.remove(estaciones.indexOf(estacion));
                                                    for (int j = 0; j < 10; ++j) {

                                                        int num_act = arrays_num[j].getId();

                                                        Resources res = getResources();
                                                        String[] linea_array = res.getStringArray(num_act);
                                                        final List<String> linea = Arrays.asList(linea_array);
                                                        if (linea.contains(estacion)) {
                                                            boolean[] checks_estacion = loadArray(arrays_num[j].getName(), arrays_num[j].getSize());
                                                            checks_estacion[linea.indexOf(estacion)] = false;
                                                            saveArray(checks_estacion, arrays_num[j].getName());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    config_editor.putStringSet("estaciones",new HashSet<String>(estaciones));
                                    config_editor.commit();
                                }
                            }
                    );
                } else if (selected.equals("Línea L7")) {
                    boolean checks[] = loadArray("linea7", 7);

                    Resources res = getResources();
                    String[] linea1_array = res.getStringArray(R.array.linea7);
                    final List<String> linea7 = Arrays.asList(linea1_array);

                    if (!estaciones.isEmpty()) {
                        for (int i = 0; i < estaciones.size(); ++i) {
                            if (linea7.contains(estaciones.get(i))) {
                                checks[linea7.indexOf(estaciones.get(i))] = true;
                            }
                        }
                    }


                    saveArray(checks,"linea7");

                    multiSpinner.setItems(linea7, checks, "Estación",
                            new MultiSpinner.MultiSpinnerListener() {
                                @Override
                                public void onItemsSelected(boolean[] selected) {
                                    boolean[] old = loadArray("linea7", 7);
                                    saveArray(selected, "linea7");
                                    boolean[] xor;
                                    for (int i = 0; i < selected.length; ++i) {
                                        if (selected[i] != old[i]) {
                                            String estacion = linea7.get(i);
                                            if (!estaciones.contains(estacion))
                                                estaciones.add(estacion);
                                            else {
                                                if (old[i] == true) {
                                                    estaciones.remove(estaciones.indexOf(estacion));
                                                    for (int j = 0; j < 10; ++j) {

                                                        int num_act = arrays_num[j].getId();

                                                        Resources res = getResources();
                                                        String[] linea_array = res.getStringArray(num_act);
                                                        final List<String> linea = Arrays.asList(linea_array);
                                                        if (linea.contains(estacion)) {
                                                            boolean[] checks_estacion = loadArray(arrays_num[j].getName(), arrays_num[j].getSize());
                                                            checks_estacion[linea.indexOf(estacion)] = false;
                                                            saveArray(checks_estacion, arrays_num[j].getName());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    config_editor.putStringSet("estaciones",new HashSet<String>(estaciones));
                                    config_editor.commit();
                                }
                            }
                    );
                } else if (selected.equals("Línea L8")) {
                    boolean checks[] = loadArray("linea8", 11);

                    Resources res = getResources();
                    String[] linea1_array = res.getStringArray(R.array.linea8);
                    final List<String> linea8 = Arrays.asList(linea1_array);

                    if (!estaciones.isEmpty()) {
                        for (int i = 0; i < estaciones.size(); ++i) {
                            if (linea8.contains(estaciones.get(i))) {
                                checks[linea8.indexOf(estaciones.get(i))] = true;
                            }
                        }
                    }


                    saveArray(checks,"linea8");

                    multiSpinner.setItems(linea8, checks, "Estación",
                            new MultiSpinner.MultiSpinnerListener() {
                                @Override
                                public void onItemsSelected(boolean[] selected) {
                                    boolean[] old = loadArray("linea8", 11);
                                    saveArray(selected, "linea8");
                                    boolean[] xor;
                                    for (int i = 0; i < selected.length; ++i) {
                                        if (selected[i] != old[i]) {
                                            String estacion = linea8.get(i);
                                            if (!estaciones.contains(estacion))
                                                estaciones.add(estacion);
                                            else {
                                                if (old[i] == true) {
                                                    estaciones.remove(estaciones.indexOf(estacion));
                                                    for (int j = 0; j < 10; ++j) {

                                                        int num_act = arrays_num[j].getId();

                                                        Resources res = getResources();
                                                        String[] linea_array = res.getStringArray(num_act);
                                                        final List<String> linea = Arrays.asList(linea_array);
                                                        if (linea.contains(estacion)) {
                                                            boolean[] checks_estacion = loadArray(arrays_num[j].getName(), arrays_num[j].getSize());
                                                            checks_estacion[linea.indexOf(estacion)] = false;
                                                            saveArray(checks_estacion, arrays_num[j].getName());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    config_editor.putStringSet("estaciones",new HashSet<String>(estaciones));
                                    config_editor.commit();
                                }
                            }
                    );
                } else if (selected.equals("Línea L9")) {
                    boolean checks[] = loadArray("linea9", 9);

                    Resources res = getResources();
                    String[] linea1_array = res.getStringArray(R.array.linea9);
                    final List<String> linea9 = Arrays.asList(linea1_array);

                    if (!estaciones.isEmpty()) {
                        for (int i = 0; i < estaciones.size(); ++i) {
                            if (linea9.contains(estaciones.get(i))) {
                                checks[linea9.indexOf(estaciones.get(i))] = true;
                            }
                        }
                    }


                    saveArray(checks,"linea9");

                    multiSpinner.setItems(linea9, checks, "Estación",
                            new MultiSpinner.MultiSpinnerListener() {
                                @Override
                                public void onItemsSelected(boolean[] selected) {
                                    boolean[] old = loadArray("linea9", 9);
                                    saveArray(selected, "linea9");
                                    boolean[] xor;
                                    for (int i = 0; i < selected.length; ++i) {
                                        if (selected[i] != old[i]) {
                                            String estacion = linea9.get(i);
                                            if (!estaciones.contains(estacion))
                                                estaciones.add(estacion);
                                            else {
                                                if (old[i] == true) {
                                                    estaciones.remove(estaciones.indexOf(estacion));
                                                    for (int j = 0; j < 10; ++j) {

                                                        int num_act = arrays_num[j].getId();

                                                        Resources res = getResources();
                                                        String[] linea_array = res.getStringArray(num_act);
                                                        final List<String> linea = Arrays.asList(linea_array);
                                                        if (linea.contains(estacion)) {
                                                            boolean[] checks_estacion = loadArray(arrays_num[j].getName(), arrays_num[j].getSize());
                                                            checks_estacion[linea.indexOf(estacion)] = false;
                                                            saveArray(checks_estacion, arrays_num[j].getName());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    config_editor.putStringSet("estaciones",new HashSet<String>(estaciones));
                                    config_editor.commit();
                                }
                            }
                    );
                } else if (selected.equals("Línea L10")) {
                    boolean checks[] = loadArray("linea10", 6);

                    Resources res = getResources();
                    String[] linea1_array = res.getStringArray(R.array.linea10);
                    final List<String> linea10 = Arrays.asList(linea1_array);

                    if (!estaciones.isEmpty()) {
                        for (int i = 0; i < estaciones.size(); ++i) {
                            if (linea10.contains(estaciones.get(i))) {
                                checks[linea10.indexOf(estaciones.get(i))] = true;
                            }
                        }
                    }


                    saveArray(checks,"linea10");

                    multiSpinner.setItems(linea10, checks, "Estación",
                            new MultiSpinner.MultiSpinnerListener() {
                                @Override
                                public void onItemsSelected(boolean[] selected) {
                                    boolean[] old = loadArray("linea10", 6);
                                    saveArray(selected, "linea10");
                                    boolean[] xor;
                                    for (int i = 0; i < selected.length; ++i) {
                                        if (selected[i] != old[i]) {
                                            String estacion = linea10.get(i);
                                            if (!estaciones.contains(estacion))
                                                estaciones.add(estacion);
                                            else {
                                                if (old[i] == true) {
                                                    estaciones.remove(estaciones.indexOf(estacion));
                                                    for (int j = 0; j < 10; ++j) {

                                                        int num_act = arrays_num[j].getId();

                                                        Resources res = getResources();
                                                        String[] linea_array = res.getStringArray(num_act);
                                                        final List<String> linea = Arrays.asList(linea_array);
                                                        if (linea.contains(estacion)) {
                                                            boolean[] checks_estacion = loadArray(arrays_num[j].getName(), arrays_num[j].getSize());
                                                            checks_estacion[linea.indexOf(estacion)] = false;
                                                            saveArray(checks_estacion, arrays_num[j].getName());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    config_editor.putStringSet("estaciones",new HashSet<String>(estaciones));
                                    config_editor.commit();
                                }
                            }
                    );
                } else if (selected.equals("Línea L11")) {
                    final boolean checks[] = loadArray("linea11", 5);

                    Resources res = getResources();
                    String[] linea1_array = res.getStringArray(R.array.linea11);
                    final List<String> linea11 = Arrays.asList(linea1_array);

                    if (!estaciones.isEmpty()) {
                        for (int i = 0; i < estaciones.size(); ++i) {
                            if (linea11.contains(estaciones.get(i))) {
                                checks[linea11.indexOf(estaciones.get(i))] = true;
                            }
                        }
                    }


                    saveArray(checks,"linea11");


                    multiSpinner.setItems(linea11, checks, "Estación",
                            new MultiSpinner.MultiSpinnerListener() {
                                @Override
                                public void onItemsSelected(boolean[] selected) {
                                    boolean[] old = loadArray("linea11", 5);
                                    saveArray(selected, "linea11");
                                    boolean[] xor;
                                    for (int i = 0; i < selected.length; ++i) {
                                        if (selected[i] != old[i]) {
                                            String estacion = linea11.get(i);
                                            if (!estaciones.contains(estacion))
                                                estaciones.add(estacion);
                                            else {
                                                if (old[i] == true) {
                                                    estaciones.remove(estaciones.indexOf(estacion));
                                                    for (int j = 0; j < 10; ++j) {

                                                        int num_act = arrays_num[j].getId();

                                                        Resources res = getResources();
                                                        String[] linea_array = res.getStringArray(num_act);
                                                        final List<String> linea = Arrays.asList(linea_array);
                                                        if (linea.contains(estacion)) {
                                                            boolean[] checks_estacion = loadArray(arrays_num[j].getName(), arrays_num[j].getSize());
                                                            checks_estacion[linea.indexOf(estacion)] = false;
                                                            saveArray(checks_estacion, arrays_num[j].getName());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    config_editor.putStringSet("estaciones",new HashSet<String>(estaciones));
                                    config_editor.commit();
                                }
                            }
                    );
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        multiSpinner = (MultiSpinner) findViewById(R.id.multi_spinner);

        boolean checks[] = loadArray("linea1", 30);

        Resources res = getResources();
        String[] linea1_array = res.getStringArray(R.array.linea1);
        final List<String> linea1 = Arrays.asList(linea1_array);

        if (!estaciones.isEmpty()) {
            for (int i = 0; i < estaciones.size(); ++i) {
                if (linea1.contains(estaciones.get(i))) {
                    checks[linea1.indexOf(estaciones.get(i))] = true;
                }
            }
        }


        saveArray(checks,"linea1");

        multiSpinner.setItems(linea1, checks, "Estación",
                new MultiSpinner.MultiSpinnerListener() {
                    @Override
                    public void onItemsSelected(boolean[] selected) {
                        boolean[] old = loadArray("linea1", 30);
                        saveArray(selected, "linea1");
                        boolean[] xor;
                        for (int i = 0; i < selected.length; ++i) {
                            if (selected[i] != old[i]) {
                                String estacion = linea1.get(i);
                                if (!estaciones.contains(estacion)) estaciones.add(estacion);
                                else estaciones.remove(estaciones.indexOf(estacion));
                            }
                        }
                    }
                }
        );
    }

}
