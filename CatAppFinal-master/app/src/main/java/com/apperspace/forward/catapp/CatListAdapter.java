package com.apperspace.forward.catapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CatListAdapter extends RecyclerView.Adapter<CatListAdapter.ViewHolder> {

    private List<Model> eventList;
    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public CatListAdapter(Context context, List<Model> eventList) {
        this.eventList = eventList;
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public int getItemCount()
    {
        return eventList == null? 0: eventList.size();
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {
        final Model item = eventList.get(position);
        holder.setDetails(item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView mBreedName;

        public ViewHolder(View itemView)
        {
            super(itemView);
            mBreedName = itemView.findViewById(R.id.tv_txt);
            itemView.setOnClickListener(this);
        }

         public void setDetails(Model item)
        {
            mBreedName.setText(item.getName());
        }

        @Override
        public void onClick(View v)
        {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

}


