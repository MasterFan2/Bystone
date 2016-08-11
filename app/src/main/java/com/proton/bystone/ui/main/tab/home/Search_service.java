package com.proton.bystone.ui.main.tab.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.Category_nameResp;
import com.proton.bystone.bean.Home_Sh;
import com.proton.bystone.bean.JsonResp;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.ui.shop.Shop_Commodity;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/*
定位
*/

/**
 * Created by Administrator on 2016/7/14.
 */
@MTFActivityFeature(layout = R.layout.placename_service)
public class Search_service extends MTFBaseActivity {
    @Bind(R.id.Home_Search)
    AutoCompleteTextView Home_Search;
    @Bind(R.id.home_serv)
    ImageView home_serv;
    ArrayList<String> result;
    ArrayList<String> code;

    String trim;
    @OnClick(R.id.Home_fh)
    public void back(View view) {
        animFinish();
    }

    @Override
    public void initialize(Bundle savedInstanceState) {
        trim = Home_Search.getText().toString().trim();
        Category_name();//收索商品


        result = new ArrayList<String>();
        code = new ArrayList<String>();

        Home_Search.setAdapter(new ArrayAdapter<String>(
              this,
                android.R.layout.simple_dropdown_item_1line,
                result)
        );

        Home_Search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object itemAtPosition = parent.getItemAtPosition(position);
                Toast.makeText(Search_service.this,itemAtPosition+""+position,Toast.LENGTH_LONG).show();
              Intent  t=new Intent(Search_service.this,Shop_Commodity.class);
                t.putExtra("ps_code",code.get(position));
                startActivity(t);
            }
        });

        //点空气滤芯
        Button but =(Button) findViewById(R.id.home_kqnx);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  t=new Intent(Search_service.this,Shop_Commodity.class);
                t.putExtra("ps_code",code.get(4));
                startActivity(t);
            }
        });

        //点空气滤芯
        Button bt =(Button) findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  t=new Intent(Search_service.this,Shop_Commodity.class);
                t.putExtra("ps_code",code.get(4));
                startActivity(t);
            }
        });

        //音响
        Button home_yx =(Button) findViewById(R.id.home_yx);
        home_yx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  t=new Intent(Search_service.this,Shop_Commodity.class);
                t.putExtra("ps_code",code.get(4));
                startActivity(t);
            }
        });

        //音响
        Button yx2 =(Button) findViewById(R.id.yx2);
        yx2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  t=new Intent(Search_service.this,Shop_Commodity.class);
                t.putExtra("ps_code",code.get(4));
                startActivity(t);
            }
        });

        //防滑由
        Button home_fhy =(Button) findViewById(R.id.home_fhy);
        home_fhy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  t=new Intent(Search_service.this,Shop_Commodity.class);
                t.putExtra("ps_code",code.get(5));
                startActivity(t);
            }
        });

        //机油虐心
        Button home_jylx =(Button) findViewById(R.id.home_jylx);
        home_jylx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  t=new Intent(Search_service.this,Shop_Commodity.class);
                t.putExtra("ps_code",code.get(26));
                startActivity(t);
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

        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetMoreCategories")
                .gson(new Gson())
                .noParams()
                // .object(loginParams)
               /* .typeValue("string","刹车片")*/
                .build();
//        ParamsBuilder<LoginParams> builder = new ParamsBuilder<LoginParams>().
//                .key("")
//                .build();

        Call<BaseResp> call = HttpClients.getInstance().commodity(requestBody);

        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                String data = response.body().getData();

                Log.e("收索商品",data+"");
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
        List<Home_Sh>   list=new Gson().fromJson(data, new TypeToken<List<Home_Sh>>() {}.getType());

        for(int i=0;i<list.size();i++) {
            String ps_name = list.get(i).getPs_NAME();
            String ps_code = list.get(i).getPs_CODE();
            result.add(ps_name);
            code.add(ps_code);
        }

        Log.e("ee",result+"");



    }


/*    t=new Intent(getActivity(),Shop_Commodity.class);
    t.putExtra("ps_code",ps_code);
    startActivity(t);*/



}
