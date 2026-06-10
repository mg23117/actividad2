package com.example.actividad2.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;

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
    List<Cliente> obtenerTodos();

    @Query("SELECT * FROM clientes WHERE nombre LIKE '%' || :busqueda || '%'")
    List<Cliente> buscar(String busqueda);

    @Query("SELECT * FROM clientes WHERE id = :id")
    Cliente obtenerPorId(int id);

    @Query("SELECT * FROM clientes WHERE nombre = :nombre")
    Cliente obtenerPorNombre(String nombre);

    @Query("SELECT * FROM clientes WHERE nombre = :nombre AND apellido = :apellido")
    Cliente obtenerPorNombreYApellido(String nombre, String apellido);
}