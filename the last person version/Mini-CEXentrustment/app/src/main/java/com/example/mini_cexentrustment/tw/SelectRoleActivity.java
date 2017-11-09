package com.example.mini_cexentrustment.tw;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mini_cexentrustment.R;
import com.example.mini_cexentrustment.framework.BaseActivity;
import com.example.mini_cexentrustment.thread.OnThreadComplete;

public class SelectRoleActivity extends BaseActivity implements OnThreadComplete {
    private final static String TAG = SelectRoleActivity.class.getSimpleName();
    // UI references.
    private ImageView mTeacherImage;
    private ImageView mStudentImage;
    private TextView mTeacherTextView;
    private TextView mStudentTextView;
    private View mProgressView;
    private View mSelectRoleFormView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);
        initViewObject();
    }

    /**
     *  初始化畫面物件
     */
    protected void initViewObject() {
        Log.d(TAG, "初始化 LoginActivity 畫面");

        mSelectRoleFormView = findViewById(R.id.email_select_role_form);
        mProgressView = findViewById(R.id.select_role_progress);

        mTeacherImage = (ImageView) findViewById(R.id.select_role_imageView);
        mTeacherTextView = (TextView) findViewById(R.id.select_role_imageView_title);
        mStudentImage = (ImageView) findViewById(R.id.select_role_imageView2);
        mStudentTextView = (TextView) findViewById(R.id.select_role_imageView2_title);

        mTeacherImage.setOnClickListener(click_btn_teacher_image);
        mTeacherTextView.setOnClickListener(click_btn_teacher_image);
        mStudentImage.setOnClickListener(click_btn_student_image);
        mStudentTextView.setOnClickListener(click_btn_student_image);
    }

    /**
     *  老師選取事件
     */
    private View.OnClickListener click_btn_teacher_image = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent newAct = new Intent();
            //newAct.setClass( LoginActivity.this, RegisteredActivity.class);
            startActivity(newAct);
        }
    };

    /**
     *  學生選取事件
     */
    private View.OnClickListener click_btn_student_image = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent newAct = new Intent();
            //newAct.setClass( LoginActivity.this, RegisteredActivity.class);
            startActivity(newAct);
        }
    };

    @Override
    public void executeCompleteCallback(String jsonData) {

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

            mSelectRoleFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mSelectRoleFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSelectRoleFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mSelectRoleFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
