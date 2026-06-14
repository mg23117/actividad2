package com.example.actividad2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.actividad2.ui.clientes.ClienteFragment;
import com.example.actividad2.ui.home.HomeFragment;
import com.example.actividad2.ui.servicios.ServicioFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {



    private BottomNavigationView bottomNavigation;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottomNavigation);
        toolbar = findViewById(R.id.toolbar);

        // Mostrar Home al iniciar
        if (savedInstanceState == null) {
            toolbar.setTitle("Dashboard");
            loadFragment(new HomeFragment());
        }

        bottomNavigation.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                toolbar.setTitle("Dashboard");
                loadFragment(new HomeFragment());
                return true;
            }

            if (id == R.id.nav_clientes) {
                toolbar.setTitle("Clientes");
                loadFragment(new ClienteFragment());
                return true;
            }

            if (id == R.id.nav_servicios) {
                toolbar.setTitle("Servicios");
                loadFragment(new ServicioFragment());
                return true;
            }

            return false;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}