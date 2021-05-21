package com.example.alphabbasket.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.alphabbasket.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CerrarSesion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CerrarSesion extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "correo";

    // TODO: Rename and change types of parameters
    private String correo;

    public CerrarSesion() {
        // Required empty public constructor
    }

    public CerrarSesion(String correo) {
        this.correo = correo;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param correo Parameter 1.
     * @return A new instance of fragment CerrarSesion.
     */
    // TODO: Rename and change types and number of parameters
    public static CerrarSesion newInstance(String correo) {
        CerrarSesion fragment = new CerrarSesion(correo);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, correo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            correo = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cerrar_sesion, container, false);
        TextView textViewCorreo=(TextView)view.findViewById(R.id.textViewCorreoSalir);

        textViewCorreo.setText(correo);
        return view;
    }
}