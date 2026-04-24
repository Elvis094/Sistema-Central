package com.example.ac.ui.agenda;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ac.R;
import com.example.ac.models.Gusto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;

public class LikesActivity extends AppCompatActivity {
    private CheckBox cbCarros, cbMotos, cbMusica, cbComida, cbDeportes, cbMaterias, cbVideojuegos, cbViajes;
    private String userId;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes);

        userId = getIntent().getStringExtra("user_id");

        cbCarros = findViewById(R.id.cbCarros);
        cbMotos = findViewById(R.id.cbMotos);
        cbMusica = findViewById(R.id.cbMusica);
        cbComida = findViewById(R.id.cbComida);
        cbDeportes = findViewById(R.id.cbDeportes);
        cbMaterias = findViewById(R.id.cbMaterias);
        cbVideojuegos = findViewById(R.id.cbVideojuegos);
        cbViajes = findViewById(R.id.cbViajes);

        cargarGustos();

        findViewById(R.id.btnGuardarGustos).setOnClickListener(v -> guardarGustos());
    }

    private void guardarGustos() {
        ArrayList<Gusto> gustosSeleccionados = new ArrayList<>();
        if (cbCarros.isChecked()) gustosSeleccionados.add(new Gusto("carros", "Carros"));
        if (cbMotos.isChecked()) gustosSeleccionados.add(new Gusto("motos", "Motos"));
        if (cbMusica.isChecked()) gustosSeleccionados.add(new Gusto("musica", "Música"));
        if (cbComida.isChecked()) gustosSeleccionados.add(new Gusto("comida", "Comida"));
        if (cbDeportes.isChecked()) gustosSeleccionados.add(new Gusto("deportes", "Deportes"));
        if (cbMaterias.isChecked()) gustosSeleccionados.add(new Gusto("materias", "Materias"));
        if (cbVideojuegos.isChecked()) gustosSeleccionados.add(new Gusto("videojuegos", "Videojuegos"));
        if (cbViajes.isChecked()) gustosSeleccionados.add(new Gusto("viajes", "Viajes"));

        SharedPreferences.Editor editor = getSharedPreferences("UserPrefs", MODE_PRIVATE).edit();
        editor.putString("gustos_" + userId, gson.toJson(gustosSeleccionados));
        editor.apply();

        Toast.makeText(this, "Gustos guardados correctamente", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void cargarGustos() {
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String json = prefs.getString("gustos_" + userId, null);
        if (json != null) {
            ArrayList<Gusto> gustos = gson.fromJson(json, new TypeToken<ArrayList<Gusto>>(){}.getType());
            for (Gusto g : gustos) {
                switch (g.getId()) {
                    case "carros": cbCarros.setChecked(true); break;
                    case "motos": cbMotos.setChecked(true); break;
                    case "musica": cbMusica.setChecked(true); break;
                    case "comida": cbComida.setChecked(true); break;
                    case "deportes": cbDeportes.setChecked(true); break;
                    case "materias": cbMaterias.setChecked(true); break;
                    case "videojuegos": cbVideojuegos.setChecked(true); break;
                    case "viajes": cbViajes.setChecked(true); break;
                }
            }
        }
    }
}