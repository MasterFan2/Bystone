package com.proton.library.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.proton.library.R;
import com.proton.library.ui.annotation.MTFActivityFeature;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


/**
 * Created by MasterFan on 2016/1/14.
 * description:all Activity class must be extends this.
 */
public abstract class MTFBaseActivity extends AppCompatActivity {

    public Activity context;
    private List<Activity> activityList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        MTFActivityFeature activityFeature = MTFBaseActivity.this.getClass().getAnnotation(MTFActivityFeature.class);
        if(activityFeature == null) throw new IllegalArgumentException(">>> not set layout resources!");
        if(activityFeature.status_bar_color() > 0 ) setStatusBarColor(activityFeature.status_bar_color());
        setContentView(activityFeature.layout());
        ButterKnife.bind(this);
        initialize(savedInstanceState);
    }

    /**
     * set status bar color
     */
    public void setStatusBarColor(int statusBarColor){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Do something for lollipop and above versions
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(context, statusBarColor));
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(statusBarColor);
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 提示
     */
    public void serverBusy() {
        Toast.makeText(context, "服务器繁忙,请稍后再试...", Toast.LENGTH_SHORT).show();
    }

    /**
     * Initialization operations, such as fetch data and more....
     */
    public abstract void initialize(Bundle savedInstanceState);

    public abstract void backPressed();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backPressed();
    }

    /**
     * anim launch Activity
     * @param clazz class
     */
    public void animStart(Class clazz){
        Intent intent = new Intent(context, clazz);
        startActivity(intent);
        overridePendingTransition(R.anim.roll_up, R.anim.roll);
    }

    /**
     * anim launch Activity
     * @param intent intent
     */
    public void animStart(Intent intent){
        startActivity(intent);
        overridePendingTransition(R.anim.roll_up, R.anim.roll);
    }

    /**
     * start activity for result
     * @param requestCode
     * @param intent
     */
    public void animStartForResult(int requestCode, Intent intent) {
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.roll_up, R.anim.roll);
    }

    /**
     * start activity for result
     * @param requestCode
     * @param clazz
     */
    public void animStartForResult(int requestCode, Class clazz) {
        Intent intent = new Intent(context, clazz);
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.roll_up, R.anim.roll);
    }

    /**
     * anim launch for result
     * @param intent
     * @param requestCode
     */
    public void animStartForResult(Intent intent, int requestCode){
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.roll_up, R.anim.roll);
    }

    /**
     * anim finish Activity
     */
    public void animFinish(){
        finish();
        overridePendingTransition(0, R.anim.roll_down);
    }
}
