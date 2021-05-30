package com.example.alphabbasket.fragmentos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alphabbasket.LoginActivity;
import com.example.alphabbasket.MainActivity;
import com.example.alphabbasket.R;
import com.example.alphabbasket.model.Cliente;
import com.example.alphabbasket.model.Constantes;
import com.example.alphabbasket.model.CustomSpinnerAdapter;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static android.graphics.Color.GRAY;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

public class EditarDatosFragment extends Fragment{


   private Cliente cliente;
    private ImageButton imageButtonNombres, imageButtonApellidos, imageButtonEdad;
    private EditText editTextNombres, editTextApellidos, editTextEdad;
    private TextView textViewResultado;
    private Boolean nombresB, apellidosB, edadB;

    public static EditarDatosFragment newInstance() {
        return new EditarDatosFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editar_datos, container, false);

        Bundle extras=getActivity().getIntent().getExtras();

        cliente=new Cliente(extras.getString("id"), extras.getString("nombres"), extras.getString("apellidos"), extras.getString("correo"),extras.getString("edad"), extras.getString("clave"));

        editTextNombres=(EditText)view.findViewById(R.id.editTextNombres);
        editTextApellidos=(EditText)view.findViewById(R.id.editTextApellidos);
        editTextEdad=(EditText)view.findViewById(R.id.editTextEdad);

        nombresB=false;
        apellidosB=false;
        edadB=false;

        textViewResultado=(TextView)view.findViewById(R.id.textViewResultado);

        editTextNombres.setEnabled(false);
        editTextApellidos.setEnabled(false);
        editTextEdad.setEnabled(false);

        editTextNombres.setHint(cliente.getNombres());
        editTextApellidos.setHint(cliente.getApellidos());
        editTextEdad.setHint(cliente.getEdad());

        imageButtonNombres=(ImageButton)view.findViewById(R.id.imageButtonNombres);
        imageButtonApellidos=(ImageButton)view.findViewById(R.id.imageButtonApellidos);
        imageButtonEdad=(ImageButton)view.findViewById(R.id.imageButtonEdad);

        imageButtonNombres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nombresB==false){
                    editTextNombres.setEnabled(true);
                    editTextNombres.setPressed(true);

                    imageButtonNombres.setImageIcon(Icon.createWithResource(getActivity().getApplicationContext(), R.drawable.editar_logo_small_30_off));
                    nombresB=true;
                }else{
                    editTextNombres.setEnabled(false);
                    editTextNombres.setPressed(false);
                    imageButtonNombres.setImageIcon(Icon.createWithResource(getActivity().getApplicationContext(), R.drawable.editar_logo_small_30));
                    if(editTextNombres.getText().toString().trim().equals(cliente.getNombres())){
                        textViewResultado.setText("Sin cambios detectados");
                        editTextNombres.setText("");
                    }else{
                        if(editTextNombres.getText().toString().length()>0){
                            final String nombre=editTextNombres.getText().toString().trim();
                            final String columna="0";
                            final String id=cliente.getId();
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.clientes,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String ServerResponse) {
                                            String uri =  Constantes.clientes+"/?correo="+cliente.getCorreo();
                                            RequestQueue queue = Volley.newRequestQueue(getContext());
                                            StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            //textViewInfo.setText(response);
                                                            try {
                                                                JSONObject jsonCliente = new JSONObject(response);
                                                                //Integer id=jsonCliente.getInt("id");
                                                                Cliente cliente=new Cliente(
                                                                        jsonCliente.getString("id"),
                                                                        jsonCliente.getString("nombres"),
                                                                        jsonCliente.getString("apellidos"),
                                                                        jsonCliente.getString("correo"),
                                                                        jsonCliente.getString("edad"),
                                                                        jsonCliente.getString("clave"));
                                                                Intent i = new Intent(getContext(), MainActivity.class );
                                                                i.putExtra("id", cliente.getId());
                                                                i.putExtra("nombres", cliente.getNombres());
                                                                i.putExtra("apellidos", cliente.getApellidos());
                                                                i.putExtra("edad", cliente.getEdad());
                                                                i.putExtra("correo", cliente.getCorreo());
                                                                i.putExtra("clave", cliente.getContrasena());
                                                                startActivity(i);
                                                                getActivity().finish();

                                                            } catch (JSONException ex) {

                                                            }

                                                        }
                                                    }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                }
                                            });
                                            queue.add(stringRequest);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {
                                            textViewResultado.setText(volleyError.getLocalizedMessage());
                                        }
                                    }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    // Creating Map String Params.
                                    Map<String, String> params = new HashMap<String, String>();
                                    // Adding All values to Params.
                                    params.put("id", id);
                                    params.put("dato", nombre);
                                    params.put("index_columna", columna);
                                    return params;
                                }
                            };
                            // Creating RequestQueue.
                            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                            // Adding the StringRequest object into requestQueue.
                            requestQueue.add(stringRequest);
                        }else{
                            textViewResultado.setText("No es posible guardar el campo 'nombre' vacío.");
                            editTextNombres.setText("");
                        }
                    }
                    nombresB=false;
                }
            }
        });

        imageButtonApellidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(apellidosB==false){
                    editTextApellidos.setEnabled(true);
                    editTextApellidos.setPressed(true);
                    imageButtonApellidos.setImageIcon(Icon.createWithResource(getActivity().getApplicationContext(), R.drawable.editar_logo_small_30_off));
                    apellidosB=true;
                }else{
                    editTextApellidos.setEnabled(false);
                    editTextApellidos.setPressed(false);
                    imageButtonApellidos.setImageIcon(Icon.createWithResource(getActivity().getApplicationContext(), R.drawable.editar_logo_small_30));
                    if(editTextApellidos.getText().toString().trim().equals(cliente.getApellidos())){
                        textViewResultado.setText("Sin cambios detectados");
                        editTextApellidos.setText("");
                    }else{
                        if(editTextApellidos.getText().toString().length()>0){
                            final String apellido=editTextApellidos.getText().toString().trim();
                            final String columna="1";
                            final String id=cliente.getId();
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.clientes,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String ServerResponse) {
                                            String uri =  Constantes.clientes+"/?correo="+cliente.getCorreo();
                                            RequestQueue queue = Volley.newRequestQueue(getContext());
                                            StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            //textViewInfo.setText(response);
                                                            try {
                                                                JSONObject jsonCliente = new JSONObject(response);
                                                                //Integer id=jsonCliente.getInt("id");
                                                                Cliente cliente=new Cliente(
                                                                        jsonCliente.getString("id"),
                                                                        jsonCliente.getString("nombres"),
                                                                        jsonCliente.getString("apellidos"),
                                                                        jsonCliente.getString("correo"),
                                                                        jsonCliente.getString("edad"),
                                                                        jsonCliente.getString("clave"));
                                                                Intent i = new Intent(getContext(), MainActivity.class );
                                                                i.putExtra("id", cliente.getId());
                                                                i.putExtra("nombres", cliente.getNombres());
                                                                i.putExtra("apellidos", cliente.getApellidos());
                                                                i.putExtra("edad", cliente.getEdad());
                                                                i.putExtra("correo", cliente.getCorreo());
                                                                i.putExtra("clave", cliente.getContrasena());
                                                                startActivity(i);
                                                                getActivity().finish();

                                                            } catch (JSONException ex) {

                                                            }

                                                        }
                                                    }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                }
                                            });
                                            queue.add(stringRequest);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {
                                            textViewResultado.setText(volleyError.getLocalizedMessage());
                                        }
                                    }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    // Creating Map String Params.
                                    Map<String, String> params = new HashMap<String, String>();
                                    // Adding All values to Params.
                                    params.put("id", id);
                                    params.put("dato", apellido);
                                    params.put("index_columna", columna);
                                    return params;
                                }
                            };
                            // Creating RequestQueue.
                            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                            // Adding the StringRequest object into requestQueue.
                            requestQueue.add(stringRequest);
                        }else{
                            textViewResultado.setText("No es posible guardar el cmapo 'apellido' vacío.");
                            editTextApellidos.setText("");
                        }
                    }
                    apellidosB=false;
                }
            }
        });

        imageButtonEdad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edadB==false){
                    editTextEdad.setEnabled(true);
                    editTextEdad.setPressed(true);
                    imageButtonEdad.setImageIcon(Icon.createWithResource(getActivity().getApplicationContext(), R.drawable.editar_logo_small_30_off));
                    edadB=true;
                }else{
                    editTextEdad.setEnabled(false);
                    editTextEdad.setPressed(false);
                    imageButtonEdad.setImageIcon(Icon.createWithResource(getActivity().getApplicationContext(), R.drawable.editar_logo_small_30));
                    if(editTextEdad.getText().toString().trim().equals(cliente.getEdad())){
                        textViewResultado.setText("Sin cambios detectados");
                        editTextEdad.setText("");
                    }else{
                        if(editTextEdad.getText().toString().length()>0){
                            final String edad=editTextEdad.getText().toString().trim();
                            final String columna="2";
                            final String id=cliente.getId();
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.clientes,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String ServerResponse) {
                                            String uri =  Constantes.clientes+"/?correo="+cliente.getCorreo();
                                            RequestQueue queue = Volley.newRequestQueue(getContext());
                                            StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            //textViewInfo.setText(response);
                                                            try {
                                                                JSONObject jsonCliente = new JSONObject(response);
                                                                //Integer id=jsonCliente.getInt("id");
                                                                Cliente cliente=new Cliente(
                                                                        jsonCliente.getString("id"),
                                                                        jsonCliente.getString("nombres"),
                                                                        jsonCliente.getString("apellidos"),
                                                                        jsonCliente.getString("correo"),
                                                                        jsonCliente.getString("edad"),
                                                                        jsonCliente.getString("clave"));
                                                                Intent i = new Intent(getContext(), MainActivity.class );
                                                                i.putExtra("id", cliente.getId());
                                                                i.putExtra("nombres", cliente.getNombres());
                                                                i.putExtra("apellidos", cliente.getApellidos());
                                                                i.putExtra("edad", cliente.getEdad());
                                                                i.putExtra("correo", cliente.getCorreo());
                                                                i.putExtra("clave", cliente.getContrasena());
                                                                startActivity(i);
                                                                getActivity().finish();

                                                            } catch (JSONException ex) {

                                                            }

                                                        }
                                                    }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {

                                                }
                                            });
                                            queue.add(stringRequest);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {
                                            textViewResultado.setText(volleyError.getLocalizedMessage());
                                        }
                                    }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    // Creating Map String Params.
                                    Map<String, String> params = new HashMap<String, String>();
                                    // Adding All values to Params.
                                    params.put("id", id);
                                    params.put("dato", edad);
                                    params.put("index_columna", columna);
                                    return params;
                                }
                            };
                            // Creating RequestQueue.
                            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                            // Adding the StringRequest object into requestQueue.
                            requestQueue.add(stringRequest);
                        }else{
                            textViewResultado.setText("No es posible guardarel campo 'edad' vacío.");
                            editTextEdad.setText("");
                        }
                    }
                    edadB=false;
                }
            }
        });
        return view;
    }


}