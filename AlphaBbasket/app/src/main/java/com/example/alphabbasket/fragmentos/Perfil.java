package com.example.alphabbasket.fragmentos;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
        buttonEditarCuenta=(Button)view.findViewById(R.id.buttonEditarPerfil);
        textViewNombre=(TextView)view.findViewById(R.id.nombre_text);


        return view;
    }

    public Perfil(int contentLayoutId) {
        super(contentLayoutId);
    }
    public void actualizarCliente(Cliente c){
        this.cliente=c;
    }
    public void setTextoNombres(String nombres){
//        textViewNombre.setText(nombres);
    }
    public Cliente getCliente() {
        return cliente;
    }

    private void agregarEventos() {
        textViewNombre.setText(getCliente().getNombres());
        buttonEditarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), EditarPerfilActivity.class );
                i.putExtra("id", cliente.getId());
                i.putExtra("nombres", cliente.getNombres());
                i.putExtra("apellidos", cliente.getApellidos());
                i.putExtra("edad", cliente.getEdad());
                i.putExtra("correo", cliente.getCorreo());
                i.putExtra("clave", cliente.getContrasena());
                startActivity(i);

            }
        });
    }
}