package com.example.alphabbasket.dao;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alphabbasket.model.Cliente;
import com.example.alphabbasket.model.Constantes;

import java.util.HashMap;
import java.util.Map;

public class ClienteDAO {
    private Boolean aux;
    private Cliente clienteNuevo=null;

    public boolean registrarCliente(Cliente cliente, Context context){
        aux=false;
        if(cliente!=null){
            clienteNuevo=cliente;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.clientes,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String ServerResponse) {
                            // Showing response message coming from server.
                            aux=true;
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            // Showing error message if something goes wrong.
                            aux=false;
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
                    params.put("clave", clienteNuevo.getContrasena());
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
        return aux;
    }
}
