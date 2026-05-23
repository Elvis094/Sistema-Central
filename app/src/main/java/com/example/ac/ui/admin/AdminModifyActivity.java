package com.example.ac.ui.admin;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ac.AdminSQLiteOpenHelper;
import com.example.ac.R;

public class AdminModifyActivity extends AppCompatActivity {

    private EditText etBusqueda, etNombre, etContra;
    private LinearLayout layoutCampos;
    private String cedulaEncontrada = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_modify);

        etBusqueda = findViewById(R.id.etModBusquedaCedula);
        etNombre = findViewById(R.id.etModNombre);
        etContra = findViewById(R.id.etModContrasena);
        layoutCampos = findViewById(R.id.layoutCamposMod);

        findViewById(R.id.btnModBuscar).setOnClickListener(v -> buscar());
        findViewById(R.id.btnModActualizar).setOnClickListener(v -> actualizar());
        findViewById(R.id.btnModEliminar).setOnClickListener(v -> eliminar());
    }

    private void buscar() {
        String cedula = etBusqueda.getText().toString().trim();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "agenda", null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();

        Cursor cursor = db.rawQuery("select nombre, contrasena from usuarios where cedula='" + cedula + "'", null);

        if (cursor.moveToFirst()) {
            cedulaEncontrada = cedula;
            etNombre.setText(cursor.getString(0));
            etContra.setText(cursor.getString(1));
            layoutCampos.setVisibility(View.VISIBLE);
        } else {
            layoutCampos.setVisibility(View.GONE);
            Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        db.close();
    }

    private void actualizar() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "agenda", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre", etNombre.getText().toString());
        values.put("contrasena", etContra.getText().toString());

        int cant = db.update("usuarios", values, "cedula='" + cedulaEncontrada + "'", null);
        db.close();

        if (cant == 1) {
            Toast.makeText(this, "Usuario actualizado", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void eliminar() {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "agenda", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        db.delete("usuarios", "cedula='" + cedulaEncontrada + "'", null);
        db.delete("perfil", "cedula='" + cedulaEncontrada + "'", null);
        db.delete("gustos", "cedula='" + cedulaEncontrada + "'", null);
        db.delete("preferencias", "cedula='" + cedulaEncontrada + "'", null);
        
        db.close();
        Toast.makeText(this, "Usuario eliminado", Toast.LENGTH_SHORT).show();
        finish();
    }
}