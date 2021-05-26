package com.example.alphabbasket.dao;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alphabbasket.LoginActivity;
import com.example.alphabbasket.MainActivity;
import com.example.alphabbasket.model.Cliente;
import com.example.alphabbasket.model.Constantes;
import com.example.alphabbasket.tools.Box;
import com.example.alphabbasket.tools.Encriptador;

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

public class ClienteDAO {

    private static Boolean aux;
    private static Cliente cliente=null;
    private Context context;
    private Box box=new Box();



    public ClienteDAO(Context context) {
        this.context = context;
    }


    public static Cliente getClienteByCorreo(String correo, Context context){
        String uri =  Constantes.clientes+"/?correo="+correo;
        cliente=new Cliente();
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String stringCliente=jsonResponse.getString("cliente");
                            JSONObject jsonCliente = new JSONObject(stringCliente);
                            cliente=new Cliente(
                                    jsonCliente.getString("id"),
                                    jsonCliente.getString("nombres"),
                                    jsonCliente.getString("apellidos"),
                                    jsonCliente.getString("correo"),
                                    jsonCliente.getString("edad"),
                                    jsonCliente.getString("clave"));

                        } catch (JSONException ex) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);
        return cliente;
    }
    public static boolean registrarCliente(Cliente c,  Context context){
        cliente=c;
        aux=false;
        if(cliente!=null){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.clientes,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String ServerResponse) {
                            aux=true;
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            aux=false;
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
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            // Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);
        }else{
            aux=false;
        }
        cliente=null;
        return aux;
    }
}
