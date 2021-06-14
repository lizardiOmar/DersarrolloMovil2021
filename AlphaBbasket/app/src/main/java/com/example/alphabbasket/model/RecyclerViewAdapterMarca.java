package com.example.alphabbasket.model;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.alphabbasket.R;

public class RecyclerViewAdapterMarca extends RecyclerView.Adapter<RecyclerViewAdapterMarca.ViewHolder> {
    private TextView textViewAux;
    private static final String TAG = "Marca: ";
    private String[] myDataSet;

    public RecyclerViewAdapterMarca(String[] dataSet) {
        myDataSet = dataSet;
    }

    @Override
    public RecyclerViewAdapterMarca.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_marcas, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterMarca.ViewHolder holder, int position) {
        Log.d(TAG, "Element " + position + " set.");
        holder.getTextView().setText(myDataSet[position]);
    }

    @Override
    public int getItemCount() {
        return myDataSet.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public ViewHolder(final View itemView) {
            super(itemView);
            textViewAux=(TextView)itemView.findViewById(R.id.textViewMarca);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {

                    Log.d(TAG, "Element " +  getAbsoluteAdapterPosition() + " clicked.");


                }
            });

        }
        public TextView getTextView() {
            return textViewAux;
        }
    }
}
