package com.example.preinterviewtask.ViewModel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.preinterviewtask.Types.NetworkState;
import com.example.preinterviewtask.Object.Item;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ItemViewModel extends ViewModel {
    private Executor executor;
    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<Item>> itemLiveData;

    public ItemViewModel() {
        init();
    }

    private void init() {
        executor = Executors.newFixedThreadPool(5);

        ItemDataSourceFactory feedDataFactory = new ItemDataSourceFactory();
        networkState = Transformations.switchMap(feedDataFactory.getMutableLiveData(),
                dataSource -> dataSource.getNetworkState());

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(20).build();

        itemLiveData = (new LivePagedListBuilder(feedDataFactory, pagedListConfig))
                .setFetchExecutor(executor)
                .build();
    }

    /*
     * Getter method for the network state
     */
    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }


    public LiveData<PagedList<Item>> getItemLiveData() {
        return itemLiveData;
    }
}
