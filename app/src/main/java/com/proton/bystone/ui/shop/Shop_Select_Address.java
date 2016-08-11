package com.proton.bystone.ui.shop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
    ImageView shop_iv;

    String str;

    List<String> list2 =new ArrayList<String>();
    ArrayList<String> list3 = new ArrayList<String>();
    ArrayList<String> list4 = new ArrayList<String>();
    ArrayList<String> list5 = new ArrayList<String>();
    List<Shouhuodizhi> list;
    List<Shouhuodizhi> lis;
    TextView shop_delo;
    TextView  shop_compile;
    View view;
    listviewdapter adapter;
    listviewdapter adapter2;
    String data;
    String data3;
    int num;

    int id ;
    String user_code ;
    String phone ;
    String Ad_Name ;
    String Ad_Address ;
    String AddressDetaile ;
    TextView shop_select;
    int isDefault;

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
       // Log.e("Mb_Code3",str);
        add_address2();

        shop_newaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t= new Intent(Shop_Select_Address.this,App.class);
                t.putExtra("bj","add");
                startActivity(t);
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
             //   Log.e("data",content);
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

        add_address2();

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
             //   Log.e("取所有未删除的地址",data+"");
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
       // Log.e("取出收货地址",list+"");

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
            shop_compile= (TextView) view.findViewById(R.id.shop_compile );
            shop_select= (TextView) view.findViewById(R.id.shop_select );
            shop_iv= (ImageView) view.findViewById(R.id.shop_iv );


            phone = list.get(position).getAd_ContactNumber();//联系电话
            Ad_Name = list.get(position).getAd_Name();//联系人
            Ad_Address = list.get(position).getAd_Address();//地区地址


            shop_shouhuoren.setText(Ad_Name);
            shop_dizhi.setText(Ad_Address);
            shop_phone.setText(phone);

            num=list.get(position).getIsDefault();
            if(num==1)
            {
                shop_iv.setImageResource(R.mipmap.icon_mai_select);
            }else if(num==0)
            {
                shop_iv.setImageResource(R.mipmap.icon_mai_disabled);
            }

            if(num==1)
            {
                SharedPreferences settings = getSharedPreferences("fn", 0);
                SharedPreferences.Editor edit = settings.edit();

                edit.putString("Ad_Name",Ad_Name);
                edit.putString("phone",phone);
                edit.putString("Ad_Address",Ad_Address);
                edit.putString("num","wuge");

                edit.commit();
                Log.e("num","aaa"+Ad_Name+"bbb"+phone+Ad_Address+"888"+num);
            }


            shop_select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("position",position+"");

                    if(num==1)
                    {
                        shop_iv.setImageResource(R.mipmap.icon_mai_disabled);
                        num=0;
                    }else if(num==0)
                    {
                        shop_iv.setImageResource(R.mipmap.icon_mai_select);
                        num=1;
                    }




                }
            });



                   //重新编辑
            shop_compile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new AlertDialog.Builder(Shop_Select_Address.this).setTitle("系统提示")
                            .setMessage("确定要重新编辑该地址嘛？")
                            .setPositiveButton("返回",new DialogInterface.OnClickListener() {//添加确定按钮



                                @Override

                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                                    // TODO Auto-generated method stub



                                }

                            }).setNegativeButton("确定",new DialogInterface.OnClickListener() {//添加返回按钮



                        @Override

                        public void onClick(DialogInterface dialog, int which) {//响应事件

                            // TODO Auto-generated method stub


                            id=list.get(position).getID();

                      //      Log.e("id",id+"");
                            user_code = list.get(position).getUser_Code();
                      //      Log.e("user_code",user_code);
                            phone = list.get(position).getAd_ContactNumber();//联系电话
                            Ad_Name = list.get(position).getAd_Name();//联系人
                            Ad_Address = list.get(position).getAd_Address();//地区地址
                            AddressDetaile = list.get(position).getAddressDetaile();//详细地址
                            isDefault = list.get(position).getIsDefault();//详细地址

                            Intent t=new Intent(Shop_Select_Address.this,App.class);//跳转到编辑该地址页面（添加地址页面）
                            t.putExtra("bj","bj");
                            t.putExtra("id",id+"");
                            t.putExtra("user_code",user_code);
                            t.putExtra("Ad_Name",Ad_Name);
                            t.putExtra("phone",phone);
                            t.putExtra("Ad_Address",Ad_Address);
                            t.putExtra("AddressDetaile",AddressDetaile);
                            t.putExtra("isDefault",isDefault);

                            startActivity(t);

                        }

                    }).show();



                }
            });


            //点击删除
            shop_delo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new AlertDialog.Builder(Shop_Select_Address.this).setTitle("系统提示")
                            .setMessage("确定要删除该地址嘛？")
                            .setPositiveButton("返回",new DialogInterface.OnClickListener() {//添加确定按钮



                                @Override

                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                                    // TODO Auto-generated method stub



                                }

                            }).setNegativeButton("确定",new DialogInterface.OnClickListener() {//添加返回按钮



                        @Override

                        public void onClick(DialogInterface dialog, int which) {//响应事件

                            // TODO Auto-generated method stub

                            //传到服务器删除
                            id=list.get(position).getID();
                       //     Log.e("id",id+"");
                            user_code = list.get(position).getUser_Code();
                       //     Log.e("user_code",user_code);
                            phone = list.get(position).getAd_ContactNumber();//联系电话
                            Ad_Name = list.get(position).getAd_Name();//联系人
                            Ad_Address = list.get(position).getAd_Address();//地区地址
                            AddressDetaile = list.get(position).getAddressDetaile();//
                            del_address();

                        }

                    }).show();



                }
            });


            return view;

        }
    }


    @butterknife.OnClick(R.id.m_title_left_btn)
    public void back(View view) {
        animFinish();
    }





}
