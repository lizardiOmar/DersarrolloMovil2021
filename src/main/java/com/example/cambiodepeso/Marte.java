package com.example.cambiodepeso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Marte extends AppCompatActivity {
    private TextView txtCalcPeso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marte);
        txtCalcPeso= (TextView)findViewById(R.id.txtCalcPeso);
        Bundle extras = getIntent().getExtras();
        double numero = extras.getDouble("peso");
        double peso= numero/9.81*3.711;
        String total= String.valueOf(peso);
        txtCalcPeso.setText(total);
    }

    public void anterior(View view){
        Intent anterior = new Intent(this, MainActivity.class);
        startActivity(anterior);
    }

}
