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

public class DialogEditarCliente extends DialogFragment {

    private EditText etNombre, etTelefono, etEmail, etDireccion, etMunicipio, etNotas;
    private Cliente clienteAEditar;
    private OnClienteActualizadoListener listener;

    public interface OnClienteActualizadoListener {
        void onClienteActualizado(Cliente cliente);
    }

    public void setOnClienteActualizadoListener(OnClienteActualizadoListener listener) {
        this.listener = listener;
    }

    public void setCliente(Cliente cliente) {
        this.clienteAEditar = cliente;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_editar_cliente, null);

        etNombre = view.findViewById(R.id.et_nombre_editar);
        etTelefono = view.findViewById(R.id.et_telefono_editar);
        etEmail = view.findViewById(R.id.et_email_editar);
        etDireccion = view.findViewById(R.id.et_direccion_editar);
        etMunicipio = view.findViewById(R.id.et_municipio_editar);
        etNotas = view.findViewById(R.id.et_notas_editar);

        // Cargamos los datos actuales del cliente en la vista de edición
        if (clienteAEditar != null) {
            etNombre.setText(clienteAEditar.getNombre());
            etTelefono.setText(clienteAEditar.getTelefono());
            etEmail.setText(clienteAEditar.getEmail());
            etDireccion.setText(clienteAEditar.getDireccion());
            etMunicipio.setText(clienteAEditar.getMunicipio());
            etNotas.setText(clienteAEditar.getNotas());
        }

        Button btnCancelar = view.findViewById(R.id.btn_cancelar_editar);
        Button btnGuardar = view.findViewById(R.id.btn_guardar_editar);

        btnCancelar.setOnClickListener(v -> dismiss());

        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String telefono = etTelefono.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String direccion = etDireccion.getText().toString().trim();
            String municipio = etMunicipio.getText().toString().trim();
            String notas = etNotas.getText().toString().trim();

            if (nombre.isEmpty()) {
                Toast.makeText(getContext(), "El nombre es obligatorio", Toast.LENGTH_SHORT).show();
                return;
            }

            clienteAEditar.setNombre(nombre);
            clienteAEditar.setTelefono(telefono);
            clienteAEditar.setEmail(email);
            clienteAEditar.setDireccion(direccion);
            clienteAEditar.setMunicipio(municipio);
            clienteAEditar.setNotas(notas);

            if (listener != null) {
                listener.onClienteActualizado(clienteAEditar);
            }
            dismiss();
        });

        builder.setView(view);
        return builder.create();
    }
}
