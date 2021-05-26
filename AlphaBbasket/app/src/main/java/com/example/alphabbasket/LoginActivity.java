package com.example.alphabbasket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alphabbasket.fragmentos.LoginFragment;
import com.example.alphabbasket.fragmentos.RegistroFragment;
import com.example.alphabbasket.model.Cliente;
import com.example.alphabbasket.tools.Box;
import com.google.android.material.tabs.TabLayout;

public class LoginActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private TextView  textViewFrase;
    private Box box=new Box();
    private TabLayout tabMain;
    private Cliente cliente;
    private FrameLayout fragmentView;
    private LoginFragment loginFRagment;
    private RegistroFragment registroFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Primera linea:
        iniciarComponentes();
        //Primera linea:
        agregarEventos();
    }

    private void agregarEventos() {

        /*
        buttonAcceder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(validarFormulario()) {
                    correo=editTextCorreo.getText().toString().trim();
                    contraseña=editTextContraseña.getText().toString().trim();

                    Toast.makeText(LoginActivity.this, ClienteDAO.getClienteByCorreo(correo, getApplicationContext()).getNombres(), Toast.LENGTH_SHORT).show();

                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constantes.localGetPassCliente,
                            new Response.Listener<String>() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onResponse(String response) {
                                    String passC="";
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        String datos=jsonResponse.getString("datos");
                                        JSONObject jsonDatos = new JSONObject(datos);
                                        passC=jsonDatos.getString("contrasena");
                                        if(box.evaluarPass(contraseña, passC)){
                                            Intent i = new Intent(getApplicationContext(), MainActivity.class );
                                            i.putExtra("correo", correo);
                                            startActivity(i);
                                            finish();
                                        }else{
                                            Toast.makeText(LoginActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException ex) {
                                        Toast.makeText(getApplicationContext(), ex.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() {
                            // Creating Map String Params.
                            Map<String, String> params = new HashMap<String, String>();
                            // Adding All values to Params.
                            params.put("correo", correo);
                            return params;
                        }
                    };
                    queue.add(stringRequest);

                }else{
                    Toast.makeText(LoginActivity.this, "Revise los datos.", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }

    private void iniciarComponentes() {
        this.tabMain=(TabLayout)findViewById(R.id.tabsMain);
        this.tabMain.addOnTabSelectedListener(this);
        this.fragmentView=(FrameLayout)findViewById(R.id.constraintlayoutFragment);
        this.registroFragment=new RegistroFragment();
        this.loginFRagment=new LoginFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.constraintlayoutFragment, loginFRagment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.constraintlayoutFragment, registroFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(registroFragment).commit();
        textViewFrase = (TextView) findViewById(R.id.textViewLema);
        Animation.AnimationListener animationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                textViewFrase.setText("Navegando al registro.");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent i = new Intent(getApplicationContext(), RegistroActivity.class );
                startActivity(i);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Toast.makeText(LoginActivity.this, "error al navegar al acceso.", Toast.LENGTH_SHORT).show();
            }
        };








    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int selected=tab.getPosition();
        switch (selected){
            case 0:{
                getSupportFragmentManager().beginTransaction().show(loginFRagment).commit();
                break;
            }
            case 1:{
                getSupportFragmentManager().beginTransaction().show(registroFragment).commit();
                break;
            }
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        int selected=tab.getPosition();
        switch (selected){
            case 0:{
                getSupportFragmentManager().beginTransaction().hide(loginFRagment).commit();
                break;
            }

            case 1:{
                getSupportFragmentManager().beginTransaction().hide(registroFragment).commit();
                break;
            }
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}