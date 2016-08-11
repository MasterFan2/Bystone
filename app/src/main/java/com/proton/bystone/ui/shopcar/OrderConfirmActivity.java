package com.proton.bystone.ui.shopcar;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proton.bystone.R;
import com.proton.bystone.bean.AddressMTF;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.Commodity;
import com.proton.bystone.bean.GoldCoin;
import com.proton.bystone.bean.OrderSubmitMajorParams;
import com.proton.bystone.bean.OrderSubmitMinorParams;
import com.proton.bystone.bean.OrderSubmitResp;
import com.proton.bystone.bean.ReservationParam;
import com.proton.bystone.cache.LoginManager;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.ui.shop.My_Shop_Pay;
import com.proton.bystone.ui.shop.Shop_Select_Address;
import com.proton.bystone.utils.L;
import com.proton.bystone.utils.T;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;
import com.proton.library.utils.ActivityManager;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 订单确认
 * create by masterFan on 2016.08.10
 */
@MTFActivityFeature(layout = R.layout.activity_order_confirm)
public class OrderConfirmActivity extends MTFBaseActivity {

    private ArrayList<Commodity> datas;     //列表显示数据
    private int currentSelectPosition = -1; //添加备注信息记录的位置，
    private GoldCoin goldCoin   = null;     //金币String格式信息

    private int iGoldCoinAll    = 0;//总共的金币数,,,10个金币当1元人民币用
    private int iCanUseGoldCoin = 0;//可用的金币整数  [10的倍数]
    private int iYuan           = 0;//金币对应的人民币
    private int surplusGoldCoin = 0;//记录已经使用的金币
    private HashMap<Commodity, String> goldCoinMaps = new HashMap<>();//记录使用金币的item项,取消使用金币的时候把对应的position项value设为null.

    @Bind(R.id.order_confirm_list_view)
    ListView listView;
    private OrderConfirmAdapter adapter;
    private AddressMTF address;

    private View headerView;            //头布局
    private LinearLayout headerRightLayout;
    private TextView headerNameTxt;     //收货人
    private TextView headerPhoneTxt;    //收货人联系电话
    private TextView headerAddrTxt;     //收货人地址
    private TextView headerGoldCoinTxt; //金币数

    @Bind(R.id.order_confirm_all_price_txt)
    TextView totalPriceTxt;

    @Bind(R.id.order_confirm_submit_btn)
    Button submitBtn;

    private AlertDialog inputDialog;
    private EditText inputEdit;

    @Override
    public void initialize(Bundle savedInstanceState) {

        ActivityManager.getInstance().addActivity(this);

        datas = getIntent().getParcelableArrayListExtra("data");

        initListheaderView();

        adapter = new OrderConfirmAdapter();
        listView.setAdapter(adapter);
        listView.addHeaderView(headerView);

        initDialog();
        getGoldCoin();
        getDefaultAddress();
    }

    /**
     * 计算总价
     */
    private void calculateTotalPrice() {
        if (datas == null || datas.size() <= 0) return;
        if (iGoldCoinAll >= 10) {//
            for (Commodity temp : datas) {
                temp.setCanUseGoldCoin(true);   //设置是否能使用金币
                temp.setUsedGoldCoin(-1);       //设置使用了多少金币 -1 默认值
                //
                BigDecimal tempDecimal = new BigDecimal(temp.getN_HYJ()).multiply(new BigDecimal(temp.getCount()));
                temp.setNeedGoldCoin(tempDecimal.intValue() * 10);//设置需要花费的金币
            }
        }

        adapter.notifyDataSetChanged();
    }

    /**
     * 初始化listView头
     */
    private void initListheaderView() {
        headerView        = LayoutInflater.from(context).inflate(R.layout.header_order_confirm, null);
        headerRightLayout = (LinearLayout) headerView.findViewById(R.id.header_order_confirm_right_layout);
        headerAddrTxt     = (TextView) headerView.findViewById(R.id.header_order_confirm_addr_txt);
        headerNameTxt     = (TextView) headerView.findViewById(R.id.header_order_confirm_name_txt);
        headerPhoneTxt    = (TextView) headerView.findViewById(R.id.header_order_confirm_phone_txt);
        headerGoldCoinTxt = (TextView) headerView.findViewById(R.id.header_order_confirm_goldcoin_txt);
    }

    /**
     * 获取默认收货地址
     */
    private void getDefaultAddress() {
        final RequestBody requestBody = new ParamsBuilder<ReservationParam>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetShippingAddress")
                .gson(new Gson())
                .typeValue("string", LoginManager.getInstance().getLoginInfo().getMb_Code())
                .typeValue("int", 1)
                .build();
        Call<BaseResp> call = HttpClients.getInstance().orderInfo(requestBody);
        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                if (response.body().getCode() == 1) {
                    List<AddressMTF> addressList = new Gson().fromJson(response.body().getData(), new TypeToken<List<AddressMTF>>() {}.getType());
                    if (addressList != null && addressList.size() > 0) {
                        address = addressList.get(0);
                        if (address != null) {
                            headerAddrTxt.setText("收货地址：" + address.getAddressDetaile());
                            headerNameTxt.setText("收货人：" + address.getAd_Name());
                            headerPhoneTxt.setText(address.getAd_ContactNumber());
                        } else {
                            headerRightLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    animStart(Shop_Select_Address.class);
                                }
                            });
                        }
                    }
                } else {
                    T.s(context, "无法获取地址");
                }
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                L.e("getDefaultAddress::" + t.getMessage());
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
                    List<GoldCoin> goldCoins = new Gson().fromJson(response.body().getData(), new TypeToken<List<GoldCoin>>() {}.getType());
                    if (goldCoins != null && goldCoins.size() > 0) {
                        goldCoin = goldCoins.get(0);
                        if (goldCoin != null) {
                            iGoldCoinAll = (int) Float.parseFloat(goldCoin.getCode());
                            iCanUseGoldCoin = iGoldCoinAll / 10 * 10;
                            surplusGoldCoin = iCanUseGoldCoin;
                            headerGoldCoinTxt.setText("你共有" + iGoldCoinAll + "金币");
                            calculateTotalPrice();
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
     * 初始化输入备注Dialog
     */
    private void initDialog() {
        //custom_dialog_input_edit
        View view = LayoutInflater.from(context).inflate(R.layout.custom_dialog_input_layout, null);
        inputEdit = (EditText) view.findViewById(R.id.custom_dialog_input_edit);

        inputDialog = new AlertDialog.Builder(context)
                .setTitle("输入备注信息")
                .setCancelable(false)
                .setView(view)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (currentSelectPosition != -1) {
                            datas.get(currentSelectPosition).setRemark(inputEdit.getText().toString());
                            inputEdit.setText("");
                            adapter.notifyDataSetChanged();
                        }
                    }
                })
                .create();
    }

    /**
     *
     */
    class OrderConfirmAdapter extends BaseAdapter {

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
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Commodity commodity = datas.get(position);

            //总价 = 会员价 * 数量 + 邮费
            //会员价
            final BigDecimal decimalMemberPrice = new BigDecimal(commodity.getN_HYJ());
            final BigDecimal totalDecimal = decimalMemberPrice.multiply(new BigDecimal(commodity.getCount())).add(new BigDecimal(commodity.getPostagePrice())).setScale(2, BigDecimal.ROUND_HALF_UP);

            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_order_confirm, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //bind data
            Picasso.with(context).load(HttpClients.PIC_URL + commodity.getVC_Url()).placeholder(R.mipmap.ic_launcher).into(holder.logoImg);
            holder.titleTxt.setText(commodity.getVC_Params());
            holder.descTxt.setText(commodity.getPadctBrief());
            holder.singleCountTxt.setText("X " + commodity.getCount());
            BigDecimal temp = new BigDecimal(commodity.getN_HYJ()).setScale(2, BigDecimal.ROUND_HALF_UP);
            holder.singlePriceTxt.setText("￥ " + temp.doubleValue());

            if (commodity.getPostagePrice() > 0) {
                holder.postFeeTxt.setText("邮费 ￥" + commodity.getPostagePrice());
            } else {
                holder.postFeeTxt.setText("免邮");
            }

            if (iGoldCoinAll > 10) {
                if (commodity.getUsedGoldCoin() == -1) {//默认展示全部
                    holder.switchCompap.setEnabled(true);
                    int tempNeed = iCanUseGoldCoin >= commodity.getNeedGoldCoin() ? commodity.getNeedGoldCoin() : iCanUseGoldCoin;
                    holder.switchTxt.setText("可用" + tempNeed + "金币抵扣" + ((tempNeed) / 10) + "元");
                } else if (commodity.getUsedGoldCoin() > 10) {
                    holder.switchCompap.setEnabled(true);
                    holder.switchTxt.setText("可用" + (commodity.getUsedGoldCoin()) + "金币抵扣" + ((commodity.getUsedGoldCoin()) / 10) + "元");
                }else if (surplusGoldCoin > 10) {
                    holder.switchCompap.setEnabled(true);
                    int tempNeed = surplusGoldCoin >= commodity.getNeedGoldCoin() ? commodity.getNeedGoldCoin() : surplusGoldCoin;
                    holder.switchTxt.setText("可用" + (tempNeed) + "金币抵扣" + ((tempNeed) / 10) + "元");
                } else {
                    holder.switchTxt.setText("暂无金币可用");
                    holder.switchCompap.setEnabled(false);
                }
            } else {
                holder.switchTxt.setText("暂无金币可用");
                holder.switchCompap.setEnabled(false);
            }

            holder.countTxt.setText("共计 " + commodity.getCount() + " 件商品 小计：");
            holder.priceTxt.setText("￥ " + totalDecimal.floatValue());

            if (!TextUtils.isEmpty(commodity.getRemark())) {
                holder.remarkTxt.setText(commodity.getRemark());
            } else {
                holder.remarkTxt.setText("输入备注信息");
            }

            ///switchCompap 禁用, 启用
            if (commodity.isCanUseGoldCoin()) {
                holder.switchCompap.setEnabled(true);
            } else {
                holder.switchCompap.setEnabled(false);
            }

            ///添加备注信息
            holder.remarkTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentSelectPosition = position;
                    inputDialog.show();
                }
            });

            ///开启,/关闭   使用金币
            holder.switchCompap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        //0.0.总金币 <= item的金币数，只有当前可用。 其它不可用, 只可使用一次
                        if ((iCanUseGoldCoin) <= commodity.getNeedGoldCoin()) {//金币小于或等于当前小订单的价钱,
                            surplusGoldCoin = 0;     //证明金币正好用完
                            commodity.setCanUseGoldCoin(true);  //设置当前switch可使用
                            commodity.setUsedGoldCoin(iCanUseGoldCoin);
                            goldCoinMaps.put(commodity, commodity.getVC_Name());
                            for (Commodity temp : datas) {
                                if (commodity.equals(temp)) continue;
                                temp.setCanUseGoldCoin(false);  //设置其它的switch不可用
                                temp.setUsedGoldCoin(0);
                            }
                            notifyDataSetChanged();//
                        } else {

                            //剩余金币小于等于需要的金币,其它没有设置
                            if (surplusGoldCoin <= commodity.getNeedGoldCoin() && surplusGoldCoin >= 10) {
                                commodity.setUsedGoldCoin(surplusGoldCoin);
                                surplusGoldCoin = 0;
                            } else {
                                surplusGoldCoin = surplusGoldCoin - commodity.getNeedGoldCoin();
                                commodity.setUsedGoldCoin(commodity.getNeedGoldCoin());
                            }
                            goldCoinMaps.put(commodity, commodity.getVC_Name());

                            for (Commodity t : datas) {
                                if (TextUtils.isEmpty(goldCoinMaps.get(t))) {
                                    t.setUsedGoldCoin(0);
                                }
                            }

                            notifyDataSetChanged();//
                            //1.1.
                            //1.2.剩于金币大于10， 所有item可开、
                        }

                    } else {//关闭金币兑换

                        notifyDataSetChanged();//更新UI
                    }
                }
            });

            //return
            return convertView;
        }

        class ViewHolder {

            @Bind(R.id.item_order_confirm_logo_img)
            ImageView logoImg;

            @Bind(R.id.item_order_confirm_name_txt)
            TextView titleTxt;

            @Bind(R.id.item_order_confirm_desc_txt)
            TextView descTxt;

            @Bind(R.id.item_order_confirm_single_price_txt)
            TextView singlePriceTxt;

            @Bind(R.id.item_order_confirm_single_count_txt)
            TextView singleCountTxt;

            @Bind(R.id.item_order_confirm_postfee_txt)
            TextView postFeeTxt;

            @Bind(R.id.item_order_confirm_remark_txt)
            TextView remarkTxt;

            @Bind(R.id.item_order_confirm_switch_txt)
            TextView switchTxt;

            @Bind(R.id.item_order_confirm_switch_img)
            SwitchCompat switchCompap;

            @Bind(R.id.item_order_confirm_count_txt)
            TextView countTxt;

            @Bind(R.id.item_order_confirm_price_txt)
            TextView priceTxt;

            public ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    /**
     * 提交订单
     * @param view
     */
    @OnClick(R.id.order_confirm_submit_btn)
    public void generateOrder(View view) {

        BigDecimal oldDecimal = new BigDecimal("0.00");//原价
        BigDecimal newDecimal = new BigDecimal("0.00");//会员价

        for (Commodity temp : datas) {
            oldDecimal.add(new BigDecimal(temp.getN_FHYJ()));
            newDecimal.add(new BigDecimal(temp.getN_HYJ()));
        }

        //主订单
        OrderSubmitMajorParams majorParams = new OrderSubmitMajorParams("", LoginManager.getInstance().getLoginInfo().getMb_Code(),
                oldDecimal.toString(), newDecimal.toString(), "0", newDecimal.toString(), "4", "");
        ArrayList<OrderSubmitMinorParams> minorsList = new ArrayList<>();
        OrderSubmitMinorParams tempMinors = null;

        for (Commodity commodity : datas) {
            tempMinors = new OrderSubmitMinorParams(commodity.getI_Company(), LoginManager.getInstance().getLoginInfo().getMb_Code()
            , commodity.getVC_Code(), commodity.getN_FHYJ(), commodity.getPacking(), commodity.getVC_Name(), new Gson().toJson(address),
                    new BigDecimal(commodity.getN_HYJ()).multiply(new BigDecimal(commodity.getCount())).toString(), "0", commodity.getCount()+"",
                    commodity.getN_HYJ(), "1", "023-48901105", "3", commodity.getRemark());
            minorsList.add(tempMinors);
        }

        //
        submit(majorParams, minorsList);
    }

    /**
     * 提交订单
     * @param majorParams 主订单
     * @param minorsList  子订单列表
     */
    private void submit(OrderSubmitMajorParams majorParams, ArrayList<OrderSubmitMinorParams> minorsList) {
        final RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("SubmitOrder")
                .gson(new Gson())
                .object(majorParams)
                .object(minorsList)
                .build();
        Call<BaseResp> call = HttpClients.getInstance().orderInfo(requestBody);
        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                if (response.body().getCode() == 1) {
                    OrderSubmitResp orderSubmitResp = new Gson().fromJson(response.body().getData(), new TypeToken<OrderSubmitResp>() {}.getType());
                    Intent intent = new Intent(context, My_Shop_Pay.class);
                    intent.putExtra("orderNum", orderSubmitResp.getOrderCode());
                    animStart(intent);

                    ActivityManager.getInstance().finishAllActivity();
                } else {
                    T.s(context, "submit order failure!!!");
                }
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                L.e("submit:order::" + t.getMessage());
            }
        });
    }

    @Override
    public void backPressed() {
        ActivityManager.getInstance().delActivity(this);
        animFinish();
    }

    @OnClick(R.id.m_title_left_btn)
    public void back(View view) {
        ActivityManager.getInstance().delActivity(this);
        animFinish();
    }
}
