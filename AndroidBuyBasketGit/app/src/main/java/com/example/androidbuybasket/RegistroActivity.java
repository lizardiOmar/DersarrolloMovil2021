package com.example.androidbuybasket;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

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
import com.example.androidbuybasket.tools.Box;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {
    //Variables correspondientes a los 'EditText' del documento XML de esta activity
    EditText txtNombre, txtApellido, txtCorreo, txtEdad, txtPass;
    //Variable correspondiente al 'TextView' del documento XML de esta activity
    //Habilita la navegación hacia la activity LOGIN
    TextView lblLogin;
    //Clase correspondiente a los datos de la aplicación para enviarlos al servicio PHP
    private Cliente clienteNuevo;
    //Variable correspondiente al 'Button' del documento XML de esta activity
    //Realiza la solicitud al servidor mediante Volley
    Button btnRegistrar;
    //Indicador booleano de validación de los datos
    //Si es false no se enviará ninguna solicitud al servidor
    Boolean flagInfo;
    //Variable para mostrar una ventana de dialogo para comunicar al usuario que está sucediendo con su petición
    ProgressDialog progressDialog;

    //Clase interna que contiene diversas herramientas para verificar campos
    private final Box box = new Box();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Inicializar componentes lineas: 174-185
        this.iniciarComponentes();
        //Click listener para navegar hacia el login
        lblLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(RegistroActivity.this, "Navegando al login.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), LoginActivity.class );
                startActivity(i);
                finish();
            }
        });
        //Click listener para crear una nueva cuenta
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                flagInfo=validarForma();
                //Si la bandera que verifica los datos es verdadera
                if(flagInfo){
                    //Se muestra un mesaje de procesamiento de datos para el usuario
                    progressDialog.setMessage("Por favor espere Bbasket está procesando los datos.");
                    progressDialog.show();
                    clienteNuevo=new Cliente(
                            "0", txtNombre.getText().toString().trim(),
                            txtApellido.getText().toString().trim(),
                            txtCorreo.getText().toString().trim(),
                            txtEdad.getText().toString().trim(),
                            box.encriptarPass(txtPass)
                    );
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.urlCrearCliente,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String ServerResponse) {
                                    // Hiding the progress dialog after all task complete.
                                    progressDialog.dismiss();
                                    // Showing response message coming from server.
                                    Toast.makeText(RegistroActivity.this, "Cuenta de Bbasket creada con éxito", Toast.LENGTH_LONG).show();
                                    txtNombre.setText("");
                                    txtApellido.setText("");
                                    txtCorreo.setText("");
                                    txtEdad.setText("");
                                    txtPass.setText("");
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    // Hiding the progress dialog after all task complete.
                                    progressDialog.dismiss();
                                    // Showing error message if something goes wrong.
                                    Toast.makeText(RegistroActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            // Creating Map String Params.
                            Map<String, String> params = new HashMap<String, String>();
                            // Adding All values to Params.
                            params.put("id", clienteNuevo.getId());
                            params.put("nombres", clienteNuevo.getNombres());
                            params.put("apellidos", clienteNuevo.getApellidos());
                            params.put("correo", clienteNuevo.getCorreo());
                            params.put("edad", clienteNuevo.getEdad());
                            params.put("contrasena", clienteNuevo.getContrasena());
                            return params;
                        }
                    };
                    // Creating RequestQueue.
                    RequestQueue requestQueue = Volley.newRequestQueue(RegistroActivity.this);
                    // Adding the StringRequest object into requestQueue.
                    requestQueue.add(stringRequest);
                }else{
                    Toast.makeText(RegistroActivity.this, "Por favor, verifique los datos ingresados.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private Boolean validarForma(){
        Boolean aux=false;
        //La funcion textViewEmpty esta definida en la clase Box lineas: 10-20
        //Regresa false si el campo esta vacío
        if(box.textViewEmpty(txtNombre)){
            if(box.textViewEmpty(txtApellido)){
                if(box.textViewEmpty(txtCorreo)){
                    if(box.textViewEmpty(txtEdad)){
                        if(box.textViewEmpty(txtPass)){
                            //La funcion correoValido esta definida en la clase Box lineas: 22-40
                            //Regresa false si el correo ingresado ni sigue el formato definido "string+'@'+string+'.'+string"
                            //La funcion validarPass esta definida en la clase Box
                            //Regresa false si la contraseña ingresada tiene menos de 6 caracteres
                            if(box.validarPass(txtPass) && box.correoValido(txtCorreo)){
                                aux = true;
                            }else{
                                aux =  false;
                            }
                        }
                    }
                }
            }
        }
       return aux;
    }
    private void iniciarComponentes(){
        //Asignación de elementos del XML
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtApellido = (EditText) findViewById(R.id.txtApellido);
        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        txtEdad = (EditText) findViewById(R.id.txtEdad);
        txtPass = (EditText) findViewById(R.id.txtPass);
        lblLogin = (TextView) findViewById(R.id.lblRegistro);
        btnRegistrar = (Button) findViewById(R.id.btnAcceso);

        progressDialog = new ProgressDialog(this);
    }
}