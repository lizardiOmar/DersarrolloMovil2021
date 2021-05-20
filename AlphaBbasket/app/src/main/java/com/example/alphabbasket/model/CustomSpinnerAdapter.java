package com.example.alphabbasket.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.alphabbasket.R;

public class CustomSpinnerAdapter extends BaseAdapter {
    Context context;
    String[] datos;
    LayoutInflater inflter;

    public CustomSpinnerAdapter(Context appContext, String[] datos) {
        this.context = context;
        this.datos = datos;
        inflter = (LayoutInflater.from(appContext));
    }

    @Override
    public int getCount() {
        return datos.length;
    }

    @Override
    public Object getItem(int i) {
        return datos[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_item_1, null);
        TextView dato = (TextView) view.findViewById(R.id.textViewSpinnerItem_1);
        dato.setText(datos[i]);
        return view;
    }
}
