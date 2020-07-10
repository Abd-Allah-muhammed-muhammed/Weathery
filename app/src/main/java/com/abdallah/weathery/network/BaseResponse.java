package com.abdallah.weathery.network;

import java.util.List;

public class BaseResponse<T> {

    public BaseResponse(Throwable throwable) {
        this.throwable = throwable;
    }

    private Throwable throwable;

    private T data;

    private Boolean status;

    private List<String> error;

    public String getMessage() {
        return message;
    }

    private String message;
    private String msg;

    private int client_id;

    public int getClient_id() {
        return client_id;
    }

    public String getUrl() {
        return url;
    }

    private String url;

    public String getMsg() {
        return msg;
    }


    public List<String> getError() {
        return error;
    }


    public BaseResponse(T data) {
        this.data = data;
    }


    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public boolean getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }


}
