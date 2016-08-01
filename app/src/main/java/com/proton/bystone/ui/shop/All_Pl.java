package com.proton.bystone.ui.shop;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BitmapUtils;
import com.proton.bystone.R;
import com.proton.bystone.bean.JsonResp;
import com.proton.bystone.bean.Shop_Pl;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/25.
 * 查看全部评论
 */
@MTFActivityFeature(layout = R.layout.shop_alpinlun)
public class All_Pl extends MTFBaseActivity {
    String str;
    List<Shop_Pl> shop_list;
    ListView listview;
    BitmapUtils   utils3;
    String aa = "http://192.168.0.119";
    GridView   home_gridView;
    String[]ary;

    @Override
    public void initialize(Bundle savedInstanceState) {
        initview();
        all_pinglun();
    }

    @Override
    public void backPressed() {

    }

    public void initview()
    {
        SharedPreferences share=getSharedPreferences("info",Activity.MODE_WORLD_READABLE);
        str=share.getString("mb_code","");
        Log.e("str",str);


        listview =(ListView) findViewById(R.id.shop_pjlist);
    }


    //取出列表评论
    public void all_pinglun()
    {

        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetProductReviewsData")
                .gson(new Gson())
                /*.noParams()*/
                //.object(loginParams)
                .typeValue("string","2016052317205144748")//商品编号
                .typeValue("int",1)
                .typeValue("int",5)//查询第一个到第5个
                .build();
//

        Call<JsonResp> call = HttpClients.getInstance().shop_all_pinglun(requestBody);

        call.enqueue(new Callback<JsonResp>() {
            @Override
            public void onResponse(Call<JsonResp> call, Response<JsonResp> response) {
                String data = response.body().getData();
                Log.e("1312323",data+"");
                eventjiexi(data);
            }

            @Override
            public void onFailure(Call<JsonResp> call, Throwable t) {
                Log.e("111","失败");
            }
        });


    }

    //解析
    public void eventjiexi(String data)
    {
       shop_list=new Gson().fromJson(data, new TypeToken<List<Shop_Pl>>() {}.getType());

        String path = shop_list.get(0).getPath();
        ary = path.split(",");
        for (int i = 0; i < ary.length; i++) {
            Log.e("111111111111",ary[i]+"");
        }


        listview.setAdapter(new  JuleBuhomeadapter2());
    }


    class JuleBuhomeadapter2 extends BaseAdapter {


        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return shop_list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return shop_list.get(position);
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
                convertView = View.inflate(All_Pl.this, R.layout.shop_pllistview, null);//标题
                holder = new viewHolder1();

                holder.shop_name = (TextView) convertView.findViewById(R.id.shop_name);

                holder.shop_date = (TextView) convertView.findViewById(R.id.shop_date);

                holder.shop_pingjia = (TextView) convertView.findViewById(R.id.shop_pingjia);

                holder.shop_yxt = (TextView) convertView.findViewById(R.id.shop_yxt);

                holder.shop_pl = (TextView) convertView.findViewById(R.id.shop_pl);

                holder.shop_huifu2 = (TextView) convertView.findViewById(R.id.shop_huifu2);

                home_gridView = (GridView) convertView.findViewById(R.id.home_gridView);
                home_gridView.setAdapter(new GridViewadapter());

                convertView.setTag(holder);
            }else{
                holder = (viewHolder1) convertView.getTag();
            }
            //拿到商品编号以后查询商品明细
            //商品编号

            holder.shop_name.setText(shop_list.get(position).getNickName());
            holder.shop_yxt.setText(shop_list.get(position).getNickName());
            holder.shop_date.setText(shop_list.get(position).getCommentsTime());
            holder.shop_pl.setText(shop_list.get(position).getCommentsTime());
            holder.shop_pingjia.setText(shop_list.get(position).getCommContent());
            holder.shop_pl.setText(shop_list.get(position).getCommContent());
            holder.shop_huifu2.setText(shop_list.get(position).getCommContent());


            // Picasso.with(context).load(aa+s).into(tuanti);


            return convertView;

        }
    }


    class GridViewadapter extends BaseAdapter
    {

        public GridViewadapter()
        {
            utils3 = new BitmapUtils(All_Pl.this);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub

            return 3;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return shop_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            //convertView=View.inflate(mActivity, R.layout.julebuhomelistviewone, null);
            View view = View.inflate(All_Pl.this, R.layout.shop_list_tu,null);
            ImageView image2=(ImageView) view.findViewById(R.id.shop_image);

            String aa = "http://192.168.0.119";
            utils3.display(image2,aa+ary[position]);
            // Picasso.with(context).load(aa+s).into(tuanti);


            return view;

        }
    }


    class viewHolder1
    {
        TextView shop_name;
        TextView shop_date;

        TextView shop_pingjia;
        TextView shop_yxt;
        TextView shop_pl;
        TextView shop_huifu2;
        GridView   home_gridView;

    }

}
