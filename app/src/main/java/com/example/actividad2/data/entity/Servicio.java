package com.example.actividad2.data.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.actividad2.data.entity.enums.EstadoServicio;
import com.example.actividad2.data.entity.enums.TipoServicio;

@Entity(
        tableName = "servicios",
        foreignKeys = @ForeignKey(
                entity = Cliente.class,
                parentColumns = "id",
                childColumns = "cliente_id",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index(value = "cliente_id")}
)
public class Servicio{
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "cliente_id")
    private int clienteId;
    @NonNull
    @ColumnInfo(name = "tipo_servicio")
    private TipoServicio tipoServicio;

    @NonNull
    private EstadoServicio estado;

    @NonNull
    @ColumnInfo(name = "descripcion_servicio")
    private String descripcionServicio;

    @ColumnInfo(name = "costo_mano_obra")
    private double costoManoObra;

    @ColumnInfo(name = "costo_materiales")
    private double costoMateriales;

    @ColumnInfo(name = "eliminado")
    private boolean eliminado = false;

    public Servicio(int clienteId, @NonNull TipoServicio tipoServicio, @NonNull EstadoServicio estado, @NonNull String descripcionServicio, double costoManoObra, double costoMateriales) {
        this.clienteId = clienteId;
        this.tipoServicio = tipoServicio;
        this.estado = estado;
        this.descripcionServicio = descripcionServicio;
        this.costoManoObra = costoManoObra;
        this.costoMateriales = costoMateriales;
    }

    public Servicio(){}

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

    @NonNull
    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(@NonNull TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    @NonNull
    public EstadoServicio getEstado() {
        return estado;
    }

    public void setEstado(@NonNull EstadoServicio estado) {
        this.estado = estado;
    }

    @NonNull
    public String getDescripcionServicio() {
        return descripcionServicio;
    }

    public void setDescripcionServicio(@NonNull String descripcionServicio) {
        this.descripcionServicio = descripcionServicio;
    }

    public double getCostoManoObra() {
        return costoManoObra;
    }

    public void setCostoManoObra(double costoManoObra) {
        this.costoManoObra = costoManoObra;
    }

    public double getCostoMateriales() {
        return costoMateriales;
    }

    public void setCostoMateriales(double costoMateriales) {
        this.costoMateriales = costoMateriales;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
}