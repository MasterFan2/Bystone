package com.proton.bystone.ui.shop;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.proton.bystone.R;
import com.proton.bystone.bean.JsonResp;
import com.proton.bystone.bean.Add_Address_Ok;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/20.
 * //添加地址
 */
@MTFActivityFeature(layout = R.layout.addnewaddress)
public class App extends MTFBaseActivity {


    EditText shopshouhuoren;
    EditText shopphone;
    EditText shopregion;
    EditText shopdetailed_address;
    ImageView shopmoren;
    Button affirm;
    String mark;
    String mbcode;


    @Override
    public void initialize(Bundle savedInstanceState) {
        initview();
        Listener();
    }

    @Override
    public void backPressed() {

    }

    public void initview()
    {
        //收货人
        shopshouhuoren=(EditText)findViewById(R.id.shop_shouhuoren);
        //联系电话
        shopphone=(EditText)findViewById(R.id.shop_phone);
        //所在地区
        shopregion=(EditText)findViewById(R.id.shop_region);
        //详细地址
        shopdetailed_address=(EditText)findViewById(R.id.shop_detailed_address);
        //设置为默认地址
        shopmoren=(ImageView)findViewById(R.id.shop_moren);
        //确认
        affirm=(Button)findViewById(R.id.affirm);
    }

    public void Listener()
    {


        mark="0";//标记是否选择了默认
        shopmoren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mark="1";
                shopmoren.setImageResource(R.mipmap.icon_mai_select);
            }
        });

        affirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences share=getSharedPreferences("info",Activity.MODE_WORLD_READABLE);
                mbcode=share.getString("mb_code","");
                Log.e("Mb_Code2",mbcode);

              add_address();

            }
        });
    }

    //会员收货地址添加
    public void add_address()
    {
        // LoginParams loginParams = new LoginParams(ed.getText().toString().trim(),ed2.getText().toString().toString(),"xxaabbc085412556sxxx",1 );
        Add_Address_Ok loginParams = new Add_Address_Ok(mbcode,
                                                        shopphone.getText().toString(),
                                                        shopshouhuoren.getText().toString(),
                                                        shopregion.getText().toString(),
                                                        mark,
                                                        shopdetailed_address.getText().toString());

        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("AddShippingAddress")
                .gson(new Gson())
                /*.noParams()*/
                .object(loginParams)

/*                .typeValue("string",mb_code)
                .typeValue("string",shop_phone.getText().toString().trim())
                .typeValue("string",shop_shouhuoren.getText().toString().trim())
                .typeValue("string",shop_region.getText().toString().trim())
                .typeValue("int",mark)
                .typeValue("string",shop_detailed_address.getText().toString().trim())*/

                .build();
//        ParamsBuilder<LoginParams> builder = new ParamsBuilder<LoginParams>().
//                .key("")
//                .build();

        Call<JsonResp> call = HttpClients.getInstance().add_address(requestBody);

        call.enqueue(new Callback<JsonResp>() {
            @Override
            public void onResponse(Call<JsonResp> call, Response<JsonResp> response) {
                String code = response.body().getData();
                Log.e("添加地址的数据",code);
                jiexi2(code);


            }

            @Override
            public void onFailure(Call<JsonResp> call, Throwable t) {
                Log.e("111","失败");
            }
        });


    }

    public void jiexi2(String code)
    {
        if(code.equals("1"))
        {
            Toast.makeText(App.this,"添加地址成功",Toast.LENGTH_SHORT).show();
            Intent t= new Intent(App.this, Shop_Select_Address.class);
            startActivity(t);
        }else{
            Toast.makeText(App.this,"添加地址失败",Toast.LENGTH_SHORT).show();
        }


    }

}

