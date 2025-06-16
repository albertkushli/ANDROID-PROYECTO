package dsa.upc.edu.listapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dsa.upc.edu.listapp.models.Objeto;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.VH> {
    private List<Objeto> objetos = new ArrayList<>();

    public interface OnRemoveClick {
        void onRemove(Objeto producto, int pos);
    }
    private OnRemoveClick removeListener;

    public void setOnRemoveClickListener(OnRemoveClick l) {
        this.removeListener = l;
    }

    public void setData(List<Objeto> d) {
        objetos = d;
        notifyDataSetChanged();
    }
    public List<Objeto> getData() {
        return objetos;
    }
    public void remove(int pos) {
        objetos.remove(pos);
        notifyItemRemoved(pos);
    }
    public void clear() {
        objetos.clear();
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_carrito, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        Objeto producto = objetos.get(position);
        holder.tvName.setText(producto.getNombre());
        holder.tvPrice.setText(String.format(Locale.getDefault(), "%d€", producto.getPrecio()));
        holder.btnRemove.setOnClickListener(v -> {
            if (removeListener != null) removeListener.onRemove(producto, position);
        });
    }

    @Override
    public int getItemCount() {
        return objetos.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        ImageButton btnRemove;
        VH(View v){
            super(v);
            tvName    = v.findViewById(R.id.tvCartProductName);
            tvPrice   = v.findViewById(R.id.tvCartProductPrice);
            btnRemove = v.findViewById(R.id.btnRemove);
        }
    }
}

