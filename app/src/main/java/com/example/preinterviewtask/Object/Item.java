package com.example.preinterviewtask.Object;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "items")
public class Item implements Serializable {

    @PrimaryKey
    public int id;
    @ColumnInfo(name = "url")
    public String url;
    @ColumnInfo(name = "title")
    public String title;


    public Item(String url, String title, int id) {
        this.url = url;
        this.title = title;
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

