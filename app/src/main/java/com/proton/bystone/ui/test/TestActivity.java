package com.proton.bystone.ui.test;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.LoginParams;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;
import com.proton.library.widget.CircleIndicator;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@MTFActivityFeature(layout = R.layout.activity_test)
public class TestActivity extends MTFBaseActivity {

    @Bind(R.id.list_view)
    ListView listView;

    CircleIndicator indicator;
    KeepFitAdapter keepFitAdapter;
    ViewPager viewPager;

    @Override
    public void initialize(Bundle savedInstanceState) {

        //header view
        View headerView = LayoutInflater.from(context).inflate(R.layout.maintain_list_header, null);
        listView.addHeaderView(headerView);
//        viewPager = (ViewPager) headerView.findViewById(R.id.home_view_pager);
//        indicator = (CircleIndicator) headerView.findViewById(R.id.view_pager_indicator);
//
//        viewPager.setAdapter(new MPagerAdapter());

        keepFitAdapter = new KeepFitAdapter();

        listView.addHeaderView(headerView);
        listView.setAdapter(keepFitAdapter);

        ////////////////////////////////////////////////////////////////////////////////////////
        LoginParams loginParams = new LoginParams("18225026634", "666888", "xxaabbc085412556sxxx", 1);
        RequestBody requestBody = new ParamsBuilder<LoginParams>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("Login")
                .gson(new Gson())
                .object(loginParams)
//                .typeValue("string", "189983288505")//只有typeValue时调用
//                .noParams()//无参数时调用
                .build();


        Call<BaseResp> call = HttpClients.getInstance().memberInfo(requestBody);

        //添加到请求队列，并开始请求数据
        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                System.out.println(">>>>>SUCCESS：：：" + response.body().toString());
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                System.out.println(">>>>>ERROR");
            }
        });
//        call.cancel();//取消数据请求

    }

    /**
     * Header pager adapter
     */
    class MPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 3;//list == null ? 0 : list.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView img = (ImageView) LayoutInflater.from(context).inflate(R.layout.item_single_img_layout, null);
//            String url = list.get(position).getContentUrl();
//            if (!TextUtils.isEmpty(url)) {
            Picasso.with(context).load("http://img0.bdstatic.com/img/image/imglogo-r.png").into(img);
//            img.setImageResource(R.drawable.temp_a);
//            }
            container.addView(img);
            return img;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
    @Override
    public void backPressed() {
        animFinish();
    }

    @OnClick(R.id.m_title_left_btn)
    public void leftClick(View v) {
        animFinish();
    }

    /**
     * 上门保养Adapter
     */
    class KeepFitAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 5;//keepfitList == null ? 0 : keepfitList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_maintain_keepfit, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            //bind data

            //return
            return convertView;
        }

        /**
         * holder
         * yiy
         */
        class ViewHolder {

            @Bind(R.id.item_maintain_keepfit_logo_img)
            ImageView logoImg;

            @Bind(R.id.item_maintain_keepfit_title_txt)
            TextView titleTxt;

            @Bind(R.id.item_maintain_keepfit_desc_txt)
            TextView descTxt;

            public ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
