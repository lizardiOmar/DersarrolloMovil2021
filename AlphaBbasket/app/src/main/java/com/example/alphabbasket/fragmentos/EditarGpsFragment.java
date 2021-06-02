package com.example.alphabbasket.fragmentos;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alphabbasket.EditarPerfilActivity;
import com.example.alphabbasket.LoginActivity;
import com.example.alphabbasket.R;
import com.example.alphabbasket.model.Cliente;
import com.example.alphabbasket.model.Constantes;
import com.example.alphabbasket.model.Direccion;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;


public class EditarGpsFragment extends Fragment implements
        OnMapReadyCallback {
    private Cliente cliente;
    private Button buttonGuardarUbicacion, buttonEliminarUbicacion;
    private LocationManager locManager;
    private String latitud, longitud, altura;
    private Fragment map;
    private Direccion direccion;
    private LatLng latLng;
    private Location location;
    private TextView textViewLatitud, textViewLongitud, textViewAltura, textViewInformacion;
    private String agregarMSG="Agregar mi ubicación";
    private String cambiarMSG="Cambiar mi ubicación";

    public static EditarGpsFragment newInstance() {
        return new EditarGpsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editar_gps, container, false);
        Bundle extras = getActivity().getIntent().getExtras();
        cliente = new Cliente(extras.getString("id"), extras.getString("nombres"), extras.getString("apellidos"), extras.getString("correo"), extras.getString("edad"), extras.getString("clave"));
        buttonGuardarUbicacion = (Button) view.findViewById(R.id.buttonGPSBuscar);
        buttonGuardarUbicacion.setText(agregarMSG);
        buttonEliminarUbicacion=(Button)view.findViewById(R.id.buttonGPSBorrar);
        String uri =  Constantes.direcciones+"/?correo="+cliente.getCorreo();
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        textViewAltura=(TextView)view.findViewById(R.id.textViewAltura);
        textViewLatitud=(TextView)view.findViewById(R.id.textViewLatitud);
        textViewLongitud=(TextView)view.findViewById(R.id.textViewLongitud);
        textViewInformacion=(TextView)view.findViewById(R.id.textViewInformacion);
        map = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        ((SupportMapFragment) map).getMapAsync(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //textViewInfo.setText(response);
                        try {
                            JSONArray aux = new JSONArray(response);
                            JSONObject direccionJSON=aux.getJSONObject(0);
                            textViewLatitud.setText(direccionJSON.getString("latitud"));
                            textViewLongitud.setText(direccionJSON.getString("longitud"));
                            textViewAltura.setText(direccionJSON.getString("altura")+" metros");
                            textViewAltura.setTextColor(GREEN);
                            textViewLongitud.setTextColor(GREEN);
                            textViewLatitud.setTextColor(GREEN);
                            buttonGuardarUbicacion.setText(cambiarMSG);
                        } catch (JSONException ex) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textViewAltura.setTextColor(RED);
                textViewLongitud.setTextColor(RED);
                textViewLatitud.setTextColor(RED);
            }
        });
        queue.add(stringRequest);
        if(textViewAltura.getCurrentTextColor()!=GREEN){
            locManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            }

            location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            latitud=String.valueOf(location.getLatitude()).trim();
            longitud=String.valueOf(location.getLongitude()).trim();
            altura=String.valueOf(location.getAltitude()).trim();
            textViewAltura.setText(altura+" metros");
            textViewLatitud.setText(latitud);
            textViewLongitud.setText(longitud);
            latLng=new LatLng(location.getLatitude(), location.getLongitude());
        }else{
            latLng=new LatLng(Double.parseDouble(textViewLatitud.getText().toString().trim()), Double.parseDouble(textViewLongitud.getText().toString().trim()));
        }


        buttonGuardarUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonGuardarUbicacion.getText().toString().equals(cambiarMSG)){
                    if(textViewAltura.getCurrentTextColor()!=GREEN){
                        textViewInformacion.setText(cliente.getNombres() +" Agregar UBICACIÓN "+ cliente.getCorreo() +"?");
                        buttonGuardarUbicacion.setText(agregarMSG);
                        //POST cambiar ubicación existente
                    }else{
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        }
                        location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        latitud=String.valueOf(location.getLatitude()).trim();
                        longitud=String.valueOf(location.getLongitude()).trim();
                        altura=String.valueOf(location.getAltitude()).trim();
                        textViewLongitud.setText(longitud);
                        textViewLatitud.setText(latitud);
                        textViewAltura.setText(altura+" metros");
                        direccion=new Direccion("0", latitud, longitud, altura, cliente.getCorreo());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.direcciones,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String ServerResponse) {
                                        textViewInformacion.setText(ServerResponse);

                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        textViewInformacion.setText(volleyError.getLocalizedMessage());
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() {
                                // Creating Map String Params.
                                Map<String, String> params = new HashMap<String, String>();
                                // Adding All values to Params.
                                params.put("correo", direccion.getCorreo());
                                params.put("latitud", direccion.getLatitud());
                                params.put("longitud", direccion.getLongitud());
                                params.put("altura", direccion.getAltura());
                                return params;
                            }
                        };
                        // Creating RequestQueue.
                        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                        // Adding the StringRequest object into requestQueue.
                        requestQueue.add(stringRequest);

                    }
                }
                else if(buttonGuardarUbicacion.getText().toString().equals(agregarMSG)){
                    textViewInformacion.setText(cliente.getNombres() +" Cambiar UBICACIÓN "+ cliente.getCorreo() +"?");
                    textViewLongitud.setText(longitud);
                    textViewLatitud.setText(latitud);
                    textViewAltura.setText(altura+" metros");
                    textViewAltura.setTextColor(GREEN);
                    textViewLongitud.setTextColor(GREEN);
                    textViewLatitud.setTextColor(GREEN);
                    buttonGuardarUbicacion.setText(cambiarMSG);
                    //POST agregar ubicación nueva
                    direccion=new Direccion("0", latitud, longitud, altura, cliente.getCorreo());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.direcciones,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String ServerResponse) {
                                    textViewInformacion.setText(ServerResponse);

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    textViewInformacion.setText(volleyError.getLocalizedMessage());
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
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    // Adding the StringRequest object into requestQueue.
                    requestQueue.add(stringRequest);
                }
            }
        });
        buttonEliminarUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewInformacion.setText(cliente.getNombres() +" ELIMINAR UBICACIÓN "+ cliente.getCorreo() +"?");
                textViewLongitud.setText("n/a");
                textViewLatitud.setText("n/a");
                textViewAltura.setText("n/a");
                textViewAltura.setTextColor(WHITE);
                textViewLongitud.setTextColor(WHITE);
                textViewLatitud.setTextColor(WHITE);
                buttonGuardarUbicacion.setText(agregarMSG);
                Bundle extras = getActivity().getIntent().getExtras();
                String correo = extras.getString("correo");
                String uri =  Constantes.direcciones+"/?correo="+correo;
                StringRequest stringRequest = new StringRequest(Request.Method.DELETE, uri,
                        new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        textViewInformacion.setText("Ubicación por defecto eliminada. "+ServerResponse);
                    }
                    },
                        new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        textViewInformacion.setText("Ubicación no eliminada. Por que "+volleyError.getMessage());
                    }
                });
                // Creating RequestQueue.
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                // Adding the StringRequest object into requestQueue.
                requestQueue.add(stringRequest);
            }
        });
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        //Tipo de mapa normal
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        //Activa los pronosticos de trafico
        googleMap.setTrafficEnabled(true);
        //Permite activar el boton en el mapa para centrar la ubicación actual
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
            //Activa los edificos en el mapa
            googleMap.setBuildingsEnabled(true);
            //Nivel de zoom (calles y cuadras)
            googleMap.setMinZoomPreference(15);

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(latLng);
            googleMap.moveCamera(cameraUpdate);



    }
}