package com.example.servicebestpractice;

/**
 * Created by Administrator on 2017/5/10.
 */

public interface DownloadListener {

    // 通知当前的下载进度
    void onProgress(int progress);

    void onSuccess();

    void onFail();

    void onPause();

    void onCancel();
}
