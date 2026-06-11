package com.example.actividad2.ui.clientes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.actividad2.R;
import com.example.actividad2.data.entity.Cliente;

public class DialogAgregarCliente extends DialogFragment {

    private EditText etNombre, etTelefono, etEmail, etDireccion, etMunicipio, etNotas;
    private OnClienteGuardadoListener listener;

    public interface OnClienteGuardadoListener {
        void onClienteGuardado(Cliente cliente);
    }

    public void setOnClienteGuardadoListener(OnClienteGuardadoListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_cliente, null);

        etNombre = view.findViewById(R.id.et_nombre);
        etTelefono = view.findViewById(R.id.et_telefono);
        etEmail = view.findViewById(R.id.et_email);
        etDireccion = view.findViewById(R.id.et_direccion);
        etMunicipio = view.findViewById(R.id.et_municipio);
        etNotas = view.findViewById(R.id.et_notas);

        Button btnCancelar = view.findViewById(R.id.btn_cancelar);
        Button btnGuardar = view.findViewById(R.id.btn_guardar);

        btnCancelar.setOnClickListener(v -> dismiss());

        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String telefono = etTelefono.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String direccion = etDireccion.getText().toString().trim();
            String municipio = etMunicipio.getText().toString().trim();
            String notas = etNotas.getText().toString().trim();

            if (nombre.isEmpty()) {
                Toast.makeText(getContext(), "El nombre completo es obligatorio", Toast.LENGTH_SHORT).show();
                return;
            }

            // Instanciamos el modelo entidad.
            Cliente nuevoCliente = new Cliente(nombre, telefono, email, direccion, municipio, notas);
            if (listener != null) {
                listener.onClienteGuardado(nuevoCliente);
            }
            dismiss();
        });

        builder.setView(view);
        return builder.create();
    }
}
