package com.example.mini_cexentrustment.tw;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mini_cexentrustment.R;
import com.example.mini_cexentrustment.define.CommandType;
import com.example.mini_cexentrustment.network.ServerConnect;
import com.example.mini_cexentrustment.thread.NetTask;

import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.security.auth.login.LoginException;

/**
 * Created by rorensu on 2017/11/15.
 */

public class Fragment_evaluation_update_3 extends Fragment implements View.OnClickListener{

    private static final String TAG = Fragment_evaluation_update_3.class.getSimpleName();
    private Button send_btn,audio_btn,stop_btn,back_btn;
    private String docutmentNo;
    private String eva_comment="";
    private int item11,item12,item21,item22,item23,item24,item25,item26;
    private int ss;
    String regEx="[^0-9]";


    private String outputFile="";
    private String encodeString="";
    private TextView txt;
    private final static int FLAG_WAV = 0;
    private int mState = -1;    //-1:，0：wav，1：amr
    private UIHandler uiHandler;
    private UIThread uiThread;
    private EditText comment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_evaluation_update_3, container, false);
        Log.d("TAG", "onCreateView");
        outputFile = Environment.getExternalStorageDirectory().
                getAbsolutePath() + "/FinalAudio.wav";
        init();
        return view ;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("TAG", "onActivityCreated");
        audio_btn = (Button) getView().findViewById(R.id.evaluation_update_3_audiostart_btn);
        audio_btn.setOnClickListener(this);
        stop_btn=(Button)getView().findViewById(R.id.evaluation_update_3_audiostop_btn);
        stop_btn.setOnClickListener(this);
        send_btn = (Button) getView().findViewById(R.id.evaluation_update_3_send_btn);
        send_btn.setOnClickListener(this);
        back_btn = (Button) getView().findViewById(R.id.evaluation_update_3_back);
        back_btn.setOnClickListener(this);

        txt = (TextView)getView().findViewById(R.id.evaluation3_status);
        comment=(EditText)getView().findViewById(R.id.evaluation_update_3_comment_edit) ;


        Bundle bundle = getArguments();
        if (bundle != null) {
            docutmentNo = bundle.getString("docutmentNo");
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
        switch(v.getId()){
            case R.id.evaluation_update_3_audiostart_btn:
                record(FLAG_WAV);
                Log.e(TAG,"start");
            break;
            case R.id.evaluation_update_3_audiostop_btn:
                stop();
                Log.e(TAG,"stop");
                upload();
                Log.e(TAG,"upload");
            break;
            case R.id.evaluation_update_3_send_btn:
                sendComand();
                Log.e(TAG,"evaluation_update_3_send_btn");
            break;
            case R.id.evaluation_update_3_back:
                final Bundle bundle1 = new Bundle();
                bundle1.putString("docutmentNo", docutmentNo);
                bundle1.putInt("itme11",item11);
                bundle1.putInt("itme12",item12);
                Fragment_evaluation_update_2 fs = new Fragment_evaluation_update_2();
                FragmentManager aa = getFragmentManager();
                FragmentTransaction ss = aa.beginTransaction();
                ss.replace(R.id.id_content, fs, "test");
                ss.addToBackStack(null);
                fs.setArguments(bundle1);
                ss.commit();
                break;


        }
        /*
        Log.d(TAG, "onClick");
        Fragment_evaluation fvu = new Fragment_evaluation();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.replace(R.id.id_content, fvu,"test");
        tx.addToBackStack(null);
        tx.commit();*/

    }

    public void sendComand() {
        Map<String, String> map = new HashMap<String, String>();
        Matcher m;
        Pattern p = Pattern.compile(regEx);
        m = p.matcher((CharSequence) docutmentNo);
        String documentId = (m.replaceAll("").trim());
        map.put("docutmentNo", documentId); //userId
        map.put("item1_1",String.valueOf(item11));
        map.put("item1_2",String.valueOf(item12));
        map.put("item2_1",String.valueOf(item21));
        map.put("item2_2",String.valueOf(item22));
        map.put("item2_3",String.valueOf(item23));
        map.put("item2_4",String.valueOf(item24));
        map.put("item2_5",String.valueOf(item25));
        map.put("item2_6",String.valueOf(item26));
        map.put("comment",comment.getText().toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt=new Date();
        map.put("evaluateDateTime",sdf.format(dt));
        NetTask netTask =  new NetTask();
        netTask.initJSONObject(map);
        netTask.setCommandType(CommandType.teacher_add_evaluation_record);
        netTask.setActiveContext(getActivity());
        netTask.execute();

        Fragment_evaluation fss = new Fragment_evaluation();
        FragmentManager fa = getFragmentManager();
        FragmentTransaction rww = fa.beginTransaction();
        rww.replace(R.id.id_content, fss,"test");
        rww.addToBackStack(null);
        rww.commit();

    }

    private void init(){
        uiHandler = new Fragment_evaluation_update_3.UIHandler();
    }
    private void record(int mFlag) {
        if (mState != -1) {
            return;
        }
        int mResult = -1;
        switch (mFlag) {
            case FLAG_WAV:
                AudioRecordFunc mRecord_1 = AudioRecordFunc.getInstance();
                mResult = mRecord_1.startRecordAndFile();
                break;

        }
        if (mResult == ErrorCode.SUCCESS) {
            uiThread = new Fragment_evaluation_update_3.UIThread();
            new Thread(uiThread).start();
            mState = mFlag;
        } else {
            Message msg = new Message();
            Bundle b = new Bundle();// 存放数据
            b.putInt("cmd", CMD_RECORDFAIL);
            b.putInt("msg", mResult);
            msg.setData(b);

            uiHandler.sendMessage(msg); // 向Handler发送消息,更新UI
        }
    }

    /**
     * 停止录音
     */
    private void stop() {
        if (mState != -1) {
            switch (mState) {
                case FLAG_WAV:
                    AudioRecordFunc mRecord_1 = AudioRecordFunc.getInstance();
                    mRecord_1.stopRecordAndFile();
                    break;

            }
            if (uiThread != null) {
                uiThread.stopThread();
            }
            if (uiHandler != null)
                uiHandler.removeCallbacks(uiThread);
            Message msg = new Message();
            Bundle b = new Bundle();// 存放数据
            b.putInt("cmd", CMD_STOP);
            b.putInt("msg", mState);
            msg.setData(b);
            uiHandler.sendMessageDelayed(msg, 1000); // 向Handler发送消息,更新UI
            mState = -1;
        }
    }

    private void upload(){
        File file = new File(Environment.getExternalStorageDirectory().
                getAbsolutePath() + "/FinalAudio.wav");
        FileInputStream inputFile = null;
        try {
            inputFile = new FileInputStream(file);
            byte buffer[] = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            encodeString = Base64.encodeToString(buffer, Base64.DEFAULT);
        } catch (Exception e) {

        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("recordFileBase64", encodeString); //隨機8碼
        NetTask netTask = new NetTask();
        netTask.initJSONObject(map);
        netTask.setCommandType(CommandType.transfer_wav_to_text_by_based64);
        netTask.setActiveContext(getActivity());
        netTask.execute();


        while(!ServerConnect.F_comment_flag){

        }

        comment.setText(ServerConnect.comment_imformation);
        eva_comment = ServerConnect.comment_imformation;
        ServerConnect.F_comment_flag=false;
        ServerConnect.comment_imformation="";
    }

    private final static int CMD_RECORDING_TIME = 2000;
    private final static int CMD_RECORDFAIL = 2001;
    private final static int CMD_STOP = 2002;

    class UIHandler extends Handler {
        public UIHandler() {
        }

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            Log.d("MyHandler", "handleMessage......");
            super.handleMessage(msg);
            Bundle b = msg.getData();
            int vCmd = b.getInt("cmd");
            switch (vCmd) {
                case CMD_RECORDING_TIME:
                    int vTime = b.getInt("msg");
                    txt.setText("正在錄音中，已錄製：" + vTime + " s");
                    Log.e(TAG, "正在錄音中，已錄製：" + vTime + " s");
                    break;
                case CMD_RECORDFAIL:
                    int vErrorCode = b.getInt("msg");
                    String vMsg = ErrorCode.getErrorInfo(getActivity(), vErrorCode);
                    txt.setText("錄音失敗：" + vMsg);
                    Log.e(TAG, "錄音失敗：" + vMsg);
                    break;
                case CMD_STOP:
                    int vFileType = b.getInt("msg");
                    switch (vFileType) {
                        case FLAG_WAV:
                            AudioRecordFunc mRecord_1 = AudioRecordFunc.getInstance();
                            long mSize = mRecord_1.getRecordFileSize();
                            txt.setText("錄音已停止.錄音文件:" + AudioFileFunc.getWavFilePath() + "\n文件大小：" + mSize);
                            break;

                    }
                    break;
                default:
                    break;
            }
        }
    }

    ;

    class UIThread implements Runnable {
        int mTimeMill = 0;
        boolean vRun = true;

        public void stopThread() {
            vRun = false;
        }

        public void run() {
            while (vRun) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                mTimeMill++;
                Log.d("thread", "mThread........" + mTimeMill);
                Message msg = new Message();
                Bundle b = new Bundle();// 存放数据
                b.putInt("cmd", CMD_RECORDING_TIME);
                b.putInt("msg", mTimeMill);
                msg.setData(b);

                uiHandler.sendMessage(msg); // 向Handler发送消息,更新UI
            }

        }
    }
}
