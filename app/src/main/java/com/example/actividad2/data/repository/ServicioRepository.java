package com.example.actividad2.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.actividad2.data.dao.ServicioDao;
import com.example.actividad2.data.database.AppDatabase;
import com.example.actividad2.data.entity.Servicio;

import java.util.List;
import java.util.concurrent.Future;

public class ServicioRepository {
    private ServicioDao servicioDao;

    public ServicioRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        this.servicioDao = appDatabase.servicioDao();
    }

    public void insertar(Servicio servicio) {
        AppDatabase.databaseWriteExecutor.execute(() -> servicioDao.insertar(servicio));
    }

    public void actualizar(Servicio servicio) {
        AppDatabase.databaseWriteExecutor.execute(() -> servicioDao.actualizar(servicio));
    }

    public void eliminar(Servicio servicio) {
        AppDatabase.databaseWriteExecutor.execute(() -> servicioDao.eliminarLogico(servicio.getId()));
    }

    public LiveData<Servicio> obtenerPorId (int id) {
        return servicioDao.obtenerPorId(id);
    }

    public LiveData<List<Servicio>> obtenerPorCliente (int clienteId) {
        return servicioDao.obtenerPorCliente(clienteId);
    }

    public LiveData<List<Servicio>> obtenerTodos() {
        return servicioDao.obtenerTodos();
    }
}