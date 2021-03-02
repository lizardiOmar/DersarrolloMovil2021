package com.example.cambiodepeso;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Marte extends AppCompatActivity {
    private TextView txtCalcPeso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marte);
        txtCalcPeso= (TextView)findViewById(R.id.txtCalcPeso);
        try {
            Bundle extras = getIntent().getExtras();
            double numero = extras.getDouble("peso");
            double peso= numero/9.81*3.71;
            String total= String.valueOf(peso);
            txtCalcPeso.setText(total);

        }catch (Exception e){
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast t = Toast.makeText(context, "error en clase inesperado", duration);
            t.show();
        }

    }

    public void anterior(View view){ finish(); }
}
