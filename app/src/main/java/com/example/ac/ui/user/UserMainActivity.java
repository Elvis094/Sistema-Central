package com.example.ac.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ac.R;
import com.example.ac.ui.calculator.CalculadoraActivity;

public class UserMainActivity extends AppCompatActivity {

    private String userId, userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        userId = getIntent().getStringExtra("user_id");
        userName = getIntent().getStringExtra("user_name");

        TextView tvWelcome = findViewById(R.id.tvUserWelcome);
        tvWelcome.setText("Bienvenido, " + userName);

        findViewById(R.id.btnPersonalInfo).setOnClickListener(v -> {
            Intent intent = new Intent(this, PersonalInfoActivity.class);
            intent.putExtra("user_id", userId);
            startActivity(intent);
        });

        findViewById(R.id.btnLikes).setOnClickListener(v -> {
            Intent intent = new Intent(this, LikesActivity.class);
            intent.putExtra("user_id", userId);
            startActivity(intent);
        });

        findViewById(R.id.btnPreferences).setOnClickListener(v -> {
            Intent intent = new Intent(this, PreferencesActivity.class);
            intent.putExtra("user_id", userId);
            startActivity(intent);
        });

        findViewById(R.id.btnCalculadora).setOnClickListener(v -> {
            startActivity(new Intent(this, CalculadoraActivity.class));
        });

        findViewById(R.id.btnCerrarSesionUser).setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}