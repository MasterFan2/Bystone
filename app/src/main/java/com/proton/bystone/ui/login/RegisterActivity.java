package com.proton.bystone.ui.login;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.proton.bystone.R;
import com.proton.bystone.bean.JsonResp;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 注册
 */

/**
 * Created by Administrator on 2016/7/6.
 */
@MTFActivityFeature(layout = R.layout.fragment_zuce)
public class RegisterActivity extends MTFBaseActivity {
    EditText  ed;
    Button bu;
    EditText ed2;
    CheckBox cb;
    TextView tvyuedu;
    Button btdenglu;

    String edone;

    @Override
    public void initialize(Bundle savedInstanceState) {
        initview();
    }

    @Override
    public void backPressed() {
        animFinish();
    }

    private void initview() {
        //请输入手机号
        ed = (EditText)findViewById(R.id.editText);
        //获取验证码
        bu = (Button)findViewById(R.id.bt);
        //请输入验证码
        ed2 = (EditText)findViewById(R.id.et2);
        //CheckBox
        cb = (CheckBox)findViewById(R.id.cb);
        //TextView我已阅读
        tvyuedu = (TextView)findViewById(R.id.tvyuedu);
        //下一步
        btdenglu = (Button)findViewById(R.id.btdenglu);

        Listener();
    }
    //各种监听
    public void Listener()
    {
        //f发送验证码的监听
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               edone = ed.getText().toString().trim();

                if(edone.equals(""))
                {
                    Toast.makeText(RegisterActivity.this,"请输入手机号",Toast.LENGTH_SHORT).show();
                }else {
                    bu.setBackground(RegisterActivity.this.getResources().getDrawable(R.drawable.yuanjiao2));
                    TimeCount  time = new TimeCount(60000, 1000);
                    time.start();
                    //发送验证码
                    data();
                }

            }
        });

        //点下一步后的监听
        btdenglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String et2o=ed2.getText().toString().trim();
                if(et2o.equals(""))
                {
                    Toast.makeText(RegisterActivity.this,"验证码不能为null",Toast.LENGTH_SHORT).show();
                }else {
                    //验证验证码
                    data2();
                }
            }
        });

        ed.addTextChangedListener(new TextWatcher(){

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {



            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                bu.setBackground(RegisterActivity.this.getResources().getDrawable(R.drawable.yuanjiao));
                if(ed.getText().toString().trim().equals(""))
                {
                    bu.setBackground(RegisterActivity.this.getResources().getDrawable(R.drawable.yuanjiao2));
                }
            }

        });

        ed2.addTextChangedListener(new TextWatcher(){

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {



            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                btdenglu.setBackground(RegisterActivity.this.getResources().getDrawable(R.drawable.yuanjiao));
                if(ed2.getText().toString().trim().equals(""))
                {
                    btdenglu.setBackground(RegisterActivity.this.getResources().getDrawable(R.drawable.yuanjiao2));
                }
            }

        });
    }
    //发送验证码
    public void data()
    {
       // LoginParams loginParams = new LoginParams(ed.getText().toString().trim(), "666888", "xxaabbc085412556sxxx", 1);

        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("RegisSendCodes")
                .gson(new Gson())
                /*.noParams()*/
               // .object(loginParams)
                .typeValue("string",ed.getText().toString().trim())
                .typeValue("int",1)
                .build();
//        ParamsBuilder<LoginParams> builder = new ParamsBuilder<LoginParams>().
//                .key("")
//                .build();

        Call<JsonResp> call = HttpClients.getInstance().sendverification(requestBody);

        call.enqueue(new Callback<JsonResp>() {
            @Override
            public void onResponse(Call<JsonResp> call, Response<JsonResp> response) {
                int code = response.body().getCode();
                jiexi(code);


            }

            @Override
            public void onFailure(Call<JsonResp> call, Throwable t) {
                Log.e("111","失败");
            }
        });


    }

    public void jiexi(int code)
    {
         if(code==1)
         {
             Toast.makeText(this,"发送验证码成功请等待",Toast.LENGTH_LONG).show();
         }
    }


       //点button提交验证，检测验证码并跳转
    public void data2()
    {
        // LoginParams loginParams = new LoginParams(ed.getText().toString().trim(), "666888", "xxaabbc085412556sxxx", 1);

        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("VerificationCodes")
                .gson(new Gson())
                /*.noParams()*/
                // .object(loginParams)
                .typeValue("string","13629770633")
                .typeValue("string","958496")
                .typeValue("int",2)
                .build();
//        ParamsBuilder<LoginParams> builder = new ParamsBuilder<LoginParams>().
//                .key("")
//                .build();

        Call<JsonResp> call = HttpClients.getInstance().yzsendverification(requestBody);

        call.enqueue(new Callback<JsonResp>() {
            @Override
            public void onResponse(Call<JsonResp> call, Response<JsonResp> response) {
                int code2 = response.body().getCode();

                jiexi2(code2);


            }

            @Override
            public void onFailure(Call<JsonResp> call, Throwable t) {
                Log.e("111","失败");
            }
        });


    }

    public void jiexi2(int code2)
    {
        Toast.makeText(this,"okokok code2"+code2,Toast.LENGTH_LONG).show();

        if(1==code2)
        {
            //验证成功跳转到下一个页面
            Toast.makeText(this,"验证成功",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,RegisterActivity2.class));
        }else{
            Toast.makeText(this,"验证码错误",Toast.LENGTH_LONG).show();
        }
    }




    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }
        @Override
        public void onFinish() {//计时完毕时触发
            bu.setText("重新验证");
            bu.setClickable(true);
            bu.setBackground(RegisterActivity.this.getResources().getDrawable(R.drawable.yuanjiao));
        }
        @Override
        public void onTick(long millisUntilFinished){//计时过程显示
            bu.setClickable(false);
            bu.setText(millisUntilFinished /1000+"秒");
        }
    }




}
