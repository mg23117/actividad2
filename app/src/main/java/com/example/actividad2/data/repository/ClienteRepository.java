package com.example.actividad2.data.repository;

import android.app.Application;

import com.example.actividad2.data.dao.ClienteDao;
import com.example.actividad2.data.database.AppDatabase;
import com.example.actividad2.data.entity.Cliente;

import java.util.List;
import java.util.concurrent.Future;

public class ClienteRepository{
    private ClienteDao clienteDao;

    public ClienteRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        this.clienteDao = appDatabase.clienteDao();
    }

    public void insertar(Cliente cliente) {
        AppDatabase.databaseWriteExecutor.execute(() -> clienteDao.insertar(cliente));
    }

    public void actualizar(Cliente cliente) {
        AppDatabase.databaseWriteExecutor.execute(() -> clienteDao.actualizar(cliente));
    }

    public Future<List<Cliente>> obtenerTodos() {
        return AppDatabase.databaseWriteExecutor.submit(() -> clienteDao.obtenerTodos());
    }

    public Future<List<Cliente>> buscar(String busqueda) {
        return AppDatabase.databaseWriteExecutor.submit(() -> clienteDao.buscar(busqueda));
    }

    public Future<Cliente> obtenerPorId(int id) {
        return AppDatabase.databaseWriteExecutor.submit(() -> clienteDao.obtenerPorId(id));
    }

    public Future<Cliente> obtenerPorNombre(String nombre) {
        return AppDatabase.databaseWriteExecutor.submit(() -> clienteDao.obtenerPorNombre(nombre));
    }

    public Future<Cliente> obtenerPorNombreYApellido(String nombre, String apellido) {
        return AppDatabase.databaseWriteExecutor.submit(() -> clienteDao.obtenerPorNombreYApellido(nombre, apellido));
    }

}