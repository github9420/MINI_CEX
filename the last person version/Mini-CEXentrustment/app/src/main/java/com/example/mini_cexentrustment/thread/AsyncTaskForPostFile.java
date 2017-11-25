package com.example.mini_cexentrustment.thread;

/**
 * Created by rorensu on 2017/11/22.
 */
        import java.io.BufferedInputStream;
        import java.io.ByteArrayOutputStream;
        import java.io.DataOutputStream;
        import java.io.File;
        import java.io.FileInputStream;
        import java.io.IOException;
        import java.io.InputStream;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.util.HashMap;
        import java.util.Map;

        import android.content.Context;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.util.Log;
        import android.widget.Toast;

        import com.example.mini_cexentrustment.common.DateTool;
        import com.example.mini_cexentrustment.dao.UserAccountDAO;
        import com.example.mini_cexentrustment.define.CommandType;
        import com.example.mini_cexentrustment.define.GDefine;
        import com.example.mini_cexentrustment.define.UserAccount;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

public class AsyncTaskForPostFile extends AsyncTask<String, Void, Void>{
    private static String TAG = "AsyncTaskForPostFile";
    private static String recording_information="";
    private Context mContext;
    private final String sp = "7dd19a1a5a0d2c";
    private final String end = "\r\n";
    private final String twoHyphens = "--";
    private final String boundary = "---------------------------";
    private final String post_url = "http://140.128.68.202/speech.html";
    private final String input_name = "recordFile";

    public AsyncTaskForPostFile(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected Void doInBackground(String... params) {
        String file_path = params[0];
        File MediaFile = new File(file_path);
        String responseCode = "-1";
        if(!MediaFile.exists()){
            Log.e(TAG,"not exist");
            return null;
        }

        try {
            URL url = new URL(post_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setChunkedStreamingMode(0);	//針對大檔傳輸的設定
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary + sp);

            int bufferSize = 102400;
            FileInputStream fileInputStream = new FileInputStream(file_path);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            dataOutputStream.writeBytes(boundary + twoHyphens + sp + end);
            dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + input_name + "\"; filename=\"" + MediaFile.getName() + "\"" + end);
            dataOutputStream.writeBytes(end);
            byte[] buffer = new byte[bufferSize];
            int count = 0;
            while((count = bufferedInputStream.read(buffer)) != -1)
            {
                dataOutputStream.write(buffer, 0 , count);
                dataOutputStream.writeBytes(end);
            }
            bufferedInputStream.close();
            fileInputStream.close();
            dataOutputStream.write((boundary + twoHyphens + sp + twoHyphens + end).getBytes());
            dataOutputStream.flush();
            Log.e(TAG,"Hi there");
            InputStream inputStream = httpURLConnection.getInputStream();
            //responseCode = dispatchCommandType(changeInputStream(inputStream));
            dispatchCommandType(changeInputStream(inputStream));
            inputStream.close();
            dataOutputStream.close();

        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
        }

        return null;
    }
    public static String changeInputStream(InputStream inputStream) {    //將輸入串流轉成字串回傳
        Log.e(TAG,"step into");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //ByteArrayOutputStream型態可不斷寫入來增長緩存區,可使用toByteArray()、toString()獲取數據
        byte[] data = new byte[1024];
        int len = 0;
        String result = "";
        if (inputStream != null) {        //判斷inputStream是否非空字串
            try {
                while ((len = inputStream.read(data)) != -1) {    //將inputStream寫入data並回傳字數到len
                    outputStream.write(data, 0, len);            //將data寫入到輸出流中,參數二為起始位置,len是讀取長度
                }
                result = new String(outputStream.toByteArray(), GDefine.encode);    //resilt取得outputStream的string並轉成encode邊碼
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("text", "Http_Client.changeInputStream.IOException="+e.toString());
            }
        }
        Log.e(TAG,"safe come out, result="+result);
        return result;                //回傳result
    }
    @Override
    protected void onPostExecute(Void result) {
        Intent intent = new Intent("PostFileComplete");
        this.mContext.sendBroadcast(intent);
        super.onPostExecute(result);
    }
    public static void dispatchCommandType(String jsonData) throws JSONException {

        //
        Log.i(TAG,"dispatchCommandType01");
        Log.i(TAG,jsonData);
        String result  = "1";
        JSONObject jsonObject=new JSONObject(jsonData);
        recording_information = jsonObject.get("text").toString();
        Log.e(TAG,recording_information+"!!!!");
       // return result;
    }
}