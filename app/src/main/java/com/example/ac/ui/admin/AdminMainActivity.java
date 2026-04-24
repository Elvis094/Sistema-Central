package com.example.ac.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ac.R;
import com.example.ac.ui.user.LoginActivity;

public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        findViewById(R.id.btnCrearUsuario).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminCreateUserActivity.class));
        });

        findViewById(R.id.btnVerBaseDatos).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminVisualizerActivity.class));
        });

        findViewById(R.id.btnModificarUsuarios).setOnClickListener(v -> {
            startActivity(new Intent(this, AdminModifyActivity.class));
        });

        findViewById(R.id.btnCerrarSesionAdmin).setOnClickListener(v -> {
            Intent intent = new Intent(AdminMainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}