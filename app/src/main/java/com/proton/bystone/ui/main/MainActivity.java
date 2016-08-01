package com.proton.bystone.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.OrderStateCodeResp;
import com.proton.bystone.bean.ReservationParam;
import com.proton.bystone.bean.Version;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.ui.main.tab.HomeFragment;
import com.proton.bystone.ui.main.tab.MaintenanceFragment;
import com.proton.bystone.ui.main.tab.MeFragment;
import com.proton.bystone.ui.main.tab.ShopFragment;
import com.proton.bystone.ui.maintenance.OrderStateActivity;
import com.proton.bystone.utils.L;
import com.proton.bystone.utils.PackageUtil;
import com.proton.bystone.utils.T;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.MTFBaseFragment;
import com.proton.library.ui.annotation.MTFActivityFeature;
import com.proton.library.widget.mtfdialog.MTDialog;
import com.proton.library.widget.mtfdialog.OnClickListener;
import com.proton.library.widget.mtfdialog.ViewHolder;

import java.util.List;

import butterknife.Bind;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 主界面
 * create by masterfan
 */
@MTFActivityFeature(layout = R.layout.activity_main)
public class MainActivity extends MTFBaseActivity implements OnClickListener {

    private int index = 0;       //touched index
    private int currentIndex = 0;//current selected


    //Fragment
    private HomeFragment homeFragment;
    private MaintenanceFragment maintainFragment;
    private ShopFragment shopFragment;
    private MeFragment meFragment;
    private MTFBaseFragment[] fragments;
    private RelativeLayout[] layouts;//set background

    //click area
    @Bind(R.id.home_home_layout)
    RelativeLayout homeLayout;

    @Bind(R.id.home_maintain_layout)
    RelativeLayout maintainLayout;

    @Bind(R.id.home_shop_layout)
    RelativeLayout shopLayout;

    @Bind(R.id.home_me_layout)
    RelativeLayout meLayout;

    Activity mActivity;

    @Override
    public void initialize(Bundle savedInstanceState) {

        //fragment init
        homeFragment = new HomeFragment();
        maintainFragment = new MaintenanceFragment();
        shopFragment = new ShopFragment();
        meFragment = new MeFragment();

        fragments = new MTFBaseFragment[]{homeFragment, maintainFragment, shopFragment, meFragment};

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, homeFragment)
                .add(R.id.fragment_container, maintainFragment)
                .add(R.id.fragment_container, shopFragment)
                .add(R.id.fragment_container, meFragment)
                .hide(maintainFragment).hide(shopFragment)
                .hide(meFragment).show(homeFragment).commit();

        //layout
        layouts = new RelativeLayout[]{homeLayout, maintainLayout, shopLayout, meLayout};
        layouts[0].setBackgroundColor(getResources().getColor(R.color.mtf_gray_700));

        checkVersion();
    }

    private void checkVersion() {
        final RequestBody requestBody = new ParamsBuilder<ReservationParam>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetAppTheLatestVersion")
                .gson(new Gson())
                .typeValue("int", 1)
                .typeValue("string", PackageUtil.getVersionName(context))
                .build();
        Call<BaseResp> call = HttpClients.getInstance().memberInfo(requestBody);
        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                if (response.body().getCode() == 1) {
                    Version versionInfo = new Gson().fromJson(response.body().getData(), new TypeToken<Version>() {}.getType());

                    showDialog(versionInfo);
//                    T.s(context, "预约成功");
//                    Intent intent = new Intent(context, OrderStateActivity.class);
//                    intent.putExtra("code", orderStateResps.get(0).getCode());//orderStateResps.get(0).getCode()201605091528083056
//                    animStart(intent);
                } else {
//                    T.s(context, "预约失败");
                }
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                L.e("getThirdLevelYear::" + t.getMessage());
            }
        });
    }

    /**
     * 显示升级提示框
     * @param version
     */
    private void showDialog(Version version) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_upload, null);
        AlertDialog tipsDialog = new AlertDialog.Builder(context)
                .setTitle("新版本更新提示")
                .setView(contentView)
                .setCancelable(false)
                .create();
//        tipsDialog.show();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    @Override
    public void backPressed() {
        animFinish();
    }

    /**
     * tab click
     *
     * @param view
     */
    public void tabClick(View view) {
        switch (view.getId()) {
            case R.id.home_home_layout:
                index = 0;
//                titleView.setTitleText("首页");
                break;
            case R.id.home_maintain_layout:
                index = 1;
//                titleView.setTitleText("维保");
                break;
            case R.id.home_shop_layout:
                index = 2;
//                titleView.setTitleText("商城");
                break;
            case R.id.home_me_layout:
                index = 3;
//                titleView.setTitleText("我");
                break;
        }

        //
        if (index != currentIndex) {
            getSupportFragmentManager().beginTransaction()
                    .hide(fragments[currentIndex])
                    .show(fragments[index]).commit();
            layouts[currentIndex].setBackgroundColor(getResources().getColor(android.R.color.transparent));
            layouts[index].setBackgroundColor(getResources().getColor(R.color.mtf_gray_700));
            fragments[index].load();//加载数据
            currentIndex = index;
        }
    }


    @Override
    public void onClick(MTDialog dialog, View view) {

    }
}
