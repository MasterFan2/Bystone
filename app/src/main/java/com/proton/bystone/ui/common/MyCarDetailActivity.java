package com.proton.bystone.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.CarInfo;
import com.proton.bystone.bean.MaintenanceRecord;
import com.proton.bystone.bean.NextMaintenance;
import com.proton.bystone.bean.UpdateMyCarParams;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.utils.L;
import com.proton.bystone.utils.S;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;
import com.proton.library.widget.MyListView;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 爱车详情
 * create by masterfan on 2016.07.29
 */
@MTFActivityFeature(layout = R.layout.activity_my_car_detail)
public class MyCarDetailActivity extends MTFBaseActivity {

    @Bind(R.id.my_car_detail_normal_maintenance_txt)
    TextView normalMaintenanceTxt;

    @Bind(R.id.my_car_detail_maintenance_txt)
    TextView maintenanceTxt;

    @Bind(R.id.my_car_detail_next_maintenance_txt)
    TextView nextMaintenanceTxt;

    @Bind(R.id.my_car_detail_tips_layout)
    LinearLayout tipsLayout;

    @Bind(R.id.my_car_detail_enable_tips_btn)
    Button enableTipsBtn;

    @Bind(R.id.my_car_detail_brand_txt)
    TextView brandTxt;

    @Bind(R.id.my_car_detail_year_txt)
    TextView yearTxt;

    @Bind(R.id.my_car_detail_car_no_txt)
    TextView carNoTxt;

    @Bind(R.id.my_car_detail_engine_txt)
    TextView engineNoTxt;

    @Bind(R.id.my_car_detail_recognize_no_txt)
    TextView recognizeNoTxt;

    @Bind(R.id.my_car_detail_date_txt)
    TextView rollDateTxt;

    @Bind(R.id.my_car_detail_list_view)
    MyListView listView;
    private MyAdapter adapter;

    private List<MaintenanceRecord> recordList;//维保记录
    private List<NextMaintenance>   nextMaintenance;//下次保养的时间和里程

    private CarInfo carInfo = null;//传递过来的车辆信息

    private Calendar calendar = Calendar.getInstance();

    @Override
    public void initialize(Bundle savedInstanceState) {

        carInfo = getIntent().getParcelableExtra("carInfo");

        adapter = new MyAdapter();
        listView.setAdapter(adapter);

        if (carInfo != null) {

            //
            bindData(carInfo);

            //
            getNextMaintenanceMileageAndDate();

            //
            getRecordData();
        }
    }

    /**
     * 车辆下次保养时间提醒
     */
    private void getNextMaintenanceMileageAndDate() {
        final RequestBody requestBody = new ParamsBuilder<UpdateMyCarParams>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetMaintenanceRemindersTime")
                .gson(new Gson())
                .typeValue("string", carInfo.getVC_CarNO())
                .build();
        Call<BaseResp> call = HttpClients.getInstance().memberInfo(requestBody);
        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                if (response.body().getCode() == 1) {
                    nextMaintenance = new Gson().fromJson(response.body().getData(), new TypeToken<List<NextMaintenance>>() {}.getType());
                    if (nextMaintenance != null && nextMaintenance.size() > 0)
                        nextMaintenanceTxt.setText("下次做保养的时间为"+ nextMaintenance.get(0).getSaveMaintenanceTime() +"，或达到"+ nextMaintenance.get(0).getNextServiceMileage() +"公里");
                } else {
                    S.o("没有获取到下次保养的时间和里程");
                }
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                L.e("getThirdLevelYear::" + t.getMessage());
            }
        });
    }

    /**
     * 查询维保记录
     */
    private void getRecordData() {
        final RequestBody requestBody = new ParamsBuilder<UpdateMyCarParams>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetMaintenanceRecord")
                .gson(new Gson())
                .typeValue("string", carInfo.getVC_CarNO())
                .build();
        Call<BaseResp> call = HttpClients.getInstance().memberInfo(requestBody);
        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                if (response.body().getCode() == 1) {
                    recordList = new Gson().fromJson(response.body().getData(), new TypeToken<List<MaintenanceRecord>>() {}.getType());
                    adapter.notifyDataSetChanged();
                } else {
                    S.o("设置默认车辆失败");
                }
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                L.e("getThirdLevelYear::" + t.getMessage());
            }
        });
    }

    /**
     * Bind data
     *
     * @param carInfo
     */
    private void bindData(CarInfo carInfo) {
        if (carInfo.getOpenRemind() == 1) {//开启保养提醒
            calendar.add(Calendar.DAY_OF_MONTH, carInfo.getCountdown());
            int year = calendar.get(Calendar.YEAR);
            int month= calendar.get(Calendar.MONTH);
            int day  = calendar.get(Calendar.DAY_OF_MONTH);

            maintenanceTxt.setText("【保养提醒】您的爱车保养日期为"+ year + "年" + month + "月" + day + "日");

            tipsLayout.setVisibility(View.VISIBLE);
            enableTipsBtn.setVisibility(View.GONE);
        } else {
            tipsLayout.setVisibility(View.GONE);
            enableTipsBtn.setVisibility(View.VISIBLE);
        }
        brandTxt.setText(carInfo.getB_Name());
        yearTxt.setText(carInfo.getM_Year());
        carNoTxt.setText(carInfo.getVC_CarNO());
        engineNoTxt.setText(carInfo.getVC_CarFDJ());
        recognizeNoTxt.setText(carInfo.getCarIdentifier());
        rollDateTxt.setText(carInfo.getDrivingLicenseTime());
        normalMaintenanceTxt.setText("(常规保养间隔:"+ carInfo.getMaintenanceMileage() +"公里)");
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return recordList == null ? 0 : recordList.size();
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
            final ViewHolder holder;
            final MaintenanceRecord record = recordList.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_maintenance_record, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //bind data
            holder.recordTxt.setText(record.getOConsumerName());

            //return
            return convertView;
        }

        class ViewHolder {

            @Bind(R.id.item_maintenance_record_txt)
            TextView recordTxt;

            public ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    @OnClick(R.id.my_car_detail_enable_tips_btn)
    public void enable() {
        Intent intent = new Intent(context, EnableMaintenanceAlertActivity.class);
        intent.putExtra("carInfo", carInfo);
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
