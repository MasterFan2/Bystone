package com.proton.bystone.ui.main.tab.home;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.proton.bystone.R;
import com.proton.bystone.location.LocationManager;
import com.proton.bystone.ui.main.tab.HomeFragment;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import butterknife.Bind;
import butterknife.OnClick;
/*
定位
*/

/**
 * Created by Administrator on 2016/7/14.
 */
@MTFActivityFeature(layout = R.layout.homeserch)
public class Homeserch  extends MTFBaseActivity {

    @Bind(R.id.home_shi)
    Button home_shi;

    Button viewById;
    String s;
   /* @OnClick(R.id.Home_fh)
    public void back(View view) {
        animFinish();
    }*/

    @Override
    public void initialize(Bundle savedInstanceState) {
           Listener();
    }

    @Override
    public void backPressed() {
    animFinish();
    }

    public void Listener()
    {
        viewById =(Button) findViewById(R.id.home_shi);//重庆
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = viewById.getText().toString();
                dingwei();//定位中
                Log.e("ss",s);
                SharedPreferences sh=getSharedPreferences("chongqing", Activity.MODE_WORLD_READABLE);
                SharedPreferences.Editor editor=sh.edit();//会创建一个editor对象，要注意。
                editor.putString("s", s);
                editor.commit();
                finish();

            }
        });


    }


    @OnClick(R.id.m_title_left_btn)
    public void back(View view) {
        animFinish();
    }


    //定位
    public void dingwei()
    {
        //定位
        LocationManager.getInstance().init(context);
        LocationManager.getInstance().setAMapLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation location) {
                LocationManager.getInstance().stopLocation();
                Toast.makeText(context,"地址定位中",Toast.LENGTH_SHORT).show();

            }
        });
        LocationManager.getInstance().startLocation();
    }

}
