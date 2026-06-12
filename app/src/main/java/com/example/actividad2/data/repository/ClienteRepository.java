package com.example.actividad2.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

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

    public LiveData<List<Cliente>> obtenerTodos() {
        return clienteDao.obtenerTodos();
    }

    public LiveData<List<Cliente>> buscar(String busqueda) {
        return clienteDao.buscar(busqueda);
    }

    public Future<Cliente> obtenerPorId(int id) {
        return AppDatabase.databaseWriteExecutor.submit(() -> clienteDao.obtenerPorId(id));
    }

    public Future<Cliente> obtenerPorNombre(String nombre) {
        return AppDatabase.databaseWriteExecutor.submit(() -> clienteDao.obtenerPorNombre(nombre));
    }
}