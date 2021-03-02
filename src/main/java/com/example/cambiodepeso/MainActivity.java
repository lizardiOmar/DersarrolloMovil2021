package com.example.cambiodepeso;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText peso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        peso = (EditText)findViewById(R.id.numPeso);

    }
    public void luna(View view){
        //if(peso.getText().equals("")){
            String valorPeso = peso.getText().toString();
            double peso1= Integer.parseInt(valorPeso);

            Intent luna= new Intent(this, Luna.class);
            luna.putExtra("peso", peso1);
            startActivity(luna);
       /* } else{
            AlertDialog dialog = new AlertDialog();
            dialog.setTitle("error");
            dialog.show();
        }*/


    }

    public void jupiter(View view){
        String valorPeso = peso.getText().toString();
        double peso1= Integer.parseInt(valorPeso);

        Intent luna= new Intent(this, Jupiter.class);
        luna.putExtra("peso", peso1);
        startActivity(luna);
    }

    public void saturno(View view){
        String valorPeso = peso.getText().toString();
        double peso1= Integer.parseInt(valorPeso);

        Intent luna= new Intent(this, Saturno.class);
        luna.putExtra("peso", peso1);
        startActivity(luna);
    }

    public void urano(View view){
        String valorPeso = peso.getText().toString();
        double peso1= Integer.parseInt(valorPeso);

        Intent luna= new Intent(this, Urano.class);
        luna.putExtra("peso", peso1);
        startActivity(luna);
    }

}
