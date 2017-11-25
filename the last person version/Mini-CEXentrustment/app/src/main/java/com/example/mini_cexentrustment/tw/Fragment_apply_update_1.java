package com.example.mini_cexentrustment.tw;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mini_cexentrustment.R;
import com.example.mini_cexentrustment.dao.UserAccountDAO;
import com.example.mini_cexentrustment.define.CommandType;
import com.example.mini_cexentrustment.define.UserAccount;
import com.example.mini_cexentrustment.define.UserEvaluation;
import com.example.mini_cexentrustment.network.ServerConnect;
import com.example.mini_cexentrustment.thread.NetTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rorensu on 2017/11/15.
 */

public class Fragment_apply_update_1 extends Fragment implements View.OnClickListener{

    private static final String TAG = Fragment_apply_update_1.class.getSimpleName();
    private static UserEvaluation UserEvaluation;
    private Button mbtn;
    private TextView txtV;
    public ListView listView;
    public String[] item_docutmentNo = {null};
    public String[] teacherId_s = {null};
    private List<String> allCountries;
    private Button next_btn,back_btn;
    private int eva_teacher_i = 1;
    private ArrayAdapter<String> adapter;
    String regEx="[^0-9]";
    View v;
    ListView lv;
    String userId="";
    private int ss;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_apply_1, container, false);
        Log.d("FRAG", "onCreateView");
        return view ;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
        boolean b = false;

        next_btn = (Button) getView().findViewById(R.id.apply_1_send);
        next_btn.setOnClickListener(this);
        back_btn = (Button) getView().findViewById(R.id.apply_1_back);
        back_btn.setOnClickListener(this);
        String item = null;

        UserAccountDAO db_data = new UserAccountDAO(getActivity());
        List<UserAccount> items = db_data.getAll();
        for (UserAccount i : items) {
            userId = String.valueOf(i.getUserId()).toString();
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("studentUserId", userId); //userId
        NetTask netTask =  new NetTask();
        netTask.initJSONObject(map);
        netTask.setCommandType(CommandType.student_get_request_setting);
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

        final Spinner spinner_apply_1 = (Spinner) getView().findViewById(R.id.apply_1_spin);
        ListView list = (ListView) getView().findViewById(R.id.fragment_apply_id);

        Matcher m;
        TextView evaluation_status = (TextView) getActivity().findViewById(R.id.testtest);
        //evaluation_status.setTextColor(getResources().getColor(R.color.md_blue_100));//blue
        JSONObject jsonObject = null;
        ServerConnect test=new ServerConnect();
        while(!test.student_get_request_setting_b){}
        test.student_get_request_setting_b = false;



        try {
//將資料寫入JSONArray
            JSONArray modFamilyJSONArray = new JSONArray(test.Test);
            item_docutmentNo = new String[modFamilyJSONArray.length()];
            teacherId_s = new String[modFamilyJSONArray.length()];
            //取出陣列內所有物件
            for(int i = 0;i < modFamilyJSONArray.length(); i++){
                //取出JSON物件
                JSONObject modFamily = modFamilyJSONArray.getJSONObject(i);
                //取得物件內資料
                String request_userId = modFamily.get("userId").toString(); // 學生新聞:內容
                String request_userName = modFamily.get("userName").toString(); // 學生新聞:內容
                item_docutmentNo[i] = request_userName;
                teacherId_s[i] = request_userId;
                Log.i(TAG,"request_documentSNo:"+request_userId);
                Log.i(TAG,"request_GoupSNo:"+request_userName);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        /*
        final JSONArray jsonArray = new JSONArray(test.Test);
        //JSONObject jsonObject = new JSONObject(jsonData);
        //JSONArray jsonArray = jsonObject.getJSONArray("data");

        item_docutmentNo = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {

            //populate arraylist with json array data
            jsonObject = jsonArray.getJSONObject(i);
            String request_userId = jsonObject.get("userId").toString(); // 學生新聞:內容
            String request_userName = jsonObject.get("userName").toString(); // 學生新聞:內容
            item_docutmentNo[i] = request_userName;
            teacherId_s[i] = request_userId;
            Log.i(TAG,"request_userId:"+request_userId);
            Log.i(TAG,"request_userName:"+request_userName);
        }
    */
        //建立存放HashMap資訊的ArrayList物件
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        //將資料轉換成HashMap型態存進ListView裡
        //Boolean document_type[] = {false};
        //adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,new String[]{"紅茶","奶茶","綠茶"})
        ArrayAdapter<String> lunchList = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,item_docutmentNo);
        //ArrayAdapter<CharSequence> adapter_level =  ArrayAdapter.createFromResource(getActivity(), R.array.level, android.R.layout.simple_spinner_item);

        lunchList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_apply_1.setAdapter(lunchList);
        spinner_apply_1.setOnItemSelectedListener(new Fragment_apply_update_1.SpinnerSelectedListener_apply_1());
        spinner_apply_1.setVisibility(View.VISIBLE);

        return 10;
    }
    class SpinnerSelectedListener_apply_1 implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            eva_teacher_i = arg2 + 1;
        }
        public void onNothingSelected(AdapterView<?> arg0) {
            Log.e(TAG,"onNothingSelected");
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
        Log.d("TAG", "onClick");
        switch (v.getId()) {
            case R.id.apply_1_send:
                Map<String, String> map = new HashMap<String, String>();
                map.put("fromStudentUserId", "90b3a95a-b8cc-11e7-9eae-04012dec4e01"); //隨機8碼
                map.put("teacherUserId", teacherId_s[eva_teacher_i - 1]);
                NetTask netTask = new NetTask();
                netTask.initJSONObject(map);
                netTask.setCommandType(CommandType.student_get_evaluation_request_evaluation);
                netTask.setActiveContext(getActivity());
                netTask.execute();

                Fragment_apply_update fvu = new Fragment_apply_update();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction tx = fm.beginTransaction();
                tx.replace(R.id.id_content, fvu, "test");
                tx.addToBackStack(null);
                tx.commit();
                break;
            case R.id.apply_1_back:
                Fragment_apply_update fff = new Fragment_apply_update();
                FragmentManager fd = getFragmentManager();
                FragmentTransaction rr = fd.beginTransaction();
                rr.replace(R.id.id_content, fff, "test");
                rr.addToBackStack(null);
                rr.commit();
                break;
        }
    }

}
