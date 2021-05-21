package com.example.alphabbasket;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.example.alphabbasket.dao.ClienteDAO;
import com.example.alphabbasket.model.Cliente;
import com.example.alphabbasket.model.Constantes;
import com.example.alphabbasket.tools.Box;
import com.example.alphabbasket.tools.Encriptador;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private TextView  textViewFrase;
    private EditText editTextCorreo, editTextContraseña;
    private Button buttonAcceder, buttonRegistrar;
    private Animation animationMoveRight;
    private Box box=new Box();
    private ClienteDAO clienteDAO;
    private String correo, contraseña;




    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Primera linea:
        iniciarComponentes();
        //Primera linea:
        agregarEventos();
    }

    private void agregarEventos() {

        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewFrase.startAnimation(animationMoveRight);
            }
        });
        buttonAcceder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(validarFormulario()) {
                    correo=editTextCorreo.getText().toString().trim();
                    contraseña=editTextContraseña.getText().toString().trim();
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.localGetPassCliente,
                            new Response.Listener<String>() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onResponse(String response) {
                                    String passC="";
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        String datos=jsonResponse.getString("datos");
                                        JSONObject jsonDatos = new JSONObject(datos);
                                        passC=jsonDatos.getString("contrasena");
                                        if(box.evaluarPass(contraseña, passC)){
                                            Intent i = new Intent(getApplicationContext(), MainActivity.class );
                                            i.putExtra("correo", correo);
                                            startActivity(i);
                                            finish();
                                        }else{
                                            Toast.makeText(LoginActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException ex) {
                                        Toast.makeText(getApplicationContext(), ex.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() {
                            // Creating Map String Params.
                            Map<String, String> params = new HashMap<String, String>();
                            // Adding All values to Params.
                            params.put("correo", correo);
                            return params;
                        }
                    };
                    queue.add(stringRequest);

                }else{
                    Toast.makeText(LoginActivity.this, "Revise los datos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void iniciarComponentes() {
        Context context=getApplicationContext();
        clienteDAO=new ClienteDAO(context);
        animationMoveRight = AnimationUtils.loadAnimation(this, R.anim.move_right);


        textViewFrase = (TextView) findViewById(R.id.textViewLema);


        editTextCorreo = (EditText) findViewById(R.id.editTextCorreoLogin);

        editTextContraseña = (EditText) findViewById(R.id.editTextClaveLogin);

        buttonAcceder = (Button) findViewById(R.id.buttonLogin);

        buttonRegistrar= (Button) findViewById(R.id.buttonNavegacionRegistro);




        animationMoveRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_right);
        Animation.AnimationListener animationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                textViewFrase.setText("Navegando al registro.");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent i = new Intent(getApplicationContext(), RegistroActivity.class );
                startActivity(i);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Toast.makeText(LoginActivity.this, "error al navegar al acceso.", Toast.LENGTH_SHORT).show();
            }
        };
        animationMoveRight.setAnimationListener(animationListener);

    }
    private Boolean validarFormulario() {
        Boolean aux = false;
        if (box.editTextEmpty(editTextCorreo)) {
            if (box.editTextEmpty(editTextContraseña)) {
                if (box.correoValido(editTextCorreo)) {
                    if (box.validarPass(editTextContraseña)) {
                        aux = true;
                    }
                }
            }
        }
        return aux;
    }



}