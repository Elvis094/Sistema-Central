package com.example.ac.ui.admin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ac.R;

public class AdminCreateUserActivity extends AppCompatActivity {

    private EditText etCrearCedula, etCrearNombre, etCrearContrasena;
    private Button btnRegistrar;
    private CheckBox cbRolAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_user);

        // 1. Enlazamos los componentes del XML
        etCrearCedula = findViewById(R.id.etCrearCedula);
        etCrearNombre = findViewById(R.id.etCrearNombre);
        etCrearContrasena = findViewById(R.id.etCrearContrasena);
        cbRolAdmin = findViewById(R.id.cbRolAdmin); // El CheckBox que agregaste
        btnRegistrar = findViewById(R.id.btnGuardarNuevoUsuario);

        btnRegistrar.setOnClickListener(v -> {
            String cedula = etCrearCedula.getText().toString().trim();
            String nombre = etCrearNombre.getText().toString().trim();
            String contrasena = etCrearContrasena.getText().toString().trim();

            // 2. Verificamos si el administrador marcó la casilla
            boolean esAdmin = cbRolAdmin.isChecked();
            String rol = esAdmin ? "ADMIN" : "USER";

            if (cedula.isEmpty() || contrasena.isEmpty() || nombre.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            // 3. GUARDAMOS EN SharedPreferences
            SharedPreferences prefs = getSharedPreferences("MisUsuarios", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            // Guardamos la contraseña usando la cédula como llave
            editor.putString(cedula, contrasena);

            // ¡ESTO ES LO NUEVO!: Guardamos el ROL del usuario usando su cédula + un sufijo
            // Así, al loguearse, buscaremos "cedula_rol" para saber a dónde mandarlo
            editor.putString(cedula + "_rol", rol);

            // Opcional: Guardamos el nombre
            editor.putString(cedula + "_nombre", nombre);

            editor.apply();

            Toast.makeText(this, "Registro exitoso como: " + rol, Toast.LENGTH_SHORT).show();

            // 4. Cerramos la pantalla para volver atrás
            finish();
        });
    }
}