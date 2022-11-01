package com.example.unibody.custom;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.unibody.R;

public class LoadingDialog{

    private  AlertDialog alertDialog;

    private Context context;

    public LoadingDialog(Context context){
        this.context = context;
        alertDialog = new AlertDialog.Builder(context)
                .setView(R.layout.loading_dialog)
                .setCancelable(false)  //点击外部区域不关闭
                .create();
        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
            {
                dismiss("Connection timed out");
                return false;
            }
        });
    }

    public void open(){
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void dismiss(String msg){
        alertDialog.dismiss();
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public void dismiss(){
        alertDialog.dismiss();
    }

    public boolean isShowing(){
        return alertDialog.isShowing();
    }

}
