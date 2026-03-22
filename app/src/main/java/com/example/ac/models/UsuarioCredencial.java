package com.example.ac.models;

import java.io.Serializable;

public class UsuarioCredencial implements Serializable {
    private String cedula;
    private String nombre;
    private String contrasena;

    public UsuarioCredencial(String cedula, String nombre, String contrasena) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    // Getters
    public String getCedula() { return cedula; }
    public String getNombre() { return nombre; }
    public String getContrasena() { return contrasena; }
}