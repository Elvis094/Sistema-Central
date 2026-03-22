package com.example.ac.ui.main;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ac.R;
import com.example.ac.ui.admin.AdminMainActivity;
import com.example.ac.ui.user.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnSoyAdmin).setOnClickListener(v -> {
            // El admin entra directo a su panel
            startActivity(new Intent(this, AdminMainActivity.class));
        });

        findViewById(R.id.btnSoyUsuario).setOnClickListener(v -> {
            // El usuario debe pasar por el Login primero
            startActivity(new Intent(this, LoginActivity.class));
        });
    }
}