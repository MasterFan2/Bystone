package com.proton.bystone.ui.maintenance;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.GoldCoin;
import com.proton.bystone.bean.OrderBaseInfo;
import com.proton.bystone.bean.ReservationParam;
import com.proton.bystone.bean.ServiceDetail;
import com.proton.bystone.cache.LoginManager;
import com.proton.bystone.config.Config;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.utils.L;
import com.proton.bystone.utils.T;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;
import com.proton.library.widget.MyListView;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
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
 * 服务详情
 */
@MTFActivityFeature(layout = R.layout.activity_service_detail)
public class ServiceDetailActivity extends MTFBaseActivity {

    ///widget
    @Bind(R.id.service_detail_maint_list_view)
    MyListView maintListView;//维保检测项
    private MaintAdapter maintAdapter;
    private List<ServiceDetail> maintServiceDetailLists;

    @Bind(R.id.service_detail_add_list_view)
    MyListView addListView;//新增项
    private AddAdapter addAdapter;
    private List<ServiceDetail> addServiceDetailLists;

    @Bind(R.id.service_detail_pay_layout)
    RelativeLayout payLayout;//付款布局

    @Bind(R.id.service_detail_comment_btn)
    Button commentBtn;//评论

    @Bind(R.id.service_detail_switch)
    SwitchCompat switchCompat;//switch widget

    @Bind(R.id.service_detail_date_txt)
    TextView dateTxt;//日期

    @Bind(R.id.service_detail_order_no_txt)
    TextView orderIdTxt;//订单号

    @Bind(R.id.service_detail_maint_total_price_txt)
    TextView maintTotalTxt;//保养小计

    @Bind(R.id.service_detail_add_total_txt)
    TextView addTotalTxt;//新增小计

    @Bind(R.id.service_detail_goldcoin_exchange_txt)
    TextView exchangeTxt;//金币兑换钱文字

    @Bind(R.id.service_detail_before_total_price_txt)
    TextView beforeTotalPriceTxt;//总金额

    @Bind(R.id.service_detail_total_goldcoin_txt)
    TextView useGoldcoinPriceTxt;//使用金币兑换的钱

    @Bind(R.id.service_detail_call_service_number_txt)
    TextView callCustomPhoneTxt;//客服电话

    @Bind(R.id.service_detail_total_price_txt)
    TextView allTotalPriceTxt;//总金额

    @Bind(R.id.service_detail_activity_name_txt)
    TextView activityNameTxt;//活动名称

    @Bind(R.id.service_detail_activity_price_txt)
    TextView acitivtyPriceTxt;//活动金额

    private String code;//maintenance order no.
    private OrderBaseInfo orderInfo = null;//订单信息

    ///计算的价钱
    private BigDecimal addTotalBigDecimal    = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);//新增项小计
    private BigDecimal maintTotalBigDecimal  = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);//维保小计
    private BigDecimal beforTotalBigDecimeal = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);//总金额

    ///活动
    private String activityName = null;//活动名称
    private int activityPrice   = 0;   //活动价钱

    ///金币
    private GoldCoin goldCoin   = null;
    private int iGoldCoin       = 0;//总共金币数
    private int iCanUseGoldCoin = 0;//可用金币数

    /**
     * Enter
     *
     * @param savedInstanceState
     */
    @Override
    public void initialize(Bundle savedInstanceState) {
        init();       //初始化listView、Adapter和对应的数据
        getGoldCoin();//获取金币
        getDate(code);//获取详情数据
    }

    /**
     * init listView & adapter & data
     */
    private void init() {
        code      = getIntent().getStringExtra("code");
        orderInfo = getIntent().getParcelableExtra("status");

        try{
            String order = orderInfo.getBookBeginTime().split("至")[0].split(" ")[0];
            dateTxt.setText(order);
        }catch (Exception e) {
            dateTxt.setText(orderInfo.getBookBeginTime());
        }

        orderIdTxt.setText("（订单ID"+orderInfo.getBookCode()+")");

        if (orderInfo.getBookStaus() < 10) {//未付款
            payLayout.setVisibility(View.VISIBLE);
            commentBtn.setVisibility(View.GONE);
        }else {//已付款
            payLayout.setVisibility(View.GONE);
            commentBtn.setVisibility(View.VISIBLE);
        }

        maintAdapter = new MaintAdapter();
        maintListView.setAdapter(maintAdapter);
        maintServiceDetailLists = new ArrayList<>();

        addAdapter = new AddAdapter();
        addListView.setAdapter(addAdapter);
        addServiceDetailLists = new ArrayList<>();

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    useGoldcoinPriceTxt.setText("-￥" + (iCanUseGoldCoin / 10));

                    allTotalPriceTxt.setText("");
                } else {
                    useGoldcoinPriceTxt.setText("-￥0.00");
                }
            }
        });
    }

    /**
     * get service detail data
     */
    private void getDate(String code) {

        if (TextUtils.isEmpty(code)) return;

        final RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetMaintenanceServicesDetail")
                .gson(new Gson())
                .typeValue("string", "201608091423285485")
                .build();
        Call<BaseResp> call = HttpClients.getInstance().maintenance(requestBody);
        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                if (response.body().getCode() == 1 && !TextUtils.isEmpty(response.body().getData())) {
                    List<ServiceDetail> serviceDetails = new Gson().fromJson(response.body().getData(), new TypeToken<List<ServiceDetail>>() {}.getType());
                    for (ServiceDetail serviceDetail : serviceDetails) {
                        if (serviceDetail.getMaterialType() == 1) {
                            maintServiceDetailLists.add(serviceDetail);
                        } else if (serviceDetail.getMaterialType() == 2) {
                            addServiceDetailLists.add(serviceDetail);
                        }
                    }

                    //refresh adapter
                    calculateMaintTotalPrice();//计算维保小计
                    maintAdapter.notifyDataSetChanged();

                    calculateAddTotalPrice();//计算新增小计
                    addAdapter.notifyDataSetChanged();

                    calculateBeforeAllPrice();//计算总金额
                    calculateAllTotalPrice(0);//计算最终 价钱
                } else {
                    T.s(context, "查询服务详情信息失败");
                }
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                L.e("GetMaintenanceServicesDetail::" + t.getMessage());
            }
        });
    }

    /**
     * 获取金币
     */
    private void getGoldCoin() {
        final RequestBody requestBody = new ParamsBuilder<ReservationParam>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetGoldBalance")
                .gson(new Gson())
                .typeValue("string", LoginManager.getInstance().getLoginInfo().getMb_Code())
                .build();
        Call<BaseResp> call = HttpClients.getInstance().memberInfo(requestBody);
        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                if (response.body().getCode() == 1) {
                    List<GoldCoin> goldCoins = new Gson().fromJson(response.body().getData(), new TypeToken<List<GoldCoin>>() {
                    }.getType());
                    if (goldCoins != null && goldCoins.size() > 0) {
                        goldCoin = goldCoins.get(0);
                        if (goldCoin != null) {
                            iGoldCoin = (int) Float.parseFloat(goldCoin.getCode());
                            if (iGoldCoin > 10) {
                                iCanUseGoldCoin = iGoldCoin / 10 * 10;//转换为整10的金币数
                                exchangeTxt.setText("可用" + iCanUseGoldCoin + "金币抵扣" + (iCanUseGoldCoin / 10) + "元");
                                switchCompat.setEnabled(true);
                            } else {
                                exchangeTxt.setText("金币太少, 无法使用金币抵扣");
                                switchCompat.setEnabled(false);
                            }
                        }
                    }

                } else {
                    T.s(context, "无法获取金币");
                }
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                L.e("getGoldCoin::" + t.getMessage());
            }
        });
    }

    /**
     * 计算保养项目总价
     */
    private void calculateMaintTotalPrice() {
        for (ServiceDetail temp : maintServiceDetailLists) {
            maintTotalBigDecimal = maintTotalBigDecimal.add(new BigDecimal(temp.getHourlyWage())).add(new BigDecimal(temp.getMaterialPrice()));
        }
        maintTotalTxt.setText("保养小计:\t\t" + maintTotalBigDecimal.toString());
    }

    /**
     * 计算新增项目总价
     */
    private void calculateAddTotalPrice() {
        for (ServiceDetail temp : addServiceDetailLists) {
            addTotalBigDecimal = addTotalBigDecimal.add(new BigDecimal(temp.getHourlyWage())).add(new BigDecimal(temp.getMaterialPrice()));
        }
        addTotalTxt.setText("新增小计：" + addTotalBigDecimal.toString());
    }

    /**
     * 计算最终价钱
     * @param useGoldCoin 使用的金币
     */
    private void calculateAllTotalPrice(int useGoldCoin) {
        //合计（总价钱） = 优惠之前价钱 - 金币抵扣 - 优惠活动
        BigDecimal allTotalBigDecimal = beforTotalBigDecimeal.subtract(new BigDecimal(activityPrice)).subtract(new BigDecimal(useGoldCoin));
        allTotalPriceTxt.setText("￥" + allTotalBigDecimal.toString());
    }

    /**
     * 计算总价
     */
    private void calculateBeforeAllPrice() {
        //总金额 = 维保小计 + 新增小计
        beforTotalBigDecimeal = maintTotalBigDecimal.add(addTotalBigDecimal);
        beforeTotalPriceTxt.setText("￥" + beforTotalBigDecimeal.toString());//设置总金额
        if (maintServiceDetailLists != null && maintServiceDetailLists.size() > 0) {
            ServiceDetail serviceDetail = maintServiceDetailLists.get(0);
            if (!TextUtils.isEmpty(serviceDetail.getEventTitle())) {//设置优惠活动名称和价钱
                activityName = serviceDetail.getEventTitle();
                activityNameTxt.setText(activityName);
                acitivtyPriceTxt.setText("-￥" + serviceDetail.getActivityPrice());
            }
        }
    }

    /**
     * maintenance adapter
     */
    class MaintAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return maintServiceDetailLists == null ? 0 : maintServiceDetailLists.size();
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
            final ServiceDetail serviceDetail = maintServiceDetailLists.get(position);
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_service_detail_maint, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //bind data
            Picasso.with(context).load(HttpClients.PIC_URL + serviceDetail.getVC_Url()).placeholder(R.mipmap.ic_launcher).into(viewHolder.logoImg);
            viewHolder.nameTxt.setText(serviceDetail.getPs_NAME());
            viewHolder.idTxt.setText(serviceDetail.getPt_Barcode());
            viewHolder.detailTxt.setText(serviceDetail.getVC_Name());
            viewHolder.descTxt.setText(serviceDetail.getPadctBrief());
            viewHolder.maintPriceTxt.setText("￥" + serviceDetail.getMaterialPrice());
            viewHolder.hourlyWageTxt.setText("工时费\t\t￥" + new BigDecimal(serviceDetail.getHourlyWage()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            //
            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.item_service_maint_name_txt)
            TextView nameTxt;

            @Bind(R.id.item_service_maint_id_txt)
            TextView idTxt;

            @Bind(R.id.item_service_maint_bottom_layout)
            LinearLayout bottomLayout;

            @Bind(R.id.item_service_maint_detail_txt)
            TextView detailTxt;

            @Bind(R.id.item_service_maint_desc_txt)
            TextView descTxt;

            @Bind(R.id.item_service_maint_price_txt)
            TextView maintPriceTxt;

            @Bind(R.id.item_service_maint_hourly_wage_txt)
            TextView hourlyWageTxt;

            @Bind(R.id.item_service_maint_logo_img)
            ImageView logoImg;

            public ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    /**
     * add adapter
     */
    class AddAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return addServiceDetailLists == null ? 0 : addServiceDetailLists.size();
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
            final ServiceDetail serviceDetail = addServiceDetailLists.get(position);
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_service_detail_add, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //bind data
            viewHolder.nameTxt.setText(serviceDetail.getVC_Name());
            viewHolder.idTxt.setText(serviceDetail.getPt_Barcode());
            viewHolder.subNameTxt.setText(serviceDetail.getVC_Name());
            viewHolder.priceTxt.setText("￥" + serviceDetail.getMaterialPrice());
            viewHolder.hourlyWageTxt.setText("￥" + new BigDecimal(serviceDetail.getHourlyWage()).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            BigDecimal bigDecimal = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
            bigDecimal = bigDecimal.add(new BigDecimal(serviceDetail.getMaterialPrice())).add(new BigDecimal(serviceDetail.getHourlyWage()));
            viewHolder.totalPriceTxt.setText("小计：\t\t￥" + bigDecimal.toString());
            //
            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.item_service_add_name_txt)
            TextView nameTxt;

            @Bind(R.id.item_service_add_id_txt)
            TextView idTxt;

            @Bind(R.id.item_service_add_sub_name_txt)
            TextView subNameTxt;

            @Bind(R.id.item_service_add_price_txt)
            TextView priceTxt;

            @Bind(R.id.item_service_add_hourlywage_txt)
            TextView hourlyWageTxt;

            @Bind(R.id.item_service_add_total_price_txt)
            TextView totalPriceTxt;

            public ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    /**
     * call customer phone number
     *
     * @param view
     */
    @OnClick(R.id.service_detail_call_service_number_txt)
    public void callCustomer(View view) {
        new AlertDialog.Builder(context).setTitle("提示您")
                .setMessage("是否拨打客服电话 " + Config.CUSTOME_PHONE_NUM + "?")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent();//拨打电话
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + Config.CUSTOME_PHONE_NUM));
                        startActivity(intent);
                    }
                })
                .create()
                .show();
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
