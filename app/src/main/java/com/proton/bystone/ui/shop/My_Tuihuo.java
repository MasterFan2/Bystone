package com.proton.bystone.ui.shop;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.Category_nameResp;
import com.proton.bystone.bean.cbms;
import com.proton.bystone.bean.JsonResp;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/27.
 * 退货
 */

@MTFActivityFeature(layout = R.layout.shop_tuihuo)
public class My_Tuihuo extends MTFBaseActivity {


    String childOrderNumber ;//订单编号
    String UserCode;//会员编号
    String VCCode ;//商品编号
    String BsCode ;
    EditText ed;
    String trim;
    Button  shop_qd;



    @Override
    public void initialize(Bundle savedInstanceState) {
       childOrderNumber = getIntent().getStringExtra("childOrderNumber");//订单编号
         UserCode = getIntent().getStringExtra("UserCode");//会员编号
        VCCode = getIntent().getStringExtra("VCCode");//商品编号
        BsCode = getIntent().getStringExtra("BsCode");//商家编号

       ed=(EditText)findViewById(R.id.shop_tuihuo);//退货原因
        shop_qd=(Button)findViewById(R.id.shop_qd);//

        trim = ed.getText().toString().trim();


        shop_qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category_name();
            }
        });

    }



    @Override
    public void backPressed() {
       animFinish();
    }


    public void Category_name()
    {
        // LoginParams loginParams = new LoginParams(ed.getText().toString().trim(), "666888", "xxaabbc085412556sxxx", 1);
        cbms cbms=new cbms(childOrderNumber,VCCode,UserCode,BsCode,trim,"");
        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("SetReturnRequest")
                .gson(new Gson())
                /*.noParams()*/
                 .object(cbms)
                //.typeValue("string","")
                .build();
//        ParamsBuilder<LoginParams> builder = new ParamsBuilder<LoginParams>().
//                .key("")
//                .build();

        Call<BaseResp> call = HttpClients.getInstance().orderInfo(requestBody);

        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                String data = response.body().getData();
                Log.e("aaaaaa",data);
                htpjiexi(data);
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                Log.e("111","失败");
            }
        });


    }
    //解析
    public void htpjiexi(String data)
    {
          if("1".equals(data))
          {
              Toast.makeText(My_Tuihuo.this,"退货请求发布成功",Toast.LENGTH_SHORT).show();
          }

    }

    @butterknife.OnClick(R.id.m_title_left_btn)
    public void back(View view) {
        animFinish();
    }




}
