package com.proton.bystone.ui.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.CarInfo;
import com.proton.bystone.bean.MyLocation;
import com.proton.bystone.bean.OrderStateCodeResp;
import com.proton.bystone.bean.Region;
import com.proton.bystone.bean.ReservationParam;
import com.proton.bystone.bean.ReservationParams2;
import com.proton.bystone.cache.LoginManager;
import com.proton.bystone.location.LocationManager;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.ui.main.tab.home.Homeserch;
import com.proton.bystone.ui.shopcar.ShopCarActivity;
import com.proton.bystone.utils.L;
import com.proton.bystone.utils.MatcherUtil;
import com.proton.bystone.utils.PrefUtils;
import com.proton.bystone.utils.T;
import com.proton.bystone.utils.TimeUtil;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;
import com.proton.library.utils.ActivityManager;
import com.proton.library.widget.dialog.KProgressHUD;
import com.proton.library.widget.picker.date.DatePickerDialog;
import com.proton.library.widget.picker.time.RadialPickerLayout;
import com.proton.library.widget.picker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 预约时间
 */
@MTFActivityFeature(layout = R.layout.activity_bespeak)
public class BespeakActivity extends MTFBaseActivity {

    @Bind(R.id.bespeak_year_month_day_txt)
    TextView yearMonthDayTxt;

    @Bind(R.id.bespeak_time_txt)
    TextView timeTxt;

    @Bind(R.id.bespeak_previous_addr_txt)
    TextView previousAddrTxt;

    @Bind(R.id.bespeak_input_previous_layout)
    LinearLayout previousLayout;

    @Bind(R.id.bespeak_input_addr_layout)
    RelativeLayout inputLayout;

    @Bind(R.id.bespeak_input_previous_view)
    View previousAddrView;

    @Bind(R.id.bespeak_input_addr_view)
    View inputAddrView;

    @Bind(R.id.bespeak_input_addr_edit)
    EditText addrEdit;

    @Bind(R.id.bespeak_name_edit)
    EditText nameEdit;

    @Bind(R.id.bespeak_phone_edit)
    EditText phoneEdit;

    @Bind(R.id.bespeak_remark_edit)
    EditText remarkEdit;

    @Bind(R.id.bespeak_done_btn)
    Button doneBtn;

    @Bind(R.id.bespeak_choose_district_txt)
    TextView chooseDistrictTxt;//选择区

    String startStr = null;
    String endStr = null;

    private CarInfo carInfo = null;//车辆信息

    private int currentSelected = 1;//选择的按钮      1：前面保存的地址    2：重新输入地址

    private Calendar calendar = Calendar.getInstance();

    ///选择的年、月、日、时、分
    private int year = calendar.get(Calendar.YEAR);
    private int month = calendar.get(Calendar.MONTH) + 1;
    private int day = calendar.get(Calendar.DAY_OF_MONTH);

    private int startHour = calendar.get(Calendar.HOUR_OF_DAY);
    private int startMinute = calendar.get(Calendar.MINUTE);

    private int endHour = startHour + 1;//默认的结束时   加1
    private int endMinute = calendar.get(Calendar.MINUTE);

    //保存本地地址信息
    private MyLocation previousLoc = null;
    private MyLocation newLoc = null;

    private boolean isGetting = false;//如果正在获取, 则路过当前获取

    private ArrayList<ReservationParams2> params2List = null;

    private boolean isBespeak = false;
    private Region region;//选择区返回结果

    private KProgressHUD progressHUD;

    @OnClick(R.id.bespeak_choose_district_txt)
    public void chooseDistrict() {
        Intent intent = new Intent(context, Homeserch.class);
        intent.putExtra("where", "bespeak");
        animStartForResult(9527, intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 9527 && resultCode == RESULT_OK) {
            if (data != null) {
                region = data.getParcelableExtra("region");
                if (region != null) {
                    chooseDistrictTxt.setText(region.getNAME());
                }
            }
        }
    }

    /**
     * init
     *
     * @param savedInstanceState
     */
    @Override
    public void initialize(Bundle savedInstanceState) {

        progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("正在识别,请稍后...")
                .setDimAmount(0.4f)
                .setCancellable(false);

        carInfo = getIntent().getParcelableExtra("carInfo");
        params2List = getIntent().getParcelableArrayListExtra("params2");

        isBespeak = getIntent().getBooleanExtra("bespeak", false);

        startStr = TimeUtil.getStringByIntIfNumLessThanTen(startHour) + ":" + TimeUtil.getStringByIntIfNumLessThanTen(startMinute);
        endStr = TimeUtil.getStringByIntIfNumLessThanTen(endHour) + ":" + TimeUtil.getStringByIntIfNumLessThanTen(endMinute);
        yearMonthDayTxt.setText(year + "." + TimeUtil.getStringByIntIfNumLessThanTen(month) + "." + TimeUtil.getStringByIntIfNumLessThanTen(day));
        timeTxt.setText(startStr + " ~ " + endStr);

        previousLoc = PrefUtils.get(context);

        if (previousLoc == null || TextUtils.isEmpty(previousLoc.getAddr())) {//本地没有位置信息,开始 获取 位置信息
            inputAddrView.setBackgroundResource(R.drawable.icon_radio_sel);
            previousAddrView.setBackgroundResource(R.drawable.icon_radio_nor);
            previousAddrTxt.setText("上次地址:无");
            currentSelected = 2;
            addrEdit.setEnabled(true);
        } else {
            previousAddrTxt.setText("上次地址:" + previousLoc.getDistrict() + " " + previousLoc.getAddr());
            addrEdit.setEnabled(false);
        }

        ActivityManager.getInstance().addActivity(this);//预约成功时， 关闭Activity
    }

    public void gotoShopcar(View view) {
        animStart(ShopCarActivity.class);
    }

    @OnClick(R.id.bespeak_input_previous_layout)
    public void previousClick(View view) {
        if (previousLoc != null && !TextUtils.isEmpty(previousLoc.getAddr())) {
            check(1);
        }
    }

    @OnClick(R.id.bespeak_input_addr_layout)
    public void inputClick(View view) {
        check(2);
    }

    /**
     * 获取位置信息
     */
    private void getLocation() {
    }

    /**
     * 检查选择项
     *
     * @param current
     */
    private void check(int current) {
        if (current != currentSelected) {
            currentSelected = current;//设置选中项
            if (current == 1) {
                previousAddrView.setBackgroundResource(R.drawable.icon_radio_sel);
                inputAddrView.setBackgroundResource(R.drawable.icon_radio_nor);
                addrEdit.setEnabled(false);
            } else if (current == 2) {
                previousAddrView.setBackgroundResource(R.drawable.icon_radio_nor);
                inputAddrView.setBackgroundResource(R.drawable.icon_radio_sel);
                if (newLoc == null) {
                    getLocation();
                }
                addrEdit.setEnabled(true);
            }
        }
    }

    /**
     * 时间选择
     */
    final TimePickerDialog timePickerDialog24h = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, boolean isStart) {
            if (isStart) {
                startHour = hourOfDay;
                startMinute = minute;
                startStr = TimeUtil.getStringByIntIfNumLessThanTen(hourOfDay) + ":" + TimeUtil.getStringByIntIfNumLessThanTen(minute);
            } else {
                endHour = hourOfDay;
                endMinute = minute;
                endStr = TimeUtil.getStringByIntIfNumLessThanTen(hourOfDay) + ":" + TimeUtil.getStringByIntIfNumLessThanTen(minute);
            }

            timeTxt.setText(startStr + " ~ " + endStr);
        }
    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

    /**
     * 日期选择
     */
    final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePickerDialog datePickerDialog, int mYear, int mMonth, int mDay) {
            year = mYear;
            month = mMonth + 1;
            day = mDay;
            String strYearMonthDay = year + "." + TimeUtil.getStringByIntIfNumLessThanTen(month) + "." + TimeUtil.getStringByIntIfNumLessThanTen(day);
            yearMonthDayTxt.setText(strYearMonthDay);
        }

    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


    /**
     * 选择年、月、日
     *
     * @param view
     */
    @OnClick(R.id.bespeak_year_month_day_txt)
    public void chooseYearMonthDay(View view) {
        datePickerDialog.show(getFragmentManager(), "yearMonthDay");
    }

    /**
     * 选择时间
     *
     * @param view
     */
    @OnClick(R.id.bespeak_time_txt)
    public void chooseTime(View view) {
        timePickerDialog24h.show(getFragmentManager(), "Time");
    }

    @OnClick(R.id.bespeak_done_btn)
    public void doneClick(View view) {
        checkNull();
    }

    private void checkNull() {
        String name = nameEdit.getText().toString();
        String phone = phoneEdit.getText().toString();
        String remark = remarkEdit.getText().toString();
        final String addr = addrEdit.getText().toString();

        if (currentSelected == 2 && region == null) {
            T.s(context, "请选择区");
            return;
        }

        if ((previousLoc == null || TextUtils.isEmpty(previousLoc.getAddr()) && TextUtils.isEmpty(addr))) {
            T.s(context, "请输入详细地址");
            return;
        }

        if (TextUtils.isEmpty(name)) {
            T.s(context, "请输入联系人姓名");
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            T.s(context, "请输入联系人电话");
            return;
        }

        if (TextUtils.isEmpty(name)) {
            T.s(context, "请输入联系人姓名");
            return;
        }

        if (!MatcherUtil.isPhone(phone)) {
            T.s(context, "电话号码有误，请检查");
            return;
        }

        String startTime = year + "-" + month + "-" + day + " " + startHour + ":" + startMinute;
        String endTime = year + "-" + month + "-" + day + " " + endHour + ":" + endMinute;

        MyLocation temp = null;
        if (currentSelected == 2) {
            newLoc = new MyLocation(addr, 29.02, 36.21, region.getCODE());
            newLoc.setDistrict(region.getNAME());
            temp = newLoc;
            PrefUtils.save(context, newLoc);
        } else if (currentSelected == 1) {
            temp = previousLoc;
        }

        //bookuser 就是昵称
        ReservationParam param = new ReservationParam("", temp.getAddr(), temp.getLongitude() + "", temp.getLatitude() + "", "1", remark,
                LoginManager.getInstance().getLoginInfo().getMb_Code(), carInfo.getM_Model(),  LoginManager.getInstance().getLoginInfo().getMb_Name(),
                LoginManager.getInstance().getLoginInfo().getMb_LoginName(), "1", phone, carInfo.getVC_CarNO(), startTime, endTime);

        progressHUD.show();

        //提交预定信息
        final RequestBody requestBody = new ParamsBuilder<ReservationParam>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("MaintenanceReservation")
                .gson(new Gson())
                .object(param)
                .object(params2List)
                .build();
        Call<BaseResp> call = HttpClients.getInstance().maintenance(requestBody);
        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                if (progressHUD.isShowing()) progressHUD.dismiss();
                if (response.body().getCode() == 1) {
                    List<OrderStateCodeResp> orderStateResps = new Gson().fromJson(response.body().getData(), new TypeToken<List<OrderStateCodeResp>>(){}.getType());
                    T.s(context, "预约成功");
                    Intent intent = new Intent(context, OrderStateActivity.class);
                    intent.putExtra("code", orderStateResps.get(0).getCode());//orderStateResps.get(0).getCode()201605091528083056
                    animStart(intent);
                    ActivityManager.getInstance().finishAllActivity();//预约成功， 结束 之前 的activity
                } else {
                    serverBusy();
                }
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                if (progressHUD.isShowing()) progressHUD.dismiss();
                serverBusy();
                L.e("getThirdLevelYear::" + t.getMessage());
            }
        });
    }

    @Override
    public void backPressed() {
        ActivityManager.getInstance().delActivity(this);//预约成功时， 关闭Activity
        if (isBespeak) ActivityManager.getInstance().finishAllActivity();
        animFinish();
    }

    @OnClick(R.id.m_title_left_btn)
    public void back(View view) {
        ActivityManager.getInstance().delActivity(this);//预约成功时， 关闭Activity
        if (isBespeak) ActivityManager.getInstance().finishAllActivity();
        animFinish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
