package com.proton.bystone.ui.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.OrderBaseInfo;
import com.proton.bystone.bean.OrderStateInfo;
import com.proton.bystone.cache.LoginManager;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.utils.L;
import com.proton.bystone.utils.T;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;
import com.proton.library.widget.MyListView;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 订单状态
 * Created by MasterFan on 2016/7/23.
 */
@MTFActivityFeature(layout = R.layout.activity_order_state)
public class OrderStateActivity extends MTFBaseActivity {

    @Bind(R.id.order_state_number_txt)
    TextView idNumTxt;

    @Bind(R.id.order_state_all_txt)
    TextView allTxt;

    @Bind(R.id.order_state_custom_service_txt)
    TextView customServiceTxt;

    @Bind(R.id.order_state_service_detail_btn)
    Button   serviceDetailBtn;

    @Bind(R.id.order_state_test_list_layout)
    RelativeLayout testListLayout;

    @Bind(R.id.order_state_maintenance_picture_layout)
    RelativeLayout picLayout;

    @Bind(R.id.order_state_test_list_txt)
    TextView testListTxt;

    @Bind(R.id.order_state_maintenance_picture_txt)
    TextView picTxt;

    @Bind(R.id.order_state_list_view)
    MyListView listView;
    private MyAdapter adapter;

    private List<OrderBaseInfo> orderBaseInfos   = null;
    private OrderBaseInfo orderBaseInfo          = null;
    private List<OrderStateInfo> orderStateInfos = null;

    private String code ;


    @Override
    public void initialize(Bundle savedInstanceState) {

        code = getIntent().getStringExtra("code");
        adapter = new MyAdapter();
        listView.setAdapter(adapter);

//        if (TextUtils.isEmpty(code)) {
//            return;
//        }
        //提交预定信息
        final RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("MaintenanceStatus")
                .gson(new Gson())
                .typeValue("string", "201608091423285485")//code)
                .build();
        Call<BaseResp> call = HttpClients.getInstance().maintenance(requestBody);
        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
//                List<CarModel> carModels = new Gson().fromJson(response.body().getData(), new TypeToken<List<CarModel>>() {}.getType());
                if (response.body().getCode() == 1) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().getData());
                        orderBaseInfos   = new Gson().fromJson(jsonArray.get(0).toString(), new TypeToken<List<OrderBaseInfo>>() {}.getType());
                        bindData();
                        orderStateInfos = new Gson().fromJson(jsonArray.get(1).toString(), new TypeToken<List<OrderStateInfo>>() {}.getType());
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    T.s(context, "查询订单失败");
                }
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                L.e("getMaintenanceStatus::" + t.getMessage());
            }
        });
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return orderStateInfos == null ? 0 : orderStateInfos.size();
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
            final OrderStateInfo orderStateInfo = orderStateInfos.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_order_state, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //bind data
            if (position == 0) {
                holder.iconImg.setImageResource(R.mipmap.icon_order_state_blue);
                holder.line1View.setVisibility(View.GONE);
                holder.dateTxt.setTextColor(getResources().getColor(R.color.blue));
                holder.descTxt.setTextColor(getResources().getColor(R.color.blue));
            }else {
                holder.iconImg.setImageResource(R.mipmap.icon_order_state_gray);
                holder.line1View.setVisibility(View.VISIBLE);
                holder.dateTxt.setTextColor(getResources().getColor(R.color.mtf_gray_600));
                holder.descTxt.setTextColor(getResources().getColor(R.color.mtf_gray_600));
            }

            holder.dateTxt.setText(orderStateInfo.getCreateTime());
            //0：已提交
            //1：已指派
            //2：企业已确认（维保企业已确认）
            //3：已派工
            //4：技师已确认
            //5：已出门
            //6：已抵达
            //7：已检测
            //8：已确认(可看服务详情s)
            //9：保养已完成（所有保养项目完成）
            //10：已付款
            //11：已回访（回访完毕流程结束）

            if (orderStateInfo.getBookStaus() == 0){
                holder.descTxt.setText("已提交");
            }else if (orderStateInfo.getBookStaus() == 1) {
                holder.descTxt.setText("已指派");
            }else if (orderStateInfo.getBookStaus() == 2) {
                holder.descTxt.setText("企业已确认");
            }else if (orderStateInfo.getBookStaus() == 3) {
                holder.descTxt.setText("已派工");
            }else if (orderStateInfo.getBookStaus() == 4) {
                holder.descTxt.setText("技师已确认");
            }else if (orderStateInfo.getBookStaus() == 5) {
                holder.descTxt.setText("已出门");
            }else if (orderStateInfo.getBookStaus() == 6) {
                holder.descTxt.setText("已抵达");
            }else if (orderStateInfo.getBookStaus() == 7) {
                holder.descTxt.setText("已检测");
            }else if (orderStateInfo.getBookStaus() == 8) {
                holder.descTxt.setText("已确认");
            }else if (orderStateInfo.getBookStaus() == 9) {
                holder.descTxt.setText("保养已完成");
            }else if (orderStateInfo.getBookStaus() == 10) {
                holder.descTxt.setText("已付款");
            }else if (orderStateInfo.getBookStaus() == 11) {
                holder.descTxt.setText("已回访");
            }
            //
            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.item_order_state_icon)
            ImageView iconImg;
            @Bind(R.id.item_order_state_line_1_view)
            View      line1View;
            @Bind(R.id.item_order_state_line_2_view)
            View      line2View;
            @Bind(R.id.item_order_state_desc_txt)
            TextView  descTxt;
            @Bind(R.id.item_order_state_date_txt)
            TextView  dateTxt;

            public ViewHolder(View view){
                ButterKnife.bind(this, view);
            }
        }
    }


    /**
     * 绑定数据
     */
    private void bindData() {
        if (orderBaseInfos != null && orderBaseInfos.size() > 0) {
            orderBaseInfo = orderBaseInfos.get(0);
            idNumTxt.setText("保养ID号:" + orderBaseInfo.getBookCode());
            String strAllTxt = "姓名：" + orderBaseInfo.getUserName() + "\n电话："+orderBaseInfo.getMobile() +
                    "\n地址：" + orderBaseInfo.getAddress() + "\n时间：" + orderBaseInfo.getBookBeginTime();
            allTxt.setText(strAllTxt);

            if (orderBaseInfo.getBookStaus() >= 7) {
                testListTxt.setText("详情 > ");
                testListLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, TestListActivity.class);
                        intent.putExtra("status", orderBaseInfo.getBookStaus());
                        intent.putExtra("code", code);
                        animStart(intent);
                    }
                });
            }

            if (orderBaseInfo.getBookStaus() >= 8) {
                serviceDetailBtn.setEnabled(true);
            }

            if (orderBaseInfo.getBookStaus() >= 9) {
                picTxt.setText("详情 > ");
                picLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, MaintenancePictureActivity.class);
                        intent.putExtra("code", code);
                        animStart(intent);
                    }
                });
            }
        }
    }



//    @OnClick(R.id.order_state_test_list_layout)
//    public void testListClick(View view) {
//
//    }
//
//    @OnClick(R.id.order_state_maintenance_picture_layout)
//    public void maintenanceClick(View view) {
//
//    }

    @OnClick(R.id.order_state_service_detail_btn)
    public void detailClick(View view)  {
        Intent intent = new Intent(context, ServiceDetailActivity.class);
        intent.putExtra("status", orderBaseInfo);
        intent.putExtra("code", code);
        animStart(intent);
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
