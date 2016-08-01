package com.proton.bystone.ui.maintenance;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.utils.L;
import com.proton.bystone.utils.T;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import org.json.JSONArray;
import org.json.JSONException;

import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@MTFActivityFeature(layout = R.layout.activity_maintenance_picture)
public class MaintenancePictureActivity extends MTFBaseActivity {

    private String code;

    @Override
    public void initialize(Bundle savedInstanceState) {
        code = getIntent().getStringExtra("code");
        getData();
    }

    private void getData() {
        //提交预定信息
        final RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetMaintenanceImg")
                .gson(new Gson())
                .typeValue("string", code)
                .build();
        Call<BaseResp> call = HttpClients.getInstance().maintenance(requestBody);
        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
//                List<CarModel> carModels = new Gson().fromJson(response.body().getData(), new TypeToken<List<CarModel>>() {}.getType());
                if (response.body().getCode() == 1) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().getData());
//                        orderBaseInfos   = new Gson().fromJson(jsonArray.get(0).toString(), new TypeToken<List<OrderBaseInfo>>() {}.getType());
//                        orderStateInfos = new Gson().fromJson(jsonArray.get(1).toString(), new TypeToken<List<OrderStateInfo>>() {}.getType());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    T.s(context, "预约成功");
                }else {
                    T.s(context, "预约失败");
                }
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                L.e("getThirdLevelYear::" + t.getMessage());
            }
        });
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
