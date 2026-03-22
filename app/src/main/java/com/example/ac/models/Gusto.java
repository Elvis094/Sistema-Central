package com.example.ac.models;

import java.io.Serializable;

public class Gusto implements Serializable {
    private String id;
    private String descripcion; // E.g., "Cine", "Música"

    public Gusto(String id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    // Getters
    public String getId() { return id; }
    public String getDescripcion() { return descripcion; }
}