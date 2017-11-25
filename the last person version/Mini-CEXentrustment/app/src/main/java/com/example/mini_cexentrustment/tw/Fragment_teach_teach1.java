package com.example.mini_cexentrustment.tw;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.mini_cexentrustment.R;

/**
 * Created by rorensu on 2017/11/18.
 */

public class Fragment_teach_teach1 extends Fragment {
    private WebView webview_teach;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_teach_teach1, container, false);
        webview_teach=(WebView)view.findViewById(R.id.id_teach1_WebView);
//
//        webview_teach.clearCache(true);
//        WebSettings webSettings=webview_teach.getSettings();
//        webSettings.setJavaScriptEnabled(true);

        //Rorensu not finished
        return view ;
    }
}
