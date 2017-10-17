package com.example.john.myapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.john.myapplication.tools_used.ActivityController;

/**
 * Created by John on 2017/8/24.
 */

public abstract class BaseActivity extends Activity implements View.OnClickListener {


    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();


    /**
     * 是否沉浸状态栏
     **/
    private boolean isSetStatusBar = false;

    /** 是否允许全屏 **/
    private boolean mAllowFullScreen = false;

    /**
     * 是否允许debug输出日至！
     */
    private boolean isDebug =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initFindViewById();
        initData();
        setListener();
        initEvent();
        /**
         * @see--此方法表示把所有的Activity放到一个数组里面，便于一次杀死！
         */
        ActivityController.addActivity(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * @see--当此页面结束的时候，顺便把数组里面的这个Activity结束了！方便剩下的一次杀死！
         */
        ActivityController.removeActivity(this);
        /**
         * @see--杀死当前的这个进程！
         *@see--获取当前进程的id，然后传入程序里面，杀死！
         */
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * [日志输出]
     *
     * @param msg
     */
    protected void $Log(String msg) {
        if (isDebug) {
            Log.d(TAG, msg);
        }
    }

    /**
     * @param id
     * @param <T>
     * @return
     * @see---自定义获取控件初始化！
     */
    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    /**
     * @param
     * @author xiaojia
     * @see---初始化数据
     */
    public abstract void initData();

    /**
     * @param
     * @author xiaojia
     * @see---初始化View
     */
    public abstract void initView();


    /**
     * [设置监听]
     */
    public abstract void setListener();

    /**
     * @see--初始化空间！
     */
    protected abstract void initFindViewById();

    /**
     * @see----初始化事件！
     */
    protected void initEvent() {

    }

    /**
     * View点击
     **/
    public abstract void widgetClick(View v);

    /**
     * @param text
     * @see--自定义Toaast
     */
    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {
        widgetClick(v);
    }

    /**
     * [沉浸状态栏]
     */
    /**
     * [是否设置沉浸状态栏]
     *
     * @param-allowFullScreen
     */
    public void setSteepStatusBar(boolean isSetStatusBar) {
        this.isSetStatusBar = isSetStatusBar;
    }

    /**
     * [是否允许全屏]
     *
     * @param allowFullScreen
     */
    public void setAllowFullScreen(boolean allowFullScreen) {
        this.mAllowFullScreen = allowFullScreen;
    }

    /**
     * [页面跳转]
     * @param clz
     */
    private void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseActivity.this, clz));
    }

    /**
     * [携带数据的页面跳转]
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 隐藏软件盘
     */
    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 点击软键盘之外的空白处，隐藏软件盘
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 显示软键盘
     */
    public void showInputMethod() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);
        }
    }

    // 返回键返回事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }
}
