package com.example.servicebestpractice;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.R.attr.type;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Administrator on 2017/5/10.
 */

public class DownloadTask extends AsyncTask<String, Integer, Integer> {

    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_PAUSED = 2;
    public static final int TYPE_CANCELED = 3;

    private DownloadListener listener;

    private boolean isCanceled;

    private boolean isPaused;

    private int lastProgress;

    public DownloadTask(DownloadListener listener) {
        this.listener = listener;
    }

    @Override
    protected Integer doInBackground(String... params) {
        // 用于在后台执行具体的下载逻辑
        InputStream is = null;
        RandomAccessFile savedFile = null;
        File file = null;
        long downloadLength = 0;    // 记录已下载的文件长度
        try {
            // 传入要下载文件的Url地址
            String downloadUrl = params[0];
            // 根据Url地址解析出下载的文件名（结果是/filename）
            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
            // 指定将文件下载到Environment.DIRECTORY_DOWNLOADS目录下
            String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            file = new File(directory + fileName);
            // 先判断一下Downloads目录是否存在要下载的文件
            if (file.exists()) {
                // 如果已存在文件，则获取已下载文件的字节数，这样可以在后面启用断点续传功能
                downloadLength = file.length();
            }
            // 通过Url地址获取下载文件的总长度
            long contentLength = getContentLength(downloadUrl);
            if (contentLength == 0) {
                // 如果文件长度为0，说明文件有问题
                return TYPE_FAILED;
            } else if (contentLength == downloadLength) {
                // 如果文件长度等于已下载文件的长度，那么就说明文件已经下载完了
                return TYPE_SUCCESS;
            }

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    // 断点下载，指定从哪个字节开始下载
                    .addHeader("RANGE", "bytes=" + downloadLength + "-")
                    .url(downloadUrl)
                    .build();
            Response response = client.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                is = response.body().byteStream();
                savedFile = new RandomAccessFile(file, "rw");
                int total = 0;  // 记录继续下载的字节数
                byte[] b = new byte[1024];
                int len;
                while ((len = is.read(b)) != 0) {
                    if (isCanceled) {
                        return TYPE_CANCELED;
                    } else if (isPaused) {
                        return TYPE_PAUSED;
                    } else {
                        total += len;
                        savedFile.write(b, 0, len);
                        // 计算已下载的百分比
                        int progress = (int) ((total + downloadLength) * 100 / contentLength);
                        publishProgress(progress);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (savedFile != null) {
                    savedFile.close();
                }
                if (isCanceled && file != null) {
                    file.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return TYPE_FAILED;
    }

    // 通过URL地址获取要下载的文件的字节数大小
    private long getContentLength(String downloadUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        Response response = client.newCall(request).execute();
        if (response != null && response.isSuccessful()) {
            long contentLength = response.body().contentLength();
            response.close();
            return contentLength;
        }
        return 0;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // 用于在界面上更新当前的下载进度
        int progress = values[0];
        if (progress > lastProgress) {
            listener.onProgress(progress);
            lastProgress = progress;
        }
    }

    public void pauseDownlaod() {
        isPaused = true;
    }

    public void cancelDownload() {
        isCanceled = true;
    }

    @Override
    protected void onPostExecute(Integer status) {
        // 通知最终的下载结果
        switch (status) {
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            case TYPE_FAILED:
                listener.onFail();
                break;
            case TYPE_PAUSED:
                listener.onPause();
                break;
            case TYPE_CANCELED:
                listener.onCancel();
                break;
            default:
                break;
        }
    }
}
