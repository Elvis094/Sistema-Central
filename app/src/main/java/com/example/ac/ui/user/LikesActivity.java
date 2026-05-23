package com.example.ac.ui.user;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ac.AdminSQLiteOpenHelper;
import com.example.ac.R;

public class LikesActivity extends AppCompatActivity {

    private RadioGroup rgVehiculos, rgComida, rgMusica, rgDeporte;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes);

        userId = getIntent().getStringExtra("user_id");

        rgVehiculos = findViewById(R.id.rgVehiculos);
        rgComida = findViewById(R.id.rgComida);
        rgMusica = findViewById(R.id.rgMusica);
        rgDeporte = findViewById(R.id.rgDeporte);

        cargarGustos();

        findViewById(R.id.btnGuardarGustos).setOnClickListener(v -> guardarGustos());
    }

    private void cargarGustos() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "agenda", null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();
        Cursor cursor = db.rawQuery("select categoria, seleccion from gustos where cedula='" + userId + "'", null);

        while (cursor.moveToNext()) {
            String cat = cursor.getString(0);
            String sel = cursor.getString(1);
            marcarSeleccion(cat, sel);
        }
        cursor.close();
        db.close();
    }

    private void marcarSeleccion(String categoria, String seleccion) {
        RadioGroup rg = null;
        if (categoria.equals("Vehículos")) rg = rgVehiculos;
        else if (categoria.equals("Comida")) rg = rgComida;
        else if (categoria.equals("Música")) rg = rgMusica;
        else if (categoria.equals("Deporte")) rg = rgDeporte;

        if (rg != null) {
            for (int i = 0; i < rg.getChildCount(); i++) {
                RadioButton rb = (RadioButton) rg.getChildAt(i);
                if (rb.getText().toString().equals(seleccion)) {
                    rb.setChecked(true);
                }
            }
        }
    }

    private void guardarGustos() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "agenda", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        db.delete("gustos", "cedula='" + userId + "'", null);

        insertarGusto(db, "Vehículos", rgVehiculos);
        insertarGusto(db, "Comida", rgComida);
        insertarGusto(db, "Música", rgMusica);
        insertarGusto(db, "Deporte", rgDeporte);

        db.close();
        Toast.makeText(this, "Gustos guardados", Toast.LENGTH_SHORT).show();
    }

    private void insertarGusto(SQLiteDatabase db, String categoria, RadioGroup rg) {
        int selectedId = rg.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton rb = findViewById(selectedId);
            ContentValues values = new ContentValues();
            values.put("cedula", userId);
            values.put("categoria", categoria);
            values.put("seleccion", rb.getText().toString());
            db.insert("gustos", null, values);
        }
    }
}