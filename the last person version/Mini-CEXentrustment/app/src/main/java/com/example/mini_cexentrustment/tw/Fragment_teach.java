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
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewFragment;

import android.widget.TextView;

import com.example.mini_cexentrustment.R;

/**
 * Created by rorensu on 2017/11/15.
 */

public class Fragment_teach extends Fragment {
    private final static String TAG = Fragment_teach.class.getSimpleName();
    //private Button btn_teach1;
    // private Button btn_teach2;
    // private Button btn_teach3;

    //private Fragment_teach_teach1 mTab01;
    //private Fragment_teach_teach2 mTab02;
    //private Fragment_teach_teach3 mTab03;
    //private FragmentManager fragmentManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teach, container, false);

        return view;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        WebView wv = (WebView) getView().findViewById(R.id.teach_web);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new SwAWebClient());
        wv.loadUrl("http://140.128.68.202/learning.html");
    }
    private class SwAWebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }
}



