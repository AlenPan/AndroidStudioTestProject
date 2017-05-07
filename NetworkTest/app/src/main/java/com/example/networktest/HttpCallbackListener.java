package com.example.networktest;

/**
 * Created by Administrator on 2017/5/7.
 */

public interface HttpCallbackListener {

    void onFinish(String response);

    void onError(Exception e);
}
