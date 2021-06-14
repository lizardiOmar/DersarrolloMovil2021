package com.example.alphabbasket.fragmentos;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alphabbasket.MainActivity;
import com.example.alphabbasket.R;
import com.example.alphabbasket.model.Cliente;
import com.example.alphabbasket.model.Constantes;
import com.example.alphabbasket.model.CustomSpinnerAdapter;
import com.example.alphabbasket.model.RecyclerViewAdapterMarca;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BusquedaFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerViewAdapterMarca recyclerViewAdapterMarca;
    private String[] mDataset={"Marca 1","Marca 2","Marca 3","Marca 4","Marca 5","Marca 6"};
    private Spinner spinnerCategoria, spinnerProductos;
    private final String[] categorias = { "Selecciona una categoría", "Lácteos", "Carnes", "Huevos", "Grasas y aceites", "Frutas", "Verduras", "Cereales y semillas", "Azucares y dulces", "Pan", "Otros"};
    private RecyclerView.LayoutManager layoutManager;
    public static BusquedaFragment newInstance() {
        return new BusquedaFragment();
    }
    private CustomSpinnerAdapter customAdapterCategorias, customAdapterProductos;
    private TextView textViewResultado;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_busqueda, container, false);

        spinnerProductos= (Spinner) view.findViewById(R.id.spinnerProductos);

        textViewResultado=(TextView)view.findViewById(R.id.textViewResultadoBusqueda);

        spinnerCategoria = (Spinner) view.findViewById(R.id.spinnerCategorias);
        customAdapterCategorias=new CustomSpinnerAdapter(view.getContext(), categorias);
        spinnerCategoria.setAdapter(customAdapterCategorias);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerViewMarcas);

        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String categoria = categorias[spinnerCategoria.getSelectedItemPosition()];
                String uri =  Constantes.ofertas+"/?categoria="+categoria;
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray jsonProductos=new JSONArray(response);
                                    int len = jsonProductos.length();
                                    if(len>0){
                                        final String [] productos=new String[len];

                                        if(len==1){
                                            textViewResultado.setText("Solo hay 1 producto en esta categoría.");
                                        }else{
                                            textViewResultado.setText("Existen "+len+" productos en esta categoría.");
                                        }
                                        ArrayList<String> productosList = new ArrayList<String>();
                                        for(int i=0; i<len;i++){
                                            productosList.add(jsonProductos.get(i).toString());
                                            JSONObject jsonProdcuto=new JSONObject(productosList.get(i));
                                            productos[i]=jsonProdcuto.getString("PRODUCTO");
                                        }
                                        customAdapterProductos=new CustomSpinnerAdapter(getContext(), productos);
                                        spinnerProductos.setAdapter(customAdapterProductos);
                                        spinnerProductos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                String producto=productos[spinnerProductos.getSelectedItemPosition()];
                                                String uriProductos =  Constantes.ofertas+"/?producto="+producto;
                                                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                                                StringRequest stringRequest = new StringRequest(Request.Method.GET, uriProductos,
                                                        new Response.Listener<String>() {
                                                            @Override
                                                            public void onResponse(String response) {
                                                                try {
                                                                    JSONArray jsonMarcas=new JSONArray(response);
                                                                    int len = jsonMarcas.length();
                                                                    if(len>0){
                                                                        String [] marcas=new String[len];

                                                                        if(len==1){
                                                                            textViewResultado.setText("Solo hay 1 marca en esta categoría.");
                                                                        }else{
                                                                            textViewResultado.setText("Existen "+len+" marcas dsiponibles.");
                                                                        }
                                                                        ArrayList<String> marcasList = new ArrayList<String>();
                                                                        for(int i=0; i<len;i++){
                                                                            marcasList.add(jsonMarcas.get(i).toString());
                                                                            JSONObject jsonProdcuto=new JSONObject(marcasList.get(i));
                                                                            marcas[i]=jsonProdcuto.getString("MARCA");
                                                                        }
                                                                        recyclerViewAdapterMarca=new RecyclerViewAdapterMarca(marcas);
                                                                        recyclerView.setAdapter(recyclerViewAdapterMarca);
                                                                        layoutManager=new GridLayoutManager(getActivity(), 3);
                                                                        recyclerView.setLayoutManager(layoutManager);
                                                                    }

                                                                } catch (JSONException e) {
                                                                    spinnerProductos.setAdapter(null);
                                                                    textViewResultado.setText("No hay marcas en la selección del producto.");
                                                                }

                                                            }
                                                        }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        textViewResultado.setText(error.getLocalizedMessage()+" VOLLEY");
                                                    }
                                                });
                                                queue.add(stringRequest);

                                                textViewResultado.setText(uriProductos);
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                            }
                                        });
                                    }

                                } catch (JSONException e) {
                                    spinnerProductos.setAdapter(null);
                                    textViewResultado.setText("No hay productos en esta categoría.");
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textViewResultado.setText(error.getLocalizedMessage()+" VOLLEY");
                    }
                });
                queue.add(stringRequest);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                textViewResultado.setText(rv.getId()+"---ID");
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        return view;
    }
}