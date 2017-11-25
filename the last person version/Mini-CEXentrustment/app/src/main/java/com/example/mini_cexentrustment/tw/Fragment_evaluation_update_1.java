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
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.example.mini_cexentrustment.R;
import com.example.mini_cexentrustment.define.UserEvaluation;
import com.example.mini_cexentrustment.define.CommandType;
import com.example.mini_cexentrustment.network.ServerConnect;
import com.example.mini_cexentrustment.thread.NetTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rorensu on 2017/11/15.
 */

public class Fragment_evaluation_update_1 extends Fragment implements View.OnClickListener{

    private static final String TAG = Fragment_evaluation_update_1.class.getSimpleName();
    private static UserEvaluation UserEvaluation;
    private Button mbtn;
    private TextView txtV;
    public ListView listView;
    private Button next_btn,cancel_btn,back_btn;
    private String docutmentNo;
    private int item11 = 1,item12 = 1;
    private Spinner spinner;
    public boolean b = true,a = true,spin1 = false,spin2=false;
    private ArrayAdapter<String> adapter;
    private static final String[] ts={"A型","B型","O型","AB型","其他"};
    String regEx="[^0-9]";
    View v;
    ListView lv;
    private int ss;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_evaluation_update_1, container, false);
        Log.d("FRAG", "onCreateView");


        return view ;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
        boolean b = false;
        next_btn = (Button) getView().findViewById(R.id.evaluation_update_next_btn);
        next_btn.setOnClickListener(this);
        cancel_btn = (Button) getView().findViewById((R.id.evaluation_update_cancel_btn));
        cancel_btn.setOnClickListener(this);
        back_btn = (Button) getView().findViewById((R.id.evaluation_update_1_back));
        back_btn.setOnClickListener(this);
        String item = null;
        Matcher m;
        Pattern p = Pattern.compile(regEx);
        Bundle bundle = getArguments();
        if (bundle != null) {
            docutmentNo = bundle.getString("docutmentNo");
        }
        m = p.matcher((CharSequence) docutmentNo);
        Map<String, String> map = new HashMap<String, String>();
        String documentId = (m.replaceAll("").trim());
        map.put("documentSNo", documentId); //userId
        NetTask netTask =  new NetTask();
        netTask.initJSONObject(map);
        netTask.setCommandType(CommandType.teacher_request_record);
        netTask.setActiveContext(getActivity());
        netTask.execute();



        try {
            ss = GetNews();
        } catch (JSONException e) {
            Log.e("MYAPP", "unexpected JSON exception", e);
            // Do something to recover ... or kill the app.
        }




    }
    public Integer GetNews() throws JSONException {

        ListView list = (ListView) getView().findViewById(R.id.fragment_evaluation_id);
        Spinner spinner_level = (Spinner) getView().findViewById(R.id.evaluation_update_level);
        Spinner spinner_perform = (Spinner) getView().findViewById(R.id.evaluation_update_perform);
        Matcher m;
        String teacher_request_GroupSNo;
        String teacher_request_GroupName;
        String teacher_request_teacherUserId;
        String teacher_request_teacherUserName;
        String teacher_request_studentUserId;
        String teacher_request_studentUserName;
        String teacher_request_teacherType;
        String teacher_request_studentType;
        String teacher_request_medicalNumber;
        String teacher_request_division;
        String teacher_request_locationType;
        String teacher_request_locationTypeOther;
        String teacher_request_diagnosis;
        String teacher_request_comment;
        String teacher_request_recordFilePath;
        String teacher_request_status;
        String teacher_request_requestDateTime;
        String teacher_request_evaluateDateTime;
        String teacher_request_modifyDateTime;
        TextView evaluation_update_apply_student = (TextView) getView().findViewById(R.id.evaluation_update_apply_student);
        TextView evaluation_update_apply_time = (TextView) getView().findViewById(R.id.evaluation_update_apply_time);
        TextView evaluation_update_run_type = (TextView) getView().findViewById(R.id.evaluation_update_run_type);
        TextView evaluation_update_number = (TextView) getView().findViewById(R.id.evaluation_update_number);
        TextView evaluation_update_evaluation_item = (TextView) getView().findViewById(R.id.evaluation_update_evaluation_item);
        TextView evaluation_update_location = (TextView) getView().findViewById(R.id.evaluation_update_location);
        TextView evaluation_update_state = (TextView) getView().findViewById(R.id.evaluation_update_state);

        JSONObject jsonObject = null;
        ServerConnect test=new ServerConnect();
        while(!test.fuck2){}

        test.fuck2 = false;

        final JSONArray jsonArray = new JSONArray(test.Test);
        jsonObject = jsonArray.getJSONObject(0);
        //jsonObject = new JSONObject(test.Test);;
        //JSONObject jsonObject = new JSONObject(jsonData);
        //JSONArray jsonArray = jsonObject.getJSONArray("data");


        //for (int i = 0; i < jsonArray.length(); i++)

        Log.i(TAG, "get into teach request record");
        teacher_request_GroupSNo = jsonObject.get("groupSNo").toString(); //學生新聞:訊息類別
        teacher_request_GroupName= jsonObject.get("groupName").toString(); //學生新聞:表單流水號
        teacher_request_teacherUserId = jsonObject.get("teacherUserId").toString(); //學生新聞:日期
        // = jsonObject.get("teacherUserName").toString(); // 學生新聞:內容
        teacher_request_studentUserId = jsonObject.get("studentUserId").toString(); //學生新聞:訊息類別
        teacher_request_studentUserName = jsonObject.get("studentUserName").toString(); //學生新聞:表單流水號
        teacher_request_teacherType = jsonObject.get("teacherType").toString(); //學生新聞:日期
        teacher_request_studentType = jsonObject.get("studentType").toString(); //學生新聞:日期
        teacher_request_medicalNumber = jsonObject.get("medicalNumber").toString(); //學生新聞:日期
        teacher_request_division = jsonObject.get("division").toString(); //學生新聞:日期
        teacher_request_locationType = jsonObject.get("locationType").toString(); //學生新聞:日期
        teacher_request_locationTypeOther = jsonObject.get("locationTypeOther").toString(); //學生新聞:日期
        teacher_request_diagnosis = jsonObject.get("diagnosis").toString(); //學生新聞:日期
        String teacher_request_item1_1 = jsonObject.get("item1_1").toString(); //學生新聞:日期
        String teacher_request_item1_2 = jsonObject.get("item1_2").toString(); //學生新聞:日期
        String teacher_request_item2_1 = jsonObject.get("item2_1").toString(); //學生新聞:日期
        String teacher_request_item2_2 = jsonObject.get("item2_2").toString(); //學生新聞:日期
        String teacher_request_item2_3 = jsonObject.get("item2_3").toString(); //學生新聞:日期
        String teacher_request_item2_4 = jsonObject.get("item2_4").toString(); //學生新聞:日期
        String teacher_request_item2_5 = jsonObject.get("item2_5").toString(); //學生新聞:日期
        String teacher_request_item2_6 = jsonObject.get("item2_6").toString(); //學生新聞:日期
        teacher_request_comment = jsonObject.get("comment").toString(); //學生新聞:日期
        teacher_request_recordFilePath = jsonObject.get("recordFilePath").toString(); //學生新聞:日期
        teacher_request_status = jsonObject.get("status").toString(); //學生新聞:日期
        teacher_request_requestDateTime = jsonObject.get("requestDateTime").toString(); //學生新聞:日期
        teacher_request_evaluateDateTime = jsonObject.get("evaluateDateTime").toString(); //學生新聞:日期
        teacher_request_modifyDateTime = jsonObject.get("modifyDateTime").toString(); //學生新聞:日期
        Log.i(TAG, "teacher_request_GroupSNo:" + teacher_request_GroupSNo);
        Log.i(TAG, "teacher_request_GroupName:" + teacher_request_GroupName);
        Log.i(TAG, "teacher_request_teacherUserId:" + teacher_request_teacherUserId);
        //Log.i(TAG, "teacher_request_teacherUserName:" + teacher_request_teacherUserName);
        Log.i(TAG, "teacher_request_studentUserId:" + teacher_request_studentUserId);
        Log.i(TAG, "teacher_request_studentUserName:" + teacher_request_studentUserName);
        Log.i(TAG, "teacher_request_teacherType:" + teacher_request_teacherType);
        Log.i(TAG, "teacher_request_studentType:" + teacher_request_studentType);
        Log.i(TAG, "teacher_request_medicalNumber:" + teacher_request_medicalNumber);
        Log.i(TAG, "teacher_request_division:" + teacher_request_division);
        Log.i(TAG, "teacher_request_locationType:" + teacher_request_locationType);
        Log.i(TAG, "teacher_request_locationTypeOther:" + teacher_request_locationTypeOther);
        Log.i(TAG, "teacher_request_diagnosis:" + teacher_request_diagnosis);
        Log.i(TAG, "teacher_request_item1_1:" + teacher_request_item1_1);
        Log.i(TAG, "teacher_request_item1_2:" + teacher_request_item1_2);
        Log.i(TAG, "teacher_request_item2_1:" + teacher_request_item2_1);
        Log.i(TAG, "teacher_request_item2_2:" + teacher_request_item2_2);
        Log.i(TAG, "teacher_request_item2_3:" + teacher_request_item2_3);
        Log.i(TAG, "teacher_request_item2_4:" + teacher_request_item2_4);
        Log.i(TAG, "teacher_request_item2_5:" + teacher_request_item2_5);
        Log.i(TAG, "teacher_request_item2_6:" + teacher_request_item2_6);
        Log.i(TAG, "teacher_request_comment:" + teacher_request_comment);
        Log.i(TAG, "teacher_request_recordFilePath:" + teacher_request_recordFilePath);
        Log.i(TAG, "teacher_request_status:" + teacher_request_status);
        Log.i(TAG, "teacher_request_requestDateTime:" + teacher_request_requestDateTime);
        Log.i(TAG, "teacher_request_evaluateDateTime:" + teacher_request_evaluateDateTime);
        Log.i(TAG, "teacher_request_modifyDateTime:" + teacher_request_modifyDateTime);


        evaluation_update_apply_student.setText("申請學生 : "+teacher_request_studentUserName);
        evaluation_update_apply_time.setText("申請時間 : " + teacher_request_requestDateTime);
        evaluation_update_run_type.setText("執行科別 : " + teacher_request_division);
        evaluation_update_number.setText("病歷號 : " + teacher_request_medicalNumber);
        evaluation_update_evaluation_item.setText("評估項目 : " + teacher_request_diagnosis);
        int teacher_request_locationType_i = Integer.valueOf(teacher_request_locationType);
        if(teacher_request_locationType_i == 1){
            evaluation_update_location.setText("執行地點 : 門診");
        }else if(teacher_request_locationType_i == 2){
            evaluation_update_location.setText("執行地點 : 急診");
        }else if(teacher_request_locationType_i == 3) {
            evaluation_update_location.setText("執行地點 : 病房");
        }else if(teacher_request_locationType_i == 4) {
            evaluation_update_location.setText("執行地點 : 加護病房");
        }else{
            evaluation_update_location.setText("執行地點 : 其他");
        }
        //evaluation_update_location.setText("執行地點 : "+teacher_request_locationType);
        evaluation_update_state.setText("病人狀況或診斷 : "+teacher_request_comment);

        ArrayAdapter<CharSequence> adapter_level =  ArrayAdapter.createFromResource(getActivity(), R.array.level, android.R.layout.simple_spinner_item);
        //adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,new String[]{"紅茶","奶茶","綠茶"})
        adapter_level.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_level.setAdapter(adapter_level);
        spinner_level.setOnItemSelectedListener(new SpinnerSelectedListener_level());
        spinner_level.setVisibility(View.VISIBLE);
        //spinner_level.setSelected(false);
        ArrayAdapter<CharSequence> adapter_perform =  ArrayAdapter.createFromResource(getActivity(), R.array.perform, android.R.layout.simple_spinner_item);
        //adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,new String[]{"紅茶","奶茶","綠茶"})
        adapter_perform.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_perform.setAdapter(adapter_perform);
        spinner_perform.setOnItemSelectedListener(new SpinnerSelectedListener_perform());
        spinner_perform.setVisibility(View.VISIBLE);




        return 10;
    }
    class SpinnerSelectedListener_level implements OnItemSelectedListener{
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            item11 = arg2 + 1;
        }
        public void onNothingSelected(AdapterView<?> arg0) {
            Log.e(TAG,"onNothingSelected");
        }
    }

    class SpinnerSelectedListener_perform implements OnItemSelectedListener{
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            item12 = arg2 + 1;
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        Log.d("FRAG", "onResume");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("FRAG", "onDestroy");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("FRAG", "onDestroyView");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("FRAG", "onPause");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d("FRAG", "onStop");
    }
    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("FRAG", "onDetach");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.evaluation_update_next_btn:
                Log.d("TAG", "onClick");
                final Bundle bundle = new Bundle();
                bundle.putString("docutmentNo", docutmentNo);
                bundle.putInt("item11", item11);
                bundle.putInt("item12", item12);
                Fragment_evaluation_update_2 fvu = new Fragment_evaluation_update_2();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction tx = fm.beginTransaction();
                tx.replace(R.id.id_content, fvu, "test");
                tx.addToBackStack(null);
                fvu.setArguments(bundle);
                tx.commit();
                break;
            case R.id.evaluation_update_cancel_btn:
                Matcher m;
                Pattern p = Pattern.compile(regEx);
                m = p.matcher((CharSequence) docutmentNo);
                String documentId = (m.replaceAll("").trim());
                Map<String, String> map = new HashMap<String, String>();
                map.put("documentSNo", documentId); //userId
                NetTask netTask =  new NetTask();
                netTask.initJSONObject(map);
                netTask.setCommandType(CommandType.teacher_evaluation_reject_request);
                netTask.setActiveContext(getActivity());
                netTask.execute();



                Fragment_evaluation fvv = new Fragment_evaluation();
                FragmentManager fs = getFragmentManager();
                FragmentTransaction txx = fs.beginTransaction();
                txx.replace(R.id.id_content, fvv,"test");
                txx.addToBackStack(null);
                txx.commit();
                break;
            case R.id.evaluation_update_1_back:
                Fragment_evaluation fss = new Fragment_evaluation();
                FragmentManager fa = getFragmentManager();
                FragmentTransaction rww = fa.beginTransaction();
                rww.replace(R.id.id_content, fss,"test");
                rww.addToBackStack(null);
                rww.commit();
                break;

        }
    }
}
