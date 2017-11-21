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
import android.widget.ListView;
import android.widget.TextView;

import com.example.mini_cexentrustment.R;
import com.example.mini_cexentrustment.define.CommandType;
import com.example.mini_cexentrustment.define.UserEvaluation;
import com.example.mini_cexentrustment.network.ServerConnect;
import com.example.mini_cexentrustment.thread.NetTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rorensu on 2017/11/15.
 */

public class Fragment_apply_update_3 extends Fragment implements View.OnClickListener{

    private static final String TAG = Fragment_apply_update_3.class.getSimpleName();
    private static UserEvaluation UserEvaluation;
    private Button mbtn;
    private TextView name_txt,time_txt,type_txt,number_txt,evaitem_txt,location_txt,patstate_txt;
    private TextView level_txt,perform_txt,interview_txt,body_txt,skill_txt,teach_txt,judment_txt,efficency_txt,comment_txt;
    public ListView listView;
    private Button next_btn;
    private int item11 = 1,item12 = 1;
    private ArrayAdapter<String> adapter;
    String regEx="[^0-9]";
    View v;
    ListView lv;
    private int ss;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_apply_3, container, false);
        Log.d("FRAG", "onCreateView");
        return view ;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
        boolean b = false;
        Matcher m;
        Pattern p = Pattern.compile(regEx);
        String item = null;

        Bundle bundle = getArguments();
        if (bundle != null) {
            item = bundle.getString("item");
        }
        m = p.matcher((CharSequence) item);
        String documentId = (m.replaceAll("").trim());

        Map<String, String> map = new HashMap<String, String>();
        map.put("documentSNo", documentId); //userId
        NetTask netTask =  new NetTask();
        netTask.initJSONObject(map);
        netTask.setCommandType(CommandType.student_get_evaluation_record);
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
        JSONObject jsonObject = null;
        ServerConnect test=new ServerConnect();
        while(!test.student_get_evaluation_record_b){}
        test.student_get_evaluation_record_b = false;
        Pattern p = Pattern.compile(regEx);

        jsonObject = new JSONObject(test.Test);
        Log.i(TAG,test.Test);
        //JSONObject jsonObject = new JSONObject(jsonData);
        Log.i(TAG, "get into teach request record");
        String student_request_GroupSNo = jsonObject.get("groupSNo").toString(); //學生新聞:訊息類別
        String student_request_GroupName = jsonObject.get("groupName").toString(); //學生新聞:表單流水號
        String student_request_teacherUserId = jsonObject.get("teacherUserId").toString(); //學生新聞:日期
        String student_request_teacherUserName = jsonObject.get("teacherUserName").toString(); // 學生新聞:內容
        String student_request_studentUserId = jsonObject.get("studentUserId").toString(); //學生新聞:訊息類別
        String student_request_studentUserName = jsonObject.get("studentUserName").toString(); //學生新聞:表單流水號
        String student_request_teacherType = jsonObject.get("teacherType").toString(); //學生新聞:日期
        String student_request_studentType = jsonObject.get("studentType").toString(); //學生新聞:日期

        String student_request_MedicalNumber = jsonObject.get("medicalNumber").toString(); //學生新聞:訊息類別
        String student_request_Division = jsonObject.get("division").toString(); //學生新聞:表單流水號
        String student_request_locationType = jsonObject.get("locationType").toString(); //學生新聞:表單流水號
        String student_request_locationTypeOther = jsonObject.get("locationTypeOther").toString(); //學生新聞:日期
        String student_request_Diagnosis = jsonObject.get("diagnosis").toString(); //學生新聞:日期
        String student_request_item1_1 = jsonObject.get("item1_1").toString(); // 學生新聞:內容
        String student_request_item1_2 = jsonObject.get("item1_2").toString(); //學生新聞:訊息類別
        String student_request_item2_1 = jsonObject.get("item2_1").toString(); //學生新聞:表單流水號
        String student_request_item2_2 = jsonObject.get("item2_2").toString(); //學生新聞:日期
        String student_request_item2_3 = jsonObject.get("item2_3").toString(); //學生新聞:日期
        String student_request_item2_4 = jsonObject.get("item2_4").toString(); //學生新聞:訊息類別
        String student_request_item2_5 = jsonObject.get("item2_5").toString(); //學生新聞:表單流水號
        String student_request_item2_6 = jsonObject.get("item2_6").toString(); //學生新聞:日期
        String student_request_Comment = jsonObject.get("comment").toString(); // 學生新聞:內容
        String student_request_Status = jsonObject.get("status").toString(); //學生新聞:日期
        String student_request_RequestDateTime = jsonObject.get("requestDateTime").toString(); //學生新聞:表單流水號
        String student_request_EvaluateDateTime = jsonObject.get("evaluateDateTime").toString(); //學生新聞:日期
        String student_request_ModifyDateTime = jsonObject.get("modifyDateTime").toString(); //學生新聞:日期

        Log.i(TAG, "student_request_Comment:" + student_request_Comment);
        Log.i(TAG, "student_request_Diagnosis:" + student_request_Diagnosis);
        Log.i(TAG, "student_request_RequestDateTime:" + student_request_RequestDateTime);
        Log.i(TAG, "student_request_item2_4:" + student_request_item2_4);
        Log.i(TAG, "student_request_EvaluateDateTime:" + student_request_EvaluateDateTime);
        Log.i(TAG, "student_request_ModifyDateTime:" + student_request_ModifyDateTime);

        name_txt = (TextView) getView().findViewById(R.id.apply_3_studentName);
        name_txt.setText(student_request_studentUserName);
        time_txt = (TextView) getView().findViewById(R.id.apply_3_apply_time);
        time_txt.setText(student_request_RequestDateTime);
        type_txt = (TextView) getView().findViewById(R.id.apply_3_runType);
        type_txt.setText(student_request_Division);
        number_txt = (TextView) getView().findViewById(R.id.apply_3_number);
        number_txt.setText(student_request_MedicalNumber);
        evaitem_txt = (TextView) getView().findViewById(R.id.apply_3_evaItem);
        //evaitem_txt.setText();
        location_txt = (TextView) getView().findViewById(R.id.apply_3_location);
        location_txt.setText(student_request_locationType);
        patstate_txt = (TextView) getView().findViewById(R.id.apply_3_patState);
        //patstate_txt.setText();
        level_txt = (TextView) getView().findViewById(R.id.apply_3_level);
        level_txt.setText(student_request_item1_1);
        perform_txt = (TextView) getView().findViewById(R.id.apply_3_perform);
        perform_txt.setText(student_request_item1_2);
        interview_txt = (TextView) getView().findViewById(R.id.apply_3_interview);
        interview_txt.setText(student_request_item2_1);
        body_txt = (TextView) getView().findViewById(R.id.apply_3_body);
        body_txt.setText(student_request_item2_2);
        skill_txt = (TextView) getView().findViewById(R.id.apply_3_skill);
        skill_txt.setText(student_request_item2_3);
        teach_txt = (TextView) getView().findViewById(R.id.apply_3_teach);
        teach_txt.setText(student_request_item2_4);
        judment_txt = (TextView) getView().findViewById(R.id.apply_3_judment);
        judment_txt.setText(student_request_item2_5);
        efficency_txt = (TextView) getView().findViewById(R.id.apply_3_efficacy);
        efficency_txt.setText(student_request_item2_6);
        comment_txt = (TextView) getView().findViewById(R.id.apply_3_comment);
        comment_txt.setText(student_request_Comment);
        return 10;
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
        Log.d("TAG", "onClick");
        final Bundle bundle = new Bundle();
        bundle.putInt("item11", item11);
        bundle.putInt("item12", item12);
        Fragment_evaluation_update_2 fvu = new Fragment_evaluation_update_2();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.replace(R.id.id_content, fvu,"test");
        tx.addToBackStack(null);
        fvu.setArguments(bundle);
        tx.commit();
    }

}
