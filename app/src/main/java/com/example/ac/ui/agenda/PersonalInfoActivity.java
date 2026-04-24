package com.example.ac.ui.agenda;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ac.R;
import com.example.ac.models.AgendaUser;
import com.example.ac.models.InformacionPersonal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class PersonalInfoActivity extends AppCompatActivity {

    private String userId;
    private ArrayList<AgendaUser> listaAgendas;

    // Componentes de la interfaz
    private EditText etNombre, etTelefono, etDireccion, etLugar, etCorreo, etSangre, etAnio;
    private RadioGroup rgGenero;
    private RadioButton rbFemenino, rbMasculino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        // 1. RECUPERAR ID DE SESIÓN (Esto evita que userId sea null)
        SharedPreferences session = getSharedPreferences("SesionActiva", MODE_PRIVATE);
        userId = session.getString("cedula_logueada", "");

        // 2. VINCULAR VISTAS
        etNombre = findViewById(R.id.etInfoNombre);
        etTelefono = findViewById(R.id.etInfoTelefono);
        rgGenero = findViewById(R.id.rgInfoGenero);
        rbFemenino = findViewById(R.id.rbFemenino);
        rbMasculino = findViewById(R.id.rbMasculino);
        etDireccion = findViewById(R.id.etInfoDireccion);
        etLugar = findViewById(R.id.etInfoLugar);
        etCorreo = findViewById(R.id.etInfoCorreo);
        etSangre = findViewById(R.id.etInfoSangre);
        etAnio = findViewById(R.id.etInfoAnio);

        // 3. CARGAR BASE DE DATOS Y MOSTRAR INFORMACIÓN
        cargarAgendas();
        mostrarDatosSiExisten();

        // 4. BOTÓN GUARDAR
        findViewById(R.id.btnGuardarInfoPersonal).setOnClickListener(v -> guardarDatos());
    }

    private void mostrarDatosSiExisten() {
        boolean infoEncontrada = false;

        // Intentamos buscar en la lista de agendas guardadas
        for (AgendaUser user : listaAgendas) {
            if (user.getCedula().equals(userId) && user.getInformacionPersonal() != null) {
                InformacionPersonal info = user.getInformacionPersonal();
                etNombre.setText(info.getNombre());
                etTelefono.setText(info.getNumero());

                if ("Masculino".equals(info.getGenero())) {
                    rbMasculino.setChecked(true);
                } else {
                    rbFemenino.setChecked(true);
                }

                etDireccion.setText(info.getDireccion());
                etLugar.setText(info.getLugarResidencia());
                etCorreo.setText(info.getCorreoElectronico());
                etSangre.setText(info.getTipoSangre());
                etAnio.setText(info.getAnioNacimiento());

                infoEncontrada = true;
                break;
            }
        }

        // SI NO HAY INFO EN AGENDA: Recuperamos el nombre desde el registro original
        if (!infoEncontrada) {
            SharedPreferences usuariosPrefs = getSharedPreferences("MisUsuarios", MODE_PRIVATE);
            String nombreRegistro = usuariosPrefs.getString(userId + "_nombre", "Usuario");
            etNombre.setText(nombreRegistro);
            rbFemenino.setChecked(true); // Valor por defecto
        }
    }

    private void guardarDatos() {
        // Validar género seleccionado
        String gender = (rgGenero.getCheckedRadioButtonId() == rbMasculino.getId()) ? "Masculino" : "Femenino";

        // Crear objeto con la nueva información
        InformacionPersonal nuevaInfo = new InformacionPersonal(
                etNombre.getText().toString().trim(),
                etTelefono.getText().toString().trim(),
                gender,
                etDireccion.getText().toString().trim(),
                etLugar.getText().toString().trim(),
                etCorreo.getText().toString().trim(),
                etSangre.getText().toString().trim(),
                etAnio.getText().toString().trim()
        );

        // Actualizar el usuario en la lista
        boolean actualizado = false;
        for (AgendaUser user : listaAgendas) {
            if (user.getCedula().equals(userId)) {
                user.setInformacionPersonal(nuevaInfo);
                actualizado = true;
                break;
            }
        }

        // Si es un usuario nuevo en la agenda, lo añadimos
        if (!actualizado) {
            AgendaUser nuevoUser = new AgendaUser(userId);
            nuevoUser.setInformacionPersonal(nuevaInfo);
            listaAgendas.add(nuevoUser);
        }

        // GUARDAR EN PREFERENCES USANDO GSON
        SharedPreferences prefs = getSharedPreferences("BaseDatosAgenda", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(listaAgendas);
        editor.putString("agendas_guardadas", json);
        editor.apply();

        Toast.makeText(this, "Información Personal Actualizada", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void cargarAgendas() {
        SharedPreferences prefs = getSharedPreferences("BaseDatosAgenda", MODE_PRIVATE);
        String json = prefs.getString("agendas_guardadas", null);
        Type type = new TypeToken<ArrayList<AgendaUser>>() {}.getType();
        listaAgendas = new Gson().fromJson(json, type);

        if (listaAgendas == null) {
            listaAgendas = new ArrayList<>();
        }
    }
}