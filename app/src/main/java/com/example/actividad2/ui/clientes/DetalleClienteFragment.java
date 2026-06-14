package com.example.actividad2.ui.clientes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.actividad2.R;

public class DetalleClienteFragment extends Fragment {

    private TextView tvNombre, tvTelefono, tvEmail, tvDireccion, tvMunicipio, tvNotas;
    private android.widget.ImageButton btnRegresar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detalle_cliente, container, false);

        // Inicializar vistas
        tvNombre = root.findViewById(R.id.tv_detalle_nombre);
        tvTelefono = root.findViewById(R.id.tv_detalle_telefono);
        tvEmail = root.findViewById(R.id.tv_detalle_email);
        tvDireccion = root.findViewById(R.id.tv_detalle_direccion);
        tvMunicipio = root.findViewById(R.id.tv_detalle_municipio);
        tvNotas = root.findViewById(R.id.tv_detalle_notas);

        btnRegresar = root.findViewById(R.id.btn_detalle_regresar);
        btnRegresar.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        // Recuperar los datos enviados desde la lista
        if (getArguments() != null) {
            tvNombre.setText(getArguments().getString("nombre", ""));
            tvTelefono.setText(getArguments().getString("telefono", ""));
            tvEmail.setText(getArguments().getString("email", ""));
            tvDireccion.setText(getArguments().getString("direccion", ""));
            tvMunicipio.setText(getArguments().getString("municipio", ""));
            tvNotas.setText(getArguments().getString("notes", ""));
        }

        return root;
    }
}
