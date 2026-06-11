package com.example.actividad2.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "servicios",
        foreignKeys = @ForeignKey(
                entity = Cliente.class,
                parentColumns = "id",
                childColumns = "cliente_id",
                onDelete = ForeignKey.CASCADE
        )
)
public class Servicio{
    public Servicio(int clienteId, String descripcion, double precio) {
        this.clienteId = clienteId;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "cliente_id")
    public int clienteId;
    public String descripcion;
    public double precio;

    // ========== GETTERS Y SETTERS ==========

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}