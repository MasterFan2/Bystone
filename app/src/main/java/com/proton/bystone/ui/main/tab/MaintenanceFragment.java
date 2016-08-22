package com.proton.bystone.ui.main.tab;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proton.bystone.R;
import com.proton.bystone.bean.Advert;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.CarCombo;
import com.proton.bystone.bean.CarComboMaintenance;
import com.proton.bystone.bean.CarInfo;
import com.proton.bystone.bean.LoginParams;
import com.proton.bystone.bean.ReservationParam;
import com.proton.bystone.cache.LoginManager;
import com.proton.bystone.config.Config;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.ui.common.MyCarActivity;
import com.proton.bystone.ui.login.LoginActivity;
import com.proton.bystone.ui.main.TabSelectedDelegate;
import com.proton.bystone.ui.maintenance.BespeakActivity;
import com.proton.bystone.ui.maintenance.ComboActivity;
import com.proton.bystone.utils.L;
import com.proton.bystone.utils.T;
import com.proton.library.ui.MTFBaseFragment;
import com.proton.library.ui.annotation.MTFFragmentFeature;
import com.proton.library.widget.CircleIndicator;
import com.proton.library.widget.MyListView;
import com.proton.library.zxing.activity.CaptureActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MaintenanceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@MTFFragmentFeature(layout = R.layout.fragment_maintain)
public class MaintenanceFragment extends MTFBaseFragment {

//    private final String[] tabs = {"上门保养", "维修保养"};

    private final static int GET_DATA                 = 0x01 >> 1;//获取数据
    private final static int REQUEST_EXTERNAL_STORAGE = 0x01 >> 2;//请求

    private int previousPosition = -1;  //上一个选择的tab position
    private boolean loading = false;    //标识是否正在读取数据， 正在读取的时候不能切换tab

    @Bind(R.id.home_maintain_listview)
    MyListView listView;

    @Bind(R.id.view_pager)
    ViewPager viewPager;

    @Bind(R.id.home_maintain_pager_layout)
    LinearLayout pagerLayout;

    @Bind(R.id.view_pager_indicator)
    CircleIndicator indicator;

    @Bind(R.id.home_maintain_img_txt_layout)
    LinearLayout imgTxtLayout;

    Handler handler;

    //总套餐数据
//    private ArrayList<List<CarCombo>> data = null;
    private ArrayList<CarComboMaintenance> comboLists = new ArrayList<>();
    private boolean readedDefault = false;//是否读取过默认车辆的套餐

    private ArrayList<CarCombo> newData = null;

    ///data and adapter
    private ComboAdapter comboAdapter = new ComboAdapter();

    //自己设置的默认车辆
    private CarInfo defaultCarInfo;

    //到自己爱车列表选择的车辆
    private CarInfo chooseCarInfo;

    /**
     * default constructor
     */
    public MaintenanceFragment() {
        // Required empty public constructor
    }

    @OnClick(R.id.m_title_right_btn)
    public void scanClick(View view) {
        animStart(CaptureActivity.class);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MaintenanceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MaintenanceFragment newInstance(Bundle args) {
        MaintenanceFragment fragment = new MaintenanceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initialize() {
        comboAdapter = new ComboAdapter();
        listView.setAdapter(comboAdapter);
//        data = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();
        //权限检测、读取本地数据
//        checkPermission();
    }

    /**
     * 权限请求结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE://请求读写权限
            {
                //授权成功
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //getData(null);
                } else {//授权失败
                    T.s(context, "获取权限失败");
                }
            }
            break;
        }
    }

    /**
     * 检测权限
     */
    private void checkPermission() {
        //请求权限
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //没有对应的权限， 请求权限
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
            } else {
                //getData(null);
            }
        } else {
            //getData(null);
        }
    }

    /**
     * 获取套餐数据ListView
     */
    private void getData(String model, final boolean isDefault) {

        if (model != null) {
            listView.setVisibility(View.VISIBLE);
            imgTxtLayout.setVisibility(View.GONE);

            //获取套餐
            final RequestBody requestBody = new ParamsBuilder<LoginParams>()
                    .key("pbevyvHkf1sFtyGL35gFfQ==")
                    .methodName("GetApplicableModelsGoods")
                    .gson(new Gson())
                    .typeValue("string", model != null ? "2016053018491859" : model)//2016053018491859临时写死
                    .build();
            Call<BaseResp> call = HttpClients.getInstance().maintenance(requestBody);
            call.enqueue(new Callback<BaseResp>() {
                @Override
                public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                    if (response.body() != null && !TextUtils.isEmpty(response.body().getData())) {
                        try {
                            JSONArray jsonArray = new JSONArray(response.body().getData());
                            List<CarCombo> carCombos = new Gson().fromJson(jsonArray.get(0).toString(), new TypeToken<List<CarCombo>>() {}.getType());
                            CarComboMaintenance comboMaintenance = null;
                            if (isDefault) {//保存默认套餐
                                comboMaintenance = new CarComboMaintenance(defaultCarInfo.getB_Name(), defaultCarInfo.getI_CarDetail(), 1, carCombos);
                                comboMaintenance.setCarInfo(defaultCarInfo);
                            } else {
                                comboMaintenance = new CarComboMaintenance(chooseCarInfo.getB_Name(), chooseCarInfo.getI_CarDetail(), 0, carCombos);
                                comboMaintenance.setCarInfo(chooseCarInfo);
                            }

                            comboLists.add(comboMaintenance);
//                            data.add(carCombos);

                            comboAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            L.e(">>MaintenanceFragment>JSONException>line243");
                        }
                    } else {
                        L.e("解析上门保养套餐>>>没有数据");
                    }
                }

                @Override
                public void onFailure(Call<BaseResp> call, Throwable t) {
                    L.e(t.getMessage());
                }
            });

        } else {
            listView.setVisibility(View.GONE);
            imgTxtLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 输入车辆信息匹配
     *
     * @param view
     */
    @OnClick(R.id.home_maintain_input_txt)
    public void inputTextClick(View view) {
        if (LoginManager.getInstance().isLogin()){
            Intent intent = new Intent(context, MyCarActivity.class);
            intent.putExtra("where", MyCarActivity.WHERE_FRAGMENT_MAINTENANCE_HOME);
            animStartForResult(intent, 9527);
        } else {
            Intent intent = new Intent(context, LoginActivity.class);
            animStartForResult(intent, 9528);
        }
    }

    @Override
    public void load() {
        getAdList();    //获取广告
        if (LoginManager.getInstance().isLogin()) getCarList();//获取洗车列表
    }

    /**
     * 获取广告
     */
    private void getAdList() {
        //获取广告信息
        final RequestBody requestBody = new ParamsBuilder<LoginParams>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetHomeGoods")
                .gson(new Gson())
//                .typeValue("string", "2016053018491859")
                .noParams()//
                .build();
        Call<BaseResp> call = HttpClients.getInstance().commodity(requestBody);
        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                if (response.body() != null && response.body().getCode() == 1) {//成功
                    ArrayList<Advert> adverts = new ArrayList<>();//保存解析的数据
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().getData());
                        JSONArray subJsonArray = jsonArray.getJSONArray(0);
                        for (int i = 0; i < subJsonArray.length(); i++) {
                            JSONObject obj = subJsonArray.getJSONObject(i);
                            adverts.add(new Advert(obj.getString("AdvTitle"), obj.getString("AdvPath"), obj.getString("Advcode"), obj.getString("AdvType")));

                        }
                        viewPager.setAdapter(new MPagerAdapter(adverts));
                        indicator.setViewPager(viewPager);


                        // 自动轮播条显示
                        if (handler == null) {
                            handler = new Handler() {
                                public void handleMessage(android.os.Message msg) {
                                    int currentItem = viewPager.getCurrentItem();

                                    if (currentItem < 4) {
                                        currentItem++;
                                    } else {
                                        currentItem = 0;
                                    }

                                    viewPager.setCurrentItem(currentItem);// 切换到下一个页面
                                    handler.sendEmptyMessageDelayed(0, 4000);// 继续延时3秒发消息,
                                    // 形成循环
                                };
                            };

                            handler.sendEmptyMessageDelayed(0, 4000);// 延时3秒后发消息
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        L.e("JSONException>>>");
                    }
                } else {//失败
                    System.out.println("无数据");
                }
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                System.out.println("ERROR:::" + t.getMessage());
            }
        });
    }

    /**
     * 获取用户的汽车列表
     */
    private void getCarList() {
        final RequestBody requestBody = new ParamsBuilder<ReservationParam>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("MyCarList")
                .gson(new Gson())
                .typeValue("string", LoginManager.getInstance().getLoginInfo().getMb_Code())
                .build();
        Call<BaseResp> call = HttpClients.getInstance().memberInfo(requestBody);
        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                if (response.body().getCode() == 1) {
                    try {
                        JSONArray jsonArr = new JSONArray(response.body().getData());
                        List<CarInfo> carInfos = new Gson().fromJson(jsonArr.get(0).toString(), new TypeToken<List<CarInfo>>() {}.getType());
                        for (CarInfo carInfo : carInfos) {
                            if (carInfo.getIsDefault() == 1) {
                                defaultCarInfo = carInfo;
                                break;
                            }
                        }

                        //没有读取
                        if (!readedDefault) getDefaultCarCombo();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    T.s(context, "获取车辆信息错误");
                }
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                L.e("getThirdLevelYear::" + t.getMessage());
            }
        });
    }

    /**
     * 获取默认车辆的套餐
     */
    private void getDefaultCarCombo() {
        if (defaultCarInfo != null) {
            readedDefault = true;//设置已经读取过默认套餐
            getData(defaultCarInfo.getI_CarDetail(), true);
        }
    }

    /**
     * Header pager adapter
     */
    class MPagerAdapter extends PagerAdapter {

        private ArrayList<Advert> adverts;

        public MPagerAdapter() {}

        public MPagerAdapter(ArrayList<Advert> mAdverts) {
            this.adverts = mAdverts;
        }

        @Override
        public int getCount() {
            return adverts == null ? 0 : adverts.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_single_img_layout, container, false);
            ImageView img = (ImageView) view.findViewById(R.id.single_item_img);
            String url = HttpClients.PIC_URL + adverts.get(position).getAdvPath();
            Picasso.with(context).load(url).into(img);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 9527) {//选择车辆返回
            if (data != null) {
                chooseCarInfo = data.getParcelableExtra("carInfo");
                if (chooseCarInfo != null) {//选择了有用的车辆信息
                    CarInfo carInfo = null;
                    for (CarComboMaintenance combo: comboLists) {//如果选择了已经存在的车辆则不进行数据访问
                        CarInfo tempCarInfo = combo.getCarInfo();
                        if (tempCarInfo.getI_CarDetail().equals(chooseCarInfo.getI_CarDetail())){
                            carInfo = tempCarInfo;
                            break;
                        }
                    }

                    if (carInfo == null)//选择则的车辆没有读取过套餐信息
                        getData(chooseCarInfo.getI_CarDetail(), false);
                    else
                        T.s(context, "已有该车的套餐信息");
                }
            }
        } else if (requestCode == 9528) {
            if (LoginManager.getInstance().isLogin()) {
                Intent intent = new Intent(context, MyCarActivity.class);
                intent.putExtra("where", MyCarActivity.WHERE_FRAGMENT_MAINTENANCE_HOME);
                animStartForResult(intent, 9527);
            } else {
                T.s(context, "登录失败");
            }
        }
    }

    /**
     * 上门保养套餐 Adapter
     */
    class ComboAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return comboLists == null ? 0 : comboLists.size();
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
            final CarComboMaintenance carComboMaintenance = comboLists.get(position);
            final List<CarCombo> comboList = carComboMaintenance.getCombos();
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_maintance_combo, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //bind data
//            holder.headerTxt.setText();
            holder.headerTxt.setText(carComboMaintenance.getName());
            CarCombo carCombo1 = comboList.get(0).getData().get(0);
            CarCombo carCombo2 = comboList.get(1).getData().get(0);
            CarCombo carCombo3 = comboList.get(2).getData().get(0);
            float price = Float.parseFloat(carCombo1.getN_HYJ()) + Float.parseFloat(carCombo2.getN_HYJ()) + Float.parseFloat(carCombo3.getN_HYJ());
            float oldPrice = Float.parseFloat(carCombo1.getN_FHYJ()) + Float.parseFloat(carCombo2.getN_FHYJ()) + Float.parseFloat(carCombo3.getN_FHYJ());

            holder.priceTxt.setText("会员价:￥" + price);
            holder.oldPriceTxt.setText("原价:￥" + oldPrice);

            Picasso.with(context).load(HttpClients.PIC_URL + carCombo1.getVC_Url()).placeholder(R.mipmap.ic_launcher).into(holder.contentOneImg);
            holder.contentOneTxt.setText("￥" + carCombo1.getPs_NAME());
            holder.priceOneTxt.setText("￥" + carCombo1.getN_HYJ());

            Picasso.with(context).load(HttpClients.PIC_URL + carCombo2.getVC_Url()).placeholder(R.mipmap.ic_launcher).into(holder.contentTwoImg);
            holder.contentTwoTxt.setText("￥" + carCombo2.getPs_NAME());
            holder.priceTwoTxt.setText("￥" + carCombo2.getN_HYJ());

            Picasso.with(context).load(HttpClients.PIC_URL + carCombo3.getVC_Url()).placeholder(R.mipmap.ic_launcher).into(holder.contentThreeImg);
            holder.contentThreeTxt.setText("￥" + carCombo3.getPs_NAME());
            holder.priceThreeTxt.setText("￥" + carCombo3.getN_HYJ());

            //默认车辆不显示删除套餐按钮
            if (carComboMaintenance.getIsDefault() == 1) {
                holder.delBtn.setVisibility(View.INVISIBLE);
            }else {
                holder.delBtn.setVisibility(View.VISIBLE);
            }

            //查看更多
            holder.chooseMoreTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MyCarActivity.class);
                    intent.putExtra("where", MyCarActivity.WHERE_FRAGMENT_MAINTENANCE_HOME);
                    animStartForResult(intent, 9527);
                }
            });

            //删除套餐
            holder.delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    comboLists.remove(carComboMaintenance);
                    comboAdapter.notifyDataSetChanged();

                    if (comboLists == null || comboLists.size() <= 0) {
                        listView.setVisibility(View.GONE);
                        imgTxtLayout.setVisibility(View.VISIBLE);
                    }
                }
            });

            //查看套餐
            holder.comboLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ComboActivity.class);
                    intent.putExtra("carInfo", carComboMaintenance.getCarInfo());
                    animStart(intent);
                }
            });

            //预约
            holder.bespeakBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ComboActivity.class);
                    intent.putExtra("carInfo", carComboMaintenance.getCarInfo());
                    intent.putExtra("bespeak", true);
                    animStart(intent);
                }
            });

            //return
            return convertView;
        }

        /**
         * holder
         */
        class ViewHolder {

            @Bind(R.id.item_maintance_combo_content_layout)
            LinearLayout comboLayout;

            @Bind(R.id.item_maintance_combo_header_txt)
            TextView headerTxt;
            @Bind(R.id.item_maintance_combo_choose_more_txt)
            TextView chooseMoreTxt;

            @Bind(R.id.item_maintance_combo_price_txt)
            TextView priceTxt;
            @Bind(R.id.item_maintance_combo_old_price_txt)
            TextView oldPriceTxt;

            @Bind(R.id.item_maintance_combo_content_1_img)
            ImageView contentOneImg;
            @Bind(R.id.item_maintance_combo_content_1_txt)
            TextView contentOneTxt;
            @Bind(R.id.item_maintance_combo_price_1_txt)
            TextView priceOneTxt;

            @Bind(R.id.item_maintance_combo_content_2_img)
            ImageView contentTwoImg;
            @Bind(R.id.item_maintance_combo_content_2_txt)
            TextView contentTwoTxt;
            @Bind(R.id.item_maintance_combo_price_2_txt)
            TextView priceTwoTxt;

            @Bind(R.id.item_maintance_combo_content_3_img)
            ImageView contentThreeImg;
            @Bind(R.id.item_maintance_combo_content_3_txt)
            TextView contentThreeTxt;
            @Bind(R.id.item_maintance_combo_price_3_txt)
            TextView priceThreeTxt;

            @Bind(R.id.item_maintance_combo_del_btn)
            Button delBtn;
            @Bind(R.id.item_maintance_combo_bespeak_btn)
            Button bespeakBtn;


            public ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
