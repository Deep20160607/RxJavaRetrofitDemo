package com.tsingda.rxjavaretrofitdemo.model.net.api;

import com.tsingda.rxjavaretrofitdemo.application.BaseApplication;
import com.tsingda.rxjavaretrofitdemo.application.ConfigConstant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author dupeng
 * @version 1.0.0
 * @since 2017/10/25 16:56
 */

public class ServiceFactory {

    private volatile static Retrofit requestRetrofit;
    private static final int DEFAULT_TIMEOUT = 10;//可以动态设置联网超时时间

    /**
     * 初始化retrofit，单例线程锁
     * @return
     */
    private static Retrofit getRetrofit() {
        if (requestRetrofit == null) {
            synchronized (ServiceFactory.class) {
                if (requestRetrofit == null) {

                    OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
                    httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

                    requestRetrofit = new Retrofit.Builder()
                            .client(httpClientBuilder.build())
                            .baseUrl(BaseApplication.getBuild() == ConfigConstant.OFFLINE ? ConfigConstant.BASE_URL_OFFLINE : ConfigConstant.BASE_URL_ONLINE)
                            .addConverterFactory(GsonConverterFactory.create())//若需要加密，则判断替换
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                }
            }
        }
        return requestRetrofit;
    }

    /**
     * 可能有多种样式的RequestService，所以单独拎出来
     * @return
     */
    public static RequestService getUserService() {
        return getRetrofit().create(RequestService.class);
    }

}
