package com.proton.bystone.ui.shop;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.JsonResp;
import com.proton.bystone.bean.Del_Add_Address_Ok;
import com.proton.bystone.bean.Shouhuodizhi;
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
 * Created by Administrator on 2016/7/21.
 * 选着收货地址
 */

@MTFActivityFeature(layout = R.layout.shop_select_address)
public class Shop_Select_Address extends MTFBaseActivity {
    ListView lv;
    SharedPreferences sharedata;
    Button shop_newaddress;

    String str;

    List<String> list2 =new ArrayList<String>();
    ArrayList<String> list3 = new ArrayList<String>();
    ArrayList<String> list4 = new ArrayList<String>();
    ArrayList<String> list5 = new ArrayList<String>();
    List<Shouhuodizhi> list;
    TextView shop_delo;
    View view;
    listviewdapter adapter;
    String data;


    int id ;
    String user_code ;
    String phone ;
    String Ad_Name ;
    String Ad_Address ;
    String AddressDetaile ;

    @Override
    public void initialize(Bundle savedInstanceState) {
        initview();

    }

    @Override
    public void backPressed() {
        animFinish();
    }

    public void initview()
    {
        lv=(ListView)findViewById(R.id.shop_lv);
        shop_newaddress= (Button)findViewById(R.id.shop_newaddress );
        SharedPreferences share=getSharedPreferences("info",Activity.MODE_WORLD_READABLE);
        str=share.getString("mb_code","");
        Log.e("Mb_Code3",str);
        add_address2();

        shop_newaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t= new Intent(Shop_Select_Address.this,App.class);
                startActivity(t);
            }
        });
    }

    //会员收货地址删除。。。。。。。
    public void del_address()
    {
        // LoginParams loginParams = new LoginParams(ed.getText().toString().trim(),ed2.getText().toString().toString(),"xxaabbc085412556sxxx",1 );
        Del_Add_Address_Ok loginParams = new Del_Add_Address_Ok(id,
                user_code,
                phone,
                Ad_Name,
                Ad_Address,
                2,
                AddressDetaile);

        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("UpdateShippingAddress")
                .gson(new Gson())
               // .noParams()
              .object(loginParams)
/*
              .typeValue("int",id)
                .typeValue("string",user_code)
                .typeValue("string",phone)
                .typeValue("string",Ad_Name)
                .typeValue("string",Ad_Address)
                .typeValue("int",2)
                .typeValue("string",AddressDetaile)*/

                .build();
//        ParamsBuilder<LoginParams> builder = new ParamsBuilder<LoginParams>().
//                .key("")
//                .build();

        Call<BaseResp> call = HttpClients.getInstance().memberInfo(requestBody);

        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                String content = response.body().getData();
                Log.e("data",content);
                jiexi2(content);


            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                Log.e("111","失败");
            }
        });


    }


    //删除
    public void jiexi2(String content)
    {
        if(content.equals("success"))
        {
           Toast.makeText(this,"删除地址成功"+id,Toast.LENGTH_SHORT).show();

        }


    }

    //取出收货地址
    public void add_address2()
    {
        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetShippingAddress")
                .gson(new Gson())
                /*.noParams()*/
                //.object(loginParams)
                .typeValue("string",str)
                .typeValue("int",2)
                .build();
//
        Call<JsonResp> call = HttpClients.getInstance().no_all_address(requestBody);
        call.enqueue(new Callback<JsonResp>() {
            @Override
            public void onResponse(Call<JsonResp> call, Response<JsonResp> response) {
              data = response.body().getData();
                Log.e("取所有未删除的地址",data+"");
                eventjiexi(data);
            }

            @Override
            public void onFailure(Call<JsonResp> call, Throwable t) {
                Log.e("111","失败");
            }
        });


    }

    //解析
    public void eventjiexi(String data)
    {
        list=new Gson().fromJson(data, new TypeToken<List<Shouhuodizhi>>() {}.getType());
        adapter= new listviewdapter();
        lv.setAdapter(adapter);
        Log.e("取出收货地址",list+"");



    }


    class listviewdapter extends BaseAdapter
    {
        @Override
        public int getCount() {
            // TODO Auto-generated method stub

            return list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            // TODO Auto-generated method stub
            //convertView=View.inflate(mActivity, R.layout.julebuhomelistviewone, null);
            view = View.inflate(Shop_Select_Address.this, R.layout.shop_profile,null);
            //更多
            TextView shop_shouhuoren= (TextView) view.findViewById(R.id.shop_shouhuoren );
            TextView shop_dizhi= (TextView) view.findViewById(R.id.shop_dizhi );
            TextView shop_phone= (TextView) view.findViewById(R.id.shop_phone );
            shop_delo= (TextView) view.findViewById(R.id.shop_delo );


            phone = list.get(position).getAd_ContactNumber();//联系电话
            Ad_Name = list.get(position).getAd_Name();//联系人
            Ad_Address = list.get(position).getAd_Address();//地区地址

            shop_shouhuoren.setText(Ad_Name);
            shop_dizhi.setText(Ad_Address);
            shop_phone.setText(phone);

                   //点击删除
        shop_delo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //传到服务器删除
                id=list.get(position).getID();
                Log.e("id",id+"");
                user_code = list.get(position).getUser_Code();
                Log.e("user_code",user_code);
                phone = list.get(position).getAd_ContactNumber();//联系电话
                Ad_Name = list.get(position).getAd_Name();//联系人
                Ad_Address = list.get(position).getAd_Address();//地区地址
                AddressDetaile = list.get(position).getAddressDetaile();//
                 del_address();

            }
        });




            return view;

        }
    }



}
