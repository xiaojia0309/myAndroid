package com.example.john.myapplication.tools_used;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.john.myapplication.R;

/**
 * Created by John on 2017/10/17.
 */

public class TitleLayout extends LinearLayout implements View.OnClickListener {

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.title,this);

        Button button_back= findViewById(R.id.title_back);
        Button button_edit= findViewById(R.id.title_edit);

        //绑定到此页面！
        button_back.setOnClickListener(this);
        button_edit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_back:
                ((Activity) getContext()).finish();
                break;
            case R.id.title_edit:
                Toast.makeText(getContext(), "You clicked Edit button",
                        Toast.LENGTH_SHORT).show();
                break;
            default:
        }

    }
}
