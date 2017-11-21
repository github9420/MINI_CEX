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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.example.mini_cexentrustment.R;
import com.example.mini_cexentrustment.define.CommandType;
import com.example.mini_cexentrustment.define.UserEvaluation;
import com.example.mini_cexentrustment.network.ServerConnect;
import com.example.mini_cexentrustment.thread.NetTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rorensu on 2017/11/15.
 */

public class Fragment_evaluation_update_2 extends Fragment implements View.OnClickListener{

    private static final String TAG = Fragment_evaluation_update_2.class.getSimpleName();
    private Button next_btn;
    private int item11,item12,item21=1,item22=1,item23=1,item24=1,item25=1,item26=1;
    private int item21_t,item22_t,item23_t,item24_t,item25_t,item26_t;
    private int ss;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_evaluation_update_2, container, false);
        Log.d(TAG, "onCreateView");
        return view ;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
        boolean b = false;
        next_btn = (Button) getView().findViewById(R.id.evaluation_update_2_next_btn);
        next_btn.setOnClickListener(this);


        Bundle bundle = getArguments();
        if (bundle != null) {
            item11 = bundle.getInt("item11");
            item12 = bundle.getInt("item12");
        }
        try {
            ss = GetNews();
        } catch (JSONException e) {
            Log.e("MYAPP", "unexpected JSON exception", e);
            // Do something to recover ... or kill the app.
        }




    }
    public Integer GetNews() throws JSONException {

        final Spinner spinner_item_21 = (Spinner) getView().findViewById(R.id.evaluation_update_2_spinner1);
        final Spinner spinner_item_22 = (Spinner) getView().findViewById(R.id.evaluation_update_2_spinner2);
        final Spinner spinner_item_23 = (Spinner) getView().findViewById(R.id.evaluation_update_2_spinner3);
        final Spinner spinner_item_24 = (Spinner) getView().findViewById(R.id.evaluation_update_2_spinner4);
        final Spinner spinner_item_25 = (Spinner) getView().findViewById(R.id.evaluation_update_2_spinner5);
        final Spinner spinner_item_26 = (Spinner) getView().findViewById(R.id.evaluation_update_2_spinner6);

        CheckBox checkbox_item21 = (CheckBox) getView().findViewById(R.id.evaluation_update_2_checkbox1);
        CheckBox checkbox_item22 = (CheckBox) getView().findViewById(R.id.evaluation_update_2_checkbox2);
        CheckBox checkbox_item23 = (CheckBox) getView().findViewById(R.id.evaluation_update_2_checkbox3);
        CheckBox checkbox_item24 = (CheckBox) getView().findViewById(R.id.evaluation_update_2_checkbox4);
        CheckBox checkbox_item25 = (CheckBox) getView().findViewById(R.id.evaluation_update_2_checkbox5);
        CheckBox checkbox_item26 = (CheckBox) getView().findViewById(R.id.evaluation_update_2_checkbox6);


        Log.i(TAG, "get into teach request record");

        ArrayAdapter<CharSequence> adapter_item_21 =  ArrayAdapter.createFromResource(getActivity(), R.array.item_evaluation, android.R.layout.simple_spinner_item);
        adapter_item_21.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_item_21.setAdapter(adapter_item_21);
        spinner_item_21.setOnItemSelectedListener(new SpinnerSelectedListener_item_21());
        spinner_item_21.setVisibility(View.VISIBLE);
        ArrayAdapter<CharSequence> adapter_item_22 =  ArrayAdapter.createFromResource(getActivity(), R.array.item_evaluation, android.R.layout.simple_spinner_item);
        adapter_item_22.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_item_22.setAdapter(adapter_item_22);
        spinner_item_22.setOnItemSelectedListener(new SpinnerSelectedListener_item_22());
        spinner_item_22.setVisibility(View.VISIBLE);

        ArrayAdapter<CharSequence> adapter_item_23 =  ArrayAdapter.createFromResource(getActivity(), R.array.item_evaluation, android.R.layout.simple_spinner_item);
        adapter_item_23.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_item_23.setAdapter(adapter_item_23);
        spinner_item_23.setOnItemSelectedListener(new SpinnerSelectedListener_item_23());
        spinner_item_23.setVisibility(View.VISIBLE);

        ArrayAdapter<CharSequence> adapter_item_24 =  ArrayAdapter.createFromResource(getActivity(), R.array.item_evaluation, android.R.layout.simple_spinner_item);
        adapter_item_24.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_item_24.setAdapter(adapter_item_24);
        spinner_item_24.setOnItemSelectedListener(new SpinnerSelectedListener_item_24());
        spinner_item_24.setVisibility(View.VISIBLE);

        ArrayAdapter<CharSequence> adapter_item_25 =  ArrayAdapter.createFromResource(getActivity(), R.array.item_evaluation, android.R.layout.simple_spinner_item);
        adapter_item_25.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_item_25.setAdapter(adapter_item_25);
        spinner_item_25.setOnItemSelectedListener(new SpinnerSelectedListener_item_25());
        spinner_item_25.setVisibility(View.VISIBLE);

        ArrayAdapter<CharSequence> adapter_item_26 =  ArrayAdapter.createFromResource(getActivity(), R.array.item_evaluation, android.R.layout.simple_spinner_item);
        adapter_item_26.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_item_26.setAdapter(adapter_item_26);
        spinner_item_26.setOnItemSelectedListener(new SpinnerSelectedListener_item_26());
        spinner_item_26.setVisibility(View.VISIBLE);

        checkbox_item21.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    spinner_item_21.setVisibility(View.INVISIBLE);
                    item21_t= item21;
                    item21 = 0;
                    Log.i(TAG,"item21" + item21);
                }else{
                    spinner_item_21.setVisibility(View.VISIBLE);
                    item21 = item21_t;
                    Log.i(TAG,"item21" + item21);
                }
            }
        });
        checkbox_item22.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    spinner_item_22.setVisibility(View.INVISIBLE);
                    item22_t= item22;
                    item22 = 0;
                    Log.i(TAG,"item22" + item22);
                }else{
                    spinner_item_22.setVisibility(View.VISIBLE);
                    item22 = item22_t;
                    Log.i(TAG,"item22" + item22);
                }
            }
        });
        checkbox_item23.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    spinner_item_23.setVisibility(View.INVISIBLE);
                    item23_t= item23;
                    item23 = 0;
                    Log.i(TAG,"item23" + item23);
                }else{
                    spinner_item_23.setVisibility(View.VISIBLE);
                    item23 = item23_t;
                    Log.i(TAG,"item23" + item23);
                }
            }
        });
        checkbox_item24.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    spinner_item_24.setVisibility(View.INVISIBLE);
                    item24_t= item24;
                    item24 = 0;
                    Log.i(TAG,"item24" + item24);
                }else{
                    spinner_item_24.setVisibility(View.VISIBLE);
                    item24 = item24_t;
                    Log.i(TAG,"item24" + item24);
                }
            }
        });
        checkbox_item25.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    spinner_item_25.setVisibility(View.INVISIBLE);
                    item25_t= item25;
                    item25 = 0;
                    Log.i(TAG,"item25" + item25);
                }else{
                    spinner_item_25.setVisibility(View.VISIBLE);
                    item25 = item25_t;
                    Log.i(TAG,"item25" + item25);
                }
            }
        });
        checkbox_item26.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    spinner_item_26.setVisibility(View.INVISIBLE);
                    item26_t= item26;
                    item26 = 0;
                    Log.i(TAG,"item26" + item26);
                }else{
                    spinner_item_26.setVisibility(View.VISIBLE);
                    item26 = item26_t;
                    Log.i(TAG,"item26" + item26);
                }
            }
        });



        return 10;
    }
    class SpinnerSelectedListener_item_21 implements OnItemSelectedListener{
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            item21 = arg2 + 1;
        }
        public void onNothingSelected(AdapterView<?> arg0) {
            Log.e(TAG,"onNothingSelected");
        }
    }

    class SpinnerSelectedListener_item_22 implements OnItemSelectedListener{
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            item22 = arg2 + 1;
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    class SpinnerSelectedListener_item_23 implements OnItemSelectedListener{
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            item23 = arg2 + 1;
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
    class SpinnerSelectedListener_item_24 implements OnItemSelectedListener{
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            item24 = arg2 + 1;
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
    class SpinnerSelectedListener_item_25 implements OnItemSelectedListener{
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            item25 = arg2 + 1;
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
    class SpinnerSelectedListener_item_26 implements OnItemSelectedListener{
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            item26 = arg2 + 1;
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }
    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick");
        final Bundle bundle = new Bundle();
        bundle.putInt("item11", item11);
        bundle.putInt("item12", item12);
        bundle.putInt("item21", item21);
        bundle.putInt("item22", item22);
        bundle.putInt("item23", item23);
        bundle.putInt("item24", item24);
        bundle.putInt("item25", item25);
        bundle.putInt("item26", item26);
        Fragment_evaluation_update_3 fvu = new Fragment_evaluation_update_3();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.replace(R.id.id_content, fvu,"test");
        tx.addToBackStack(null);
        fvu.setArguments(bundle);
        tx.commit();
    }
}
