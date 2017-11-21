package com.example.mini_cexentrustment.tw;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;


import com.example.mini_cexentrustment.R;
import com.example.mini_cexentrustment.define.CommandType;
import com.example.mini_cexentrustment.thread.NetTask;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rorensu on 2017/11/16.
 */
// the fragment inquire condition of the student version
public class Fragment_inquire_condition extends Fragment implements View.OnClickListener{
    private final static String TAG = Fragment_inquire_condition.class.getSimpleName();
    private Button next_inquire_S;
    private EditText studentIDNum;
    private EditText teacherName;
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
        View view = inflater.inflate(R.layout.fragment_inquire_condition, container, false);

        initViews(view);
        return view ;
    }
    //initialize the viewobjec
    private void initViews(View view) {
        next_inquire_S=(Button) view.findViewById(R.id.id_btn_next_inquire_S);
        studentIDNum=(EditText) view.findViewById(R.id.id_edt_studentIdNum);
        teacherName=(EditText) view.findViewById(R.id.id_edt_teacherName);
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
    //startDateTime.
        //缺少calendar

        next_inquire_S.setOnClickListener(this);

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }
    @Override
    public void onClick(View v) {
        Log.e(TAG,"onclick");
        selectedStatus=eva_status.getSelectedItem().toString();
        teacherUserId=teacherName.getText().toString();
        studentUserId="";
        subjectSNo=subject.getText().toString();
        selectedStatus=eva_status.getSelectedItem().toString();


        bundle.putString("status",selectedStatus);
        bundle.putString("teacherUserId",teacherUserId);
        bundle.putString("studentUserId",studentUserId);
        bundle.putString("subjectSNo",subjectSNo);
        bundle.putString("endDateTime",string_endDateTime);


//        Fragment_inquire_statisc fvu = new Fragment_inquire_statisc();
//
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction tx = fm.beginTransaction();
//
//        tx.replace(R.id.id_content_inquire, fvu,"test");
//        tx.addToBackStack(null);
//        fvu.setArguments(bundle);
//        tx.commit();



        Fragment_inquire_statisc fvu = new Fragment_inquire_statisc();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();

        tx.replace(R.id.id_content_inquire, fvu,"test");
        tx.addToBackStack(null);
        fvu.setArguments(bundle);
        tx.commit();
        //apptemtGetStatisc();

    }

    private static String DateFix(int c){
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
}
