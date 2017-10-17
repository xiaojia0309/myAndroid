package com.example.john.myapplication.activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.john.myapplication.R;

import javax.security.auth.login.LoginException;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        if(savedInstanceState!=null){
            String data = savedInstanceState.getString("qweqwr");
            Log.d("TAG", "onCreate: "+data);
        }
    }


    public static void actionStart(Context context, String data1, String data2){

        Intent intent = new Intent(context,Main2Activity.class);
        intent.putExtra("data1",data1);
        intent.putExtra("data2",data2);
        context.startActivity(intent);

    }


    /**
     * @author xiaojia
     * @see--这个方法是用来保证程序正常运行的基础，当此Activity为处于顶桟时，内存不足杀死程序后，当再次开启此
     * @see--Activity时，onCreate方法从bungdle中取出此值！
     * @param outState
     * @param outPersistentState
     */
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("qweqwr","123");
    }
}
