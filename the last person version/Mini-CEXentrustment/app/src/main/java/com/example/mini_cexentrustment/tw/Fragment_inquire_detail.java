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
import com.example.mini_cexentrustment.define.CommandType;
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


    String startDatetime;
    String endDatetime;

    private Button btn_back;

    public ListView listView;
    public List<Item_detail> detail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_inquire_detail, container, false);
        listView=(ListView)view.findViewById(R.id.id_listview_result);

       /* Bundle bundle=this.getArguments();
        if (bundle != null) {
            //startDatetime=bundle.getString("startDateTime");
            //endDatetime=bundle.getString("endDateTime");
            Log.e(TAG, startDatetime);
        }else{
            Log.e(TAG, "fuck ???");
        }*/

        detail =new ArrayList<Item_detail>();
        //listView.setAdapter(new Frag_detail_adapter(this,detail));
        Get_detail();
        btn_back=(Button)view.findViewById(R.id.id_frg_detail_back);
        btn_back.setOnClickListener(this);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Log.e(TAG,"i really want to fu ck ");
                Toast toast = Toast.makeText(getActivity(),
                        "尚未有新增評量功能!", Toast.LENGTH_LONG);
                //顯示Toast
                toast.show();
                //新增評量
                return false;
            }
        });
        return view ;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }
    private void Get_detail() {

        Map<String, String> map = new HashMap<String, String>();
        map.put("teacherUserId","");
        map.put("teacherUserName","");
        map.put("teacherUserOrgId","");
        map.put("studentUserId","90b3a95a-b8cc-11e7-9eae-04012dec4e01");
        map.put("studentUserName","");
        map.put("studentUserOrgId","");
        map.put("divison","");
        map.put("startDateTime","2017-01-01 00:00:00");
        map.put("endDateTime", "2017-11-05 23:00:00");
        map.put("status","60");
        //map.put("divison",subjectSNo);
        //map.put("startDateTime",string_startDateTime);
        //map.put("endDateTime", string_endDateTime);
        //map.put("status",selectedStatus);
        NetTask netTask =  new NetTask();
        netTask.initJSONObject(map);
        netTask.setCommandType(CommandType.student_get_result_list);
        netTask.setActiveContext(getActivity());
        netTask.execute();
        while(!ServerConnect.F_detail_flag){

        }
        GettheDetail(ServerConnect.detail_imformation);
    }

    private void GettheDetail(String jsonData) {
        Log.e(TAG,"getthedetail");

        try {
            JSONArray jsonArray = new JSONArray(jsonData);

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

