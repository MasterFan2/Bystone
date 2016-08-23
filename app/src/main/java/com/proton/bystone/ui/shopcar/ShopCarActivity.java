package com.proton.bystone.ui.shopcar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.CarModel;
import com.proton.bystone.bean.Commodity;
import com.proton.bystone.bean.DataBean;
import com.proton.bystone.bean.LoginParams;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.utils.L;
import com.proton.bystone.utils.S;
import com.proton.bystone.utils.T;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;
import com.proton.library.utils.ActivityManager;
import com.proton.library.utils.DimenUtils;
import com.proton.library.widget.shadow.ShadowProperty;
import com.proton.library.widget.shadow.ShadowViewHelper;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 购物车
 */
@MTFActivityFeature(layout = R.layout.activity_shop_car)
public class ShopCarActivity extends MTFBaseActivity {

    @Bind(R.id.shop_car_list_view)
    ListView listView;
    private ShopCarAdapter adapter;

    @Bind(R.id.shopping_car_cbx)
    View allCkbView;

    @Bind(R.id.shopping_car_pay_btn)
    Button payBtn;

    @Bind(R.id.shopping_car_all_price_txt)
    TextView allPriceTxt;

    @Bind(R.id.shop_car_empty_view)
    View emptyView;

    @Bind(R.id.empty_shop_car_btn)
    Button emptyBtn;

    @Bind(R.id.shop_car_content_view)
    RelativeLayout contentLayout;

    @Bind(R.id.shop_car_bottom_layout)
    RelativeLayout bottomLayout;//底部

    private ArrayList<Commodity> allData = null;//购物车中的数据

    private boolean allChecked = false;

    DecimalFormat df = new DecimalFormat("0.00");

    @OnClick(R.id.empty_shop_car_btn)
    public void showGoods() {//随便逛逛
        animFinish();
    }

    @Override
    public void initialize(Bundle savedInstanceState) {

        addShadowToBottom();

        ActivityManager.getInstance().addActivity(this);
        getData();
    }

    /**
     * 添加底部阴影效果
     */
    private void addShadowToBottom() {
        ShadowProperty shadowProperty = new ShadowProperty()
                .setShadowColor(0x77000000)
                .setShadowRadius(DimenUtils.dip2px(context, 2));

        ShadowViewHelper.bindShadowHelper(shadowProperty, bottomLayout);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) bottomLayout.getLayoutParams();
        lp.leftMargin   = -shadowProperty.getShadowOffset();
        lp.rightMargin  = -shadowProperty.getShadowOffset();
        lp.bottomMargin = -shadowProperty.getShadowOffset();
        bottomLayout.setLayoutParams(lp);
    }

    @OnClick(R.id.shop_car_ckb_layout)
    public void onCheckedChanged(View view) {

        allChecked = !allChecked;

        if (allChecked) allCkbView.setBackgroundResource(R.mipmap.icon_checkbox_sel);
        else            allCkbView.setBackgroundResource(R.mipmap.icon_checkbox_nor);

        for (Commodity commodity : allData) {
            if (allChecked) {
                commodity.setChecked(true);
            } else {
                commodity.setChecked(false);
            }
        }
        calculateTotal();
        adapter.notifyDataSetChanged();
    }

    /**
     * 到订单确认
     * @param view
     */
    @OnClick(R.id.shopping_car_pay_btn)
    public void confirmOrder(View view) {
        if (allData == null ) return;
        ArrayList<Commodity> temps = new ArrayList<>();
        for (Commodity commodity: allData) {
            if (commodity.isChecked()) {
                temps.add(commodity);
            }
        }
        if (temps.size() <= 0){
            T.s(context, "请选择需要结算的商品!");
            return;
        }

        Intent intent = new Intent(context, OrderConfirmActivity.class);
        intent.putParcelableArrayListExtra("data", temps);
        animStart(intent);
    }

    /**
     * 计算总价
     */
    private void calculateTotal() {
        if (allData == null) return;
        BigDecimal totalBigDecimal = new BigDecimal(0);
        int checkedCount = 0;//选中的数量

        for (Commodity commodity : allData) {
            if (commodity.isChecked()) {//计算选中的价格
                totalBigDecimal = totalBigDecimal.add(new BigDecimal(commodity.getN_HYJ()).multiply(new BigDecimal(commodity.getCount())));
//                total += commodity.getPostagePrice() * commodity.getCount();
                checkedCount = checkedCount + 1;
            }
        }

        totalBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        allPriceTxt.setText("￥" + (df.format(totalBigDecimal.doubleValue())));
        payBtn.setText("结算("+ checkedCount +")");
    }

    /**
     * 加载数据
     */
    private void getData() {
        allData = MyShoppingCar.getShoppingCar().getCommodities();
        adapter = new ShopCarAdapter();
        listView.setAdapter(adapter);

        if (allData == null || allData.size() <= 0){
            contentLayout.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            contentLayout.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            calculateTotal();//计算价钱
        }
    }

    class ShopCarAdapter extends BaseAdapter {

//        ArrayList<Commodity> data;
//
//        public ShopCarAdapter(ArrayList<Commodity> commodityList){
//            this.data = commodityList;
//        }

        @Override
        public int getCount() {
            return allData == null ? 0 : allData.size();
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
            final Commodity commodity = allData.get(position);
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_shop_car, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            //bind data
            Picasso.with(context).load(HttpClients.PIC_URL + commodity.getVC_Url()).placeholder(R.mipmap.ic_launcher).into(holder.logoImg);
            holder.titleTxt.setText(commodity.getPs_Name());
            holder.headTxt.setText(commodity.getVC_Params());
            holder.descTxt.setText(TextUtils.isEmpty(commodity.getPadctBrief()) ? "保养必备" : commodity.getPadctBrief());
            holder.countTxt.setText("" + commodity.getCount());
            BigDecimal itemPrice = new BigDecimal(commodity.getN_HYJ()).setScale(2, BigDecimal.ROUND_HALF_UP);
            holder.priceTxt.setText("￥ " + itemPrice.toString());

            if (commodity.isChecked()) {
                holder.chkView.setBackgroundResource(R.mipmap.icon_checkbox_sel);
            }else {
                holder.chkView.setBackgroundResource(R.mipmap.icon_checkbox_nor);
            }

            ///选中 、 非选中
            holder.headerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean checked = commodity.isChecked();
                    if (checked) commodity.setChecked(false);
                    else         commodity.setChecked(true);

                    int checkCount = 0;//选中的商品数量
                    for (Commodity tempCommodity: allData) {
                        if (tempCommodity.isChecked()) {
                            checkCount ++;
                        }
                    }

                    if (checkCount == allData.size()) {//全选
                        allChecked = true;
                        allCkbView.setBackgroundResource(R.mipmap.icon_checkbox_sel);
                    } else {
                        allChecked = false;
                        allCkbView.setBackgroundResource(R.mipmap.icon_checkbox_nor);
                    }
                    calculateTotal();
                    adapter.notifyDataSetChanged();
                }
            });

            ///数量减
            holder.subtractImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (commodity.getCount() <= 1) return;
                    else {
                        commodity.subtract();
                        adapter.notifyDataSetChanged();
                        calculateTotal();
                    }
                }
            });

            ///数量加
            holder.plusImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commodity.increment();
                    adapter.notifyDataSetChanged();
                    calculateTotal();
                }
            });

            ///焦点
            holder.countTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        int count = 1;
                        String strCount = holder.countTxt.getText().toString();
                        try {
                            count = Integer.parseInt(strCount);
                        }catch (Exception e) {
                            S.o("" + e.getMessage());
                            count = 1;
                        }
                        commodity.setCount(count);
                        calculateTotal();
                    }
                }
            });

            //return
            return convertView;
        }

        class ViewHolder  {

            @Bind(R.id.item_shop_car_header_layout)
            LinearLayout headerLayout;

            @Bind(R.id.item_shop_car_check_view)
            View chkView;

            @Bind(R.id.item_shop_car_title_txt)
            TextView titleTxt;

            @Bind(R.id.item_shop_car_logo_img)
            ImageView logoImg;

            @Bind(R.id.item_shop_car_head_txt)
            TextView headTxt;

            @Bind(R.id.item_shop_car_desc_txt)
            TextView descTxt;

            @Bind(R.id.item_shop_car_price_txt)
            TextView priceTxt;

            @Bind(R.id.item_shop_car_count_edit)
            EditText countTxt;

            @Bind(R.id.item_shop_car_subtract_img)
            RelativeLayout subtractImg;

            @Bind(R.id.item_shop_car_plus_img)
            RelativeLayout plusImg;

            public ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
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
