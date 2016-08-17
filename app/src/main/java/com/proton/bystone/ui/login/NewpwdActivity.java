package com.proton.bystone.ui.login;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.JsonResp;
import com.proton.bystone.bean.LoginResp;
import com.proton.bystone.bean.Newpasswd;
import com.proton.bystone.cache.LoginManager;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import butterknife.Bind;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 设置新密码
 */

/**
 * Created by Administrator on 2016/7/8.
 */
@MTFActivityFeature(layout = R.layout.fragment_newpwd)
public class NewpwdActivity extends MTFBaseActivity {
    EditText et2;
    EditText et3;
    Button bt3;

    @Bind(R.id.m_title_left_btn)
    TextView m_title_left_btn;


    @Override
    public void initialize(Bundle savedInstanceState) {
        initview();
        Listenet();

    }

    @Override
    public void backPressed() {
        animFinish();
    }

    private void initview() {
        //设置新密码
        et2 = (EditText)findViewById(R.id.et2);
        //重新再输入一遍
        et3 = (EditText)findViewById(R.id.et3);
        //完成
        bt3 = (Button)findViewById(R.id.bt3);

    }

    private void Listenet() {
        m_title_left_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animFinish();
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all_pinglun();
            }
        });

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
                if (et3.getText().toString().trim().equals("") ) {
                    bt3.setBackground(NewpwdActivity.this.getResources().getDrawable(R.drawable.yuanjiao2));

                } else {
                    bt3.setBackground(NewpwdActivity.this.getResources().getDrawable(R.drawable.yuanjiao));

                }

            }

        });
    }


/*    @butterknife.OnClick(R.id.m_title_left_btn)
    public void back(View view) {
        animFinish();
    }*/


   //修改密码
    public void all_pinglun()
    {
        LoginResp loginInfo = LoginManager.getInstance().getLoginInfo();
        String mb_loginName = loginInfo.getMb_LoginName();

        Newpasswd newpass=new Newpasswd(mb_loginName,et3.getText().toString().trim());
        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("ModifyPassword")
                .gson(new Gson())
                /*.noParams()*/
                .object(newpass)

              /*  .typeValue("String","18225026697")
                .typeValue("String",111111)*/
                .build();
//

        Call<BaseResp> call = HttpClients.getInstance().memberInfo(requestBody);

        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                String data = response.body().getData();
                Log.e("修改密码",data+"");

                if("1".equals(data))
                {
                    Toast.makeText(NewpwdActivity.this,"修改密码成功",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                Log.e("111","失败");
            }
        });


    }



}
