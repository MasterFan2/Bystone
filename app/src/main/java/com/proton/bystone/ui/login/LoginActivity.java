package com.proton.bystone.ui.login;
/**
 * 帐号密码登陆
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.proton.bystone.R;
import com.proton.bystone.bean.JsonResp;
import com.proton.bystone.bean.LoginParams;
import com.proton.bystone.bean.login;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.ui.main.MainActivity;
import com.proton.bystone.ui.main.tab.MeFragment;
import com.proton.bystone.ui.shop.Shop_Detail;
import com.proton.bystone.ui.shop.Shop_Ok;
import com.proton.bystone.utils.LoginUtil;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/6.
 * <p>
 * 网络测试成功可以用
 */
@MTFActivityFeature(layout = R.layout.activity_login)
public class LoginActivity extends MTFBaseActivity {

    @Bind(R.id.login_uname_edit)
    EditText nameEdit;

    @Bind(R.id.login_pwd_edit)
    EditText pwdEdit;

    @Bind(R.id.login_btn)
    Button loginEdit;

    @Bind(R.id.login_forget_txt)
    TextView forgetTxt;

    @Bind(R.id.login_modify_pwd_txt)
    TextView modifyPwdTxt;

    private DbManager db;

    @Override
    public void initialize(Bundle savedInstanceState) {
        db = x.getDb(MDbUtils.getDaoConfig());

    }

    /**
     * 登录检查
     */
    @OnClick(R.id.login_btn)
    public void checkLogin() {
        String uname = nameEdit.getText().toString();
        String pwd = pwdEdit.getText().toString();

        if (TextUtils.isEmpty(uname)) {
            T.s(context, "请输入手机号");
            return;
        }
        if (!MatcherUtil.isPhone(uname)) {
            T.s(context, "手机号格式错误,请检查");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            T.s(context, "请输入密码");
            return;
        }

        login(uname, pwd);
    }

    /**
     * 登录
     *
     * @param name
     * @param pwd
     */
    private void login(String name, String pwd) {
        LoginParams loginParams = new LoginParams(name, pwd, "xxaabbc085412556sxx", 1);
        final RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("Login")
                .gson(new Gson())
                .object(loginParams)
                .build();

        Call<JsonResp> call = HttpClients.getInstance().login(requestBody);
        call.enqueue(new Callback<JsonResp>() {
            @Override
            public void onResponse(Call<JsonResp> call, Response<JsonResp> response) {

                if (response.body().getCode() == 1) {///登录成功
                    LoginResp loginResp = new Gson().fromJson(response.body().getData(), new TypeToken<LoginResp>() {}.getType());
                    LoginManager.getInstance().cacheLogin(loginResp);
                    T.s(context, "登录成功");
                    setResult(RESULT_OK);
                    animFinish();
                } else {
                    L.e("登录失败");
                }
            }

            @Override
            public void onFailure(Call<JsonResp> call, Throwable t) {
                L.e("登录失败");
            }
        });
    }

    @Override
    public void backPressed() {
        setResult(RESULT_CANCELED);
        animFinish();
    }

    @OnClick(R.id.m_title_left_btn)
    public void back(View view) {
        setResult(RESULT_CANCELED);
        animFinish();
    }
}
