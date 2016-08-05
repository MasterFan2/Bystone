package com.proton.bystone.ui.main.tab;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.proton.bystone.R;
import com.proton.bystone.bean.LoginResp;
import com.proton.bystone.cache.LoginManager;
import com.proton.bystone.ui.login.LoginActivity;


import com.proton.bystone.ui.shop.My_Exit;
import com.proton.bystone.ui.shop.My_Indext;
import com.proton.bystone.ui.shop.My_Jinbi;
import com.proton.bystone.ui.shop.My_Privilege;
import com.proton.bystone.ui.shop.My_fk;
import com.proton.library.ui.MTFBaseFragment;
import com.proton.library.ui.annotation.MTFFragmentFeature;

/*import org.apache.http.entity.StringEntity;*/

import java.util.ArrayList;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MeFragment#newInstance} factory method to
 * create an instance of this fragment.
 * 我的主页
 */
@MTFFragmentFeature(layout = R.layout.wodezhu)
public class MeFragment extends MTFBaseFragment {
   @Bind(R.id.my_login)
    TextView my_login;
    @Bind(R.id.my_humname)
    TextView my_humname;
    @Bind(R.id.my_phone)
    TextView my_phone;
   @Bind(R.id.my_indent)
   TextView my_indent;//我的订单
   @Bind(R.id.my_privilege)
   RelativeLayout my_privilege;//优惠套餐
    @Bind(R.id.my_jinbi)
    RelativeLayout my_jinbi;//金币
    @Bind(R.id.my_fankui)
    RelativeLayout my_fankui;//反馈
    ArrayList<String> list;

    @Bind(R.id.my_minefor)
    ImageView my_minefor;//我的订单
    @Bind(R.id.my_minefor2)
    ImageView my_minefor2;//待保养
    @Bind(R.id.my_minefor3)
    ImageView my_minefor3;//待收货
    @Bind(R.id.my_minefor4)
    ImageView my_minefor4;//待付款
    @Bind(R.id.my_minefor5)
    ImageView my_minefor5;//待评价

    @Bind(R.id.my_exite)
    RelativeLayout my_exite;//点此可退出

    public MeFragment() {
        // Required empty public constructor

    }


    @Override
    public void onResume() {
        super.onResume();
      Refresh();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 9527) {
            //
            boolean login = LoginManager.getInstance().isLogin();
            if(login) {
                LoginResp loginInfo = LoginManager.getInstance().getLoginInfo();
                my_humname.setText(loginInfo.getMb_Name());
                my_phone.setText(loginInfo.getMb_LoginName());
                my_login.setVisibility(View.GONE);
                my_humname.setVisibility(View.VISIBLE);
                my_phone.setVisibility(View.VISIBLE);

            }

        }
    }

    @Override
    public void initialize() {

      Refresh();
        boolean login = LoginManager.getInstance().isLogin();
        LoginResp loginInfo = LoginManager.getInstance().getLoginInfo();
        my_humname.setText(loginInfo.getMb_Name());
        my_phone.setText(loginInfo.getMb_LoginName());
        my_login.setVisibility(View.GONE);
        my_humname.setVisibility(View.VISIBLE);
        my_phone.setVisibility(View.VISIBLE);



    my_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean login = LoginManager.getInstance().isLogin();
                if(!login) {
                    animStartForResult(9527, LoginActivity.class);
                }

            }
        });

     //我的订单
        my_indent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(getActivity(), My_Indext.class);
                t.putExtra("number","0");
                startActivity(t);
            }
        });
        //Y优惠套餐
        my_privilege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到活动优惠
                Intent t = new Intent(getActivity(), My_Privilege.class);
                startActivity(t);
            }
        });

        //我的金币
        my_jinbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到活动优惠
                Intent t = new Intent(getActivity(), My_Jinbi.class);
                startActivity(t);
            }
        });

        //反馈
        my_fankui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent t = new Intent(getActivity(), My_fk.class);
                startActivity(t);
            }
        });

        //我的订单
        my_minefor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(getActivity(), My_Indext.class);
                t.putExtra("number","0");
                startActivity(t);
            }
        });

        //待保养
        my_minefor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(getActivity(), My_Indext.class);
                t.putExtra("number","1");
                startActivity(t);
            }
        });


        //待收货
        my_minefor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(getActivity(), My_Indext.class);
                t.putExtra("number","2");
                startActivity(t);
            }
        });

        //待付款
        my_minefor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(getActivity(), My_Indext.class);
                t.putExtra("number","3");
                startActivity(t);
            }
        });

        //待评价
        my_minefor5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(getActivity(), My_Indext.class);
                t.putExtra("number","4");
                startActivity(t);
            }
        });

//跳转到退出页
        my_exite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(getActivity(), My_Exit.class);

                startActivity(t);
            }
        });





    }

    @Override
    public void load() {

    }


    public static MeFragment newInstance(Bundle args) {
       MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public String LoginSendCodes(String phonenumber)
    {
        return null;
    }


 public void Refresh()
   {
       SharedPreferences test = getActivity().getSharedPreferences("NameAndPhone",
               Activity.MODE_PRIVATE);
       String biaoji = test.getString("biaoji", "");
       String name = test.getString("name", "");
       String phone = test.getString("phone", "");

       //接受到了数据
       if(biaoji.equals("11"))
       {
           my_login.setVisibility(View.GONE);
           my_humname.setVisibility(View.VISIBLE);
           my_humname.setText(name);
           my_phone.setVisibility(View.VISIBLE);
           my_phone.setText(phone);
       }


       my_login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent t = new Intent(getActivity(), LoginActivity.class);
               t.putExtra("landing", "mya");
               startActivity(t);

           }
       });

   }



}
