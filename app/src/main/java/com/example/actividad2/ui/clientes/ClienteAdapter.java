package com.example.actividad2.ui.clientes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        private TextView tvNombre, tvTelefono, tvEmail, tvMunicipio;
        private Button btnEditar;

        public ClienteHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.text_view_nombre);
            tvTelefono = itemView.findViewById(R.id.text_view_telefono);
            tvEmail = itemView.findViewById(R.id.text_view_email);
            tvMunicipio = itemView.findViewById(R.id.text_view_municipio);
            btnEditar = itemView.findViewById(R.id.btn_editar);

            btnEditar.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onEditClick(clientes.get(position));
                }
            });
        }
    }
}
