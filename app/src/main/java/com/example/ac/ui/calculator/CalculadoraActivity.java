package com.example.ac.ui.calculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ac.R;

public class CalculadoraActivity extends AppCompatActivity {

    private EditText et1, et2;
    private TextView tvRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);

        et1 = findViewById(R.id.etNum1);
        et2 = findViewById(R.id.etNum2);
        tvRes = findViewById(R.id.tvResultadoCalc);

        findViewById(R.id.btnSuma).setOnClickListener(v -> calcular('+'));
        findViewById(R.id.btnResta).setOnClickListener(v -> calcular('-'));
        findViewById(R.id.btnMulti).setOnClickListener(v -> calcular('*'));
        findViewById(R.id.btnDivi).setOnClickListener(v -> calcular('/'));
    }

    private void calcular(char op) {
        String s1 = et1.getText().toString();
        String s2 = et2.getText().toString();

        if (s1.isEmpty() || s2.isEmpty()) {
            Toast.makeText(this, "Ingresa ambos números", Toast.LENGTH_SHORT).show();
            return;
        }

        double n1 = Double.parseDouble(s1);
        double n2 = Double.parseDouble(s2);
        double res = 0;

        switch (op) {
            case '+': res = n1 + n2; break;
            case '-': res = n1 - n2; break;
            case '*': res = n1 * n2; break;
            case '/': 
                if (n2 != 0) res = n1 / n2; 
                else {
                    Toast.makeText(this, "División por cero", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
        }

        tvRes.setText("Resultado: " + res);
    }
}