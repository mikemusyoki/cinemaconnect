package com.michaelmasai.cinemaconnnect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SnackAdapter extends RecyclerView.Adapter<SnackAdapter.SnackViewHolder> {

    // --- Interface for Click Events ---
    public interface OnSnackSelectListener {
        void onSnackSelected(SnackModel snack);
    }

    // --- Fields ---
    private final List<SnackModel> snackList;
    private final OnSnackSelectListener listener;

    // --- Constructor ---
    public SnackAdapter(List<SnackModel> snackList, OnSnackSelectListener listener) {
        this.snackList = snackList;
        this.listener = listener;
    }

    // --- Adapter Methods ---

    @NonNull
    @Override
    public SnackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_snack, parent, false);
        return new SnackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SnackViewHolder holder, int position) {
        SnackModel snack = snackList.get(position);
        holder.bind(snack, listener);
    }

    @Override
    public int getItemCount() {
        return snackList.size();
    }

    // --- ViewHolder Inner Class ---

    public class SnackViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvSnackEmoji;
        private final TextView tvSnackName;
        private final TextView tvSnackPrice;

        public SnackViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSnackEmoji = itemView.findViewById(R.id.snackEmoji);
            tvSnackName = itemView.findViewById(R.id.snackName);
            tvSnackPrice = itemView.findViewById(R.id.snackPrice);
        }

        /**
         * Binds data to the views and handles selection logic.
         */
        public void bind(final SnackModel snack, final OnSnackSelectListener listener) {
            // 1. Set Data
            tvSnackEmoji.setText(snack.getEmoji());
            tvSnackName.setText(snack.getName());
            tvSnackPrice.setText("KSh " + snack.getPrice());

            // 2. Set Visual State (Dim if selected)
            itemView.setAlpha(snack.isSelected() ? 0.6f : 1.0f);

            // 3. Handle Click
            itemView.setOnClickListener(v -> {
                // Toggle state in model
                // FIX: Use the setter 'setSelected' instead of the getter 'isSelected'
                snack.setSelected(!snack.isSelected());

                // Refresh view to show new Alpha
                notifyItemChanged(getAdapterPosition());

                // Notify parent activity
                listener.onSnackSelected(snack);
            });
        }
    }
}