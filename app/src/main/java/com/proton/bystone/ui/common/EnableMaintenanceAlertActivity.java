package com.proton.bystone.ui.common;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.CarInfo;
import com.proton.bystone.bean.MaintenanceAlertParams;
import com.proton.bystone.bean.OrderStateCodeResp;
import com.proton.bystone.bean.ReservationParam;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.ui.maintenance.OrderStateActivity;
import com.proton.bystone.utils.L;
import com.proton.bystone.utils.T;
import com.proton.bystone.utils.TimeUtil;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;
import com.proton.library.widget.picker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 开启保养提醒
 */
@MTFActivityFeature(layout = R.layout.activity_enable_maintenance_alert)
public class EnableMaintenanceAlertActivity extends MTFBaseActivity {

    @Bind(R.id.alert_date_txt)
    TextView previousDateTxt;

    @Bind(R.id.alert_year_mileage_txt)
    TextView yearMileageTxt;

    @Bind(R.id.alert_previous_mileage_edit)
    EditText previousMileageEdit;

    @Bind(R.id.alert_all_mileage_edit)
    EditText allMileageEdit;


    private Calendar calendar = Calendar.getInstance();

    ///选择的年、月、日、时、分
    private int year = -1;
    private int month = -1;
    private int day = -1;

    private int chooseYearMileage = -1;//选择的年里程

    private CarInfo carInfo = null;

    /**
     * 日期选择
     */
    final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePickerDialog datePickerDialog, int mYear, int mMonth, int mDay) {
            year = mYear;
            month = mMonth + 1;
            day = mDay;
            String strYearMonthDay = year + "." + TimeUtil.getStringByIntIfNumLessThanTen(month) + "." + TimeUtil.getStringByIntIfNumLessThanTen(day);
            previousDateTxt.setText(strYearMonthDay);
        }

    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

    /***
     * 初始化
     *
     * @param savedInstanceState
     */
    @Override
    public void initialize(Bundle savedInstanceState) {
        carInfo = getIntent().getParcelableExtra("carInfo");
    }

    /**
     * 选择日期
     *
     * @param v
     */
    @OnClick(R.id.alert_date_txt)
    public void chooseDate(View v) {
        datePickerDialog.show(getFragmentManager(), "datePicker");
    }

    @OnClick(R.id.alert_year_mileage_txt)
    public void chooseYearMileage(View v) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("请选择 年里程")
                .setSingleChoiceItems(new String[]{"2万公里", "3万公里", "4万公里", "5万公里", "6万公里"}, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                chooseYearMileage = 2;
                                break;
                            case 1:
                                chooseYearMileage = 3;
                                break;
                            case 2:
                                chooseYearMileage = 4;
                                break;
                            case 3:
                                chooseYearMileage = 5;
                                break;
                            case 4:
                                chooseYearMileage = 6;
                                break;
                        }
                        yearMileageTxt.setText(chooseYearMileage + "万公里");
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }

    /**
     * 检测上传数据
     */
    private void checkParams() {

        String preMaintenanceMileage = previousMileageEdit.getText().toString();
        String allMileage = allMileageEdit.getText().toString();

        if (TextUtils.isEmpty(preMaintenanceMileage)) {
            T.s(context, "请输入上次保养里程");
            return;
        }

        if (year == -1 || month == -1 || day == -1) {
            T.s(context, "请选择上次保养时间");
            return;
        }

        if (chooseYearMileage == -1) {
            T.s(context, "请选择年里程");
            return;
        }

        if (TextUtils.isEmpty(allMileage)) {
            T.s(context, "请输入总里程");
            return;
        }

        //开启保养提醒
        enableAlert(preMaintenanceMileage, allMileage);
    }

    /**
     * 开启保养提醒
     */
    private void enableAlert(String preMaintenanceMileage, String allMileage) {

        MaintenanceAlertParams params = new MaintenanceAlertParams(carInfo.getID(), Integer.parseInt(preMaintenanceMileage), year + "-" + month + "-" + day, chooseYearMileage, Integer.parseInt(allMileage));
        //OpenMaintenanceReminders
        final RequestBody requestBody = new ParamsBuilder<MaintenanceAlertParams>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("OpenMaintenanceReminders")
                .gson(new Gson())
                .object(params)
                .build();
        Call<BaseResp> call = HttpClients.getInstance().memberInfo(requestBody);
        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                if (response.body().getCode() == 1) {
//                    List<OrderStateCodeResp> orderStateResps = new Gson().fromJson(response.body().getData(), new TypeToken<List<OrderStateCodeResp>>() {}.getType());
                    if ("1".equals(response.body().getData())) {
                        T.s(context, "开启保养提醒成功");
                    } else {
                        T.s(context, "开启保养提醒失败");
                    }
//                    Intent intent = new Intent(context, OrderStateActivity.class);
//                    intent.putExtra("code", "201605091528083056");//orderStateResps.get(0).getCode()201605091528083056
//                    animStart(intent);
                } else {
                    T.s(context, "预约失败");
                }
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                L.e("getThirdLevelYear::" + t.getMessage());
            }
        });
    }

    /**
     * 返回按键
     */
    @Override
    public void backPressed() {
        animFinish();
    }

    /**
     * 上传
     *
     * @param view
     */
    @OnClick(R.id.m_title_right_btn)
    public void confirm(View view) {
        checkParams();
    }

    /**
     * title 的返回按钮
     *
     * @param view
     */
    @OnClick(R.id.m_title_left_btn)
    public void back(View view) {
        animFinish();
    }
}
