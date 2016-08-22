package com.proton.bystone.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.proton.bystone.R;
import com.proton.bystone.ui.main.tab.HomeFragment;
import com.proton.bystone.ui.main.tab.MaintenanceFragment;
import com.proton.bystone.ui.main.tab.MeFragment;
import com.proton.bystone.ui.main.tab.ShopFragment;
import com.proton.bystone.utils.L;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.MTFBaseFragment;
import com.proton.library.ui.annotation.MTFActivityFeature;
import com.proton.library.widget.mtfdialog.MTDialog;
import com.proton.library.widget.mtfdialog.OnClickListener;

import butterknife.Bind;

/**
 * 主界面
 * create by masterfan
 */
@MTFActivityFeature(layout = R.layout.activity_main)
public class MainActivity extends MTFBaseActivity implements OnClickListener,TabSelectedDelegate {

    private int index = -1;       //touched index
    private int currentIndex = 0;//current selected


    //Fragment
    private HomeFragment homeFragment;
    private MaintenanceFragment maintainFragment;
    private ShopFragment shopFragment;
    private MeFragment meFragment;
    private MTFBaseFragment[] fragments;
    private ImageView[] imageViews;//set background

    //click area
    @Bind(R.id.tab_home_img)
    ImageView homeImg;

    @Bind(R.id.tab_maint_img)
    ImageView maintImg;

    @Bind(R.id.tab_shop_img)
    ImageView shopImg;

    @Bind(R.id.tab_mine_img)
    ImageView mineImg;

    Activity mActivity;

    /**
     * 测试方法
     */
    public void testMethod() {
        System.out.println("Test.");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //Fragment重叠的临时解决办法。
        //super.onSaveInstanceState(outState);
    }

    @Override
    public void initialize(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            L.e("<<<X>>>MainActivity>>>>savedInstanceState != null");
        } else {
            L.e(">>>X<<<MainActivity>>>>savedInstanceState is null");

        }
        //fragment init
        homeFragment = new HomeFragment();
        maintainFragment = new MaintenanceFragment();
        shopFragment = new ShopFragment();
        meFragment = new MeFragment();

        fragments = new MTFBaseFragment[]{homeFragment, maintainFragment, shopFragment, meFragment};


        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, homeFragment, "home")
                .add(R.id.fragment_container, maintainFragment, "maintenance")
                .add(R.id.fragment_container, shopFragment, "shop")
                .add(R.id.fragment_container, meFragment, "me")
                .hide(maintainFragment).hide(shopFragment)
                .hide(meFragment).show(homeFragment).commit();

        //layout
        imageViews = new ImageView[]{homeImg, maintImg, shopImg, mineImg};
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private long exitTime = 0L;

    @Override
    public void backPressed() {
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
                imageViews[0].setImageResource(R.mipmap.icon_tab_home_sel);
                imageViews[1].setImageResource(R.mipmap.icon_tab_maint_nor);
                imageViews[2].setImageResource(R.mipmap.icon_tab_shop_nor);
                imageViews[3].setImageResource(R.mipmap.icon_tab_min_nor);
                break;
            case R.id.home_maintain_layout:
                index = 1;
                imageViews[0].setImageResource(R.mipmap.icon_tab_home_nor);
                imageViews[1].setImageResource(R.mipmap.icon_tab_maint_sel);
                imageViews[2].setImageResource(R.mipmap.icon_tab_shop_nor);
                imageViews[3].setImageResource(R.mipmap.icon_tab_min_nor);
                break;
            case R.id.home_shop_layout:
                index = 2;
                imageViews[0].setImageResource(R.mipmap.icon_tab_home_nor);
                imageViews[1].setImageResource(R.mipmap.icon_tab_maint_nor);
                imageViews[2].setImageResource(R.mipmap.icon_tab_shop_sel);
                imageViews[3].setImageResource(R.mipmap.icon_tab_min_nor);
                break;
            case R.id.home_me_layout:
                index = 3;
                imageViews[0].setImageResource(R.mipmap.icon_tab_home_nor);
                imageViews[1].setImageResource(R.mipmap.icon_tab_maint_nor);
                imageViews[2].setImageResource(R.mipmap.icon_tab_shop_nor);
                imageViews[3].setImageResource(R.mipmap.icon_tab_min_sel);
                break;
        }
        if (index != currentIndex) {
            getSupportFragmentManager().beginTransaction()
                    .hide(fragments[currentIndex])
                    .show(fragments[index]).commit();
            fragments[index].load();//加载数据
            currentIndex = index;
        }
    }


    @Override
    public void onClick(MTDialog dialog, View view) {

    }

    private TabSelectedDelegate tabSelectedDelegate;
    public void setTabSelectedDelegate(TabSelectedDelegate tabSelectedDelegate) {
        this.tabSelectedDelegate = tabSelectedDelegate;
    }

    @Override
    public void check(int tab) {
        switch (tab) {
            case 1:
                index = 1;
                imageViews[0].setImageResource(R.mipmap.icon_tab_home_nor);
                imageViews[1].setImageResource(R.mipmap.icon_tab_maint_sel);
                imageViews[2].setImageResource(R.mipmap.icon_tab_shop_nor);
                imageViews[3].setImageResource(R.mipmap.icon_tab_min_nor);
                break;
            case 2:
                index = 2;
                imageViews[0].setImageResource(R.mipmap.icon_tab_home_nor);
                imageViews[1].setImageResource(R.mipmap.icon_tab_maint_nor);
                imageViews[2].setImageResource(R.mipmap.icon_tab_shop_sel);
                imageViews[3].setImageResource(R.mipmap.icon_tab_min_nor);
                break;
        }

        getSupportFragmentManager().beginTransaction()
                .hide(fragments[currentIndex])
                .show(fragments[index]).commit();
        fragments[index].load();//加载数据
        currentIndex = index;
    }
}
