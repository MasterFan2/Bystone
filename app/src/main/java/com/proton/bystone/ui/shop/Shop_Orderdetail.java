package com.proton.bystone.ui.shop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BitmapUtils;
import com.proton.bystone.R;
import com.proton.bystone.bean.AddressMTF;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.Ddxq;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.ui.shopcar.ShopCarActivity;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import java.util.List;

import butterknife.Bind;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/20.
 * 订单详情表
 */
@MTFActivityFeature(layout = R.layout.shop_shop_orderdetail)
public class Shop_Orderdetail extends MTFBaseActivity {
    List<Ddxq> shop_pay_cs;
    ListView listview;
    TextView shop_yf;
    TextView shop_zje;
    TextView dikou;
    TextView shop_shr;
    AddressMTF shop_pay;
    String data;
    TextView shop_addr;
    TextView shop_phone;
    Button shop_th;

 /*   @Bind(R.id.Home_car)
    ImageView Home_car;*/
    BitmapUtils    utils;



    @Override
    public void initialize(Bundle savedInstanceState) {
        initview();
    }

    @Override
    public void backPressed() {

    }

    public void initview() {
        listview = (ListView) findViewById(R.id.shop_lv);
        shop_yf = (TextView) findViewById(R.id.shop_yf);//总运费
        shop_zje = (TextView) findViewById(R.id.shop_zje);//总金额
        dikou = (TextView) findViewById(R.id.dikou);//抵扣金币

        shop_shr = (TextView) findViewById(R.id.shop_shr);//收货人
        shop_addr = (TextView) findViewById(R.id.shop_addr);//地址
        shop_phone = (TextView) findViewById(R.id.shop_phone);//地址

        getData();

    /*    Home_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t= new Intent(Shop_Orderdetail.this,ShopCarActivity.class);
                startActivity(t);
            }
        });

        Home_fh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/


        TextView   m_title_left_btn=(TextView) findViewById(R.id.m_title_left_btn);
        m_title_left_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animFinish();
            }
        });


        TextView   m_title_right_btn=(TextView) findViewById(R.id.m_title_right_btn);
        m_title_right_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(Shop_Orderdetail.this, ShopCarActivity.class);
                startActivity(t);
            }
        });
    }

    public void getData() {
        wechat_payment();


    }

    //订单详情
    public void wechat_payment() {

        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetOrderDetail")
                .gson(new Gson())
                /*.noParams()*/
                //.object(loginParams)
                .typeValue("string", "201605091528083056")//订单编号
                .build();
//

        Call<BaseResp> call = HttpClients.getInstance().orderInfo(requestBody);

        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                data = response.body().getData();
                Log.e("订单详情数据", data + "");
              // jiexi(data);

            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                Log.e("111", "失败");
            }
        });


    }

    //解析
    public void jiexi(String data) {


            shop_pay_cs = new Gson().fromJson(data, new TypeToken<List<Ddxq>>() {
            }.getType());

            listview.setAdapter(new JuleBuhomeadapter2());
            shop_yf.setText(shop_pay_cs.get(0).getLogisticsPrice());//总运费
            shop_zje.setText(shop_pay_cs.get(0).getOSfJe());//总运费
            dikou.setText(shop_pay_cs.get(0).getOJbDk());//抵扣金币

            String address = shop_pay_cs.get(0).getAddress();


            shop_pay = new Gson().fromJson(address, new TypeToken<AddressMTF>() {
            }.getType());
            shop_shr.setText(shop_pay.getAd_Name());//收货人
            shop_addr.setText(shop_pay.getAd_Address());//地址
            shop_phone.setText(shop_pay.getAd_ContactNumber());



    }


   class JuleBuhomeadapter2 extends BaseAdapter {

       public JuleBuhomeadapter2() {

       utils = new BitmapUtils(Shop_Orderdetail.this);
       }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 1;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            viewHolder1 holder;
            if (convertView == null) {
                convertView = View.inflate(Shop_Orderdetail.this, R.layout.shop_wuliu, null);//标题
                holder = new viewHolder1();
                holder.img = (ImageView) convertView.findViewById(R.id.shop_listview_item);
                holder.shop_Nid = (TextView) convertView.findViewById(R.id.shop_Nid);
                holder.headline = (TextView) convertView.findViewById(R.id.headline);
                holder.prompt = (TextView) convertView.findViewById(R.id.prompt);
                holder.money = (TextView) convertView.findViewById(R.id.money);
                holder.shop_data = (TextView) convertView.findViewById(R.id.shop_data);
                holder.shop_th = (Button) convertView.findViewById(R.id.shop_th);//退货
                holder.ckwl = (Button) convertView.findViewById(R.id.ckwl);//查看物流
                holder.shop_shuijing = (Button) convertView.findViewById(R.id.shop_shuijing);//确认水晶

                convertView.setTag(holder);
            } else {
                holder = (viewHolder1) convertView.getTag();
            }

            holder.shop_Nid.setText(shop_pay_cs.get(0).getChildOrderNumber());
            holder.headline.setText(shop_pay_cs.get(0).getVC_Name());
            holder.prompt.setText(shop_pay_cs.get(0).getPadctBrief());
            holder.money.setText(shop_pay_cs.get(0).getOSpHyJ());
            holder.shop_data.setText(shop_pay_cs.get(0).getLogisticsTime());
            String aa = "http://192.168.0.119";
            utils.display( holder.img, aa  + shop_pay_cs.get(0).getVC_Url());

            //退货
            holder.shop_th.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent t = new Intent(Shop_Orderdetail.this, My_Tuihuo.class);
                    t.putExtra("childOrderNumber", shop_pay_cs.get(0).getChildOrderNumber());
                    t.putExtra("UserCode", shop_pay_cs.get(0).getUserCode());
                    t.putExtra("VCCode", shop_pay_cs.get(0).getVCCode());
                    t.putExtra("BsCode", shop_pay_cs.get(0).getBsCode());
                    startActivity(t);
                }
            });
            //查看物流
            holder.ckwl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent t = new Intent(Shop_Orderdetail.this, Shop_Ckwu.class);

                    t.putExtra("ExpressId", shop_pay_cs.get(0).getExpressId());//物流单号
                    t.putExtra("ExpressSimple", shop_pay_cs.get(0).getExpressSimple());//快递公司编号


                    startActivity(t);
                }
            });

            //确认收货
            holder.shop_shuijing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    qrsh();

                }
            });


            // Picasso.with(context).load(aa+s).into(tuanti);


            return convertView;

        }
    }


    //确认收货
    public void qrsh() {

        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("ConfirmReceipt")
                .gson(new Gson())
               /*.noParams()*/
                //.object(loginParams)
                .typeValue("string", shop_pay_cs.get(0).getUserCode())//会员编号
                .typeValue("string", "201605091528083056")//订单编号
                .build();


        Call<BaseResp> call = HttpClients.getInstance().orderInfo(requestBody);

        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                String data = response.body().getData();
                Log.e("确认收货", data + "");
                qr(data);


            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                Log.e("111", "失败");
            }
        });


    }

    public void qr(String data)
    {
        if("1".equals(data))
        {
            Toast.makeText(Shop_Orderdetail.this,"确认收货",Toast.LENGTH_LONG).show();
            Intent t = new Intent(Shop_Orderdetail.this, shop_Sh.class);
            startActivity(t);
        }
    }



    class viewHolder1
    {
        TextView shop_Nid;
        TextView headline;
        TextView prompt;
        TextView money;
        TextView shop_data;
        Button shop_th;
        Button ckwl;
        Button shop_shuijing;
        ImageView img;
    }




}
