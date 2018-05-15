package com.chailijun.baselib.data;

import com.google.gson.annotations.SerializedName;



public class Http<T> {

    @SerializedName("code")
    private int code;

    @SerializedName("data")
    private T data;

    @SerializedName("message")
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
