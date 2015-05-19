package com.example.abel.jsonparse;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ListActivity {

    private ProgressDialog pDialog;

    // URL to get contacts JSON
    private static String url = "http://iin8.szhernandez.dx.am/aabbcc.json";


    // JSON Node names
    private final String KEY_TAG = "prestamos"; // parent node
    private final String KEY_CLAVE = "clave_prestamo"; // parent node
    private final String KEY_FECHA = "fecha";
    private final String KEY_NOMBRE = "nombre_sol";
    private final String KEY_AREA = "area_sol";
    private final String KEY_DESCRIPCION = "descripcion";
    private final String KEY_RECIBIDO = "recibido";
    private final String KEY_ENTREGADO = "entregado";

    // contacts JSONArray
    JSONArray prestamos = null;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> prestamosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prestamosList = new ArrayList<HashMap<String, String>>();

        ListView lv = getListView();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // getting values from selected ListItem
                String clv = ((TextView) view.findViewById(R.id.clave2_2)).getText().toString();
                String fec = ((TextView) view.findViewById(R.id.fecha2_2)).getText().toString();
                String nom = ((TextView) view.findViewById(R.id.nombre2_2)).getText().toString();
                String are = ((TextView) view.findViewById(R.id.area2_2)).getText().toString();
                String des = ((TextView) view.findViewById(R.id.descripcion2_2)).getText().toString();
                String rec = ((TextView) view.findViewById(R.id.recibido2_2)).getText().toString();
                String ent = ((TextView) view.findViewById(R.id.entregado2_2)).getText().toString();

                // Starting new intent
                Intent in = new Intent(getApplicationContext(), individual_prestamo.class);
                in.putExtra(KEY_CLAVE, clv);
                in.putExtra(KEY_FECHA, fec);
                in.putExtra(KEY_NOMBRE, nom);
                in.putExtra(KEY_AREA, are);
                in.putExtra(KEY_DESCRIPCION, des);
                in.putExtra(KEY_RECIBIDO, rec);
                in.putExtra(KEY_ENTREGADO, ent);
                startActivity(in);

            }
        });

        // Calling async task to get json
        new GetContacts().execute();
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    prestamos = jsonObj.getJSONArray(KEY_TAG);

                    // looping through All Contacts
                    for (int i = 0; i < prestamos.length(); i++) {
                        JSONObject c = prestamos.getJSONObject(i);

                        // Phone node is JSON Object
                        String clv = c.getString(KEY_CLAVE);
                        String fec = c.getString(KEY_FECHA);
                        String nom = c.getString(KEY_NOMBRE);
                        String are = c.getString(KEY_AREA);
                        String des = c.getString(KEY_DESCRIPCION);
                        String rec = c.getString(KEY_RECIBIDO);
                        String ent = c.getString(KEY_ENTREGADO);

                        // tmp hashmap for single contact
                        HashMap<String, String> prt = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        prt.put(KEY_CLAVE, clv);
                        prt.put(KEY_FECHA, fec);
                        prt.put(KEY_NOMBRE, nom);
                        prt.put(KEY_AREA, are);
                        prt.put(KEY_DESCRIPCION, des);
                        prt.put(KEY_RECIBIDO, rec);
                        prt.put(KEY_ENTREGADO, ent);

                        // adding contact to contact list
                        prestamosList.add(prt);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, prestamosList,
                    R.layout.list_prestamo, new String[] { KEY_CLAVE, KEY_FECHA,KEY_NOMBRE, KEY_AREA, KEY_DESCRIPCION, KEY_RECIBIDO, KEY_ENTREGADO}, new int[] { R.id.clave2_2,
                    R.id.fecha2_2, R.id.nombre2_2, R.id.area2_2, R.id.descripcion2_2, R.id.recibido2_2, R.id.entregado2_2 });

            setListAdapter(adapter);
        }
    }
}
