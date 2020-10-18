package com.example.preinterviewtask.ui.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.preinterviewtask.Interface.CustomItemClickListener;
import com.example.preinterviewtask.Object.Item;
import com.example.preinterviewtask.Paging.ItemViewModel;
import com.example.preinterviewtask.Types.Keys;
import com.example.preinterviewtask.R;
import com.example.preinterviewtask.Adapter.ItemRecycleAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {
    public static String TYPE = "TYPE";
    public static String ITEM = "ITEM";
    private ItemRecycleAdapter adapter;
    private ItemViewModel feedViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        feedViewModel = new ItemViewModel();

        RecyclerView recyclerView = findViewById(R.id.recycleView);
        FloatingActionButton addBtn = findViewById(R.id.addBtn);

        recyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        adapter = new ItemRecycleAdapter(this, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position, Item item) {
                openDetails(Keys.viewType, item);
            }
        }, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position, Item item) {
                DeleteItem(MainActivity.this);
            }
        }, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position, Item item) {
                openDetails(Keys.updateType, item);
            }
        });

        feedViewModel.getItemLiveData().observe(this, pagedList -> {
            adapter.submitList(pagedList);
        });


        feedViewModel.getNetworkState().observe(this, networkState -> {
            adapter.setNetworkState(networkState);
        });

        //setting the adapter
        recyclerView.setAdapter(adapter);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDetails(Keys.addType, null);
            }
        });
    }

    public void DeleteItem(final Activity activity) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final String message = getResources().getString(R.string.deleteItem);

        builder.setMessage(message)
                .setPositiveButton(getResources().getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                d.dismiss();
                            }
                        })
                .setNegativeButton(getResources().getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                d.cancel();
                            }
                        });
        builder.create().show();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void openDetails(int type, Item item) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra(TYPE, type);
        if (type != Keys.addType)
            intent.putExtra(ITEM, item);
        startActivity(intent);
    }

}