package com.example.abel.jsonparse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class individual_prestamo extends Activity {

    static final String KEY_CLAVE = "clave_prestamo"; // parent node
    static final String KEY_FECHA = "fecha";
    static final String KEY_NOMBRE = "nombre_sol";
    static final String KEY_AREA = "area_sol";
    static final String KEY_DESCRIPCION = "descripcion";
    static final String KEY_RECIBIDO = "recibido";
    static final String KEY_ENTREGADO = "entregado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_prestamo);

        // getting intent data
        Intent in = getIntent();

        // Get JSON values from previous intent
        String clave_prestamo = in.getStringExtra(KEY_CLAVE);
        String fecha = in.getStringExtra(KEY_FECHA);
        String nombre_sol = in.getStringExtra(KEY_NOMBRE);
        String area_sol = in.getStringExtra(KEY_AREA);
        String descripcion = in.getStringExtra(KEY_DESCRIPCION);
        String recibido = in.getStringExtra(KEY_RECIBIDO);
        String entregado = in.getStringExtra(KEY_ENTREGADO);

        // Displaying all values on the screen
        TextView clv = (TextView) findViewById(R.id.clave2);
        TextView fec = (TextView) findViewById(R.id.fecha2);
        TextView nom = (TextView) findViewById(R.id.nombre2);
        TextView are = (TextView) findViewById(R.id.area2);
        TextView des = (TextView) findViewById(R.id.descripcion2);
        TextView rec = (TextView) findViewById(R.id.recibido2);
        TextView ent = (TextView) findViewById(R.id.entregado2);

        clv.setText(clave_prestamo);
        fec.setText(fecha);
        nom.setText(nombre_sol);
        are.setText(area_sol);
        des.setText(descripcion);
        rec.setText(recibido);
        ent.setText(entregado);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_individual_prestamo, menu);
        return true;
    }

}
