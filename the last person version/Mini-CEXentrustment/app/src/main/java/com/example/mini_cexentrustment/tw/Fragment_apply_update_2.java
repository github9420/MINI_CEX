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
import android.widget.EditText;
import java.util.Date;

import android.widget.Spinner;
import   java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import android.widget.TextView;

import com.example.mini_cexentrustment.R;
import com.example.mini_cexentrustment.define.CommandType;
import com.example.mini_cexentrustment.thread.NetTask;

import org.json.JSONException;

/**
 * Created by rorensu on 2017/11/15.
 */

public class Fragment_apply_update_2 extends Fragment implements View.OnClickListener{

    private static final String TAG = Fragment_apply_update_2.class.getSimpleName();
    private Button save_btn,back_btn;
    private int location_int = 1;
    private String location_s = "1";
    private EditText number_edit,runType_edit,otherLocation_edit,patstate_edit;
    private TextView nowtime_txt;
    private String documentNo;
    View v;
    private int ss;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_apply_2, container, false);
        Log.d("FRAG", "onCreateView");
        return view ;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
        save_btn = (Button) getView().findViewById(R.id.apply_2_save_btn);
        save_btn.setOnClickListener(this);
        back_btn = (Button) getView().findViewById(R.id.apply_2_back);
        back_btn.setOnClickListener(this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            documentNo = bundle.getString("documentNo");
        }
        try {
            ss = GetNews();
        } catch (JSONException e) {
            Log.e("MYAPP", "unexpected JSON exception", e);
            // Do something to recover ... or kill the app.
        }

    }
    public Integer GetNews() throws JSONException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//取得現在時間
        Date dt=new Date();
//透過SimpleDateFormat的format方法將Date轉為字串
        String dts=sdf.format(dt);
        nowtime_txt = (TextView) getView().findViewById(R.id.apply_2_nowtime_id) ;
        nowtime_txt.setText(dts);
        number_edit = (EditText) getView().findViewById(R.id.apply_2_number);
        runType_edit = (EditText) getView().findViewById(R.id.apply_2_type);
        otherLocation_edit = (EditText) getView().findViewById(R.id.apply_2_otherLocation);
        patstate_edit = (EditText) getView().findViewById(R.id.apply_2_state);
        final Spinner spinner_apply_2_location = (Spinner) getView().findViewById(R.id.apply_2_location);
        ArrayAdapter<CharSequence> adapter_item_21 =  ArrayAdapter.createFromResource(getActivity(), R.array.location, android.R.layout.simple_spinner_item);
        adapter_item_21.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_apply_2_location.setAdapter(adapter_item_21);
        spinner_apply_2_location.setOnItemSelectedListener(new Fragment_apply_update_2.SpinnerSelectedListener_location());
        spinner_apply_2_location.setVisibility(View.VISIBLE);

        return 10;
    }
    class SpinnerSelectedListener_location implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            location_int = arg2 + 1;
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
        switch (v.getId()) {
            case R.id.apply_2_save_btn:
                Log.d("TAG", "onClick");
                Log.i(TAG, patstate_edit.getText().toString());
                Log.i(TAG, number_edit.getText().toString());
                Log.i(TAG, runType_edit.getText().toString());
                Log.i(TAG, otherLocation_edit.getText().toString());

                Map<String, String> map = new HashMap<String, String>();
                map.put("documentNo", documentNo); //userId
                map.put("medicalNumber", number_edit.getText().toString()); //userId
                map.put("division", runType_edit.getText().toString()); //userId
                map.put("locationType", String.valueOf(location_int)); //userId
                map.put("locationTypeOther", otherLocation_edit.getText().toString()); //userId
                map.put("Diagnosis", patstate_edit.getText().toString()); //userId
                NetTask netTask = new NetTask();
                netTask.initJSONObject(map);
                netTask.setCommandType(CommandType.student_evaluation_fill_evaluation_info);
                netTask.setActiveContext(getActivity());
                netTask.execute();
                Log.e(TAG, "asdf");
                Fragment_apply_update fvu = new Fragment_apply_update();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction tx = fm.beginTransaction();
                tx.replace(R.id.id_content, fvu, "test");
                tx.addToBackStack(null);
                tx.commit();
                break;
            case R.id.apply_2_back :
                Fragment_apply_update fff = new Fragment_apply_update();
                FragmentManager dd = getFragmentManager();
                FragmentTransaction ss = dd.beginTransaction();
                ss.replace(R.id.id_content, fff, "test");
                ss.addToBackStack(null);
                ss.commit();
                break;
        }

    }

}
