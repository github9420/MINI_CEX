package com.example.mini_cexentrustment.tw;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mini_cexentrustment.R;
import com.example.mini_cexentrustment.dao.UserAccountDAO;
import com.example.mini_cexentrustment.define.CommandType;
import com.example.mini_cexentrustment.define.UserAccount;
import com.example.mini_cexentrustment.network.ServerConnect;
import com.example.mini_cexentrustment.thread.NetTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rorensu on 2017/11/16.
 */

public class Fragment_inquire_detail extends Fragment implements View.OnClickListener{
    private final static String TAG = Fragment_inquire_detail.class.getSimpleName();


    String startDatetime="";
    String endDatetime="";
    String selectedStatus="";
    String teacherUserId="";
    String studentUserId="";
    String subjectSNo="";
    String userType="";
    private Button btn_back;

    public ListView listView;
    public List<Item_detail> detail;
    final Bundle bundle = new Bundle();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inquire_detail, container, false);
        listView = (ListView) view.findViewById(R.id.id_listview_result);

        Bundle bundleid = getArguments();
        if (bundleid != null) {
            startDatetime=bundleid.getString("startDateTime");
            endDatetime=bundleid.getString("endDateTime");

            selectedStatus=bundleid.getString("status");
            teacherUserId=bundleid.getString("teacherUserId");
            studentUserId=bundleid.getString("studentUserId");
            subjectSNo=bundleid.getString("subjectSNo");

            bundle.putString("subjectSNo",subjectSNo);
            Log.e(TAG, "thing");
        } else {
            Log.e(TAG, "test");
        }
        UserAccountDAO db_data = new UserAccountDAO(getActivity());
        List<UserAccount> items = db_data.getAll();
        for (UserAccount i : items) {
            userType = String.valueOf(i.getLoginRole()).toString();
        }

            detail = new ArrayList<Item_detail>();
            //listView.setAdapter(new Frag_detail_adapter(this,detail));
            Get_detail();
            btn_back = (Button) view.findViewById(R.id.id_frg_detail_back);
            btn_back.setOnClickListener(this);

        return  view;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }
    private void Get_detail() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("teacherUserId",teacherUserId);
        map.put("teacherUserName","");
        map.put("teacherUserOrgId","");
        map.put("studentUserId",studentUserId);
        map.put("studentUserName","");
        map.put("studentUserOrgId","");
        map.put("divison",subjectSNo);
        map.put("startDateTime",startDatetime);
        map.put("endDateTime", endDatetime);
//        map.put("startDateTime","2017-01-01 00:00:00");
//        map.put("endDateTime", "2017-11-05 23:00:00");
        map.put("status",selectedStatus);

        NetTask netTask =  new NetTask();
        netTask.initJSONObject(map);
        netTask.setCommandType(CommandType.student_get_result_list);
        netTask.setActiveContext(getActivity());
        netTask.execute();
        Log.e(TAG,"before while");
        while(!ServerConnect.F_detail_flag){

        }
        ServerConnect.F_detail_flag=false;
        GettheDetail(ServerConnect.detail_imformation);
    }

    private void GettheDetail(String jsonData) {
        Log.e(TAG,"getthedetail");
        int jslength=0;
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            // x is a reference to int[]
            jslength=jsonArray.length(); // 利用new指令產生物件
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                detail.add(new Item_detail(jsonObject));
            }
            //populate listview with arraylist
            listView.setAdapter(new Frag_detail_adapter(getActivity(),detail));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG,"student_get_result_list end");
        //txt_result_imformation.setText(ServerConnect.Statisc_imformation);
        //ServerConnect.Statisc_imformation="";
         // x is a reference to int[]
        final int[] item_status;
        item_status = new int[jslength]; // 利用new指令產生物件
        final String[] item_docutmentNo;
        item_docutmentNo = new String[jslength];
        int x=0;
        for (Item_detail i : detail) {
            item_status[x]=Integer.valueOf(i.getStatus().toString());
            item_docutmentNo[x]=String.valueOf(i.getDocumentSNo());
            Log.i(TAG,"GG:"+ String.valueOf(i.getevaluateDateTime()).toString());
            Log.i(TAG,"GG:"+ String.valueOf(i.getevaluateDateTime()).toString());
            x++;
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2,
                                    long arg3) {

                if (userType.equals("student")) {

                    if(item_status[arg2] == 10) {
                        Fragment_apply_update_3 fvu = new Fragment_apply_update_3();
                        bundle.putString("item", item_docutmentNo[arg2]);
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction tx = fm.beginTransaction();
                        tx.replace(R.id.id_content, fvu,"test");
                        tx.addToBackStack(null);
                        fvu.setArguments(bundle);
                        tx.commit();

                        Toast.makeText(getActivity(), "點選第 " + (arg2) + " 個 \n內容：" + arg2, Toast.LENGTH_SHORT).show();
                    }
                    else if(item_status[arg2] == 60){
                        Fragment_apply_update_3 fvu = new Fragment_apply_update_3();
                        bundle.putString("docutmentNo", item_docutmentNo[arg2]);
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction tx = fm.beginTransaction();
                        tx.replace(R.id.id_content, fvu,"test");
                        tx.addToBackStack(null);
                        fvu.setArguments(bundle);
                        tx.commit();
                        Toast.makeText(getActivity(), "點選第 " + (arg2) + " 個 \n內容：" + arg2, Toast.LENGTH_SHORT).show();
                    }else{
                        Log.e(TAG,"nothing");
                    }


                }else{
                    if(item_status[arg2] == 10) {
                        Fragment_apply_update_3 fvu = new Fragment_apply_update_3();
                        bundle.putString("item", item_docutmentNo[arg2]);
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction tx = fm.beginTransaction();
                        tx.replace(R.id.id_content, fvu,"test");
                        tx.addToBackStack(null);
                        fvu.setArguments(bundle);
                        tx.commit();

                        Toast.makeText(getActivity(), "點選第 " + (arg2) + " 個 \n內容：" + arg2, Toast.LENGTH_SHORT).show();
                    }
                    else if(item_status[arg2] == 60){
                        Fragment_inquire_teacher_list fvu = new Fragment_inquire_teacher_list();
                        bundle.putString("docutmentNo", item_docutmentNo[arg2]);
                        bundle.putString("startDatetime", startDatetime);
                        bundle.putString("endDatetime", endDatetime);
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction tx = fm.beginTransaction();
                        tx.replace(R.id.id_content, fvu,"test");
                        tx.addToBackStack(null);
                        fvu.setArguments(bundle);
                        tx.commit();
                        Toast.makeText(getActivity(), "點選第 " + (arg2) + " 個 \n內容：" + arg2, Toast.LENGTH_SHORT).show();
                    }else{
                        Log.e(TAG,"nothing");
                    }


                }

/*
                Toast toast = Toast.makeText(getActivity(),
                        "尚未有新增評量功能!", Toast.LENGTH_LONG);
                //顯示Toast
                toast.show();*/
                //新增評量

            }
        });

        ServerConnect.F_detail_flag=false;
        ServerConnect.detail_imformation="";
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG,"hihi");
        Fragment_inquire_condition fvu = new Fragment_inquire_condition();

        FragmentManager ffm = getFragmentManager();
        FragmentTransaction txx = ffm.beginTransaction();

        txx.replace(R.id.id_content_inquire, fvu,"tocondition");
        txx.addToBackStack(null);
        txx.hide(this);
        txx.commit();
    }
}

