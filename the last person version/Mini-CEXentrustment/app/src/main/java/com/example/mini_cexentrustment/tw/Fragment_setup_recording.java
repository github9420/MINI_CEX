package com.example.mini_cexentrustment.tw;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mini_cexentrustment.R;
import com.example.mini_cexentrustment.define.CommandType;
import com.example.mini_cexentrustment.thread.AsyncTaskForPostFile;
import com.example.mini_cexentrustment.thread.NetTask;

import org.json.JSONException;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rorensu on 2017/11/22.
 */

public class Fragment_setup_recording extends Fragment implements View.OnClickListener {
    private final static String TAG = Fragment_setup_recording.class.getSimpleName();
    private MediaRecorder myAudioRecorder;
    private String outputFile = null;
    private Button start, stop, play,upload,back;
   private String encodeString="";
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup_recording, container, false);



        start = (Button) view.findViewById(R.id.id_btn_recording_start);
        stop = (Button) view.findViewById(R.id.id_btn_recording_stop);
        play = (Button) view.findViewById(R.id.id_btn_recording_play);
        upload =(Button) view.findViewById(R.id.id_btn_recording_upload);
        back=(Button) view.findViewById(R.id.id_btn_recording_back);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        play.setOnClickListener(this);
        upload.setOnClickListener(this);
        back.setOnClickListener(this);

        stop.setEnabled(false);
        play.setEnabled(false);
        upload.setEnabled(false);
        outputFile = Environment.getExternalStorageDirectory().
                getAbsolutePath() + "/myrecording.wav";


        return view;
    }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.id_btn_recording_start:
                    start(v);
                    break;
                case R.id.id_btn_recording_stop:
                    stop(v);
                    break;
                case R.id.id_btn_recording_play:
                    play(v);
                    break;
                case R.id.id_btn_recording_upload:
                    upload_file();
                    break;
                case R.id.id_btn_recording_back:
                    Fragment_setup fse = new Fragment_setup();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction tx = fm.beginTransaction();
                    tx.replace(R.id.id_content, fse, "recordingtosetup");
                    tx.addToBackStack(null);
                    //tx.hide(this);
                    tx.remove(this);
                    tx.commit();

                    break;
            }
        }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void start(View view) {
        try {
            myAudioRecorder = new MediaRecorder();
            myAudioRecorder.setAudioSamplingRate(16000);
            myAudioRecorder.setAudioChannels(1);
            myAudioRecorder.setAudioEncodingBitRate(16);
            myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            //myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            myAudioRecorder.setOutputFile(outputFile);
            myAudioRecorder.prepare();
            myAudioRecorder.start();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        start.setEnabled(false);
        stop.setEnabled(true);
        Toast.makeText(getActivity(), "Recording started", Toast.LENGTH_LONG).show();

    }

    public void stop(View view) {
        myAudioRecorder.stop();
        myAudioRecorder.release();
        myAudioRecorder = null;
        stop.setEnabled(false);
        play.setEnabled(true);
        Toast.makeText(getActivity(), "Audio recorded successfully",
                Toast.LENGTH_LONG).show();
    }
/*
    public void play(View view) throws IllegalArgumentException,
            SecurityException, IllegalStateException, IOException {

        MediaPlayer m = new MediaPlayer();
        m.setDataSource(outputFile);
        m.prepare();
        m.start();
        Toast.makeText(getActivity(), "Playing audio", Toast.LENGTH_LONG).show();
    }*/

    public void play(View view) {

        MediaPlayer m = new MediaPlayer();
        try {
            m.setDataSource(outputFile);
            m.prepare();
            m.start();
            Toast.makeText(getActivity(), "Playing audio", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
            start.setEnabled(true);
        upload.setEnabled(true);
    }
//    public static String encodeBase64File(String path) throws Exception {
//        File  file = new File(path);
//        FileInputStream inputFile = new FileInputStream(file);
//        byte[] buffer = new byte[(int)file.length()];
//        inputFile.read(buffer);
//        inputFile.close();
//        return new BASE64Encoder().encode(buffer);
//    }


    public void upload_file(){


        File file =new File(Environment.getExternalStorageDirectory().
                getAbsolutePath() + "/myrecording.wav");
        FileInputStream inputFile=null;
        try{
            inputFile =new FileInputStream(file);
            byte buffer[] =new byte[(int)file.length()];
            inputFile.read(buffer);
            inputFile.close();
            encodeString=Base64.encodeToString(buffer,Base64.DEFAULT);
        }catch (Exception e){

        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("recordFileBase64",encodeString ); //隨機8碼
        NetTask netTask =  new NetTask();
        netTask.initJSONObject(map);
        netTask.setCommandType(CommandType.transfer_wav_to_text_by_based64);
        netTask.setActiveContext(getActivity());
        netTask.execute();
        Log.i(TAG,encodeString);
        //way1
        /*File file = new File(Environment.getExternalStorageDirectory() + "/hello-4.wav");
        byte[] bytes = readFileToByteArray(file);

        String encoded = Base64.encodeToString(bytes, 0);
        Log.e("~~~~~~~~ Encoded: ", encoded);
*/

        //way2
        /*//註冊AsyncTaskForPostFile完成的通知
        getActivity().registerReceiver(AsyncTaskForPostFileReceiver, new IntentFilter("PostFileComplete"));
        //取得Download的路徑
        String DownloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        //取得sample.apk的路徑
        String FilePath =outputFile;
        //開始上傳
        AsyncTaskForPostFile PostFile = new AsyncTaskForPostFile(getActivity());
        PostFile.execute(FilePath, null, null);
        Toast.makeText(getActivity(), "Upload audio", Toast.LENGTH_LONG).show();
        */


        //way3
        // 先取得圖片
        // uriFilePath是當初設定照相完，要存在哪
        // 如果忘記可以去看文章上面說的教學文
       /* FileInputStream fileInputStream = new FileInputStream(new File(uriFilePath.getPath()));

        final String BOUNDARY 	= "==================================";
        final String HYPHENS 	= "--";
        final String CRLF 		= "\r\n";
        URL url 				= new URL("http://140.128.68.202/speech.html");
        HttpURLConnection conn 	= (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");						// method一定要是POST
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);

        // 把Content Type設為multipart/form-data
        // 以及設定Boundary，Boundary很重要!
        // 當你不只一個參數時，Boundary是用來區隔參數的
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);


        // 下面是開始寫參數
        String strContentDisposition = "Content-Disposition: form-data; name=\"image\"; filename=\"image\"";
        String strContentType = "Content-Type: image/jpeg";
        DataOutputStream dataOS = new DataOutputStream(conn.getOutputStream());
        dataOS.writeBytes(HYPHENS+BOUNDARY+CRLF);		// 寫--==================================
        dataOS.writeBytes(strContentDisposition+CRLF);	// 寫(Disposition)
        dataOS.writeBytes(contentType+CRLF);			// 寫(Content Type)
        dataOS.writeBytes(CRLF);

        int iBytesAvailable = fileInputStream.available();
        byte[] byteData = new byte[iBytesAvailable];
        int iBytesRead = fileInputStream.read(byteData, 0, iBytesAvailable);
        while (iBytesRead > 0) {
            dataOS.write(byteData, 0, iBytesAvailable);	// 開始寫內容
            iBytesAvailable = fileInputStream.available();
            iBytesRead = fileInputStream.read(byteData, 0, iBytesAvailable);
        }
        dataOS.writeBytes(CRLF);
        dataOS.writeBytes(HYPHENS+BOUNDARY+HYPHENS);	// (結束)寫--==================================--
        file.close();
        dataOS.flush();
        dataOS.close();*/
    }
    private final BroadcastReceiver AsyncTaskForPostFileReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //顯示上傳結束
            Toast.makeText(getActivity(), "PostFileComplete", Toast.LENGTH_SHORT).show();
        }
    };
}