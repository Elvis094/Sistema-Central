package com.example.ac.ui.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ac.R;
import com.example.ac.ui.admin.AdminCreateUserActivity;
import com.example.ac.ui.admin.AdminMainActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etCedula, etContrasena;
    private Button btnLoginIngresar;
    private TextView tvIrRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etCedula = findViewById(R.id.etLoginCedula);
        etContrasena = findViewById(R.id.etLoginContrasena);
        btnLoginIngresar = findViewById(R.id.btnLoginIngresar);
        tvIrRegistro = findViewById(R.id.tvIrRegistro);

        btnLoginIngresar.setOnClickListener(view -> {
            String cedula = etCedula.getText().toString().trim();
            String contrasena = etContrasena.getText().toString().trim();

            if (cedula.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Llena ambos campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // --- LÓGICA DE SESIÓN ---
            SharedPreferences usuariosPrefs = getSharedPreferences("MisUsuarios", MODE_PRIVATE);

            // Caso A: Admin Maestro
            if (cedula.equals("0000") && contrasena.equals("admin123")) {
                guardarSesion(cedula, "Administrador");
                startActivity(new Intent(this, AdminMainActivity.class));
                finish();
            }
            // Caso B: Usuarios registrados por el Admin
            else {
                String passGuardada = usuariosPrefs.getString(cedula, "");
                if (!passGuardada.isEmpty() && passGuardada.equals(contrasena)) {

                    // Verificamos si es un Admin creado o un Usuario normal
                    String rol = usuariosPrefs.getString(cedula + "_rol", "USER");
                    String nombre = usuariosPrefs.getString(cedula + "_nombre", "Usuario");

                    guardarSesion(cedula, nombre); // Guardamos quién entró

                    if (rol.equals("ADMIN")) {
                        startActivity(new Intent(this, AdminMainActivity.class));
                    } else {
                        startActivity(new Intent(this, UserMainActivity.class));
                    }
                    finish();
                } else {
                    Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvIrRegistro.setOnClickListener(view -> {
            startActivity(new Intent(this, AdminCreateUserActivity.class));
        });
    }

    // Función para que el nombre no sea NULL en otras pantallas
    private void guardarSesion(String cedula, String nombre) {
        SharedPreferences session = getSharedPreferences("SesionActiva", MODE_PRIVATE);
        SharedPreferences.Editor editor = session.edit();
        editor.putString("cedula_logueada", cedula);
        editor.putString("nombre_logueado", nombre);
        editor.apply();
    }
}