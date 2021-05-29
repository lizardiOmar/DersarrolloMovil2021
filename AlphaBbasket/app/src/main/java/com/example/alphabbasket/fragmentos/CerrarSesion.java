package com.example.alphabbasket.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;


public class CerrarSesion extends Fragment {

    private TextView textViewNombres;
    private Button buttonSalir;


    public static CerrarSesion newInstance() {
        return new CerrarSesion();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cerrar_sesion, container, false);
        textViewNombres=(TextView)view.findViewById(R.id.textViewNombresSalir);

        Bundle extras = getActivity().getIntent().getExtras();
        String nombres=extras.getString("nombres");
        textViewNombres.setText(nombres+".");
        buttonSalir=(Button)view.findViewById(R.id.buttonSalir);

        buttonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), LoginActivity.class );
                startActivity(i);
                getActivity().finish();
            }
        });
        return view;
    }
   
}