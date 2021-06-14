package com.example.alphabbasket.model;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alphabbasket.LoginActivity;
import com.example.alphabbasket.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RecyclerViewAdapterAyuda extends RecyclerView.Adapter<RecyclerViewAdapterAyuda.ViewHolder>{
    private TextView titulo, ayudaContenido;
    private Button btSi, btNo;
    private View v;
    private String idAyuda, idCliente;

    public RecyclerViewAdapterAyuda(String idCli) {
        this.idCliente=idCli;
    }

    @Override
    public RecyclerViewAdapterAyuda.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_marcas, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(
            RecyclerViewAdapterAyuda.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public ViewHolder(final View itemView) {
            super(itemView);

            titulo=(TextView)v.findViewById(R.id.textViewTituloAyuda);
            ayudaContenido=(TextView)v.findViewById(R.id.textViewContenidoAyuda);
            RequestQueue queue = Volley.newRequestQueue(v.getContext());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Constantes.ayuda,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //textViewInfo.setText(response);
                            try {
                                JSONObject jsonAyuda = new JSONObject(response);
                                titulo.setText(jsonAyuda.getString("titulo"));
                                ayudaContenido.setText(jsonAyuda.getString("descripcion"));
                                idAyuda = jsonAyuda.getString("id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        },new Response.ErrorListener(){
                            @Override
                            public void onErrorResponse (VolleyError error) {
                            }
                    });
            queue.add(stringRequest);

            btSi=(Button)v.findViewById(R.id.buttonPositivo);
            btSi.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    eventoBotones(btSi.getText().toString());
                }
            });

            btNo=(Button)v.findViewById(R.id.buttonNegativo);
            btNo.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    eventoBotones(btNo.getText().toString());
                }
            });

        }
    }

    public void eventoBotones(final String res){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.ayuda,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        //mensaaje toast
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //mensaaje toast
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.
                params.put("id", idAyuda);
                params.put("util", res);
                params.put("idCliente", idCliente);
                return params;
            }
        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
}
