package com.example.alphabbasket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;

import com.example.alphabbasket.fragmentos.CerrarSesion;
import com.example.alphabbasket.fragmentos.Perfil;
import com.example.alphabbasket.model.Cliente;
import com.google.android.material.tabs.TabLayout;


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
        setContentView(R.layout.activity_main);
        iniciarComponentes();

    }



    private Cliente getCliente() {
        Bundle extras = getIntent().getExtras();
        String id = extras.getString("id");
        String nombres = extras.getString("nombres");
        String apellidos = extras.getString("apellidos");
        String correo = extras.getString("correo");
        String edad = extras.getString("edad");
        String clave = extras.getString("clave");
        cliente =new Cliente(id, nombres, apellidos, correo, edad, clave);
        return cliente;
    }
    private void iniciarComponentes(){
        this.tabMain=(TabLayout)findViewById(R.id.tabsMain);
        this.tabMain.addOnTabSelectedListener(this);
        this.fragmentView=(ConstraintLayout)findViewById(R.id.constraintlayoutFragment);


        this.fragmentCerrarSesion=new CerrarSesion();
        fragmentPerfil=new Perfil(R.id.constraintlayoutFragment);
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

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}