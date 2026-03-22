package com.example.ac.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ac.R;
import com.example.ac.ui.calculator.CalculadoraActivity;
import com.example.ac.ui.agenda.AgendaMainActivity;
import com.example.ac.ui.main.MainActivity;

public class UserMainActivity extends AppCompatActivity {

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        com.google.android.material.floatingactionbutton.FloatingActionButton btnBuscar = findViewById(R.id.fabBuscarUsuario);

        btnBuscar.setOnClickListener(view -> {
            android.content.Intent intentBusqueda = new android.content.Intent(UserMainActivity.this, com.example.ac.ui.agenda.SearchUserActivity.class);
            startActivity(intentBusqueda);
        });

        // Recibimos la cédula del Login
        userId = getIntent().getStringExtra("user_id");

        TextView tvUserId = findViewById(R.id.tvUserIdDisplay);
        tvUserId.setText("Cédula: " + userId);

        findViewById(R.id.btnIrCalculadora).setOnClickListener(v -> {
            startActivity(new Intent(this, CalculadoraActivity.class));
        });

        findViewById(R.id.btnIrAgenda).setOnClickListener(v -> {
            Intent intent = new Intent(this, AgendaMainActivity.class);
            intent.putExtra("user_id", userId);
            startActivity(intent);
        });

        findViewById(R.id.btnCerrarSesion).setOnClickListener(v -> {
            // Regresamos al selector inicial de roles
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        });
    }
}