package com.example.androidbuybasket.tools;

import android.os.Build;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.androidbuybasket.Constantes;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Box {
    //Clase interna que encripta/desencripta las contraseñas y las envía así al servidor
    private final Encriptador encriptador=new Encriptador();
    public Boolean textViewEmpty(TextView textView){
        Boolean result=false;
        String text = textView.getText().toString().trim();
        if(TextUtils.isEmpty(text)){
            textView.setError("Este campo es obligatorio.");
            result=false;
        }else{
            result=true;
        }
        return result;
    }

    public Boolean correoValido(TextView textView){
        Boolean result=false;
        if(textViewEmpty(textView)){
            String correo=textView.getText().toString().trim();
            Pattern pattern = Pattern
                    .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher mather = pattern.matcher(correo);
            if(!mather.find()){
                textView.setError("El correo ingresado no es válido.");
                result=false;
            }else{
                result=true;
            }
        }else{
            result=false;
        }
        return result;
    }

    //contraseña valida (al menos 6 caracteres)
    public Boolean validarPass(TextView textView){
        Boolean result=false;
        if(textViewEmpty(textView)){
            String pass=textView.getText().toString().trim();
            if(pass.length()<6){
                textView.setError("La contraseña debe tener al menos seis carácteres");
                  return false;
            }else{
                result=true;
            }
        }else {
            result=false;
        }

        return result;
    }
    //Encriptar contraseña válida
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String encriptarPass(TextView textView){
        String pass="";
            if(validarPass(textView)){
                try {
                    pass = encriptador.encriptar(textView.getText().toString().trim(), Constantes.claveEncriptacion);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                }
            }else{
                return null;
            }
        return pass;
    }
    //Encriptar contraseña válida
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Boolean evaluarPass(TextView textView, String response){
        Boolean aux=false;
        try {
            String passD = encriptador.desencriptar(response, Constantes.claveEncriptacion);
            if(validarPass(textView)){
                if(passD.equals(textView.getText().toString().trim())){
                    aux=true;
                }else{
                    textView.setError("Verifica la contraseña");
                    textView.setText("");
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return aux;
    }
}
