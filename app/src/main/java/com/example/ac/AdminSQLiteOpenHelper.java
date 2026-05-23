package com.example.ac;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabla de Usuarios para Login y Registro
        db.execSQL("create table usuarios(cedula text primary key, nombre text, contrasena text, rol text)");
        
        // Tabla de Información Personal del Usuario
        db.execSQL("create table perfil(cedula text primary key, numero text, email text, sexo text, direccion text, residencia text, " +
                "FOREIGN KEY(cedula) REFERENCES usuarios(cedula))");
        
        // Tabla de Gustos (Selección por categorías)
        db.execSQL("create table gustos(id_gusto integer primary key autoincrement, cedula text, categoria text, seleccion text, " +
                "FOREIGN KEY(cedula) REFERENCES usuarios(cedula))");
        
        // Tabla de Preferencias (Descripción de gustos)
        db.execSQL("create table preferencias(cedula text primary key, descripcion text, " +
                "FOREIGN KEY(cedula) REFERENCES usuarios(cedula))");

        // Insertar Admin por defecto
        db.execSQL("insert into usuarios values('0000', 'Administrador', 'admin123', 'ADMIN')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists usuarios");
        db.execSQL("drop table if exists perfil");
        db.execSQL("drop table if exists gustos");
        db.execSQL("drop table if exists preferencias");
        onCreate(db);
    }
}