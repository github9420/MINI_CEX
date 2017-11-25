package com.example.mini_cexentrustment.tw;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
 * Created by rorensu on 2017/11/16.
 */

public class Fragment_inquire_statisc extends Fragment implements View.OnClickListener {
    private final static String TAG = Fragment_inquire_statisc.class.getSimpleName();
    String teacherUserId = "";
    String studentUserId = "";
    String subjectSNo = "";
    String string_startDateTime = "";
    String string_endDateTime = "";
    String selectedStatus = "";

    private Button btn_back;
    private Button btn_next;
    private TextView txt_requst_condition;
    private TextView txt_result_imformation;
    private String userType = "";
    final Bundle bundle_static = new Bundle();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inquire_statisc, container, false);
        txt_requst_condition = (TextView) view.findViewById(R.id.id_txt_request_condition);
        txt_result_imformation = (TextView) view.findViewById(R.id.id_txt_result_imformation);
        btn_back = (Button) view.findViewById(R.id.id_btn_back);
        btn_next = (Button) view.findViewById(R.id.id_btn_next);
        btn_back.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            teacherUserId = bundle.getString("teacherUserId");
            studentUserId = bundle.getString("studentUserId");
            subjectSNo = bundle.getString("subjectSNo");
            string_startDateTime = bundle.getString("startDateTime");
            string_endDateTime = bundle.getString("endDateTime");
            selectedStatus = bundle.getString("status");

        } else {
            Log.e(TAG, "test1");
        }
        Get_calculation_result();
    }


    private void Get_calculation_result() {
        UserAccountDAO db_data = new UserAccountDAO(getActivity());
        List<UserAccount> items = db_data.getAll();
        for (UserAccount i : items) {
            userType = String.valueOf(i.getLoginRole()).toString();
        }


        if (userType.equals("student")) {
            Log.e(TAG,"role student");
            Map<String, String> map = new HashMap<String, String>();
            map.put("teacherUserId", teacherUserId);
            map.put("teacherUserName", "");
            map.put("teacherUserOrgId", "");
            map.put("studentUserId", studentUserId);
            map.put("studentUserName", "");
            map.put("studentUserOrgId", "");
            map.put("divison", subjectSNo);
            map.put("startDateTime", string_startDateTime);
            map.put("endDateTime", string_endDateTime);
//            map.put("startDateTime", "2017-01-01 00:00:00");
//            map.put("endDateTime", "2017-11-05 23:00:00");
            map.put("status", selectedStatus);
            //map.put("divison",subjectSNo);
            //map.put("startDateTime",string_startDateTime);
            //map.put("endDateTime", string_endDateTime);
            //map.put("status",selectedStatus);
            NetTask netTask = new NetTask();
            netTask.initJSONObject(map);
            netTask.setCommandType(CommandType.student_get_calculation_result);
            netTask.setActiveContext(getActivity());
            netTask.execute();
            while (!ServerConnect.F_statisc_flag) {

            }
            GettheData();
        } else if (userType.equals("teacher")) {
            Log.e(TAG,"role teacher");
            Map<String, String> map = new HashMap<String, String>();
            map.put("teacherUserId", teacherUserId);
            map.put("teacherUserName", "");
            map.put("teacherUserOrgId", "");
            map.put("studentUserId", "");
            map.put("studentUserName", "");
            map.put("studentUserOrgId", "");
            map.put("divison", "");
            map.put("startDateTime",string_startDateTime );
            map.put("endDateTime", string_endDateTime);
            map.put("status", selectedStatus);

            NetTask netTask = new NetTask();
            netTask.initJSONObject(map);
            netTask.setCommandType(CommandType.teacher_get_calculation_result);
            netTask.setActiveContext(getActivity());
            netTask.execute();
            while (!ServerConnect.F_statisc_flag) {

            }
            GettheData();
        } else {
            Log.e(TAG,"role nothing");
        };

    }
    private void GettheData() {
        Log.e(TAG, ServerConnect.Statisc_imformation);
        txt_result_imformation.setText(ServerConnect.Statisc_imformation);
        ServerConnect.Statisc_imformation = "";
        ServerConnect.F_statisc_flag = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_back:
                Log.e(TAG, "back");
                Fragment_inquire_condition fic = new Fragment_inquire_condition();

                FragmentManager fm = getFragmentManager();
                FragmentTransaction tx = fm.beginTransaction();

                tx.replace(R.id.id_content_inquire, fic, "test");
                tx.addToBackStack(null);
                tx.hide(this);
                tx.commit();

                break;
            case R.id.id_btn_next:
                Log.e(TAG, "next");
                Log.e(TAG, "back");
                Fragment_inquire_detail fvu = new Fragment_inquire_detail();

                FragmentManager ffm = getFragmentManager();
                FragmentTransaction txx = ffm.beginTransaction();

                txx.replace(R.id.id_content_inquire, fvu, "todetail");
                txx.addToBackStack(null);
                bundle_static.putString("startDateTime", string_startDateTime);
                bundle_static.putString("endDateTime", string_endDateTime);
                fvu.setArguments(bundle_static);
                txx.hide(this);
                txx.commit();
                break;
            default:
                break;
        }
    }
}
