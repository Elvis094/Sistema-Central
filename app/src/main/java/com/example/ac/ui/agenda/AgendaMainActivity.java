package com.example.ac.ui.agenda;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ac.R;
import com.example.ac.ui.user.UserMainActivity;

public class AgendaMainActivity extends AppCompatActivity {

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_main);

        // Recuperamos la cédula del usuario que inició sesión
        userId = getIntent().getStringExtra("user_id");

        findViewById(R.id.btnAgendaInfoPersonal).setOnClickListener(v -> {
            Intent intent = new Intent(this, PersonalInfoActivity.class);
            intent.putExtra("user_id", userId);
            startActivity(intent);
        });

        findViewById(R.id.btnAgendaGustos).setOnClickListener(v -> {
            Intent intent = new Intent(this, LikesActivity.class);
            intent.putExtra("user_id", userId);
            startActivity(intent);
        });

        findViewById(R.id.btnAgendaPreferencias).setOnClickListener(v -> {
            Intent intent = new Intent(this, PreferencesActivity.class);
            intent.putExtra("user_id", userId);
            startActivity(intent);
        });

        findViewById(R.id.btnVolverUserMain).setOnClickListener(v -> {
            finish(); // Simplemente cierra esta pantalla y vuelve al menú anterior
        });
    }
}