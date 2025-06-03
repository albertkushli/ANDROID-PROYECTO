package dsa.upc.edu.listapp;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dsa.upc.edu.listapp.models.Objeto;
import dsa.upc.edu.listapp.utils.Constants;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.VH> {

    private static final String BASE_URL = Constants.BASE_URL;

    private List<Objeto> objetos = new ArrayList<>();

    public interface OnBuyClickListener {
        void onBuyClick(Objeto producto);
    }

    private OnBuyClickListener buyClickListener;

    public void setOnBuyClickListener(OnBuyClickListener listener) {
        this.buyClickListener = listener;
    }

    public void setData(List<Objeto> d) {
        objetos = d;
        notifyDataSetChanged();
    }

    public Objeto getProductAt(int pos) {
        return objetos.get(pos);
    }

    public void remove(int pos) {
        objetos.remove(pos);
        notifyItemRemoved(pos);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_product, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int pos) {
        Objeto producto = objetos.get(pos);
        holder.tvName.setText(producto.getNombre());
        holder.tvPrice.setText(String.format(Locale.getDefault(), "%d€", producto.getPrecio()));

        // Construir URL completa de la imagen
        String imageUrl = producto.getImagen();
        if (imageUrl == null || imageUrl.isEmpty()) {
            // Usa imagen por defecto o placeholder
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.img)
                    .into(holder.imgProduct);
        } else {
            String fullUrl = BASE_URL + imageUrl;
            Glide.with(holder.itemView.getContext())
                    .load(fullUrl)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.img)
                    .into(holder.imgProduct);
        }

        holder.btnBuy.setOnClickListener(v -> {
            if (buyClickListener != null) buyClickListener.onBuyClick(producto);
        });
    }

    @Override
    public int getItemCount() {
        return objetos.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        ImageView imgProduct;
        Button btnBuy;

        VH(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvProductName);
            tvPrice = v.findViewById(R.id.tvProductPrice);
            imgProduct = v.findViewById(R.id.imgProduct);
            btnBuy = v.findViewById(R.id.btnBuy);
        }
    }
}

