package com.proton.bystone.ui.shop;

import android.os.Bundle;

import com.proton.bystone.R;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

/**
 * Created by Administrator on 2016/7/27.
 * 跳转我的
 */

@MTFActivityFeature(layout = R.layout.my_account)
public class My_Privilege extends MTFBaseActivity {


    @Override
    public void initialize(Bundle savedInstanceState) {

    }

    @Override
    public void backPressed() {
   animFinish();
    }
}
