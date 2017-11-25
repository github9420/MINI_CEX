package com.example.mini_cexentrustment.tw;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.mini_cexentrustment.R;

/**
 * Created by rorensu on 2017/11/22.
 */

public class Fragment_setup_aboutapp extends Fragment implements View.OnClickListener{
    private Button btn_back;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup_aboutapp, container, false);
        btn_back=(Button)view.findViewById(R.id.setup_aboutapp_back);
        btn_back.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Fragment_setup fse = new Fragment_setup();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.replace(R.id.id_content, fse, "abouttosetup");
        tx.addToBackStack(null);
        //tx.hide(this);
        tx.remove(this);
        tx.commit();
    }
}
