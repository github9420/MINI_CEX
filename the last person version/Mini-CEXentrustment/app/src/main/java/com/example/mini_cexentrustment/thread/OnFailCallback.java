package com.example.mini_cexentrustment.thread;

/**
 * Created by 信威 on 2017/5/31.
 * 錯誤結果回調
 */
public interface OnFailCallback {
    public void createUserFailCallback(int code);
    public void readDataFailCallback(String jsonData);
}
