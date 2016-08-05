package com.proton.bystone.ui.login;


import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.proton.bystone.R;
import com.proton.bystone.bean.Duanxin;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

/**
 * 忘记密码
 */

/**
 * Created by Administrator on 2016/7/6.
 */
@MTFActivityFeature(layout = R.layout.wj)
public class WjActivity extends MTFBaseActivity {
    EditText  ed;
    Button bu;
    EditText ed2;
    CheckBox cb;
    TextView tvyuedu;
    Button xiayibu;

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

        //登陆
        xiayibu = (Button)findViewById(R.id.xiayibu);

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
                    Toast.makeText(WjActivity.this,"请输入手机号",Toast.LENGTH_SHORT).show();
                }else {
                    bu.setBackground(WjActivity.this.getResources().getDrawable(R.drawable.yuanjiao2));
                    TimeCount  time = new TimeCount(60000, 1000);
                    time.start();
                }

            }
        });

        //点下一步后的监听
        xiayibu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String et2o=ed2.getText().toString().trim();
                if(et2o.equals(""))
                {
                    Toast.makeText(WjActivity.this,"验证码不能为null",Toast.LENGTH_SHORT).show();
                }else {
                    //验证验证码
                    sx(et2o);

                    startActivity(new Intent(WjActivity.this,NewpwdActivity.class));
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
                bu.setBackground(WjActivity.this.getResources().getDrawable(R.drawable.yuanjiao));
                if(ed.getText().toString().trim().equals(""))
                {
                    bu.setBackground(WjActivity.this.getResources().getDrawable(R.drawable.yuanjiao2));
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
                xiayibu.setBackground(WjActivity.this.getResources().getDrawable(R.drawable.yuanjiao));
                if(ed2.getText().toString().trim().equals(""))
                {
                    xiayibu.setBackground(WjActivity.this.getResources().getDrawable(R.drawable.yuanjiao2));
                }
            }

        });
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }
        @Override
        public void onFinish() {//计时完毕时触发
            bu.setText("重新验证");
            bu.setClickable(true);
            bu.setBackground(WjActivity.this.getResources().getDrawable(R.drawable.yuanjiao));
        }
        @Override
        public void onTick(long millisUntilFinished){//计时过程显示
            bu.setClickable(false);
            bu.setText(millisUntilFinished /1000+"秒");
        }
    }

    public void sx(String phonenumber) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("phonenumber", phonenumber);
        String path="http://192.168.0.119:8081/api/MemberInfoInterface";
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST,path,params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result= responseInfo.result;
                        System.out.println("找到了:"+result);
                        //解析
                        jiexi(result);

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        System.out.println("没找到了哦:");
                    }
                });

    }

    public void jiexi(String result)
    {
        Gson g=new Gson();
        Duanxin duanxin = g.fromJson(result, Duanxin.class);
      //  System.out.println("5555555555555555555555555555"+duanxin);
        int code = duanxin.getcode();
        if(code==102)
        {
            //跳转到我的主页面
            startActivity(new Intent(WjActivity.this,HomePagesActivity.class));
        }
    }



}
