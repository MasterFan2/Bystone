package com.proton.bystone.ui.shop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.proton.bystone.R;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/20.
 * 订单详情表
 */
@MTFActivityFeature(layout = R.layout.shop_shop_orderdetail)
public class Shop_Orderdetail extends MTFBaseActivity {

    TextView shop_address;
    List<String> list = new ArrayList<String>();


    @Override
    public void initialize(Bundle savedInstanceState) {
        initview();
    }

    @Override
    public void backPressed() {

    }

    public void initview()
    {

    }

}
