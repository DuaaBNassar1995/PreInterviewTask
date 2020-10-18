package com.example.preinterviewtask.Paging;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class ItemDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<ItemDataSource> mutableLiveData;
    private ItemDataSource feedDataSource;

    public ItemDataSourceFactory() {
        this.mutableLiveData = new MutableLiveData<ItemDataSource>();
    }

    @Override
    public DataSource create() {
        feedDataSource = new ItemDataSource();
        mutableLiveData.postValue(feedDataSource);
        return feedDataSource;
    }

    public MutableLiveData<ItemDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}