package com.example.preinterviewtask.ViewModel;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.preinterviewtask.Interface.ItemDao;
import com.example.preinterviewtask.Object.Item;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Item.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ItemDao itemDao();
    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 3;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
