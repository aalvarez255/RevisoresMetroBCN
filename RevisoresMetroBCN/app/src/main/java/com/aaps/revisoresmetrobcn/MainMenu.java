package com.aaps.revisoresmetrobcn;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by adrian on 15/08/14.
 */
public class MainMenu extends Activity {

    TextView titulo;

    public void gotoNewAlert(View v) {
        Intent intent = new Intent(this, NewAlert.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.menu);
        titulo = (TextView) findViewById(R.id.textView);

        Typeface font = Typeface.createFromAsset(getApplicationContext().getResources().getAssets(), "LANENAR_.ttf");
        titulo.setTypeface(font);

        TextView nuevaAlerta = (TextView) findViewById(R.id.textView2);
        TextView ultimasAlertas = (TextView) findViewById(R.id.textView3);
        TextView configuracion = (TextView) findViewById(R.id.textView4);

        Typeface font2 = Typeface.createFromAsset(getApplicationContext().getResources().getAssets(), "LANENAR_.ttf");
        nuevaAlerta.setTypeface(font2);
        ultimasAlertas.setTypeface(font2);
        configuracion.setTypeface(font2);

        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout3);
        final RelativeLayout relativeLayout2 = (RelativeLayout) findViewById(R.id.relativeLayout2);
        final RelativeLayout relativeLayout3 = (RelativeLayout) findViewById(R.id.relativeLayout4);

    }
}
