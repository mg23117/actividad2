package com.example.actividad2.ui.clientes;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actividad2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ClienteFragment extends Fragment {

    private ClienteViewModel clienteViewModel;
    private ClienteAdapter adapter;
    private TextView tvNoClientes;
    private EditText etBuscar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cliente, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_clientes);
        tvNoClientes = view.findViewById(R.id.tv_no_clientes); // Label de la pág 2 de la guía
        etBuscar = view.findViewById(R.id.et_buscar_cliente);
        FloatingActionButton fab = view.findViewById(R.id.fab_agregar_cliente);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        adapter = new ClienteAdapter();
        recyclerView.setAdapter(adapter);

        clienteViewModel = new ViewModelProvider(this).get(ClienteViewModel.class);

        // Listar y observar clientes en tiempo real
        clienteViewModel.getAllClientes().observe(getViewLifecycleOwner(), clientes -> {
            if (clientes == null || clientes.isEmpty()) {
                tvNoClientes.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                tvNoClientes.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                adapter.setClientes(clientes);
            }
        });

        // Evento para Agregar Cliente (+)
        fab.setOnClickListener(v -> {
            DialogAgregarCliente dialog = new DialogAgregarCliente();
            dialog.setOnClienteGuardadoListener(nuevoCliente -> {
                clienteViewModel.insert(nuevoCliente);
            });
            dialog.show(getParentFragmentManager(), "DialogAgregarCliente");
        });

        // Evento para Editar un Cliente de la lista
        adapter.setOnItemClickListener(cliente -> {
            DialogEditarCliente dialog = new DialogEditarCliente();
            dialog.setCliente(cliente);
            dialog.setOnClienteActualizadoListener(clienteActualizado -> {
                clienteViewModel.update(clienteActualizado);
            });
            dialog.show(getParentFragmentManager(), "DialogEditarCliente");
        });

        // BONUS: Barra de búsqueda funcional por nombre
        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarClientes(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private void filtrarClientes(String texto) {
        if (texto.isEmpty()) {
            // Si el buscador está vacío, volvemos a mostrar la lista completa
            clienteViewModel.getAllClientes().observe(getViewLifecycleOwner(), clientes -> {
                adapter.setClientes(clientes);
            });
        } else {
            // Consultamos pasándole el parámetro con comodines SQL (%)
            clienteViewModel.buscarClientes("%" + texto + "%").observe(getViewLifecycleOwner(), clientes -> {
                adapter.setClientes(clientes);
            });
        }
    }
}
