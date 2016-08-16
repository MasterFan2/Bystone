package com.proton.bystone.ui.main.tab.home;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BitmapUtils;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.EventResp;
import com.proton.bystone.bean.JsonResp;
import com.proton.bystone.bean.Region;
import com.proton.bystone.location.LocationManager;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.ui.main.tab.HomeFragment;
import com.proton.bystone.ui.shopcar.ShopCarActivity;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/*
定位
*/

/**
 * Created by Administrator on 2016/7/14.
 */
@MTFActivityFeature(layout = R.layout.homeserch)
public class Homeserch  extends MTFBaseActivity {



    Button viewById;
    String s;
    List<Region> region;
    GridView gridView;
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
        LocationManager.getInstance().stopLocation();
    }

    public void Listener()
    {

/*        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = viewById.getText().toString();

                Log.e("ss",s);
                SharedPreferences sh=getSharedPreferences("chongqing", Activity.MODE_WORLD_READABLE);
                SharedPreferences.Editor editor=sh.edit();//会创建一个editor对象，要注意。
                editor.putString("s", s);
                editor.commit();
                finish();

            }
        });*/

        Category_name();

        gridView=(GridView)findViewById(R.id.shop_gridView);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context,position,Toast.LENGTH_LONG).show();
                /*dingwei();*/
            }
        });


    }


    @OnClick(R.id.m_title_left_btn)
    public void back(View view) {
        LocationManager.getInstance().stopLocation();
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
               /* Toast.makeText(context,"地址定位中",Toast.LENGTH_SHORT).show();*/

            }
        });
        LocationManager.getInstance().startLocation();
    }


    public void Category_name()
    {
        // LoginParams loginParams = new LoginParams(ed.getText().toString().trim(), "666888", "xxaabbc085412556sxxx", 1);

        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetArea")
                .gson(new Gson())
                .noParams()
                // .object(loginParams)
                /*.typeValue("string","")*/
                .build();
//        ParamsBuilder<LoginParams> builder = new ParamsBuilder<LoginParams>().
//                .key("")
//                .build();
        //分类名称
        Call<BaseResp> call = HttpClients.getInstance().maintenance(requestBody);

        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                String data = response.body().getData();
                Log.e("地区列表",data+"");
                jx(data);

            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                Log.e("111","失败");
            }
        });


    }

    public void jx(String data)
    {
       region=new Gson().fromJson(data, new TypeToken<List<Region>>() {}.getType());

        gridView.setAdapter(new GridViewadapter2());


    }


    class GridViewadapter2 extends BaseAdapter
    {


        @Override
        public int getCount() {
            // TODO Auto-generated method stub

            return  region == null ? 0 : region.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return region.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            viewHolder1 holder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.home_serrce, null);
                holder = new viewHolder1();
                holder.headline = (TextView) convertView.findViewById(R.id.home_bt);
                convertView.setTag(holder);
            }else{
                holder = (viewHolder1) convertView.getTag();
            }

            holder.headline.setText(region.get(position).getNAME());



            return convertView;
        }
    }

    class viewHolder1 {
        TextView headline;


    }


}
