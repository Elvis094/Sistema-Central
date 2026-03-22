package com.example.ac.ui.agenda;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ac.R;
import com.example.ac.models.AgendaUser;
import com.example.ac.models.Gusto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class LikesActivity extends AppCompatActivity {

    private String userId;
    private ArrayList<AgendaUser> listaAgendas;
    private AgendaUser usuarioActual;
    private TextView tvListaGustos;
    private EditText etNuevoGusto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes);

        userId = getIntent().getStringExtra("user_id");
        tvListaGustos = findViewById(R.id.tvListaGustos);
        etNuevoGusto = findViewById(R.id.etNuevoGusto);

        cargarAgendas();
        buscarUsuarioActual();
        actualizarPantalla();

        findViewById(R.id.btnAgregarGusto).setOnClickListener(v -> {
            String textoGusto = etNuevoGusto.getText().toString().trim();
            if (!textoGusto.isEmpty()) {
                String id = String.valueOf(System.currentTimeMillis());
                usuarioActual.getGustos().add(new Gusto(id, textoGusto));
                guardarCambios();
                actualizarPantalla();
                etNuevoGusto.setText("");
            } else {
                Toast.makeText(this, "Escribe un gusto primero", Toast.LENGTH_SHORT).show();
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
        usuarioActual = new AgendaUser(userId);
        listaAgendas.add(usuarioActual);
    }

    private void actualizarPantalla() {
        StringBuilder sb = new StringBuilder();
        for (Gusto g : usuarioActual.getGustos()) {
            sb.append("• ").append(g.getDescripcion()).append("\n");
        }
        tvListaGustos.setText(sb.toString().isEmpty() ? "No hay gustos registrados." : sb.toString());
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