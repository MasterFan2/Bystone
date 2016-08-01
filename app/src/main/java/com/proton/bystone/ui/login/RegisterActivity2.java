package com.proton.bystone.ui.login;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.proton.bystone.R;
import com.proton.bystone.bean.JsonResp;
import com.proton.bystone.bean.Refister;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 注册2
 */

/**
 * Created by Administrator on 2016/7/6.
 */
@MTFActivityFeature(layout = R.layout.fragment_zuce2)
public class RegisterActivity2 extends MTFBaseActivity {

    EditText et2;

    Button bt2;
    EditText editText;


    @Override
    public void initialize(Bundle savedInstanceState) {
        Listener();
    }

    @Override
    public void backPressed() {
        animFinish();
    }


    public void Listener() {
        //请输入6到8位密码
        editText = (EditText) findViewById(R.id.editText);
        //邀请人手机号
        et2 = (EditText) findViewById(R.id.et2);
        //注册
        bt2 = (Button) findViewById(R.id.bt2);

        et2.addTextChangedListener(new TextWatcher(){

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
                bt2.setBackground(RegisterActivity2.this.getResources().getDrawable(R.drawable.yuanjiao));
                if(et2.getText().toString().trim().equals(""))
                {
                    bt2.setBackground(RegisterActivity2.this.getResources().getDrawable(R.drawable.yuanjiao2));
                }
            }

        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data2();
            }
        });
    }


    //提交祖册
    public void data2()
    {
        // LoginParams loginParams = new LoginParams(ed.getText().toString().trim(), "666888", "xxaabbc085412556sxxx", 1);
        Refister refister=new Refister("123","13629770634",555555,1);
        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("SignUp")
                .gson(new Gson())
                /*.noParams()*/
                .object(refister)
               /* .typeValue("string","13629770633")
                .typeValue("string","958496")
                .typeValue("int",2)*/
                .build();
//        ParamsBuilder<LoginParams> builder = new ParamsBuilder<LoginParams>().
//                .key("")
//                .build();

        Call<JsonResp> call = HttpClients.getInstance().regist(requestBody);

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
            Toast.makeText(this,"注册成功",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,HomePagesActivity.class));
        }else{
            Toast.makeText(this,"注册错误",Toast.LENGTH_LONG).show();
        }
    }







}
