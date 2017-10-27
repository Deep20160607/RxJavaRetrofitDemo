package com.tsingda.rxjavaretrofitdemo.model.net.api;


import com.tsingda.rxjavaretrofitdemo.model.net.bean.UserBeanRes;
import com.tsingda.rxjavaretrofitdemo.model.net.bean.common.ResCommon;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 请求接口注解类(可以根据功能划分)
 *
 * @author dupeng
 * @version 1.0.0
 * @since 2017/10/25 17:16
 */

public interface RequestService {


    /**Get-Query*/
    @GET("getInfo")
    //Call<UserBeanRes> getQuery(@Query("userName") String userName, @Query("userPwd") String userPwd);
    rx.Observable<ResCommon<UserBeanRes>> getQuery(@Query("userName") String userName, @Query("userPwd") String userPwd);

    /**Get-List_Query*/
    @GET("GoodShop")
    //Call<UserBeanRes> getQuery(@Query("userName") String userName, @Query("userPwd") String userPwd);
    rx.Observable<ResCommon<List<UserBeanRes>>> getListQuery(@Query("userName") String userName, @Query("userPwd") String userPwd);

    /**POST-Field*/
    @FormUrlEncoded
    @POST("GoodShop")
    //Call<UserBeanRes> getField(@Field("userName") String userName, @Field("userPwd") String userPwd);
    rx.Observable<UserBeanRes> getField(@Field("userName") String userName, @Field("userPwd") String userPwd);

    /**POST-Body*/
    @POST("GoodShop")
    //Call<UserBeanRes> getBody(@Body UserBeanRes userName);
    rx.Observable<UserBeanRes> getBody(@Body UserBeanRes userName);
}
