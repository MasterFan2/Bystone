package com.proton.bystone.ui.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.CarBrand;
import com.proton.bystone.bean.CarBrandTwoLevel;
import com.proton.bystone.bean.CarModel;
import com.proton.bystone.bean.LoginParams;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.utils.L;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;
import com.proton.library.utils.PinyinUtil;
import com.proton.library.widget.MyGridView;
import com.proton.library.widget.indexer.FancyIndexer;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 车辆品牌选择界面
 * create by masterfan on 2016.07.18
 */
@MTFActivityFeature(layout = R.layout.activity_car_brand)
public class CarBrandActivity extends MTFBaseActivity {

    ///请求码
    public static final int GET_CAR_BRAND_REQUEST = 0x01 >> 1;

    ///滑动控件
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    ///主品牌列表
    @Bind(R.id.lv_content)
    ListView contentListView;//
    private MyAdapter adapter;

    ///右侧弹出菜单
    @Bind(R.id.car_brand_right_menu_listview)
    ListView rightMenuListView;//
    private TwoLevelBrandAdapter twoLevelBrandAdapter;

    ///右滑二级菜单， 显示车辆年款
    @Bind(R.id.car_brand_right_menu_listview_level2)
    ListView yearListView;
    private CarModelAdapter carModelAdapter;

    ///索引控件
    @Bind(R.id.bar)
    FancyIndexer mFancyIndexer;

    ///搜索框
    EditText editText;

    ///右滑一、二级菜单
    @Bind(R.id.car_brand_right_menu_layout_level_one)
    LinearLayout levelOneLayout;
    @Bind(R.id.car_brand_right_menu_layout_level_two)
    LinearLayout levelTwoLayout;

    @Bind(R.id.car_brand_second_logo_img)
    ImageView secondlogoImg;

    @Bind(R.id.car_brand_second_name_txt)
    TextView  secondNameTxt;

    ///Header hot
    private MyGridView headerGridView;    //热门品牌 Grid
    private HotGridViewAdapter hotAdapter;//热门品牌适配器

    private List<CarBrand> brands    = null;//所有品牌
    private List<CarBrand> hotBrands = null;//热门品牌

    //-----------------选择的品牌数据----------------
    private CarBrand         selectedBrand;
    private CarBrandTwoLevel selectedTwoLevelBrand;
    private CarModel         selectedModel;

    @Override
    public void initialize(Bundle savedInstanceState) {

        View headerView = LayoutInflater.from(context).inflate(R.layout.car_brand_hot_header, null);
        headerGridView = (MyGridView) headerView.findViewById(R.id.item_car_brand_header_gridview);
        editText = (EditText) headerView.findViewById(R.id.item_car_brand_header_search_edit);

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        //add header
        contentListView.addHeaderView(headerView);

        //hot
        hotAdapter = new HotGridViewAdapter();
        headerGridView.setAdapter(hotAdapter);
        headerGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    drawerLayout.openDrawer(GravityCompat.END);
                }
                selectedBrand = hotBrands.get(position);
                getSecondLevelBrand(selectedBrand.getB_Code());
            }
        });

        //main adapter
        adapter = new MyAdapter();
        contentListView.setAdapter(adapter);

        //right menu
        twoLevelBrandAdapter = new TwoLevelBrandAdapter();
        rightMenuListView.setAdapter(twoLevelBrandAdapter);

        //car model
        carModelAdapter = new CarModelAdapter();
        yearListView.setAdapter(carModelAdapter);

        //索引的选择事件
        mFancyIndexer.setOnTouchLetterChangedListener(new FancyIndexer.OnTouchLetterChangedListener() {

            @Override
            public void onTouchLetterChanged(String letter) {
                // 从集合中查找第一个拼音首字母为letter的索引, 进行跳转
                for (int i = 0; i < brands.size(); i++) {
                    CarBrand goodMan = brands.get(i);
                    String s = goodMan.getPinyin().charAt(0) + "";
                    if (TextUtils.equals(s, letter)) {
                        // 匹配成功, 中断循环, 跳转到i位置
                        contentListView.setSelection(i);
                        break;
                    }
                }
            }
        });

        getHotData();

        //
        getData();
    }

    @OnItemClick(R.id.car_brand_right_menu_listview_level2)
    public void onModelYearItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectedModel = carModelAdapter.getData().get(position);
        Intent intent = new Intent();
        intent.putExtra("brand",         selectedBrand);
        intent.putExtra("brandTwoLevel", selectedTwoLevelBrand);
        intent.putExtra("brandModel",    selectedModel);
        setResult(RESULT_OK, intent);
        animFinish();
    }
    /**
     * 二级列表点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @OnItemClick(R.id.car_brand_right_menu_listview)
    public void onTwoLevelItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.openDrawer(GravityCompat.END);
        }
        selectedTwoLevelBrand = twoLevelBrandAdapter.getData().get(position);


        //动画显示二级菜单【年款】
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
        anim.setFillAfter(true);
        levelOneLayout.setVisibility(View.GONE);
        levelTwoLayout.setVisibility(View.VISIBLE);
        levelTwoLayout.startAnimation(anim);

        //获取数据
        getThirdLevelYear(selectedTwoLevelBrand.getB_Code());
//        setResult(RESULT_OK, intent);
//        animFinish();
    }

    /**
     * 主列表item点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @OnItemClick(R.id.lv_content)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.openDrawer(GravityCompat.END);
        }
        selectedBrand = brands.get(position - 1);

        //显示二级title的image和name
        Picasso.with(context).load(HttpClients.PIC_URL + selectedBrand.getB_logo()).placeholder(R.mipmap.ic_launcher).into(secondlogoImg);
        secondNameTxt.setText(selectedBrand.getB_Name());

        //获取二级数据
        getSecondLevelBrand(selectedBrand.getB_Code());
    }

    /**
     * 二级菜单关闭按钮
     * @param view
     */
    @OnClick(R.id.car_brand_right_menu_layout_level_two_rpl)
    public void closeLevelTwoMenu(View view){
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.slide_out_right);
        anim.setFillAfter(true);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                levelOneLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        levelTwoLayout.setVisibility(View.VISIBLE);
        levelTwoLayout.startAnimation(anim);
    }

    @OnClick(R.id.car_brand_right_menu_layout_level_one_rpl)
    public void closeLevelOneMenu(View view){
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        }
    }

    /**
     * 获取三级年款
     */
    private void getThirdLevelYear(String parentCode) {
        //获取三级年款
        final RequestBody requestBody = new ParamsBuilder<LoginParams>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetCarModel")
                .gson(new Gson())
                .typeValue("string", parentCode)
                .build();
        Call<BaseResp> call = HttpClients.getInstance().memberInfo(requestBody);
        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                List<CarModel> carModels = new Gson().fromJson(response.body().getData(), new TypeToken<List<CarModel>>() {}.getType());
                carModelAdapter.setData(carModels);
                carModelAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                L.e("getThirdLevelYear::" + t.getMessage());
            }
        });
    }

    /**
     * 获取二级品牌
     */
    private void getSecondLevelBrand(String parentCode) {
        //获取二级品牌
        final RequestBody requestBody = new ParamsBuilder<LoginParams>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetTwoBrands")
                .gson(new Gson())
                .typeValue("string", parentCode)
                .build();
        Call<BaseResp> call = HttpClients.getInstance().memberInfo(requestBody);
        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                List<CarBrandTwoLevel> twoLevelBrands = new Gson().fromJson(response.body().getData(), new TypeToken<List<CarBrandTwoLevel>>() {}.getType());
                twoLevelBrandAdapter.setData(twoLevelBrands);
                twoLevelBrandAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                L.e("getSecondLevelBrand::" + t.getMessage());
            }
        });
    }

    /**
     * 获取热门品牌
     */
    private void getHotData() {
        //获取车辆热门品牌
        final RequestBody requestBody = new ParamsBuilder<LoginParams>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetCarPopularBrands")
                .gson(new Gson())
                .noParams()//
                .build();
        Call<BaseResp> call = HttpClients.getInstance().memberInfo(requestBody);
        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                hotBrands = new Gson().fromJson(response.body().getData(), new TypeToken<List<CarBrand>>() {}.getType());
                hotAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                L.e(t.getMessage());
            }
        });
    }

    /**
     * 获取所有品牌
     */
    private void getData() {
        //获取品牌列表
        final RequestBody requestBody = new ParamsBuilder<LoginParams>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetCarABrand")
                .gson(new Gson())
//                .typeValue("string", "2016053018491859")
                .noParams()//
                .build();
        Call<BaseResp> call = HttpClients.getInstance().memberInfo(requestBody);
        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                brands = new Gson().fromJson(response.body().getData(), new TypeToken<List<CarBrand>>() {}.getType());
                for (CarBrand carBrand : brands) {
                    carBrand.setPinyin(PinyinUtil.getPinyin(carBrand.getB_Name()));
                }
                Collections.sort(brands);
                adapter.setDatas(brands);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                System.out.println(1);
            }
        });
    }

    /**
     * 热门品牌adapter
     */
    class HotGridViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return hotBrands == null ? 0 : hotBrands.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            final CarBrand carBrand = hotBrands.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_car_brand_hot, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }else  {
                holder = (ViewHolder) convertView.getTag();
            }

            //bind data
            holder.nameTxt.setText(carBrand.getB_Name());
            Picasso.with(context).load(carBrand.getB_logo()).placeholder(R.mipmap.ic_launcher).into(holder.logoImg);
            //return
            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.item_car_brand_hot_logo_img)
            ImageView logoImg;

            @Bind(R.id.item_car_brand_hot_brand_name_txt)
            TextView  nameTxt;

            public ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    /**
     * ListView adapter
     */
    class MyAdapter extends BaseAdapter {

        private List<CarBrand> datas;

        public void setDatas(List<CarBrand> datas) {
            this.datas = datas;
        }

        @Override
        public int getCount() {
            return datas == null ? 0 : datas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            final CarBrand carBrand = datas.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_car_brand, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //bind data
            // 进行分组, 比较上一个拼音的首字母和自己是否一致, 如果不一致, 就显示tv_index
            String currentLetter = carBrand.getPinyin().charAt(0) + "";
            String indexStr = null;
            if (position == 0) {
                // 1. 如果是第一位
                indexStr = currentLetter;
            } else {
                // 获取上一个拼音
                String preLetter = datas.get(position - 1).getPinyin().charAt(0) + "";
                if (!TextUtils.equals(currentLetter, preLetter)) {
                    // 2. 当跟上一个不同时, 赋值, 显示
                    indexStr = currentLetter;
                }
            }

            holder.indexLayout.setVisibility(indexStr == null ? View.GONE : View.VISIBLE);
            holder.indexTxt.setText(indexStr);
            holder.nameTxt.setText(carBrand.getB_Name());
            Picasso.with(context).load(HttpClients.PIC_URL + carBrand.getB_logo()).placeholder(R.mipmap.ic_launcher).into(holder.logoImg);

            //return
            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.item_car_brand_index_txt)
            TextView indexTxt;

            @Bind(R.id.item_car_brand_name_txt)
            TextView nameTxt;

            @Bind(R.id.item_car_brand_logo_img)
            ImageView logoImg;

            @Bind(R.id.item_car_brand_index_layout)
            LinearLayout indexLayout;

            public ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    /**
     * 二级品牌
     */
    class TwoLevelBrandAdapter extends BaseAdapter {

        private List<CarBrandTwoLevel> twoLevelBrands = null;//二级品牌

        public TwoLevelBrandAdapter(){}

        public List<CarBrandTwoLevel> getData() {
            return twoLevelBrands;
        }

        public void setData(List<CarBrandTwoLevel> twoLevelBrands) {
            this.twoLevelBrands = twoLevelBrands;
        }

        @Override
        public int getCount() {
            return twoLevelBrands == null ? 0 : twoLevelBrands.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final CarBrandTwoLevel twoLevelBrand = twoLevelBrands.get(position);
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_single_text_view, parent, false);
                holder = new ViewHolder(convertView);

                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            //bind data
            holder.textView.setText(twoLevelBrand.getB_Name());

            //return
            return convertView;
        }

        class ViewHolder  {
            @Bind(R.id.item_single_text_txt)
            TextView textView;

            public ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    /**
     * 三级年款
     */
    class CarModelAdapter extends BaseAdapter {

        private List<CarModel> carModels = null;//二级品牌

        public CarModelAdapter(){}

        public List<CarModel> getData() {
            return carModels;
        }

        public void setData(List<CarModel> carModels) {
            this.carModels = carModels;
        }

        @Override
        public int getCount() {
            return carModels == null ? 0 : carModels.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final CarModel carModel = carModels.get(position);
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_single_text_view, parent, false);
                holder = new ViewHolder(convertView);

                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            //bind data
            holder.textView.setText(carModel.getModel());

            //return
            return convertView;
        }

        class ViewHolder  {
            @Bind(R.id.item_single_text_txt)
            TextView textView;

            public ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    @Override
    public void backPressed() {
        animFinish();
    }

    @OnClick(R.id.m_title_left_btn)
    public void back(View view) {
        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        }else {
            animFinish();
        }
    }
}
