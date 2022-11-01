package com.example.unibody.me.fragment.http;

public interface CallBack<T> {

    public void onResponse(T data);

    public void onFail(String msg);

}
