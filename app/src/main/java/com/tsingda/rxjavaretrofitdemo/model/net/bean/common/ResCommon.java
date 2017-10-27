package com.tsingda.rxjavaretrofitdemo.model.net.bean.common;

/**
 * @author dupeng
 * @version 1.0.0
 * @since 2017/10/25 18:05
 */

public class ResCommon<T> {

    private String resCode;
    private String resMessage;
    private T data;

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMessage() {
        return resMessage;
    }

    public void setResMessage(String resMessage) {
        this.resMessage = resMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("resCode=" + resCode + " resMessage=" + resMessage );
        if (null != data) {
            sb.append(" subjects:" + data.toString());
        }
        return sb.toString();
    }
}
