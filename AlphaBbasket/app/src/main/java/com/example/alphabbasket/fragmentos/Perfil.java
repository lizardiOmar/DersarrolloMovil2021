package com.example.alphabbasket.fragmentos;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alphabbasket.EditarPerfilActivity;
import com.example.alphabbasket.R;
import com.example.alphabbasket.model.Cliente;
import com.google.android.material.tabs.TabLayout;


public class Perfil extends Fragment {
    private Button buttonEditarCuenta;
    private Cliente cliente;
    private TextView textViewNombre;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        //Asignar clase Java a XML
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        textViewNombre=(TextView)view.findViewById(R.id.nombre_text);
        Bundle extras = getActivity().getIntent().getExtras();
        String nombres = extras.getString("nombres");
        textViewNombre.setText(nombres);
        return view;
    }

    public Perfil(int contentLayoutId) {
        super(contentLayoutId);
    }

    private void agregarEventos() {
        textViewNombre.setText(cliente.getNombres());
        buttonEditarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), EditarPerfilActivity.class );

                startActivity(i);

            }
        });
    }
}