
package com.example.actividad2.ui.home;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.actividad2.R;
import com.example.actividad2.ui.clientes.ClienteViewModel;
import com.example.actividad2.ui.servicios.ServicioViewModel;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment {

    private ClienteViewModel clienteViewModel;
    private ServicioViewModel servicioViewModel;
    private TextView tvCountClientes, tvCountServicios;

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, android.os.Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Vincular los TextViews del XML
        tvCountClientes = view.findViewById(R.id.tv_count_clientes);
        tvCountServicios = view.findViewById(R.id.tv_count_servicios);

        View cardClientes = view.findViewById(R.id.card_acceso_clientes);
        View cardServicios = view.findViewById(R.id.card_acceso_servicios);

        // Inicializar ViewModels
        clienteViewModel = new ViewModelProvider(this).get(ClienteViewModel.class);
        servicioViewModel = new ViewModelProvider(this).get(ServicioViewModel.class);

        // Observar Clientes
        clienteViewModel.getAllClientes().observe(getViewLifecycleOwner(), clientes -> {
            if (clientes != null) {
                tvCountClientes.setText(String.valueOf(clientes.size()));
            }
        });

        // Observar Servicios
        servicioViewModel.getAllServicios().observe(getViewLifecycleOwner(), servicios -> {
            if (servicios != null) {
                tvCountServicios.setText(String.valueOf(servicios.size()));
            }
        });

        BottomNavigationView bottomNav =
                requireActivity().findViewById(R.id.bottomNavigation);

        cardClientes.setOnClickListener(v ->
                bottomNav.setSelectedItemId(R.id.nav_clientes));

        cardServicios.setOnClickListener(v ->
                bottomNav.setSelectedItemId(R.id.nav_servicios));
    }
}