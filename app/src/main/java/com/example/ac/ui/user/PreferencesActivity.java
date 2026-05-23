package com.example.ac.ui.user;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ac.AdminSQLiteOpenHelper;
import com.example.ac.R;

public class PreferencesActivity extends AppCompatActivity {

    private TextView tvGustos;
    private EditText etDesc;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        userId = getIntent().getStringExtra("user_id");

        tvGustos = findViewById(R.id.tvGustosCargados);
        etDesc = findViewById(R.id.etDescripcionPreferencias);

        cargarGustosYPreferencias();

        findViewById(R.id.btnGuardarPreferencias).setOnClickListener(v -> guardarPreferencias());
    }

    private void cargarGustosYPreferencias() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "agenda", null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();

        // Cargar Gustos seleccionados específicamente
        Cursor cGustos = db.rawQuery("select categoria, seleccion from gustos where cedula='" + userId + "'", null);
        StringBuilder sb = new StringBuilder("Preferencias sobre tus gustos seleccionados:\n\n");
        
        if (cGustos.getCount() == 0) {
            sb.append("(No has seleccionado gustos todavía)");
        } else {
            while (cGustos.moveToNext()) {
                sb.append("• ").append(cGustos.getString(0)).append(": ").append(cGustos.getString(1)).append("\n");
            }
        }
        tvGustos.setText(sb.toString());
        cGustos.close();

        // Cargar Preferencia (descripción detallada sobre esos gustos)
        Cursor cPref = db.rawQuery("select descripcion from preferencias where cedula='" + userId + "'", null);
        if (cPref.moveToFirst()) {
            etDesc.setText(cPref.getString(0));
        }
        cPref.close();
        db.close();
    }

    private void guardarPreferencias() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "agenda", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("cedula", userId);
        values.put("descripcion", etDesc.getText().toString());

        long result = db.replace("preferencias", null, values);
        db.close();

        if (result != -1) {
            Toast.makeText(this, "Preferencias guardadas", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show();
        }
    }
}