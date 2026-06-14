package com.example.actividad2.ui.clientes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.actividad2.data.entity.Cliente;
import com.example.actividad2.data.repository.ClienteRepository;

import java.util.List;

public class ClienteViewModel extends AndroidViewModel {
    private ClienteRepository repository;
    private LiveData<List<Cliente>> allClientes;

    //Constructor.
    public ClienteViewModel(@NonNull Application application) {
        super(application);
        repository = new ClienteRepository(application);
        allClientes = repository.obtenerTodos();
    }

    public LiveData<List<Cliente>> getAllClientes(){
        return allClientes;
    }

    public void insert(Cliente cliente) {
        repository.insertar(cliente);
    }

    public void update(Cliente cliente) {
        repository.actualizar(cliente);
    }

    public void desactivarCliente(int id) {
        repository.desactivarCliente(id);
    }

    public LiveData<List<Cliente>> buscarClientes(String nombre) {
        return repository.buscar(nombre);
    }
}
