package com.example.ac.ui.user;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ac.AdminSQLiteOpenHelper;
import com.example.ac.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText etCedula, etNombre, etContrasena;
    private Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etCedula = findViewById(R.id.etRegCedula);
        etNombre = findViewById(R.id.etRegNombre);
        etContrasena = findViewById(R.id.etRegContrasena);
        btnRegistrar = findViewById(R.id.btnRegistrarUsuario);

        btnRegistrar.setOnClickListener(v -> {
            String cedula = etCedula.getText().toString().trim();
            String nombre = etNombre.getText().toString().trim();
            String contrasena = etContrasena.getText().toString().trim();

            if (cedula.isEmpty() || nombre.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "agenda", null, 1);
            SQLiteDatabase db = admin.getWritableDatabase();

            ContentValues registro = new ContentValues();
            registro.put("cedula", cedula);
            registro.put("nombre", nombre);
            registro.put("contrasena", contrasena);
            registro.put("rol", "USER");

            long result = db.insert("usuarios", null, registro);
            db.close();

            if (result != -1) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al registrar. ¿Cédula ya existe?", Toast.LENGTH_SHORT).show();
            }
        });
    }
}