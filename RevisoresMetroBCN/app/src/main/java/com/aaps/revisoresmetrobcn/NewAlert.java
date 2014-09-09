package com.aaps.revisoresmetrobcn;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by adrian on 17/08/14.
 */
public class NewAlert extends Activity {

    Spinner linea;
    Spinner estacion;

    public void goBack(View v) {
        finish();
    }

    public void sendAlert(View v) {
        String lineaSeleccionada = linea.getSelectedItem().toString();
        Log.v("TEST", lineaSeleccionada);
        String estacionSeleccionada = estacion.getSelectedItem().toString();
        boolean result = false;
        try {
            result = new SendAlert().execute(getApplicationContext()).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!result) {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Se ha producido un error de conexión. Inténtelo de nuevo más tarde.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_alert);

        TextView title = (TextView) findViewById(R.id.textView);
        Typeface font = Typeface.createFromAsset(getApplicationContext().getResources().getAssets(), "LANENAR_.ttf");
        title.setTypeface(font);

        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        TextView textView4 = (TextView) findViewById(R.id.textView4);

        textView2.setTypeface(font);
        textView3.setTypeface(font);
        textView4.setTypeface(font);

        linea = (Spinner) findViewById(R.id.spinner);
        linea.setPrompt("Línea:");
        estacion = (Spinner) findViewById(R.id.spinner2);
        estacion.setPrompt("Estación:");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.lineas, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        linea.setAdapter(adapter);
        linea.setEmptyView(findViewById(R.id.spiner_default));

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.linea1, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        estacion.setAdapter(adapter2);
        estacion.setEmptyView(findViewById(R.id.spiner_default));
        linea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selected = parentView.getItemAtPosition(position).toString();

                if (selected.equals("Línea L1")) {
                    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getApplicationContext(),
                            R.array.linea1, R.layout.spinner_item);
                    adapter2.setDropDownViewResource(R.layout.spinner_item);
                    estacion.setAdapter(adapter2);
                }
                else if (selected.equals("Línea L2")) {
                    estacion.setEnabled(true);
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                            R.array.linea2, R.layout.spinner_item);
                    adapter.setDropDownViewResource(R.layout.spinner_item);
                    estacion.setAdapter(adapter);
                }
                else if (selected.equals("Línea L3")) {
                    estacion.setEnabled(true);
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                            R.array.linea3, R.layout.spinner_item);
                    adapter.setDropDownViewResource(R.layout.spinner_item);
                    estacion.setAdapter(adapter);
                }
                else if (selected.equals("Línea L4")) {
                    estacion.setEnabled(true);
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                            R.array.linea4, R.layout.spinner_item);
                    adapter.setDropDownViewResource(R.layout.spinner_item);
                    estacion.setAdapter(adapter);
                }
                else if (selected.equals("Línea L5")) {
                    estacion.setEnabled(true);
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                            R.array.linea5, R.layout.spinner_item);
                    adapter.setDropDownViewResource(R.layout.spinner_item);
                    estacion.setAdapter(adapter);
                }
                else if (selected.equals("Línea L6")) {
                    estacion.setEnabled(true);
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                            R.array.linea6, R.layout.spinner_item);
                    adapter.setDropDownViewResource(R.layout.spinner_item);
                    estacion.setAdapter(adapter);
                }
                else if (selected.equals("Línea L7")) {
                    estacion.setEnabled(true);
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                            R.array.linea7, R.layout.spinner_item);
                    adapter.setDropDownViewResource(R.layout.spinner_item);
                    estacion.setAdapter(adapter);
                }
                else if (selected.equals("Línea L8")) {
                    estacion.setEnabled(true);
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                            R.array.linea8, R.layout.spinner_item);
                    adapter.setDropDownViewResource(R.layout.spinner_item);
                    estacion.setAdapter(adapter);
                }
                else if (selected.equals("Línea L9")) {
                    estacion.setEnabled(true);
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                            R.array.linea9, R.layout.spinner_item);
                    adapter.setDropDownViewResource(R.layout.spinner_item);
                    estacion.setAdapter(adapter);
                }
                else if (selected.equals("Línea L10")) {
                    estacion.setEnabled(true);
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                            R.array.linea10, R.layout.spinner_item);
                    adapter.setDropDownViewResource(R.layout.spinner_item);
                    estacion.setAdapter(adapter);
                }
                else if (selected.equals("Línea L11")) {
                    estacion.setEnabled(true);
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                            R.array.linea11, R.layout.spinner_item);
                    adapter.setDropDownViewResource(R.layout.spinner_item);
                    estacion.setAdapter(adapter);
                }
            }
        });


    }
}
