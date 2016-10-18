package com.example.zangsyeriong.demo_retrofit.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.zangsyeriong.demo_retrofit.R;
import com.example.zangsyeriong.demo_retrofit.config.MyRetrofitInterface;
import com.example.zangsyeriong.demo_retrofit.constants.MyConstant;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity {
    private int num=0;
    private Button btnDownLoad;
    private ImageView ivShow;
    private MyRetrofitInterface myRetrofitInterface;
    private ProgressBar downloadPb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //实例化Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.baidu.com/")//最基本的URL
                .build();
        myRetrofitInterface = retrofit.create(MyRetrofitInterface.class);

        btnDownLoad = (Button) findViewById(R.id.btn_downLoad);
        ivShow = (ImageView) findViewById(R.id.iv_show);
        downloadPb = (ProgressBar) findViewById(R.id.pb_downLoad);
        btnDownLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getBaiDu();
                new DownLoadAsyncTask().execute();
            }
        });
    }
    public class DownLoadAsyncTask extends AsyncTask<Void, Integer, Bitmap> {


        @Override
        protected Bitmap doInBackground(Void... params) {

            Call<ResponseBody> call = myRetrofitInterface.getDownLoadStreaming(MyConstant.IMG_URL);
//            BufferedInputStream bis = null;
//            ByteArrayOutputStream baos = null;
            FileOutputStream fos = null;
            Bitmap bitmap = null;
            try {
                Response<ResponseBody> response = call.execute();
                ResponseBody responseBody = response.body();
                long l = responseBody.contentLength();
                InputStream inputStream = responseBody.byteStream();
//                bis = new BufferedInputStream(inputStream);
//                baos = new ByteArrayOutputStream();
                File file = new File(Environment.getExternalStorageDirectory()+"a.png");
                fos = new FileOutputStream(file);
                byte[] data = new byte[1024];

                int len = 0;
                while ((len = inputStream.read(data)) != -1){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    baos.write(data, 0, len);
                    fos.write(data, 0, len);
                    num+=len;
                    publishProgress((int)(num*100/l));
                    fos.flush();
                }
//                byte[] datas = bis.toByteArray();
//                bitmap = BitmapFactory.decodeByteArray(datas, 0 ,datas.length);
                fos.close();
//                bis.close();
                inputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
//                if (bis != null){
//                    try {
//                        bis.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (baos != null){
//                    try {
//                        baos.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
                if (fos != null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            downloadPb.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
//            ivShow.setImageBitmap(bitmap);
        }
    }
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                BufferedInputStream bis = null;
//                ByteArrayOutputStream baos = null;
//                FileOutputStream fos = null;
//                Bitmap bitmap = null;
//                ResponseBody responseBody = response.body();
//                try {
//                    InputStream inputStream = responseBody.byteStream();
//                    bis = new BufferedInputStream(inputStream);
//                    baos = new ByteArrayOutputStream();
//                    File file = new File(Environment.getExternalStorageDirectory()+"a.png");
//                    fos = new FileOutputStream(file);
//                    byte[] data = new byte[1024];
//                    int len = 0;
//                    while ((len = bis.read(data)) != -1){
//                        baos.write(data, 0, len);
//                        fos.write(data, 0, len);
//                        fos.flush();
//                        Log.e("Tag",fos.toString());
//                    }
//                    byte[] datas = baos.toByteArray();
//                    bitmap = BitmapFactory.decodeByteArray(datas, 0 ,datas.length);
//                    fos.close();
//                    bis.close();
//                    inputStream.close();
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }finally {
//                    if (bis != null){
//                        try {
//                            bis.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (baos != null){
//                        try {
//                            baos.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (fos != null){
//                        try {
//                            fos.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
    }
//    private void getBaiDu(){
//        Call<ResponseBody> call = myRetrofitInterface.getHttp("https://www.hao123.com/");
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                ResponseBody responseBody = response.body();
//                try {
//                    String s = responseBody.string();
//                    tvShow.setText(s);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
//    }
//}
