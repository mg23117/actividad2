package com.example.actividad2.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "clientes")
public class Cliente{
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    @ColumnInfo(collate = ColumnInfo.LOCALIZED)
    private String nombre;
    @NonNull
    private String telefono;
    @NonNull
    private String email;
    private String direccion;
    private String municipio;
    private String notas;
    private boolean activo;

    public Cliente(@NonNull String nombre, @NonNull String telefono, String direccion, @NonNull String email, String municipio, String notas) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.municipio = municipio;
        this.notas = notas;
        this.activo = true;
        setTelefono(telefono);
        setEmail(email);
    }

    public Cliente(){}

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getNombre() {
        return nombre;
    }

    public void setNombre(@NonNull String nombre) {
        this.nombre = nombre;
    }

    @NonNull
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(@NonNull String telefono) {
        this.telefono = telefono;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public boolean isActivo() { return activo; }

    public void setActivo(boolean activo) { this.activo = activo; }

}