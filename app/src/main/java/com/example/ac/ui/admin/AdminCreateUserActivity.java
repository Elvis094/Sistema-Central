package com.example.ac.ui.admin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ac.R;
import com.example.ac.models.UsuarioCredencial;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class AdminCreateUserActivity extends AppCompatActivity {

    private EditText etCedula, etNombre, etContrasena;
    private ArrayList<UsuarioCredencial> listaCredenciales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_user);

        etCedula = findViewById(R.id.etCrearCedula);
        etNombre = findViewById(R.id.etCrearNombre);
        etContrasena = findViewById(R.id.etCrearContrasena);

        cargarCredenciales();

        findViewById(R.id.btnGuardarNuevoUsuario).setOnClickListener(v -> guardarUsuario());
    }

    private void guardarUsuario() {
        String cedula = etCedula.getText().toString().trim();
        String nombre = etNombre.getText().toString().trim();
        String contrasena = etContrasena.getText().toString().trim();

        if (cedula.isEmpty() || nombre.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar que la cédula no exista ya
        for (UsuarioCredencial u : listaCredenciales) {
            if (u.getCedula().equals(cedula)) {
                Toast.makeText(this, "Esta cédula ya está registrada", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Agregar y guardar
        listaCredenciales.add(new UsuarioCredencial(cedula, nombre, contrasena));
        SharedPreferences prefs = getSharedPreferences("CredencialesUsuarios", MODE_PRIVATE);
        prefs.edit().putString("credenciales_guardadas", new Gson().toJson(listaCredenciales)).apply();

        Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
        finish(); // Vuelve al menú de Admin
    }

    private void cargarCredenciales() {
        SharedPreferences prefs = getSharedPreferences("CredencialesUsuarios", MODE_PRIVATE);
        String json = prefs.getString("credenciales_guardadas", null);
        Type type = new TypeToken<ArrayList<UsuarioCredencial>>() {}.getType();
        listaCredenciales = new Gson().fromJson(json, type);
        if (listaCredenciales == null) listaCredenciales = new ArrayList<>();
    }
}