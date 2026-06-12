
package com.example.actividad2.ui.home;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.actividad2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, android.os.Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View cardClientes = view.findViewById(R.id.card_acceso_clientes);
        View cardServicios = view.findViewById(R.id.card_acceso_servicios);

        BottomNavigationView bottomNav =
                requireActivity().findViewById(R.id.bottomNavigation);

        cardClientes.setOnClickListener(v ->
                bottomNav.setSelectedItemId(R.id.nav_clientes));

        cardServicios.setOnClickListener(v ->
                bottomNav.setSelectedItemId(R.id.nav_servicios));
    }
}