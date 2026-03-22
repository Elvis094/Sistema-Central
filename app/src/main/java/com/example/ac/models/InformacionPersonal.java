package com.example.ac.models;

public class InformacionPersonal {
    private String nombre; // Ahora guardará el nombre completo aquí
    private String numero;
    private String genero;
    private String direccion;
    private String lugarResidencia;
    private String correoElectronico;
    private String tipoSangre;
    private String anioNacimiento;

    public InformacionPersonal(String nombre, String numero, String genero, String direccion, String lugarResidencia, String correoElectronico, String tipoSangre, String anioNacimiento) {
        this.nombre = nombre;
        this.numero = numero;
        this.genero = genero;
        this.direccion = direccion;
        this.lugarResidencia = lugarResidencia;
        this.correoElectronico = correoElectronico;
        this.tipoSangre = tipoSangre;
        this.anioNacimiento = anioNacimiento;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getLugarResidencia() { return lugarResidencia; }
    public void setLugarResidencia(String lugarResidencia) { this.lugarResidencia = lugarResidencia; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getTipoSangre() { return tipoSangre; }
    public void setTipoSangre(String tipoSangre) { this.tipoSangre = tipoSangre; }

    public String getAnioNacimiento() { return anioNacimiento; }
    public void setAnioNacimiento(String anioNacimiento) { this.anioNacimiento = anioNacimiento; }
}