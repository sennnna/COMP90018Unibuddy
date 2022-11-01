package com.example.unibody.me.fragment.bean;


public class PhotoState {
    private String originPath;
    private String path;

    private boolean isUrl;
    private int state;
    private String url;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setIsUrl(String url) {
        this.url = url;
    }

    public String getOriginPath() {
        return originPath;
    }

    public void setOriginPath(String originPath) {
        this.originPath = originPath;
    }


    public boolean isUrl() {
        return isUrl;
    }

    public void setIsUrl(boolean url) {
        isUrl = url;
    }
}
