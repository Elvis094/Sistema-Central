package com.example.ac.ui.admin;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ac.R;
import com.example.ac.models.UsuarioCredencial;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class AdminModifyActivity extends AppCompatActivity {

    private ArrayList<UsuarioCredencial> listaCredenciales;
    private UsuarioCredencial usuarioEncontrado = null;
    private int indexEncontrado = -1;

    private EditText etBusqueda, etNuevoNombre, etNuevaContra;
    private LinearLayout layoutDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_modify);

        etBusqueda = findViewById(R.id.etModificarBusquedaCedula);
        etNuevoNombre = findViewById(R.id.etModificarNombre);
        etNuevaContra = findViewById(R.id.etModificarContrasena);
        layoutDatos = findViewById(R.id.layoutDatosModificar);

        cargarCredenciales();

        // Botón Buscar
        findViewById(R.id.btnModificarBuscar).setOnClickListener(v -> {
            String cedula = etBusqueda.getText().toString().trim();
            buscarUsuario(cedula);
        });

        // Botón Actualizar
        findViewById(R.id.btnGuardarModificacion).setOnClickListener(v -> {
            if (usuarioEncontrado != null) {
                String nom = etNuevoNombre.getText().toString().trim();
                String pass = etNuevaContra.getText().toString().trim();

                if(!nom.isEmpty() && !pass.isEmpty()) {
                    // Actualizamos el objeto en la lista
                    listaCredenciales.set(indexEncontrado, new UsuarioCredencial(usuarioEncontrado.getCedula(), nom, pass));
                    guardarCredenciales();
                    Toast.makeText(this, "Usuario Actualizado Exitosamente", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Los campos no pueden estar vacíos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Botón Eliminar
        findViewById(R.id.btnEliminarUsuario).setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("PELIGRO")
                    .setMessage("¿Seguro que deseas eliminar a este usuario? Ya no podrá iniciar sesión.")
                    .setPositiveButton("ELIMINAR", (dialog, which) -> {
                        listaCredenciales.remove(indexEncontrado);
                        guardarCredenciales();
                        Toast.makeText(this, "Usuario Borrado del Sistema", Toast.LENGTH_LONG).show();
                        finish();
                    })
                    .setNegativeButton("CANCELAR", null)
                    .show();
        });
    }

    private void buscarUsuario(String cedula) {
        usuarioEncontrado = null;
        indexEncontrado = -1;

        for (int i = 0; i < listaCredenciales.size(); i++) {
            if (listaCredenciales.get(i).getCedula().equals(cedula)) {
                usuarioEncontrado = listaCredenciales.get(i);
                indexEncontrado = i;
                break;
            }
        }

        if (usuarioEncontrado != null) {
            layoutDatos.setVisibility(View.VISIBLE);
            etNuevoNombre.setText(usuarioEncontrado.getNombre());
            etNuevaContra.setText(usuarioEncontrado.getContrasena());
            Toast.makeText(this, "Usuario encontrado", Toast.LENGTH_SHORT).show();
        } else {
            layoutDatos.setVisibility(View.GONE);
            Toast.makeText(this, "No existe un usuario con esa cédula", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarCredenciales() {
        SharedPreferences prefs = getSharedPreferences("CredencialesUsuarios", MODE_PRIVATE);
        String json = prefs.getString("credenciales_guardadas", null);
        Type type = new TypeToken<ArrayList<UsuarioCredencial>>() {}.getType();
        listaCredenciales = new Gson().fromJson(json, type);
        if (listaCredenciales == null) listaCredenciales = new ArrayList<>();
    }

    private void guardarCredenciales() {
        SharedPreferences prefs = getSharedPreferences("CredencialesUsuarios", MODE_PRIVATE);
        prefs.edit().putString("credenciales_guardadas", new Gson().toJson(listaCredenciales)).apply();
    }
}