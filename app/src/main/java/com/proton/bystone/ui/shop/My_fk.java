package com.proton.bystone.ui.shop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.Fankui;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.ui.shopcar.ShopCarActivity;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.MTFBaseFragment;
import com.proton.library.ui.annotation.MTFActivityFeature;
import com.proton.library.ui.annotation.MTFFragmentFeature;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/27.
 * 反馈
 */
@MTFActivityFeature(layout = R.layout.my_retroaction)
public class My_fk extends MTFBaseActivity {
    @Bind(R.id.fankui)
    Button fankui;
    @Bind(R.id.shop_code)
    EditText shop_code;
    String mbcode;
    @Override
    public void initialize(Bundle savedInstanceState) {
        fankui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fanku();
            }
        });
    }

    @Override
    public void backPressed() {
animFinish();

    }

    public void fanku()
    {

        mbcode = getIntent().getStringExtra("mbcode");
        Log.e("mbcode",mbcode);
        Fankui  fk=new Fankui(mbcode,shop_code.getText().toString().trim(),2);

        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("SetServingFeedbac")
                .gson(new Gson())
                /*.noParams()*/
                .object(fk)
               /* .typeValue("string",mbcode)
                .typeValue("string",shop_code.getText().toString().trim())
                .typeValue("int",2)*/

                .build();
//        ParamsBuilder<LoginParams> builder = new ParamsBuilder<LoginParams>().
//                .key("")
//                .build();

        Call<BaseResp> call = HttpClients.getInstance().memberInfo(requestBody);

        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                String data = response.body().getData();
               Log.e("反馈",data);
                if("1".equals(data))
                {
                    Toast.makeText(My_fk.this,"提交反馈成功",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                Log.e("111","失败");
            }
        });


    }

    @OnClick(R.id.m_title_left_btn)
    public void back(View view) {
        animFinish();
    }



}
