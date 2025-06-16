package dsa.upc.edu.listapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import dsa.upc.edu.listapp.models.Faq;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.FaqViewHolder> {
    private List<Faq> faqs = new ArrayList<>();

    public void setFaqs(List<Faq> faqs) {
        this.faqs = faqs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FaqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_faq, parent, false);
        return new FaqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaqViewHolder holder, int position) {
        Faq faq = faqs.get(position);
        holder.bind(faq);
    }

    @Override
    public int getItemCount() {
        return faqs.size();
    }

    static class FaqViewHolder extends RecyclerView.ViewHolder {
        private TextView tvQuestion, tvAnswer, tvSenderDate;

        public FaqViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            tvAnswer = itemView.findViewById(R.id.tvAnswer);
            tvSenderDate = itemView.findViewById(R.id.tvSenderDate);
        }

        public void bind(Faq faq) {
            tvQuestion.setText(faq.getQuestion());
            tvAnswer.setText(faq.getAnswer());
            tvSenderDate.setText(faq.getSender() + " · " + faq.getDate());
        }
    }
}