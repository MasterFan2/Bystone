package com.proton.bystone.ui.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.proton.bystone.R;
import com.proton.bystone.cache.LoginManager;
import com.proton.bystone.ui.login.NewpwdActivity;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/7/27.
 * 可退出界面
 */

@MTFActivityFeature(layout = R.layout.my_exit)
public class My_Exit extends MTFBaseActivity {
    @Bind(R.id.my_exit)
    Button my_exit;

//    @Bind(R.id.my_picture)
//    Button my_picture;//点头像换照片

    @Bind(R.id.shop_setting)
    RelativeLayout shop_setting;
    @Override
    public void initialize(Bundle savedInstanceState) {
        my_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

        shop_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent t=new Intent(My_Exit.this, NewpwdActivity.class);
                startActivity(t);
            }
        });

        my_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().delLoginInfo();
                finish();
            }
        });

      /*  my_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                INTENT_ACTION_PICK();
            }
        });*/
    }

 /*   public static Intent INTENT_ACTION_PICK() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image*//*");
        return intent;
    }*/

    @Override
    public void backPressed() {
       animFinish();
    }
}
