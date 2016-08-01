package com.proton.bystone.ui.shop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BitmapUtils;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.Shop_Fenlei;
import com.proton.bystone.bean.Shop_List;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;
import com.proton.library.ui.annotation.MTFFragmentFeature;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/27.
 * 商品分类
 */
@MTFActivityFeature(layout = R.layout.shop_all_fenlei)
public class Shop_Sort extends MTFBaseActivity{
    String ps_code;
    List<Shop_Fenlei>  listfor;
    ArrayList<String> list;
    ArrayList<String> listImg_Pic;
    BitmapUtils    utils2;

    @Bind(R.id.shop_youpin)
    RelativeLayout shop_youpin;

    @Bind(R.id.shop_xj)
    LinearLayout shop_xj;

    @Bind(R.id.shop_you)
    ListView shop_you;
    boolean flag=false;
    @Override
    public void initialize(Bundle savedInstanceState) {

        Category_name();

        Listener();
    }

    public void Listener()
    {
        shop_youpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flag==false) {
                    shop_xj.setVisibility(View.VISIBLE);
                    Category_name();
                    flag = true;
                }else{
                    shop_xj.setVisibility(View.GONE);
                    flag = false;
                }
            }
        });


        shop_you.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String   vccode = listfor.get(position).getPs_CODE();
                Intent t= new Intent(Shop_Sort.this,Shop_Detail.class);
                t.putExtra("vccode",vccode);
                startActivity(t);
            }
        });


    }

    @Override
    public void backPressed() {
    animFinish();
    }

    //商品列表
    public void Category_name() {
        // LoginParams loginParams = new LoginParams(ed.getText().toString().trim(), "666888", "xxaabbc085412556sxxx", 1);

        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetMoreCategories")
                .gson(new Gson())
                .noParams()
                // .object(loginParams)
//                .typeValue("string", ps_code)

                .build();
//        ParamsBuilder<LoginParams> builder = new ParamsBuilder<LoginParams>().
//                .key("")
//                .build();
        //分类名称
        Call<BaseResp> call = HttpClients.getInstance().commodity(requestBody);

        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {


                String data = response.body().getData();
                htpjiexi(data);
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                Log.e("111", "失败");
            }
        });
    }

    //解析
    public void htpjiexi(String data)
    {
        listfor=new Gson().fromJson(data, new TypeToken<List<Shop_Fenlei>>() {}.getType());
        shop_you.setAdapter(new GridViewadapter2());
    }

    class GridViewadapter2 extends BaseAdapter
    {

        public GridViewadapter2()
        {
          utils2 = new BitmapUtils(Shop_Sort.this);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub

            return listfor.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return listfor.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            viewHolder1 holder;
            if (convertView == null) {
                convertView = View.inflate(Shop_Sort.this, R.layout.new_bj, null);//标题
                holder = new viewHolder1();
                holder.shop_tb = (TextView) convertView.findViewById(R.id.shop_tb);
//                holder.image2 = (ImageView) convertView.findViewById(R.id.shop_tp);
                convertView.setTag(holder);
            }else{
                holder = (viewHolder1) convertView.getTag();
            }

            holder.shop_tb.setText(listfor.get(position).getPs_NAME());




            return convertView;
        }
    }

    class viewHolder1
    {

        TextView shop_tb;
        ImageView image2;

    }



}
