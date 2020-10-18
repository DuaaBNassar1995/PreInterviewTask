package com.example.preinterviewtask.ViewModel;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.preinterviewtask.Interface.ItemDao;
import com.example.preinterviewtask.Object.Item;

import java.util.List;

public class ItemRepository {
    private ItemDao mItemDao;
    private LiveData<List<Item>> mAllItems;

    ItemRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mItemDao = db.itemDao();
        mAllItems = mItemDao.concertsByDate();
    }

    LiveData<List<Item>> getAllItems() {
        return mAllItems;
    }

    void insert(Item item) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mItemDao.insert(item);
        });
    }

    void delete(Item item) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mItemDao.delete(item);
        });
    }

    void update(Item item) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mItemDao.update(item);
        });
    }
}