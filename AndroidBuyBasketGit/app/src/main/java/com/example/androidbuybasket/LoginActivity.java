package com.example.androidbuybasket;

import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidbuybasket.tools.Box;
import com.example.androidbuybasket.tools.Encriptador;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class LoginActivity extends AppCompatActivity {
    Button btnAcceso;
    EditText txtCorreo, txtContrasena;
    String correo, contrasena, passC;
    TextView lblLogin;
    ProgressDialog progressDialog;
    Boolean flagInfo, loginBool = false;
    private final Box box=new Box();
    private final Encriptador encriptador=new Encriptador();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iniciarComponentes();
        lblLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Navegando al registro.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), RegistroActivity.class );
                startActivity(i);
                finish();
            }
        });
        btnAcceso.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                flagInfo=validarForma();
                if(flagInfo){
                    correo = txtCorreo.getText().toString().trim();
                    contrasena = txtContrasena.getText().toString().trim();
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.urlGetPassCliente,
                            new Response.Listener<String>() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        String datos=jsonResponse.getString("datos");
                                        JSONObject jsonDatos = new JSONObject(datos);
                                        passC=jsonDatos.getString("contrasena");
                                    } catch (JSONException ex) {
                                        txtCorreo.setError("Verifica tu correo.");
                                        txtCorreo.setText("");
                                        Toast.makeText(LoginActivity.this, ex.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                    }
                                    loginBool=box.evaluarPass(txtContrasena, passC);
                                    if (loginBool){
                                        Toast.makeText(LoginActivity.this, "Sesión Iniciada", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(getApplicationContext(), ClienteActivity.class );
                                        i.putExtra("correo", correo);
                                        startActivity(i);
                                        finish();
                                    }else{
                                        Toast.makeText(LoginActivity.this, "Verifica la contraseña.", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(LoginActivity.this, "error inesperado", Toast.LENGTH_LONG).show();
                }

            }

        });
    }
    private Boolean validarForma(){
        Boolean aux=false;

        if(box.textViewEmpty(txtCorreo)){
            if(box.textViewEmpty(txtContrasena)){
                if(box.correoValido(txtCorreo)){
                    if(box.validarPass(txtContrasena)){
                        aux=true;
                    }
                }
            }
        }
        return aux;
    }
    private void iniciarComponentes(){
        this.lblLogin=(TextView)findViewById(R.id.lblRegistro);
        this.txtCorreo=(EditText)findViewById(R.id.txtCorreo);
        this.txtContrasena=(EditText)findViewById(R.id.txtPass);

        this.progressDialog = new ProgressDialog(this);
        this.btnAcceso=(Button)findViewById(R.id.btnAcceso);
    }
}