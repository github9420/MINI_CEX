package com.example.mini_cexentrustment.tw;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.mini_cexentrustment.R;
import com.example.mini_cexentrustment.dao.UserAccountDAO;
import com.example.mini_cexentrustment.define.CommandType;
import com.example.mini_cexentrustment.define.UserAccount;
import com.example.mini_cexentrustment.thread.NetTask;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rorensu on 2017/11/18.
 */

public class Fragment_inquire_condition_teacher extends Fragment implements View.OnClickListener{
    private final static String TAG = Fragment_inquire_condition_teacher.class.getSimpleName();
    private Button next_inquire;
    private EditText studentIDNum;
    private EditText studentName;
    private EditText subject;
    private DatePicker startDateTime;
    private DatePicker endDateTime;
    private Spinner eva_status;
    String Year,Mon,Day;
    String teacherUserId="";
    String studentUserId="";
    String subjectSNo="";
    String string_startDateTime="";
    String string_endDateTime="";
    String selectedStatus="";

    final Bundle bundle = new Bundle();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_inquire_condition_teacher, container, false);

        initViews(view);
        return view ;
    }

    private void initViews(View view) {
        next_inquire=(Button) view.findViewById(R.id.id_btn_bewxt_inquire);
        studentIDNum=(EditText) view.findViewById(R.id.id_edt_studentIdNum);
        studentName=(EditText) view.findViewById(R.id.id_edt_studentName);
        subject=(EditText) view.findViewById(R.id.id_edt_subject);
        eva_status=(Spinner) view.findViewById(R.id.id_spinner_evaStatus);
        final String[] Status = {"0", "20", "40", "60","80","100"};
        ArrayAdapter<String> StatusList = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                Status);
        eva_status.setAdapter(StatusList);

        startDateTime=(DatePicker)view.findViewById(R.id.id_startdate_picker);
        endDateTime=(DatePicker)view.findViewById(R.id.id_enddate_picker);

        Calendar TodayDate = Calendar.getInstance();    //透過Calendar取的資料
        int sYear = TodayDate.get(Calendar.YEAR);       //一開啟軟體即取得年的數值
        int sMon  = TodayDate.get(Calendar.MONTH) + 1;  //一開啟軟體即取得月的數值
        //月的起始是0，所以+1.
        int sDay = TodayDate.get(Calendar.DAY_OF_MONTH);//一開啟軟體即取得日的數值
        //將取得的數字轉成String.
        Year = DateFix(sYear);
        Mon  = DateFix(sMon);
        Day  = DateFix(sDay);

        startDateTime.init(TodayDate.get(Calendar.YEAR),TodayDate.get(Calendar.MONTH),
                TodayDate.get(Calendar.DAY_OF_MONTH),
                //DatePicker年月日更改後，會觸發作以下的事情。
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                        Year = DateFix(year);
                        Mon  = DateFix(monthOfYear + 1); //月的初始是0，所以先加 1。
                        Day  = DateFix(dayOfMonth);
                        string_startDateTime=Year+"-"+Mon+"-"+Day+" 00:00:00";
                        //Log.e(TAG,"time is:"+string_startDateTime);
                    }
                });

        endDateTime.init(TodayDate.get(Calendar.YEAR),TodayDate.get(Calendar.MONTH),
                TodayDate.get(Calendar.DAY_OF_MONTH),
                //DatePicker年月日更改後，會觸發作以下的事情。
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                        Year = DateFix(year);
                        Mon  = DateFix(monthOfYear + 1); //月的初始是0，所以先加 1。
                        Day  = DateFix(dayOfMonth);
                        string_endDateTime=Year+"-"+Mon+"-"+Day+" 23:00:00";
                        //Log.e(TAG,"time is:"+string_endDateTime);
                    }
                });


        //缺少calendar



        next_inquire.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.e(TAG,"teacher");
        //attemptGetStatisc();

        selectedStatus=eva_status.getSelectedItem().toString();

        UserAccountDAO db_data=new UserAccountDAO(getActivity());
        List<UserAccount> items=db_data.getAll();
        for(UserAccount i:items){
            teacherUserId=String.valueOf(i.getUserId()).toString();
        }

        studentUserId=studentIDNum.getText().toString();
        subjectSNo=subject.getText().toString();
        selectedStatus=eva_status.getSelectedItem().toString();
        subjectSNo=subject.getText().toString();

        bundle.putString("teacherUserId","4d61b315-8d59-400b-a329-727c64a72965");
        bundle.putString("teacherUserName","");
        bundle.putString("teacherUserOrgId","");
        bundle.putString("studentUserId",studentUserId);
        bundle.putString("studentUserName","");
        bundle.putString("studentUserOrgId","");
        bundle.putString("division","");
        bundle.putString("startDateTime",string_startDateTime);
        bundle.putString("endDateTime",string_endDateTime);
        bundle.putString("status",selectedStatus);

        Fragment_inquire_statisc fvu = new Fragment_inquire_statisc();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();

        tx.replace(R.id.id_content_inquire, fvu,"test");
        tx.addToBackStack(null);
        fvu.setArguments(bundle);
        tx.commit();
    }

    private void attemptGetStatisc() {
        //StudentVersion





        String startDateTime="";
        String endDateTime="";
        String selectedStatus=eva_status.getSelectedItem().toString();

        //Log.e(TAG,selectedStatus);
        Map<String, String> map = new HashMap<String, String>();
        map.put("teacherUserId", teacherUserId);
        map.put("studentUserId", studentUserId);
        map.put("subjectSNo", subjectSNo);
        map.put("startDateTime",startDateTime);
        map.put("endDateTime", endDateTime);
        map.put("departmentName",subjectSNo);
        map.put("status",selectedStatus);

//        NetTask netTask =  new NetTask();
//        netTask.initJSONObject(map);
//        netTask.setCommandType(CommandType.teacher_get_calculation_result);
//        netTask.setActiveContext(getActivity());
//        netTask.execute();

    }
    private static String DateFix(int c){
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

}
