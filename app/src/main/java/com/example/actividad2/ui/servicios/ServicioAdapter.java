package com.example.actividad2.ui.servicios;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actividad2.R;
import com.example.actividad2.data.entity.Cliente;
import com.example.actividad2.data.entity.Servicio;

import java.util.ArrayList;
import java.util.List;

public class ServicioAdapter extends RecyclerView.Adapter<ServicioAdapter.ServicioHolder> {

    private List<Servicio> servicios = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick (Servicio servicio);
        void onEditClick (Servicio servicio);
        void onDeleteClick (Servicio servicio);
    }

    private OnItemClickListener listener; // listener

    // setter del listener
    public void setOnItemClickListener (OnItemClickListener listener) {
        this.listener = listener;
    }

    // Infla item_servicio.xml, o sea convierte XML en Objeto View
    @NonNull
    @Override
    public ServicioHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_servicio, parent, false);


        return new ServicioHolder(itemView);
    }

    // información de cada tarjeta
    @Override
    public void onBindViewHolder(@NonNull ServicioHolder holder, int position) {

        Servicio servicioActual = servicios.get(position);

        double total = servicioActual.getCostoManoObra() + servicioActual.getCostoMateriales();

        holder.tvTipoServicio.setText(
                "Tipo: " + servicioActual.getTipoServicio().name()
        );

        holder.tvDescripcionServicio.setText(
                "Descripción: " + servicioActual.getDescripcionServicio()
        );

        holder.tvEstadoServicio.setText(
                "Estado: " + servicioActual.getEstado().name()
        );

        // Temporal
        holder.tvCliente.setText(
                "Cliente: " + obtenerNombreCliente(servicioActual.getClienteId())
        );

        holder.tvTotal.setText("$ " + total);
    }

    // indicarle a recyclerView cuantas filas debe dibujar
    @Override
    public int getItemCount() {
        return servicios.size();
    }

    // actualizar la lista
    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
        notifyDataSetChanged(); // le dice de dibujar otra vez
    }

    public void setClientes(List<Cliente> clientes){
        this.clientes = clientes;
    }

    private String obtenerNombreCliente(int clienteId){

        for(Cliente cliente : clientes){
            if(cliente.getId() == clienteId){
                return cliente.getNombre();
            }
        }

        return "Cliente no encontrado";
    }

    class ServicioHolder extends RecyclerView.ViewHolder {
        private TextView tvCliente, tvTipoServicio, tvDescripcionServicio, tvEstadoServicio, tvTotal;;
        //private Button btnEditar, btnEliminar;
        private ImageButton btnMenu;
        private CardView cardView;

        // constructor
        public ServicioHolder (@NonNull View itemView){
            super(itemView);
            tvCliente = itemView.findViewById(R.id.text_view_cliente);
            tvTipoServicio = itemView.findViewById(R.id.text_view_tipo_servicio);
            tvDescripcionServicio = itemView.findViewById(R.id.text_view_descripcion_servicio);
            tvEstadoServicio = itemView.findViewById(R.id.text_view_estado_servicio);
            tvTotal = itemView.findViewById(R.id.text_view_total);
            btnMenu = itemView.findViewById(R.id.btn_menu_servicio);
            cardView = itemView.findViewById(R.id.card_servicio);

            //evento detalle
            cardView.setOnClickListener(v -> {

                int position = getBindingAdapterPosition();

                if(listener != null &&
                        position != RecyclerView.NO_POSITION){

                    listener.onItemClick(
                            servicios.get(position)
                    );
                }
            });

            // Menu desplegable para las opciones editar y eliminar
            btnMenu.setOnClickListener(v -> {

                int position = getBindingAdapterPosition();

                if(position == RecyclerView.NO_POSITION){
                    return;
                }

                PopupMenu popupMenu = new PopupMenu(itemView.getContext(), btnMenu);

                popupMenu.inflate(R.menu.menu_servicio);

                popupMenu.setOnMenuItemClickListener(item -> {
                    int itemId = item.getItemId();

                    if(itemId == R.id.action_editar){ // evento editar

                        if(listener != null){
                            listener.onEditClick(servicios.get(position));
                        }

                        return true;
                    }

                    if(itemId == R.id.action_eliminar){ // evento eliminar

                        if(listener != null){
                            listener.onDeleteClick(
                                    servicios.get(position)
                            );
                        }

                        return true;
                    }

                    return false;
                });

                popupMenu.show();
            });
        }


    }
}
