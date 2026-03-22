package com.example.ac.ui.agenda;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ac.R;
import com.example.ac.models.AgendaUser;
import com.example.ac.models.Gusto; // Asumiendo que existe
import com.example.ac.models.Preferencia; // Asumiendo que existe
import com.example.ac.models.InformacionPersonal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class SearchUserActivity extends AppCompatActivity {

    private EditText etBuscarCedula;
    private ArrayList<AgendaUser> listaAgendas;

    // Vistas de resultados profesionales
    private LinearLayout containerResultados;
    private TextView tvEstadoEspera;
    private TextView tvResCedula, tvResNombre, tvResTelefono, tvResCorreo;
    private TextView tvResGustos, tvResPreferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        // Inicializar todas las vistas
        etBuscarCedula = findViewById(R.id.etBuscarCedula);
        Button btnBuscarUsuario = findViewById(R.id.btnBuscarUsuario);

        containerResultados = findViewById(R.id.containerResultados);
        tvEstadoEspera = findViewById(R.id.tvEstadoEspera);

        // Datos Personales
        tvResCedula = findViewById(R.id.tvResCedula);
        tvResNombre = findViewById(R.id.tvResNombre);
        tvResTelefono = findViewById(R.id.tvResTelefono);
        tvResCorreo = findViewById(R.id.tvResCorreo);

        // Gustos/Prefs
        tvResGustos = findViewById(R.id.tvResGustos);
        tvResPreferencias = findViewById(R.id.tvResPreferencias);

        cargarBaseDatos();

        btnBuscarUsuario.setOnClickListener(v -> buscarUsuario());
    }

    private void buscarUsuario() {
        String cedulaBuscada = etBuscarCedula.getText().toString().trim();

        if (cedulaBuscada.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese una cédula", Toast.LENGTH_SHORT).show();
            return;
        }

        AgendaUser usuarioEncontrado = null;
        for (AgendaUser user : listaAgendas) {
            if (user.getCedula().equals(cedulaBuscada)) {
                usuarioEncontrado = user;
                break;
            }
        }

        if (usuarioEncontrado != null) {
            mostrarPerfil(usuarioEncontrado);
        } else {
            // No encontrado
            containerResultados.setVisibility(View.GONE);
            tvEstadoEspera.setVisibility(View.VISIBLE);
            tvEstadoEspera.setText("Cédula " + cedulaBuscada + " no encontrada.");
            tvEstadoEspera.setTextColor(getResources().getColor(R.color.red_error));
        }
    }

    private void mostrarPerfil(AgendaUser user) {
        // Ocultar espera, mostrar resultados
        tvEstadoEspera.setVisibility(View.GONE);
        containerResultados.setVisibility(View.VISIBLE);

        // 1. Asignar Datos Personales
        tvResCedula.setText("Cédula: " + user.getCedula());

        if (user.getInformacionPersonal() != null) {
            InformacionPersonal info = user.getInformacionPersonal();
            tvResNombre.setText("Nombre: " + info.getNombre());
            tvResTelefono.setText("Teléfono: " + info.getNumero());
            tvResCorreo.setText("Correo: " + info.getCorreoElectronico());
        } else {
            tvResNombre.setText("Nombre: Sin registrar");
            tvResTelefono.setText("Teléfono: Sin registrar");
            tvResCorreo.setText("Correo: Sin registrar");
        }

        // --- SOLUCIÓN: Mostrar Gustos y Preferencias ---

        // Formatear Gustos
        if (user.getGustos() != null && !user.getGustos().isEmpty()) {
            StringBuilder gustosStr = new StringBuilder();
            for (Gusto gusto : user.getGustos()) {
                gustosStr.append("• ").append(gusto.getDescripcion()).append("\n");
            }
            tvResGustos.setText(gustosStr.toString().trim());
        } else {
            tvResGustos.setText("Gustos: No hay datos registrados.");
            tvResGustos.setTextColor(getResources().getColor(R.color.text_muted));
        }

        // Formatear Preferencias
        if (user.getPreferencias() != null && !user.getPreferencias().isEmpty()) {
            StringBuilder prefsStr = new StringBuilder();
            for (Preferencia pref : user.getPreferencias()) {
                prefsStr.append("• ").append(pref.getClave()).append(": ").append(pref.getValor()).append("\n");
            }
            tvResPreferencias.setText(prefsStr.toString().trim());
        } else {
            tvResPreferencias.setText("Preferencias: No hay datos registrados.");
            tvResPreferencias.setTextColor(getResources().getColor(R.color.text_muted));
        }
    }

    private void cargarBaseDatos() {
        SharedPreferences prefs = getSharedPreferences("BaseDatosAgenda", MODE_PRIVATE);
        String json = prefs.getString("agendas_guardadas", null);
        Type type = new TypeToken<ArrayList<AgendaUser>>() {}.getType();
        listaAgendas = new Gson().fromJson(json, type);
        if (listaAgendas == null) listaAgendas = new ArrayList<>();
    }
}