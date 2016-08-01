package com.proton.bystone.ui.login;
/**
 * 我的主页
 */

import android.app.Activity;
import android.os.Bundle;

import com.proton.bystone.R;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

/**
 * Created by Administrator on 2016/7/6.
 */
@MTFActivityFeature(layout = R.layout.wodezhu)
public class HomePagesActivity extends MTFBaseActivity {
    @Override
    public void initialize(Bundle savedInstanceState) {

    }

    @Override
    public void backPressed() {
        animFinish();
    }
}
