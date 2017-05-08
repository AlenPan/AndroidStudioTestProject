package com.example.androidthreadtest;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/5/8.
 */

public class DownloadTask extends AsyncTask<Void, Integer, Boolean> {

    ProgressDialog progressDialog;

    @Override
    protected void onPreExecute() {
        // 显示进度对话框
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            while (true) {
                int downloadPercent = doDownload();
                publishProgress(downloadPercent);
                if (downloadPercent >= 100) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // 在这里更新下载进度
        progressDialog.setMessage("Downloaded " + values[0] + "%");
    }

    @Override
    protected void onPostExecute(Boolean result) {
        progressDialog.dismiss();
        // 在这里提示下载结果
        if (result) {
            toast("Download succeeded");
        } else {
            toast("Download failed");
        } 
    }

    private void toast(String s) {
    }

    private int doDownload() {
        return 0;
    }
}
