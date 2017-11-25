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
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mini_cexentrustment.R;
import com.example.mini_cexentrustment.define.CommandType;
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

public class Fragment_evaluation extends Fragment implements View.OnClickListener{

    private static final String TAG = Fragment_evaluation.class.getSimpleName();
    private Button mbtn;
    private TextView txtV;
    public ListView listView;
    String regEx="[^0-9]";
    View v;
    ListView lv;
    private int ss;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_evaluation, container, false);

        return view ;
    }


    @Override
    public void onClick(View v) {


    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Map<String, String> map = new HashMap<String, String>();
        map.put("teacherUserId", "4d61b315-8d59-400b-a329-727c64a72965"); //userId
        NetTask netTask =  new NetTask();
        netTask.initJSONObject(map);
        netTask.setCommandType(CommandType.teacher_request_list);
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

        Matcher m;
        TextView evaluation_status = (TextView) getActivity().findViewById(R.id.testtest);
        //evaluation_status.setTextColor(getResources().getColor(R.color.md_blue_100));//blue
        JSONObject jsonObject = null;
        ServerConnect test=new ServerConnect();
        while(!test.fuck1){}
        test.fuck1 = false;
        Pattern p = Pattern.compile(regEx);

        final JSONArray jsonArray = new JSONArray(test.Test);
        //JSONObject jsonObject = new JSONObject(jsonData);
        //JSONArray jsonArray = jsonObject.getJSONArray("data");


        for (int i = 0; i < jsonArray.length(); i++) {

            //populate arraylist with json array data
            jsonObject = jsonArray.getJSONObject(i);
            Log.i(TAG,"get into teach request list");
            String teacher_request_documentSNo = jsonObject.get("documentSNo").toString(); // 學生新聞:內容
            String teacher_request_GroupSNo = jsonObject.get("groupSNo").toString(); //學生新聞:訊息類別
            String teacher_request_GroupName = jsonObject.get("groupName").toString(); //學生新聞:表單流水號
            String teacher_request_teacherUserId = jsonObject.get("teacherUserId").toString(); //學生新聞:日期
            String teacher_request_teacherName = jsonObject.get("teacherName").toString(); // 學生新聞:內容
            String teacher_request_studentUserId = jsonObject.get("studentUserId").toString(); //學生新聞:訊息類別
            String teacher_request_studentName = jsonObject.get("studentName").toString(); //學生新聞:表單流水號
            String teacher_request_status = jsonObject.get("status").toString(); //學生新聞:日期
            String teacher_request_requestDateTime = jsonObject.get("requestDateTime").toString(); //學生新聞:日期
            Log.i(TAG,"teacher_request_documentSNo:"+teacher_request_documentSNo);
            Log.i(TAG,"teacher_request_GroupSNo:"+teacher_request_GroupSNo);
            Log.i(TAG,"teacher_request_GroupName:"+teacher_request_GroupName);
            Log.i(TAG,"teacher_request_teacherUserId:"+teacher_request_teacherUserId);
            Log.i(TAG,"teacher_request_teacherName:"+teacher_request_teacherName);
            Log.i(TAG,"teacher_request_studentUserId:"+teacher_request_studentUserId);
            Log.i(TAG,"teacher_request_studentName:"+teacher_request_studentName);
            Log.i(TAG,"teacher_request_status:"+teacher_request_status);
            Log.i(TAG,"teacher_request_requestDateTime:"+teacher_request_requestDateTime);

        }

        //建立存放HashMap資訊的ArrayList物件
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        Log.i(TAG,"test :"+jsonArray.length());
        //將資料轉換成HashMap型態存進ListView裡
        //Boolean document_type[] = {false};
        final int[] item_status; // x is a reference to int[]
        item_status = new int[jsonArray.length()]; // 利用new指令產生物件
        final String[] item_docutmentNo;
        item_docutmentNo = new String[jsonArray.length()];
        for(int i = 0; i < jsonArray.length(); i++){
            HashMap<String, Object> map = new HashMap<String, Object>();
            String textcolor = jsonArray.getJSONObject(i).get("status").toString();
            item_status[i] = Integer.parseInt(textcolor);
            item_docutmentNo[i] = jsonArray.getJSONObject(i).get("documentSNo").toString();
            map.put("status", jsonArray.getJSONObject(i).get("status"));//Img
            map.put("groupName", jsonArray.getJSONObject(i).get("groupName"));//Title

            map.put("requestDateTime", jsonArray.getJSONObject(i).get("requestDateTime"));
            listItem.add(map);
        }

        SimpleAdapter listItemAdapter = new SimpleAdapter(getActivity(),listItem, //套入動態資訊
                R.layout.list_item,//套用自訂的XML
                new String[] {"groupName","status","requestDateTime"}, //動態資訊取出順序

                new int[] {R.id.news_content_id,R.id.news_newsType_id,R.id.news_dateTime_id}){
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                View itemView = super.getView(position, convertView, parent);
                TextView text = (TextView) itemView.findViewById(R.id.news_newsType_id);
                for(int i = 0; i < jsonArray.length(); i++) {
                    if(item_status[position] == 60) {
                        text.setTextColor(getResources().getColor(R.color.md_black_10));
                        text.setText("已評量");
                        item_status[position] = 60;
                    }else if(item_status[position] == 30) {
                        text.setTextColor(getResources().getColor(R.color.md_red_400));
                        text.setText("待評量");
                        item_status[position] = 30;
                    }
                    else if(item_status[position] == 10) {
                        text.setTextColor(getResources().getColor(R.color.md_blue_400));
                        text.setText("未輸入");
                        item_status[position] = 10;
                    }else if(item_status[position] == 20) {
                        text.setTextColor(getResources().getColor(R.color.md_black_10));
                        text.setText("已取消");
                        item_status[position] = 20;
                    }else if(item_status[position] == 80) {
                        text.setTextColor(getResources().getColor(R.color.md_black_100));
                        text.setText("作廢");
                        item_status[position] = 80;
                    }
                }
                return itemView;
            }
        };

        final Bundle bundle = new Bundle();

        //執行SimpleAdapter
        list.setAdapter(listItemAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                if(item_status[arg2] == 30) {
                    bundle.putString("docutmentNo", item_docutmentNo[arg2]);
                    Fragment_evaluation_update_1 fvu = new Fragment_evaluation_update_1();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction tx = fm.beginTransaction();
                    tx.replace(R.id.id_content, fvu,"test");
                    tx.addToBackStack(null);
                    fvu.setArguments(bundle);
                    tx.commit();
                }

            }
        });



        return 10;
    }
}
