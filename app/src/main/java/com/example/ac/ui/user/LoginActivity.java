package com.example.ac.ui.user;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

    private EditText etCedula, etContrasena;
    private ArrayList<UsuarioCredencial> listaCredenciales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etCedula = findViewById(R.id.etLoginCedula);
        etContrasena = findViewById(R.id.etLoginContrasena);

        cargarCredenciales();

        findViewById(R.id.btnLoginIngresar).setOnClickListener(v -> {
            String cedula = etCedula.getText().toString().trim();
            String contrasena = etContrasena.getText().toString().trim();

            if (cedula.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean loginExitoso = false;
            for (UsuarioCredencial u : listaCredenciales) {
                if (u.getCedula().equals(cedula) && u.getContrasena().equals(contrasena)) {
                    loginExitoso = true;
                    break;
                }
            }

            if (loginExitoso) {
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, UserMainActivity.class);
                intent.putExtra("user_id", cedula); // Pasamos la cédula a la siguiente pantalla
                startActivity(intent);
                finish(); // Cerramos el login
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarCredenciales() {
        SharedPreferences prefs = getSharedPreferences("CredencialesUsuarios", MODE_PRIVATE);
        String json = prefs.getString("credenciales_guardadas", null);
        Type type = new TypeToken<ArrayList<UsuarioCredencial>>() {}.getType();
        listaCredenciales = new Gson().fromJson(json, type);
        if (listaCredenciales == null) listaCredenciales = new ArrayList<>();
    }
}