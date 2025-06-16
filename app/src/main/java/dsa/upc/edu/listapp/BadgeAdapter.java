package dsa.upc.edu.listapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dsa.upc.edu.listapp.models.Insignia;

public class BadgeAdapter extends RecyclerView.Adapter<BadgeAdapter.BadgeViewHolder> {
    private List<Insignia> insignias = new ArrayList<>();

    public void setInsignias(List<Insignia> insignias) {
        this.insignias = insignias;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BadgeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_badge, parent, false);
        return new BadgeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BadgeViewHolder holder, int position) {
        Insignia insignia = insignias.get(position);
        holder.bind(insignia);
    }

    @Override
    public int getItemCount() {
        return insignias.size();
    }

    static class BadgeViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivBadge;
        private TextView tvBadgeName;

        public BadgeViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBadge = itemView.findViewById(R.id.ivBadge);
            tvBadgeName = itemView.findViewById(R.id.tvBadgeName);
        }

        public void bind(Insignia insignia) {
            tvBadgeName.setText(insignia.getNombre());

            // Cargar imagen de la insignia
            String avatarPath = insignia.getAvatar();

            if (avatarPath != null && !avatarPath.isEmpty()) {
                // La ruta viene como "img/insigniaEspada.png"
                // Extraemos solo el nombre del archivo sin extensión
                String fileName = avatarPath;
                if (avatarPath.contains("/")) {
                    fileName = avatarPath.substring(avatarPath.lastIndexOf("/") + 1);
                }
                if (fileName.contains(".")) {
                    fileName = fileName.substring(0, fileName.lastIndexOf("."));
                }

                // Convertir a nombre de recurso drawable
                // insigniaEspada -> insignia_espada
                String resourceName = fileName
                        .replaceAll("([a-z])([A-Z])", "$1_$2") // CamelCase to snake_case
                        .toLowerCase();

                // Buscar el recurso en drawable
                int resourceId = itemView.getContext().getResources().getIdentifier(
                        resourceName,
                        "drawable",
                        itemView.getContext().getPackageName()
                );

                if (resourceId != 0) {
                    ivBadge.setImageResource(resourceId);
                } else {
                    // Si no encuentra el recurso, intenta con el nombre original
                    resourceId = itemView.getContext().getResources().getIdentifier(
                            fileName.toLowerCase(),
                            "drawable",
                            itemView.getContext().getPackageName()
                    );

                    if (resourceId != 0) {
                        ivBadge.setImageResource(resourceId);
                    } else {
                        // Imagen por defecto si no se encuentra
                        ivBadge.setImageResource(android.R.drawable.star_on);
                    }
                }
            } else {
                // Sin imagen especificada
                ivBadge.setImageResource(android.R.drawable.star_on);
            }
        }
    }
}