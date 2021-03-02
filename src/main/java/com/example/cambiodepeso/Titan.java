package com.example.cambiodepeso;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Titan extends AppCompatActivity {
    private TextView txtCalcPeso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_titan);
        txtCalcPeso= (TextView)findViewById(R.id.txtCalcPeso);
        Bundle extras = getIntent().getExtras();
        double numero = extras.getDouble("peso");
        double peso= (numero/9.81)*1.352;
        String total= String.valueOf(peso);
        txtCalcPeso.setText(total);
    }
    public void anterior(View view){
        Intent anterior = new Intent(this, MainActivity.class);
        startActivity(anterior);
    }
}
