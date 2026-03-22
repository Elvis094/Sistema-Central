package com.example.ac.ui.admin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ac.R;
import com.example.ac.models.UsuarioCredencial;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class AdminVisualizerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_visualizer);

        TextView tvLista = findViewById(R.id.tvListaUsuariosAdmin);
        findViewById(R.id.btnVolverVisualizador).setOnClickListener(v -> finish());

        // Cargar y mostrar
        SharedPreferences sharedPreferences = getSharedPreferences("CredencialesUsuarios", MODE_PRIVATE);
        String json = sharedPreferences.getString("credenciales_guardadas", null);

        if (json != null) {
            Type type = new TypeToken<ArrayList<UsuarioCredencial>>() {}.getType();
            ArrayList<UsuarioCredencial> lista = new Gson().fromJson(json, type);

            if (lista != null && !lista.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (UsuarioCredencial u : lista) {
                    sb.append("🆔 Cédula: ").append(u.getCedula()).append("\n");
                    sb.append("👤 Nombre: ").append(u.getNombre()).append("\n");
                    sb.append("🔑 Pass: ").append(u.getContrasena()).append("\n");
                    sb.append("-----------------------------\n");
                }
                tvLista.setText(sb.toString());
            } else {
                tvLista.setText("No hay usuarios registrados aún.");
            }
        } else {
            tvLista.setText("Base de datos vacía.");
        }
    }
}