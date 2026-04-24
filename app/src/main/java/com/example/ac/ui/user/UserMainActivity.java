package com.example.ac.ui.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ac.R;
import com.example.ac.ui.calculator.CalculadoraActivity;
import com.example.ac.ui.agenda.AgendaMainActivity;
import com.example.ac.ui.agenda.SearchUserActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UserMainActivity extends AppCompatActivity {

    private String userId;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        // 1. RECUPERAR DATOS DE LA SESIÓN (Aquí arreglamos el NULL)
        SharedPreferences session = getSharedPreferences("SesionActiva", MODE_PRIVATE);
        userId = session.getString("cedula_logueada", "Desconocido");
        userName = session.getString("nombre_logueado", "Usuario");

        // 2. MOSTRAR BIENVENIDA Y CÉDULA
        TextView tvUserId = findViewById(R.id.tvUserIdDisplay);
        // Supongamos que tienes un TextView para el nombre, si no, lo añadimos al de Bienvenida
        tvUserId.setText("Cédula: " + userId + "\nBienvenido, " + userName);

        // 3. BOTÓN BUSCAR (FAB)
        FloatingActionButton btnBuscar = findViewById(R.id.fabBuscarUsuario);
        btnBuscar.setOnClickListener(view -> {
            startActivity(new Intent(UserMainActivity.this, SearchUserActivity.class));
        });

        // 4. BOTÓN CALCULADORA
        findViewById(R.id.btnIrCalculadora).setOnClickListener(v -> {
            startActivity(new Intent(this, CalculadoraActivity.class));
        });

        // 5. BOTÓN AGENDA (Pasamos el ID por si acaso, aunque ya está en sesión)
        findViewById(R.id.btnIrAgenda).setOnClickListener(v -> {
            Intent intent = new Intent(this, AgendaMainActivity.class);
            intent.putExtra("user_id", userId);
            startActivity(intent);
        });

        // 6. CERRAR SESIÓN (Limpiamos la memoria al salir)
        findViewById(R.id.btnCerrarSesion).setOnClickListener(v -> {
            SharedPreferences.Editor editor = session.edit();
            editor.clear(); // Borra la sesión para que el siguiente no vea tus datos
            editor.apply();

            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}