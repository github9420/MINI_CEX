package com.example.mini_cexentrustment.tw;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mini_cexentrustment.R;
import com.example.mini_cexentrustment.dao.UserAccountDAO;
import com.example.mini_cexentrustment.define.CommandType;
import com.example.mini_cexentrustment.define.UserAccount;
import com.example.mini_cexentrustment.framework.BaseActivity;
import com.example.mini_cexentrustment.thread.NetTask;
import com.example.mini_cexentrustment.thread.OnThreadComplete;

import java.util.HashMap;
import java.util.Map;

//重設密碼
public class ResetPassword extends BaseActivity implements OnThreadComplete {
    private final static String TAG = ResetPassword.class.getSimpleName();

    // UI references.
    private EditText mPasswordView;
    private EditText mConfirmPasswordView;
    private View mProgressView;
    private View mForgetPasswordFormView;
    private Button btn_submit,back_btn;
    private static UserAccountDAO mUserAccountDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initViewObject();
    }

    /**
     *  初始化畫面物件
     */
    protected void initViewObject() {
        Log.d(TAG, "初始化 LoginActivity 畫面");

        mForgetPasswordFormView = findViewById(R.id.scroll_reset_password_form);
        mProgressView = findViewById(R.id.reset_password_progress);
        mPasswordView.setHint(R.string.new_prompt_password);
        mConfirmPasswordView = (EditText) findViewById(R.id.confirm_new_prompt_password);
        mConfirmPasswordView.setHint(R.string.confirm_new_prompt_password);
        //1.送出
        btn_submit = (Button)findViewById(R.id.btn_submit);
        btn_submit.setText(R.string.submit);
        btn_submit.setOnClickListener(click_btn_submit);
        back_btn = (Button)findViewById(R.id.reset_password_back);
        back_btn.setOnClickListener(click_back);
    }

    /**
     *  送出事件
     */
    private View.OnClickListener click_btn_submit = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            //變更密碼
            mPasswordView = (EditText) findViewById(R.id.reset_password_text_password);
            mConfirmPasswordView = (EditText) findViewById(R.id.confirm_reset_password_text_password);
            attemptForget_Password();
        }
    };
    private View.OnClickListener click_back = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            //變更密碼
            Intent newAct = new Intent();
            newAct.setClass( ResetPassword.this, LoginActivity.class);
            startActivity(newAct);
        }
    };


    @Override
    public void executeCompleteCallback(String jsonData) {
        showProgress(false);
        if(jsonData == "1"){
            Intent newAct = new Intent();
            //newAct.setClass( ResetPassword.this, LoginActivity.class);
            startActivity(newAct);
            //ForgetPasswordActivity.this.finish();
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     * 欄位檢查
     */
    private void attemptForget_Password() {

        // Reset errors.
        mPasswordView.setError(null);
        mConfirmPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String password = mPasswordView.getText().toString();
        String confirmPassword = mConfirmPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid confirm password, if the user entered one.
        if (!TextUtils.isEmpty(confirmPassword) && !isPasswordValid(confirmPassword) ) {
            mConfirmPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mConfirmPasswordView;
            cancel = true;
        }

        if(!isPasswordMachValid(password, confirmPassword)){
            mConfirmPasswordView.setError(getString(R.string.error_incorrect_password_match));
            focusView = mConfirmPasswordView;
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
            //實作變更密碼
            if(mUserAccountDAO == null){
                mUserAccountDAO = new UserAccountDAO(this);
            }
            UserAccount  mUserAccount = mUserAccountDAO.getCurrentObject();
            if(mUserAccount != null){
                Map<String, String> map = new HashMap<String, String>();
                map.put("email", mUserAccount.getEmail()); //隨機8碼
                map.put("token", mUserAccount.getToken());
                map.put("userNewPassword", password);
                NetTask netTask =  new NetTask();
                netTask.initJSONObject(map);
                netTask.setCommandType(CommandType.account_modify_user_password);
                netTask.setActiveContext(ResetPassword.this);
                netTask.execute();
            }
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
