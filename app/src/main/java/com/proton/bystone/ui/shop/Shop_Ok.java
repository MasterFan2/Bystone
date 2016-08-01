package com.proton.bystone.ui.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.proton.bystone.R;
import com.proton.bystone.ui.shopcar.ShopCarActivity;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/20.
 * //跳转到订单确认
 */
@MTFActivityFeature(layout = R.layout.ok_arayacak)
public class Shop_Ok extends MTFBaseActivity {


    @Bind(R.id.Home_car)
    ImageView Home_car;

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
        byn=(Button)findViewById(R.id.shop_jian);//监听减号
        byt=(Button)findViewById(R.id.shop_jia);//监听加号
        nb=(EditText)findViewById(R.id.shop_nb);

        shop_money=(TextView)findViewById(R.id.shop_nb);
        shop_jinbi=(TextView)findViewById(R.id.shop_jinbi);

        viewById = (TextView) findViewById(R.id.money2);

        shop_mane= (TextView) findViewById(R.id.shop_money7788);//付款

        shop_switch= (Switch) findViewById(R.id.shop_switch);//shop_switch

        shop_heji  = (TextView) findViewById(R.id.shop_heji);//合计



        //添加收获地址
     shop_address=(TextView)findViewById(R.id.shop_address);
        shop_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t= new Intent(Shop_Ok.this,App.class);
                startActivity(t);
            }
        });
        shop_pay=(Button)findViewById(R.id.shop_pay);
        shop_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t= new Intent(Shop_Ok.this,My_Shop_Pay.class);
                startActivity(t);
            }
        });
    }

    public void  Listener()
    {
        byn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String trim = nb.getText().toString().trim();
            int i=Integer.valueOf(trim).intValue();

            s = viewById.getText().toString().trim();//暂时拿到会员价
            dv = Double.parseDouble(s);

            if(i>0)
            {
                i=i-1;
                nb.setText(i+"");
                shop_mane.setText(dv*i+"");
            }




        }
    });

        byt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = nb.getText().toString().trim();//
                int i=Integer.valueOf(trim).intValue();

                s = viewById.getText().toString().trim();//暂时拿到会员价
                dv = Double.parseDouble(s);

                if(i>=0)
                {
                  i=i+1;

                    nb.setText(i+"");


                   shop_mane.setText(dv*i+"");


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

        shop_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    Toast.makeText(Shop_Ok.this,"选中了",Toast.LENGTH_SHORT).show();
                    shop_jinbi.setText("27.00");

                    //下面算合计
                    String trim = shop_mane.getText().toString().trim();//暂时拿到会员价
                    sb= Double.parseDouble(trim);
                    double v = sb - 27;
                    shop_heji.setText(v+"");
                }else{
                    Toast.makeText(Shop_Ok.this,"没选中",Toast.LENGTH_SHORT).show();
                    shop_jinbi.setText("0.00");
                }
            }
        });

    }

    @OnClick(R.id.Home_fh)
    public void back(View view) {
        animFinish();
    }

}
