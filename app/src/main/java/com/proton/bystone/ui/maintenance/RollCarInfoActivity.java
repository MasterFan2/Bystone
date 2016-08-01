package com.proton.bystone.ui.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.CarBrand;
import com.proton.bystone.bean.CarBrandTwoLevel;
import com.proton.bystone.bean.CarModel;
import com.proton.bystone.bean.CarUploadParam;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.utils.L;
import com.proton.bystone.utils.S;
import com.proton.bystone.utils.T;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 车辆信息录入
 * Created by MasterFan on 2016/7/16.
 */

@MTFActivityFeature(layout = R.layout.activity_roll_car_info)
public class RollCarInfoActivity extends MTFBaseActivity {


    private CarBrand carBrand = null;//大品牌
    private CarBrandTwoLevel carBrandTwoLevel = null;//品牌下面的系列
    private CarModel carModel = null;//年款

    @Bind(R.id.roll_car_info_selected_txt)
    TextView selectedBrandTxt;//选择的品牌、系列

    @Bind(R.id.roll_car_info_choose_car_year_txt)
    TextView selectedYearModelTxt;//选择的年款

    @Bind(R.id.roll_car_info_car_no_edit)
    EditText carNoEdit;

    @Bind(R.id.roll_car_info_car_engine_edit)
    EditText engineEdit;

    @Bind(R.id.roll_car_info_car_recognition_no_edit)
    EditText recognitionEdit;

    private DbManager db ;

    /**
     * @param savedInstanceState
     */
    @Override
    public void initialize(Bundle savedInstanceState) {
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("test.db")
                // 不设置dbDir时, 默认存储在app的私有目录.
                .setDbDir(new File("/sdcard")) // "sdcard"的写法并非最佳实践, 这里为了简单, 先这样写了.
                .setDbVersion(2)
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // TODO: ...
                        // db.addColumn(...);
                        // db.dropTable(...);
                        // ...
                        // or
                        // db.dropDb();
                    }
                });
        db = x.getDb(daoConfig);
    }

    /**
     * 点击年款选择【跳转选择界面】
     *
     * @param view
     */
    @OnClick({R.id.roll_car_info_choose_car_year_layout, R.id.roll_car_info_choose_car_brand_layout})
    public void chooseYearClick(View view) {
        animStartForResult(CarBrandActivity.GET_CAR_BRAND_REQUEST, CarBrandActivity.class);
    }

//    /**
//     * 选择品牌 【跳转选择界面】
//     * @param view
//     */
//    @OnClick(R.id.roll_car_info_choose_car_brand_layout)
//    public void chooseBrandClick(View view) {
//        animStartForResult(CarBrandActivity.GET_CAR_BRAND_REQUEST, CarBrandActivity.class);
//    }

    @OnClick(R.id.roll_car_info_upload_btn)
    public void checkUpload(View view) {
        String carNo = carNoEdit.getText().toString();
        String engine = engineEdit.getText().toString();
        String recognition = recognitionEdit.getText().toString();

        if (TextUtils.isEmpty(carNo)) {
            S.s(view, "请输入车牌号!");
            return;
        }
        if (TextUtils.isEmpty(engine)) {
            S.s(view, "请输入发动机号!");
            return;
        }
        if (TextUtils.isEmpty(recognition)) {
            S.s(view, "请输入车辆识别号!");
            return;
        }

        CarUploadParam carUploadParam = new CarUploadParam("201605261656057265", carModel.getM_Code(), carNo, engine, recognition);
        upload(carUploadParam);
    }

    /**
     * @param carUploadParam
     */
    private void upload(final CarUploadParam carUploadParam) {
        //获取三级年款
        final RequestBody requestBody = new ParamsBuilder<CarUploadParam>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("MyAddCar")
                .gson(new Gson())
                .object(carUploadParam)
                .build();
        Call<BaseResp> call = HttpClients.getInstance().memberInfo(requestBody);
        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                //0：失败，
                // 1：成功,
                // 2：该车型已
                if (response.body().getCode() == 1) {
                    T.s(context, "上传成功");
                    try {//缓存上传的数据
                        CarUploadParam localCarUploadParam = db.findFirst(CarUploadParam.class);
                        if (localCarUploadParam != null)
                            db.delete(localCarUploadParam);
                        db.save(carUploadParam);
                        S.o("上传成功， 缓存到本地");
                    } catch (DbException e) {
                        e.printStackTrace();
                        L.e(":::UPload>>" + e.getMessage() + ":::" + e.getCause().getMessage());
                    }
                    Intent intent = new Intent(context, ComboActivity.class);

                    animStart(intent);
                }else if (response.body().getCode() == 0) {
                    T.s(context, "上传失败，请稍后再试");
                }else if (response.body().getCode() == 2) {
                    T.s(context, "该车型已存在");
                }
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                L.e("getThirdLevelYear::" + t.getMessage());
            }
        });
    }

    /**
     * 选择品牌返回
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CarBrandActivity.GET_CAR_BRAND_REQUEST && resultCode == RESULT_OK && data != null) {
            carBrand = data.getParcelableExtra("brand");
            carBrandTwoLevel = data.getParcelableExtra("brandTwoLevel");
            carModel = data.getParcelableExtra("brandModel");
            if (carBrand != null && carBrandTwoLevel != null) {
                selectedBrandTxt.setText(carBrand.getB_Name() + " [" + carBrandTwoLevel.getB_Name() + "]");
                selectedYearModelTxt.setText(carModel.getModel());
            }
        }
    }

    @OnClick(R.id.m_title_left_btn)
    public void back(View v) {
        animFinish();
    }

    @Override
    public void backPressed() {
        animFinish();
    }
}
