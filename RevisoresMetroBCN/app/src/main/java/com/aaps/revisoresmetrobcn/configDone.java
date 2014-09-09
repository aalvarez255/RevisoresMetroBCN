package com.aaps.revisoresmetrobcn;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by adrian on 14/08/14.
 */
public class configDone extends Activity {

    TextView display;
    ProgressBar progressBar;
    ImageView imageView;
    Button button;
    SharedPreferences config;
    SharedPreferences.Editor config_editor;

    public void startApp(View v) {
        config_editor.putBoolean("appIni",true);
        config_editor.commit();
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.config_done);

        config = this.getSharedPreferences(
                "com.aaps.revisoresmetrobcn;", Context.MODE_PRIVATE);
        config_editor = config.edit();

        display = (TextView) findViewById(R.id.textView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        imageView = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.button);

        imageView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        button.setEnabled(false);

        Typeface font = Typeface.createFromAsset(getApplicationContext().getResources().getAssets(), "LANENAR_.ttf");
        button.setTypeface(font);


        boolean sendToServer = false;
        try {
            sendToServer = new SendGCM().execute(getApplicationContext()).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sendToServer) {
            progressBar.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            button.setEnabled(true);
            display.setText("Felicidades, su configuración se ha procesado correctamente.\nYa puede" +
                    " utilizar Revisores Metro BCN! ");
        }
        else {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Se ha producido un error de conexión. Inténtelo de nuevo más tarde.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }



    }
}
