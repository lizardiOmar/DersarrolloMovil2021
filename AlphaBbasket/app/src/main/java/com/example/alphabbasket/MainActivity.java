package com.example.alphabbasket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alphabbasket.fragmentos.CerrarSesion;
import com.example.alphabbasket.fragmentos.Perfil;
import com.example.alphabbasket.model.Cliente;
import com.example.alphabbasket.model.Constantes;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private Cliente cliente;
    private Context c=this;
    private TabLayout tabMain;
    private ConstraintLayout fragmentView;
    private Perfil fragmentPerfil;
    private CerrarSesion fragmentCerrarSesion;
    private String correo;
    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        iniciarComponentes();

    }



    private void getCliente() {

        Bundle extras = getIntent().getExtras();
        correo = extras.getString("correo");
        String uri =  Constantes.clientes+"/?correo="+correo;
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

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
                                    jsonCliente.getString("clave"),
                                    jsonCliente.getString("edad"));
                            Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                        } catch (JSONException ex) {
                            Toast.makeText(MainActivity.this, ex.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            c=null;
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);

    }
    private void iniciarComponentes(){
        getCliente();
        this.tabMain=(TabLayout)findViewById(R.id.tabsMain);
        this.tabMain.addOnTabSelectedListener(this);
        this.fragmentView=(ConstraintLayout)findViewById(R.id.constraintlayoutFragment);
        this.fragmentPerfil=new Perfil(R.id.constraintlayoutFragment);
        this.fragmentPerfil.actualizarCliente(cliente);
        this.fragmentCerrarSesion=new CerrarSesion(correo);
        getSupportFragmentManager().beginTransaction().add(R.id.constraintlayoutFragment, fragmentPerfil).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.constraintlayoutFragment, fragmentCerrarSesion).commit();
        getSupportFragmentManager().beginTransaction().hide(fragmentCerrarSesion).commit();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int selected=tab.getPosition();
        switch (selected){
            case 0:{
                tab.setIcon(R.drawable.perfil_logo_small);
                getSupportFragmentManager().beginTransaction().show(fragmentPerfil).commit();
                break;
            }

            case 1:{
                tab.setIcon(R.drawable.buscar_logo_small);

                break;
            }
            case 2:{
                tab.setIcon(R.drawable.venta_logo_small);
                break;
            }
            case 3:{
                tab.setIcon(R.drawable.ayuda_logo_small);
                break;
            }
            case 4:{
                tab.setIcon(R.drawable.cerrar_logo_small);
                getSupportFragmentManager().beginTransaction().show(fragmentCerrarSesion).commit();
                break;
            }

        }
    }



    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        int selected=tab.getPosition();
        switch (selected) {
            case 0: {
                tab.setIcon(R.drawable.perfil_logo_off_small);
                getSupportFragmentManager().beginTransaction().hide(fragmentPerfil).commit();
                break;
            }

            case 1: {
                tab.setIcon(R.drawable.buscar_logo_off_small);
                break;
            }
            case 2: {
                tab.setIcon(R.drawable.venta_logo_off_small);
                break;
            }
            case 3: {
                tab.setIcon(R.drawable.ayuda_logo_off_small);
                break;
            }
            case 4: {
                tab.setIcon(R.drawable.cerrar_logo_off_small);
                getSupportFragmentManager().beginTransaction().hide(fragmentCerrarSesion).commit();
                break;
            }
        }
    }
    private void loadFragment(Fragment fragment) {

    }
    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}