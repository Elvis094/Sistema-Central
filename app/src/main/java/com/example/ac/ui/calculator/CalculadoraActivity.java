package com.example.ac.ui.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ac.R;

public class CalculadoraActivity extends AppCompatActivity {

    private TextView tvDisplay;
    private double operando1 = 0;
    private String operacionActual = "";
    private boolean esNuevaOperacion = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);

        tvDisplay = findViewById(R.id.tvDisplay);
        findViewById(R.id.btnVolver).setOnClickListener(v -> finish()); // Cierra la activity y vuelve

        View.OnClickListener listenerNumeros = v -> {
            Button b = (Button) v;
            if (esNuevaOperacion) {
                tvDisplay.setText(b.getText().toString());
                esNuevaOperacion = false;
            } else {
                tvDisplay.append(b.getText().toString());
            }
        };

        // Asignar listeners a los números
        int[] numIds = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnPunto};
        for (int id : numIds) {
            findViewById(id).setOnClickListener(listenerNumeros);
        }

        // Operaciones
        View.OnClickListener listenerOperaciones = v -> {
            Button b = (Button) v;
            operando1 = Double.parseDouble(tvDisplay.getText().toString());
            operacionActual = b.getText().toString();
            esNuevaOperacion = true;
        };

        findViewById(R.id.btnSum).setOnClickListener(listenerOperaciones);
        findViewById(R.id.btnRes).setOnClickListener(listenerOperaciones);
        findViewById(R.id.btnMult).setOnClickListener(listenerOperaciones);
        findViewById(R.id.btnDiv).setOnClickListener(listenerOperaciones);

        findViewById(R.id.btnIgual).setOnClickListener(v -> calcular());

        findViewById(R.id.btnC).setOnClickListener(v -> {
            tvDisplay.setText("0");
            operando1 = 0;
            esNuevaOperacion = true;
        });

        findViewById(R.id.btnDel).setOnClickListener(v -> {
            String texto = tvDisplay.getText().toString();
            if (texto.length() > 1) {
                tvDisplay.setText(texto.substring(0, texto.length() - 1));
            } else {
                tvDisplay.setText("0");
                esNuevaOperacion = true;
            }
        });
    }

    private void calcular() {
        double operando2 = Double.parseDouble(tvDisplay.getText().toString());
        double resultado = 0;

        switch (operacionActual) {
            case "+": resultado = operando1 + operando2; break;
            case "-": resultado = operando1 - operando2; break;
            case "*": resultado = operando1 * operando2; break;
            case "/":
                if (operando2 != 0) resultado = operando1 / operando2;
                break;
        }

        // Mostrar sin decimales si es un número entero
        if (resultado % 1 == 0) {
            tvDisplay.setText(String.valueOf((long) resultado));
        } else {
            tvDisplay.setText(String.valueOf(resultado));
        }
        esNuevaOperacion = true;
    }
}