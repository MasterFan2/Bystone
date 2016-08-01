package com.proton.bystone.ui.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.proton.bystone.R;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/27.
 * 跳转我的支付
 */

@MTFActivityFeature(layout = R.layout.shop_pay)
public class My_Shop_Pay extends MTFBaseActivity {
    @Bind(R.id.shop_pay)
    Button bt;//点击确认付款
    @Override
    public void initialize(Bundle savedInstanceState) {
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent t= new Intent(My_Shop_Pay.this,Shop_Orderdetail.class);
                startActivity(t);
            }
        });
    }

    @Override
    public void backPressed() {
   animFinish();
    }

    @OnClick(R.id.Home_fh)
    public void back(View view) {
        animFinish();
    }
}
