package com.example.ac.ui.user;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ac.AdminSQLiteOpenHelper;
import com.example.ac.R;
import com.example.ac.ui.admin.AdminMainActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etCedula, etContrasena;
    private Button btnLoginIngresar;
    private TextView tvIrRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etCedula = findViewById(R.id.etLoginCedula);
        etContrasena = findViewById(R.id.etLoginContrasena);
        btnLoginIngresar = findViewById(R.id.btnLoginIngresar);
        tvIrRegistro = findViewById(R.id.tvIrRegistro);

        btnLoginIngresar.setOnClickListener(v -> {
            String cedula = etCedula.getText().toString().trim();
            String contrasena = etContrasena.getText().toString().trim();

            if (cedula.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "agenda", null, 1);
            SQLiteDatabase db = admin.getReadableDatabase();

            Cursor cursor = db.rawQuery("select rol, nombre from usuarios where cedula='" + cedula + "' and contrasena='" + contrasena + "'", null);

            if (cursor.moveToFirst()) {
                String rol = cursor.getString(0);
                String nombre = cursor.getString(1);
                
                Intent intent;
                if (rol.equals("ADMIN")) {
                    intent = new Intent(this, AdminMainActivity.class);
                } else {
                    intent = new Intent(this, UserMainActivity.class);
                }
                intent.putExtra("user_id", cedula);
                intent.putExtra("user_name", nombre);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
            db.close();
        });

        tvIrRegistro.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}