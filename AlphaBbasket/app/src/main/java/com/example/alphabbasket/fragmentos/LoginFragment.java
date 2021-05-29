package com.example.alphabbasket.fragmentos;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
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
import com.example.alphabbasket.MainActivity;
import com.example.alphabbasket.R;
import com.example.alphabbasket.model.Cliente;
import com.example.alphabbasket.model.Constantes;
import com.example.alphabbasket.tools.Box;
import com.example.alphabbasket.tools.Encriptador;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class LoginFragment extends Fragment {
    private String correo, clave;
    private LoginViewModel mViewModel;
    private EditText editTextCorreo, editTextClave;
    private TextView textViewInfo;
    private Button buttonLogin, buttonRecuperarClave;
    private Boolean flag=false;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }
    private Box box=new Box();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_login, container, false);

        this.buttonLogin=(Button)view.findViewById(R.id.buttonLogin);
        this.buttonRecuperarClave=(Button)view.findViewById(R.id.buttonRecuperarClave);
        this.textViewInfo=(TextView)view.findViewById(R.id.textViewInfo);

        this.editTextCorreo=(EditText) view.findViewById(R.id.editTextCorreoLogin);
        this.editTextClave=(EditText) view.findViewById(R.id.editTextClaveLogin);
        editTextClave.setVisibility(View.INVISIBLE);
        buttonLogin.setVisibility(View.INVISIBLE);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                correo=editTextCorreo.getText().toString().trim();
                clave=editTextClave.getText().toString().trim();
                String uri =  Constantes.clientes+"/?correo="+correo;
                RequestQueue queue = Volley.newRequestQueue(getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //textViewInfo.setText(response);
                                try {
                                    JSONObject jsonCliente = new JSONObject(response);
                                    //Integer id=jsonCliente.getInt("id");
                                    Cliente cliente=new Cliente(
                                            jsonCliente.getString("id"),
                                            jsonCliente.getString("nombres"),
                                            jsonCliente.getString("apellidos"),
                                            jsonCliente.getString("correo"),
                                            jsonCliente.getString("edad"),
                                            jsonCliente.getString("clave"));

                                    if(box.evaluarPass(clave, cliente.getContrasena())){
                                        Intent i = new Intent(getContext(), MainActivity.class );
                                        i.putExtra("id", cliente.getId());
                                        i.putExtra("nombres", cliente.getNombres());
                                        i.putExtra("apellidos", cliente.getApellidos());
                                        i.putExtra("edad", cliente.getEdad());
                                        i.putExtra("correo", cliente.getCorreo());
                                        i.putExtra("clave", cliente.getContrasena());
                                        startActivity(i);
                                        getActivity().finish();
                                    }else{
                                        editTextClave.setText("");
                                        editTextClave.setError("Verifique la contraseña");
                                        textViewInfo.setText(clave+" incorrecta.");
                                    }
                                } catch (JSONException ex) {
                                    editTextCorreo.setText("");
                                    editTextCorreo.setError("Verifique el correo.");
                                    editTextClave.setText("");
                                    editTextClave.setError("Verifique la contraseña");
                                    editTextClave.setVisibility(View.INVISIBLE);
                                    textViewInfo.setText(correo+" incorrecto.");
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
                queue.add(stringRequest);
            }
        });

        buttonRecuperarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        editTextCorreo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()==0){
                    editTextCorreo.setError("¿No tienes una cuenta?");
                    editTextClave.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(box.correoValido(editTextCorreo)){
                    editTextClave.setVisibility(View.VISIBLE);
                }else{
                    editTextCorreo.setError("Correo no válido");
                    editTextClave.setVisibility(View.INVISIBLE);
                }
            }
        });
        editTextClave.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()>=6){
                    buttonLogin.setVisibility(View.VISIBLE);

                }else{
                    editTextClave.setError("La clave debe tener al menos 6 carácteres.");
                    if(buttonLogin.getVisibility()==View.VISIBLE){
                        buttonLogin.setVisibility(View.INVISIBLE);
                    }
                }


            }
        });
        return view;
    }






    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        // TODO: Use the ViewModel
    }

}