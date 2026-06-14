package com.example.actividad2.ui.servicios;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.actividad2.R;
import com.example.actividad2.data.entity.Cliente;
import com.example.actividad2.data.entity.Servicio;
import com.example.actividad2.data.entity.enums.EstadoServicio;
import com.example.actividad2.data.entity.enums.TipoServicio;

import java.util.ArrayList;
import java.util.List;

public class DialogAgregarServicio extends DialogFragment {

    private Spinner spinnerCliente, spinnerTipoServicio, spinnerEstado;
    private EditText etDescripcionServicio, etCostoManoObra, etCostoMateriales;
    private OnServicioGuardadoListener listener;

    private List<Cliente> clientes;
    private Servicio servicioEditar;

    public interface OnServicioGuardadoListener {
        void onServicioGuardado (Servicio servicio);
    }

    public void setOnServicioGuardadoListener (OnServicioGuardadoListener listener){
        this.listener = listener;
    }

    public void setClientes(List<Cliente> clientes) { // setter para recibir clientes
        this.clientes = clientes;
    }
    public void setServicioEditar(Servicio servicioEditar) {
        this.servicioEditar = servicioEditar;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog (@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_servicio, null);

        spinnerCliente = view.findViewById(R.id.spinner_cliente);

        spinnerTipoServicio = view.findViewById(R.id.spinner_tipo_servicio);

        spinnerEstado = view.findViewById(R.id.spinner_estado);

        etDescripcionServicio = view.findViewById(R.id.et_descripcion_servicio);

        etCostoManoObra = view.findViewById(R.id.et_costo_mano_obra);

        etCostoMateriales = view.findViewById(R.id.et_costo_materiales);

        Button btnCancelar = view.findViewById(R.id.btn_cancelar);

        Button btnGuardar = view.findViewById(R.id.btn_guardar);

        // llenar Spinner TipoServicio
        ArrayAdapter<TipoServicio> tipoAdapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_spinner_item, TipoServicio.values()
        );

        tipoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerTipoServicio.setAdapter(tipoAdapter);

        // llenar Spinner Estado
        ArrayAdapter<EstadoServicio> estadoAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item, EstadoServicio.values()
        );

        estadoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerEstado.setAdapter(estadoAdapter);

        // llenar Spinner Cliente
        if (clientes != null) {
            List<String> nombresClientes = new ArrayList<>();

            for (Cliente cliente : clientes){
                nombresClientes.add(cliente.getNombre());
            }

            ArrayAdapter<String> clienteAdapter = new ArrayAdapter<>(
                    getContext(),
                    android.R.layout.simple_spinner_item, nombresClientes
            );

            clienteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnerCliente.setAdapter(clienteAdapter);

            // Si estamos editando, cargar datos existentes
            if (servicioEditar != null) {

                etDescripcionServicio.setText(servicioEditar.getDescripcionServicio());
                etCostoManoObra.setText(String.valueOf(servicioEditar.getCostoManoObra()));
                etCostoMateriales.setText(String.valueOf(servicioEditar.getCostoMateriales()));
                spinnerTipoServicio.setSelection(servicioEditar.getTipoServicio().ordinal());
                spinnerEstado.setSelection(servicioEditar.getEstado().ordinal());

                // seleccionar cliente correcto
                for (int i = 0; i < clientes.size(); i++) {

                    if (clientes.get(i).getId() == servicioEditar.getClienteId()) {
                        spinnerCliente.setSelection(i);
                        break;
                    }
                }

                btnGuardar.setText("Actualizar");
            }
        }

        // boton Cancelar
        btnCancelar.setOnClickListener(v -> dismiss());

        // boton Guardar
        btnGuardar.setOnClickListener(v -> {
            String descripcion = etDescripcionServicio.getText().toString().trim(); // leer descripción
            if (descripcion.isEmpty()){
                Toast.makeText(getContext(), "La descripción es obligatoria", Toast.LENGTH_SHORT).show(); // validar descripción
                return;
            }

            // leer costos
            double costoManoObra = 0;
            double costoMateriales = 0;

            try {
                costoManoObra = Double.parseDouble(etCostoManoObra.getText().toString().trim());
                costoMateriales = Double.parseDouble(etCostoMateriales.getText().toString().trim());
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Ingrese costos válidos", Toast.LENGTH_SHORT).show();
                return;
            }

            // obtener cliente
            Cliente clienteSeleccionado = clientes.get(spinnerCliente.getSelectedItemPosition());

            // Obtener enums
            TipoServicio tipoServicio = (TipoServicio) spinnerTipoServicio.getSelectedItem();

            EstadoServicio estado = (EstadoServicio) spinnerEstado.getSelectedItem();


            Servicio servicioGuardar;

            if (servicioEditar == null) {

                // NUEVO SERVICIO
                servicioGuardar = new Servicio(clienteSeleccionado.getId(), tipoServicio, estado, descripcion, costoManoObra, costoMateriales);

            } else {

                // ACTUALIZAR SERVICIO EXISTENTE
                servicioEditar.setClienteId(clienteSeleccionado.getId());
                servicioEditar.setTipoServicio(tipoServicio);
                servicioEditar.setEstado(estado);
                servicioEditar.setDescripcionServicio(descripcion);
                servicioEditar.setCostoManoObra(costoManoObra);
                servicioEditar.setCostoMateriales(costoMateriales);
                servicioGuardar = servicioEditar;
            }

            // Notificar listener
            if (listener != null) {
                listener.onServicioGuardado(servicioGuardar);
            }

            dismiss(); // cerrar

        });

        builder.setView(view);
        return builder.create();
    }

}
