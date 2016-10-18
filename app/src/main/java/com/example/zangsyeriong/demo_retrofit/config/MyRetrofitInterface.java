package com.example.zangsyeriong.demo_retrofit.config;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by ZangSyeriong on 2016/10/17.
 */
public interface MyRetrofitInterface {


    //url全部替换
    @GET
    Call<ResponseBody> getHttp(@Url() String url);

    //Path部分替换,把1234替换成6875.
    @GET("ewqtr/{1234}")
    Call<ResponseBody> getPath(@Path("6875") String path);

    //Query传递一个参数时使用
    @GET("fgrf/12314")
    Call<ResponseBody> getQuery(@Query("id") String param, @Query("clazz") String clazz);

    //QueryMap传递多个参数
    @GET("fgsret/32141")
    Call<ResponseBody> getQueryMap(@QueryMap() Map<String, String> params);

    //Field传递一个参数表单,必须用FormUrlEncoded和POST，不能用GET
    @FormUrlEncoded
    @POST
    Call<ResponseBody> getField(@Field("id")String param, @Field("clazz")String clazz);

    //FieldMap传递多个参数表单
    @FormUrlEncoded
    @POST
    Call<ResponseBody> getFieldMap(@FieldMap() Map<String, String> params);

    //Header提交一个网络请求头参数
    @GET("sagfdsg/123145")
    Call<ResponseBody> getHead(@Header("6543") String url);

    //HeaderMap提交多个网络请求头参数
    @GET
    Call<ResponseBody> getHeaderMap(@HeaderMap() Map<String, String> params);

    //----------------------------上传文件-----------------------------------
    //Part(必须用Multipart,POST)
    @Multipart
    @POST
    Call<ResponseBody> getUpLoadPart(@Part()RequestBody requestBody);

    //Body
    @POST
    Call<ResponseBody> getUpLoadBody(@Body RequestBody requestBody);

    //----------------------------下载文件-----------------------------------
    //Streaming(POST和GET都可以使用)通过地址下载文件
    @Streaming
    @POST
    Call<ResponseBody> getDownLoadStreaming(@Url() String url);

}
