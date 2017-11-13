package com.example.mini_cexentrustment.thread;

/**
 * Created by 信威 on 2017/5/31.
 * 存儲結果回調
 */
public interface OnSaveComplete {
    public void editCompleteCallback(int code);
    public void uploadPhotoComplete(int code);
}
