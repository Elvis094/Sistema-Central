package com.example.ac.ui.agenda;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ac.R;
import com.example.ac.models.Preferencia;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

public class PreferencesActivity extends AppCompatActivity {
    private String userId;
    private Gson gson = new Gson();
    private ArrayList<CheckBox> checkBoxes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        userId = getIntent().getStringExtra("user_id");

        // Registrar todos los CheckBoxes
        int[] ids = {
            R.id.pref_car_mazda, R.id.pref_car_toyota, R.id.pref_car_ford,
            R.id.pref_moto_yamaha, R.id.pref_moto_kawasaki, R.id.pref_moto_ducati,
            R.id.pref_mus_rock, R.id.pref_mus_pop, R.id.pref_mus_jazz,
            R.id.pref_com_pizza, R.id.pref_com_hamburguesa, R.id.pref_com_sushi,
            R.id.pref_dep_futbol, R.id.pref_dep_basket, R.id.pref_dep_tenis,
            R.id.pref_mat_calculo, R.id.pref_mat_programacion, R.id.pref_mat_fisica
        };

        for (int id : ids) {
            checkBoxes.add(findViewById(id));
        }

        cargarPreferencias();

        findViewById(R.id.btnGuardarPreferencias).setOnClickListener(v -> guardarPreferencias());
    }

    private void guardarPreferencias() {
        ArrayList<Preferencia> prefsSeleccionadas = new ArrayList<>();
        for (CheckBox cb : checkBoxes) {
            if (cb.isChecked()) {
                // Usamos el ID del checkbox como clave y el texto como valor
                String resourceIdName = getResources().getResourceEntryName(cb.getId());
                String categoria = resourceIdName.split("_")[1]; // car, moto, mus, etc
                prefsSeleccionadas.add(new Preferencia(categoria, cb.getText().toString()));
            }
        }

        SharedPreferences.Editor editor = getSharedPreferences("UserPrefs", MODE_PRIVATE).edit();
        editor.putString("prefs_" + userId, gson.toJson(prefsSeleccionadas));
        editor.apply();

        Toast.makeText(this, "Preferencias guardadas", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void cargarPreferencias() {
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String json = prefs.getString("prefs_" + userId, null);
        if (json != null) {
            ArrayList<Preferencia> lista = gson.fromJson(json, new TypeToken<ArrayList<Preferencia>>(){}.getType());
            for (Preferencia p : lista) {
                for (CheckBox cb : checkBoxes) {
                    if (cb.getText().toString().equals(p.getValor())) {
                        cb.setChecked(true);
                    }
                }
            }
        }
    }
}