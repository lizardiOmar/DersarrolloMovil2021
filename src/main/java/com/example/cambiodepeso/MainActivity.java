package com.example.cambiodepeso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText peso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        peso = (EditText)findViewById(R.id.numPeso);

    }
    public double getPeso(String peso){
        double peso1=0.0;
        try{
            peso1= Integer.parseInt(peso);
        }catch(Exception ex){
            return 0.0;
        }finally{
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(this, "Escibe un número", duration);
            toast.show();
        }
        return peso1;
    }
    public void luna(View view){
        double peso_double=getPeso(peso.getText().toString());

        Intent luna= new Intent(this, Luna.class);
        luna.putExtra("peso", peso_double);
        startActivity(luna);
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

}
