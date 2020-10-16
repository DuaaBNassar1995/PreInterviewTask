package com.example.preinterviewtask.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.preinterviewtask.Interface.CustomItemClickListener;
import com.example.preinterviewtask.Object.ItemObject;
import com.example.preinterviewtask.R;

import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {

    ArrayList<ItemObject> arrayList;

    CustomItemClickListener listenerItem ,deleteListener ,editListener;

    public RecycleAdapter(ArrayList<ItemObject> arrayList ,
                              CustomItemClickListener listenerItem ,CustomItemClickListener deleteListener
            ,CustomItemClickListener editListener) {

        this.arrayList = arrayList;
        this.listenerItem = listenerItem ;
        this.editListener = editListener ;
        this.deleteListener = deleteListener ;
    }

    @NonNull
    @Override
    public RecycleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecycleAdapter.MyViewHolder holder, final int position) {
        final ItemObject item = arrayList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerItem.onItemClick(v, holder.getAdapterPosition());
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteListener.onItemClick(v, holder.getAdapterPosition());
            }
        });

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editListener.onItemClick(v, holder.getAdapterPosition());
            }
        });

        holder.title.setText(item.getTitle());
    }

    private void removeItem (int position){

    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
            TextView title ;
            ImageButton deleteBtn , editBtn ;

        public MyViewHolder(View view) {
            super(view);
            title = itemView.findViewById(R.id.title);
            deleteBtn = itemView.findViewById(R.id.delete);
            editBtn = itemView.findViewById(R.id.edit);
        }
    }
}
