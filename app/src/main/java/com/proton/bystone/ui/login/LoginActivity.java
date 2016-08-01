package com.proton.bystone.ui.login;
/**
 * 帐号密码登陆
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.proton.bystone.bean.LoginParams;
import com.proton.bystone.bean.login;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.ui.main.MainActivity;
import com.proton.bystone.ui.main.tab.MeFragment;
import com.proton.bystone.ui.shop.Shop_Detail;
import com.proton.bystone.ui.shop.Shop_Ok;
import com.proton.bystone.utils.LoginUtil;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/6.
 *
 * 网络测试成功可以用
 */
@MTFActivityFeature(layout = R.layout.login)
public class LoginActivity extends MTFBaseActivity {
    Button wj;
    Button gg;
    EditText  ed;

    EditText ed2;

    CheckBox cb;
    int  pass;
    Object mb_pwd;
    public int code2;
    List<login>  listevent;
    TextView tvyuedu;
    Button btdenglu;
    String mb_code;
    String edone;
    login login;
    String mb_loginName;
    String mb_name;

    //is need result
    private boolean needResult = false;

    @Override
    public void initialize(Bundle savedInstanceState) {
        needResult = getIntent().getBooleanExtra("needResult", false);

        initview();
    }

    @Override
    public void backPressed() {
        animFinish();
    }

    private void initview() {
        //请输入手机号
        ed = (EditText)findViewById(R.id.editText);
        ed2 = (EditText)findViewById(R.id.et2);

        btdenglu = (Button)findViewById(R.id.btdenglu);

        wj=(Button)findViewById(R.id.wj);
        gg=(Button)findViewById(R.id.gg);
        Listener();


    }
    //各种监听
    public void Listener()
    {

        //忘记密码
        wj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,WjActivity.class));

            }
        });
        //更改密码
        gg.setOnClickListener(new View.OnClickListener() {
            //更改密码
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this,GgActivity.class));
            }
        });



        btdenglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String et2o=ed2.getText().toString().trim();
                if(et2o.equals(""))
                {
                    Toast.makeText(LoginActivity.this,"验证码不能为null",Toast.LENGTH_SHORT).show();
                }else {
                    ;
                   data2();

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
                btdenglu.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.yuanjiao));
                if(ed2.getText().toString().trim().equals(""))
                {
                    btdenglu.setBackground(LoginActivity.this.getResources().getDrawable(R.drawable.yuanjiao2));
                }
            }

        });
    }


  //点账号密码登陆
    public void data2()
    {
      /* LoginParams loginParams = new LoginParams("18225026697","666888","xxaabbc085412556sxx",1 );*/
        LoginParams loginParams = new LoginParams(ed.getText().toString().trim(),ed2.getText().toString().trim(),"xxaabbc085412556sxx",1 );
        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("Login")
                .gson(new Gson())
               // .noParams()
                .object(loginParams)
              /*  .typeValue("string","13629770633")
                .typeValue("string","958496")
                .typeValue("string","xxaabbc085412556sxxx")
                .typeValue("int",1)*/
                .build();
//        ParamsBuilder<LoginParams> builder = new ParamsBuilder<LoginParams>().
//                .key("")
//                .build();

        Call<JsonResp> call = HttpClients.getInstance().login(requestBody);

        call.enqueue(new Callback<JsonResp>() {
            @Override
            public void onResponse(Call<JsonResp> call, Response<JsonResp> response) {

                String data = response.body().getData();
                Log.e("333",data);
                jiexi2(data);

            }

            @Override
            public void onFailure(Call<JsonResp> call, Throwable t) {
                Log.e("111","失败");
            }
        });


    }

   public void jiexi2(String data)
    {
        Gson  g=new Gson();
        login = g.fromJson(data, login.class);
        mb_code = login.getMb_Code();
        mb_name = login.getMb_Name();//拿到姓名
        mb_loginName = login.getMb_LoginName();//拿到用户名，也有可能是手机号
        mb_pwd = login.getMb_Pwd();//拿到密码

        SharedPreferences sp = this.getSharedPreferences("info",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("mb_code", mb_code);
        editor.commit();

       // if(mb_loginName.equals(ed.getText().toString().trim())/*&&mb_pwd.equals(ed2.getText().toString().trim())*/)
        if(data!=null)
        {//login success

//            Intent t= new Intent(this, HomePagesActivity.class);
//            startActivity(t);

            LoginUtil.saveLogin(this, true);

            if (needResult) {//master fan call
                setResult(RESULT_OK);
                finish();
            }else {//self
                String landing = getIntent().getStringExtra("landing");
                if(landing.equals("mya"))
                {
                    SharedPreferences spp = context.getSharedPreferences("NameAndPhone", Context.MODE_PRIVATE);
                    spp.edit().putString("biaoji","11")
                            .putString("name",mb_name)
                            .putString("phone",mb_loginName).commit();
                    finish();
                }else if(landing.equals("log")){
                    //跳转订单确认
                    Intent t= new Intent(LoginActivity.this,Shop_Ok.class);
                    startActivity(t);
                }
            }


        } else {//login error

        }




    }





}
