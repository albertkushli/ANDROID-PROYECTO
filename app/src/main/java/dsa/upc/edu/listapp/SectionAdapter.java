package dsa.upc.edu.listapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dsa.upc.edu.listapp.models.CategoriaObjeto;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.VH> {
    private List<CategoriaObjeto> sections = new ArrayList<>();
    private OnClickListener listener;

    public interface OnClickListener {
        void onClick(CategoriaObjeto categoria);
    }

    public SectionAdapter(OnClickListener listener) {
        this.listener = listener;
    }

    public void setDataFromStrings(List<CategoriaObjeto> Categorias) {
        this.sections = new ArrayList<>(Categorias);
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_section, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        CategoriaObjeto categoria = sections.get(position);
        holder.tv.setText(categoria.getNombre());
        holder.itemView.setOnClickListener(v -> listener.onClick(categoria));
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tv;
        VH(View v) {
            super(v);
            tv = v.findViewById(R.id.tvSectionName);
        }
    }
}