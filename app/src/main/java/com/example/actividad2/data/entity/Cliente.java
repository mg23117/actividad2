package com.example.actividad2.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "clientes")
public class Cliente{
    public Cliente(String nombre, String telefono, String apellido) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.apellido = apellido;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nombre;

    private String apellido;
    private String telefono;
}