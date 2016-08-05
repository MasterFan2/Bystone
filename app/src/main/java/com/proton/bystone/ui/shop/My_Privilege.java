package com.proton.bystone.ui.shop;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.Jbnum;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/27.
 * 跳转我的
 */

@MTFActivityFeature(layout = R.layout.my_account)
public class My_Privilege extends MTFBaseActivity {


    @Override
    public void initialize(Bundle savedInstanceState) {
        data2();
    }

    @Override
    public void backPressed() {
   animFinish();
    }


    //获取数据

    public void data2()
    {


        // Modified_Pwd pwd=new Modified_Pwd(et2.getText().toString().trim(),et3.getText().toString().trim());
        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetAllActivityList")
                .gson(new Gson())
               // .noParams()
                //.object(pwd)
            .typeValue("","")
               /* .typeValue("string","958496")
                .typeValue("int",2)*/
                .build();
//        ParamsBuilder<LoginParams> builder = new ParamsBuilder<LoginParams>().
//                .key("")
//                .build();

        Call<BaseResp> call = HttpClients.getInstance().commodity(requestBody);

        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                 String data = response.body().getData();
                 Log.e("优惠活动",data);
                //jiexi2(data);


            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                Log.e("111","失败");
            }
        });


    }

    public void jiexi2(String Data)
    {
      /*  Gson g=new Gson();
        Jbnum jbnum = g.fromJson(Data, Jbnum.class);*/

       // List<Jbnum> jbnum=new Gson().fromJson(Data, new TypeToken<List<Jbnum>>() {}.getType());

    }
}
