package com.example.preinterviewtask.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.preinterviewtask.Interface.CustomItemClickListener;
import com.example.preinterviewtask.Types.NetworkState;
import com.example.preinterviewtask.Object.Item;
import com.example.preinterviewtask.R;

public class RecycleAdapter extends PagedListAdapter<Item, RecycleAdapter.NetworkItemBinding> {
    private static final int TYPE_PROGRESS = 0;
    private static final int TYPE_ITEM = 1;

    private Context context;
    private NetworkState networkState;

    private CustomItemClickListener itemClickListener;
    private CustomItemClickListener deleteListener;
    private CustomItemClickListener updateListener;

    public RecycleAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    public RecycleAdapter(Context context, CustomItemClickListener itemClickListener,
                          CustomItemClickListener deleteListener,
                          CustomItemClickListener updateListener) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.deleteListener = deleteListener;
        this.itemClickListener = itemClickListener;
        this.updateListener = updateListener;
    }


    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return TYPE_PROGRESS;
        } else {
            return TYPE_ITEM;
        }
    }


    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED) {
            return true;
        } else {
            return false;
        }
    }
    @NonNull
    @Override
    public NetworkItemBinding onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new NetworkItemBinding(view);

    }

    @Override
    public void onBindViewHolder(@NonNull NetworkItemBinding holder, int position) {
        Item item = getItem(position);

        if (item != null) {
            holder.textView.setText(item.title);
        } else {
            Toast.makeText(context, "Item not exit", Toast.LENGTH_LONG).show();
        }
        holder.textView.setText(item.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(v, holder.getAdapterPosition()
                        ,getItem(holder.getAdapterPosition()));
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteListener.onItemClick(v, holder.getAdapterPosition()
                        ,getItem(holder.getAdapterPosition()));
            }
        });

        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateListener.onItemClick(v, holder.getAdapterPosition()
                        ,getItem(holder.getAdapterPosition()));
            }
        });

    }

    private static DiffUtil.ItemCallback<Item> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Item>() {
                @Override
                public boolean areItemsTheSame(Item oldItem, Item newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(Item oldItem, Item newItem) {
                    return oldItem.equals(newItem);
                }
            };

    class NetworkItemBinding extends RecyclerView.ViewHolder {

        TextView textView;
        ImageButton deleteBtn ,updateBtn ;

        public NetworkItemBinding(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.title);
            deleteBtn = itemView.findViewById(R.id.delete);
            updateBtn = itemView.findViewById(R.id.edit);
        }
    }
    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }


}