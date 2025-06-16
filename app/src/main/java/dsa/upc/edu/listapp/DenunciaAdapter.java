package dsa.upc.edu.listapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import dsa.upc.edu.listapp.models.Denuncia;

public class DenunciaAdapter extends RecyclerView.Adapter<DenunciaAdapter.DenunciaViewHolder> {
    private List<Denuncia> denuncias = new ArrayList<>();

    public void setDenuncias(List<Denuncia> denuncias) {
        this.denuncias = denuncias;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DenunciaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_denuncia, parent, false);
        return new DenunciaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DenunciaViewHolder holder, int position) {
        Denuncia denuncia = denuncias.get(position);
        holder.bind(denuncia);
    }

    @Override
    public int getItemCount() {
        return denuncias.size();
    }

    static class DenunciaViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitulo, tvMensaje, tvRemitente, tvFecha;

        public DenunciaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvMensaje = itemView.findViewById(R.id.tvMensaje);
            tvRemitente = itemView.findViewById(R.id.tvRemitente);
            tvFecha = itemView.findViewById(R.id.tvFecha);
        }

        public void bind(Denuncia denuncia) {
            tvTitulo.setText(denuncia.getTitulo());
            tvMensaje.setText(denuncia.getMensaje());

            // Mostrar el usuario que hizo la denuncia
            String remitente = denuncia.getId_usuario();
            if (remitente != null && !remitente.isEmpty()) {
                tvRemitente.setText("Por: " + remitente);
            } else {
                tvRemitente.setText("Por: Anónimo");
            }

            // Mostrar la fecha
            if (denuncia.getFecha() != null) {
                tvFecha.setText(denuncia.getFecha());
            } else {
                tvFecha.setText("Fecha no disponible");
            }
        }

        private String formatearFecha(String fechaOriginal) {
            if (fechaOriginal == null || fechaOriginal.isEmpty()) {
                return "Fecha no disponible";
            }

            try {
                // Si la fecha viene del backend en formato ISO
                SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                Date fecha = formatoEntrada.parse(fechaOriginal);

                // Formato de salida más legible
                SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                return formatoSalida.format(fecha);
            } catch (ParseException e) {
                // Si falla el parseo, intentar otro formato o devolver la fecha original
                return fechaOriginal;
            }
        }
    }
}