package com.example.mini_cexentrustment.tw;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mini_cexentrustment.R;

import org.w3c.dom.Text;

/**
 * Created by rorensu on 2017/11/15.
 */

public class BrandNewFg extends android.app.Fragment {
    private TextView txt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brandnew, null);
        txt=(TextView)view.findViewById(R.id.textView_fbn);
        return view;
    }
}
