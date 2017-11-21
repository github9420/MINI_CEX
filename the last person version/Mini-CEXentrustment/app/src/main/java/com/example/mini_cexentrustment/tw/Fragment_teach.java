package com.example.mini_cexentrustment.tw;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mini_cexentrustment.R;

/**
 * Created by rorensu on 2017/11/15.
 */

public class Fragment_teach extends Fragment implements View.OnClickListener {
    private final static String TAG = Fragment_teach.class.getSimpleName();
    private Button btn_teach1;
    private Button btn_teach2;
    private Button btn_teach3;

    private Fragment_teach_teach1 mTab01;
    private Fragment_teach_teach2 mTab02;
    private Fragment_teach_teach3 mTab03;
    private FragmentManager fragmentManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_teach, container, false);
        btn_teach1=(Button)view.findViewById(R.id.id_btn_teach1);
        btn_teach2=(Button)view.findViewById(R.id.id_btn_teach2);
        btn_teach3=(Button)view.findViewById(R.id.id_btn_teach3);

        btn_teach1.setOnClickListener(this);
        btn_teach2.setOnClickListener(this);
        btn_teach3.setOnClickListener(this);
        fragmentManager = getFragmentManager();
        setTabSelection_R(0);
        return view ;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_btn_teach1:
                setTabSelection_R(0);
                break;
            case R.id.id_btn_teach2:
                setTabSelection_R(1);
                break;
            case R.id.id_btn_teach3:
                setTabSelection_R(2);
                break;

        }


    }
    private void setTabSelection_R(int index)
    {
        // 重置按钮
        //resetBtn();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index)
        {
            case 0:
                // 如果MessageFragment为空，则创建一个并添加到界面上
                mTab01 = new Fragment_teach_teach1();
                transaction.add(R.id.id_content, mTab01);
                //transaction.add(R.id.id_content＿inquire, mTab01);
                transaction.addToBackStack(null);
                Log.e(TAG,"mTab01");
                break;
            case 1:
                // 当点击了消息tab时，改变控件的图片和文字颜色
                // 如果MessageFragment为空，则创建一个并添加到界面上
                mTab02 = new Fragment_teach_teach2();
                transaction.add(R.id.id_content, mTab02);
                //transaction.add(R.id.id_content＿inquire, mTab02);
                transaction.addToBackStack(null);
                Log.e(TAG,"mTab02");

                break;
            case 2:
                // 当点击了动态tab时，改变控件的图片和文字颜色
                // 如果NewsFragment为空，则创建一个并添加到界面上
                mTab03 = new Fragment_teach_teach3();
                transaction.add(R.id.id_content, mTab03);
                transaction.addToBackStack(null);
                Log.e(TAG,"mTab03");
                break;



        }
        transaction.commit();
    }
    private void hideFragments(FragmentTransaction transaction) {

        if (mTab01 != null)
        {
            transaction.hide(mTab01);
            //transaction.remove(mTab01);

        }
        if (mTab02 != null){

            //transaction.remove(mTab02);
            transaction.hide(mTab02);
        }
        if (mTab03 != null)
        {
            //transaction.remove(mTab03);
            transaction.hide(mTab03);
        }


    }
}
