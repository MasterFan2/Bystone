package com.proton.bystone.ui.main.tab.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.proton.bystone.R;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import butterknife.OnClick;
/*
定位
*/

/**
 * Created by Administrator on 2016/7/14.
 */
@MTFActivityFeature(layout = R.layout.placename_service)
public class Search_service extends MTFBaseActivity {

    @OnClick(R.id.Home_fh)
    public void back(View view) {
        animFinish();
    }

    @Override
    public void initialize(Bundle savedInstanceState) {

    }

    @Override
    public void backPressed() {
        animFinish();
    }


}
