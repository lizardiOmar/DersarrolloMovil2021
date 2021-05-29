package com.example.alphabbasket.fragmentos;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alphabbasket.LoginActivity;
import com.example.alphabbasket.R;
import com.example.alphabbasket.dao.ClienteDAO;
import com.example.alphabbasket.model.Cliente;
import com.example.alphabbasket.model.Constantes;
import com.example.alphabbasket.tools.Box;

import java.util.HashMap;
import java.util.Map;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;


public class RegistroFragment extends Fragment {
    private Box box=new Box();
    private TextView textViewNombres, textViewApellidos, textViewCorreo, textViewEdad, textViewClave;
    private EditText editTextNombres, editTextApellidos, editTextCorreo, editTextEdad, editTextClave;
    private Button buttonRegistrar;
    private String nombres, apellidos, correo, edad, clave;
    public static RegistroFragment newInstance() {
        return new RegistroFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_registro, container, false);
        this.editTextNombres=(EditText)view.findViewById(R.id.editTextNombres);
        this.editTextApellidos=(EditText)view.findViewById(R.id.editTextApellidos);
        this.editTextCorreo=(EditText)view.findViewById(R.id.editTextCorreo);
        this.editTextEdad=(EditText)view.findViewById(R.id.editTextEdad);
        this.editTextClave=(EditText)view.findViewById(R.id.editTextClaveRegistro);
        this.textViewNombres=(TextView)view.findViewById(R.id.textViewNombresRegistro);

        nombres="";
        apellidos="";
        correo="";
        edad="";
        clave="";

        this.textViewApellidos=(TextView)view.findViewById(R.id.textViewApellidosRegistro);
        this.textViewCorreo=(TextView)view.findViewById(R.id.textViewCorreoRegistro);
        this.textViewEdad=(TextView)view.findViewById(R.id.textViewEdadRegistro);
        this.textViewClave=(TextView)view.findViewById(R.id.textViewClaveRegistro);

        this.textViewNombres.setText("¿Cómo te llamas?");
        this.textViewApellidos.setText("¿Cómo te apellidas?");
        this.textViewCorreo.setText("¿Cuál es tú correo electrónico?");
        this.textViewEdad.setText("¿Cuántos años tienes?");
        this.textViewClave.setText("Elige una contraseña:");

        this.buttonRegistrar=(Button)view.findViewById(R.id.buttonRegistrarFragment);

        this.buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(box.editTextEmpty(editTextNombres)){
                    if(box.editTextEmpty(editTextApellidos)){
                        if(box.correoValido(editTextCorreo)){
                            if(box.editTextEmpty(editTextEdad)){
                                if(box.editTextEmpty(editTextClave)){
                                    //REGISTRO
                                    final Cliente cliente=new Cliente("0", nombres, apellidos, correo, edad, clave);
                                    textViewClave.setText(clave);
                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.clientes,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String ServerResponse) {
                                                    Intent i = new Intent(getActivity().getApplicationContext(), LoginActivity.class );
                                                    startActivity(i);
                                                    getActivity().finish();
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError volleyError) {
                                                    textViewNombres.setText(volleyError.getLocalizedMessage());
                                                }
                                            }) {
                                        @Override
                                        protected Map<String, String> getParams() {
                                            // Creating Map String Params.
                                            Map<String, String> params = new HashMap<String, String>();
                                            // Adding All values to Params.
                                            params.put("id", cliente.getId());
                                            params.put("nombres", cliente.getNombres());
                                            params.put("apellidos", cliente.getApellidos());
                                            params.put("correo", cliente.getCorreo());
                                            params.put("edad", cliente.getEdad());
                                            params.put("clave", cliente.getContrasena());
                                            return params;
                                        }
                                    };
                                    // Creating RequestQueue.
                                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                                    // Adding the StringRequest object into requestQueue.
                                    requestQueue.add(stringRequest);

                                }
                            }
                        }
                    }
                }
            }
        });

        editTextNombres.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i2>=2){
                    textViewNombres.setTextColor(GREEN);
                    textViewNombres.setText("Perfecto!");
                    nombres=String.valueOf(charSequence);
                }else{
                    textViewNombres.setText("¿Cómo te llamas?");
                    textViewNombres.setTextColor(WHITE);
                    nombres="";
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editTextApellidos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i2>=2){
                    textViewApellidos.setTextColor(GREEN);
                    textViewApellidos.setText("Perfecto!");
                    apellidos=String.valueOf(charSequence);
                }else{
                    textViewApellidos.setText("¿Cómo te apellidas?");
                    textViewApellidos.setTextColor(WHITE);
                    apellidos="";
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editTextCorreo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(box.correoValido(editTextCorreo)){
                    textViewCorreo.setTextColor(GREEN);
                    textViewCorreo.setText("Perfecto!");
                    correo=String.valueOf(charSequence);
                }else{
                    textViewCorreo.setText("¿Cuál es tú correo electrónico?");
                    textViewCorreo.setTextColor(WHITE);
                    correo="";
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editTextEdad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!(i2>0)){
                    textViewEdad.setText("¿Cuántos años tienes?");
                    textViewEdad.setTextColor(WHITE);
                    edad="";
                }else{
                    if(Integer.parseInt(String.valueOf(charSequence))>17){
                        textViewEdad.setTextColor(GREEN);
                        textViewEdad.setText("Perfecto!");
                        edad=String.valueOf(charSequence);
                    }else{
                        editTextEdad.setError("Debes ser mayor de edad.");
                        edad="";
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editTextClave.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()>5){
                    textViewClave.setTextColor(GREEN);
                    textViewClave.setText("Perfecto!");
                    clave=box.encriptarPass(editTextClave);
                }else{
                    textViewClave.setText("Elige una contraseña:");
                    textViewClave.setTextColor(WHITE);
                    clave="";
                }
            }
        });

        return view;
    }
}