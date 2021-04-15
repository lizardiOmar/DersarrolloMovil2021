package com.example.androidbuybasket;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class ClienteActivity extends AppCompatActivity {
    private Cliente cliente;
    private TextView txtNombres, txtApellidos, txtCorreo, txtEdad, txtContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        this.txtNombres=(TextView)findViewById(R.id.txtNombres);
        this.txtApellidos=(TextView)findViewById(R.id.txtApellidos);
        this.txtCorreo=(TextView)findViewById(R.id.txtCorreo);
        this.txtEdad=(TextView)findViewById(R.id.txtEdad);
        this.txtContrasena=(TextView)findViewById(R.id.txtContrasena);
        RequestQueue queue = Volley.newRequestQueue(ClienteActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.urlAccederCliente,
                new Response.Listener<String>() {


                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(LoginActivity.this, response, Toast.LENGTH_LONG).show();
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String stringCliente=jsonResponse.getString("cliente");


                            JSONObject jsonCliente = new JSONObject(stringCliente);
                            //Integer id=jsonCliente.getInt("id");
                            cliente=new Cliente(
                                    jsonCliente.getString("id"),
                                    jsonCliente.getString("nombres"),
                                    jsonCliente.getString("apellidos"),
                                    jsonCliente.getString("correo"),
                                    jsonCliente.getString("edad"),
                                    jsonCliente.getString("contrasena"));
                            txtNombres.setText(cliente.getId()+". NOMBRES: "+cliente.getNombres());
                            txtApellidos.setText("APELLIDOS: "+cliente.getApellidos());
                            txtCorreo.setText("CORREO: "+cliente.getCorreo());
                            txtEdad.setText("EDAD: "+cliente.getEdad());
                            txtContrasena.setText("CONTRASEÑA: "+cliente.getContrasena());


                        } catch (JSONException ex) {

                            Toast.makeText(ClienteActivity.this, ex.getLocalizedMessage(), Toast.LENGTH_LONG).show();
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
                Bundle extras = getIntent().getExtras();
                String correo = extras.getString("correo");
                params.put("correo", correo);
                return params;
            }
        };
        queue.add(stringRequest);
    }
}