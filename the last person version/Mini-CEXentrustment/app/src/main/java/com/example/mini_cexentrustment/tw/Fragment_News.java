package com.example.mini_cexentrustment.tw;


import android.app.Fragment;
        import android.app.FragmentManager;
        import android.app.FragmentTransaction;
        import android.content.Context;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Toast;
        import java.util.*;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;
        import java.io.IOException;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.TextView;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;
        import com.example.mini_cexentrustment.R;
import com.example.mini_cexentrustment.dao.UserAccountDAO;
import com.example.mini_cexentrustment.define.CommandType;
import com.example.mini_cexentrustment.define.UserAccount;
import com.example.mini_cexentrustment.thread.NetTask;
        import com.example.mini_cexentrustment.network.ServerConnect;
        import android.widget.SimpleAdapter;
        import android.widget.AdapterView;

        import org.json.JSONArray;


/**
 * Created by rorensu on 2017/11/15.
 */

public class Fragment_News extends Fragment{

    private static final String TAG = Fragment_News.class.getSimpleName();
    private Button mbtn;
    private TextView txtV;
    public ListView listView;
    private String[] documentNo;
    String userId="";
    String userType="";

    String regEx="[^0-9]";
    View v;
    ListView lv;
    private int ss;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        return view ;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        UserAccountDAO db_data = new UserAccountDAO(getActivity());
        List<UserAccount> items = db_data.getAll();
        for (UserAccount i : items) {
            userType = String.valueOf(i.getLoginRole()).toString();
            userId = String.valueOf(i.getUserId()).toString();
        }

        if(userType.equals("teacher")) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", userId); //userId
            NetTask netTask = new NetTask();
            netTask.initJSONObject(map);
            netTask.setCommandType(CommandType.teacher_get_news);
            netTask.setActiveContext(getActivity());
            netTask.execute();
        }else if(userType.equals("student")){
            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", userId); //userId
            NetTask netTask = new NetTask();
            netTask.initJSONObject(map);
            netTask.setCommandType(CommandType.student_get_news);
            netTask.setActiveContext(getActivity());
            netTask.execute();
        }

        try {
            ss = GetNews();
        } catch (JSONException e) {
            Log.e("MYAPP", "unexpected JSON exception", e);
            // Do something to recover ... or kill the app.
        }



    }
    public Integer GetNews() throws JSONException {

        ListView list = (ListView) getView().findViewById(R.id.fragment_news_id);
        Matcher m;


        JSONObject jsonObject = null;
        ServerConnect test=new ServerConnect();
        while(!test.fuck){}
        test.fuck = false;
        Pattern p = Pattern.compile(regEx);

        final JSONArray jsonArray = new JSONArray(test.Test);
        //JSONObject jsonObject = new JSONObject(jsonData);
        //JSONArray jsonArray = jsonObject.getJSONArray("data");

        documentNo = new String[jsonArray.length()]; // 利用new指令產生物件
        for (int i = 0; i < jsonArray.length(); i++) {

            //populate arraylist with json array data
            jsonObject = jsonArray.getJSONObject(i);
            Log.i(TAG,"get into student get news");
            String news_content = jsonObject.get("content").toString(); // 學生新聞:內容
            String news_newsType = jsonObject.get("newsType").toString(); //學生新聞:訊息類別
            String news_documentSNo = jsonObject.get("documentSNo").toString(); //學生新聞:表單流水號
            documentNo[i] = news_documentSNo;
            String news_dateTime = jsonObject.get("dateTime").toString(); //學生新聞:日期
            Log.i(TAG,"news_content:"+news_content);
            Log.i(TAG,"news_newsType:"+news_newsType);
            Log.i(TAG,"news_documentSNo:"+news_documentSNo);
            Log.i(TAG,"news_dataTime:"+news_dateTime);
            Log.i(TAG,test.Test);

        }

        //建立存放HashMap資訊的ArrayList物件
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        Log.i(TAG,"test :"+jsonArray.length());
        //將資料轉換成HashMap型態存進ListView裡
        //Boolean document_type[] = {false};
        final Boolean[] document_type; // x is a reference to int[]
        document_type = new Boolean[jsonArray.length()]; // 利用new指令產生物件
        for(int i = 0; i < jsonArray.length(); i++){
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", jsonArray.getJSONObject(i).get("content"));//Img
            //map.put("ItemTitle", jsonArray.getJSONObject(i).get("newsType"));//Title
            map.put("ItemTitle", "");//Title
            m = p.matcher((CharSequence) jsonArray.getJSONObject(i).get("newsType"));
            if(Integer.parseInt(m.replaceAll("").trim()) == 30) {
                document_type[i] = true;
            }
            else {
                document_type[i] = false;
            }
            map.put("ItemDate", jsonArray.getJSONObject(i).get("dateTime"));
            listItem.add(map);
        }
        //document_type[2] = true;

        SimpleAdapter listItemAdapter = new SimpleAdapter(getActivity(),listItem, //套入動態資訊
                R.layout.list_item,//套用自訂的XML
                new String[] {"ItemImage","ItemTitle","ItemDate"}, //動態資訊取出順序
                new int[] {R.id.news_content_id,R.id.news_newsType_id,R.id.news_dateTime_id});

        //執行SimpleAdapter
        list.setAdapter(listItemAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                if(document_type[arg2]) {
                    Toast.makeText(getActivity(), "點選第 " + (arg2) + " 個 \n內容：" + arg2, Toast.LENGTH_SHORT).show();
                    final Bundle bundle1 = new Bundle();
                    bundle1.putString("docutmentNo", documentNo[arg2]);
                    Fragment_evaluation_update_1 fs = new Fragment_evaluation_update_1();
                    FragmentManager aa = getFragmentManager();
                    FragmentTransaction ss = aa.beginTransaction();
                    ss.replace(R.id.id_content, fs, "test");
                    ss.addToBackStack(null);
                    fs.setArguments(bundle1);
                    ss.commit();
                }
            }
        });



        return 10;
    }

}
