package com.proton.bystone.ui.shopcar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.CarModel;
import com.proton.bystone.bean.Commodity;
import com.proton.bystone.bean.DataBean;
import com.proton.bystone.bean.LoginParams;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.utils.L;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 购物车
 */
@MTFActivityFeature(layout = R.layout.activity_shop_car)
public class ShopCarActivity extends MTFBaseActivity {

    @Bind(R.id.shop_car_list_view)
    ListView listView;
    private ShopCarAdapter adapter;

    @Bind(R.id.shopping_car_cbx)
    CheckBox allCkb;

    @Bind(R.id.shopping_car_pay_btn)
    Button payBtn;

    @Bind(R.id.shopping_car_all_price_txt)
    TextView allPriceTxt;

    @Override
    public void initialize(Bundle savedInstanceState) {
        getData();
    }

    /**
     * 加载数据
     */
    private void getData() {
        adapter = new ShopCarAdapter(MyShoppingCar.getShoppingCar().getCommodities());
        listView.setAdapter(adapter);
    }

    class ShopCarAdapter extends BaseAdapter {

        ArrayList<Commodity> data;

        public ShopCarAdapter(ArrayList<Commodity> commodityList){
            this.data = commodityList;
        }

        @Override
        public int getCount() {
            return 3;//data == null ? 0 : data.size();
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
//            final Commodity commodity = data.get(position);
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_shop_car, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            //bind data


            //return
            return convertView;
        }

        class ViewHolder  {

            @Bind(R.id.item_shop_car_header_layout)
            LinearLayout headerLayout;

            @Bind(R.id.item_shop_car_check_view)
            View chkView;

            @Bind(R.id.item_shop_car_title_txt)
            TextView titleTxt;

            @Bind(R.id.item_shop_car_logo_img)
            ImageView logoImg;

            @Bind(R.id.item_shop_car_head_txt)
            TextView headTxt;

            @Bind(R.id.item_shop_car_desc_txt)
            TextView descTxt;

            @Bind(R.id.item_shop_car_price_txt)
            TextView priceTxt;

            @Bind(R.id.item_shop_car_count_txt)
            TextView countTxt;

            public ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
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
