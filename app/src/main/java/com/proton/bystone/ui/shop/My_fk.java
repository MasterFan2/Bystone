package com.proton.bystone.ui.shop;

import android.os.Bundle;

import com.proton.bystone.R;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.MTFBaseFragment;
import com.proton.library.ui.annotation.MTFActivityFeature;
import com.proton.library.ui.annotation.MTFFragmentFeature;

/**
 * Created by Administrator on 2016/7/27.
 * 反馈
 */
@MTFActivityFeature(layout = R.layout.my_retroaction)
public class My_fk extends MTFBaseActivity {

    @Override
    public void initialize(Bundle savedInstanceState) {

    }

    @Override
    public void backPressed() {
animFinish();

    }
}
