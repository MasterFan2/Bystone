package com.proton.bystone.ui.main.tab;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.proton.bystone.R;
import com.proton.bystone.bean.Bean_a;
import com.proton.bystone.bean.Bean_b;
import com.proton.bystone.bean.Bean_c;
import com.proton.bystone.bean.Category_nameResp;
import com.proton.bystone.bean.DataBean;
import com.proton.bystone.bean.JsonResp;
import com.proton.bystone.bean.EventResp;
import com.proton.bystone.bean.Fist;
import com.proton.bystone.location.LocationManager;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.ui.login.SendCallBack;
import com.proton.bystone.ui.main.tab.home.Homeserch;
import com.proton.bystone.ui.main.tab.home.Search_service;
import com.proton.bystone.ui.shop.Shop_Commodity;
import com.proton.bystone.ui.shop.Shop_Detail;
import com.proton.bystone.ui.shop.Shop_Sort;
import com.proton.bystone.ui.view.RefreshListView;
import com.proton.library.ui.MTFBaseFragment;
import com.proton.library.ui.annotation.MTFFragmentFeature;
import com.proton.library.zxing.activity.CaptureActivity;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

@MTFFragmentFeature(layout = R.layout.fragment_shop)
public class ShopFragment extends MTFBaseFragment {
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ShopFragment.
     */
    // TODO: Rename and change types and number of parameters
   /* ArrayList<TextView>  list3;*/

    @Bind(R.id.shop_listview)
    ListView listview;

    @Bind(R.id.home_btn)
    Button home_btn;//黄泥磅

    @Bind(R.id.home_search)
    EditText home_search;//搜索
    String s;
    ViewPager  viewpager;
    String data;
    String productId;
    List<DataBean> database;
    Button but;
    List<Category_nameResp>  listfor;
    List<EventResp>  listevent;
    List<Bean_a> list_a = new ArrayList<>();
    List<Bean_b> list_b = new ArrayList<>();
    List<Bean_c> list_c = new ArrayList<>();

    List<String> list = new ArrayList<String>();
    List<String> list2 = new ArrayList<String>();
    ArrayList<String>  lis;
    ArrayList<String>  lis2;
    List<String> one = new ArrayList<String>();
    List<String> two = new ArrayList<String>();
    List<String> three = new ArrayList<String>();
    ImageView image;
    BitmapUtils utils3;
    BitmapUtils utils2;
    BitmapUtils utils;
    GridView gridView;
    GridView gridView2;
    String vccode;

    String ps_code;
    Intent t;
    @Bind(R.id.search_header_search_layout)
    LinearLayout searchLayout;
    @Bind(R.id.search_header_scan_img)
    ImageView scanImg;
    @Bind(R.id.search_header_city_txt)
    TextView cityTxt;



    public static ShopFragment newInstance(Bundle args) {
        ShopFragment fragment = new ShopFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initialize() {

        // 加载头布局
        View headerView = View.inflate(ShopFragment.this.getActivity(), R.layout.first_layout2,
                null);
        viewpager = (ViewPager) headerView.findViewById(R.id.shop_vPager);
        //横向gridView
        gridView=(GridView)headerView.findViewById(R.id.shop_gridView);
        //纵向gridView
        gridView2=(GridView)headerView.findViewById(R.id.shop_gridView2);


        viewpa();
        Category_name();
        event();
        listview.addHeaderView(headerView);

        gridView.setAdapter(new GridViewadapter());
        gridView2.setAdapter(new GridViewadapter2());

        listview.setAdapter(new JuleBuhomeadapter2() );
       Listener();
        //listview刷新的监听

        viewpa();
/*
      listview.setOnRefreshListener(new RefreshListView.OnRefreshListener() {

            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub

                *//**//*//**//*//*收起下拉刷新
                new Handler().postDelayed(new Runnable(){

                            public void run() {

                        //execute the task
                        listview.onRefreshComplete(true);
                    }

                }, 3000);

            }

            @Override
            public void onLoadMore() {
                // TODO Auto-generated method stub
                //加载下一页数据
                new Handler().postDelayed(new Runnable(){

                        public void run() {

                            viewpa();
                          //  listview.onRefreshComplete(true);

                        }

                    }, 3000);// 收起加载更多的布局
                }
          });*/
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                // TODO Auto-generated method stub
                vccode = database.get(position).getVC_Code();
                Intent t= new Intent(getActivity(),Shop_Detail.class);

//                Bundle mBundle = new Bundle();
//                mBundle.putString("list",listevent+"");
               t.putExtra("vccode",vccode);//商品编号
//                mBundle.putString("vccode",vccode);
//                t.putExtras(mBundle);
                startActivity(t);

            }

        });




/*        //黄泥磅
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animStartForResult(1000,Homeserch.class);
            }
        });
        //搜索
        home_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animStartForResult(1000,Search_service.class);
            }
        });*/


        //黄泥磅
        cityTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animStartForResult(1000,Homeserch.class);
                dingwei();

            }
        });

        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animStartForResult(1000,Search_service.class);

            }
        });



    }

    @Override
    public void load() {

    }

    public void Listener()
    {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position)
                {
                    case 0:
                        //拿到商品编号
                         ps_code = listfor.get(position).getPs_CODE();
                          t=new Intent(getActivity(),Shop_Commodity.class);
                        t.putExtra("ps_code",ps_code);
                        startActivity(t);
                        break;
                    case 1:
                        //拿到商品编号
                        ps_code = listfor.get(position).getPs_CODE();
                        t=new Intent(getActivity(),Shop_Commodity.class);
                        t.putExtra("ps_code",ps_code);
                        startActivity(t);
                        break;
                    case 2:
                        ps_code = listfor.get(position).getPs_CODE();
                        t=new Intent(getActivity(),Shop_Commodity.class);
                        t.putExtra("ps_code",ps_code);
                        startActivity(t);
                        break;
                    case 3:
                        //跳转到商品分类
                        t=new Intent(getActivity(),Shop_Sort.class);
                        startActivity(t);
                        break;
                }


            }
        });
    }

    public void viewpa()
    {
        JsonDemo bean = new JsonDemo();
        bean.key = "pbevyvHkf1sFtyGL35gFfQ==";
        bean.methodName = "GetHomeGoods";

        bean.para = "";
        String json = new GsonBuilder().disableHtmlEscaping().create().toJson(bean);

        String path = "http://192.168.0.119:8081/api/CommodityInterface";

        sendJson(json, path, new SendCallBack() {
            @Override
            public void onSuccess(String res) {

      //          Log.e("123", "res=" + res);
            }

            @Override
            public void onFailure(int code) {

            }
        });

    }

    class JsonDemo {
        //        xiaxiele   jiu dang demo
        public String key;
        public String methodName;
        public String para;
    }

    public void sendJson(final String json, final String path,
                         final SendCallBack scb) {
        HttpUtils utils = new HttpUtils();
        utils.configTimeout(10000);
        RequestParams params = new RequestParams();
        try {
            params.setBodyEntity(new StringEntity(json, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        utils.send(HttpRequest.HttpMethod.POST, path, params, new RequestCallBack<String>() {

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                scb.onFailure(arg0.getExceptionCode());
        //        Log.e("o", "没找到");
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                // scb.onSuccess(arg0.result);
                String result = arg0.result;
              //  listview.onRefreshComplete(true);
                jiexi(result);


            }

        });
    }

    public void jiexi(String result) {
        if(!TextUtils.isEmpty(result)) {
            Gson json = new Gson();
            //    Log.e("o", "OK" + result);
            Fist fi = json.fromJson(result, Fist.class);
            //    Log.e("o", "jiexi++++" + fi.getData());


            try {
                //ConstantMy.MJSON为dota对应的字符串。就是那3个数组类型的数组
                JSONArray jsonArray = new JSONArray(fi.getData());
                JSONArray jsonArray1 = jsonArray.getJSONArray(0);
                JSONArray jsonArray2 = jsonArray.getJSONArray(1);
                JSONArray jsonArray3 = jsonArray.getJSONArray(2);

                Bean_a bean_a; //第一个实体
                for (int i = 0; i < jsonArray1.length(); i++) {
                    bean_a = new Bean_a();
                    JSONObject jsonObject = jsonArray1.getJSONObject(i);
                    bean_a.setAdvPath((String) jsonObject.get("AdvPath"));
                    String advPath = (String) jsonObject.get("AdvPath");

                    list.add(advPath);
                    list_a.add(bean_a);


                }

                for (int i = 0; i < jsonArray2.length(); i++) {
                    Bean_b bean_b = new Bean_b();
                    JSONObject jsonObject = jsonArray2.getJSONObject(i);
                    String VC_Url = (String) jsonObject.get("VC_Url");
                    list2.add(VC_Url);
                }

                for (int i = 0; i < jsonArray2.length(); i++) {
                    Bean_b bean_b = new Bean_b();
                    JSONObject jsonObject = jsonArray2.getJSONObject(i);
                    String VC_Code = (String) jsonObject.get("VC_Code");
                    String VC_Name = (String) jsonObject.get("VC_Name");
                    String VC_Url = (String) jsonObject.get("VC_Url");

                    one.add(VC_Code);
                    two.add(VC_Name);
                    three.add(VC_Url);

                    //         Log.e("多少个",three+"");

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            viewpager.setAdapter(new MyPagerAdapter());
        }


    }

    class MyPagerAdapter extends PagerAdapter
    {
        public MyPagerAdapter() {

            utils = new BitmapUtils(getActivity());
        }
        @Override
        public int getCount() {
            return  list == null ? 0 : list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView image = new ImageView(getActivity());
            //image.setImageResource(R.mipmap.icon_pwd);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            String aa = "http://192.168.0.119";
            utils.display(image, aa  + list.get(position));

            container.addView(image);
            return image;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
        }


    }


    class JuleBuhomeadapter2 extends BaseAdapter {

        public JuleBuhomeadapter2()
        {
            utils3 = new BitmapUtils(getActivity());
        }
            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return  database == null ? 0 : database.size();
            }

            @Override
            public Object getItem(int position) {
                // TODO Auto-generated method stub
                return database.get(position);
            }

            @Override
            public long getItemId(int position) {
                // TODO Auto-generated method stub
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                //convertView=View.inflate(mActivity, R.layout.julebuhomelistviewone, null);
                viewHolder1 holder;
                if (convertView == null) {
                    convertView = View.inflate(getActivity(), R.layout.shop_listview, null);//标题
                    holder = new viewHolder1();

                    holder.headline = (TextView) convertView.findViewById(R.id.headline);
                    //已售1024
                    holder.print = (TextView) convertView.findViewById(R.id.print);
                   /* //让你的爱车...
                    holder.prompt = (TextView) convertView.findViewById(R.id.prompt);*/
                    //原价...
                    holder.money = (TextView) convertView.findViewById(R.id.money);
                    //会员...
                    holder.member = (TextView) convertView.findViewById(R.id.member);
                    //图片
                    holder.image2 = (ImageView) convertView.findViewById(R.id.shop_listview_item);
                    convertView.setTag(holder);
                }else{
                    holder = (viewHolder1) convertView.getTag();
                }
                //拿到商品编号以后查询商品明细
                //商品编号


                String aa = "http://192.168.0.119";
                holder.headline.setText(database.get(position).getVC_Name());
                holder.print.setText("1024");
                //员价
                holder.member.setText(database.get(position).getN_FHYJ());
                //会员价
                holder.money.setText(database.get(position).getN_HYJ());
/* holder.prompt.setText(database.get(position).getVC_Rule());*/

                utils3.display(holder.image2,aa+database.get(position).getVC_Url());
                // Picasso.with(context).load(aa+s).into(tuanti);


                return convertView;

            }
        }


    class viewHolder1
    {
        TextView headline;
        TextView print;
        /*TextView prompt;*/
        TextView money;
        TextView member;
        ImageView image2;

    }


    //获取GridViewa的数据
    //获取商品编号下listview的详细信息,从网络获取
    //分裂名称
    public void Category_name()
    {
        // LoginParams loginParams = new LoginParams(ed.getText().toString().trim(), "666888", "xxaabbc085412556sxxx", 1);

        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetLikeCategories")
                .gson(new Gson())
                /*.noParams()*/
                // .object(loginParams)
                .typeValue("string","")
                .build();
//        ParamsBuilder<LoginParams> builder = new ParamsBuilder<LoginParams>().
//                .key("")
//                .build();
        //分类名称
        Call<JsonResp> call = HttpClients.getInstance().Category_name(requestBody);

        call.enqueue(new Callback<JsonResp>() {
            @Override
            public void onResponse(Call<JsonResp> call, Response<JsonResp> response) {
                String data = response.body().getData();

                String content = response.body().getContent();

              //  Log.e("能否32222332获取......",data+"");
                htpjiexi(data);
            }

            @Override
            public void onFailure(Call<JsonResp> call, Throwable t) {
                Log.e("111","失败");
            }
        });


    }
//解析
  public void htpjiexi(String data)
    {
        if(!TextUtils.isEmpty(data)) {
            listfor = new Gson().fromJson(data, new TypeToken<List<Category_nameResp>>() {
            }.getType());

            lis = new ArrayList<String>();
            //标题
            lis2 = new ArrayList<String>();
            for (int i = 0; i < listfor.size(); i++) {
                String ll = listfor.get(i).getImg_Pic();

                lis.add(ll);

            }

            for (int i = 0; i < listfor.size(); i++) {

                String l2 = listfor.get(i).getPs_NAME();

                lis2.add(l2);
            }
            //   Log.e("aa",listfor+"");
        }


    }

    //活动
    public void event()
    {

        // LoginParams loginParams = new LoginParams(ed.getText().toString().trim(), "666888", "xxaabbc085412556sxxx", 1);

        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("GetMallActivitiesGoods")
                .gson(new Gson())
                .noParams()
                // .object(loginParams)
               // .typeValue("string","")
                .build();
//        ParamsBuilder<LoginParams> builder = new ParamsBuilder<LoginParams>().
//                .key("")
//                .build();
        //分类名称
        Call<JsonResp> call = HttpClients.getInstance().event(requestBody);

        call.enqueue(new Callback<JsonResp>() {
            @Override
            public void onResponse(Call<JsonResp> call, Response<JsonResp> response) {
                String data = response.body().getData();

         //       Log.e("活动2", data +"");
               eventjiexi(data);
            }

            @Override
            public void onFailure(Call<JsonResp> call, Throwable t) {
                Log.e("111","失败");
            }
        });


    }
    //解析
    public void eventjiexi(String data)
    {
        if(!TextUtils.isEmpty(data)) {
            listevent = new Gson().fromJson(data, new TypeToken<List<EventResp>>() {
            }.getType());
            database = listevent.get(0).getData();
        }



    }


    class GridViewadapter extends BaseAdapter
    {

        public GridViewadapter()
        {
            utils2 = new BitmapUtils(getActivity());
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub

            return  lis == null ? 0 : lis.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return lis.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            //convertView=View.inflate(mActivity, R.layout.julebuhomelistviewone, null);
            View view = View.inflate(getActivity(), R.layout.shop_gridview,null);
             //更多
            TextView headline= (TextView) view.findViewById(R.id.label );

            if(lis2!=null&&lis2.size()>0) {
                headline.setText(lis2.get(position));
            }
            //拿到每个商品编号

            ImageView image2=(ImageView) view.findViewById(R.id.shop_image);
            //动态的java代码设置图片
            String aa = "http://192.168.0.119";
            utils2.display(image2,aa+lis.get(position));
            // Picasso.with(context).load(aa+s).into(tuanti);


            return view;

        }
    }

   class GridViewadapter2 extends BaseAdapter
    {

        public GridViewadapter2()
        {
            utils2 = new BitmapUtils(getActivity());
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub

           return  lis == null ? 0 : lis.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return lis.get(position);
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
                convertView = View.inflate(getActivity(), R.layout.shop_listview, null);//标题
                holder = new viewHolder1();
                holder.headline = (TextView) convertView.findViewById(R.id.headline);
                //已售1024
                holder.print = (TextView) convertView.findViewById(R.id.print);
                /*//让你的爱车...
                holder.prompt = (TextView) convertView.findViewById(R.id.prompt);*/
                //原价...
                holder.money = (TextView) convertView.findViewById(R.id.money);
                //图片
                holder.image2 = (ImageView) convertView.findViewById(R.id.shop_listview_item);
                convertView.setTag(holder);
            }else{
                holder = (viewHolder1) convertView.getTag();
            }
            //动态java代码设置图片

            String aa = "http://192.168.0.119";
            holder.headline.setText(database.get(position).getTitle());
            holder.print.setText(database.get(position).getN_HYJ());
           /* holder.prompt.setText("你的爱车.....");*/
            if(list2!=null&&list2.size()>0) {
                utils2.display(holder.image2, aa + list2.get(position));
                // Picasso.with(context).load(aa+s).into(tuanti);
            }


            return convertView;
        }
    }


    //定位
    public void dingwei()
    {
        //定位
        LocationManager.getInstance().init(context);
        LocationManager.getInstance().setAMapLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation location) {
                LocationManager.getInstance().stopLocation();


            }
        });
        LocationManager.getInstance().startLocation();
    }

    @OnClick(R.id.search_header_scan_img)
    public void goScan(View view) {
        animStart(CaptureActivity.class);
    }


    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sp = context.getSharedPreferences("chongqing", Activity.MODE_WORLD_READABLE);
        s = sp.getString("s","");
        if(!TextUtils.isEmpty(s))
        {
            cityTxt.setText(s);
        }
        Log.e("sssss",s);
    }

}






