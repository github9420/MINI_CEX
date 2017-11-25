package com.example.mini_cexentrustment.tw;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mini_cexentrustment.R;
import com.example.mini_cexentrustment.dao.UserAccountDAO;
import com.example.mini_cexentrustment.define.CommandType;
import com.example.mini_cexentrustment.define.UserAccount;
import com.example.mini_cexentrustment.network.ServerConnect;
import com.example.mini_cexentrustment.thread.NetTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rorensu on 2017/11/15.
 */

public class Fragment_setup extends Fragment implements View.OnClickListener {
    private final static String TAG = Fragment_setup.class.getSimpleName();
    private ListView listview;
    private TextView txt_edit,txt_about,txt_logout;
    private TextView txt_recording;
    private String[] str = {"編輯個人資料", "關於APP", "登出"};
    private String userType="";
    private String userId="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup, null);
        Log.e(TAG,"setup");
        txt_about=(TextView)view.findViewById(R.id.id_txt_about);
        txt_edit=(TextView)view.findViewById(R.id.id_txt_profile);
        txt_logout=(TextView)view.findViewById(R.id.id_txt_logout);
        txt_about.setOnClickListener(this);
        txt_edit.setOnClickListener(this);
        txt_logout.setOnClickListener(this);



        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.e(TAG,"active");
        super.onActivityCreated(savedInstanceState);

        //不堪用
        /*view = (ListView) getActivity().findViewById(R.id.listview_setup);
        //ListView 要顯示的內容

        //android.R.layout.simple_list_item_1 為內建樣式，還有其他樣式可自行研究
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, str);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(onClickListView);*/
    }

    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {


                Fragment_setup_editprofile fse = new Fragment_setup_editprofile();

                FragmentManager fm = getFragmentManager();
                FragmentTransaction tx = fm.beginTransaction();

                tx.replace(R.id.id_content, fse, "setuptoprofile");
                tx.addToBackStack(null);
                //tx.hide(getActivity//());
                tx.commit();

                Log.e(TAG, "setuptoprofile");
            } else if (position == 1) {
                UserAccountDAO db_data=new UserAccountDAO(getActivity());
                List<UserAccount> items=db_data.getAll();
                for(UserAccount i:items){
                    Log.e(TAG,"SQLuserTYPE"+String.valueOf(i.getLoginRole()).toString());
                    //userType=String.valueOf(i.getUserType()).toString();
                }
                Toast.makeText(getActivity(),"關於app", Toast.LENGTH_SHORT).show();
            } else if (position == 2) {
                Toast.makeText(getActivity(),"登出功能", Toast.LENGTH_SHORT).show();
            }

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_txt_profile:
                Fragment_setup_editprofile fse = new Fragment_setup_editprofile();

                FragmentManager fm = getFragmentManager();
                FragmentTransaction tx = fm.beginTransaction();

                tx.replace(R.id.id_content, fse, "setuptoprofile");
                tx.addToBackStack(null);
                tx.hide(this);
                tx.commit();
                break;
            case R.id.id_txt_about:
                Fragment_setup_aboutapp fsa = new Fragment_setup_aboutapp();

                FragmentManager fmsa = getFragmentManager();
                FragmentTransaction txsa = fmsa.beginTransaction();

                txsa.replace(R.id.id_content, fsa, "setuptoabout");
                txsa.addToBackStack(null);
                txsa.hide(this);
                txsa.commit();

                Toast.makeText(getActivity(),"關於app", Toast.LENGTH_SHORT).show();
                break;
            case R.id.id_txt_logout:
                //Toast.makeText(getActivity(),"登出功能", Toast.LENGTH_SHORT).show();
                UserAccountDAO db_data = new UserAccountDAO(getActivity());
                List<UserAccount> items = db_data.getAll();

                for (UserAccount i : items) {
                    userType = String.valueOf(i.getLoginRole()).toString();
                    userId=String.valueOf(i.getUserId()).toString();
                }
                Log.e(TAG,userId);

                if(userType.equals("teacher")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("userId",userId);
                    NetTask netTask =  new NetTask();
                    netTask.initJSONObject(map);
                    netTask.setCommandType(CommandType.teacher_user_logout);
                    netTask.setActiveContext(getActivity());
                    netTask.execute();
                }else{

                    Map<String, String> map = new HashMap<String, String>();
                    map.put("userId",userId);

                    NetTask netTask =  new NetTask();
                    netTask.initJSONObject(map);
                    netTask.setCommandType(CommandType.student_user_logout);
                    netTask.setActiveContext(getActivity());
                    netTask.execute();
                    Log.e(TAG,"tt");
                }
                while(!ServerConnect.F_logout_flag){

                }Log.e(TAG,"pp");
                //ServerConnect.logout_imformation
                if (ServerConnect.logout_imformation) {
                    Log.e(TAG,"登出成功");
                    android.os.Process.killProcess(android.os.Process.myPid());

                }else{
                    Toast.makeText(getActivity(),"登出失敗", Toast.LENGTH_SHORT).show();
                }

                ServerConnect.F_logout_flag=false;
                ServerConnect.logout_imformation=false;
                break;
        }
    }
}
