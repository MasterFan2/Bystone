package com.proton.bystone.ui.login;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.proton.bystone.R;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

/**
 * 设置新密码
 */

/**
 * Created by Administrator on 2016/7/8.
 */
@MTFActivityFeature(layout = R.layout.fragment_newpwd)
public class NewpwdActivity extends MTFBaseActivity {
    EditText et2;
    EditText et3;
    Button bt3;


    @Override
    public void initialize(Bundle savedInstanceState) {
        initview();
        Listenet();

    }

    @Override
    public void backPressed() {
        animFinish();
    }

    private void initview() {
        //设置新密码
        et2 = (EditText)findViewById(R.id.et2);
        //重新再输入一遍
        et3 = (EditText)findViewById(R.id.et3);
        //完成
        bt3 = (Button)findViewById(R.id.bt3);

    }

    private void Listenet() {

        et3.addTextChangedListener(new TextWatcher(){

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {



            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (et3.getText().toString().trim().equals("") ) {
                    bt3.setBackground(NewpwdActivity.this.getResources().getDrawable(R.drawable.yuanjiao2));

                } else {
                    bt3.setBackground(NewpwdActivity.this.getResources().getDrawable(R.drawable.yuanjiao));
                }

            }

        });
    }


}
