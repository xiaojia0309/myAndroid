package com.example.john.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.john.myapplication.R;
import com.example.john.myapplication.tools_okHttp.MyOkHttp;
import com.example.john.myapplication.tools_okHttp.response.JsonResponseHandler;
import com.example.john.myapplication.tools_okHttp.util.LogUtils;
import com.example.john.myapplication.tools_used.MYtools;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by John on 2017/8/24.
 */

public class Login extends BaseActivity {


    private EditText username;
    private EditText userpassword;
    private CheckBox remember;
    private CheckBox autologin;
    private Button login;
    private SharedPreferences sp;

    private String userNameValue, passwordValue;

    //初始化
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public void initView() {
        setContentView(R.layout.login);
    }

    @Override
    protected void initFindViewById() {

        // 初始化用户名、密码、记住密码、自动登录、登录按钮
        username = findViewById(R.id.username);
        userpassword = findViewById(R.id.userpassword);
        remember = findViewById(R.id.remember);
        autologin = findViewById(R.id.autologin);
        login = findViewById(R.id.login);
    }

    @Override
    public void initData() {

        try{
            int version= getVersionCode();
            toast(version+"<---------->"+getVersionName());
        }catch (Exception e){

        }

        //保存信息到shareprence，方便验证登录状态！
        sp = getSharedPreferences("userInfo", 0);
        String name = sp.getString("USER_NAME", "");
        String pass = sp.getString("PASSWORD", "");

        boolean choseRemember = sp.getBoolean("remember", false);
        boolean choseAutoLogin = sp.getBoolean("autologin", false);

        if (choseRemember) {
            username.setText(name);
            userpassword.setText(pass);
            remember.setChecked(true);
        }
        //如果上次登录选了自动登录，那进入登录页面也自动勾选自动登录
        if (choseAutoLogin) {
            autologin.setChecked(true);
            remember.setChecked(true);
            username.setText(name);
            userpassword.setText(pass);
            Intent intent = new Intent();
            intent.putExtra("param", "");
            intent.setClass(Login.this, MainActivity.class);
            Login.this.startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        }
    }

    @Override
    public void setListener() {
        login.setOnClickListener(this);
        //实时监控自动按钮是否登陆！
        autologin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    remember.setChecked(true);
                }
            }
        });
    }

    @Override
    public void widgetClick(View v) {

        switch (v.getId()) {
            case R.id.login:
                /**
                 * @see-一键启动新的页面！
                 * @see---并且很直观的可以看清MainActivity需要哪些参数！方便便写！
                 */
                Main2Activity.actionStart(Login.this,"123","123");
                Map<String, String> userInfo = getValueFromUserSubmit();
                loginServer(userInfo);
        }

    }

    /**
     * @param
     * @author xiaojia
     * @see-------获取用户输入的用户名以及密码！
     */
    private Map<String, String> getValueFromUserSubmit() {
        userNameValue = username.getText().toString();
        passwordValue = userpassword.getText().toString();

        Map<String, String> params = new HashMap<String, String>();

        params.put("userName", userNameValue);
        params.put("passWord", passwordValue);
        return params;

    }

    /**
     * @param
     * @author xiaojia
     * @date
     * @see
     */
    private void loginServer(Map<String, String> maps) {

        final Map<String, String> params = new HashMap<String, String>();

        params.put("userName", maps.get("userName"));
        params.put("passWord", maps.get("passWord"));


        MyOkHttp.get().post(this, this.getString(R.string.userLogin), params, new JsonResponseHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                toast("正常" + response);//成功验证用户信息。处理用户信息！
                SharedPreferences.Editor editor = sp.edit();
                //保存用户名和密码
                editor.putString("USER_NAME", userNameValue);
                editor.putString("PASSWORD", passwordValue);

                //是否记住密码
                if (remember.isChecked()) {
                    editor.putBoolean("remember", true);
                } else {
                    editor.putBoolean("remember", false);
                }

                //是否自动登录
                if (autologin.isChecked()) {
                    editor.putBoolean("autologin", true);


                } else {
                    editor.putBoolean("autologin", false);
                }
                editor.commit();

                Intent intent = new Intent();
                intent.putExtra("param", response + "");
                intent.setClass(Login.this, MainActivity.class);
                Login.this.startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                finish();
                LogUtils.v(TAG, statusCode + " " + response + "访问正常····");
            }

            @Override
            public void onFailure(int statusCode, String error_msg) {
                toast("错误");
                LogUtils.v(TAG, statusCode + " " + error_msg + "------");
            }
        });

    }

    private void gotoNewActivity(Context c1, Context c2, String param) {
        Log.d(TAG, "gotoNewActivity: ");

    }

    /*
 * 获取当前程序的版本号
 */
    public int getVersionCode() throws Exception{
        //获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        Log.e("TAG","版本号"+packInfo.versionCode);
        Log.e("TAG","版本名"+packInfo.versionName);
        return packInfo.versionCode;
    }

    /*
* 获取当前程序的版本名
*/
    public String getVersionName() throws Exception{
        //获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        Log.e("TAG","版本号"+packInfo.versionCode);
        Log.e("TAG","版本名"+packInfo.versionName);
        return packInfo.versionName;
    }
}

