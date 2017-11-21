package com.example.mini_cexentrustment.tw;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.mini_cexentrustment.R;

import org.json.JSONException;

/**
 * Created by rorensu on 2017/11/15.
 */

public class Fragment_evaluation_update_3 extends Fragment implements View.OnClickListener{

    private static final String TAG = Fragment_evaluation_update_3.class.getSimpleName();
    private Button send_btn,audio_btn;
    private int item11,item12,item21,item22,item23,item24,item25,item26;
    private int ss;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_evaluation_update_3, container, false);
        Log.d("TAG", "onCreateView");
        return view ;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("TAG", "onActivityCreated");
        audio_btn = (Button) getView().findViewById(R.id.evaluation_update_3_audio_btn);
        audio_btn.setOnClickListener(this);
        send_btn = (Button) getView().findViewById(R.id.evaluation_update_3_send_btn);
        send_btn.setOnClickListener(this);



        Bundle bundle = getArguments();
        if (bundle != null) {
            item11 = bundle.getInt("item11");
            item12 = bundle.getInt("item12");
            item21 = bundle.getInt("item21");
            item22 = bundle.getInt("item22");
            item23 = bundle.getInt("item23");
            item24 = bundle.getInt("item24");
            item25 = bundle.getInt("item25");
            item26 = bundle.getInt("item26");
        }

        try {
            ss = GetNews();
        } catch (JSONException e) {
            Log.e("MYAPP", "unexpected JSON exception", e);
            // Do something to recover ... or kill the app.
        }




    }
    public Integer GetNews() throws JSONException {

        return 10;
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
        Fragment_evaluation fvu = new Fragment_evaluation();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.replace(R.id.id_content, fvu,"test");
        tx.addToBackStack(null);
        tx.commit();

    }
}
