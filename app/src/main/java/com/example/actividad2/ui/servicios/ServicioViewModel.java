package com.example.actividad2.ui.servicios;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.actividad2.data.entity.Servicio;
import com.example.actividad2.data.repository.ServicioRepository;

import java.util.List;

public class ServicioViewModel extends AndroidViewModel { //clase intermediaria con el fragment y el repositorio

    private ServicioRepository repository;
    private LiveData<List<Servicio>> allServicios;

    public ServicioViewModel(@NonNull Application application){
        super(application);

        repository = new ServicioRepository(application);
        allServicios = repository.obtenerTodos();
    }

    public LiveData<List<Servicio>> getAllServicios (){
        return allServicios;
    }

    public void insert (Servicio servicio){
        repository.insertar(servicio);
    }

    public void update (Servicio servicio){
        repository.actualizar(servicio);
    }

    public void delete (Servicio servicio){
        repository.eliminar(servicio);
    }

}
