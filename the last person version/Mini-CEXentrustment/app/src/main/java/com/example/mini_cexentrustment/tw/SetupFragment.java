package com.example.mini_cexentrustment.tw;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.mini_cexentrustment.R;
import android.app.ListFragment;
import android.widget.ListView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.util.Log;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
/**
 * Created by Carson_Ho on 16/5/23.
 */
public class SetupFragment extends Fragment


{
    public static final String TAG = "SetupFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup, null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView listview = (ListView) getActivity().findViewById(R.id.listview_setup);
        //ListView 要顯示的內容
        String[] str = {"編輯個人資料","關於APP","登出"};
        //android.R.layout.simple_list_item_1 為內建樣式，還有其他樣式可自行研究
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,str);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(onClickListView);
    }
    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Toast 快顯功能 第三個參數 Toast.LENGTH_SHORT 2秒  LENGTH_LONG 5秒
            Toast.makeText(getActivity(),"點選第 "+(position +1) +" 個 \n內容："+position, Toast.LENGTH_SHORT).show();
        }
    };

}

