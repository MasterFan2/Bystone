package com.proton.bystone.ui.payment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.OrderSubmitResp;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.pay.app.PayResult;
import com.proton.bystone.ui.shopcar.MyShoppingCar;
import com.proton.bystone.utils.L;
import com.proton.bystone.utils.S;
import com.proton.bystone.utils.T;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;
import com.proton.library.utils.ActivityManager;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 选择支付方式
 */
@MTFActivityFeature(layout = R.layout.activity_choose_payment)
public class ChoosePaymentActivity extends MTFBaseActivity {

    //3:银行卡支付
    //2:支付宝
    //1:微信支付
    //4:现场支付
    private int choosedPayment = 3;//默认值

    //1:主订单
    //2:子订单(只有在我的订单的付款的时候才是子订单)
    private int orderType = 1;

    //订单号
    private String orderNum = null;

    ///widget
    @Bind(R.id.choose_payment_bank_card_view)
    View bankCardView;

    @Bind(R.id.choose_payment_alipay_view)
    View alipayView;

    @Bind(R.id.choose_payment_wechat_view)
    View wechatView;

    @Bind(R.id.choose_payment_money_view)
    View moneyView;

    @Override
    public void initialize(Bundle savedInstanceState) {
        orderNum = getIntent().getStringExtra("orderNum");
    }

    @OnClick({R.id.choose_payment_bank_card_layout, R.id.choose_payment_alipay_layout, R.id.choose_payment_wechat_layout, R.id.choose_payment_money_layout})
    public void chooseClick(View view) {
        switch (view.getId()) {
            case R.id.choose_payment_bank_card_layout:
                choosedPayment = 3;
                bankCardView.setBackgroundResource(R.mipmap.icon_radio_blue_sel);
                alipayView.setBackgroundResource(R.mipmap.icon_radio_blue_nor);
                wechatView.setBackgroundResource(R.mipmap.icon_radio_blue_nor);
                moneyView.setBackgroundResource(R.mipmap.icon_radio_blue_nor);
                break;
            case R.id.choose_payment_alipay_layout:
                choosedPayment = 2;
                bankCardView.setBackgroundResource(R.mipmap.icon_radio_blue_nor);
                alipayView.setBackgroundResource(R.mipmap.icon_radio_blue_sel);
                wechatView.setBackgroundResource(R.mipmap.icon_radio_blue_nor);
                moneyView.setBackgroundResource(R.mipmap.icon_radio_blue_nor);
                break;
            case R.id.choose_payment_wechat_layout:
                T.s(context, "微信支付暂时不可用");
                choosedPayment = 1;
                bankCardView.setBackgroundResource(R.mipmap.icon_radio_blue_nor);
                alipayView.setBackgroundResource(R.mipmap.icon_radio_blue_nor);
                wechatView.setBackgroundResource(R.mipmap.icon_radio_blue_sel);
                moneyView.setBackgroundResource(R.mipmap.icon_radio_blue_nor);
                break;
            case R.id.choose_payment_money_layout:
                choosedPayment = 4;
                bankCardView.setBackgroundResource(R.mipmap.icon_radio_blue_nor);
                alipayView.setBackgroundResource(R.mipmap.icon_radio_blue_nor);
                wechatView.setBackgroundResource(R.mipmap.icon_radio_blue_nor);
                moneyView.setBackgroundResource(R.mipmap.icon_radio_blue_sel);
                break;
        }
    }

    /**
     * 提示现金支付
     */
    private void tipsMoneyPay() {
        new AlertDialog.Builder(context)
                .setTitle("提示您")
                .setMessage("使用现金支付, 您把需要支付的金额给您做维保的技师就可以了")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        doSign();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    /**
     * 确认付款
     *
     * @param view
     */
    @OnClick(R.id.choose_payment_pay_btn)
    public void confirmPay(View view) {
        if (TextUtils.isEmpty(orderNum)) {
            T.s(context, "无法获取订单号,请稍后重试");
            return;
        }

        if (choosedPayment == 4) {//提示现金支付
            tipsMoneyPay();
        } else if (choosedPayment == 3) {
            unionPay(null);
        } else {
            doSign();
        }
    }

    /**
     * 服务器签名
     */
    private void doSign() {
        final RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("OrderSignedInspection")
                .gson(new Gson())
                .typeValue("string", orderNum)
                .typeValue("int", choosedPayment)//1:微信      2:支付宝       3:银联        4:现金
                .typeValue("int", orderType)     //1:主订单    2:子订单(只有在我的订单的付款的时候才是子订单)
                .typeValue("int", 1)             //1:用户版    2:技师版
                .build();
        Call<BaseResp> call = HttpClients.getInstance().orderInfo(requestBody);
        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, final Response<BaseResp> response) {
                if (response.body().getCode() == 1) {
                    signResult(response.body().getData());
                } else {
                    T.s(context, "sign failure!!!");
                }
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                L.e("sign::" + t.getMessage());
            }
        });
    }

    /**
     * 签名处理结果
     *
     * @param signResult
     */
    private void signResult(String signResult) {
        if (choosedPayment == 2) {      //支付宝
            doAlipay(signResult);
        } else if (choosedPayment == 1) {//微信支付
            wechatPay(signResult);
        } else if (choosedPayment == 3) {//银联支付
            unionPay(signResult);
        } else if (choosedPayment == 4) {//现金
            moneyPay(signResult);
        }
    }

    /***
     * 支付宝支付
     */
    private void doAlipay(final String signResult) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {

                // 构造PayTask 对象
                PayTask alipay = new PayTask(ChoosePaymentActivity.this);

                // 调用支付接口，获取支付结果
                String result = alipay.pay(signResult, true);

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

    /**
     * 微信支付
     */
    private void wechatPay(final String signResult) {
        //
    }

    /**
     * 银联支付
     */
    private void unionPay(final String signResult) {
        Uri uri = Uri.parse("http://192.168.0.119:8081/PaySubmit?ordercode=" + orderNum + "&ordertype=" + orderType + "&usertype=1");
        Intent it = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(it);
    }

    /**
     * 现金支付
     *
     * @param signResult
     */
    private void moneyPay(final String signResult) {
        //
        animFinish();
    }

    /**
     * 支付宝返回处理
     */
    Handler mHandler = new Handler() {
        @Override
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
                        Toast.makeText(ChoosePaymentActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        animFinish();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(ChoosePaymentActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(ChoosePaymentActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            }
        }
    };

    @Override
    public void backPressed() {
        animFinish();
    }

    @OnClick(R.id.m_title_left_btn)
    public void back(View view) {
        animFinish();
    }
}
