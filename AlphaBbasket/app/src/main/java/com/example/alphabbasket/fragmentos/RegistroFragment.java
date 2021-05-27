package com.example.alphabbasket.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alphabbasket.R;
import com.example.alphabbasket.tools.Box;


public class RegistroFragment extends Fragment {
    private Box box=new Box();


    public static RegistroFragment newInstance() {
        return new RegistroFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_registro, container, false);

        return view;
    }
}