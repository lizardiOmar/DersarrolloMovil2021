package com.example.alphabbasket.fragmentos;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
import com.example.alphabbasket.EditarPerfilActivity;
import com.example.alphabbasket.MainActivity;
import com.example.alphabbasket.R;
import com.example.alphabbasket.model.Cliente;
import com.example.alphabbasket.model.Constantes;
import com.example.alphabbasket.model.Direccion;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;


public class Perfil extends Fragment implements TabLayout.OnTabSelectedListener {
    private Button buttonEditarCuenta;
    private Cliente cliente;
    private TextView textViewNombre, textViewTituloEditar, textViewFlagGps;
    private TabLayout tabEditar;
    private ConstraintLayout fragmentView;
    private EditarDatosFragment editarDatosFragment;
    private  EditarGpsFragment editarGpsFragment;
    private Direccion direccion;
    private Bundle extras;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        //Asignar clase Java a XML
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        textViewNombre=(TextView)view.findViewById(R.id.nombre_text);
        textViewTituloEditar=(TextView)view.findViewById(R.id.textViewTituloEditar);
        extras = getActivity().getIntent().getExtras();
        String nombres = extras.getString("nombres");
        textViewNombre.setText(nombres);
        this.tabEditar=(TabLayout)view.findViewById(R.id.tabsMain);
        this.tabEditar.addOnTabSelectedListener(this);
        this.textViewFlagGps=(TextView)view.findViewById(R.id.textViewFlagGps);

        this.fragmentView=(ConstraintLayout)view.findViewById(R.id.constraintlayoutFragmentInFragment);
        textViewTituloEditar.setText("Cambia tú información.");
        this.editarDatosFragment=new EditarDatosFragment();
        this.editarGpsFragment=new EditarGpsFragment();
        getChildFragmentManager().beginTransaction().add(R.id.constraintlayoutFragmentInFragment, editarDatosFragment).commit();
        getChildFragmentManager().beginTransaction().add(R.id.constraintlayoutFragmentInFragment, editarGpsFragment).commit();
        getChildFragmentManager().beginTransaction().hide(editarGpsFragment).commit();
        return view;
    }

    public Perfil(int contentLayoutId) {
        super(contentLayoutId);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0: {
                tab.setIcon(R.drawable.editar_logo_small_30);
                textViewTituloEditar.setText("Cambia tú información.");
                getChildFragmentManager().beginTransaction().show(editarDatosFragment).commit();
                break;
            }

            case 1: {
                tab.setIcon(R.drawable.gps_logo_small_30);
                getChildFragmentManager().beginTransaction().show(editarGpsFragment).commit();
                textViewTituloEditar.setText("Cambia tú ubicación.");
                break;
            }
            case 2: {
                tab.setIcon(R.drawable.bolsa_logo);
                textViewTituloEditar.setText("Cambia tú contraseña.");
                break;
            }
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0: {
                tab.setIcon(R.drawable.editar_logo_small_30_off);

                getChildFragmentManager().beginTransaction().hide(editarDatosFragment).commit();
                break;
            }

            case 1: {
                tab.setIcon(R.drawable.gps_logo_small_30_off);
                getChildFragmentManager().beginTransaction().hide(editarGpsFragment).commit();
                break;
            }
            case 2: {
                tab.setIcon(R.drawable.bolsa_logo_off);
                break;
            }
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}