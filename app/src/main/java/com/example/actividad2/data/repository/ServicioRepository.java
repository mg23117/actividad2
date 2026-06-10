package com.example.actividad2.data.repository;

import android.app.Application;

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
        AppDatabase.databaseWriteExecutor.execute(() -> servicioDao.insertar(servicio));)
    }

    public void eliminar(Servicio servicio) {
        AppDatabase.databaseWriteExecutor.execute(() -> servicioDao.eliminar(servicio));
    }

    public Future<Servicio> obtenerPorId(int id) {
        return AppDatabase.databaseWriteExecutor.submit(() -> servicioDao.obtenerPorId(id));
    }

    public Future<List<Servicio>> obtenerPorCliente(int clienteId) {
        return AppDatabase.databaseWriteExecutor.submit(() -> servicioDao.obtenerPorCliente(clienteId));
    }
}
}