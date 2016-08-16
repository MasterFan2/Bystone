package com.proton.bystone.ui.shop;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.proton.bystone.R;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.MTFBaseFragment;
import com.proton.library.ui.annotation.MTFActivityFeature;
import com.proton.library.ui.annotation.MTFFragmentFeature;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/7/26.
 * 商品列表
 */
@MTFActivityFeature(layout = R.layout.shop_shangping)
public class Shop_Catalogue extends MTFBaseActivity {


    @Override
    public void initialize(Bundle savedInstanceState) {
        Log.e("aaa","111");
    }

    @Override
    public void backPressed() {

    }
}
