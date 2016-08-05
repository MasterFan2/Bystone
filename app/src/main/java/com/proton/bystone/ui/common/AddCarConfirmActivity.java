package com.proton.bystone.ui.common;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.gson.Gson;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.DrivingLicense;
import com.proton.bystone.bean.DrivingLicensePic;
import com.proton.bystone.bean.RecognizeCardsinfoItem;
import com.proton.bystone.bean.ReservationParam;
import com.proton.bystone.config.Config;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.utils.L;
import com.proton.bystone.utils.T;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;
import com.proton.library.utils.ActivityManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 行驶证确认
 */
@MTFActivityFeature(layout = R.layout.activity_add_car_confirm)
public class AddCarConfirmActivity extends MTFBaseActivity {

    //上传服务器成功后返回的正照和副照的URL地址
    private String frontServerUrl = null;
    private String copyServerUrl = null;

    //
    private Map<String, String> carInfo = new HashMap<>();

    //车牌号码
    @Bind(R.id.car_num_edit)
    EditText carNumEdit;
    @Bind(R.id.car_num_cbx)
    CheckBox carNumCbx;

    //车辆类型
    @Bind(R.id.car_type_edit)
    EditText typeEdit;
    @Bind(R.id.car_type_cbx)
    CheckBox typeCbx;

    //所有人
    @Bind(R.id.car_own_edit)
    EditText ownEdit;
    @Bind(R.id.car_own_cbx)
    CheckBox ownCbx;

    //住址
    @Bind(R.id.car_address_edit)
    EditText addrEdit;
    @Bind(R.id.car_address_cbx)
    CheckBox addrCbx;

    //使用性质
    @Bind(R.id.car_proton_edit)
    EditText protonEdit;
    @Bind(R.id.car_proton_cbx)
    CheckBox protonCbx;

    //品牌型号
    @Bind(R.id.car_brand_edit)
    EditText brandEdit;
    @Bind(R.id.car_brand_cbx)
    CheckBox brandCbx;

    //车辆识别号
    @Bind(R.id.car_recognize_edit)
    EditText recognizeEdit;
    @Bind(R.id.car_recognize_cbx)
    CheckBox recognizeCbx;

    //发动机号
    @Bind(R.id.car_engine_edit)
    EditText engineEdit;
    @Bind(R.id.car_engine_cbx)
    CheckBox engineCbx;

    //注册日期
    @Bind(R.id.car_register_edit)
    EditText registerEdit;
    @Bind(R.id.car_register_cbx)
    CheckBox registerCbx;

    //发证日期
    @Bind(R.id.car_dat_edit)
    EditText dateEdit;
    @Bind(R.id.car_date_cbx)
    CheckBox dateCbx;

    @Override
    public void initialize(Bundle savedInstanceState) {

        //
        frontServerUrl = getIntent().getStringExtra("front");
        copyServerUrl  = getIntent().getStringExtra("copy");

        ArrayList<RecognizeCardsinfoItem> items = getIntent().getParcelableArrayListExtra("items");
        fillData(items);

    }

    /**
     * 上传车辆信息
     * @param view
     */
    @OnClick(R.id.add_car_confirm_btn)
    public void upload(View view) {

        ArrayList<DrivingLicensePic> dlPicList = new ArrayList<>();
        //正照
        DrivingLicensePic frontDlPic = new DrivingLicensePic(Config.USER_CODE, "5", frontServerUrl, carInfo.get("carNum"));

        //副照
        DrivingLicensePic copyDlPic  = new DrivingLicensePic(Config.USER_CODE, "6", copyServerUrl,  carInfo.get("carNum"));
        dlPicList.add(frontDlPic);
        dlPicList.add(copyDlPic) ;

        DrivingLicense drivingLicense = new DrivingLicense(Config.USER_CODE, carInfo.get("carNum"), carInfo.get("type"), carInfo.get("own")
        ,carInfo.get("address"), carInfo.get("proton"), carInfo.get("brand"), carInfo.get("recognize"), carInfo.get("engine"), carInfo.get("register"),
                carInfo.get("date"));

        final RequestBody requestBody = new ParamsBuilder<ReservationParam>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("DrivingLicenseRecognition")
                .gson(new Gson())
                .object(drivingLicense)
                .object(dlPicList)
                .build();
        Call<BaseResp> call = HttpClients.getInstance().memberInfo(requestBody);
        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                if (response.body().getCode() == 1) {//上传车辆信息success
                    T.s(context, "上传车辆信息成功");
                    ActivityManager.getInstance().finishAllActivity();
                }else {
                    L.e(">>>AddCarConfirmActivity>上传车辆信息失败");
                }
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                L.e(">>>AddCarConfirmActivity>上传车辆信息失败");
            }
        });
    }

    /**
     * 设置可输入、不可输入
     */
    @OnCheckedChanged({R.id.car_date_cbx, R.id.car_register_cbx, R.id.car_engine_cbx, R.id.car_recognize_cbx, R.id.car_brand_cbx,
            R.id.car_proton_cbx, R.id.car_address_cbx, R.id.car_own_cbx, R.id.car_type_cbx, R.id.car_num_cbx})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.car_date_cbx:
                if (isChecked) {
                    dateEdit.setEnabled(false);
                    carInfo.put("date", dateEdit.getText().toString());
                }
                else dateEdit.setEnabled(true);
                break;
            case R.id.car_register_cbx:
                if (isChecked) {
                    registerEdit.setEnabled(false);
                    carInfo.put("register", registerEdit.getText().toString());
                }
                else registerEdit.setEnabled(true);
                break;
            case R.id.car_engine_cbx:
                if (isChecked) {
                    engineEdit.setEnabled(false);
                    carInfo.put("engine", engineEdit.getText().toString());
                }
                else engineEdit.setEnabled(true);
                break;
            case R.id.car_recognize_cbx:
                if (isChecked) {
                    recognizeEdit.setEnabled(false);
                    carInfo.put("recognize", recognizeEdit.getText().toString());
                }
                else recognizeEdit.setEnabled(true);
                break;
            case R.id.car_brand_cbx:
                if (isChecked) {
                    brandEdit.setEnabled(false);
                    carInfo.put("brand", brandEdit.getText().toString());
                }
                else brandEdit.setEnabled(true);
                break;
            case R.id.car_proton_cbx:
                if (isChecked) {
                    protonEdit.setEnabled(false);
                    carInfo.put("proton", protonEdit.getText().toString());
                }
                else protonEdit.setEnabled(true);
                break;
            case R.id.car_address_cbx:
                if (isChecked) {
                    addrEdit.setEnabled(false);
                    carInfo.put("address", addrEdit.getText().toString());
                }
                else addrEdit.setEnabled(true);
                break;
            case R.id.car_own_cbx:
                if (isChecked) {
                    ownEdit.setEnabled(false);
                    carInfo.put("own", ownEdit.getText().toString());
                }
                else ownEdit.setEnabled(true);
                break;
            case R.id.car_type_cbx:
                if (isChecked) {
                    typeEdit.setEnabled(false);
                    carInfo.put("type", typeEdit.getText().toString());
                }
                else typeEdit.setEnabled(true);
                break;
            case R.id.car_num_cbx:
                if (isChecked) {
                    carNumEdit.setEnabled(false);
                    carInfo.put("carNum", carNumEdit.getText().toString());
                }
                else carNumEdit.setEnabled(true);
                break;
        }
    }

    /**
     * 填充数据
     *
     * @param items
     */
    private void fillData(ArrayList<RecognizeCardsinfoItem> items) {

        if (items == null || items.size() <= 0) return;

        for (RecognizeCardsinfoItem item : items) {
            if (item.getDesc().equals("号牌号码")) {
                carInfo.put("carNum", item.getContent());
            } else if (item.getDesc().equals("车辆类型")) {
                carInfo.put("type", item.getContent());
            } else if (item.getDesc().equals("所有人")) {
                carInfo.put("own", item.getContent());
            } else if (item.getDesc().equals("住址")) {
                carInfo.put("address", item.getContent());
            } else if (item.getDesc().equals("品牌型号")) {
                carInfo.put("brand", item.getContent());
            } else if (item.getDesc().equals("车辆识别代号")) {
                carInfo.put("recognize", item.getContent());
            } else if (item.getDesc().equals("发动机号码")) {
                carInfo.put("engine", item.getContent());
            } else if (item.getDesc().equals("注册日期")) {
                carInfo.put("register", item.getContent());
            } else if (item.getDesc().equals("发证日期")) {
                carInfo.put("date", item.getContent());
            } else if (item.getDesc().equals("使用性质")) {
                carInfo.put("proton", item.getContent());
            }
        }

        bindView();
    }

    /**
     * 设置数据到Edit中
     */
    private void bindView() {
        carNumEdit.setText(carInfo.get("carNum"));
        typeEdit.setText(carInfo.get("type"));
        ownEdit.setText(carInfo.get("own"));
        addrEdit.setText(carInfo.get("address"));
        protonEdit.setText(carInfo.get("proton"));
        brandEdit.setText(carInfo.get("brand"));
        recognizeEdit.setText(carInfo.get("recognize"));
        engineEdit.setText(carInfo.get("engine"));
        registerEdit.setText(carInfo.get("register"));
        dateEdit.setText(carInfo.get("date"));
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
