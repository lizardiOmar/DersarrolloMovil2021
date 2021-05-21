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
    private Cliente cliente;
    private TextView textViewNombres;
    private Context context;
    private Button buttonSalir;
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
        textViewNombres=(TextView)view.findViewById(R.id.textViewNombresSalir);
        context=view.getContext();
        String uri =  Constantes.clientes+"/?correo="+correo;
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
                            //AQUI setea los valores en el xml
                            textViewNombres.setText(cliente.getNombres());

                            Toast.makeText(context, cliente.getNombres(), Toast.LENGTH_LONG).show();
                        } catch (JSONException ex) {
                            Toast.makeText(context, ex.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
        buttonSalir=(Button)view.findViewById(R.id.buttonSalir);
        buttonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), LoginActivity.class );
                startActivity(i);
            }
        });
        return view;
    }
   
}