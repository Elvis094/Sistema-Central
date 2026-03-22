package com.example.ac.models;

import java.io.Serializable;
import java.util.ArrayList;

public class AgendaUser implements Serializable {
    private String cedula; // El ID que lo vincula
    private InformacionPersonal informacionPersonal;
    private ArrayList<Gusto> gustos;
    private ArrayList<Preferencia> preferencias;

    public AgendaUser(String cedula) {
        this.cedula = cedula;
        this.gustos = new ArrayList<>();
        this.preferencias = new ArrayList<>();
    }

    // Getters
    public String getCedula() { return cedula; }
    public InformacionPersonal getInformacionPersonal() { return informacionPersonal; }
    public ArrayList<Gusto> getGustos() { return gustos; }
    public ArrayList<Preferencia> getPreferencias() { return preferencias; }

    // Setters
    public void setInformacionPersonal(InformacionPersonal informacionPersonal) { this.informacionPersonal = informacionPersonal; }
}