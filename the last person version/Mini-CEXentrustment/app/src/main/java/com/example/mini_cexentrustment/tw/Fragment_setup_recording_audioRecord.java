package com.example.mini_cexentrustment.tw;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mini_cexentrustment.R;
import com.example.mini_cexentrustment.define.CommandType;
import com.example.mini_cexentrustment.thread.NetTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by rorensu on 2017/11/22.
 */

public class Fragment_setup_recording_audioRecord extends Fragment {
    private final static String TAG = Fragment_setup_recording_audioRecord.class.getSimpleName();
    private final static int FLAG_WAV = 0;
    private int mState = -1;    //-1:没再录制，0：录制wav，1：录制amr
    private Button btn_record_wav;
    private Button btn_record_play;
    private Button btn_record_upload;
    private Button btn_record_back;
    private Button btn_stop;
    private TextView txt;
    private UIHandler uiHandler;
    private UIThread uiThread;
    private String outputFile = "";
    private String encodeString = "";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup_recording, container, false);
        outputFile = Environment.getExternalStorageDirectory().
                getAbsolutePath() + "/FinalAudio.wav";
        findViewByIds(view);
        setListeners();
        init();
        return view;
    }

    private void findViewByIds(View view) {
        btn_record_wav = (Button) view.findViewById(R.id.id_btn_recording_start);
        btn_record_play = (Button) view.findViewById(R.id.id_btn_recording_play);
        btn_record_upload = (Button) view.findViewById(R.id.id_btn_recording_upload);
        btn_record_back = (Button) view.findViewById(R.id.id_btn_recording_back);
        btn_stop = (Button) view.findViewById(R.id.id_btn_recording_stop);
        txt = (TextView) view.findViewById(R.id.record_textView1);
    }

    private void setListeners() {
        btn_record_wav.setOnClickListener(btn_record_wav_clickListener);
        btn_stop.setOnClickListener(btn_stop_clickListener);
        btn_record_play.setOnClickListener(btn_record_play_clickListener);
        btn_record_upload.setOnClickListener(btn_record_upload_clickListener);

    }

    private void init() {
        uiHandler = new UIHandler();
    }

    private Button.OnClickListener btn_record_wav_clickListener = new Button.OnClickListener() {
        public void onClick(View v) {
            record(FLAG_WAV);
        }
    };

    private Button.OnClickListener btn_stop_clickListener = new Button.OnClickListener() {
        public void onClick(View v) {
            stop();
        }
    };


    private Button.OnClickListener btn_record_play_clickListener = new Button.OnClickListener() {
        public void onClick(View v) {
            MediaPlayer m = new MediaPlayer();
            try {
                m.setDataSource(outputFile);
                m.prepare();
                m.start();
                Toast.makeText(getActivity(), "Playing audio", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            btn_record_play.setEnabled(true);
            btn_record_upload.setEnabled(true);
        }
    };
    private Button.OnClickListener btn_record_upload_clickListener = new Button.OnClickListener() {
        public void onClick(View v) {
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
            Log.i(TAG, encodeString);
        }
    };

    /**
     * 开始录音
     *
     * @param mFlag，0：录制wav格式，1：录音amr格式
     */
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
            uiThread = new UIThread();
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