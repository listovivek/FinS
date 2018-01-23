package com.myappilication.xpress.finjan2017.progressstyle;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.Window;

import com.myappilication.xpress.finjan2017.R;

/**
 * Created by suresh on 4/10/17.
 */
public class ProgressBarStyle {

    private static ProgressBarStyle mInstance = null;
  //  public static RotateLoading rLoading;

    public static ProgressBarStyle getInstance()
    {
        if (mInstance == null)
        {
            mInstance = new ProgressBarStyle();
        }
        return mInstance;
    }

    public Dialog createProgressDialog(final Context con)
    {
        final Dialog loadingDialog = new Dialog(con);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingDialog.setContentView(R.layout.prograssbarstyle);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        loadingDialog.setCancelable(false);
        //rLoading = (RotateLoading) loadingDialog.findViewById(R.id.progress_wheel);
        /*loadingDialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    try{
                        ((Activity) con).finish();
                        loadingDialog.dismiss();
                    }catch (Exception e){
                    }

                }
                return true;
            }
        });*/


        return loadingDialog;
    }

    public void dismissProgressDialog(Dialog dialog)
    {
        if (dialog != null)
        {
            if (dialog.isShowing())
            {
                dialog.dismiss();
            }
        }
    }
}
