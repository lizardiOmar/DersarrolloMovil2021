package com.example.alphabbasket.fragmentos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.alphabbasket.R;
import com.example.alphabbasket.model.Cliente;
import com.example.alphabbasket.model.CustomSpinnerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditarDatosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarDatosFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Cliente cliente =null;
    private Spinner spinnerDatosCliente;
    private final String[] datos = { "...", "Nombres", "Apellidos", "Edad"};
    private String datoNuevo="";
    private int aux=0;
    private CustomSpinnerAdapter customAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button buttonGuardarCambios;
    private EditText editTextCambiarDato;
    private Context context;
    public EditarDatosFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditarDatosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditarDatosFragment newInstance(String param1, String param2) {
        EditarDatosFragment fragment = new EditarDatosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editar_datos, container, false);

        customAdapter=new CustomSpinnerAdapter(view.getContext(),datos);
        spinnerDatosCliente = (Spinner) view.findViewById(R.id.spinnerDatosCliente);
        buttonGuardarCambios=(Button)view.findViewById(R.id.buttonGuardarCambio);
        editTextCambiarDato=(EditText)view.findViewById(R.id.editTextDatoEditable);

        context=view.getContext();
        spinnerDatosCliente.setAdapter(customAdapter);
        spinnerDatosCliente.setOnItemSelectedListener(this);
        return view;
    }

    @Override
    public void onItemSelected(final AdapterView<?> adapterView, View view, int i, long l) {
        if(!editTextCambiarDato.getText().toString().equals("")){
            //se asigna el indice de la selección a una variable final interna
            final int x=i;
            //Se crea una ventana de alerta en una implementación interna (c=context)
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            //Setters de la ventana (titulo, icono)
            builder.setTitle("Descartar cambios");
            builder.setIcon(R.drawable.editar_logo_small_30);
            builder.setMessage("¿deseas descartar los cambios en ("+ datos[aux]+" "+editTextCambiarDato.getText().toString().trim()+")?");
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                //Botón aceptar descartar la información
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //la variable dato nuevo se iguala con nada
                    datoNuevo="";
                    //se asigna ese valor al editTextCambiarDato
                    editTextCambiarDato.setText("");
                    //Se asigna al adaptador la selección mas reciente
                    adapterView.setSelection(x);
                    switch (x){
                        //Si el item corresponde al 0, no se habilita ninguna opción de edición
                        case 0:
                            editTextCambiarDato.setEnabled(false);
                            buttonGuardarCambios.setVisibility(View.INVISIBLE);
                            editTextCambiarDato.setHint("Selecciona una opción");
                            aux=x;
                            break;
                        //Si el item corresponde al 1, se habilita la opción de edición de nombres
                        case 1:
                            buttonGuardarCambios.setText("Cambiar mis nombres");
                            buttonGuardarCambios.setVisibility(View.VISIBLE);
                            editTextCambiarDato.setHint(cliente.getNombres());
                            editTextCambiarDato.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                            editTextCambiarDato.setEnabled(true);
                            aux=x;
                            break;
                        //Si el item corresponde al 2, se habilita la opción de edición de apellidos
                        case 2:
                            buttonGuardarCambios.setText("Cambiar mis apellidos");
                            buttonGuardarCambios.setVisibility(View.VISIBLE);
                            editTextCambiarDato.setHint(cliente.getApellidos());
                            editTextCambiarDato.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                            editTextCambiarDato.setEnabled(true);
                            aux=x;
                            break;
                        //Si el item corresponde al 3, se habilita la opción de edición de edad
                        case 3:
                            buttonGuardarCambios.setText("Cambiar mi edad");
                            buttonGuardarCambios.setVisibility(View.VISIBLE);
                            editTextCambiarDato.setHint(cliente.getEdad());
                            editTextCambiarDato.setInputType(InputType.TYPE_CLASS_NUMBER);
                            editTextCambiarDato.setEnabled(true);
                            aux=x;
                            break;
                    }
                }
            });
            //Botón para no descartar la información
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Sé le asigna a la variable datoNuevo el valor del contenido del editTextCambioDato
                    datoNuevo=editTextCambiarDato.getText().toString().trim();
                    //Sé vacía la variable datoNuevo
                    editTextCambiarDato.setText("");
                    //Se asigna al adaptador la selección del dato que se está editando
                    adapterView.setSelection(aux);
                    dialog.cancel();
                }
            });
            AlertDialog dialog = builder.create();

            dialog.show();
            //Sí el editTextDatoEditable está vacío
        }else{
            //Si la variable 'datoNuevo' no está vacía
            if(!datoNuevo.equals("")){
                //Se coloca su valor dentro del editTextCambiarDato
                editTextCambiarDato.setText(datoNuevo);
                //Y sé vacía la variable
                datoNuevo="";
                //Si la variable 'datoNuevo' sí está vacía
            }else{
                switch (i){
                    //Si el item corresponde al 0, no se habilita ninguna opción de edición
                    case 0:
                        editTextCambiarDato.setEnabled(false);
                        buttonGuardarCambios.setVisibility(View.INVISIBLE);
                        editTextCambiarDato.setHint("Selecciona una opción");
                        aux=i;
                        break;
                    //Si el item corresponde al 1, se habilita la opción de edición de nombres
                    case 1:
                        buttonGuardarCambios.setText("Cambiar mis nombres");
                        buttonGuardarCambios.setVisibility(View.VISIBLE);
                        editTextCambiarDato.setHint(cliente.getNombres());
                        editTextCambiarDato.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                        editTextCambiarDato.setEnabled(true);
                        aux=i;
                        break;
                    //Si el item corresponde al 2, se habilita la opción de edición de apellidos
                    case 2:
                        buttonGuardarCambios.setText("Cambiar mis apellidos");
                        buttonGuardarCambios.setVisibility(View.VISIBLE);
                        editTextCambiarDato.setHint(cliente.getApellidos());
                        editTextCambiarDato.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                        editTextCambiarDato.setEnabled(true);
                        aux=i;
                        break;
                    //Si el item corresponde al 3, se habilita la opción de edición de edad
                    case 3:
                        buttonGuardarCambios.setText("Cambiar mi edad");
                        buttonGuardarCambios.setVisibility(View.VISIBLE);
                        editTextCambiarDato.setHint(cliente.getEdad());
                        editTextCambiarDato.setInputType(InputType.TYPE_CLASS_NUMBER);
                        editTextCambiarDato.setEnabled(true);
                        aux=i;
                        break;
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}