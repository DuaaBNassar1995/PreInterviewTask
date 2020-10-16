package com.example.preinterviewtask.Interface;

/**
 * Created by AL-Qema on 20/06/18.
 */

    public interface UniversalCallBack {

        void onResponse(Object result);
        void onFailure(Object result);
        void onFinish();
        void OnError(String message);
    }
