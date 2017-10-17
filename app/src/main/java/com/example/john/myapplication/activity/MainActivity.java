package com.example.john.myapplication.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.john.myapplication.R;

public class MainActivity extends BaseActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initFindViewById();
        initData();
        setListener();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //复写并且初始化菜单布局文件！
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add:
                toast("add");
                break;
            case R.id.delete_item:
                toast("delete");
                break;
            default:
        }

        return true;
    }

    /**
     * @author 复写返回键！
     */
    @Override
    public void onBackPressed() {
        Log.e(TAG, "onBackPressed: 点击了返回键！");
        super.onBackPressed();
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        setContentView(R.layout.activity_main);
    }

    @Override
    public void setListener() {

    }

    @Override
    protected void initFindViewById() {
        textView = findView(R.id.myText);
    }

    @Override
    public void widgetClick(View v) {

    }

}
