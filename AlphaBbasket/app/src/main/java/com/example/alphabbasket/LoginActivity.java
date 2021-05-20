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
import com.example.alphabbasket.model.Constantes;
import com.example.alphabbasket.tools.Box;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private TextView textViewTitulo, textViewFrase, textViewSinCuenta;
    private EditText editTextCorreo, editTextContraseña;
    private Button buttonAcceder, buttonRegistrar;
    private Animation animationMoveRight;
    private final Box box=new Box();
    private String correo, contraseña;
    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String EMAIL_KEY = "email_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    // variable for shared preferences.
    SharedPreferences bbasketSharedPreferences;
    String preferencesCorreo, preferencesClave;
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
                Boolean flag=validarFormulario();
                if(flag){
                    correo = editTextCorreo.getText().toString().trim();
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.localGetPassCliente,
                            new Response.Listener<String>() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        String datos=jsonResponse.getString("datos");
                                        JSONObject jsonDatos = new JSONObject(datos);
                                        String passC=jsonDatos.getString("contrasena");
                                        Boolean loginBool=box.evaluarPass(editTextContraseña, passC);
                                        if (loginBool){
                                            //Toast.makeText(LoginActivity.this, "Sesión Iniciada", Toast.LENGTH_LONG).show();
                                            Intent i = new Intent(getApplicationContext(), MainActivity.class );
                                            i.putExtra("correo", correo);
                                            startActivity(i);
                                            finish();
                                        }else{
                                            Toast.makeText(LoginActivity.this, "Verifica la contraseña.", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException ex) {
                                        editTextCorreo.setError("Verifica tu correo.");
                                        editTextCorreo.setText("");
                                        Toast.makeText(LoginActivity.this, ex.getLocalizedMessage(), Toast.LENGTH_LONG).show();
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

                }
            }
        });
    }

    private void iniciarComponentes() {

        animationMoveRight = AnimationUtils.loadAnimation(this, R.anim.move_right);

        textViewTitulo = (TextView) findViewById(R.id.textViewBbasket);

        textViewFrase = (TextView) findViewById(R.id.textViewLema);

        textViewSinCuenta = (TextView) findViewById(R.id.textViewSinCuenta);

        editTextCorreo = (EditText) findViewById(R.id.editTextCorreoLogin);

        editTextContraseña = (EditText) findViewById(R.id.editTextClaveLogin);

        buttonAcceder = (Button) findViewById(R.id.buttonLogin);

        buttonRegistrar= (Button) findViewById(R.id.buttonNavegacionRegistro);


        bbasketSharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // in shared prefs inside het string method
        // we are passing key value as EMAIL_KEY and
        // default value is
        // set to null if not present.
        preferencesCorreo = bbasketSharedPreferences.getString(EMAIL_KEY, null);
        preferencesClave = bbasketSharedPreferences.getString(PASSWORD_KEY, null);

        animationMoveRight = AnimationUtils.loadAnimation(this, R.anim.move_right);
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