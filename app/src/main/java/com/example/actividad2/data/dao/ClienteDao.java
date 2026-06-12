package com.example.actividad2.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;

import androidx.room.Query;
import androidx.room.Update;

import com.example.actividad2.data.entity.Cliente;

import java.util.List;

@Dao
public interface ClienteDao {
    @Insert
    void insertar(Cliente cliente);

    @Update
    void actualizar(Cliente cliente);

    @Query("SELECT * FROM clientes")
    LiveData<List<Cliente>> obtenerTodos();

    @Query("SELECT * FROM clientes WHERE nombre LIKE '%' || :busqueda || '%'")
    LiveData<List<Cliente>> buscar(String busqueda);

    @Query("SELECT * FROM clientes WHERE id = :id")
    Cliente obtenerPorId(int id);

    @Query("SELECT * FROM clientes WHERE nombre = :nombre")
    Cliente obtenerPorNombre(String nombre);
}