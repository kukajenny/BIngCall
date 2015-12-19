package com.example.zjp.bingcall.Longclick_dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.zjp.bingcall.R;

/**
 * Created by zjp on 15-12-18.
 */
public class Mydialog  extends Dialog{

    Context context;
    LoginInputListener listener;

    public Mydialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
    }
    public Mydialog(Context context, int theme,LoginInputListener listener){
        super(context, theme);
        this.context = context;
        this.listener=listener;

    }

    public interface LoginInputListener
    {
        void onLoginInputComplete(int flag);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog);
        Button button_call=(Button)findViewById(R.id.dialog_buttoncall);
        Button button_modify= (Button) findViewById(R.id.dialog_buttonmodify);
        Button button_delete=(Button)findViewById(R.id.dialog_buttondelete);

        button_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onLoginInputComplete(1);
                dismiss();
            }
        });

        button_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onLoginInputComplete(2);
                dismiss();
            }
        });

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onLoginInputComplete(3);
                dismiss();
            }
        });
    }

}
