package com.example.connectmovil_nc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrarUsuarioLogin extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText txtCorreo, txtContrasenia, txtConfirmarContraseña;
    private Button BotonRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_login);

        ImageButton volver = findViewById(R.id.btnVolver);
        auth = FirebaseAuth.getInstance();
        txtCorreo = findViewById(R.id.txtCorreo);
        txtContrasenia = findViewById(R.id.txtContrasenia);
        txtConfirmarContraseña = findViewById(R.id.txtConfirmarUsuario);
        BotonRegistrar = findViewById(R.id.btnRegistrar);

        BotonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = txtCorreo.getText().toString().trim();
                String contrasenia = txtContrasenia.getText().toString().trim();
                String confirmarContrasenia = txtConfirmarContraseña.getText().toString().trim();

                if (correo.isEmpty() || contrasenia.isEmpty() || confirmarContrasenia.isEmpty()) {
                    Toast.makeText(RegistrarUsuarioLogin.this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (contrasenia.length() < 8) {
                    Toast.makeText(RegistrarUsuarioLogin.this, "La contraseña debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!contrasenia.equals(confirmarContrasenia)) {
                    Toast.makeText(RegistrarUsuarioLogin.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(correo, contrasenia)
                        .addOnCompleteListener(RegistrarUsuarioLogin.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegistrarUsuarioLogin.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(RegistrarUsuarioLogin.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                                    Log.e("RegistrarUsuario", "Error al registrar el usuario", task.getException());
                                }
                            }
                        });
            }
        });
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}