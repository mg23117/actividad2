package com.example.actividad2.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.actividad2.data.entity.Servicio;

import java.util.List;

@Dao
public interface ServicioDao {
    @Insert
    void insertar(Servicio servicio);

    @Query("SELECT * FROM servicios WHERE cliente_id = :clienteId")
    List<Servicio> obtenerPorCliente(int clienteId);

    @Delete
    void eliminar(Servicio servicio);

    @Query("SELECT * FROM servicios WHERE id = :id")
    Servicio obtenerPorId(int id);
}