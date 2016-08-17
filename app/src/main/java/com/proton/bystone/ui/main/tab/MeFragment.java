package com.proton.bystone.ui.main.tab;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.proton.bystone.R;
import com.proton.bystone.bean.LoginResp;
import com.proton.bystone.cache.LoginManager;
import com.proton.bystone.ui.common.MyCarActivity;
import com.proton.bystone.ui.login.LoginActivity;


import com.proton.bystone.ui.main.MainActivity;
import com.proton.bystone.ui.shop.My_Exit;
import com.proton.bystone.ui.shop.My_Indext;
import com.proton.bystone.ui.shop.My_Jinbi;
import com.proton.bystone.ui.shop.My_Privilege;
import com.proton.bystone.ui.shop.My_fk;
import com.proton.bystone.ui.shopcar.ShopCarActivity;
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

    @Bind(R.id.my_car)
    RelativeLayout my_car;
    @Bind(R.id.m_title_right_btn)
    TextView m_title_right_btn;
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

    @Bind(R.id.icon_mine_reddian)
    ImageView icon_mine_reddian;
    @Bind(R.id.shop_wd)
    TextView shop_wd;

    @Bind(R.id.icon_by)
    ImageView icon_by;
    @Bind(R.id.shop_wby)
    TextView shop_wby;

    @Bind(R.id.shop_dfk)
    ImageView shop_dfk;
    @Bind(R.id.shop_fk)
    TextView shop_fk;

    @Bind(R.id.shop_dsh)
    ImageView shop_dsh;
    @Bind(R.id.shop_ye)
    TextView shop_ye;

    @Bind(R.id.shop_pj)
    ImageView shop_pj;
    @Bind(R.id.shop_fg)
    TextView shop_fg;

    @Bind(R.id.my_exite)
    RelativeLayout my_exite;//点此可退出
    LoginResp loginInfo;
    String hp;
    String htt;
    String  shouhuo;
    String httq;
    String pj;

    boolean login;///aaaaaaa




    public MeFragment() {
        // Required empty public constructor

    }


    @Override
    public void onResume() {
        super.onResume();
      Refresh();
        login = LoginManager.getInstance().isLogin();
        loginInfo = LoginManager.getInstance().getLoginInfo();
        if(!login)
        {
            my_login.setVisibility(View.VISIBLE);
            my_humname.setVisibility(View.GONE);
            my_phone.setVisibility(View.GONE);
        }else{
            my_humname.setText(loginInfo.getMb_Name());
            my_phone.setText(loginInfo.getMb_LoginName());
            my_login.setVisibility(View.GONE);
            my_humname.setVisibility(View.VISIBLE);
            my_phone.setVisibility(View.VISIBLE);
        }

        SharedPreferences hhp = context.getSharedPreferences("hhp", 0);
        hp = hhp.getString("hp", "");
    htt = hhp.getString("htt", "");
       shouhuo = hhp.getString("shouhuo", "");
        httq = hhp.getString("httq", "");
        pj = hhp.getString("pj", "");

        if(!TextUtils.isEmpty(hp))
        {
            icon_mine_reddian.setVisibility(View.VISIBLE);
            shop_wd.setVisibility(View.VISIBLE);
            shop_wd.setText(hp + "");
        }

        if(!TextUtils.isEmpty(htt))
        {
            icon_by.setVisibility(View.VISIBLE);
            shop_wby.setVisibility(View.VISIBLE);
            shop_wby.setText(htt+ "");
        }

        if(!TextUtils.isEmpty(shouhuo))
        {
            shop_dsh.setVisibility(View.VISIBLE);
            shop_ye.setVisibility(View.VISIBLE);
            shop_ye.setText(shouhuo + "");
        }


        if(!TextUtils.isEmpty(httq))
        {
            shop_dfk.setVisibility(View.VISIBLE);
            shop_fk.setVisibility(View.VISIBLE);
            shop_fk.setText(httq + "");
        }

        if(!TextUtils.isEmpty(pj))
        {
            shop_pj.setVisibility(View.VISIBLE);
            shop_fg.setVisibility(View.VISIBLE);
            shop_fg.setText(pj + "");
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 9527) {
            //
            login = LoginManager.getInstance().isLogin();
            if(login) {
                loginInfo = LoginManager.getInstance().getLoginInfo();
                my_humname.setText(loginInfo.getMb_Name());
                my_phone.setText(loginInfo.getMb_LoginName());
                my_login.setVisibility(View.GONE);
                my_humname.setVisibility(View.VISIBLE);
                my_phone.setVisibility(View.VISIBLE);


                //跳转到退出页
                my_exite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent t = new Intent(getActivity(), My_Exit.class);
                        startActivity(t);
                    }
                });


            }
        }

    }




    @Override
    public void initialize() {


        Refresh();
         login = LoginManager.getInstance().isLogin();
        loginInfo = LoginManager.getInstance().getLoginInfo();

        if(login) {
            my_humname.setText(loginInfo.getMb_Name());
            my_phone.setText(loginInfo.getMb_LoginName());
            my_login.setVisibility(View.GONE);
            my_humname.setVisibility(View.VISIBLE);
            my_phone.setVisibility(View.VISIBLE);
        }

        my_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean login = LoginManager.getInstance().isLogin();
                if(!login) {
                    animStartForResult(9527, LoginActivity.class);
                }else{
                    placelogin();
                }

            }
        });

     //我的订单
        my_indent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = LoginManager.getInstance().isLogin();
                if(login) {
                    Intent t = new Intent(getActivity(), My_Indext.class);
                    t.putExtra("number", "0");
                    startActivity(t);
                }else{
                    placelogin();
                }
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
                login = LoginManager.getInstance().isLogin();
                if(login) {
                    Intent t = new Intent(getActivity(), My_Jinbi.class);
                    t.putExtra("mbcode", loginInfo.getMb_Code());
                    startActivity(t);
                }else{
                    placelogin();
                }
            }
        });

        //反馈
        my_fankui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = LoginManager.getInstance().isLogin();
                if(login) {
                    Intent t = new Intent(getActivity(), My_fk.class);
                    t.putExtra("mbcode", loginInfo.getMb_Code());
                    startActivity(t);
                }else{
                    placelogin();
                }
            }
        });

        //我的订单
        my_minefor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = LoginManager.getInstance().isLogin();
                if(login) {
                    Intent t = new Intent(getActivity(), My_Indext.class);
                    t.putExtra("number", "0");
                    startActivity(t);
                }else{
                    placelogin();
                }
            }
        });

        //待保养
        my_minefor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = LoginManager.getInstance().isLogin();
                if(login) {
                    Intent t = new Intent(getActivity(), My_Indext.class);
                    t.putExtra("number", "1");
                    startActivity(t);
                }else{
                    placelogin();
                }
            }
        });


        //待收货
        my_minefor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = LoginManager.getInstance().isLogin();
                if(login) {
                    Intent t = new Intent(getActivity(), My_Indext.class);
                    t.putExtra("number", "2");
                    startActivity(t);
                }else{
                    placelogin();
                }
            }
        });

        //待付款
        my_minefor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = LoginManager.getInstance().isLogin();
                if(login) {
                    Intent t = new Intent(getActivity(), My_Indext.class);
                    t.putExtra("number", "3");
                    startActivity(t);
                }else{
                    placelogin();
                }
            }
        });

        //待评价
        my_minefor5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = LoginManager.getInstance().isLogin();
                if(login) {
                    Intent t = new Intent(getActivity(), My_Indext.class);
                    t.putExtra("number", "4");
                    startActivity(t);
                }else{
                    placelogin();
                }
            }
        });


        //跳转到退出页
        my_exite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login) {
                    Intent t = new Intent(getActivity(), My_Exit.class);

                    startActivity(t);
                }else{
                    placelogin();
                }
            }
        });


        m_title_right_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = LoginManager.getInstance().isLogin();
                if(login) {
                    Intent t = new Intent(context, ShopCarActivity.class);
                    startActivity(t);
                }else{
                    placelogin();
                }
            }
        });

//爱车
        my_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login) {
                    Intent t = new Intent(context, MyCarActivity.class);
                    startActivity(t);
                }else{
                    placelogin();
                }
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


    public void placelogin()
    {
        Toast.makeText(context,"请登陆!",Toast.LENGTH_LONG).show();
    }


/*  public void setdata(String data)
  {
      Log.e("data65544554",data);
  }*/
}
