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
  
    public void noNumber(){
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(this, "Escibe un número", duration);
        toast.show();
    }
    public double getPeso(String peso){
        double peso1;
        try{
            peso1= Double.parseDouble(peso);
        }catch(Exception ex){
            peso1=0.0;
            return peso1;
        }
        return peso1;
    }

    public void luna(View view){
        double peso_double=getPeso(peso.getText().toString());
        if(peso_double!=0){
            Intent luna= new Intent(this, Luna.class);
            luna.putExtra("peso", peso_double);
            startActivity(luna);
        }else{
            noNumber();
        }
    }

    public void jupiter(View view){
        double peso_double=getPeso(peso.getText().toString());
        if(peso_double!=0){
            Intent jupiter= new Intent(this, Jupiter.class);
            jupiter.putExtra("peso", peso_double);
            startActivity(jupiter);
        }else{
            noNumber();
        }
    }

    public void saturno(View view){
        double peso_double=getPeso(peso.getText().toString());
        if(peso_double!=0){
            Intent saturno= new Intent(this, Saturno.class);
            saturno.putExtra("peso", peso_double);
            startActivity(saturno);
        }else{
            noNumber();
        }
    }

    public void urano(View view){
       double peso_double=getPeso(peso.getText().toString());
        if(peso_double!=0){
            Intent urano= new Intent(this, Urano.class);
            saturno.putExtra("peso", peso_double);
            startActivity(urano);
        }else{
            noNumber();
        }
    }
    public void marte(View view){
        double peso_double=getPeso(peso.getText().toString());
        if(peso_double!=0){
            Intent marte= new Intent(this, Marte.class);
            marte.putExtra("peso", peso_double);
            startActivity(marte);
        }else{
            noNumber();
        }
    }
}
