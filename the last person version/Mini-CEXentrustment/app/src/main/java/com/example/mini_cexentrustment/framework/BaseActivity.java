package com.example.mini_cexentrustment.framework;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.example.mini_cexentrustment.R;

import java.util.List;

public class BaseActivity extends AppCompatActivity {
    private  static  final String TAG = BaseActivity.class.getSimpleName();
    //public  abstract  void initJSONObject();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.sliding_in_left, R.anim.sliding_out_left);

    }

    @Override
    public  void finish(){
        super.finish();
        if(!isLastOneActivity()){
            overridePendingTransition(R.anim.sliding_in_right, R.anim.sliding_out_right);
        }
    }

    protected void onFinish() {

    }

    @SuppressWarnings("deprecation")
    public boolean isLastOneActivity() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            Log.d(TAG, "isLastOneActivity tasks.get(0).numActivities " + tasks.get(0).numActivities);
            if (tasks.get(0).numActivities == 1) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || event.getAction() == KeyEvent.KEYCODE_BACK) {
            backAction();
            if (backPress()) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void onError() {
        backPress();
    }

    protected boolean backPress() {
        finish();
        return true;
    }

    protected void backAction() {
        /*
        // 按下"返回" 按鈕 顯示主螢幕
        // 所以這個 應用程序不會被意外關閉
        Intent intentHome = new Intent(Intent.ACTION_MAIN);
        intentHome.addCategory(Intent.CATEGORY_HOME);
        intentHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentHome);
        */
    }

}
