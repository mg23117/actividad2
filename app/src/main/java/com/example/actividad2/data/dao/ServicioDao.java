package com.example.actividad2.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.actividad2.data.entity.Servicio;

import java.util.List;

@Dao
public interface ServicioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertar(Servicio servicio);

    @Query("SELECT * FROM servicios WHERE id = :id")
    LiveData<Servicio> obtenerPorId(int id);

    @Update
    void actualizar(Servicio servicio);

    @Query("UPDATE servicios SET eliminado = 1 WHERE id = :id") // eliminación lógica
    void eliminarLogico(int id);

    @Query("SELECT * FROM servicios WHERE cliente_id = :clienteId AND eliminado = 0 ORDER BY id DESC")
    LiveData<List<Servicio>> obtenerPorCliente(int clienteId);

    @Query("SELECT * FROM servicios WHERE eliminado = 0 ORDER BY id DESC")
    LiveData<List<Servicio>> obtenerTodos();
}