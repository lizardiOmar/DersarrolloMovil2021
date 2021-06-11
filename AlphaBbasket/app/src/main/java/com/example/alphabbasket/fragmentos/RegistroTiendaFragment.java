package com.example.alphabbasket.fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alphabbasket.LoginActivity;
import com.example.alphabbasket.R;
import com.example.alphabbasket.model.Constantes;
import com.example.alphabbasket.model.Tienda;
import com.example.alphabbasket.tools.Box;

import java.util.HashMap;
import java.util.Map;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.WHITE;

public class RegistroTiendaFragment extends Fragment {
    private Bundle extras;
    private Box box=new Box();
    private TextView textViewNombre, textViewHoEntrada, textViewHoSalida;
    private EditText editTextNombreT, editTextHoEntrada, editTextHoSalida;
    private Button buttonRegistrar;
    private String nombreTienda, horarioEntrada, horarioSalida, idC;
    public static RegistroTiendaFragment newInstance() {
        return new RegistroTiendaFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_registro_tienda, container, false);
        this.editTextNombreT=(EditText)view.findViewById(R.id.editTextNombreTienda);
        this.editTextHoEntrada=(EditText)view.findViewById(R.id.editTextHorarioEntrada);
        this.editTextHoSalida=(EditText)view.findViewById(R.id.editTextHorarioSalida);
        extras = getActivity().getIntent().getExtras();
        idC = extras.getString("id");

        nombreTienda="";
        horarioEntrada="";
        horarioSalida="";

        this.textViewNombre=(TextView)view.findViewById(R.id.textViewNombreTienda);
        this.textViewHoEntrada=(TextView)view.findViewById(R.id.textViewHorarioE);
        this.textViewHoSalida=(TextView)view.findViewById(R.id.textViewHorarioS);

        this.textViewNombre.setText("¿Cual es el nombre de la tienda?");
        this.textViewHoEntrada.setText("Hora de apertura");
        this.textViewHoSalida.setText("Hora de clausura");

        this.buttonRegistrar=(Button)view.findViewById(R.id.buttonRegistrarFragment);

        this.buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(box.editTextEmpty(editTextNombreT)){
                    if(box.editTextEmpty(editTextHoEntrada)){
                        if(box.editTextEmpty(editTextHoSalida)){
                                    //REGISTRO
                                    final Tienda tienda=new Tienda("0", nombreTienda, horarioEntrada, horarioSalida, idC);
                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.tiendas,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String ServerResponse) {
                                                    Intent i = new Intent(getActivity().getApplicationContext(), LoginActivity.class );
                                                    startActivity(i);
                                                    getActivity().finish();
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError volleyError) {
                                                    textViewNombre.setText(volleyError.getLocalizedMessage());
                                                }
                                            }) {
                                        @Override
                                        protected Map<String, String> getParams() {
                                            // Creating Map String Params.
                                            Map<String, String> params = new HashMap<String, String>();
                                            // Adding All values to Params.
                                            params.put("id", tienda.getId());
                                            params.put("nombreTienda", tienda.getNombreTienda());
                                            params.put("horaApertura", tienda.getHorarioEntrada());
                                            params.put("horaCierre", tienda.getHorarioSalida());
                                            params.put("idCliente", tienda.getIdCliente());
                                            return params;
                                        }
                                    };
                                    // Creating RequestQueue.
                                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                                    // Adding the StringRequest object into requestQueue.
                                    requestQueue.add(stringRequest);

                                }
                            }
                        }
                    }
        });

        editTextNombreT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i2>=2){
                    textViewNombre.setTextColor(GREEN);
                    textViewNombre.setText("Perfecto!");
                    nombreTienda=String.valueOf(charSequence);
                }else{
                    textViewNombre.setText("¿Cual es el nombre de la tienda?");
                    textViewNombre.setTextColor(WHITE);
                    nombreTienda="";
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editTextHoEntrada.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i2>=2){
                    textViewHoEntrada.setTextColor(GREEN);
                    textViewHoEntrada.setText("Perfecto!");
                    horarioEntrada=String.valueOf(charSequence);
                }else{
                    textViewHoEntrada.setText("¿A que hora abre?");
                    textViewHoEntrada.setTextColor(WHITE);
                    horarioEntrada="";
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editTextHoSalida.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i2>=2){
                    textViewHoSalida.setTextColor(GREEN);
                    textViewHoSalida.setText("Perfecto!");
                    horarioSalida=String.valueOf(charSequence);
                }else{
                    textViewHoSalida.setText("¿A que hora cierra?");
                    textViewHoSalida.setTextColor(WHITE);
                    horarioSalida="";
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }
}
