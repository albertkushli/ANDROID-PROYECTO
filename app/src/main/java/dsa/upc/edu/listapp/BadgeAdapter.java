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

            String avatarPath = insignia.getAvatar();

            if (avatarPath != null && !avatarPath.isEmpty()) {
                // Extraemos el nombre del archivo sin ruta ni extensión
                String fileName = avatarPath.substring(avatarPath.lastIndexOf("/") + 1);
                if (fileName.contains(".")) {
                    fileName = fileName.substring(0, fileName.lastIndexOf("."));
                }

                // Convertimos el nombre a minúsculas, tal como se guarda en drawable
                String resourceName = fileName.toLowerCase();

                // Buscamos el recurso drawable
                int resourceId = itemView.getContext().getResources().getIdentifier(
                        resourceName,
                        "drawable",
                        itemView.getContext().getPackageName()
                );

                if (resourceId != 0) {
                    ivBadge.setImageResource(resourceId);
                } else {
                    ivBadge.setImageResource(android.R.drawable.star_on); // Imagen por defecto
                }
            } else {
                ivBadge.setImageResource(android.R.drawable.star_on); // Imagen por defecto si no hay avatar
            }
        }
    }
}
