package com.example.mini_cexentrustment.tw;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.mini_cexentrustment.R;
import com.example.mini_cexentrustment.define.CommandType;
import com.example.mini_cexentrustment.framework.BaseActivity;
import com.example.mini_cexentrustment.thread.NetTask;
import com.example.mini_cexentrustment.thread.OnThreadComplete;

import java.util.HashMap;
import java.util.Map;

public class RegisteredActivity extends BaseActivity implements OnThreadComplete {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mConfimPasswordView;
    private EditText mNameView;
    private EditText mUnitView;
    private EditText mMobilePhoneView;
    private EditText mPhoneView;
    private EditText mPhoneEXView;

    private View mProgressView;
    private View mRegisteredFormView;
    private Button btn_submit;
    private Button btn_cancel;

    private final static String TAG = LoginActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        initViewObject();
    }

    /**
     *  初始化操作表單
     */
    private  void initViewObject(){
        setupActionBar();
        mRegisteredFormView = findViewById(R.id.scroll_registered_form);
        mProgressView = findViewById(R.id.registered_progress);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.registered_text_email);
        mEmailView.setHint(R.string.prompt_email3);
        mPasswordView = (EditText) findViewById(R.id.registered_text_password);
        mPasswordView.setHint(R.string.prompt_password);
        mNameView = (EditText) findViewById(R.id.registered_text_name);
        mNameView.setHint(R.string.name);

        mUnitView = (EditText) findViewById(R.id.registered_text_unit);
        mConfimPasswordView = (EditText) findViewById(R.id.confirm_registered_password_text_password);

        mMobilePhoneView = (EditText) findViewById(R.id.registered_text_mobilePhone);
        mPhoneView = (EditText) findViewById(R.id.registered_text_phone);
        mPhoneEXView = (EditText) findViewById(R.id.registered_text_phone_Ex);

        //4.送出
        btn_submit = (Button)findViewById(R.id.btn_submit);
        btn_submit.setText(R.string.submit);
        btn_submit.setOnClickListener(click_btn_submit);

    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            // 顯示攔 顯示向上按鈕
            actionBar.setDisplayHomeAsUpEnabled(true);
            ColorDrawable actionBarColor = new ColorDrawable(Color.rgb(67, 67, 67));
            actionBar.setBackgroundDrawable(actionBarColor);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(RegisteredActivity.this, LoginActivity.class));
                break;
        }
        return true;
    }

    /**
     *  送出事件
     */
    private View.OnClickListener click_btn_submit = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            //註冊事件
            attemptRegistered();
        }
    };

    @Override
    public void executeCompleteCallback(String jsonData) {
        showProgress(false);
        if(jsonData =="1"){
            Intent newAct = new Intent();
            newAct.setClass( RegisteredActivity.this, LoginActivity.class);
            startActivity(newAct);
            //RegisteredActivity.this.finish();
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     * 欄位檢查
     */
    private void attemptRegistered() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mNameView.setError(null);
        mUnitView.setError(null);
        mConfimPasswordView.setError(null);
        mMobilePhoneView.setError(null);
        mPhoneView.setError(null);
        mPhoneEXView.setError(null);



        // Store values at the time of the login attempt.
//        String email = mEmailView.getText().toString();
//        String password = mPasswordView.getText().toString();
//        String name = mNameView.getText().toString();
//        String unit = mUnitView.getText().toString();
//        String confimPassword = mConfimPasswordView.getText().toString();
//        String mobilePhone = mMobilePhoneView.getText().toString();
//        String phone = mPhoneView.getText().toString();
//        String phoneEX = mPhoneEXView.getText().toString();

        String email = mEmailView.getText().toString();
        String userPassword = mPasswordView.getText().toString();
        String userName = mNameView.getText().toString();
        //String businessNo = mUnitView.getText().toString();
        String businessNo="8978198";
        String groupName=mUnitView.getText().toString();
        //String groupName="紅星球";
        String mobilePhone = mMobilePhoneView.getText().toString();
        String confimPassword = mConfimPasswordView.getText().toString();
        String phone = mPhoneView.getText().toString();
        String phoneEx = mPhoneEXView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(userPassword) && !isPasswordValid(userPassword)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(confimPassword) && !isPasswordValid(confimPassword)) {
            mConfimPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mConfimPasswordView;
            cancel = true;
        }

        if(!isPasswordMatchValid(userPassword, confimPassword)){
            Log.i(TAG,"password="+userPassword);
            Log.i(TAG,"confirmpassword="+confimPassword);
            mConfimPasswordView.setError(getString(R.string.error_incorrect_password_match));
            focusView = mConfimPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (TextUtils.isEmpty(userName)) {
            mNameView.setError(getString(R.string.error_field_required));
            focusView = mNameView;
            cancel = true;
        }

        if (TextUtils.isEmpty(businessNo)) {
            mUnitView.setError(getString(R.string.error_field_required));
            focusView = mUnitView;
            cancel = true;
        }

        if (!TextUtils.isEmpty(mobilePhone) && !isPhoneValid(mobilePhone)) {
            mMobilePhoneView.setError(getString(R.string.error_invalid_phone));
            focusView = mMobilePhoneView;
            cancel = true;
        }
        if (!TextUtils.isEmpty(phone) && !isPhoneValid(phone)) {
            mPhoneView.setError(getString(R.string.error_invalid_phone));
            focusView = mPhoneView;
            cancel = true;
        }
        /*
        if (TextUtils.isEmpty(phoneEX)) {
            mPhoneEXView.setError(getString(R.string.error_invalid_phone));
            focusView = mPhoneEXView;
            cancel = true;
        }*/

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            //實作更新資料

            Map<String, String> map = new HashMap<String, String>();
            map.put("email", email);
            map.put("userPassword", userPassword);
            map.put("userName", userName);
            map.put("businessNo", businessNo);
            map.put("groupName", groupName);
            map.put("mobilePhone", mobilePhone);
            map.put("phone", phone);
            map.put("phoneEx", phoneEx);
            Log.e(TAG,"email: "+ email);
            Log.e(TAG,"userPassword: "+ userPassword);
            Log.e(TAG,"businessNo: "+businessNo);
            Log.e(TAG,"groupName: "+groupName);
            Log.e(TAG,"mobilePhone: "+ mobilePhone);
            Log.e(TAG,"phone: "+ phone);
            Log.e(TAG,"phoneEx: "+ phoneEx);


            NetTask netTask =  new NetTask();
            netTask.initJSONObject(map);
            netTask.setCommandType(CommandType.account_user_sign_up);
            netTask.setActiveContext(RegisteredActivity.this);
            netTask.execute();
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisteredFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisteredFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisteredFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisteredFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 4;
    }

    private boolean isPhoneValid(String phone) {
        //TODO: Replace this with your own logic
        return phone.length() >= 8;
    }

    private boolean isPasswordMatchValid(String password, String confirmPassword) {
        //TODO: Replace this with your own logic
        if(password.equals(confirmPassword) ){
            return true;
        }
        return false;

    }

}
