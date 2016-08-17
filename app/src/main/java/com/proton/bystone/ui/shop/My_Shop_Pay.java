package com.proton.bystone.ui.shop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.JsonResp;
import com.proton.bystone.bean.Shop_Pl;
import com.proton.bystone.bean.shop_pay_cs;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.pay.PayActivity;


import com.proton.bystone.pay.app.PayDemoActivity;
import com.proton.bystone.pay.app.PayResult;
import com.proton.bystone.pay.yinlian.BBaseActivity;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/27.
 * 跳转我的支付
 */

@MTFActivityFeature(layout = R.layout.shop_pay)
public class My_Shop_Pay extends MTFBaseActivity {
    final String LOG_TAG = "PayDemo";
    Context mContext = null;
    int mGoodsIdx = 0;

    ProgressDialog mLoadingDialog = null;

    final int PLUGIN_VALID = 0;
    final int PLUGIN_NOT_INSTALLED = -1;
    final int PLUGIN_NEED_UPGRADE = 2;

    final String mMode = "00";
    final String TN_URL_01 = "http://101.231.204.84:8091/sim/getacptn";

    @Bind(R.id.shop_pay)
    Button bt;//点击确认付款

  @Bind(R.id.shop_yhkzf)
    TextView shop_yhkzf;//银联支付

    @Bind(R.id.shop_wxzf)
    TextView shop_wxzf;//微信支付

    @Bind(R.id.shop_zhifu)
    ImageView shop_zhifu;//支付宝图标变化

    @Bind(R.id.shop_weixin)
    ImageView shop_weixin;//微信图标变化



    @Bind(R.id.shop_iv)
    ImageView shop_iv;//银联图标变化


    @Bind(R.id.shop_py)
    TextView shop_p;//支付宝客户端支付
    String ddbh;

    String appid;
    String Partnerid;
    String Prepayid;
    String PackageX;
    String Noncestr;
    int Timestamp;
    String sign;
    Boolean flag=false;
    Boolean ff=false;
    Boolean yl=false;
    String   order_number;
    String   money;

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(My_Shop_Pay.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(My_Shop_Pay.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(My_Shop_Pay.this, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };
    @Override
    public void initialize(Bundle savedInstanceState) {

     order_number = getIntent().getStringExtra("orderNum");//拿到订单编号
      money = getIntent().getStringExtra("money");//合计多少钱
        Log.e("dfg",money+order_number);
        TextView  shop_heji  = (TextView) findViewById(R.id.shop_hj);//合计
        shop_heji.setText(money+"");
        //点击确认付款

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(flag) {
                   //  wechat_payment();//支付宝
                        Intent t= new Intent(My_Shop_Pay.this,Shop_Orderdetail.class);
                        startActivity(t);

                    }

                    if(ff)
                    {
                       // wechat_payment2();//微信，
                    }
                //银联
                  if(yl)
                  {
/*                    Intent intent = new Intent(context,WebActivity.class);
                      intent.putExtra("url", "http://192.168.0.119:8081/PaySubmit?ordercode=201608081802521687&ordertype=2&usertype=1");
                      startActivity(intent);*/

                      Uri uri = Uri.parse("http://192.168.0.119:8081/PaySubmit?ordercode=201608081802521687&ordertype=2&usertype=1");
                      Intent it = new Intent(Intent.ACTION_VIEW, uri);
                      startActivity(it);
                  }


            }
        });
        //支付宝客户端支付
        shop_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!flag)
                {
                    shop_zhifu.setImageResource(R.mipmap.icon_mai_true);
                    bt.setBackground(My_Shop_Pay.this.getResources().getDrawable(R.drawable.yuanjiao));
                    flag=true;
                }else{
                    shop_zhifu.setImageResource(R.mipmap.icon_mai_disabled);
                    bt.setBackground(My_Shop_Pay.this.getResources().getDrawable(R.drawable.yuanjiao2));
                    flag=false;
                }
            }
        });

        //微信支付
        shop_wxzf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ff)
                {
                    shop_weixin.setImageResource(R.mipmap.icon_mai_true);
                    bt.setBackground(My_Shop_Pay.this.getResources().getDrawable(R.drawable.yuanjiao));
                    ff=true;

                }else{
                    shop_weixin.setImageResource(R.mipmap.icon_mai_disabled);
                    bt.setBackground(My_Shop_Pay.this.getResources().getDrawable(R.drawable.yuanjiao2));
                    ff=false;

                }



            }
        });
//银联支付
        shop_yhkzf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!yl)
                {
                    shop_iv.setImageResource(R.mipmap.icon_mai_true);
                    bt.setBackground(My_Shop_Pay.this.getResources().getDrawable(R.drawable.yuanjiao));
                    yl=true;

                }else{
                    shop_iv.setImageResource(R.mipmap.icon_mai_disabled);
                    bt.setBackground(My_Shop_Pay.this.getResources().getDrawable(R.drawable.yuanjiao2));
                    yl=false;

                }

            }
        });






    }

    @Override
    public void backPressed() {
   animFinish();
    }

    @OnClick(R.id.m_title_left_btn)
    public void back(View view) {
        animFinish();
    }

   //支付宝
    public void wechat_payment()
    {

        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("OrderSignedInspection")
                .gson(new Gson())
                /*.noParams()*/
                //.object(loginParams)
                .typeValue("string","201608081802521687")//订单编号
                .typeValue("int",2)
                .typeValue("int",2)
                .typeValue("int",2)

                .build();
//

        Call<BaseResp> call = HttpClients.getInstance().orderInfo(requestBody);

        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                String data = response.body().getData();
                Log.e("微信支付参数",data+"");
             //jiexi(data);这解析微信
                 jiexi2(data);//这解析支付宝
            }
            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                Log.e("111","失败");
            }
        });


    }



    public void wechat_payment2()
    {

        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("OrderSignedInspection")
                .gson(new Gson())
                /*.noParams()*/
                //.object(loginParams)
                .typeValue("string","201607281300525158")//订单编号
                .typeValue("int",1)
                .typeValue("int",1)

                .build();
//

        Call<BaseResp> call = HttpClients.getInstance().orderInfo(requestBody);

        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                String data = response.body().getData();
                Log.e("微信支付参数",data+"");
                jiexi(data);//这解析微信

            }
            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                Log.e("111","失败");
            }
        });


    }

    //解析
    public void jiexi(String data)
    {

      /*  List<shop_pay_cs>    shop_pay_cs=new Gson().fromJson(data, new TypeToken<List<shop_pay_cs>>() {}.getType());*/

        Gson gson=new Gson();
        shop_pay_cs shop_pay_cs = gson.fromJson(data, shop_pay_cs.class);

        appid=shop_pay_cs.getAppid();
       Partnerid=shop_pay_cs.getPartnerid();
         Prepayid=shop_pay_cs.getPrepayid();
    PackageX=shop_pay_cs.getPackageX();
         Noncestr=shop_pay_cs.getNoncestr();
        Timestamp=shop_pay_cs.getTimestamp();
         sign=shop_pay_cs.getSign();

        Log.e("appid",appid+Partnerid+Prepayid+PackageX+Noncestr+Timestamp+sign);

         if(data!=null)
         {
             Intent t=new Intent(My_Shop_Pay.this, PayActivity.class);

             Bundle bundle = new Bundle();
             bundle.putString("appid", appid);
             bundle.putString("Partnerid", Partnerid);
             bundle.putString("Prepayid", Prepayid);
             bundle.putString("PackageX", PackageX);
             bundle.putString("Noncestr", Noncestr);
             bundle.putInt("Timestamp", Timestamp);
             bundle.putString("sign", sign);

             t.putExtras(bundle);

             startActivity(t);
         }

    }

    public void jiexi2(final String data) {

        if (data != null) {

            Runnable payRunnable = new Runnable() {

                @Override
                public void run() {
                    // 构造PayTask 对象
                    PayTask alipay = new PayTask(My_Shop_Pay.this);
                    // 调用支付接口，获取支付结果
                    String result = alipay.pay(data,true);

                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }
            };

// 必须异步调用
            Thread payThread = new Thread(payRunnable);
            payThread.start();



        }



    }

}
