package com.example.preinterviewtask.Interface;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.preinterviewtask.Object.Item;

import java.util.List;

@Dao
public interface ItemDao {
    // The Integer type parameter tells Room to use a
    // PositionalDataSource object.
    @Query("SELECT * FROM items")
    LiveData<List<Item>> concertsByDate();

    @Insert
    void insert(Item item);

    @Delete
    void delete(Item item);
    @Update
    public void update(Item item);
}