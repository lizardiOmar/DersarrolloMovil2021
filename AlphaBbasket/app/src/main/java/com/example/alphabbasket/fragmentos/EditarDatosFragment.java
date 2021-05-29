package com.example.alphabbasket.fragmentos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class EditarDatosFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private int columna_index;

    private String edad, nombres, apellidos, id, correo;
    private Cliente cliente;
    private Spinner spinnerDatosCliente;
    private final String[] datos = { "...", "Nombres", "Apellidos", "Edad"};
    private String datoNuevo="";
    private int aux=0;
    private CustomSpinnerAdapter customAdapter;
    private Button buttonGuardarCambios;
    private EditText editTextCambiarDato;
    private TextView textViewResultadoEditarDatos;

    public static EditarDatosFragment newInstance() {
        EditarDatosFragment fragment = new EditarDatosFragment();




        return fragment;
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

        customAdapter=new CustomSpinnerAdapter(view.getContext(),datos);
        spinnerDatosCliente = (Spinner) view.findViewById(R.id.spinnerDatosCliente);
        buttonGuardarCambios=(Button)view.findViewById(R.id.buttonGuardarCambio);
        editTextCambiarDato=(EditText)view.findViewById(R.id.editTextDatoEditable);
        textViewResultadoEditarDatos=(TextView)view.findViewById(R.id.textViewResultadoEditarDatos);

        spinnerDatosCliente.setAdapter(customAdapter);
        spinnerDatosCliente.setOnItemSelectedListener(this);
        Bundle extras=getActivity().getIntent().getExtras();
        nombres = extras.getString("nombres");
        apellidos = extras.getString("apellidos");
        edad = extras.getString("edad");
        correo=extras.getString("correo");
        id = extras.getString("id");

        editTextCambiarDato.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {

                buttonGuardarCambios.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if(editTextCambiarDato.toString().trim().equals(nombres)||editTextCambiarDato.toString().trim().equals(apellidos)||editTextCambiarDato.toString().trim().equals(edad)){
                            textViewResultadoEditarDatos.setText("No hay nungún cambio detectado en el dato "+datos[columna_index+1]);
                            editTextCambiarDato.setError("No hay cambios");
                        }else{
                            if(editable.length()>0){
                                final String dato=editTextCambiarDato.getText().toString().trim();
                                final String mensaje="Cambiar "+datos[columna_index+1]+" en el usuario "+id+" por "+dato;
                                final String columna=Integer.toString(columna_index);
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.clientes,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String ServerResponse) {
                                                String uri =  Constantes.clientes+"/?correo="+correo;
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
                                                textViewResultadoEditarDatos.setText(volleyError.getLocalizedMessage());
                                            }
                                        }) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        // Creating Map String Params.
                                        Map<String, String> params = new HashMap<String, String>();
                                        // Adding All values to Params.
                                        params.put("id", id);
                                        params.put("dato", dato);
                                        params.put("index_columna", columna);
                                        return params;
                                    }
                                };
                                // Creating RequestQueue.
                                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                                // Adding the StringRequest object into requestQueue.
                                requestQueue.add(stringRequest);
                            }else{
                                editTextCambiarDato.setError("Escribe algo.");
                            }
                        }
                    }

                });
            }
        });





        return view;
    }

    @Override
    public void onItemSelected(final AdapterView<?> adapterView, View view, int i, long l) {
        //se asigna el indice de la selección a una variable final interna
        final int x=i;
        if(x!=0){
            columna_index=x-1;
            textViewResultadoEditarDatos.setText("Dato seleccionado: '"+datos[x]+"'."+columna_index);
        }else{
            textViewResultadoEditarDatos.setText("Ningún dato seleccionado.");
            columna_index=100;
        }

        if(!editTextCambiarDato.getText().toString().equals("")){
            //Se crea una ventana de alerta en una implementación interna (c=context)
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                            editTextCambiarDato.setHint(nombres);
                            editTextCambiarDato.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                            editTextCambiarDato.setEnabled(true);
                            aux=x;
                            break;
                        //Si el item corresponde al 2, se habilita la opción de edición de apellidos
                        case 2:
                            buttonGuardarCambios.setText("Cambiar mis apellidos");
                            buttonGuardarCambios.setVisibility(View.VISIBLE);
                            editTextCambiarDato.setHint(apellidos);
                            editTextCambiarDato.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                            editTextCambiarDato.setEnabled(true);
                            aux=x;
                            break;
                        //Si el item corresponde al 3, se habilita la opción de edición de edad
                        case 3:
                            buttonGuardarCambios.setText("Cambiar mi edad");
                            buttonGuardarCambios.setVisibility(View.VISIBLE);
                            editTextCambiarDato.setHint(edad);
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
                        editTextCambiarDato.setHint(nombres);
                        editTextCambiarDato.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                        editTextCambiarDato.setEnabled(true);
                        aux=i;
                        break;
                    //Si el item corresponde al 2, se habilita la opción de edición de apellidos
                    case 2:
                        buttonGuardarCambios.setText("Cambiar mis apellidos");
                        buttonGuardarCambios.setVisibility(View.VISIBLE);
                        editTextCambiarDato.setHint(apellidos);
                        editTextCambiarDato.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                        editTextCambiarDato.setEnabled(true);
                        aux=i;
                        break;
                    //Si el item corresponde al 3, se habilita la opción de edición de edad
                    case 3:
                        buttonGuardarCambios.setText("Cambiar mi edad");
                        buttonGuardarCambios.setVisibility(View.VISIBLE);
                        editTextCambiarDato.setHint(edad);
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