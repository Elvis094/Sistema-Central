package com.example.ac.ui.agenda;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ac.R;
import com.example.ac.models.AgendaUser;
import com.example.ac.models.Preferencia;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class PreferencesActivity extends AppCompatActivity {

    private String userId;
    private ArrayList<AgendaUser> listaAgendas;
    private AgendaUser usuarioActual;
    private TextView tvListaPreferencias;
    private EditText etPrefClave, etPrefValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        // Recibir la cédula del usuario que inició sesión
        userId = getIntent().getStringExtra("user_id");

        tvListaPreferencias = findViewById(R.id.tvListaPreferencias);
        etPrefClave = findViewById(R.id.etPrefClave);
        etPrefValor = findViewById(R.id.etPrefValor);

        cargarAgendas();
        buscarUsuarioActual();
        actualizarPantalla();

        findViewById(R.id.btnAgregarPreferencia).setOnClickListener(v -> {
            String clave = etPrefClave.getText().toString().trim();
            String valor = etPrefValor.getText().toString().trim();

            if (!clave.isEmpty() && !valor.isEmpty()) {
                // Usamos el Array de Preferencias tal como lo pediste
                usuarioActual.getPreferencias().add(new Preferencia(clave, valor));
                guardarCambios();
                actualizarPantalla();

                // Limpiar los campos después de agregar
                etPrefClave.setText("");
                etPrefValor.setText("");
                Toast.makeText(this, "Preferencia agregada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Llena ambos campos por favor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buscarUsuarioActual() {
        for (AgendaUser user : listaAgendas) {
            if (user.getCedula().equals(userId)) {
                usuarioActual = user;
                return;
            }
        }
        // Si no existe, lo creamos
        usuarioActual = new AgendaUser(userId);
        listaAgendas.add(usuarioActual);
    }

    private void actualizarPantalla() {
        StringBuilder sb = new StringBuilder();
        for (Preferencia p : usuarioActual.getPreferencias()) {
            sb.append("• ").append(p.getClave()).append(": ").append(p.getValor()).append("\n\n");
        }
        tvListaPreferencias.setText(sb.toString().isEmpty() ? "No hay preferencias registradas." : sb.toString());
    }

    private void guardarCambios() {
        SharedPreferences prefs = getSharedPreferences("BaseDatosAgenda", MODE_PRIVATE);
        prefs.edit().putString("agendas_guardadas", new Gson().toJson(listaAgendas)).apply();
    }

    private void cargarAgendas() {
        SharedPreferences prefs = getSharedPreferences("BaseDatosAgenda", MODE_PRIVATE);
        String json = prefs.getString("agendas_guardadas", null);
        Type type = new TypeToken<ArrayList<AgendaUser>>() {}.getType();
        listaAgendas = new Gson().fromJson(json, type);
        if (listaAgendas == null) listaAgendas = new ArrayList<>();
    }
}