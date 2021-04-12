package com.example.androidbuybasket;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText txtNombre, txtApellido, txtCorreo, txtEdad, txtPass;
    TextView lblLogin;
    String nombre, apellido, correo, pass, edad;

    Button btnRegistrar;
    Boolean flagInfo;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtApellido = (EditText) findViewById(R.id.txtApellido);
        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        txtEdad = (EditText) findViewById(R.id.txtEdad);
        txtPass = (EditText) findViewById(R.id.txtPass);
        lblLogin = (TextView) findViewById(R.id.lblRegistro);
        progressDialog = new ProgressDialog(this);
        btnRegistrar = (Button) findViewById(R.id.btnAcceso);
        lblLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_LONG).show();
               
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre = txtNombre.getText().toString().trim();
                apellido = txtApellido.getText().toString().trim();
                correo = txtCorreo.getText().toString().trim();
                if(TextUtils.isEmpty(txtEdad.getText().toString())){
                    txtNombre.setError("La edad es obligatoria.");
                    flagInfo = false;
                }else{
                    edad = txtEdad.getText().toString().trim();
                }
                pass = txtPass.getText().toString().trim();
                if(TextUtils.isEmpty(nombre)){
                    txtNombre.setError("Por favor, escribe tu primer nombre");
                    flagInfo = false;
                }else{
                    flagInfo = true;
                    if(TextUtils.isEmpty(apellido)){
                        txtApellido.setError("Debes escribir tu apellido.");
                        flagInfo = false;
                    }else{
                        flagInfo = true;
                        if(TextUtils.isEmpty(correo)){
                            txtCorreo.setError("Tú correo es necesario.");
                            flagInfo = false;
                        }else{
                            flagInfo = true;
                            if(Integer.parseInt(edad)<18){
                                txtEdad.setError("Debes ser mayor de 18 años.");
                                flagInfo = false;
                            }else{
                                flagInfo = true;
                                if(TextUtils.isEmpty(pass)){
                                    txtPass.setError("Debes elegir una contraseña..");
                                    flagInfo = false;
                                }else{
                                    flagInfo = true;
                                }

                            }

                        }

                    }

                }
                if(flagInfo){

                    // Showing progress dialog at user registration time.
                    progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
                    progressDialog.show();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.urlCrearCliente,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String ServerResponse) {

                                    // Hiding the progress dialog after all task complete.
                                    progressDialog.dismiss();

                                    // Showing response message coming from server.
                                    Toast.makeText(MainActivity.this, "Cliente creado", Toast.LENGTH_LONG).show();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {

                                    // Hiding the progress dialog after all task complete.
                                    progressDialog.dismiss();

                                    // Showing error message if something goes wrong.
                                    Toast.makeText(MainActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {

                            // Creating Map String Params.
                            Map<String, String> params = new HashMap<String, String>();
                            // Adding All values to Params.
                            params.put("id", "0");
                            params.put("nombres", nombre);
                            params.put("apellidos", apellido);
                            params.put("correo", correo);
                            params.put("edad", edad);
                            params.put("contrasena", pass);

                            return params;
                        }

                    };
                    // Creating RequestQueue.
                    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                    // Adding the StringRequest object into requestQueue.
                    requestQueue.add(stringRequest);
                }
            }
        });

    }
    public void login(View view){
        Intent i = new Intent(this, LoginActivity.class );
        startActivity(i);
    };
}