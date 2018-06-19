package com.example.gao.letsv.MainViews;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.Toast;

import com.example.gao.letsv.LoginViews.LoginActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;


/**
 * Created by Lucien on 2018/6/19.
 */

public class UploadFile {

    private static boolean isSuccess = false;

    /**
     * 异步上传文件
     *
     * @param path :文件路径
     * @param url  :上传的url
     */
    public static boolean uploadFile(final Context context, String path, String url, String filename) throws Exception {
        File file = new File(path);
        if (file.exists() && file.length() > 0) {
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(60000);
            RequestParams params = new RequestParams();
            params.put("file", file);
            //params.put("newFilename", filename);

            // 设置进度条最大值
            //progress.setMax(Integer.parseInt(file.length() + ""));
            SweetAlertDialog pDialog2 = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            pDialog2.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog2.setTitleText("正在上传......");
            pDialog2.setCancelable(false);
            pDialog2.show();

            // 上传文件
            client.post(url, params, new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    // 上传成功后要做的工作
                    //progress.dismiss();
                    pDialog2.cancel();
                    Toast.makeText(context, "上传成功", Toast.LENGTH_LONG).show();
                    // progress.setProgress(0);
                    isSuccess = true;
                    Intent intent = new Intent(context, PhotoViewActivity.class);
                    context.startActivity(intent);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    //progress.dismiss();
                    pDialog2.cancel();
                    Toast.makeText(context, "上传失败", Toast.LENGTH_LONG).show();
                    isSuccess = false;
                }

                @Override
                public void onProgress(long bytesWritten, long totalSize) {
                    //int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
                    // 上传进度显示
                    //progress.setProgress(count);
                    super.onProgress(bytesWritten, totalSize);
                }

                @Override
                public void onRetry(int retryNo) {
                    super.onRetry(retryNo);
                    // 返回重试次数
                }
            });
        } else {
            //progress.dismiss();
            Toast.makeText(context, "文件不存在", Toast.LENGTH_LONG).show();
        }
        return isSuccess;
    }
}
