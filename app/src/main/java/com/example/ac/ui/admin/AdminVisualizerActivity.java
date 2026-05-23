package com.example.ac.ui.admin;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ac.AdminSQLiteOpenHelper;
import com.example.ac.R;

public class AdminVisualizerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_visualizer);

        TextView tvLista = findViewById(R.id.tvListaDatos);
        findViewById(R.id.btnVolverAdmin).setOnClickListener(v -> finish());

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "agenda", null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();

        StringBuilder sb = new StringBuilder();

        // Obtener todos los usuarios (excepto el propio admin maestro para brevedad si se desea, pero mejor todos)
        Cursor cUser = db.rawQuery("select cedula, nombre, rol from usuarios", null);
        
        while (cUser.moveToNext()) {
            String cedula = cUser.getString(0);
            String nombre = cUser.getString(1);
            String rol = cUser.getString(2);

            sb.append("==============================\n");
            sb.append("USUARIO: ").append(nombre).append(" (").append(rol).append(")\n");
            sb.append("CÉDULA: ").append(cedula).append("\n");

            // Información Perfil
            Cursor cPerfil = db.rawQuery("select numero, email, sexo, direccion, residencia from perfil where cedula='" + cedula + "'", null);
            if (cPerfil.moveToFirst()) {
                sb.append("\n[INFO PERSONAL]\n");
                sb.append("• Tel: ").append(cPerfil.getString(0)).append("\n");
                sb.append("• Email: ").append(cPerfil.getString(1)).append("\n");
                sb.append("• Sexo: ").append(cPerfil.getString(2)).append("\n");
                sb.append("• Dir: ").append(cPerfil.getString(3)).append("\n");
                sb.append("• Res: ").append(cPerfil.getString(4)).append("\n");
            }
            cPerfil.close();

            // Gustos
            Cursor cGustos = db.rawQuery("select categoria, seleccion from gustos where cedula='" + cedula + "'", null);
            if (cGustos.getCount() > 0) {
                sb.append("\n[GUSTOS]\n");
                while (cGustos.moveToNext()) {
                    sb.append("• ").append(cGustos.getString(0)).append(": ").append(cGustos.getString(1)).append("\n");
                }
            }
            cGustos.close();

            // Preferencias
            Cursor cPref = db.rawQuery("select descripcion from preferencias where cedula='" + cedula + "'", null);
            if (cPref.moveToFirst()) {
                sb.append("\n[PREFERENCIAS]\n");
                sb.append(cPref.getString(0)).append("\n");
            }
            cPref.close();
            
            sb.append("\n");
        }
        cUser.close();

        if (sb.length() == 0) {
            tvLista.setText("No hay datos registrados.");
        } else {
            tvLista.setText(sb.toString());
        }

        db.close();
    }
}