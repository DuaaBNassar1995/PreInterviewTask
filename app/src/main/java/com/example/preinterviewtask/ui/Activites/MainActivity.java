package com.example.preinterviewtask.ui.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import com.example.preinterviewtask.Interface.CustomItemClickListener;
import com.example.preinterviewtask.Object.ItemObject;
import com.example.preinterviewtask.R;
import com.example.preinterviewtask.Adapter.RecycleAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecycleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycleView);
        recyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        ArrayList<ItemObject> arrayList = new ArrayList<>();
        arrayList.add(new ItemObject(1, "title", "image"));

        adapter = new RecycleAdapter(arrayList, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                startActivity(intent);
            }
        },new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                DeleteItem(MainActivity.this ,position);
            }
        }, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    public void DeleteItem(final Activity activity, final int position) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final String message = getResources().getString(R.string.deleteItem);

        builder.setMessage(message)
                .setPositiveButton(getResources().getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {

                                adapter.notifyDataSetChanged();
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
}