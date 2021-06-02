package com.example.alphabbasket.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.example.alphabbasket.R;
import com.example.alphabbasket.model.RecyclerViewAdapterMarca;

public class BusquedaFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerViewAdapterMarca recyclerViewAdapterMarca;
    private String[] mDataset={"Marca 1","Marca 2","Marca 3","Marca 4","Marca 5","Marca 6"};
    private GridLayoutManager mLayoutManager;
    RecyclerView.LayoutManager layoutManager;
    public static BusquedaFragment newInstance() {
        return new BusquedaFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_busqueda, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerViewMarcas);

        recyclerViewAdapterMarca=new RecyclerViewAdapterMarca(mDataset);
        recyclerView.setAdapter(recyclerViewAdapterMarca);
        layoutManager=new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }
}