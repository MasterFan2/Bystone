package com.proton.bystone.ui.shop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
            rq();

        }else if(number.equals("1"))
        {
            maintain();

        }else if(number.equals("2"))
        {
            maintain4();
        }else if(number.equals("3"))
        {
            maintain2();



        }else if(number.equals("4"))
        {
            maintain5();
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
            }
        });

        //待保养
        my_all3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                maintain();

            }
        });

       //待收货
        my_all4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maintain4();
            }
        });

        //待付款
        my_all2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maintain2();
            }
        });

       //待评价
        my_all5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maintain5();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent t=new Intent(My_Indext.this,OrderStateActivity.class);
                    t.putExtra("code",htt.get(position).getBookCode());
                    startActivity(t);

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
         //      Log.e("待评价",data);
                maintain2(data);
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
                holder.wait = (TextView) convertView.findViewById(R.id.wait);//等待
                holder.my_minefor = (TextView) convertView.findViewById(R.id.my_minefor);//DATA
                holder.my_car_number = (TextView) convertView.findViewById(R.id.my_car_number);//渝A888
                convertView.setTag(holder);
            }else{
                holder = (viewHolder1) convertView.getTag();
            }

            holder.my_by.setText(pj.get(position).getOConsumerName());
            holder.wait.setText(pj.get(position).getOrderType()+"");
//            holder.my_minefor.setText(yj);
            holder.my_car_number.setText(pj.get(position).getBookCode());

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
                maintain2(data);
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
                holder.wait = (TextView) convertView.findViewById(R.id.wait);//等待
                holder.my_minefor = (TextView) convertView.findViewById(R.id.my_minefor);//DATA
                holder.my_car_number = (TextView) convertView.findViewById(R.id.my_car_number);//渝A888
                convertView.setTag(holder);
            }else{
                holder = (viewHolder1) convertView.getTag();
            }

            holder.my_by.setText(shouhuo.get(position).getOConsumerName());
            holder.wait.setText(shouhuo.get(position).getOrderType()+"");
//            holder.my_minefor.setText(yj);
            holder.my_car_number.setText(shouhuo.get(position).getBookCode());

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
              //  Log.e("待付款",data);
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

            return httq.size();
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
                holder.wait = (TextView) convertView.findViewById(R.id.wait);//等待
                holder.my_minefor = (TextView) convertView.findViewById(R.id.my_minefor);//DATA
                holder.my_car_number = (TextView) convertView.findViewById(R.id.my_car_number);//渝A888
                convertView.setTag(holder);
            }else{
                holder = (viewHolder1) convertView.getTag();
            }

            holder.my_by.setText(httq.get(position).getOConsumerName());
            holder.wait.setText(httq.get(position).getOrderType()+"");
//            holder.my_minefor.setText(yj);
            holder.my_car_number.setText(httq.get(position).getBookCode());

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

            return htt.size();
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
                holder.wait = (TextView) convertView.findViewById(R.id.wait);//等待
                holder.my_minefor = (TextView) convertView.findViewById(R.id.my_minefor);//DATA
                holder.my_car_number = (TextView) convertView.findViewById(R.id.my_car_number);//渝A888
                convertView.setTag(holder);
            }else{
                holder = (viewHolder1) convertView.getTag();
            }

            holder.my_by.setText(htt.get(position).getOConsumerName());
            holder.wait.setText(htt.get(position).getOrderType()+"");
//            holder.my_minefor.setText(yj);
            holder.my_car_number.setText(htt.get(position).getBookCode());




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

    class goodrq extends BaseAdapter
    {

        public goodrq()
        {
            utils2 = new BitmapUtils(My_Indext.this);
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
                convertView = View.inflate(My_Indext.this, R.layout.shop_qborder, null);//标题
                holder = new viewHolder1();
                holder.my_by = (TextView) convertView.findViewById(R.id.my_by);//上门保养
                holder.wait = (TextView) convertView.findViewById(R.id.wait);//等待
                holder.my_minefor = (TextView) convertView.findViewById(R.id.my_minefor);//DATA
                holder.my_car_number = (TextView) convertView.findViewById(R.id.my_car_number);//渝A888
                convertView.setTag(holder);
            }else{
                holder = (viewHolder1) convertView.getTag();
            }
            holder.my_by.setText(hp.get(position).getOConsumerName());
            holder.wait.setText(hp.get(position).getOrderType()+"");
          //  holder.my_minefor.setText(yj);
            holder.my_car_number.setText(hp.get(position).getBookCode());

            return convertView;
        }
    }

    class viewHolder1
    {
        TextView my_minefor;
        TextView wait;
        TextView my_by;
        TextView my_car_number;


    }

    @butterknife.OnClick(R.id.m_title_left_btn)
    public void back(View view) {
        editor.commit();
        finish();

    }



}
