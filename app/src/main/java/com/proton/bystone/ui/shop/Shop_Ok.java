package com.proton.bystone.ui.shop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BitmapUtils;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.Commodity;
import com.proton.bystone.bean.E_Order;
import com.proton.bystone.bean.Main_Order;
import com.proton.bystone.bean.shop_Order;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.ui.shopcar.ShopCarActivity;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/20.
 * //跳转到订单确认
 */
@MTFActivityFeature(layout = R.layout.ok_arayacak)
public class Shop_Ok extends MTFBaseActivity {
    @Bind(R.id.Home_car)
    ImageView Home_car;
    String  order;
    String aa="http://192.168.0.119";
    TextView shop_address;
    Button  shop_pay;
    Button  byn;
    Button  byt;

    EditText   nb;
    TextView  shop_money;
    TextView viewById;
    TextView shop_mane;
    Switch   shop_switch;
    double dv;
    String s;
    TextView shop_jinbi;
    TextView shop_heji;
    Double sb;
    double dikou;
    String nu;
    String Ad ;
    String ph ;
    String Ade ;
    int i;//商品数量
    List<shop_Order> shop_Order;
    String orderCode;

    ImageView  shop_listview_item;
    TextView shop_tv ;
    TextView shop_by;
    TextView shop_money2 ;
    TextView member2 ;
    String vcparams ;
    String shop_member ;
    String shop_mone ;
    String img ;
    String shop_prompt ;
    double v;
    String I_Company;
    String getVC_Code;

    String mb_code;//会员编号
    String VC_XH ;//商家规格型号
    String getVC_Name ;//商品名称
    EditText shop_beizhu;


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
        I_Company = getIntent().getStringExtra("I_Company");//商家编号

        /*订单确认图和各种数据*/
         vcparams = getIntent().getStringExtra("vcparams");
         shop_member = getIntent().getStringExtra("shop_member");
         shop_mone = getIntent().getStringExtra("shop_money");
         img = getIntent().getStringExtra("list");
         shop_prompt = getIntent().getStringExtra("shop_prompt");

         shop_listview_item=(ImageView)findViewById(R.id.shop_listview_item);
        shop_tv = (TextView) findViewById(R.id.shop_tv);
        shop_by = (TextView) findViewById(R.id.shop_by);
         shop_money2 = (TextView) findViewById(R.id.shop_money2);//会员价
         member2 = (TextView) findViewById(R.id.member2);//原价
        shop_tv.setText(vcparams);//头
        shop_by.setText(shop_prompt);//让你的爱车
        member2.setText(shop_mone);//原价
        shop_money2.setText(shop_member);//会员
        BitmapUtils  bit=new BitmapUtils(Shop_Ok.this);
        bit.display(shop_listview_item,aa+img);

       shop_beizhu  =(EditText)findViewById(R.id.shop_beizhu);//订单备注
        byn=(Button)findViewById(R.id.shop_jian);//监听减号
        byt=(Button)findViewById(R.id.shop_jia);//监听加号
        nb=(EditText)findViewById(R.id.shop_nb);

        shop_money=(TextView)findViewById(R.id.shop_nb);
        shop_jinbi=(TextView)findViewById(R.id.shop_jinbi);

        viewById = (TextView) findViewById(R.id.shop_money2);

        shop_mane= (TextView) findViewById(R.id.shop_money7788);//付款

        shop_switch= (Switch) findViewById(R.id.shop_switch);//shop_switch

        shop_heji  = (TextView) findViewById(R.id.shop_heji);//合计


        shop_pay = (Button) findViewById(R.id.shop_pay);
        shop_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先生成订单在跳转
             place_order();



            }
        });


        TextView shop_shr  = (TextView) findViewById(R.id.shop_shr);
        TextView shop_adname  = (TextView) findViewById(R.id.shop_adname);
        TextView shop_dingdanok  = (TextView) findViewById(R.id.shop_dingdanok);
        TextView shop_num  = (TextView) findViewById(R.id.shop_num);
        TextView shop_dizi  = (TextView) findViewById(R.id.shop_dizi);
        TextView shop_dz  = (TextView) findViewById(R.id.shop_dz);
        shop_address = (TextView) findViewById(R.id.shop_address);

        SharedPreferences settings = getSharedPreferences("fn", 0);
         nu = settings.getString("num","");
         Ad = settings.getString("Ad_Name","");
         ph = settings.getString("phone","");
         Ade = settings.getString("Ad_Address","");
       // Log.e("nu","1111"+Ad+"aaa"+ph+Ade+"5555"+nu);
        if(nu.equals("wuge"))
        {
            shop_shr.setVisibility(View.VISIBLE);
            shop_adname.setVisibility(View.VISIBLE);
            shop_dingdanok.setVisibility(View.VISIBLE);
            shop_num.setVisibility(View.VISIBLE);
            shop_dizi.setVisibility(View.VISIBLE);
            shop_dz.setVisibility(View.VISIBLE);
            shop_address.setVisibility(View.GONE);

            shop_adname.setText(Ad);
            shop_num.setText(ph);
            shop_dz.setText(Ade);
        }else {
            shop_shr.setVisibility(View.GONE);
            shop_adname.setVisibility(View.GONE);
            shop_dingdanok.setVisibility(View.GONE);
            shop_num.setVisibility(View.GONE);
            shop_dizi.setVisibility(View.GONE);
            shop_dz.setVisibility(View.GONE);

            shop_address.setVisibility(View.VISIBLE);

            //添加收获地址

            shop_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent t = new Intent(Shop_Ok.this, App.class);
                    t.putExtra("bj", "add");
                    startActivity(t);


                }
            });

        }


    }

    public void  Listener()
    {
        //减号
        byn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String trim = nb.getText().toString().trim();
            i = Integer.valueOf(trim).intValue();
            s = viewById.getText().toString().trim();//暂时拿到会员价
            dv = Double.parseDouble(s);
            Log.e("", s);
            if (i > 0) {
                i = i - 1;
                nb.setText(i + "");
                shop_mane.setText(dv * i + "");
                shop_heji.setText(dv * i + "");
            }
        }
        });

        byt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = nb.getText().toString().trim();//
                 i=Integer.valueOf(trim).intValue();

                s = viewById.getText().toString().trim();//暂时拿到会员价
                dv = Double.parseDouble(s);

                if(i>=0)
                {
                  i=i+1;

                    nb.setText(i+"");
                    shop_mane.setText(dv*i+"");
                    shop_heji.setText(dv*i+"");
                }

            }
        });
//跳转购物车
        Home_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t= new Intent(Shop_Ok.this,ShopCarActivity.class);
                startActivity(t);
            }
        });


   s = viewById.getText().toString().trim();//暂时拿到会员价
      Double  dv = Double.parseDouble(s);
        shop_heji.setText(dv+"");
        shop_mane.setText(dv+"");

        String trim = shop_mane.getText().toString().trim();//暂时拿到会员价
        sb= Double.parseDouble(trim);
        v=sb;
        shop_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    dikou=27;
                    shop_jinbi.setText(dikou+"");

                    //下面算合计
                    String trim = shop_mane.getText().toString().trim();//暂时拿到会员价
                    sb= Double.parseDouble(trim);
                     v = sb - 27;
                    shop_heji.setText(v+"");
                }else{
                    dikou=0;
                    shop_jinbi.setText(dikou+"");

                    //下面算合计
                    String trim = shop_mane.getText().toString().trim();//暂时拿到会员价
                    sb= Double.parseDouble(trim);
                    v = sb -0;
                    shop_heji.setText(v+"");
                }
            }
        });

    }

    @OnClick(R.id.Home_fh)
    public void back(View view) {
        animFinish();
    }


    //生成订单
    public void  place_order()
    {
        SharedPreferences sp = this.getSharedPreferences("info",
                Context.MODE_PRIVATE);
        mb_code = sp.getString("mb_code", "");//会员编号


        VC_XH = getIntent().getStringExtra("VC_XH");//商家规格型号
         getVC_Name = getIntent().getStringExtra("getVC_Name");//商品名称
       getVC_Code = getIntent().getStringExtra("getVC_Code");//商品订单


        Main_Order loginParams = new Main_Order("",mb_code,shop_mone,shop_member,dikou,3,v,"");

       /* Log.e("dikou ",dikou+"");
        Log.e("v2",v+"");*/

      ArrayList<E_Order>  list=new ArrayList<E_Order>();
      E_Order e_Order = new E_Order(I_Company,mb_code,getVC_Code,shop_mone,VC_XH,getVC_Name,"",i+1+"" ,v,
            shop_member,1,"13629770634",3,shop_beizhu.getText().toString().trim(),ph,Ad,Ade);
        list.add(e_Order) ;

        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("SubmitOrder")
                .gson(new Gson())

                /*.noParams()*/
                .object(loginParams)
                .object(list)
               /* .typeValue("string","[")
                .typeValue("String","{")

                .typeValue("String","}")
                .typeValue("String","]")*/


                .build();

      //   Log.e("e",list+"");
//

        Call<BaseResp> call = HttpClients.getInstance().orderInfo(requestBody);

        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                String data = response.body().getData();
               // Log.e("订单生成提交",data+"");

                 jiexi(data);
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                Log.e("111","失败");
            }
        });


    }

    //解析
    public void jiexi(String data)
    {


        Gson gson=new Gson();
        com.proton.bystone.bean.shop_Order shop = gson.fromJson(data, shop_Order.class);
        //拿到订单编号
       //orderCode = shop.getOrderCode();
       Intent t = new Intent(Shop_Ok.this, My_Shop_Pay.class);
       //t.putExtra("ddbh",order);//订单编号*/
       //Log.e("orderCode",orderCode);
       startActivity(t);
    }


}
