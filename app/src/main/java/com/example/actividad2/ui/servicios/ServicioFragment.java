package com.example.actividad2.ui.servicios;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actividad2.R;
import com.example.actividad2.data.entity.Cliente;
import com.example.actividad2.data.entity.Servicio;
import com.example.actividad2.data.entity.enums.EstadoServicio;
import com.example.actividad2.ui.clientes.ClienteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ServicioFragment extends Fragment {

    private ServicioViewModel servicioViewModel; // tiene los metodos crud (guarda/elimina/actualiza servicios)
    private ClienteViewModel clienteViewModel;  // obtiene clientes
    private ServicioAdapter adapter; // conecta RecyclerView con la Lista de Servicios
    private TextView tvNoServicios; // para mostrar mensaje cuando la base de datos esté vacía

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_servicio, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_servicios);
        tvNoServicios = view.findViewById(R.id.tv_no_servicios);
        FloatingActionButton fab = view.findViewById(R.id.fab_agregar_servicio);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setHasFixedSize(true); // Los elementos tendrán tamaños similares.

        adapter = new ServicioAdapter();
        recyclerView.setAdapter(adapter);

        servicioViewModel = new ViewModelProvider(this).get(ServicioViewModel.class); // inicializar ServicioViewModel
        clienteViewModel = new ViewModelProvider(this).get(ClienteViewModel.class); // inicializar clienteViewModel

        clienteViewModel.getAllClientes().observe(getViewLifecycleOwner(), clientes -> {
            adapter.setClientes(clientes);
        });

        // listar y observar servicios en tiempo real
        servicioViewModel.getAllServicios()
                .observe(getViewLifecycleOwner(), servicios -> {

                    if (servicios == null || servicios.isEmpty()) {

                        tvNoServicios.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);

                    } else {

                        tvNoServicios.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                        adapter.setServicios(servicios); // actualizar la lista
                    }
                });

        // evento floating action button
        fab.setOnClickListener(v -> {

            // Verificar existencia de clientes antes de crear un servicio
            clienteViewModel.getAllClientes().observe(getViewLifecycleOwner(), clientes -> {
                if (clientes == null || clientes.isEmpty()) {
                    Toast.makeText(getContext(), "Debe registrar al menos un cliente antes de crear servicios", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Abrir DialogAgregarServicio
                DialogAgregarServicio dialog = new DialogAgregarServicio();
                dialog.setClientes(clientes);
                dialog.setOnServicioGuardadoListener(servicio -> {
                    servicioViewModel.insert(servicio); // guarda en Room
                });

                dialog.show(getParentFragmentManager(), "DialogAgregarServicio");
            });

        });

        // Eventos: detalles, editar y eliminar
        adapter.setOnItemClickListener(new ServicioAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Servicio servicio) { // click en tarjeta para mostrar full detalles de un servicio

                clienteViewModel.getAllClientes().observe(getViewLifecycleOwner(), clientes -> {

                    String nombreCliente = "Cliente no encontrado";

                            for (var cliente : clientes) {
                                if (cliente.getId() == servicio.getClienteId()) {
                                    nombreCliente = cliente.getNombre();
                                    break;
                                }
                            }

                            DialogDetalleServicio dialog = new DialogDetalleServicio();

                            dialog.setServicio(servicio);

                            dialog.setNombreCliente(nombreCliente);

                            dialog.show(
                                    getParentFragmentManager(),
                                    "DialogDetalleServicio"
                            );
                        });
            }

            @Override
            public void onEditClick(Servicio servicio) { // click en editar

                clienteViewModel.getAllClientes().observe(getViewLifecycleOwner(), clientes -> {

                    DialogAgregarServicio dialog = new DialogAgregarServicio();

                    dialog.setClientes(clientes);

                    dialog.setServicioEditar(servicio);

                    dialog.setOnServicioGuardadoListener(servicioActualizado -> {servicioViewModel.update(servicioActualizado);
                        Toast.makeText(getContext(), "Servicio actualizado correctamente", Toast.LENGTH_SHORT).show();
                    });

                    dialog.show(getParentFragmentManager(), "DialogEditarServicio");
                });
            }

            @Override
            public void onDeleteClick(Servicio servicio) { // click en eliminar

                // No permitir eliminar servicios en proceso
                if (servicio.getEstado() == EstadoServicio.EN_PROCESO) {

                    Toast.makeText(
                            getContext(),
                            "No se puede eliminar un servicio que está en proceso",
                            Toast.LENGTH_SHORT
                    ).show();

                    return;
                }

                clienteViewModel.getAllClientes().observe(getViewLifecycleOwner(), clientes -> {

                    String nombreCliente = "Cliente no encontrado";

                    for (Cliente cliente : clientes) {
                        if (cliente.getId() == servicio.getClienteId()) {
                            nombreCliente = cliente.getNombre();
                            break;
                        }
                    }

                new AlertDialog.Builder(requireContext())
                        .setTitle("Eliminar servicio")
                        .setMessage(
                                "¿Está seguro de eliminar este servicio?\n\n" +
                                        "Cliente: " + nombreCliente + "\n" +
                                        "Tipo: " + servicio.getTipoServicio() + "\n" +
                                        "Descripción: " + servicio.getDescripcionServicio() + "\n" +
                                        "Estado: " + servicio.getEstado()
                        )
                        .setNegativeButton("Cancelar", null)
                        .setPositiveButton("Eliminar", (dialog, which) -> {
                            servicioViewModel.delete(servicio);
                            Toast.makeText(getContext(), "Servicio eliminado", Toast.LENGTH_SHORT).show();
                        })
                        .show();
                });
            }
        });

        return view;
    }
}
