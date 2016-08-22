package com.proton.bystone.ui.shop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BitmapUtils;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.Shop_All_Order;
import com.proton.bystone.bean.Shop_List;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.ui.login.LoginActivity;
import com.proton.bystone.ui.main.tab.MeFragment;
import com.proton.bystone.ui.maintenance.OrderStateActivity;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import java.util.List;

import butterknife.Bind;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/27.
 * 跳转我的订单
 */

@MTFActivityFeature(layout = R.layout.my_indent)
public class My_Indext extends MTFBaseActivity {
    @Bind(R.id.shop_frame)
    ListView lv;


    @Bind(R.id.my_all3)
    TextView my_all3;//待保养
    @Bind(R.id.my_all)
    TextView my_all;//全部订单
    @Bind(R.id.my_all2)
    TextView my_all2;//待付款
    @Bind(R.id.my_all4)
    TextView my_all4;//待收货
    @Bind(R.id.my_all5)
    TextView my_all5;//评价
    List<Shop_All_Order>  htt;
    List<Shop_All_Order>  httq;
    List<Shop_All_Order>   shouhuo;


    List<Shop_All_Order>   pj;
    List<Shop_All_Order>  hw;
    List<Shop_All_Order>   hp;
    String mb_code;
    BitmapUtils   utils2;
    String bookCode;
    SharedPreferences settings;
    SharedPreferences.Editor editor;





    @Override
    public void initialize(Bundle savedInstanceState) {
        SharedPreferences sp = this.getSharedPreferences("info",
                Context.MODE_PRIVATE);
        mb_code = sp.getString("mb_code", "");
      //  Log.e("全部订单获取会员编号",mb_code);


        String number = getIntent().getStringExtra("number");

        if(number.equals("0"))
        {
            rq();//全部订单
            //my_all.setTextColor(Color.parseColor("3c85ee"));
            my_all.setTextColor(Color.BLUE);
            my_all3.setTextColor(Color.GRAY);
            my_all4.setTextColor(Color.GRAY);
            my_all2.setTextColor(Color.GRAY);
            my_all5.setTextColor(Color.GRAY);

        }else if(number.equals("1"))
        {

            maintain2();
            my_all3.setTextColor(Color.BLUE);
            my_all.setTextColor(Color.GRAY);
            my_all4.setTextColor(Color.GRAY);
            my_all2.setTextColor(Color.GRAY);
            my_all5.setTextColor(Color.GRAY);

        }else if(number.equals("2"))
        {
            maintain4();//收货
            my_all3.setTextColor(Color.GRAY);
            my_all.setTextColor(Color.GRAY);
            my_all4.setTextColor(Color.GRAY);
            my_all2.setTextColor(Color.BLUE);
            my_all5.setTextColor(Color.GRAY);

        }else if(number.equals("3"))
        {
            maintain5();

            my_all3.setTextColor(Color.GRAY);
            my_all.setTextColor(Color.GRAY);
            my_all4.setTextColor(Color.BLUE);
            my_all2.setTextColor(Color.GRAY);
            my_all5.setTextColor(Color.GRAY);


        }else if(number.equals("4"))
        {

            maintain();//查询保养
            my_all3.setTextColor(Color.GRAY);
            my_all.setTextColor(Color.GRAY);
            my_all4.setTextColor(Color.GRAY);
            my_all2.setTextColor(Color.GRAY);
            my_all5.setTextColor(Color.BLUE);

        }

        Listener();


        settings = getSharedPreferences("hhp", 0);
        editor = settings.edit();


    }

    @Override
    public void backPressed() {
        animFinish();
    }

    public void Listener() {


       //全部订单
        my_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rq();
                my_all.setTextColor(Color.BLUE);
                my_all3.setTextColor(Color.GRAY);
                my_all4.setTextColor(Color.GRAY);
                my_all2.setTextColor(Color.GRAY);
                my_all5.setTextColor(Color.GRAY);
            }
        });

        //待保养
        my_all5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                maintain();//查询保养
                my_all3.setTextColor(Color.GRAY);
                my_all.setTextColor(Color.GRAY);
                my_all4.setTextColor(Color.GRAY);
                my_all2.setTextColor(Color.GRAY);
                my_all5.setTextColor(Color.BLUE);



            }
        });

        //待付款
        my_all3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maintain2();
                my_all3.setTextColor(Color.BLUE);
                my_all.setTextColor(Color.GRAY);
                my_all4.setTextColor(Color.GRAY);
                my_all2.setTextColor(Color.GRAY);
                my_all5.setTextColor(Color.GRAY);
            }
        });

       //待评价
        my_all4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maintain5();

                my_all3.setTextColor(Color.GRAY);
                my_all.setTextColor(Color.GRAY);
                my_all4.setTextColor(Color.BLUE);
                my_all2.setTextColor(Color.GRAY);
                my_all5.setTextColor(Color.GRAY);
            }
        });



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(hp!=null&&hp.size()>0) {
                    int orderType = hp.get(position).getOrderType();
                    if (1 == orderType) {
                        Intent t = new Intent(My_Indext.this, OrderStateActivity.class);
                        t.putExtra("code", hp.get(position).getBookCode());
                        startActivity(t);
                    }
                }else if(httq!=null&&httq.size()>0) {
                    int orderType2= httq.get(position).getOrderType();
                    if(1==orderType2) {
                        Intent t = new Intent(My_Indext.this, OrderStateActivity.class);
                        t.putExtra("code", httq.get(position).getBookCode());
                        startActivity(t);
                    }
                }else if(htt!=null&&htt.size()>0)
                {
                    int orderType3=htt.get(position).getOrderType();
                  if(1==orderType3) {
                    Intent t = new Intent(My_Indext.this, OrderStateActivity.class);
                    t.putExtra("code", htt.get(position).getBookCode());
                    startActivity(t);
                }
                }






            }
        });

        //待收货
        my_all2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               maintain4();//收货
                my_all3.setTextColor(Color.GRAY);
                my_all.setTextColor(Color.GRAY);
                my_all4.setTextColor(Color.GRAY);
                my_all2.setTextColor(Color.BLUE);
                my_all5.setTextColor(Color.GRAY);
            Log.e("111","111");

            }
        });




    }
    //查询待评价
    public void maintain5() {
        // LoginParams loginParams = new LoginParams(ed.getText().toString().trim(), "666888", "xxaabbc085412556sxxx", 1);

        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetMySearchOrder")
                .gson(new Gson())
                /*.noParams()*/
                // .object(loginParams)
                .typeValue("string", /*mb_code*/"201605261656057265")
                .typeValue("int",3)

                .build();
//        ParamsBuilder<LoginParams> builder = new ParamsBuilder<LoginParams>().
//                .key("")
//                .build();
        //分类名称
        Call<BaseResp> call = HttpClients.getInstance().orderInfo(requestBody);

        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {


                String data = response.body().getData();
              Log.e("待评价",data);
                lv.setAdapter(new Nul());
              //  maintain5(data);
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                Log.e("111", "失败");
            }
        });
    }
    //查询待评价订单
    public void maintain5(String data)
    {
        pj=new Gson().fromJson(data, new TypeToken<List<Shop_All_Order>>() {}.getType());
        lv.setAdapter(new maintainmy5());
        editor.putString("pj",pj.size()+"");

    }
    //查询待评价订单
    class maintainmy5 extends BaseAdapter
    {

        public maintainmy5()
        {
            utils2 = new BitmapUtils(My_Indext.this);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub

            return pj.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return pj.get(position);
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
                convertView = View.inflate(My_Indext.this, R.layout.shop_qborder, null);//标题
                holder = new viewHolder1();
                holder.my_by = (TextView) convertView.findViewById(R.id.my_by);//上门保养
                holder.wait = (TextView) convertView.findViewById(R.id.wait);//等待保养
                holder.my_minefor = (TextView) convertView.findViewById(R.id.my_minefor);//DATA
                holder.my_car_number = (TextView) convertView.findViewById(R.id.my_car_number);//渝A888
                holder.day = (TextView) convertView.findViewById(R.id.shop_day);//时间
                holder.OrderStatus = (TextView) convertView.findViewById(R.id.wait);//代付款等
                holder.icon_right = (ImageView) convertView.findViewById(R.id.shop_jt);//箭头向下
                holder.shop_listview_item = (ImageView) convertView.findViewById(R.id.shop_listview_item);//图
                holder.shop_map = (RelativeLayout) convertView.findViewById(R.id.shop_map);//是否显示图
                holder.headline = (TextView) convertView.findViewById(R.id.headline);//标题
                holder.money = (TextView) convertView.findViewById(R.id.money);//总价
                holder.member = (TextView) convertView.findViewById(R.id.member);//个数
                holder.shop_sfk = (TextView) convertView.findViewById(R.id.shop_sfk);//个数

                holder.shop_kf = (TextView) convertView.findViewById(R.id.shop_kf);
                holder.my_cancel = (TextView) convertView.findViewById(R.id.my_cancel);
                holder.shop_ok = (TextView) convertView.findViewById(R.id.shop_ok);
                holder.shop_sfko = (RelativeLayout) convertView.findViewById(R.id.shop_sfko);

                convertView.setTag(holder);
            }else{
                holder = (viewHolder1) convertView.getTag();
            }

 /*           holder.my_by.setText(httq.get(position).getOConsumerName());
            holder.wait.setText(httq.get(position).getOrderType()+"");

//            holder.my_minefor.setText(yj);

            holder.my_car_number.setText(httq.get(position).getBookCode());
            holder.shop_map.setVisibility(View.GONE);*/




            int orderType = httq.get(position).getOrderType();//订单类型
            switch (orderType)
            {
                case 1:
                    holder.my_by.setText("上门保养");
                    holder.shop_map.setVisibility(View.GONE);
                    holder.shop_sfko.setVisibility(View.GONE);
                    holder.shop_kf .setVisibility(View.INVISIBLE);
                    holder.shop_ok .setVisibility(View.VISIBLE);

                    holder.my_cancel .setText("取消预约");

                    holder.shop_ok .setText("联系客服");
                    holder.my_car_number.setText(View.VISIBLE);
                    holder.my_car_number.setText(hp.get(position).getBookCarNum());
                    break;
                case 2:
                    holder.my_by.setText("到店保养");
                    holder.shop_map.setVisibility(View.GONE);
                    break;
                case 3:

                    String aa = "http://192.168.0.119";
                    holder.my_by.setText("商城购物");
                    holder.my_car_number.setVisibility(View.GONE);
                    holder.icon_right.setImageResource(R.mipmap.icon_mai_bottom_1);
                    utils2.display(holder.shop_listview_item,aa+shouhuo.get(position).getVC_Url());
                    holder.headline.setText(pj.get(position).getVC_Name());
                    holder.money.setText(pj.get(position).getOSfJe());//总价
                    holder.member.setText(pj.get(position).getCmtyNumber()+"");//个数
                    holder.shop_sfk.setText(pj.get(position).getOSfJe());//实付款
                    if(3==orderType)
                    {
                        holder.shop_kf .setText("退货");
                        holder.my_cancel .setText("联系客服");
                        holder.shop_ok.setText("评价");
                    }
                    break;
            }

            int OrderStatus = shouhuo.get(position).getOrderStatus();
            switch (OrderStatus)
            {
                case 0:
                    holder.OrderStatus.setText("取消");
                    break;
                case 1:
                    holder.OrderStatus.setText("待付款");
                    break;
                case 2:
                    holder.OrderStatus.setText("待收货");
                    break;
                case 3:
                    holder.OrderStatus.setText("待评价");
                    break;
                case 4:
                    holder.OrderStatus.setText("退货审核中");
                    break;
                case 5:
                    holder.OrderStatus.setText("退货结束");
                    break;
                case 6:
                    holder.OrderStatus.setText("完成");
                    break;

                case 100:
                    holder.OrderStatus.setText("待保养");
                    break;
            }





            return convertView;
        }
    }





    //查询待收货订单
    public void maintain4() {
        // LoginParams loginParams = new LoginParams(ed.getText().toString().trim(), "666888", "xxaabbc085412556sxxx", 1);

        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetMySearchOrder")
                .gson(new Gson())
                /*.noParams()*/
                // .object(loginParams)
                .typeValue("string", /*mb_code*/"201605261656057265")
                .typeValue("int", 2)

                .build();
//        ParamsBuilder<LoginParams> builder = new ParamsBuilder<LoginParams>().
//                .key("")
//                .build();
        //分类名称
        Call<BaseResp> call = HttpClients.getInstance().orderInfo(requestBody);

        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {


                String data = response.body().getData();
             //   Log.e("待收货",data);
                maintain4(data);
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                Log.e("111", "失败");
            }
        });
    }
    //查询待收货订单
    public void maintain4(String data)
    {


        shouhuo=new Gson().fromJson(data, new TypeToken<List<Shop_All_Order>>() {}.getType());
        lv.setAdapter(new maintainmy4());
        editor.putString("shouhuo",shouhuo.size()+"");
        Log.e("shouhuo",shouhuo+"");

    }
    //查询待收货订单
    class maintainmy4 extends BaseAdapter
    {

        public maintainmy4()
        {
            utils2 = new BitmapUtils(My_Indext.this);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub

            return shouhuo.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return shouhuo.get(position);
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
                convertView = View.inflate(My_Indext.this, R.layout.shop_qborder, null);//标题
                holder = new viewHolder1();
                holder.my_by = (TextView) convertView.findViewById(R.id.my_by);//上门保养
                holder.wait = (TextView) convertView.findViewById(R.id.wait);//等待保养
                holder.my_minefor = (TextView) convertView.findViewById(R.id.my_minefor);//DATA
                holder.my_car_number = (TextView) convertView.findViewById(R.id.my_car_number);//渝A888
                holder.day = (TextView) convertView.findViewById(R.id.shop_day);//时间
                holder.OrderStatus = (TextView) convertView.findViewById(R.id.wait);//代付款等
                holder.icon_right = (ImageView) convertView.findViewById(R.id.shop_jt);//箭头向下
                holder.shop_listview_item = (ImageView) convertView.findViewById(R.id.shop_listview_item);//图
                holder.shop_map = (RelativeLayout) convertView.findViewById(R.id.shop_map);//是否显示图
                holder.headline = (TextView) convertView.findViewById(R.id.headline);//标题
                holder.money = (TextView) convertView.findViewById(R.id.money);//总价
                holder.member = (TextView) convertView.findViewById(R.id.member);//个数
                holder.shop_sfk = (TextView) convertView.findViewById(R.id.shop_sfk);//个数

                holder.shop_kf = (TextView) convertView.findViewById(R.id.shop_kf);
                holder.my_cancel = (TextView) convertView.findViewById(R.id.my_cancel);
                holder.shop_ok = (TextView) convertView.findViewById(R.id.shop_ok);
                holder.shop_sfko = (RelativeLayout) convertView.findViewById(R.id.shop_sfko);

                convertView.setTag(holder);
            }else{
                holder = (viewHolder1) convertView.getTag();
            }

            int orderType = shouhuo.get(position).getOrderType();//订单类型
            switch (orderType)
            {
                case 1:
                    holder.my_by.setText("上门保养");
                    holder.shop_map.setVisibility(View.GONE);
                    holder.shop_sfko.setVisibility(View.GONE);
                    holder.shop_kf .setVisibility(View.VISIBLE);
                    holder.shop_kf .setText("取消预约");
                    holder.my_cancel .setText("联系客服");
                    holder.shop_ok.setText("确认付款");
                   /* holder.my_car_number.setText(View.VISIBLE);
                   holder.my_car_number.setText(hp.get(position).getBookCarNum());*/
                    break;
                case 2:
                    holder.my_by.setText("到店保养");
                    holder.shop_map.setVisibility(View.GONE);
                    break;
                case 3:

                    String aa = "http://192.168.0.119";
                    holder.my_by.setText("商城购物");
                    holder.my_car_number.setVisibility(View.GONE);
                    holder.icon_right.setImageResource(R.mipmap.icon_mai_bottom_1);
                    utils2.display(holder.shop_listview_item,aa+shouhuo.get(position).getVC_Url());
                    holder.headline.setText(shouhuo.get(position).getVC_Name());
                    holder.money.setText(shouhuo.get(position).getOSfJe());//总价
                    holder.member.setText(shouhuo.get(position).getCmtyNumber()+"");//个数
                    holder.shop_sfk.setText(shouhuo.get(position).getOSfJe());//实付款

                        holder.shop_kf .setText("联系物流");
                        holder.my_cancel .setText("查看客服");
                        holder.shop_ok.setText("确认收货");

                    //确认收货
                    holder.shop_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent t=new Intent(My_Indext.this,shop_Sh.class);
                            startActivity(t);
                        }
                    });

                    //联系物流
                    holder.shop_kf.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent t=new Intent(My_Indext.this,Shop_Ckwu.class);
                            startActivity(t);
                        }
                    });









                    break;
            }

            int OrderStatus = shouhuo.get(position).getOrderStatus();
            switch (OrderStatus)
            {
                case 0:
                    holder.OrderStatus.setText("取消");
                    break;
                case 1:
                    holder.OrderStatus.setText("待付款");
                    break;
                case 2:
                    holder.OrderStatus.setText("待收货");
                    break;
                case 3:
                    holder.OrderStatus.setText("待评价");
                    break;
                case 4:
                    holder.OrderStatus.setText("退货审核中");
                    break;
                case 5:
                    holder.OrderStatus.setText("退货结束");
                    break;
                case 6:
                    holder.OrderStatus.setText("完成");
                    break;

                case 100:
                    holder.OrderStatus.setText("待保养");
                    break;
            }




            return convertView;
        }
    }




    //查询待付款订单
    public void maintain2() {
        // LoginParams loginParams = new LoginParams(ed.getText().toString().trim(), "666888", "xxaabbc085412556sxxx", 1);

        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetMySearchOrder")
                .gson(new Gson())
                /*.noParams()*/
                // .object(loginParams)
                .typeValue("string", "201605261656057265")
                .typeValue("int", 1)

                .build();
//        ParamsBuilder<LoginParams> builder = new ParamsBuilder<LoginParams>().
//                .key("")
//                .build();
        //分类名称
        Call<BaseResp> call = HttpClients.getInstance().orderInfo(requestBody);

        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {


                String data = response.body().getData();
               Log.e("待付款",data);
                maintain2(data);
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                Log.e("111", "失败");
            }
        });
    }
    //查询待付款订单
    public void maintain2(String data)
    {
        httq=new Gson().fromJson(data, new TypeToken<List<Shop_All_Order>>() {}.getType());
        lv.setAdapter(new maintainmy2());
        editor.putString("httq",httq.size()+"");


    }
    //查询待付款订单
    class maintainmy2 extends BaseAdapter
    {

        public maintainmy2()
        {
            utils2 = new BitmapUtils(My_Indext.this);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub

            return httq == null ? 0 : httq.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return httq.get(position);
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
                convertView = View.inflate(My_Indext.this, R.layout.shop_qborder, null);//标题
                holder = new viewHolder1();
                holder.my_by = (TextView) convertView.findViewById(R.id.my_by);//上门保养
                holder.wait = (TextView) convertView.findViewById(R.id.wait);//等待保养
                holder.my_minefor = (TextView) convertView.findViewById(R.id.my_minefor);//DATA
                holder.my_car_number = (TextView) convertView.findViewById(R.id.my_car_number);//渝A888
                holder.day = (TextView) convertView.findViewById(R.id.shop_day);//时间
                holder.OrderStatus = (TextView) convertView.findViewById(R.id.wait);//代付款等
                holder.icon_right = (ImageView) convertView.findViewById(R.id.shop_jt);//箭头向下
                holder.shop_listview_item = (ImageView) convertView.findViewById(R.id.shop_listview_item);//图
                holder.shop_map = (RelativeLayout) convertView.findViewById(R.id.shop_map);//是否显示图
                holder.headline = (TextView) convertView.findViewById(R.id.headline);//标题
                holder.money = (TextView) convertView.findViewById(R.id.money);//总价
                holder.member = (TextView) convertView.findViewById(R.id.member);//个数
                holder.shop_sfk = (TextView) convertView.findViewById(R.id.shop_sfk);//个数

                holder.shop_kf = (TextView) convertView.findViewById(R.id.shop_kf);
                holder.my_cancel = (TextView) convertView.findViewById(R.id.my_cancel);
                holder.shop_ok = (TextView) convertView.findViewById(R.id.shop_ok);
                holder.shop_sfko = (RelativeLayout) convertView.findViewById(R.id.shop_sfko);

                convertView.setTag(holder);
            }else{
                holder = (viewHolder1) convertView.getTag();
            }

 /*           holder.my_by.setText(httq.get(position).getOConsumerName());
            holder.wait.setText(httq.get(position).getOrderType()+"");

//            holder.my_minefor.setText(yj);

            holder.my_car_number.setText(httq.get(position).getBookCode());
            holder.shop_map.setVisibility(View.GONE);*/




            int orderType = httq.get(position).getOrderType();//订单类型
            switch (orderType)
            {
                case 1:
                    holder.my_by.setText("上门保养");
                    holder.shop_map.setVisibility(View.GONE);
                    holder.shop_sfko.setVisibility(View.GONE);
                    holder.shop_kf .setVisibility(View.INVISIBLE);

                    //holder.shop_kf .setText("取消预约");
                    holder.my_cancel .setText("联系客服");
                    holder.shop_ok.setText("确认付款");
                   /* holder.my_car_number.setText(View.VISIBLE);
                   holder.my_car_number.setText(hp.get(position).getBookCarNum());*/
                    //确认付款
                    holder.shop_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent t=new Intent(My_Indext.this,My_Shop_Pay.class);
                            startActivity(t);
                        }
                    });
                    break;
                case 2:
                    holder.my_by.setText("到店保养");
                    holder.shop_map.setVisibility(View.GONE);
                    break;
                case 3:

                    String aa = "http://192.168.0.119";
                    holder.my_by.setText("商城购物");
                    holder.my_car_number.setVisibility(View.GONE);
                    holder.icon_right.setImageResource(R.mipmap.icon_mai_bottom_1);
                    utils2.display(holder.shop_listview_item,aa+httq.get(position).getVC_Url());
                    holder.headline.setText(httq.get(position).getVC_Name());
                    holder.money.setText(httq.get(position).getOSfJe());//总价
                    holder.member.setText(httq.get(position).getCmtyNumber()+"");//个数
                    holder.shop_sfk.setText(httq.get(position).getOSfJe());//实付款
                    if(3==orderType)
                    {
                        holder.shop_kf .setText("取消");
                        holder.my_cancel .setText("联系客服");
                        holder.shop_ok.setText("确认付款");
                        //确认付款
                        holder.shop_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent t=new Intent(My_Indext.this,My_Shop_Pay.class);
                                startActivity(t);
                            }
                        });
                    }
                    break;
            }

            int OrderStatus = httq.get(position).getOrderStatus();
            switch (OrderStatus)
            {
                case 0:
                    holder.OrderStatus.setText("取消");
                    break;
                case 1:
                    holder.OrderStatus.setText("待付款");
                    break;
                case 2:
                    holder.OrderStatus.setText("待收货");
                    break;
                case 3:
                    holder.OrderStatus.setText("待评价");
                    break;
                case 4:
                    holder.OrderStatus.setText("退货审核中");
                    break;
                case 5:
                    holder.OrderStatus.setText("退货结束");
                    break;
                case 6:
                    holder.OrderStatus.setText("完成");
                    break;

                case 100:
                    holder.OrderStatus.setText("待保养");
                    break;
            }








            return convertView;
        }
    }


    //查询待保养订单
    public void maintain() {
        // LoginParams loginParams = new LoginParams(ed.getText().toString().trim(), "666888", "xxaabbc085412556sxxx", 1);

        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetMySearchOrder")
                .gson(new Gson())
                /*.noParams()*/
                // .object(loginParams)
                .typeValue("string", /*mb_code*/"201605261656057265")
                .typeValue("int", 4)

                .build();
//        ParamsBuilder<LoginParams> builder = new ParamsBuilder<LoginParams>().
//                .key("")
//                .build();
        //分类名称
        Call<BaseResp> call = HttpClients.getInstance().orderInfo(requestBody);

        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {


                String data = response.body().getData();
                Log.e("待保养订单",data);

                maintain(data);


            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                Log.e("111", "失败");
            }
        });
    }

    public void maintain(String data)
    {
        htt=new Gson().fromJson(data, new TypeToken<List<Shop_All_Order>>() {}.getType());
        lv.setAdapter(new maintainmy());

        editor.putString("htt",htt.size()+"");




    }

    class maintainmy extends BaseAdapter
    {

        public maintainmy()
        {
            utils2 = new BitmapUtils(My_Indext.this);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub

            return htt == null ? 0 : htt.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return htt.get(position);
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
                convertView = View.inflate(My_Indext.this, R.layout.shop_qborder, null);//标题
                holder = new viewHolder1();
                holder.my_by = (TextView) convertView.findViewById(R.id.my_by);//上门保养
                holder.wait = (TextView) convertView.findViewById(R.id.wait);//等待保养
                holder.my_minefor = (TextView) convertView.findViewById(R.id.my_minefor);//DATA
                holder.my_car_number = (TextView) convertView.findViewById(R.id.my_car_number);//渝A888
                holder.day = (TextView) convertView.findViewById(R.id.shop_day);//时间
                holder.OrderStatus = (TextView) convertView.findViewById(R.id.wait);//代付款等
                holder.icon_right = (ImageView) convertView.findViewById(R.id.shop_jt);//箭头向下
                holder.shop_listview_item = (ImageView) convertView.findViewById(R.id.shop_listview_item);//图
                holder.shop_map = (RelativeLayout) convertView.findViewById(R.id.shop_map);//是否显示图
                holder.headline = (TextView) convertView.findViewById(R.id.headline);//标题
                holder.money = (TextView) convertView.findViewById(R.id.money);//总价
                holder.member = (TextView) convertView.findViewById(R.id.member);//个数
                holder.shop_sfk = (TextView) convertView.findViewById(R.id.shop_sfk);//个数

                holder.shop_kf = (TextView) convertView.findViewById(R.id.shop_kf);
                holder.my_cancel = (TextView) convertView.findViewById(R.id.my_cancel);
                holder.shop_ok = (TextView) convertView.findViewById(R.id.shop_ok);
                holder.shop_sfko = (RelativeLayout) convertView.findViewById(R.id.shop_sfko);

                convertView.setTag(holder);
            }else{
                holder = (viewHolder1) convertView.getTag();
            }

            int orderType = htt.get(position).getOrderType();
            switch (orderType)
            {
                case 1:
                    holder.my_by.setText("上门保养");
                    holder.shop_map.setVisibility(View.GONE);
                    holder.shop_sfko.setVisibility(View.GONE);
                    holder.shop_kf .setVisibility(View.INVISIBLE);
                    holder.shop_ok .setVisibility(View.VISIBLE);

                    holder.my_cancel .setText("取消预约");

                    holder.shop_ok .setText("联系客服");
                  /*  holder.my_car_number.setText(View.VISIBLE);
                   holder.my_car_number.setText(hp.get(position).getBookCarNum());*/
                    break;

            }

            int OrderStatus = htt.get(position).getOrderStatus();
            switch (OrderStatus)
            {
                case 0:
                    holder.OrderStatus.setText("取消");
                    break;
                case 1:
                    holder.OrderStatus.setText("待付款");
                    break;
                case 2:
                    holder.OrderStatus.setText("待收货");
                    break;
                case 3:
                    holder.OrderStatus.setText("待评价");
                    break;
                case 4:
                    holder.OrderStatus.setText("退货审核中");
                    break;
                case 5:
                    holder.OrderStatus.setText("退货结束");
                    break;
                case 6:
                    holder.OrderStatus.setText("完成");
                    break;

                case 100:
                    holder.OrderStatus.setText("待保养");
                    break;
            }



            holder.day.setText(htt.get(position).getCreationTime());



            return convertView;
        }
    }




    //查询全部订单
    public void rq() {
        // LoginParams loginParams = new LoginParams(ed.getText().toString().trim(), "666888", "xxaabbc085412556sxxx", 1);

        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetMySearchOrder")
                .gson(new Gson())
                /*.noParams()*/
                // .object(loginParams)
                .typeValue("string", "201605261656057265")
                .typeValue("int", 0)

                .build();
//        ParamsBuilder<LoginParams> builder = new ParamsBuilder<LoginParams>().
//                .key("")
//                .build();
        //分类名称
        Call<BaseResp> call = HttpClients.getInstance().orderInfo(requestBody);

        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {

                String data = response.body().getData();
            Log.e("全部订单",data);
                renqi(data);
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                Log.e("111", "失败");
            }
        });
    }

    //解析全部订单
    public void renqi(String data)
    {
        hp=new Gson().fromJson(data, new TypeToken<List<Shop_All_Order>>() {}.getType());

        if(hp!=null) {
            lv.setAdapter(new goodrq());
        }

        editor.putString("hp",hp.size()+"");

    }

    class Nul extends BaseAdapter
    {

        public Nul()
        {
            utils2 = new BitmapUtils(My_Indext.this);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub

            return 0;
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

            return convertView;
        }
    }



    class goodrq extends BaseAdapter
    {

        public goodrq()
        {
            utils2 = new BitmapUtils(My_Indext.this);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub

            return hp == null ? 0 : hp.size();
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
                convertView = View.inflate(My_Indext.this, R.layout.shop_qborder, null);//标题
                holder = new viewHolder1();
                holder.my_by = (TextView) convertView.findViewById(R.id.my_by);//上门保养
                holder.wait = (TextView) convertView.findViewById(R.id.wait);//等待保养
                holder.my_minefor = (TextView) convertView.findViewById(R.id.my_minefor);//DATA
                holder.my_car_number = (TextView) convertView.findViewById(R.id.my_car_number);//渝A888
                holder.day = (TextView) convertView.findViewById(R.id.shop_day);//时间
                holder.OrderStatus = (TextView) convertView.findViewById(R.id.wait);//代付款等
                holder.icon_right = (ImageView) convertView.findViewById(R.id.shop_jt);//箭头向下
                holder.shop_listview_item = (ImageView) convertView.findViewById(R.id.shop_listview_item);//图
                holder.shop_map = (RelativeLayout) convertView.findViewById(R.id.shop_map);//是否显示图
                holder.headline = (TextView) convertView.findViewById(R.id.headline);//标题
                holder.money = (TextView) convertView.findViewById(R.id.money);//总价
                holder.member = (TextView) convertView.findViewById(R.id.member);//个数
                holder.shop_sfk = (TextView) convertView.findViewById(R.id.shop_sfk);//个数

                holder.shop_kf = (TextView) convertView.findViewById(R.id.shop_kf);
                holder.my_cancel = (TextView) convertView.findViewById(R.id.my_cancel);
                holder.shop_ok = (TextView) convertView.findViewById(R.id.shop_ok);
                holder.shop_sfko = (RelativeLayout) convertView.findViewById(R.id.shop_sfko);
             //   holder.ycx = (TextView) convertView.findViewById(R.id.ycx);
                convertView.setTag(holder);
            }else{
                holder = (viewHolder1) convertView.getTag();
            }

         int    orderType = hp.get(position).getOrderType();
            switch (orderType)
            {
                case 1:
                    holder.my_by.setText("上门保养");
                    holder.shop_map.setVisibility(View.GONE);
                    holder.shop_sfko.setVisibility(View.GONE);
                    holder.shop_kf .setVisibility(View.VISIBLE);
                    holder.shop_kf .setText("取消预约");
                    holder.my_cancel .setText("联系客服");
                    holder.shop_ok.setText("确认付款");
                   /* holder.my_car_number.setText(View.VISIBLE);
                   holder.my_car_number.setText(hp.get(position).getBookCarNum());*/
                    //取消预约
                    holder.shop_kf.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(My_Indext.this,"暂时无法取消预约",Toast.LENGTH_SHORT).show();
                        }
                    });
//确认付款
                    holder.shop_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent t=new Intent(My_Indext.this,My_Shop_Pay.class);
                            startActivity(t);
                        }
                    });
                    break;
                case 2:
                    holder.my_by.setText("到店保养");
                    holder.shop_map.setVisibility(View.GONE);
                    break;
                case 3:

                    String aa = "http://192.168.0.119";
                    holder.my_by.setText("商城购物");
                    holder.my_car_number.setVisibility(View.GONE);
                    holder.icon_right.setImageResource(R.mipmap.icon_mai_bottom_1);
                    utils2.display(holder.shop_listview_item,aa+hp.get(position).getVC_Url());
                    holder.headline.setText(hp.get(position).getVC_Name());
                    holder.money.setText(hp.get(position).getOSfJe());//总价
                    holder.member.setText(hp.get(position).getCmtyNumber()+"");//个数
                    holder.shop_sfk.setText(hp.get(position).getOSfJe());//实付款
                   // holder.ycx.setVisibility(View.VISIBLE);//线显现
                    if(3==orderType)
                    {
                        holder.shop_kf .setText("退货");
                        holder.my_cancel .setText("联系客服");
                        holder.shop_ok.setText("评价");

                        //退货
                        holder.shop_kf.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent t=new Intent(My_Indext.this,My_Tuihuo.class);
                                startActivity(t);
                            }
                        });
//评价
                        holder.shop_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent t=new Intent(My_Indext.this,shop_Sh.class);
                                startActivity(t);
                            }
                        });
                    }
                    break;
            }

            int OrderStatus = hp.get(position).getOrderStatus();
            switch (orderType)
            {
                case 0:
                    holder.OrderStatus.setText("取消");
                    break;
                case 1:
                    holder.OrderStatus.setText("待付款");
                    break;
                case 2:
                    holder.OrderStatus.setText("待收货");
                    break;
                case 3:
                    holder.OrderStatus.setText("待评价");
                    break;
                case 4:
                    holder.OrderStatus.setText("退货审核中");
                    break;
                case 5:
                    holder.OrderStatus.setText("退货结束");
                    break;
                case 6:
                    holder.OrderStatus.setText("完成");
                    break;

                case 100:
                    holder.OrderStatus.setText("待保养");
                    break;
            }




   /*        if("1".equals(orderType)) {
                holder.wait.setText("");
            }*/

            holder.day.setText(hp.get(position).getCreationTime());
            return convertView;
        }
    }

    class viewHolder1
    {
        TextView my_minefor;
        TextView wait;
        TextView my_by;
        TextView my_car_number;
        TextView day;
        TextView OrderStatus;
        ImageView icon_right;
        RelativeLayout shop_map;
        RelativeLayout shop_sfko;
        ImageView shop_listview_item;
        TextView headline;
        TextView money;
        TextView member;
        TextView shop_sfk;

        TextView shop_kf;
        TextView my_cancel;
        TextView shop_ok;
        //TextView ycx;






    }

    @butterknife.OnClick(R.id.m_title_left_btn)
    public void back(View view) {
        editor.commit();
        finish();

    }



}
