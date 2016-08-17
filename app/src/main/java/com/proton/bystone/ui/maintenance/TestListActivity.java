package com.proton.bystone.ui.maintenance;

import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.CarCombo;
import com.proton.bystone.bean.ComboKey;
import com.proton.bystone.bean.Discount;
import com.proton.bystone.bean.SuggestChange;
import com.proton.bystone.bean.TestChildBean;
import com.proton.bystone.bean.TestListParams;
import com.proton.bystone.bean.TestParentBean;
import com.proton.bystone.cache.LoginManager;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.utils.L;
import com.proton.bystone.utils.T;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;
import com.proton.library.widget.MyExpandListView;
import com.proton.library.widget.MyListView;
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
 * 检查单
 */
@MTFActivityFeature(layout = R.layout.activity_test_list)
public class TestListActivity extends MTFBaseActivity {

    @Bind(R.id.test_list_expandable_list_view)
    MyExpandListView expandListView;
    private MyAdapter adapter;
    private List<TestParentBean> parents = null;
    private Map<String, List<TestChildBean>> map = null;

    ///保存组装后的数据  重复的添加到一个key中。
    private ArrayList<ComboKey> keys = new ArrayList<>();
    private HashMap<ComboKey, ArrayList<CarCombo>> maps = new HashMap<>();

    private ArrayList<SuggestChange> suggestChanges = null;//建议更换项
    private ArrayList<Discount> discounts = null;//优惠项

    private DecimalFormat df = new DecimalFormat("0.00");//数字 格式化

    private int status = 0;//状态， 如果>= 15 则不可修改只能看

    @Bind(R.id.test_list_suggest_list_view)
    MyListView suggestListView;//建议列表
    private SuggestChangeAdapter suggestChangeAdapter;

    @Bind(R.id.test_list_confirm_btn)
    Button confirmBtn;//确认按钮

    @Bind(R.id.test_list_suggest_gsf_txt)
    TextView gsfTxt;//工时费

    @Bind(R.id.test_list_suggest_maint_price_txt)
    TextView maintTxt;//保养小计

    @Bind(R.id.test_list_suggest_fee_txt)
    TextView feeTxt;//建议小计

    @Bind(R.id.test_list_suggest_discount_price_txt)
    TextView discountTxt;//优惠

    String code;//查询的bookCode

    @Override
    public void initialize(Bundle savedInstanceState) {

        //
        status = getIntent().getIntExtra("status", 0);
        if (status >= 9) {
            confirmBtn.setVisibility(View.GONE);
        }
        //
        adapter = new MyAdapter();
        expandListView.setAdapter(adapter);
        expandListView.setGroupIndicator(null);

        //
        suggestChangeAdapter = new SuggestChangeAdapter();
        suggestListView.setAdapter(suggestChangeAdapter);

        //
        code = getIntent().getStringExtra("code");

        getData();
    }


    /**
     * 确认检测单提交
     * @param view
     */
    @OnClick(R.id.test_list_confirm_btn)
    public void submitTestList(View view) {
        new AlertDialog.Builder(context)
                .setTitle("提示您")
                .setMessage("确认后将无法修改，是否继续")
                .setNegativeButton("再看看", null)
                .setPositiveButton("已确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        doSubmit();
                    }
                })
                .create()
                .show();
    }

    /**
     * 提交操作
     */
    private void doSubmit() {
        ArrayList<TestListParams> paramses = generateParams();

        //提交预定信息
        final RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("SetSaveRecodsPadctRelated")
                .gson(new Gson())
                .object(paramses)
                .build();
        Call<BaseResp> call = HttpClients.getInstance().maintenance(requestBody);
        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                if (response.body() != null && response.body().getCode() == 1) {
                    if ("1".equals(response.body().getData())){
                        T.s(context, "检测单已确认");
                        animFinish();
                    }else {
                        T.s(context, "检测单确认失败，请稍后再试。");
                    }
                }else {
                    //
                    T.s(context, "服务器繁忙，请稍后再试。");
                }
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                T.s(context, "服务器繁忙，请稍后再试。");
                L.e(" 确认检测单提交::" + t.getMessage());
            }
        });
    }

    /**
     * 生成提交参数
     */
    private ArrayList<TestListParams> generateParams() {
        ArrayList<TestListParams> testListParamses = new ArrayList<>();
        TestListParams tempTestListParams = null;

        //维保检测项
        for (Map.Entry<ComboKey, ArrayList<CarCombo>> entry : maps.entrySet()) {
            ArrayList<CarCombo> vList = entry.getValue();
            for (CarCombo combo : vList) {
                if (combo.getSelected() == 1){
                    tempTestListParams = new TestListParams(
                            code, LoginManager.getInstance().getLoginInfo().getMb_Code(), combo.getType_CODE(), combo.getPt_Code(), "", "1", combo.getN_HYJ(),
                            combo.getGongShiFei()+"", combo.getPs_NAME(), discounts == null || discounts.size() <= 0 ? "0" : discounts.get(0).getD_Value()
                    );
                    testListParamses.add(tempTestListParams);
                }
            }
        }

        //其它检测项
        if (suggestChanges != null && suggestChanges.size() > 0) {
            for (SuggestChange suggestChange : suggestChanges) {
                if (suggestChange.getSelected() == 1){
                    tempTestListParams = new TestListParams(
                            code, LoginManager.getInstance().getLoginInfo().getMb_Code(), "","", suggestChange.getI_ProCode(), "2", suggestChange.getPt_Price()+"",
                            suggestChange.getGongShiFei()+"", suggestChange.getVC_Name(), "0"
                    );
                    testListParamses.add(tempTestListParams);
                }
            }
        }

        return testListParamses;
    }

    /**
     * 获取检测单数据
     */
    private void getData() {
        //提交预定信息
        final RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("MaintenanceDetectionOfSingle")
                .gson(new Gson())
                .typeValue("string", "201608111248543221")//code)
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
                        suggestChanges = new Gson().fromJson(jsonArray.get(1).toString(), new TypeToken<List<SuggestChange>>() {
                        }.getType());
                        discounts = new Gson().fromJson(jsonArray.get(2).toString(), new TypeToken<List<Discount>>() {
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
                                    value.setDisable(true);//目前为必选项
//                                    value.setCanCanceled(true);
                                }
                            }
                            maps.put(key, values);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    for (int i = 0; i < keys.size(); i++) {//展开expandable list view
                        expandListView.expandGroup(i);
                    }
                    adapter.notifyDataSetChanged();//更新数据
                    calculateMaintPrice();//计算维保小计
                    calculateSuggestPrice();//计算建议小计
                    if (discounts != null && discounts.size() > 0) {
                        discountTxt.setVisibility(View.VISIBLE);
                        discountTxt.setText(discounts.get(0).getEventTitle() + "\t\t￥" + discounts.get(0).getD_Value());
                    } else {
                        discountTxt.setVisibility(View.GONE);
                    }

                    suggestChangeAdapter.notifyDataSetChanged();//更换建议刷新显示
                } else {
                    L.e("解析上门保养套餐>>>没有数据");
                }
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                L.e("getThirdLevelYear::" + t.getMessage());
            }
        });
    }

    /**
     * 计算维保小计
     */
    private void calculateMaintPrice() {
        if (maps == null || maps.size() <= 0) return;

        BigDecimal totalBigDecimal = new BigDecimal("0").setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal gsfBigDecimal = new BigDecimal("0").setScale(2, BigDecimal.ROUND_HALF_UP);
        for (Map.Entry<ComboKey, ArrayList<CarCombo>> entry : maps.entrySet()) {
            ArrayList<CarCombo> vList = entry.getValue();
            for (CarCombo combo : vList) {
                if (combo.getSelected() == 1) {
                    //总价=单个价钱+工时费
                    gsfBigDecimal = gsfBigDecimal.add(new BigDecimal(combo.getGongShiFei()));
                    totalBigDecimal = totalBigDecimal.add(new BigDecimal(combo.getN_HYJ())).add(new BigDecimal(combo.getGongShiFei()));
                }
            }
        }

        //有优惠活动的时候 ， 减去优惠
        if (discounts != null && discounts.size() > 0) {
            totalBigDecimal = totalBigDecimal.subtract(new BigDecimal(discounts.get(0).getD_Value()));
        }

        gsfTxt.setText("工时费\t\t￥" + gsfBigDecimal.doubleValue());
        maintTxt.setText("保养小计\t\t￥" + totalBigDecimal.doubleValue());

    }

    /**
     * 计算建议小计
     */
    private void calculateSuggestPrice() {
        if (suggestChanges == null || suggestChanges.size() <= 0) return;

        BigDecimal totalBigDecimal = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);

        for (SuggestChange suggestChange : suggestChanges) {
            if (suggestChange.getSelected() == 1)
                totalBigDecimal = totalBigDecimal.add(new BigDecimal(suggestChange.getPt_Price())).add(new BigDecimal(suggestChange.getGongShiFei()));
        }

        feeTxt.setText("更换小计\t\t" + totalBigDecimal.floatValue());
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

            if (status >= 9) {//用户已经确认， 不能修改
                if (carCombo.getSelected() == 1) {
                    holder.rbView.setBackgroundResource(R.mipmap.icon_radio_blue_sel_disable);
                }else  if (carCombo.getSelected() == 0) {
                    holder.rbView.setBackgroundResource(R.mipmap.icon_radio_blue_nor);
                }
            } else {
                if (carCombo.isDisable()) {//禁用    没有选择效果。  必选项  不添加点击事件
                    holder.rbView.setBackgroundResource(R.mipmap.icon_radio_blue_sel_disable);
                } else {

                    if (carCombo.getSelected() == 1) {//设置 选中/非选中状态
                        holder.rbView.setBackgroundResource(R.mipmap.icon_radio_blue_sel);
                    } else if (carCombo.getSelected() == 0) {
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
                                calculateMaintPrice();
                            } else {//必需任选其一
                                if (carCombo.getSelected() == 0) {//选中
                                    final int size = childList.size();
                                    for (int i = 0; i < size; i++) {//除了当前点击的item其它 的设置为非选中状态
                                        childList.get(i).setSelected(0);
                                    }
                                    carCombo.setSelected(1);
                                    calculateMaintPrice();
                                }
                            }
                            notifyDataSetChanged();
                        }
                    });
                }
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
     * 建议更换Adapter
     */
    class SuggestChangeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return suggestChanges == null ? 0 : suggestChanges.size();
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
            final SuggestChange suggestChange = suggestChanges.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_suggest_change, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

           if (status >= 9) {
               if (suggestChange.getSelected() == 1) {
                   holder.chkView.setBackgroundResource(R.mipmap.icon_checkbox_disable);
               } else {
                   holder.chkView.setBackgroundResource(R.mipmap.icon_checkbox_nor);
               }
           } else {
               //bind
               if (suggestChange.getSelected() == 1) {
                   holder.chkView.setBackgroundResource(R.mipmap.icon_checkbox_sel);
               } else {
                   holder.chkView.setBackgroundResource(R.mipmap.icon_checkbox_nor);
               }
               holder.nameTxt.setText(suggestChange.getVC_Name());
               holder.priceTxt.setText("￥" + suggestChange.getPt_Price());
               holder.feeTxt.setText("￥" + suggestChange.getGongShiFei());

               holder.rootView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       if (suggestChange.getSelected() == 1) {
                           suggestChange.setSelected(0);
                       } else {
                           suggestChange.setSelected(1);
                       }

                       calculateSuggestPrice();
                       //
                       notifyDataSetChanged();
                   }
               });
           }

            //return
            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.item_suggest_change_chk_view)
            View chkView;

            @Bind(R.id.item_suggest_change_name_txt)
            TextView nameTxt;

            @Bind(R.id.item_suggest_change_price_txt)
            TextView priceTxt;

            @Bind(R.id.item_suggest_change_fee_txt)
            TextView feeTxt;

            @Bind(R.id.item_suggest_change_root_view)
            RelativeLayout rootView;

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
        animFinish();
    }
}
