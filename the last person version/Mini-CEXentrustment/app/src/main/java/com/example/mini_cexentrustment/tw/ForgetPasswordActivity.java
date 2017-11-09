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
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.mini_cexentrustment.R;
import com.example.mini_cexentrustment.define.CommandType;
import com.example.mini_cexentrustment.framework.BaseActivity;
import com.example.mini_cexentrustment.thread.NetTask;
import com.example.mini_cexentrustment.thread.OnThreadComplete;

import java.util.HashMap;
import java.util.Map;

public class ForgetPasswordActivity extends BaseActivity implements OnThreadComplete {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private View mProgressView;
    private View mForgetPasswordFormView;
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initViewObject();
    }

    /**
     *  初始化操作表單
     */
    private  void initViewObject(){
        setupActionBar();
        mEmailView = (AutoCompleteTextView) findViewById(R.id.forget_password_text_email);
        mEmailView.setHint(R.string.prompt_email);
        mForgetPasswordFormView = findViewById(R.id.scroll_forget_password_form);
        mProgressView = findViewById(R.id.forget_password_progress);
        //1.送出
        btn_submit = (Button)findViewById(R.id.btn_submit);
        btn_submit.setText(R.string.submit);
        btn_submit.setOnClickListener(click_btn_submit);

    }

    /**
     *  送出事件
     */
    private View.OnClickListener click_btn_submit = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            //變更密碼
            mEmailView = (AutoCompleteTextView) findViewById(R.id.forget_password_text_email);
            attemptForget_Password();
        }
    };
    @Override
    public void executeCompleteCallback(String jsonData) {
        showProgress(false);
        if(jsonData == "1"){
            Intent newAct = new Intent();
            newAct.setClass( ForgetPasswordActivity.this, LoginActivity.class);
            startActivity(newAct);
            //ForgetPasswordActivity.this.finish();
        }
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
                startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
                break;
        }
        return true;
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     * 欄位檢查
     */
    private void attemptForget_Password() {

        // Reset errors.
        mEmailView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();

        boolean cancel = false;
        View focusView = null;

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

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            //實作忘記密碼
            Map<String, String> map = new HashMap<String, String>();
            map.put("email", email);
            NetTask netTask =  new NetTask();
            netTask.initJSONObject(map);
            netTask.setCommandType(CommandType.account_send_setting_password_email);
            netTask.setActiveContext(ForgetPasswordActivity.this);
            netTask.execute();
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
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

            mForgetPasswordFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mForgetPasswordFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mForgetPasswordFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mForgetPasswordFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private boolean isPasswordMachValid(String password, String confirmPassword) {
        //TODO: Replace this with your own logic
        if(password == confirmPassword){
            return true;
        }
        return false;
    }

}
