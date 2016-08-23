package com.proton.bystone.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.ReservationParam;
import com.proton.bystone.bean.Version;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.ui.main.MainActivity;
import com.proton.bystone.utils.L;
import com.proton.bystone.utils.PackageUtil;
import com.proton.bystone.utils.T;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;
import com.proton.library.widget.tiles.TilesFrameLayout;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@MTFActivityFeature(layout = R.layout.activity_launch)
public class LaunchActivity extends MTFBaseActivity {

    @Override
    public void initialize(Bundle savedInstanceState) {
        new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        checkVersion();
                        break;
                }
            }
        }.sendEmptyMessageDelayed(0, 1500);
    }

    @Override
    public void backPressed() {

    }

    /**
     * 显示升级提示框
     * @param version
     */
    private void showDialog(Version version) {
        Intent intent = new Intent(context, UpVersionActivity.class);
        intent.putExtra("version", version);
        animStartForResult(intent, 9527);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 9527) {
            if (resultCode == UpVersionActivity.FORCE_UPDATE_CANCEL){
                //必需强制更新， 用户取消更新， 程序不可用。 退出程序
                T.s(context, "版本过旧，程序退出");
                animFinish();
            } else {
                animStart(MainActivity.class);
                finish();
            }
        }
    }

    /**
     * 检查版本更新， 暂时没检查
     */
    private void checkVersion() {
        animStart(MainActivity.class);
        finish();

        //-------------------split line---------------------

//        final RequestBody requestBody = new ParamsBuilder<ReservationParam>()
//                .key("pbevyvHkf1sFtyGL35gFfQ==")
//                .methodName("GetAppTheLatestVersion")
//                .gson(new Gson())
//                .typeValue("int", 1)
//                .typeValue("string", PackageUtil.getVersionName(context))
//                .typeValue("string", "车事通用户版")
//                .build();
//        Call<BaseResp> call = HttpClients.getInstance().memberInfo(requestBody);
//        call.enqueue(new Callback<BaseResp>() {
//            @Override
//            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
//                if (response.body().getCode() == 1) {
//                    Version versionInfo = new Gson().fromJson(response.body().getData(), new TypeToken<Version>() {}.getType());
//                    showDialog(versionInfo);
//                } else {
//                    animStart(MainActivity.class);
//                    finish();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<BaseResp> call, Throwable t) {
//                L.e("checkVersion::" + t.getMessage());
//            }
//        });
    }
}
