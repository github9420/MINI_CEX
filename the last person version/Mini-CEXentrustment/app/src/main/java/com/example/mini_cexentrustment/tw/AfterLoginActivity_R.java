package com.example.mini_cexentrustment.tw;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.example.mini_cexentrustment.R;
import com.example.mini_cexentrustment.dao.UserAccountDAO;
import com.example.mini_cexentrustment.define.UserAccount;
import com.example.mini_cexentrustment.framework.BaseActivity;
import com.example.mini_cexentrustment.thread.NetTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rorensu on 2017/11/15.
 */

public class AfterLoginActivity_R extends BaseActivity implements View.OnClickListener {
    private final static String TAG = AfterLoginActivity_R.class.getSimpleName();
    private ImageButton imgBtn_n;
    private ImageButton imgBtn_e;
    private ImageButton imgBtn_i;
    private ImageButton imgBtn_t;
    private ImageButton imgBtn_s;

    private Fragment_News mTab01;
    private Fragment_evaluation mTab02;
    private Fragment_apply_update mTab02S;
    private Fragment_inquire mTab03;
    private Fragment_teach mTab04;
    private Fragment_setup mTab05;

    private FragmentManager fragmentManager;

    private String userType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //to get the imformation frome the db
        UserAccountDAO db_data = new UserAccountDAO(this);
        List<UserAccount> items = db_data.getAll();
        for (UserAccount i : items) {
            userType = String.valueOf(i.getLoginRole()).toString();  //to check whether u r the teacher or student
        }
        //userType="teacher";     //測試用



        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_afterlogin_r);
        initViews();
        UserAccount mm = new UserAccount();
        Log.e(TAG, "" + mm.getId());

        fragmentManager = getFragmentManager();
        setTabSelection_R(0);


    }


    private void initViews() {
        imgBtn_n = (ImageButton) findViewById(R.id.imgBtn_news);
        imgBtn_e = (ImageButton) findViewById(R.id.imgBtn_evaluation);
        imgBtn_i = (ImageButton) findViewById(R.id.imgBtn_inqire);
        imgBtn_t = (ImageButton) findViewById(R.id.imgBtn_teach);
        imgBtn_s = (ImageButton) findViewById(R.id.imgBtn_setting);

        imgBtn_n.setOnClickListener(this);
        imgBtn_e.setOnClickListener(this);
        imgBtn_i.setOnClickListener(this);
        imgBtn_t.setOnClickListener(this);
        imgBtn_s.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBtn_news:
                setTabSelection_R(0);
                break;
            case R.id.imgBtn_evaluation:
                setTabSelection_R(1);
                break;
            case R.id.imgBtn_inqire:
                setTabSelection_R(2);
                break;
            case R.id.imgBtn_teach:
                setTabSelection_R(3);
                break;
            case R.id.imgBtn_setting:
                setTabSelection_R(4);
                break;
        }

    }

    private void setTabSelection_R(int index) {
        // 重置按钮
        //resetBtn();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                // 如果MessageFragment为空，则创建一个并添加到界面上

                mTab01 = new Fragment_News();
                transaction.add(R.id.id_content, mTab01);
                transaction.addToBackStack(null);
                Log.e(TAG, "mTab01");
                break;
            case 1:
                // 当点击了消息tab时，改变控件的图片和文字颜色
                // 如果MessageFragment为空，则创建一个并添加到界面上
                if (userType.equals("teacher")) {
                    mTab02 = new Fragment_evaluation();
                    transaction.add(R.id.id_content, mTab02);
                    transaction.addToBackStack(null);
                    Log.e(TAG, "mTab02");
                    Log.e(TAG, "the correct role:" + userType);
                } else if (userType.equals("student")) {
                    Log.e(TAG, "the correct role:" + userType);
                    mTab02S = new Fragment_apply_update();
                    transaction.add(R.id.id_content, mTab02S);
                    transaction.addToBackStack(null);
                    Log.e(TAG, "mTab02S");
                } else {
                    Log.e(TAG, "mTab02 has the wrong role");
                    Log.e(TAG, "the correct role:" + userType);
                }
                break;
            case 2:
                // 当点击了动态tab时，改变控件的图片和文字颜色
                // 如果NewsFragment为空，则创建一个并添加到界面上

                mTab03 = new Fragment_inquire();
                transaction.add(R.id.id_content, mTab03);
                transaction.addToBackStack(null);
                Log.e(TAG, "mTab03");
                break;
            case 3:
                mTab04 = new Fragment_teach();
                transaction.add(R.id.id_content, mTab04);
                transaction.addToBackStack(null);
                Log.e(TAG, "mTab04");
                break;
            case 4:
                mTab05 = new Fragment_setup();
                transaction.add(R.id.id_content, mTab05);
                transaction.addToBackStack(null);
                Log.e(TAG, "mTab05");
                break;


        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {

        if (mTab01 != null) {
            //transaction.hide(mTab01);
            transaction.remove(mTab01);
        }
        if (mTab02 != null) {

            transaction.remove(mTab02);

        }
        if (mTab02S != null) {
            transaction.remove(mTab02S);

        }
        if (mTab03 != null) {
            transaction.remove(mTab03);
            //transaction.hide(mTab03);
        }
        if (mTab04 != null) {
            transaction.remove(mTab04);
            //transaction.hide(mTab03);
        }
        if (mTab05 != null) {
            transaction.remove(mTab05);
            //transaction.hide(mTab03);
        }
    }


    //以後可以設定按下按鈕後會變色
    private void resetBtn() {

    }
}
