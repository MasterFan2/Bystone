package com.proton.bystone.ui.maintenance;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.CarCombo;
import com.proton.bystone.bean.CarInfo;
import com.proton.bystone.bean.ComboKey;
import com.proton.bystone.bean.LoginParams;
import com.proton.bystone.bean.LoginResp;
import com.proton.bystone.bean.ReservationParams2;
import com.proton.bystone.cache.LoginManager;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.ui.login.LoginActivity;
import com.proton.bystone.utils.L;
import com.proton.bystone.utils.LoginUtil;
import com.proton.bystone.utils.T;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;
import com.proton.library.utils.ActivityManager;
import com.proton.library.widget.dialog.KProgressHUD;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 套餐界面
 */
@MTFActivityFeature(layout = R.layout.activity_combo)
public class ComboActivity extends MTFBaseActivity {

    @Bind(R.id.combo_hour_charge_txt)
    TextView hourChargeTxt;//工时费

    @Bind(R.id.combo_old_price_txt)
    TextView oldTxt;

    @Bind(R.id.combo_now_price_txt)
    TextView newTxt;

    @Bind(R.id.combo_expand_list_view)
    ExpandableListView expandableListView;
    private MyAdapter adapter;

    DecimalFormat df = new DecimalFormat("0.00");//数字 格式化

    private int hourCharge = 0;//工时费

    private CarInfo carInfo = null;

    ///保存组装后的数据  重复的添加到一个key中。
    ArrayList<ComboKey> keys = new ArrayList<>();
    HashMap<ComboKey, ArrayList<CarCombo>> maps = new HashMap<>();

    private boolean isBespeak = false; //是否是预约
    private KProgressHUD progressHUD;              //等待窗口

    @Override
    public void initialize(Bundle savedInstanceState) {
        ActivityManager.getInstance().addActivity(this);//预约成功时， 关闭Activity

        carInfo = getIntent().getParcelableExtra("carInfo");

        ///
        initDialog();

        isBespeak = getIntent().getBooleanExtra("bespeak", false);

        //
        if (isBespeak) progressHUD.show();

        ///
        adapter = new MyAdapter();
        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

        ///
        getData();
    }

    /**
     * init  dialog
     */
    private void initDialog() {
        progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("请稍后...")
                .setDimAmount(0.4f)
                .setCancellable(false);
    }

    /**
     * get car info
     */
    private void getData() {
        if (carInfo == null) {
            T.s(context, "无法获取车辆信息");
            return;
        }
        //获取车辆热门品牌
        final RequestBody requestBody = new ParamsBuilder<LoginParams>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetApplicableModelsGoods")
                .gson(new Gson())
                .typeValue("string", carInfo.getI_CarDetail())//2016053018491859临时写死
                .build();
        Call<BaseResp> call = HttpClients.getInstance().maintenance(requestBody);
        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                if (response.body() != null && !TextUtils.isEmpty(response.body().getData())) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().getData());
                        List<CarCombo> carCombos = new Gson().fromJson(jsonArray.get(0).toString(), new TypeToken<List<CarCombo>>() {
                        }.getType());

                        for (CarCombo combo : carCombos) {//生成Key.和value 值
                            ComboKey key = new ComboKey(combo.getType_CODE(), combo.getPs_NAME(), combo.getGongShiFei(), false);
                            keys.add(key);
                            ArrayList<CarCombo> values = combo.getData();

                            final int size = values.size();
                            for (int i = 0; i < size; i++) {
                                CarCombo value = values.get(i);
                                if (i == 0) value.setChecked(true);//设置默认第一个为选中状态

                                if (key.getType_CODE().equals("201607011037086328")) {//必选项
                                    value.setDisable(true);
                                    value.setChecked(true);
                                }

                                if (key.getType_CODE().equals("201605231700199538")) {//可选项
                                    value.setCanCanceled(true);
                                }
                            }
                            maps.put(key, values);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    for (int i = 0; i < keys.size(); i++) {//展开expandable list view
                        expandableListView.expandGroup(i);
                    }
                    adapter.notifyDataSetChanged();//更新数据
                    calculateHourCharge();         //计算工时费
                    hourChargeTxt.setText("工时费:￥" + hourCharge);
                    calculateTotalPrice();         //计算价钱

                    if (isBespeak) confirmClick(null);
                } else {
                    L.e("解析上门保养套餐>>>没有数据");
                }
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                L.e(t.getMessage());
            }
        });
    }

    /**
     * ExpandableListView adapter
     */
    class MyAdapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            return keys == null ? 0 : keys.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            ComboKey key = keys.get(groupPosition);
            return maps == null ? 0 : maps.get(key).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return null;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            final ParentHolder holder;
            final ComboKey comboKey = keys.get(groupPosition);
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_expand_parent, parent, false);
                holder = new ParentHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ParentHolder) convertView.getTag();
            }

            //
            holder.cbView.setVisibility(View.GONE);
            holder.tv.setText(comboKey.getPs_NAME());

            //
            return convertView;
        }

        @Override
        public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
            final ChildHolder holder;
            final ComboKey key = keys.get(groupPosition);
            final List<CarCombo> childList = maps.get(key);
            final CarCombo carCombo = childList.get(childPosition);
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_expand_child, parent, false);
                holder = new ChildHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ChildHolder) convertView.getTag();
            }

            //
            holder.nameTxt.setText(carCombo.getVC_Name());
            double d = Double.parseDouble(carCombo.getN_HYJ());

            holder.priceTxt.setText("￥" + df.format(d));
            holder.descTxt.setText(carCombo.getPadctBrief());
            Picasso.with(context).load(HttpClients.PIC_URL + carCombo.getVC_Url()).placeholder(R.mipmap.ic_launcher).into(holder.logoImg);

            try {
                holder.oldTxt.setText("￥" + df.format(df.parse(carCombo.getN_FHYJ()).doubleValue()));
            } catch (ParseException e) {
                holder.oldTxt.setText("￥" + carCombo.getN_FHYJ());
                e.printStackTrace();
            }
            holder.oldTxt.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线
            holder.oldTxt.getPaint().setAntiAlias(true);// 抗锯齿

            if (carCombo.isDisable()) {//禁用    没有选择效果。  必选项  不添加点击事件
                holder.rbView.setBackgroundResource(R.mipmap.icon_radio_blue_sel_disable);
            } else {

                if (carCombo.isChecked()) {//设置 选中/非选中状态
                    holder.rbView.setBackgroundResource(R.mipmap.icon_radio_blue_sel);
                } else {
                    holder.rbView.setBackgroundResource(R.mipmap.icon_radio_blue_nor);
                }

                holder.rootLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //可以 选择/取消选择
                        if (carCombo.isCanCanceled()) {
                            if (carCombo.isChecked()) {
                                carCombo.setChecked(false);
                            } else {
                                carCombo.setChecked(true);
                            }
                            calculateTotalPrice();
                        } else {//必需任选其一
                            //选中
                            if (!carCombo.isChecked()) {
                                int count = 0;
                                final int size = childList.size();
                                for (int i = 0; i < size; i++) {//除了当前点击的item其它 的设置为非选中状态
                                    childList.get(i).setChecked(false);
                                }

                                carCombo.setChecked(true);
                            }
                            calculateTotalPrice();
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }
            //
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

        class ParentHolder {

            @Bind(R.id.item_test_list_expand_parent_root)
            LinearLayout rootLayout;

            @Bind(R.id.item_test_list_expand_parent_view)
            View cbView;

            @Bind(R.id.item_test_list_expand_name_parent_txt)
            TextView tv;

            public ParentHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

        class ChildHolder {

            @Bind(R.id.item_test_list_expand_child_root)
            LinearLayout rootLayout;

            @Bind(R.id.item_test_list_expand_child_view)
            View rbView;

            @Bind(R.id.item_test_list_expand_child_name_txt)
            TextView nameTxt;

            @Bind(R.id.item_test_list_expand_child_price_txt)
            TextView priceTxt;

            @Bind(R.id.item_test_list_expand_child_old_price_txt)
            TextView oldTxt;

            @Bind(R.id.item_test_list_expand_child_logo_img)
            ImageView logoImg;

            @Bind(R.id.item_test_list_expand_child_desc_txt)
            TextView descTxt;

            public ChildHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    /**
     * 计算工时费
     */
    private void calculateHourCharge() {
        if (keys != null && keys.size() > 0) {
            for (ComboKey key : keys) {
                hourCharge += key.getGongShiFei();
            }
        }
    }

    /**
     * 计算价钱
     */
    private void calculateTotalPrice() {
        double totalOld = 0d;
        double totalNew = 0d;

        for (Map.Entry<ComboKey, ArrayList<CarCombo>> entry : maps.entrySet()) {
            ArrayList<CarCombo> vList = entry.getValue();
            for (CarCombo combo : vList) {
                if (combo.isChecked()) {
                    try {
                        Number numOld = df.parse(combo.getN_FHYJ());
                        Number numNew = df.parse(combo.getN_HYJ());

                        totalOld = totalOld + numOld.doubleValue();
                        totalNew = totalNew + numNew.doubleValue();


                    } catch (ParseException e) {
                        e.printStackTrace();
                        L.e("ComboActivity>>计算价钱错误");
                    }
                }

            }
        }

        totalOld += hourCharge;
        totalNew += hourCharge;

        oldTxt.setText("原价：￥" + totalOld);
        newTxt.setText("现价：￥" + totalNew);
    }

    /**
     * 跳转预约
     *
     * @param v
     */
    @OnClick(R.id.combo_confirm_btn)
    public void confirmClick(View v) {

        ArrayList<ReservationParams2> params2List = new ArrayList<>();

        //维保提交参数2
        for (Map.Entry<ComboKey, ArrayList<CarCombo>> entry : maps.entrySet()) {
            ArrayList<CarCombo> vList = entry.getValue();
            for (CarCombo combo : vList) {
                if (combo.isChecked()) {
                    ReservationParams2 params2 = new ReservationParams2(LoginManager.getInstance().getLoginInfo().getMb_Code(), combo.getType_CODE(), combo.getPt_Code());
                    params2List.add(params2);
                }
            }
        }

        if (progressHUD.isShowing()) progressHUD.dismiss();

        Intent intent = new Intent(context, BespeakActivity.class);
        intent.putExtra("carInfo", carInfo);
        if (isBespeak) intent.putExtra("bespeak", true);
        intent.putParcelableArrayListExtra("params2", params2List);
        animStart(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 9527 && resultCode == RESULT_OK) {
            if (LoginUtil.checkLogin(context)) {
                animStart(BespeakActivity.class);
            } else {
                T.s(context, "请登录");
            }
        }
    }

    @Override
    public void backPressed() {
        ActivityManager.getInstance().delActivity(this);//预约成功时， 关闭Activity
        animFinish();
    }

    @OnClick(R.id.m_title_left_btn)
    public void back(View view) {
        ActivityManager.getInstance().delActivity(this);//预约成功时， 关闭Activity
        animFinish();
    }
}
