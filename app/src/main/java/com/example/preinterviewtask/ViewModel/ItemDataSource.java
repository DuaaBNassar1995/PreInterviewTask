package com.example.preinterviewtask.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.example.preinterviewtask.Types.NetworkState;
import com.example.preinterviewtask.Object.Item;
import com.example.preinterviewtask.RetrofitApi.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ItemDataSource extends PageKeyedDataSource<Integer, Item> {

    private static final String TAG = ItemDataSource.class.getSimpleName();

    private MutableLiveData networkState;
    private MutableLiveData initialLoading;
    public static final int PAGE_SIZE = 50;

    public static final int FIRST_PAGE = 1;

    public ItemDataSource() {

        networkState = new MutableLiveData();
        initialLoading = new MutableLiveData();
    }


    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public MutableLiveData getInitialLoading() {
        return initialLoading;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull final LoadInitialCallback<Integer, Item> callback) {


        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);

        RetrofitClient.getInstance()
                .getApi()
                .getItems(1, params.requestedLoadSize)
                .enqueue(new Callback<List<Item>>() {
                    @Override
                    public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                        if(response.isSuccessful()) {
                            callback.onResult(response.body(), null, FIRST_PAGE+1);
                            initialLoading.postValue(NetworkState.LOADED);
                            networkState.postValue(NetworkState.LOADED);

                        } else {
                            initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Item>> call, Throwable t) {
                        String errorMessage = t == null ? "unknown error" : t.getMessage();
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                    }
                });
    }



    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Item> callback) {


    }


    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params,
                          @NonNull LoadCallback<Integer, Item> callback) {


        networkState.postValue(NetworkState.LOADING);

        RetrofitClient.getInstance()
                .getApi()
                .getItems(params.key, params.requestedLoadSize).enqueue(new Callback<List<Item>> () {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {

                if(response.isSuccessful()) {
                    Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;

                    callback.onResult(response.body(), adjacentKey);
                    networkState.postValue(NetworkState.LOADED);

                } else networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                String errorMessage = t == null ? "unknown error" : t.getMessage();
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
            }
        });
    }
}