package com.proton.bystone.ui.common;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.CarInfo;
import com.proton.bystone.bean.CarVerifyInfo;
import com.proton.bystone.bean.ReservationParam;
import com.proton.bystone.bean.UpdateMyCarParams;
import com.proton.bystone.config.Config;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.utils.L;
import com.proton.bystone.utils.S;
import com.proton.bystone.utils.T;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 我的爱车
 */
@MTFActivityFeature(layout = R.layout.activity_my_car)
public class MyCarActivity extends MTFBaseActivity {

    /**标识从维保跳转过来*/
    public final static String WHERE_FRAGMENT_MAINTENANCE_HOME = "fragment_maintenance";

    @Bind(R.id.my_car_list_view)
    ListView listView;
    private MyAdapter adapter;

    @Bind(R.id.my_car_verify_tips_txt)
    TextView verifyTxt;

    @Bind(R.id.my_car_no_data_layout)
    LinearLayout nodataLayout;

    private String where = "";//标识从什么地方跳转过来的

    ///列表数据
    private List<CarInfo>       data       = null;
    private List<CarVerifyInfo> verifyList = null;//审核信息
    private CarInfo defaultServerCarInfo   = null;//获取服务器端 的默认车辆
    private CarInfo chooseCurrentCarInfo   = null;//选择的车辆

    @Override
    public void initialize(Bundle savedInstanceState) {

        where = getIntent().getStringExtra("where");

        adapter = new MyAdapter();
        listView.setAdapter(adapter);

        //获取车辆信息
        final RequestBody requestBody = new ParamsBuilder<ReservationParam>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("MyCarList")
                .gson(new Gson())
                .typeValue("string", Config.USER_CODE)
                .build();
        Call<BaseResp> call = HttpClients.getInstance().memberInfo(requestBody);
        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                if (response.body().getCode() == 1) {
                    try {
                        JSONArray jsonArr = new JSONArray(response.body().getData());
                        data       = new Gson().fromJson(jsonArr.get(0).toString(), new TypeToken<List<CarInfo>>() {}.getType());

                        if (data == null || data.size() <= 0) {
                            nodataLayout.setVisibility(View.VISIBLE);
                            listView    .setVisibility(View.GONE);
                        }else  {
                            nodataLayout.setVisibility(View.GONE);
                            listView    .setVisibility(View.VISIBLE);
                        }
                        verifyList = new Gson().fromJson(jsonArr.get(1).toString(), new TypeToken<List<CarVerifyInfo>>() {}.getType());

                        ///设置提法审核的文字
                        if (verifyList != null && verifyList.size() > 0) {
                            verifyTxt.setText(verifyList.get(0).getAuditRemind());
                            verifyTxt.setVisibility(View.VISIBLE);
                        } else {
                            verifyTxt.setVisibility(View.GONE);
                        }

                        for (CarInfo carInfo : data) {
                            if (carInfo.getIsDefault() == 1) {
                                defaultServerCarInfo = carInfo;
                                break;
                            }
                        }

                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    T.s(context, "获取车辆信息错误");
                }
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                L.e("getThirdLevelYear::" + t.getMessage());
            }
        });
    }

    @OnItemClick(R.id.my_car_list_view)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    /**
     * 删除车辆信息
     *
     * @param carInfo
     */
    private void delMyCarInfo(final CarInfo carInfo) {
        if (carInfo == null) return;
        //提交预定信息
        final RequestBody requestBody = new ParamsBuilder<ReservationParam>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("RemoveCar")
                .gson(new Gson())
                .typeValue("int", carInfo.getID())
                .build();
        Call<BaseResp> call = HttpClients.getInstance().memberInfo(requestBody);
        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                if (response.body().getCode() == 1) {
                    T.s(context, "删除车辆信息成功");
                    data.remove(carInfo);
                    adapter.notifyDataSetChanged();
                } else {
                    T.s(context, "删除车辆信息错误");
                }
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                L.e("getThirdLevelYear::" + t.getMessage());
            }
        });
    }

    /**
     * 设置车辆为默认
     *
     * @param carInfo
     */
    private void defaultMyCarInfo(final CarInfo carInfo) {
        if (carInfo == null) return;

        UpdateMyCarParams updateMyCarParams = new UpdateMyCarParams(carInfo.getID(), carInfo.getUser_Code(), carInfo.getI_CarDetail(), carInfo.getVC_CarNO(), carInfo.getVC_CarFDJ(), "1", carInfo.getCarIdentifier());

        ///更新车辆信息
        final RequestBody requestBody = new ParamsBuilder<UpdateMyCarParams>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("UpdateCar")
                .gson(new Gson())
                .object(updateMyCarParams)
                .build();
        Call<BaseResp> call = HttpClients.getInstance().memberInfo(requestBody);
        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                if (response.body().getCode() == 1) {
                    S.o("设置默认车辆成功");
                    data.remove(carInfo);
                    adapter.notifyDataSetChanged();
                } else {
                    S.o("设置默认车辆失败");
                }
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                L.e("getThirdLevelYear::" + t.getMessage());
            }
        });
    }

    /**
     * ListView adapter
     */
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            final CarInfo carInfo = data.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_my_car, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //bind data
            holder.nameTxt.setText(carInfo.getB_Name());
            holder.idNumTxt.setText(carInfo.getVC_CarNO());

            if (carInfo.getCountdown() <= 7) {
                holder.redTipsTxt.setVisibility(View.VISIBLE);
                holder.redTipsTxt.setText("离保养时间还有"+carInfo.getCountdown() + "天！");
            }else {
                holder.redTipsTxt.setVisibility(View.INVISIBLE);
            }

            if (carInfo.getIsDefault() == 1) {//选中
                holder.checkedView.setBackgroundResource(R.mipmap.icon_radio_blue_sel);
            } else {
                holder.checkedView.setBackgroundResource(R.mipmap.icon_radio_blue_nor);
            }

            //选中和非选中效果
            holder.bottomLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (CarInfo tempCarInfo : data) {
                        tempCarInfo.setIsDefault(0);
                    }
                    carInfo.setIsDefault(1);
                    chooseCurrentCarInfo = carInfo;
                    notifyDataSetChanged();
                }
            });

            //维保首页
            if (where != null && where.equals(WHERE_FRAGMENT_MAINTENANCE_HOME)) {
                holder.goMaintanenceBtn.setText("确定");
                holder.goMaintanenceBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("carInfo", carInfo);
                        setResult(200, intent);
                        animFinish();
                    }
                });
            }

            //删除车辆
            holder.delTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new AlertDialog.Builder(context)
                            .setTitle("提示您")
                            .setMessage("车辆信息删除后不能恢复, 确认删除？")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    delMyCarInfo(carInfo);
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                }
            });

            //跳详情
            holder.contentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CarInfo carInfo = data.get(position);
                    Intent intent = new Intent(context, MyCarDetailActivity.class);
                    intent.putExtra("carInfo", carInfo);
                    animStart(intent);
                }
            });

            Picasso.with(context).load(HttpClients.PIC_URL + carInfo.getB_logo()).placeholder(R.mipmap.ic_launcher).into(holder.logoImg);

            //return
            return convertView;
        }

        class ViewHolder {

            @Bind(R.id.item_my_car_content_layout)
            LinearLayout contentLayout;

            @Bind(R.id.item_my_car_name_txt)
            TextView nameTxt;

            @Bind(R.id.item_my_car_id_num_txt)
            TextView idNumTxt;

            @Bind(R.id.item_my_car_red_tips_txt)
            TextView redTipsTxt;

            @Bind(R.id.item_my_car_rb_view)
            View checkedView;

            @Bind(R.id.item_my_car_do_maintenance_btn)
            Button goMaintanenceBtn;

            @Bind(R.id.item_my_car_del_txt)
            TextView delTxt;

            @Bind(R.id.item_my_car_logo_img)
            ImageView logoImg;

            @Bind(R.id.item_my_car_bottom_layout)
            RelativeLayout bottomLayout;

            public ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }



    /**
     * 检查服务端默认车辆和选择的车辆是否一致， 不一致则上传新选择的默认车辆
     */
    private void checkDefault() {
        if (defaultServerCarInfo != null &&  chooseCurrentCarInfo != null && defaultServerCarInfo.getID() != chooseCurrentCarInfo.getID()) {
            defaultMyCarInfo(chooseCurrentCarInfo);
        }
    }

    /**
     * 跳转到添加爱车界面
     * @param v
     */
    @OnClick(R.id.my_car_add_my_car_btn)
    public void addMyCarBtnClick(View v) {
        T.s(context, "添加爱车");
    }

    @OnClick(R.id.m_title_right_btn)
    public void rightBtnClick(View view) {
        animStart(AddMyCarActivity.class);
    }

    @Override
    public void backPressed() {
        checkDefault();
        animFinish();
    }

    @OnClick(R.id.m_title_left_btn)
    public void back(View view) {
        checkDefault();
        animFinish();
    }
}
