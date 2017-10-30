package com.tsingda.rxjavaretrofitdemo.model.net.api;

import com.tsingda.rxjavaretrofitdemo.application.BaseApplication;
import com.tsingda.rxjavaretrofitdemo.application.ConfigConstant;
import com.tsingda.rxjavaretrofitdemo.utils.SystemState;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
    private static Retrofit getRetrofit(boolean isCache) {
        if (requestRetrofit == null) {
            synchronized (ServiceFactory.class) {
                if (requestRetrofit == null) {
                    OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
                    httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
                    if(isCache) {
                        File cacheFile = new File(BaseApplication.getAppContext().getExternalCacheDir(),"ZhiBookCache");
                        Cache cache = new Cache(cacheFile,1024*1024*50);
                        Interceptor interceptor = new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request request = chain.request();
                                if (!SystemState.isNetConnected()) {
                                    request = request.newBuilder()
                                            .cacheControl(CacheControl.FORCE_CACHE)
                                            .build();
                                }
                                Response response = chain.proceed(request);
                                if (SystemState.isNetConnected()) {
                                    int maxAge = 0 * 60;
                                    // 有网络时 设置缓存超时时间0个小时
                                    response.newBuilder()
                                            .header("Cache-Control", "public, max-age=" + maxAge)
                                            .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                                            .build();
                                } else {
                                    // 无网络时，设置超时为4周
                                    int maxStale = 60 * 60 * 24 * 28;
                                    response.newBuilder()
                                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                            .removeHeader("Pragma")
                                            .build();
                                }
                                return response;
                            }
                        };
                        httpClientBuilder.cache(cache)
                                .addInterceptor(interceptor)
                                .addNetworkInterceptor(interceptor);
                    }
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
    public static RequestService getUserService(boolean isCache) {
        return getRetrofit(isCache).create(RequestService.class);
    }

}
