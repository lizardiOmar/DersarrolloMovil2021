package com.example.alphabbasket.fragmentos;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alphabbasket.R;
import com.example.alphabbasket.model.RecyclerViewAdapterAyuda;
import com.example.alphabbasket.model.RecyclerViewAdapterMarca;

public class Ayuda extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerViewAdapterAyuda recyclerViewAdapterAyuda;
    private CardView cont;
    private Bundle extras;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_ayuda, container, false);
        extras = getActivity().getIntent().getExtras();
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerViewAyudas);
        recyclerViewAdapterAyuda=new RecyclerViewAdapterAyuda(extras.getString("id"));

        return view;
    }
}