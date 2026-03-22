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
import com.example.ac.models.UsuarioCredencial;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class PersonalInfoActivity extends AppCompatActivity {

    private String userId;
    private ArrayList<AgendaUser> listaAgendas;
    private ArrayList<UsuarioCredencial> listaCredenciales;

    // Variables (Ya no existe etApellidos)
    private EditText etNombre, etTelefono, etDireccion, etLugar, etCorreo, etSangre, etAnio;
    private RadioGroup rgGenero;
    private RadioButton rbFemenino, rbMasculino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        userId = getIntent().getStringExtra("user_id");

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

        cargarAgendas();
        cargarCredenciales();

        mostrarDatosSiExisten();

        findViewById(R.id.btnGuardarInfoPersonal).setOnClickListener(v -> guardarDatos());
    }

    private void mostrarDatosSiExisten() {
        boolean infoEncontrada = false;

        for (AgendaUser user : listaAgendas) {
            if (user.getCedula().equals(userId) && user.getInformacionPersonal() != null) {
                InformacionPersonal info = user.getInformacionPersonal();
                etNombre.setText(info.getNombre());
                etTelefono.setText(info.getNumero());

                if ("Femenino".equals(info.getGenero())) {
                    rbFemenino.setChecked(true);
                } else if ("Masculino".equals(info.getGenero())) {
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

        // AUTOCOMPLETADO DIRECTO Y LIMPIO
        if (!infoEncontrada) {
            for (UsuarioCredencial cred : listaCredenciales) {
                if (cred.getCedula().equals(userId)) {
                    // Ponemos exactamente lo que el Admin escribió
                    etNombre.setText(cred.getNombre());
                    rbFemenino.setChecked(true);
                    break;
                }
            }
        }
    }

    private void guardarDatos() {
        String gender;
        int selectedId = rgGenero.getCheckedRadioButtonId();
        if (selectedId == rbFemenino.getId()) {
            gender = "Femenino";
        } else if (selectedId == rbMasculino.getId()) {
            gender = "Masculino";
        } else {
            gender = "Femenino";
        }

        // Creamos la nueva info SIN el parámetro de apellidos
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

        boolean encontrado = false;
        for (AgendaUser user : listaAgendas) {
            if (user.getCedula().equals(userId)) {
                user.setInformacionPersonal(nuevaInfo);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            AgendaUser nuevoUser = new AgendaUser(userId);
            nuevoUser.setInformacionPersonal(nuevaInfo);
            listaAgendas.add(nuevoUser);
        }

        SharedPreferences prefs = getSharedPreferences("BaseDatosAgenda", MODE_PRIVATE);
        prefs.edit().putString("agendas_guardadas", new Gson().toJson(listaAgendas)).apply();

        Toast.makeText(this, "Información Personal Actualizada", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void cargarAgendas() {
        SharedPreferences prefs = getSharedPreferences("BaseDatosAgenda", MODE_PRIVATE);
        String json = prefs.getString("agendas_guardadas", null);
        Type type = new TypeToken<ArrayList<AgendaUser>>() {}.getType();
        listaAgendas = new Gson().fromJson(json, type);
        if (listaAgendas == null) listaAgendas = new ArrayList<>();
    }

    private void cargarCredenciales() {
        SharedPreferences prefs = getSharedPreferences("CredencialesUsuarios", MODE_PRIVATE);
        String json = prefs.getString("credenciales_guardadas", null);
        Type type = new TypeToken<ArrayList<UsuarioCredencial>>() {}.getType();
        listaCredenciales = new Gson().fromJson(json, type);
        if (listaCredenciales == null) listaCredenciales = new ArrayList<>();
    }
}