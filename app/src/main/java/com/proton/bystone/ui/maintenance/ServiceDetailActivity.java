package com.proton.bystone.ui.maintenance;

import android.os.Bundle;
import android.view.View;

import com.proton.bystone.R;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import butterknife.OnClick;

/**
 * 服务详情
 */
@MTFActivityFeature(layout = R.layout.activity_service_detail)
public class ServiceDetailActivity extends MTFBaseActivity {

    @Override
    public void initialize(Bundle savedInstanceState) {

    }

    @Override
    public void backPressed() {
        animFinish();
    }

    @OnClick(R.id.m_title_left_btn)
    public void back(View view) {
        animFinish();
    }
}
