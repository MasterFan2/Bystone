package com.proton.bystone.ui.shop;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import java.io.UnsupportedEncodingException;
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
    @Bind(R.id.shop_pay)
    Button bt;//点击确认付款

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

      // ddbh = getIntent().getStringExtra("ddbh");//拿到订单编号

        //点击确认付款
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t= new Intent(My_Shop_Pay.this,Shop_Orderdetail.class);
                startActivity(t);
            }
        });
        //支付宝客户端支付
        shop_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wechat_payment();
            }
        });
    }

    @Override
    public void backPressed() {
   animFinish();
    }

    @OnClick(R.id.Home_fh)
    public void back(View view) {
        animFinish();
    }

   //调服务器微信支付获取的信息
    public void wechat_payment()
    {

        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("OrderSignedInspection")
                .gson(new Gson())
                /*.noParams()*/
                //.object(loginParams)
                .typeValue("string","201607281300525158")//订单编号
                .typeValue("int",2)
                .typeValue("int",1)

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
