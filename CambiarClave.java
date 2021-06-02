package com.example.alphabbasket.fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alphabbasket.R;
import com.example.alphabbasket.model.Cliente;
import com.example.alphabbasket.model.Constantes;
import com.example.alphabbasket.tools.Box;

import org.json.JSONException;
import org.json.JSONObject;

public class CambiarClave extends Fragment {
    private EditText contActual;
    private EditText contNueva;
    private EditText contNuevaBis;
    private TextView informacion;
    private TextView aviso;
    private Button bEnviar;
    private Button bRestablecer;

    private String clave;
    private String correo = "heandsheofi@gmail.com";
    private Box box=new Box();

    public static CambiarClave newInstance() {return new CambiarClave();}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cambiar_clave, container, false);

        contActual = (EditText) view.findViewById(R.id.passActual);
        contNueva = (EditText) view.findViewById(R.id.passNew);
        contNuevaBis = (EditText) view.findViewById(R.id.passNewBis);
        informacion = (TextView) view.findViewById(R.id.infoText);
        aviso = (TextView) view.findViewById(R.id.textViewNueva);
        contNueva.setVisibility(View.INVISIBLE);
        contNuevaBis.setVisibility(View.INVISIBLE);
        aviso.setVisibility(View.INVISIBLE);
        informacion.setVisibility(View.INVISIBLE);

        this.bEnviar = (Button) view.findViewById(R.id.buttonEnviar);
        bEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                informacion.setVisibility(View.INVISIBLE);
                clave = contActual.getText().toString().trim();
                String uri = Constantes.clientes + "/?correo=" + correo;
                RequestQueue queue = Volley.newRequestQueue(getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //textViewInfo.setText(response);
                                try {
                                    JSONObject jsonCliente = new JSONObject(response);
                                    //Integer id=jsonCliente.getInt("id");
                                    Cliente cliente = new Cliente(
                                            jsonCliente.getString("id"),
                                            jsonCliente.getString("nombres"),
                                            jsonCliente.getString("apellidos"),
                                            jsonCliente.getString("correo"),
                                            jsonCliente.getString("edad"),
                                            jsonCliente.getString("clave"));

                                    if (box.evaluarPass(clave, cliente.getContrasena())) {
                                        aviso.setVisibility(View.VISIBLE);
                                        contNueva.setVisibility(View.VISIBLE);
                                        contNuevaBis.setVisibility(View.VISIBLE);

                                    } else {
                                        contActual.setText("");
                                        contActual.setError("Verifique la contraseña");
                                        informacion.setText(clave + " incorrecta.");
                                        informacion.setVisibility(View.VISIBLE);
                                    }
                                } catch (JSONException ex) {
                                    contActual.setText("");
                                    contActual.setError("Verifique la contraseña.");
                                    informacion.setText(correo + " incorrecto.");
                                    informacion.setVisibility(View.VISIBLE);
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
                queue.add(stringRequest);
            }
        });
        this.bRestablecer = (Button) view.findViewById(R.id.buttonRestablecer);
        bEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(contNueva.getText() == contNuevaBis.getText()){
                    aviso.setVisibility(View.INVISIBLE);
                    aviso.setText("Iguales");
                    aviso.setVisibility(View.VISIBLE);
                }else{
                    aviso.setVisibility(View.INVISIBLE);
                    aviso.setText("Diferentes");
                    aviso.setVisibility(View.VISIBLE);
                }
            }
        });
        return view;
    }



}