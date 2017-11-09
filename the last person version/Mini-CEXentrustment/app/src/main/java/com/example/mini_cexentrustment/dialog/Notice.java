package com.example.mini_cexentrustment.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.mini_cexentrustment.R;
import com.example.mini_cexentrustment.tw.ForgetPasswordActivity;
import com.example.mini_cexentrustment.tw.LoginActivity;
import com.example.mini_cexentrustment.tw.RegisteredActivity;
import com.example.mini_cexentrustment.tw.ResetPassword;


/**
 * Created by 信威 on 2017/8/3.
 */
public class Notice {

    private static final String TAG = Notice.class.getSimpleName();
    private static Context mContext;

    protected Notice() {

    }
    public static void setHandler(Context ctx) {
        mContext = ctx;
    }

    /**
     *  忘記密碼錯誤- emailDoesNotExist: 信箱不存在
     */
    public static void popForgetPasswordErrorMessageByEmailDoesNotExist() {
        //AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AlertDialogCustom);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.system_send);
        builder.setMessage(R.string.send_setting_password_email_emailDoesNotExist);
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
        builder = null;
    }

    /**
     *  忘記密碼錯誤
     */
    public static void popForgetPasswordErrorMessage() {
        //AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AlertDialogCustom);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.system_send);
        builder.setMessage(R.string.send_setting_password_email_exception);
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
        builder = null;
    }
    /**
     *  忘記密碼成功
     */
    public static void popForgetPasswordSuccessMessage(final String result) {
        //AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AlertDialogCustom);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.system_send);
        builder.setMessage(R.string.send_setting_password_email_success);
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO
                dialog.dismiss();
                ((ForgetPasswordActivity) mContext).executeCompleteCallback(result);
            }
        });
        builder.create();
        builder.show();
        builder = null;
    }

    /**
     *  變更密碼錯誤
     */
    public static void popResetPasswordErrorMessage() {
        //AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AlertDialogCustom);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.system_send);
        builder.setMessage(R.string.password_change_exception);
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
        builder = null;
    }
    /**
     *  變更密碼成功
     */
    public static void popResetPasswordSuccessMessage(final String result) {
        //AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AlertDialogCustom);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.system_send);
        builder.setMessage(R.string.password_change_success);
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO
                dialog.dismiss();
                ((ResetPassword) mContext).executeCompleteCallback(result);
            }
        });
        builder.create();
        builder.show();
        builder = null;
    }

    /**
     *  註冊錯誤 -accountHasBeenUsed: 帳號已註冊
     */
    public static void popRegisteredErrorMessageByAccountHasBeenUsed() {
        //AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AlertDialogCustom);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.system_send);
        builder.setMessage(R.string.registered_accountHasBeenUsed);
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
        builder = null;
    }
    /**
     *  註冊錯誤 -groupAlreadyExist: 組織已註冊
     */
    public static void popRegisteredErrorMessageByGroupAlreadyExist() {
        //AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AlertDialogCustom);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.system_send);
        builder.setMessage(R.string.registered_groupAlreadyExist);
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
        builder = null;
    }
    /**
     *  註冊錯誤
     */
    public static void popRegisteredErrorMessage() {
        //AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AlertDialogCustom);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.system_send);
        builder.setMessage(R.string.registered_exception);
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
        builder = null;
    }
    /**
     *  註冊成功
     */
    public static void popRegisteredSuccessMessage(final String result) {
        //AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AlertDialogCustom);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.system_send);
        builder.setMessage(R.string.registered_success);
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO
                dialog.dismiss();
               ((RegisteredActivity) mContext).executeCompleteCallback(result);
            }
        });
        builder.create();
        builder.show();
        builder = null;
    }

    /**
     *  登入錯誤
     */
    public static void popLoginErrorMessage(final String result) {
        //AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AlertDialogCustom);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.system_send);
        builder.setMessage(R.string.sign_in_exception);
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO
                dialog.dismiss();
                ((LoginActivity) mContext).executeCompleteCallback(result);
            }
        });
        builder.create();
        builder.show();
        builder = null;
    }
    /**
     *  登入成功
     */
    public static void popLoginSuccessMessage(final String result) {
        //AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AlertDialogCustom);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.system_send);
        builder.setMessage(R.string.sign_in_success);
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO
                dialog.dismiss();
                ((LoginActivity) mContext).executeCompleteCallback(result);
            }
        });
        builder.create();
        builder.show();
        builder = null;
    }
}
