package com.example.ac.models;

import java.io.Serializable;

public class Preferencia implements Serializable {
    private String clave; // E.g., "Color", "Comida"
    private String valor; // E.g., "Azul", "Pizza"

    public Preferencia(String clave, String valor) {
        this.clave = clave;
        this.valor = valor;
    }

    // Getters
    public String getClave() { return clave; }
    public String getValor() { return valor; }
}