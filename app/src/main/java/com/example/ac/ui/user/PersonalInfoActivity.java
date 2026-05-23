package com.example.ac.ui.user;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ac.AdminSQLiteOpenHelper;
import com.example.ac.R;

public class PersonalInfoActivity extends AppCompatActivity {

    private EditText etNumero, etEmail, etSexo, etDireccion, etResidencia;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        userId = getIntent().getStringExtra("user_id");

        etNumero = findViewById(R.id.etInfoNumero);
        etEmail = findViewById(R.id.etInfoEmail);
        etSexo = findViewById(R.id.etInfoSexo);
        etDireccion = findViewById(R.id.etInfoDireccion);
        etResidencia = findViewById(R.id.etInfoResidencia);

        cargarDatos();

        findViewById(R.id.btnGuardarInfo).setOnClickListener(v -> guardarDatos());
    }

    private void cargarDatos() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "agenda", null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();
        Cursor cursor = db.rawQuery("select numero, email, sexo, direccion, residencia from perfil where cedula='" + userId + "'", null);

        if (cursor.moveToFirst()) {
            etNumero.setText(cursor.getString(0));
            etEmail.setText(cursor.getString(1));
            etSexo.setText(cursor.getString(2));
            etDireccion.setText(cursor.getString(3));
            etResidencia.setText(cursor.getString(4));
        }
        cursor.close();
        db.close();
    }

    private void guardarDatos() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "agenda", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("cedula", userId);
        valores.put("numero", etNumero.getText().toString());
        valores.put("email", etEmail.getText().toString());
        valores.put("sexo", etSexo.getText().toString());
        valores.put("direccion", etDireccion.getText().toString());
        valores.put("residencia", etResidencia.getText().toString());

        long result = db.replace("perfil", null, valores);
        db.close();

        if (result != -1) {
            Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show();
        }
    }
}