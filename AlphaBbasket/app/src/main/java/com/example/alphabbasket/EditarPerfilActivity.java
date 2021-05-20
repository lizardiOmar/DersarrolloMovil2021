package com.example.alphabbasket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alphabbasket.model.Cliente;
import com.example.alphabbasket.model.Constantes;
import com.example.alphabbasket.model.CustomSpinnerAdapter;
import com.example.alphabbasket.model.Direccion;
import com.example.alphabbasket.tools.Box;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;

public class EditarPerfilActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        AdapterView.OnItemSelectedListener {
    private LocationManager locManager;
    private LocationListener locListener;
    private Location location;
    private EditText editTextCambiarDato;
    private Button buttonGPS, buttonCancelar, buttonEliminarCuenta, buttonGuardarCambios;
    private Boolean flag = false;
    private String latitud, longitud, altura;
    private Fragment map;
    private Direccion direccion;
    private Cliente cliente;
    private Context c=this;
    private Spinner spinnerDatosCliente;
    private LatLng latLng;
    private Box box=new Box();
    private int aux=0;
    private String datoNuevo="";
    private final String[] datos = { "...", "Nombres", "Apellidos", "Edad"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        iniciarComponentes();
        agregarEventos();
        rastreoGPS();
    }

    private void agregarEventos() {

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                Bundle extras = getIntent().getExtras();
                String correo = extras.getString("correo");
                i.putExtra("correo", correo);
                startActivity(i);
                finish();
            }
        });
        buttonEliminarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setTitle("Eliminar cuenta");
                builder.setMessage(cliente.getNombres()+" de verdad deseas eliminar tu cuenta de B-basket?");
                builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Bundle extras = getIntent().getExtras();
                        String correo = extras.getString("correo");
                        String uri =  Constantes.clientes+"/?correo="+correo;
                        RequestQueue queue = Volley.newRequestQueue(EditarPerfilActivity.this);
                        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, uri,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String ServerResponse) {
                                        Toast.makeText(EditarPerfilActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        Toast.makeText(EditarPerfilActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                                    }
                                });
                        // Creating RequestQueue.
                        RequestQueue requestQueue = Volley.newRequestQueue(EditarPerfilActivity.this);
                        // Adding the StringRequest object into requestQueue.
                        requestQueue.add(stringRequest);
                        Intent i = new Intent(getApplicationContext(), RegistroActivity.class );
                        startActivity(i);
                        finish();
                    }

                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        buttonGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditarPerfilActivity.this, "BUSCANDO", Toast.LENGTH_LONG).show();
                rastreoGPS();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.direcciones,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String ServerResponse) {
                                // Showing response message coming from server
                                //Toast.makeText(EditarPerfilActivity.this, ServerResponse+" dijo el servidor", Toast.LENGTH_LONG).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                // Showing error message if something goes wrong.
                                //Toast.makeText(EditarPerfilActivity.this, volleyError.getLocalizedMessage()+" dijo el volley", Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        // Creating Map String Params.
                        Map<String, String> params = new HashMap<String, String>();
                        // Adding All values to Params.
                        params.put("id", direccion.getId());
                        params.put("latitud", direccion.getLatitud());
                        params.put("longitud", direccion.getLongitud());
                        params.put("altura", direccion.getAltura());
                        params.put("correo", direccion.getCorreo());
                        return params;
                    }
                };
                // Creating RequestQueue.
                RequestQueue requestQueue = Volley.newRequestQueue(EditarPerfilActivity.this);
                // Adding the StringRequest object into requestQueue.
                requestQueue.add(stringRequest);

            }
        });
        spinnerDatosCliente.setOnItemSelectedListener(this);
    }



    private void rastreoGPS() {
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(EditarPerfilActivity.this, "NO ACTIVASTE EL GPS", Toast.LENGTH_LONG).show();
            return;
        }
        location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location!=null){
            latitud=String.valueOf(location.getLatitude()).trim();
            longitud=String.valueOf(location.getLongitude()).trim();
            altura=String.valueOf(location.getAltitude()).trim();
            direccion=new Direccion("0", latitud, longitud, altura, cliente.getCorreo());
            latLng=new LatLng(location.getLatitude(), location.getLongitude());
            Toast.makeText(EditarPerfilActivity.this, "Ubicación: "+latLng.toString(), Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(EditarPerfilActivity.this, "Ubicación: desconocida", Toast.LENGTH_LONG).show();
        }


    }



    private void iniciarComponentes() {

        Bundle extras = getIntent().getExtras();
        String id = extras.getString("id");
        String nombres = extras.getString("nombres");
        String apellidos = extras.getString("apellidos");
        String edad = extras.getString("edad");
        String correo = extras.getString("correo");
        String clave = extras.getString("clave");

        cliente=new Cliente(id, nombres, apellidos, correo, clave, edad);
        buttonGuardarCambios=(Button)findViewById(R.id.buttonGuardarCambio);
        editTextCambiarDato=(EditText)findViewById(R.id.editTextDatoEditable);
        this.buttonCancelar = (Button) findViewById(R.id.buttonCancelar);
        this.buttonGPS = (Button) findViewById(R.id.buttonGPSBuscar);
        this.buttonEliminarCuenta = (Button) findViewById(R.id.buttonEliminar);

        this.spinnerDatosCliente = (Spinner) findViewById(R.id.spinnerDatosCliente);


        CustomSpinnerAdapter customAdapter=new CustomSpinnerAdapter(getApplicationContext(),datos);
        // Create an ArrayAdapter using the string array and a default spinner layout
        spinnerDatosCliente.setAdapter(customAdapter);
        mapaDisponible();

    }

    private void mapaDisponible() {
        if (map == null) {
            map = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            ((SupportMapFragment) map).getMapAsync(EditarPerfilActivity.this);

        }
        if (map != null) {

            Toast.makeText(this, "Mapa de Google disponible", Toast.LENGTH_SHORT).show();

        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "No hay Mapa de Google disponible", Toast.LENGTH_SHORT).show();
            return;
        }else{
            //Tipo de mapa normal
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            //Activa los pronosticos de trafico
            googleMap.setTrafficEnabled(true);
            //Permite activar el boton en el mapa para centrar la ubicación actual
            googleMap.setMyLocationEnabled(true);
            //Activa los edificos en el mapa
            googleMap.setBuildingsEnabled(true);
            //Nivel de zoom (calles y cuadras)
            googleMap.setMinZoomPreference(15);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(latLng);
            googleMap.moveCamera(cameraUpdate);

        }

    }

    @Override
    public void onItemSelected(final AdapterView<?> adapterView, View view, int i, long l) {

        //Si el editTextCambiarDato no está vacío
        if(!editTextCambiarDato.getText().toString().equals("")){
            //se asigna el indice de la selección a una variable final interna
            final int x=i;
            //Se crea una ventana de alerta en una implementación interna (c=context)
            AlertDialog.Builder builder = new AlertDialog.Builder(c);
            //Setters de la ventana (titulo, icono)
            builder.setTitle("Descartar cambios");
            builder.setIcon(R.drawable.editar_logo_small_30);
            builder.setMessage("¿deseas descartar los cambios en ("+ datos[aux]+" "+editTextCambiarDato.getText().toString().trim()+")?");
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                //Botón aceptar descartar la información
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //la variable dato nuevo se iguala con nada
                    datoNuevo="";
                    //se asigna ese valor al editTextCambiarDato
                    editTextCambiarDato.setText("");
                    //Se asigna al adaptador la selección mas reciente
                    adapterView.setSelection(x);
                    switch (x){
                        //Si el item corresponde al 0, no se habilita ninguna opción de edición
                        case 0:
                            editTextCambiarDato.setEnabled(false);
                            buttonGuardarCambios.setVisibility(View.INVISIBLE);
                            editTextCambiarDato.setHint("Selecciona una opción");
                            aux=x;
                            break;
                        //Si el item corresponde al 1, se habilita la opción de edición de nombres
                        case 1:
                            buttonGuardarCambios.setText("Cambiar mis nombres");
                            buttonGuardarCambios.setVisibility(View.VISIBLE);
                            editTextCambiarDato.setHint(cliente.getNombres());
                            editTextCambiarDato.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                            editTextCambiarDato.setEnabled(true);
                            aux=x;
                            break;
                        //Si el item corresponde al 2, se habilita la opción de edición de apellidos
                        case 2:
                            buttonGuardarCambios.setText("Cambiar mis apellidos");
                            buttonGuardarCambios.setVisibility(View.VISIBLE);
                            editTextCambiarDato.setHint(cliente.getApellidos());
                            editTextCambiarDato.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                            editTextCambiarDato.setEnabled(true);
                            aux=x;
                            break;
                        //Si el item corresponde al 3, se habilita la opción de edición de edad
                        case 3:
                            buttonGuardarCambios.setText("Cambiar mi edad");
                            buttonGuardarCambios.setVisibility(View.VISIBLE);
                            editTextCambiarDato.setHint(cliente.getEdad());
                            editTextCambiarDato.setInputType(InputType.TYPE_CLASS_NUMBER);
                            editTextCambiarDato.setEnabled(true);
                            aux=x;
                            break;
                    }
                }
            });
            //Botón para no descartar la información
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Sé le asigna a la variable datoNuevo el valor del contenido del editTextCambioDato
                    datoNuevo=editTextCambiarDato.getText().toString().trim();
                    //Sé vacía la variable datoNuevo
                    editTextCambiarDato.setText("");
                    //Se asigna al adaptador la selección del dato que se está editando
                    adapterView.setSelection(aux);
                    dialog.cancel();
                }
            });
            AlertDialog dialog = builder.create();

            dialog.show();
        //Sí el editTextDatoEditable está vacío
        }else{
            //Si la variable 'datoNuevo' no está vacía
            if(!datoNuevo.equals("")){
                //Se coloca su valor dentro del editTextCambiarDato
                editTextCambiarDato.setText(datoNuevo);
                //Y sé vacía la variable
                datoNuevo="";
                //Si la variable 'datoNuevo' sí está vacía
            }else{
                switch (i){
                    //Si el item corresponde al 0, no se habilita ninguna opción de edición
                    case 0:
                        editTextCambiarDato.setEnabled(false);
                        buttonGuardarCambios.setVisibility(View.INVISIBLE);
                        editTextCambiarDato.setHint("Selecciona una opción");
                        aux=i;
                        break;
                    //Si el item corresponde al 1, se habilita la opción de edición de nombres
                    case 1:
                        buttonGuardarCambios.setText("Cambiar mis nombres");
                        buttonGuardarCambios.setVisibility(View.VISIBLE);
                        editTextCambiarDato.setHint(cliente.getNombres());
                        editTextCambiarDato.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                        editTextCambiarDato.setEnabled(true);
                        aux=i;
                        break;
                    //Si el item corresponde al 2, se habilita la opción de edición de apellidos
                    case 2:
                        buttonGuardarCambios.setText("Cambiar mis apellidos");
                        buttonGuardarCambios.setVisibility(View.VISIBLE);
                        editTextCambiarDato.setHint(cliente.getApellidos());
                        editTextCambiarDato.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                        editTextCambiarDato.setEnabled(true);
                        aux=i;
                        break;
                    //Si el item corresponde al 3, se habilita la opción de edición de edad
                    case 3:
                        buttonGuardarCambios.setText("Cambiar mi edad");
                        buttonGuardarCambios.setVisibility(View.VISIBLE);
                        editTextCambiarDato.setHint(cliente.getEdad());
                        editTextCambiarDato.setInputType(InputType.TYPE_CLASS_NUMBER);
                        editTextCambiarDato.setEnabled(true);
                        aux=i;
                        break;
                }
            }
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}