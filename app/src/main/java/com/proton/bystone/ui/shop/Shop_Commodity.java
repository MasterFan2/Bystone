package com.proton.bystone.ui.shop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BitmapUtils;
import com.proton.bystone.R;
import com.proton.bystone.bean.Advert;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.Bean_a;
import com.proton.bystone.bean.Category_nameResp;
import com.proton.bystone.bean.JsonResp;
import com.proton.bystone.bean.Shop_List;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/27.
 * 点汽机油后出现的商品
 */
@MTFActivityFeature(layout = R.layout.shop_shangping)
public class Shop_Commodity extends MTFBaseActivity {
    String ps_code;//品分类编号
    BitmapUtils   utils2;
    List<Shop_List>  listfor;
    Shop_List shop_list;
    List<Shop_List> sortOp;
    List<Shop_List>  hp;
    ;

    @Bind(R.id.shop_commodity)
    ListView lv;

    //按价格排序
    @Bind(R.id.shop_cast)
    ImageView img;

    //好评
    @Bind(R.id.shop_go)
    ImageView shop_go;

    //人气
    @Bind(R.id.shop_rq)
    ImageView shop_rq;

    //按好评排序
    @Bind(R.id.shop_good)
    TextView shop_good;

    //按好评排序
    @Bind(R.id.shop_jggd)
    TextView shop_jggd;

    //按人气排序
    @Bind(R.id.shop_rqzg)
    TextView shop_rqzg;

    @Override
    public void initialize(Bundle savedInstanceState) {
          ps_code=getIntent().getStringExtra("ps_code");
         Category_name();
         Listener();
    }

    public void Listener()
    {
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnim();
                //获取价格从低到搞数据
                price();
            }
        });
//点击价格高低
        shop_jggd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setVisibility(View.VISIBLE);
                shop_go.setVisibility(View.GONE);
                shop_rq.setVisibility(View.GONE);
                Category_name();


            }
        });

        shop_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                img.setVisibility(View.GONE);
                shop_go.setVisibility(View.VISIBLE);
                shop_rq.setVisibility(View.GONE);
                //获取价格从低到搞数据
                good();
            }
        });
//按人气
        shop_rqzg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                img.setVisibility(View.GONE);
                shop_go.setVisibility(View.GONE);
                shop_rq.setVisibility(View.VISIBLE);
                //获取人气排序
                rq();
            }
        });


        shop_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                img.setVisibility(View.GONE);
                shop_go.setVisibility(View.VISIBLE);
                shop_rq.setVisibility(View.GONE);
                //获取价格从低到搞数据
                good();
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String vccode=listfor.get(position).getVC_Code();
                Intent t= new Intent(Shop_Commodity.this,Shop_Detail.class);
                t.putExtra("vccode",vccode);
                startActivity(t);

            }
        });
    }

    //解析人气从低搞到数据
    public void rq() {
        // LoginParams loginParams = new LoginParams(ed.getText().toString().trim(), "666888", "xxaabbc085412556sxxx", 1);

        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetCommodityIsClassNumber")
                .gson(new Gson())
                /*.noParams()*/
                // .object(loginParams)
                .typeValue("string", ps_code)
                .typeValue("int", 0)
                .typeValue("int", 9)
                .typeValue("int", 2)
                .build();
//        ParamsBuilder<LoginParams> builder = new ParamsBuilder<LoginParams>().
//                .key("")
//                .build();
        //分类名称
        Call<BaseResp> call = HttpClients.getInstance().commodity(requestBody);

        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {


                String data = response.body().getData();
              Log.e("3333",data);
                renqi(data);
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                Log.e("111", "失败");
            }
        });
    }


    //解析人气从低搞到数据
    public void renqi(String data)
    {
        hp=new Gson().fromJson(data, new TypeToken<List<Shop_List>>() {}.getType());
        lv.setAdapter(new goodrq());

    }
    //解析人气从低搞到数据
    class goodrq extends BaseAdapter
    {

        public goodrq()
        {
            utils2 = new BitmapUtils(Shop_Commodity.this);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub

            return hp.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return hp.get(position);
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
                convertView = View.inflate(Shop_Commodity.this, R.layout.shop_listview, null);//标题
                holder = new viewHolder1();
                //title
                holder.headline = (TextView) convertView.findViewById(R.id.headline);
                //原价...
                holder.money = (TextView) convertView.findViewById(R.id.money);
                //会员价...
                holder.member = (TextView) convertView.findViewById(R.id.member);
                //图片
                holder.image2 = (ImageView) convertView.findViewById(R.id.shop_listview_item);
                convertView.setTag(holder);
            }else{
                holder = (viewHolder1) convertView.getTag();
            }
            //动态java代码设置图片

            String aa = "http://192.168.0.119";
            //TITLE
//会员价
            double n_hyj = hp.get(position).getN_HYJ();
            String s = Double.toString(n_hyj);

//原价
            double yuanjia = hp.get(position).getN_HYJ();
            String yj = Double.toString(yuanjia);
            holder.headline.setText(hp.get(position).getVC_Name());
            holder.member.setText(s);//会员价
            holder.money.setText(yj);//原价


            utils2.display(holder.image2,aa+hp.get(position).getVC_Url());
            //   Picasso.with(context).load(aa+s).into(tuanti);

            return convertView;
        }
    }
    //获取好评从低到高数据
    public void good() {
        // LoginParams loginParams = new LoginParams(ed.getText().toString().trim(), "666888", "xxaabbc085412556sxxx", 1);

        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetCommodityIsClassNumber")
                .gson(new Gson())
                /*.noParams()*/
                // .object(loginParams)
                .typeValue("string", ps_code)
                .typeValue("int", 0)
                .typeValue("int", 9)
                .typeValue("int", 1)
                .build();
//        ParamsBuilder<LoginParams> builder = new ParamsBuilder<LoginParams>().
//                .key("")
//                .build();
        //分类名称
        Call<BaseResp> call = HttpClients.getInstance().commodity(requestBody);

        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {


                String data = response.body().getData();
//                Log.e("3333",data);
                evaluate(data);
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                Log.e("111", "失败");
            }
        });
    }


    //解析好评从低搞到数据
    public void evaluate(String data)
    {
        hp=new Gson().fromJson(data, new TypeToken<List<Shop_List>>() {}.getType());
        lv.setAdapter(new goodappraise());

    }
    //解析好评从低搞到数据
    class goodappraise extends BaseAdapter
    {

        public goodappraise()
        {
            utils2 = new BitmapUtils(Shop_Commodity.this);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub

            return hp.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return hp.get(position);
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
                convertView = View.inflate(Shop_Commodity.this, R.layout.shop_listview, null);//标题
                holder = new viewHolder1();
                //title
                holder.headline = (TextView) convertView.findViewById(R.id.headline);
                //原价...
                holder.money = (TextView) convertView.findViewById(R.id.money);
                //会员价...
                holder.member = (TextView) convertView.findViewById(R.id.member);
                //图片
                holder.image2 = (ImageView) convertView.findViewById(R.id.shop_listview_item);
                convertView.setTag(holder);
            }else{
                holder = (viewHolder1) convertView.getTag();
            }
            //动态java代码设置图片

            String aa = "http://192.168.0.119";
            //TITLE
//会员价
            double n_hyj = hp.get(position).getN_HYJ();
            String s = Double.toString(n_hyj);
//原价
            double yuanjia = hp.get(position).getN_HYJ();
            String yj = Double.toString(yuanjia);
            holder.headline.setText(hp.get(position).getVC_Name());
            holder.member.setText(s);//会员价
            holder.money.setText(yj);//原价


            utils2.display(holder.image2,aa+hp.get(position).getVC_Url());
            //   Picasso.with(context).load(aa+s).into(tuanti);

            return convertView;
        }
    }

    /**
     * 开启动画
     */
    private void startAnim() {
        // 动画集合
        AnimationSet set = new AnimationSet(true);
        // 旋转动画
        RotateAnimation rotate = new RotateAnimation(0, 180,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        rotate.setDuration(1000);// 动画时间
        rotate.setFillAfter(true);// 保持动画状态
        set.addAnimation(rotate);

        img.startAnimation(set);
    }

    @Override
    public void backPressed() {
       animFinish();
    }


    //获取价格从低到高数据
    public void price() {
        // LoginParams loginParams = new LoginParams(ed.getText().toString().trim(), "666888", "xxaabbc085412556sxxx", 1);

        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetCommodityIsClassNumber")
                .gson(new Gson())
                /*.noParams()*/
                // .object(loginParams)
                .typeValue("string", ps_code)
                .typeValue("int", 0)
                .typeValue("int", 9)
                .typeValue("int", 0)
                .build();
//        ParamsBuilder<LoginParams> builder = new ParamsBuilder<LoginParams>().
//                .key("")
//                .build();
        //分类名称
        Call<BaseResp> call = HttpClients.getInstance().commodity(requestBody);

        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {


                String data = response.body().getData();
//                Log.e("3333",data);
               price(data);
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                Log.e("111", "失败");
            }
        });
    }


    //解析价格从低搞到数据
    public void price(String data)
    {
        sortOp=new Gson().fromJson(data, new TypeToken<List<Shop_List>>() {}.getType());
        lv.setAdapter(new sortOp());

    }
    //解析价格从低搞到数据
    class sortOp extends BaseAdapter
    {

        public sortOp()
        {
            utils2 = new BitmapUtils(Shop_Commodity.this);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub

            return sortOp.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return sortOp.get(position);
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
                convertView = View.inflate(Shop_Commodity.this, R.layout.shop_listview, null);//标题
                holder = new viewHolder1();
                //title
                holder.headline = (TextView) convertView.findViewById(R.id.headline);
                //原价...
                holder.money = (TextView) convertView.findViewById(R.id.money);
                //会员价...
                holder.member = (TextView) convertView.findViewById(R.id.member);
                //图片
                holder.image2 = (ImageView) convertView.findViewById(R.id.shop_listview_item);
                convertView.setTag(holder);
            }else{
                holder = (viewHolder1) convertView.getTag();
            }
            //动态java代码设置图片

            String aa = "http://192.168.0.119";
            //TITLE
//会员价
            double n_hyj = sortOp.get(position).getN_HYJ();
            String s = Double.toString(n_hyj);
//原价
            double yuanjia = sortOp.get(position).getN_HYJ();
            String yj = Double.toString(yuanjia);
            holder.headline.setText(sortOp.get(position).getVC_Name());
            holder.member.setText(s);//会员价
            holder.money.setText(yj);//原价


            utils2.display(holder.image2,aa+sortOp.get(position).getVC_Url());
            //   Picasso.with(context).load(aa+s).into(tuanti);

            return convertView;
        }
    }

    //商品列表
    public void Category_name() {
        // LoginParams loginParams = new LoginParams(ed.getText().toString().trim(), "666888", "xxaabbc085412556sxxx", 1);

        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetCommodityIsClassNumber")
                .gson(new Gson())
                /*.noParams()*/
                // .object(loginParams)
                .typeValue("string", ps_code)
                .typeValue("int", 0)
                .typeValue("int", 9)
                .typeValue("int", 1)
                .build();
//        ParamsBuilder<LoginParams> builder = new ParamsBuilder<LoginParams>().
//                .key("")
//                .build();
        //分类名称
        Call<BaseResp> call = HttpClients.getInstance().commodity(requestBody);

        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {


                String data = response.body().getData();
              //  Log.e("111100000",data);
                htpjiexi(data);
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                Log.e("111", "失败");
            }
        });
    }

        //解析
    public void htpjiexi(String data)
    {
       listfor=new Gson().fromJson(data, new TypeToken<List<Shop_List>>() {}.getType());
        lv.setAdapter(new GridViewadapter2());

    }

    class GridViewadapter2 extends BaseAdapter
    {

        public GridViewadapter2()
        {
            utils2 = new BitmapUtils(Shop_Commodity.this);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub

            return listfor.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return listfor.get(position);
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
                convertView = View.inflate(Shop_Commodity.this, R.layout.shop_listview, null);//标题
                holder = new viewHolder1();
                //title
                holder.headline = (TextView) convertView.findViewById(R.id.headline);
                //原价...
                holder.money = (TextView) convertView.findViewById(R.id.money);
                //会员价...
                holder.member = (TextView) convertView.findViewById(R.id.member);
                //图片
                holder.image2 = (ImageView) convertView.findViewById(R.id.shop_listview_item);
                convertView.setTag(holder);
            }else{
                holder = (viewHolder1) convertView.getTag();
            }
            //动态java代码设置图片

            String aa = "http://192.168.0.119";
            //TITLE
//会员价
            double n_hyj = listfor.get(position).getN_HYJ();
            String s = Double.toString(n_hyj);
//原价
            double yuanjia = listfor.get(position).getN_HYJ();
            String yj = Double.toString(yuanjia);
            holder.headline.setText(listfor.get(position).getVC_Name());
            holder.member.setText(s);//会员价
             holder.money.setText(yj);//原价


       utils2.display(holder.image2,aa+listfor.get(position).getVC_Url());
          //   Picasso.with(context).load(aa+s).into(tuanti);

            return convertView;
        }
    }


    class viewHolder1
    {
        TextView headline;
        TextView print;
        TextView money;
        TextView member;
        ImageView image2;

    }




}

