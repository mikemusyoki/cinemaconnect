package com.michaelmasai.cinemaconnnect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScreenSelectionAdapter extends RecyclerView.Adapter<ScreenSelectionAdapter.ViewHolder> {

    List<ScreenModel> screenList;
    OnScreenClickListener listener;

    public interface OnScreenClickListener {
        void onScreenClick(ScreenModel screen);
    }

    public ScreenSelectionAdapter(List<ScreenModel> screenList, OnScreenClickListener listener) {
        this.screenList = screenList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_screen, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScreenModel screen = screenList.get(position);

        holder.screenName.setText(screen.getScreenName());
        holder.screenType.setText(screen.getScreenType());
        holder.screenCapacity.setText("Capacity: " + screen.getCapacity());

        holder.itemView.setOnClickListener(v -> listener.onScreenClick(screen));
    }

    @Override
    public int getItemCount() {
        return screenList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView screenName, screenType, screenCapacity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            screenName = itemView.findViewById(R.id.screenName);
            screenType = itemView.findViewById(R.id.screenType);
            screenCapacity = itemView.findViewById(R.id.screenCapacity);
        }
    }
}
