package com.example.alphabbasket;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alphabbasket.dao.ClienteDAO;
import com.example.alphabbasket.model.Cliente;
import com.example.alphabbasket.model.Constantes;
import com.example.alphabbasket.tools.Box;
import com.example.alphabbasket.tools.Tiempo;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {
    //Variables correspondientes a los 'EditText' del documento XML de esta activity
    private EditText editTextNombres, editTextApellidos, editTextCorreoRegistro, editTextEdad, editTextContraseñaRegistro, editTextContraseñaConfirmacion;
    private String nombres, apellidos, correo, edad, contraseña, contraseñaConfirmacion;
    //Variable correspondiente al 'TextView' del documento XML de esta activity
    //Habilita la navegación hacia la activity LOGIN
    private TextView textViewTitulo, textViewFrase, textViewConCuenta;
    //Clase correspondiente a los datos de la aplicación para enviarlos al servicio PHP
    private Cliente clienteNuevo;
    //Variable correspondiente al 'Button' del documento XML de esta activity
    //Realiza la solicitud al servidor mediante Volley
    private Button buttonRegistrar, buttonLogin;
    private Animation.AnimationListener animationListenerButtonNavigation, animationListenerButtonRegistro;
    //Indicador booleano de validación de los datos
    //Si es false no se enviará ninguna solicitud al servidor
    private Boolean flag;
    //Clase interna que contiene diversas herramientas para verificar campos
    private Animation animationMoveRight;
    private Box box = new Box();
    private Context context;
    private ClienteDAO clienteDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        //Primera linea:
        iniciarComponentes();
        //Primera linea:
        agregarEventos();

    }

    private void agregarEventos() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationMoveRight.setAnimationListener(animationListenerButtonNavigation);
                textViewFrase.startAnimation(animationMoveRight);

            }
        });
        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationMoveRight.setAnimationListener(animationListenerButtonRegistro);
                textViewFrase.startAnimation(animationMoveRight);
            }
        });

    }
    private void iniciarComponentes(){
        context=getApplicationContext();
        clienteDAO=new ClienteDAO(context);
        editTextNombres= this.findViewById(R.id.editTextNombres);
        editTextApellidos=this.findViewById(R.id.editTextApellidos);
        editTextCorreoRegistro=this.findViewById(R.id.editTextCorreoRegistro);
        editTextEdad=this.findViewById(R.id.editTextEdad);
        editTextContraseñaRegistro=this.findViewById(R.id.editTextClaveRegistro);
        editTextContraseñaConfirmacion=this.findViewById(R.id.editTextClaveRegistroConfirmacion);

        textViewTitulo=this.findViewById(R.id.textViewBbasket);
        textViewFrase=this.findViewById(R.id.textViewLema);
        textViewConCuenta=this.findViewById(R.id.textViewConCuenta);

        buttonRegistrar=this.findViewById(R.id.buttonRegistrar);
        buttonLogin=this.findViewById(R.id.buttonNavegacionLogin);

        animationMoveRight = AnimationUtils.loadAnimation(this, R.anim.move_right);

        animationListenerButtonNavigation = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                textViewFrase.setText("Bienvenido de nuevo a B-basket.");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class );
                startActivity(i);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Toast.makeText(RegistroActivity.this, "error al navegar al acceso.", Toast.LENGTH_SHORT).show();
            }
        };
        animationListenerButtonRegistro = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                textViewFrase.setText("Verificando datos.");

                flag=false;
                if(box.editTextEmpty(editTextNombres)){
                    nombres=editTextNombres.getText().toString().trim();
                    if(box.editTextEmpty(editTextApellidos)){
                        apellidos=editTextApellidos.getText().toString().trim();
                        if(box.editTextEmpty(editTextEdad)){
                            edad=editTextEdad.getText().toString().trim();
                            if(box.correoValido(editTextCorreoRegistro)){
                                correo=editTextCorreoRegistro.getText().toString().trim();
                                if(box.validarPass(editTextContraseñaRegistro)){
                                    contraseña=editTextContraseñaRegistro.getText().toString().trim();
                                    if(box.validarPass(editTextContraseñaConfirmacion)){
                                        contraseñaConfirmacion=editTextContraseñaConfirmacion.getText().toString().trim();
                                        if(contraseña.equals(contraseñaConfirmacion)){
                                            flag=true;
                                        }else{
                                            editTextContraseñaConfirmacion.setError("Las contraseñas deben coincidir");
                                            editTextContraseñaRegistro.setError("Las contraseñas deben coincidir");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if(flag){
                    String contraseñaEncriptada=box.encriptarPass(editTextContraseñaConfirmacion);
                    clienteNuevo=new Cliente("0", nombres, apellidos, correo, edad, contraseñaEncriptada);

                    if(clienteDAO.registrarCliente(clienteNuevo)){
                        textViewFrase.setText("Cuenta Registrada");
                    }else{
                        textViewFrase.setText("Error inesperado");
                    }
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(flag){
                    animation.setAnimationListener(null);
                    textViewFrase.startAnimation(animationMoveRight);
                    textViewTitulo.setText("Registrando cuenta.");
                    textViewFrase.setText("");
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class );
                    startActivity(i);
                    finish();
                }else{
                    textViewFrase.setText("Por favor, verifique los datos.");
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Toast.makeText(RegistroActivity.this, "error al validar los datos.", Toast.LENGTH_SHORT).show();
            }
        };

    }
}