package com.example.actividad2.ui.clientes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actividad2.R;
import com.example.actividad2.data.entity.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClienteHolder> {
    private List<Cliente> clientes = new ArrayList<>();
    private ClienteAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(Cliente cliente);
        void onDeleteClick(Cliente cliente);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClienteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cliente, parent, false);
        return new ClienteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteHolder holder, int position) {
        Cliente currentCliente = clientes.get(position);
        holder.tvNombre.setText(currentCliente.getNombre());
        holder.tvTelefono.setText(currentCliente.getTelefono());
        holder.tvEmail.setText(currentCliente.getEmail());
        holder.tvMunicipio.setText(currentCliente.getMunicipio());

        holder.itemView.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putString("nombre", currentCliente.getNombre());
            args.putString("telefono", currentCliente.getTelefono());
            args.putString("email", currentCliente.getEmail());
            args.putString("direccion", currentCliente.getDireccion());
            args.putString("municipio", currentCliente.getMunicipio());
            args.putString("notes", currentCliente.getNotas());

            androidx.fragment.app.Fragment detalleFragment = new com.example.actividad2.ui.clientes.DetalleClienteFragment();
            detalleFragment.setArguments(args);

            if (v.getContext() instanceof androidx.appcompat.app.AppCompatActivity) {
                androidx.appcompat.app.AppCompatActivity activity = (androidx.appcompat.app.AppCompatActivity) v.getContext();
                int contenedorId = activity.getResources().getIdentifier("nav_host_fragment_activity_main", "id", activity.getPackageName());

                if (contenedorId == 0) {
                    contenedorId = activity.getResources().getIdentifier("fragment_container", "id", activity.getPackageName());
                }
                if (contenedorId != 0) {
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(contenedorId, detalleFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
        notifyDataSetChanged();
    }

    class ClienteHolder extends RecyclerView.ViewHolder {
        private final TextView tvNombre, tvTelefono, tvEmail, tvMunicipio;
        private final ImageButton btnEditar;
        private final ImageButton btnEliminar;

        public ClienteHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.text_view_nombre);
            tvTelefono = itemView.findViewById(R.id.text_view_telefono);
            tvEmail = itemView.findViewById(R.id.text_view_email);
            tvMunicipio = itemView.findViewById(R.id.text_view_municipio);
            btnEditar = itemView.findViewById(R.id.btn_editar);
            btnEliminar = itemView.findViewById(R.id.btn_eliminar);

            btnEditar.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onEditClick(clientes.get(position));
                }
            });

            btnEliminar.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION){
                    listener.onDeleteClick(clientes.get(position));
                }
            });
        }
    }
}
