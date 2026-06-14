package com.example.actividad2.ui.servicios;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.actividad2.R;
import com.example.actividad2.data.entity.Servicio;

public class DialogDetalleServicio extends DialogFragment {

    private Servicio servicio;
    private String nombreCliente;

    public void setServicio(Servicio servicio){
        this.servicio = servicio;
    }

    public void setNombreCliente(String nombreCliente){
        this.nombreCliente = nombreCliente;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_detalle_servicio, null);
        TextView tvCliente = view.findViewById(R.id.tv_detalle_cliente);
        TextView tvTipo = view.findViewById(R.id.tv_detalle_tipo);
        TextView tvEstado = view.findViewById(R.id.tv_detalle_estado);
        TextView tvDescripcion = view.findViewById(R.id.tv_detalle_descripcion);
        TextView tvManoObra = view.findViewById(R.id.tv_detalle_mano_obra);
        TextView tvMateriales = view.findViewById(R.id.tv_detalle_materiales);
        TextView tvTotal = view.findViewById(R.id.tv_detalle_total);
        Button btnCerrar = view.findViewById(R.id.btn_cerrar);

        tvTipo.setText("Tipo: " + servicio.getTipoServicio().name());
        tvEstado.setText("Estado: " + servicio.getEstado().name());
        tvDescripcion.setText("Descripción: " + servicio.getDescripcionServicio());
        tvManoObra.setText("Mano de obra: $" + servicio.getCostoManoObra());
        tvMateriales.setText("Materiales: $" + servicio.getCostoMateriales());

        double total = servicio.getCostoManoObra() + servicio.getCostoMateriales();
        tvTotal.setText("Total: $" + total);

        tvCliente.setText("Cliente: " + nombreCliente);

        btnCerrar.setOnClickListener(v -> dismiss());

        builder.setView(view);

        return builder.create();
    }
}