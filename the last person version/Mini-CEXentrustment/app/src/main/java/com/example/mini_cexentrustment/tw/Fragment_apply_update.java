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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rorensu on 2017/11/15.
 */

public class Fragment_apply_update extends Fragment implements View.OnClickListener{

    private static final String TAG = Fragment_apply_update.class.getSimpleName();
    private static com.example.mini_cexentrustment.define.UserEvaluation UserEvaluation;
    private Button mbtn;
    private TextView txtV;
    public ListView listView;
    private Button next_btn;
    String userId="";
    private int item11 = 1,item12 = 1;
    private ArrayAdapter<String> adapter;
    String regEx="[^0-9]";
    View v;
    ListView lv;
    private int ss;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_apply, container, false);
        Log.d("FRAG", "onCreateView");
        return view ;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
        boolean b = false;
        next_btn = (Button) getView().findViewById(R.id.apply_btn);
        next_btn.setOnClickListener(this);
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
        netTask.setCommandType(CommandType.student_get_request_list);
        netTask.setActiveContext(getActivity());
        netTask.execute();
        Log.e(TAG,"asdf");




        try {
            ss = GetNews();
        } catch (JSONException e) {
            Log.e("MYAPP", "unexpected JSON exception", e);
            // Do something to recover ... or kill the app.
        }




    }
    public Integer GetNews() throws JSONException {

        ListView list = (ListView) getView().findViewById(R.id.fragment_apply_id);

        Matcher m;
        TextView evaluation_status = (TextView) getActivity().findViewById(R.id.testtest);
        //evaluation_status.setTextColor(getResources().getColor(R.color.md_blue_100));//blue
        JSONObject jsonObject = null;
        ServerConnect test=new ServerConnect();

        while(!test.student_get_request_list_b){}

        test.student_get_request_list_b = false;

        Pattern p = Pattern.compile(regEx);

        final JSONArray jsonArray = new JSONArray(test.Test);
        //JSONObject jsonObject = new JSONObject(jsonData);
        //JSONArray jsonArray = jsonObject.getJSONArray("data");

        Log.e(TAG,"fdsa tt");
        Log.e(TAG,test.Test);
        for (int i = 0; i < jsonArray.length(); i++) {
            //populate arraylist with json array data
            jsonObject = jsonArray.getJSONObject(i);
            Log.i(TAG,"get into teach request list");
            String request_documentSNo = jsonObject.get("documentSNo").toString(); // 學生新聞:內容
            String request_GoupSNo = jsonObject.get("groupSNo").toString(); //學生新聞:訊息類別
            String request_GroupName = jsonObject.get("groupName").toString(); //學生新聞:表單流水號
            String request_teacherUserId = jsonObject.get("teacherUserId").toString(); //學生新聞:日期
            String request_teacherName = jsonObject.get("teacherName").toString(); //學生新聞:日期
            String request_studentUserId = jsonObject.get("studentUserId").toString(); //學生新聞:日期
            String request_studentName = jsonObject.get("studentName").toString(); //學生新聞:日期
            String request_status = jsonObject.get("status").toString(); //學生新聞:日期
            String request_modifyDateTime = jsonObject.get("modifyDateTime").toString(); //學生新聞:日期
            Log.i(TAG,"request_documentSNo:"+request_documentSNo);
            Log.i(TAG,"request_GoupSNo:"+request_GoupSNo);
            Log.i(TAG,"request_teacherUserId:"+request_teacherUserId);
            Log.i(TAG,"request_GroupName:"+request_GroupName);
            Log.i(TAG,"request_teacherName:"+request_teacherName);
            Log.i(TAG,"request_studentUserId:"+request_studentUserId);
            Log.i(TAG,"request_studentName:"+request_studentName);
            Log.i(TAG,"request_status :"+request_status);
            Log.i(TAG,"request_modifyDateTime"+request_modifyDateTime);
        }
        Log.e(TAG,"fdsa");
        //建立存放HashMap資訊的ArrayList物件
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        Log.i(TAG,"test :"+jsonArray.length());
        //將資料轉換成HashMap型態存進ListView裡
        final int[] item_status; // x is a reference to int[]
        item_status = new int[jsonArray.length()]; // 利用new指令產生物件
        final String[] item_docutmentNo;
        item_docutmentNo = new String[jsonArray.length()];
        for(int i = 0; i < jsonArray.length(); i++){
            HashMap<String, Object> map = new HashMap<String, Object>();
            String textcolor = jsonArray.getJSONObject(i).get("status").toString();
            item_status[i] = Integer.parseInt(textcolor);
            item_docutmentNo[i] = jsonArray.getJSONObject(i).get("documentSNo").toString();
            map.put("evaluation_status", jsonArray.getJSONObject(i).get("teacherName"));//Img

            map.put("status", jsonArray.getJSONObject(i).get("status").toString());//Title
            map.put("ItemDate", jsonArray.getJSONObject(i).get("modifyDateTime"));
            listItem.add(map);
        }

        SimpleAdapter listItemAdapter = new SimpleAdapter(getActivity(),listItem, //套入動態資訊
                R.layout.list_item,//套用自訂的XML
                new String[] {"evaluation_status","status","ItemDate"}, //動態資訊取出順序
                new int[] {R.id.news_content_id, R.id.news_newsType_id, R.id.news_dateTime_id}){
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                View itemView = super.getView(position, convertView, parent);
                TextView text = (TextView) itemView.findViewById(R.id.news_newsType_id);

                for(int i = 0; i < jsonArray.length(); i++) {
                    if(item_status[position] == 60) {
                        text.setTextColor(getResources().getColor(R.color.md_green_300));
                        text.setText("已評量");
                        item_status[position] = 60;
                    }else if(item_status[position] == 30) {
                        text.setText("待評量");
                        item_status[position] = 30;
                        text.setTextColor(getResources().getColor(R.color.md_red_400));
                    }
                    else if(item_status[position] == 10) {
                        text.setTextColor(getResources().getColor(R.color.md_blue_500));
                        text.setText("未輸入");
                        item_status[position] = 10;
                    }else if(item_status[position] == 20) {
                        text.setTextColor(getResources().getColor(R.color.md_black_100));
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
                if(item_status[arg2] == 10) {
                    Fragment_apply_update_2 fvu = new Fragment_apply_update_2();
                    bundle.putString("docutmentNo", item_docutmentNo[arg2]);
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction tx = fm.beginTransaction();
                    tx.replace(R.id.id_content, fvu,"test");
                    tx.addToBackStack(null);
                    fvu.setArguments(bundle);
                    tx.commit();

                    Toast.makeText(getActivity(), "點選第 " + (arg2) + " 個 \n內容：" + arg2, Toast.LENGTH_SHORT).show();
                }
                else if(item_status[arg2] == 30){
                    Fragment_apply_update_3 fvu = new Fragment_apply_update_3();
                    bundle.putString("docutmentNo", item_docutmentNo[arg2]);
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction tx = fm.beginTransaction();
                    tx.replace(R.id.id_content, fvu,"test");
                    tx.addToBackStack(null);
                    fvu.setArguments(bundle);
                    tx.commit();
                    Toast.makeText(getActivity(), "點選第 " + (arg2) + " 個 \n內容：" + arg2, Toast.LENGTH_SHORT).show();
                }
                /*
                else if(item_status[arg2] == 60){
                    Fragment_apply_update_2 fvu = new Fragment_apply_update_2();
                    bundle.putString("item", item_docutmentNo[arg2]);
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction tx = fm.beginTransaction();
                    tx.replace(R.id.id_content, fvu,"test");
                    tx.addToBackStack(null);
                    fvu.setArguments(bundle);
                    tx.commit();
                }
                */
            }
        });




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

        Fragment_apply_update_1 fvu = new Fragment_apply_update_1();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.replace(R.id.id_content, fvu,"test");
        tx.addToBackStack(null);
        fvu.setArguments(bundle);
        tx.commit();
    }

}
