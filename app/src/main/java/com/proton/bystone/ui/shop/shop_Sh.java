package com.proton.bystone.ui.shop;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.PnJia;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/8/9.
 * 评论
 */
@MTFActivityFeature(layout = R.layout.shop_shouhuo)
public class shop_Sh extends MTFBaseActivity {
    String userCode;
    @Override
    public void initialize(Bundle savedInstanceState) {

         wechat_payment();
    }

    @Override
    public void backPressed() {

    }


    //评论详情
    public void wechat_payment()
    {
        //PnJia loginParams = new PnJia(1,"111","1111","201605261656057265","满意","小强","111");

        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("SubmitCommentPoints")
                .gson(new Gson())
                /*.noParams()*/
                //.object(loginParams)
                .typeValue("string",1)
                .typeValue("string","")
                .typeValue("string","")
                .typeValue("string","201605261656057265")
                .typeValue("string","满意")
                .typeValue("string","小强")
                .typeValue("string","")

                .build();
//

        Call<BaseResp> call = HttpClients.getInstance().orderInfo(requestBody);

        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
           String     data = response.body().getData();
                Log.e("评论",data+"");


            }
            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                Log.e("111","失败");
            }
        });


    }


    @butterknife.OnClick(R.id.m_title_left_btn)
    public void back(View view) {
        animFinish();
    }
}
