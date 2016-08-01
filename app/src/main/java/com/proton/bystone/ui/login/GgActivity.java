package com.proton.bystone.ui.login;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.proton.bystone.R;
import com.proton.bystone.bean.JsonResp;
import com.proton.bystone.bean.Modified_Pwd;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 更改密码
 */

/**
 * Created by Administrator on 2016/7/6.
 */
@MTFActivityFeature(layout = R.layout.fragment_newpwd)
public class GgActivity extends MTFBaseActivity {
    EditText et3;
    Button bt3;
    EditText et2;

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
        et2 = (EditText) findViewById(R.id.et2);

        et3 = (EditText) findViewById(R.id.et3);

        bt3 = (Button) findViewById(R.id.bt3);

        et3.addTextChangedListener(new TextWatcher(){

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

                if(et3.getText().toString().trim().equals(""))
                {
                    bt3.setBackground(GgActivity.this.getResources().getDrawable(R.drawable.yuanjiao2));
                }else {

                    bt3.setBackground(GgActivity.this.getResources().getDrawable(R.drawable.yuanjiao));
                }
            }

        });
    }


    //更改密码
    public void data2()
    {
       // LoginParams loginParams = new LoginParams(ed.getText().toString().trim(),ed2.getText().toString().toString(),"xxaabbc085412556sxxx",1 );
        Modified_Pwd pwd=new Modified_Pwd(et2.getText().toString().trim(),et3.getText().toString().trim());
        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("ModifyPassword")
                .gson(new Gson())
                /*.noParams()*/
                .object(pwd)
        /*      .typeValue("string","13629770633")
                .typeValue("string","958496")
                .typeValue("int",2)*/
                .build();
//        ParamsBuilder<LoginParams> builder = new ParamsBuilder<LoginParams>().
//                .key("")
//                .build();

        Call<JsonResp> call = HttpClients.getInstance().modified_pwd(requestBody);

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
            //验证成功跳转到下一个页面跳转主页
            Toast.makeText(this,"修改密码成功",Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this,"修改密码错误",Toast.LENGTH_LONG).show();
        }
    }

}
