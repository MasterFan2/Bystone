package com.proton.bystone.ui.shop;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.Category_nameResp;
import com.proton.bystone.bean.Duanxin;
import com.proton.bystone.bean.Jbnum;
import com.proton.bystone.bean.JsonResp;
import com.proton.bystone.bean.Mb_code;
import com.proton.bystone.bean.Modified_Pwd;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import java.util.List;

import butterknife.Bind;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/27.
 * 点击金币
 *
 */

@MTFActivityFeature(layout = R.layout.my_jinbi)
public class My_Jinbi extends MTFBaseActivity {

    @Bind(R.id.my_jb)
    TextView but;

    @Bind(R.id.my_bl)
    TextView my_bl;

    @Bind(R.id.my_mi)
    TextView my_mi;

    @Bind(R.id.my_tc)
    TextView my_tc;

    String mb_code;
    @Override
    public void initialize(Bundle savedInstanceState) {
         data2();
    }

    @Override
    public void backPressed() {
   animFinish();
    }

   //获取数据

   public void data2()
   {
       String mbcode = getIntent().getStringExtra("mbcode");

       // Modified_Pwd pwd=new Modified_Pwd(et2.getText().toString().trim(),et3.getText().toString().trim());
      // Mb_Code  mb=new Mb_Code(mb_code);
       RequestBody requestBody = new ParamsBuilder<>()
               .key("pbevyvHkf1sFtyGL35gFfQ==")
               .methodName("GetIntegral")
               .gson(new Gson())
                /*.noParams()*/
               //.object(pwd)
            .typeValue("string",/*mb_code*/"201605261656057265")//会员积分金币数
               /* .typeValue("string","958496")
                .typeValue("int",2)*/
               .build();
//        ParamsBuilder<LoginParams> builder = new ParamsBuilder<LoginParams>().
//                .key("")
//                .build();

       Call<BaseResp> call = HttpClients.getInstance().memberInfo(requestBody);

       call.enqueue(new Callback<BaseResp>() {
           @Override
           public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
               String data = response.body().getData();
               Log.e("123456",data);
              jiexi2(data);


           }

           @Override
           public void onFailure(Call<BaseResp> call, Throwable t) {
               Log.e("111","失败");
           }
       });


   }

    public void jiexi2(String Data)
    {


      List<Jbnum>  jbnum=new Gson().fromJson(Data, new TypeToken<List<Jbnum>>() {}.getType());
        String code = jbnum.get(0).getGoldBalance();//拿到金币数量
        int InviteFriendsGold = jbnum.get(0).getInviteFriendsGold();//邀请好友祖册所得
        int ConsumPtionGold = jbnum.get(0).getConsumPtionGold();//好友消费提成
        String Exceed = jbnum.get(0).getExceed();//超过车友比例

        but.setText(code);
        my_bl.setText(Exceed);
        my_mi.setText(InviteFriendsGold+"");
        my_tc.setText(ConsumPtionGold+"");
    }
    @butterknife.OnClick(R.id.m_title_left_btn)
    public void back(View view) {
        animFinish();
    }

}
