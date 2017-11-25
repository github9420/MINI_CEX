package com.example.mini_cexentrustment.tw;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mini_cexentrustment.R;
import com.example.mini_cexentrustment.dao.MyDBHelper;
import com.example.mini_cexentrustment.dao.UserAccountDAO;
import com.example.mini_cexentrustment.define.UserAccount;


import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by rorensu on 2017/11/15.
 */

public class Fragment_inquire extends Fragment implements View.OnClickListener {
    private final static String TAG = Fragment_inquire.class.getSimpleName();

    private Button btn_condition;
    private Button btn_statisc;
    private Button btn_detail;

    private Fragment_inquire_condition mTab01;
    private Fragment_inquire_condition_teacher mTab01T;
    private Fragment_inquire_statisc mTab02;
    private Fragment_inquire_detail mTab03;
    private FragmentManager fragmentManager;
    private String userType = "teacher";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_inquire, container, false);


        //to get the imformation frome the db
        UserAccountDAO db_data = new UserAccountDAO(getActivity());
        List<UserAccount> items = db_data.getAll();
        for (UserAccount i : items) {
            //Log.e(TAG,"SQLuserTYPE"+String.valueOf(i.getUserType()).toString());
            userType = String.valueOf(i.getLoginRole()).toString();
        }

        btn_condition = (Button) view.findViewById(R.id.btn_condition);
        btn_statisc = (Button) view.findViewById(R.id.btn_statistics);
        btn_detail = (Button) view.findViewById(R.id.btn_details);

        btn_condition.setEnabled(false);
        btn_statisc.setEnabled(true);
        btn_detail.setEnabled(true);

        btn_condition.setOnClickListener(this);
        btn_statisc.setOnClickListener(this);
        btn_detail.setOnClickListener(this);
        fragmentManager = getFragmentManager();
        if (userType.equals("student")) {
            setTabSelection_S(0);
            Log.e(TAG, "student");
        } else {
            setTabSelection_T(0);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        if (userType.equals("student")) {
            switch (v.getId()) {
                case R.id.btn_condition:
                    setTabSelection_S(0);
                    break;
                case R.id.btn_statistics:
                    setTabSelection_S(1);
                    break;
                case R.id.btn_details:
                    setTabSelection_S(2);
                    break;

            }
        } else {
            switch (v.getId()) {
                case R.id.btn_condition:
                    setTabSelection_T(0);
                    break;
                case R.id.btn_statistics:
                    setTabSelection_T(1);
                    break;
                case R.id.btn_details:
                    setTabSelection_T(2);
                    break;
            }
        }


    }

    private void setTabSelection_S(int index) {
        // 重置按钮
        //resetBtn();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                // 如果MessageFragment为空，则创建一个并添加到界面上
                mTab01 = new Fragment_inquire_condition();
                transaction.add(R.id.id_content_inquire, mTab01);
                //transaction.add(R.id.id_content＿inquire, mTab01);
                transaction.addToBackStack(null);
                Log.e(TAG, "mTab01");
                break;
            case 1:
                // 当点击了消息tab时，改变控件的图片和文字颜色
                // 如果MessageFragment为空，则创建一个并添加到界面上
                mTab02 = new Fragment_inquire_statisc();
                transaction.add(R.id.id_content_inquire, mTab02);
                //transaction.add(R.id.id_content＿inquire, mTab02);
                transaction.addToBackStack(null);
                Log.e(TAG, "mTab02");

                break;
            case 2:
                // 当点击了动态tab时，改变控件的图片和文字颜色
                // 如果NewsFragment为空，则创建一个并添加到界面上
                mTab03 = new Fragment_inquire_detail();
                transaction.add(R.id.id_content_inquire, mTab03);
                transaction.addToBackStack(null);
                Log.e(TAG, "mTab03");
                break;


        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
//原來的方法
        /*if (mTab01 != null||mTab01T!=null)
        {
            if(userType.equals("student")){
                transaction.hide(mTab01);
                //transaction.remove(mTab01);
                Log.e(TAG,"hi hi");
            }else{
                transaction.hide(mTab01T);
                //transaction.remove(mTab01);
                Log.e(TAG,"hi hi");
            }

        }*/
        if (mTab01 != null) {

            transaction.hide(mTab01);
            //transaction.remove(mTab01);
            Log.e(TAG, "hi hi");
        }
        if (mTab01T != null) {
            transaction.hide(mTab01T);
            //transaction.remove(mTab01);
            Log.e(TAG, "hi hi");
        }
        if (mTab02 != null) {

            //transaction.remove(mTab02);
            transaction.hide(mTab02);
        }
        if (mTab03 != null) {
            //transaction.remove(mTab03);
            transaction.hide(mTab03);
        }


    }

    private void setTabSelection_T(int index) {
        // 重置按钮
        //resetBtn();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                // 如果MessageFragment为空，则创建一个并添加到界面上
                mTab01T = new Fragment_inquire_condition_teacher();
                transaction.add(R.id.id_content_inquire, mTab01T);
                //transaction.add(R.id.id_content＿inquire, mTab01);
                transaction.addToBackStack(null);
                Log.e(TAG, "mTab01");
                break;
            case 1:
                // 当点击了消息tab时，改变控件的图片和文字颜色
                // 如果MessageFragment为空，则创建一个并添加到界面上
                mTab02 = new Fragment_inquire_statisc();
                transaction.add(R.id.id_content_inquire, mTab02);
                //transaction.add(R.id.id_content＿inquire, mTab02);
                transaction.addToBackStack(null);
                Log.e(TAG, "mTab02");

                break;
            case 2:
                // 当点击了动态tab时，改变控件的图片和文字颜色
                // 如果NewsFragment为空，则创建一个并添加到界面上
                mTab03 = new Fragment_inquire_detail();
                transaction.add(R.id.id_content_inquire, mTab03);
                transaction.addToBackStack(null);
                Log.e(TAG, "mTab03");
                break;


        }
        transaction.commit();
    }
}
