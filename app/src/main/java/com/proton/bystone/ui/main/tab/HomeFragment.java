package com.proton.bystone.ui.main.tab;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.Bean_a;
import com.proton.bystone.bean.Bean_b;
import com.proton.bystone.bean.Bean_c;
import com.proton.bystone.bean.Commodity;
import com.proton.bystone.bean.Fist;
import com.proton.bystone.bean.Home_Weather;
import com.proton.bystone.bean.NewRecommendResp;
import com.proton.bystone.bean.readd;
import com.proton.bystone.bean.Shop_Fenlei;
import com.proton.bystone.bean.Shop_List;
import com.proton.bystone.bean.home_yj;
import com.proton.bystone.location.LocationManager;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.ui.login.SendCallBack;
import com.proton.bystone.ui.main.tab.home.Homeserch;
import com.proton.bystone.ui.main.tab.home.Search_service;
import com.proton.bystone.ui.shop.Shop_Commodity;
import com.proton.bystone.ui.shop.Shop_Detail;
import com.proton.bystone.ui.shop.Shop_Sort;
import com.proton.library.ui.MTFBaseFragment;
import com.proton.library.ui.annotation.MTFFragmentFeature;
import com.proton.library.zxing.activity.CaptureActivity;

/*import org.apache.http.entity.StringEntity;*/
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@MTFFragmentFeature(layout = R.layout.fragment_home)
public class HomeFragment extends MTFBaseFragment {

  /*  @Bind(R.id.vPager)
    ViewPager vp;
    @Bind(R.id.but)
    Button but;*/

    ///search header
    @Bind(R.id.search_header_search_layout)
    LinearLayout searchLayout;
    @Bind(R.id.search_header_scan_img)
    ImageView scanImg;
    @Bind(R.id.search_header_city_txt)
    TextView cityTxt;
    List<Home_Weather.ResultsBean.IndexBean> index;
    String s;

    @OnClick(R.id.search_header_scan_img)
    public void goScan(View view) {
        animStart(CaptureActivity.class);
    }


    @Bind(R.id.lv)
    ListView listview;
    Shop_Fenlei tq;
//    //搜索
//    @Bind(R.id.search)
//    EditText search;
//    //黄泥磅
//    @Bind(R.id.btn)
//    Button btn;
    ViewPager vp;
    BitmapUtils utils;
    String vccode;
    String ps_code;
    Intent t;
    Button but;
    List<Bean_a> list_a = new ArrayList<>();
    List<Bean_b> list_b = new ArrayList<>();
    List<Bean_c> list_c = new ArrayList<>();

    List<String> list = new ArrayList<String>();
    List<String> list2 = new ArrayList<String>();

    List<String> vc_code = new ArrayList<String>();
    List<String> vc_name = new ArrayList<String>();
    List<String> vc_url = new ArrayList<String>();

    List<String> vc_code2 = new ArrayList<String>();
    List<String> vc_name2 = new ArrayList<String>();
    List<String> vc_url2 = new ArrayList<String>();
    ImageView image;
    BitmapUtils utils2;
    BitmapUtils utils3;
    GridView gridView;
    GridView gridView2;
    TextView yzq;
    TextView  home_ssd;
    TextView   home_byxc;
    ArrayList  <TextView>list3;
    List<Home_Weather.ResultsBean.WeatherDataBean> weather_data;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(Bundle args) {
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void initialize() {



        // 加载头布局
        View headerView = View.inflate(HomeFragment.this.getActivity(), R.layout.first_layout,
                null);
        vp = (ViewPager) headerView.findViewById(R.id.vPager);
        but = (Button) headerView.findViewById(R.id.but);
        gridView = (GridView) headerView.findViewById(R.id.home_gridView);
        gridView2 = (GridView) headerView.findViewById(R.id.home_gridView2);
       yzq = (TextView) headerView.findViewById(R.id.home_yzq);//阴转晴
       home_byxc = (TextView) headerView.findViewById(R.id.home_byxc);//不易洗车
        home_ssd = (TextView) headerView.findViewById(R.id.home_ssd);//摄氏度


        list3=new ArrayList<TextView>();
        for (int i = 0; i <3 ; i++) {
             TextView  tv=new TextView(HomeFragment.this.getActivity());
           // tv.setText("测试"+i);
            list3.add(tv);

        }


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vccode = vc_code2.get(position);
                Intent t= new Intent(getActivity(),Shop_Detail.class);
                t.putExtra("vccode",vccode);
                startActivity(t);

            }
        });

        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             String   vccode = vc_code.get(position);
                Intent t= new Intent(getActivity(),Shop_Detail.class);
                t.putExtra("vccode",vccode);
                startActivity(t);

            }
        });


        listview.addHeaderView(headerView);

        viewpa();
        gridView.setAdapter(new JuleBuhomeadapter3());
        gridView2.setAdapter(new JuleBuhomeadapter4());

        listview.setAdapter(new JuleBuhomeadapter2() );

        Listener();

        Category_name();//天气
        price();


    }

    @Override
    public void load() {

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

                Log.e("123", "res=" + res);
            }

            @Override
            public void onFailure(int code) {

            }
        });

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
    //定位
    public void dingwei()
    {
        //定位
        LocationManager.getInstance().init(context);
        LocationManager.getInstance().setAMapLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation location) {
                LocationManager.getInstance().stopLocation();
                Toast.makeText(context,"地址定位中",Toast.LENGTH_SHORT).show();

            }
        });
        LocationManager.getInstance().startLocation();
    }

    public void Listener() {

        SharedPreferences sp = context.getSharedPreferences("chongqing", Activity.MODE_WORLD_READABLE);
        s = sp.getString("s","");
        if(!TextUtils.isEmpty(s))
        {
            cityTxt.setText(s);
        }




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
           //     Log.e("o", "没找到");
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                // scb.onSuccess(arg0.result);
                String result = arg0.result;
                jiexi(result);
            }

        });
    }

    public void jiexi(String result) {
        Gson json = new Gson();

        Fist fi = json.fromJson(result, Fist.class);
       // Log.e("o", "解析了哦" + fi.getData());


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
                String advPath =(String) jsonObject.get("AdvPath");
                list_a.add(bean_a);
                list.add(advPath);
            }

            for (int i = 0; i < jsonArray2.length(); i++) {
                Bean_b  bean_b = new Bean_b();
                JSONObject jsonObject = jsonArray2.getJSONObject(i);

                String VC_Url =(String) jsonObject.get("VC_Url");
                String VC_Code =(String) jsonObject.get("VC_Code");
                String VC_Name =(String) jsonObject.get("VC_Name");

                 vc_code2.add(VC_Code);
                 vc_name2.add(VC_Name);
                 vc_url2.add(VC_Url);

                list_b.add(bean_b);
                list2.add(VC_Url);
            }

            Log.e("list2",list2+"");
            //最新推荐

            for (int i = 0; i < jsonArray3.length(); i++) {
                NewRecommendResp nr = new NewRecommendResp();
                JSONObject jsonObject = jsonArray3.getJSONObject(i);
                String VC_Code =(String) jsonObject.get("VC_Code");
                String VC_Name =(String) jsonObject.get("VC_Name");
                String VC_Url =(String) jsonObject.get("VC_Url");

                vc_code.add(VC_Code);
                vc_name.add(VC_Name);
                vc_url.add(VC_Url);


            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

       // Log.e("123","list:"+list_a);
        vp.setAdapter(new MyPagerAdapter());

    }

    class MyPagerAdapter extends PagerAdapter
    {
        public MyPagerAdapter() {

            utils = new BitmapUtils(getActivity());
        }
        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
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
           yzq.setText(weather_data.get(position).getWeather());
            home_byxc.setText(index.get(position).getTitle());
            home_ssd.setText(weather_data.get(position).getTemperature());


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
            utils2 = new BitmapUtils(getActivity());
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return list2.get(position);
        }

        @Override
        public long getItemId(int position ) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            //convertView=View.inflate(mActivity, R.layout.julebuhomelistviewone, null);
            View view = View.inflate(getActivity(), R.layout.listviewfist, null);

            TextView tv1= (TextView) view.findViewById(R.id.home_title);

            /*TextView tv2=(TextView) view.findViewById(R.id.home_by);*/
            //图片
            ImageView image2=(ImageView) view.findViewById(R.id.home_image);
            //动态的java代码设置图片

            String aa = "http://192.168.0.119";
            tv1.setText("青木年华");
            /*tv2.setText("青木");*/
            utils2.display(image2,aa+list2.get(position));
           // Picasso.with(context).load(aa+s).into(tuanti);
         //   Log.e("测试",aa+list2.get(position));

            return view;


        }

       class viewHolder1
        {
            TextView tv1;

            ImageView  image2;
        }


    }



    class JuleBuhomeadapter3 extends BaseAdapter {

        public JuleBuhomeadapter3()
        {
            utils3 = new BitmapUtils(getActivity());
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub

            return vc_code2 == null ? 0 : vc_code2.size();


        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return vc_code2.get(position);
        }

        @Override
        public long getItemId(int position ) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            //convertView=View.inflate(mActivity, R.layout.julebuhomelistviewone, null);
            View view = View.inflate(getActivity(), R.layout.listviewfist, null);

            TextView tv1= (TextView) view.findViewById(R.id.home_title);

            //图片
            ImageView image2=(ImageView) view.findViewById(R.id.home_image);
            //动态的java代码设置图片

            String aa = "http://192.168.0.119";
           tv1.setText(vc_name2.get(position));
            //Log.e("vc_url2",vc_url2+"");
            utils3.display(image2,aa+vc_url2.get(position));
            // Picasso.with(context).load(aa+s).into(tuanti);
           /* Log.e("测试",aa+vc_url.get(position));*/

            return view;


        }




    }


   class JuleBuhomeadapter4 extends BaseAdapter {

        public JuleBuhomeadapter4()
        {
            utils3 = new BitmapUtils(getActivity());
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub

            return  vc_code == null ? 0 : vc_code.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return vc_code.get(position);
        }

        @Override
        public long getItemId(int position ) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            //convertView=View.inflate(mActivity, R.layout.julebuhomelistviewone, null);
            View view = View.inflate(getActivity(), R.layout.listviewfist, null);

            TextView tv1= (TextView) view.findViewById(R.id.home_title);
            //图片
            ImageView image2=(ImageView) view.findViewById(R.id.home_image);
            //动态的java代码设置图片

            String aa = "http://192.168.0.119";
            tv1.setText(vc_name.get(position));

            utils2.display(image2,aa+vc_url.get(position));
            // Picasso.with(context).load(aa+s).into(tuanti);


            return view;


        }




    }



    //获取天气

    public void Category_name() {
        // LoginParams loginParams = new LoginParams(ed.getText().toString().trim(), "666888", "xxaabbc085412556sxxx", 1);
    //    Log.e("s",s);
        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("FunGetWeather")
                .gson(new Gson())
                //.noParams()
                // .object(loginParams)
                .typeValue("string", "重庆")

                .build();
//        ParamsBuilder<LoginParams> builder = new ParamsBuilder<LoginParams>().
//                .key("")
//                .build();
        //分类名称
        Call<BaseResp> call = HttpClients.getInstance().memberInfo(requestBody);

        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {


                String data = response.body().getData();
                Log.e("data",data);
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
        Home_Weather  o = new Gson().fromJson(data, new TypeToken<Home_Weather>() {
        }.getType());
        weather_data = o.getResults().get(0).getWeather_data();
        index = o.getResults().get(0).getIndex();

    }



    //油价
    public void price() {
        // LoginParams loginParams = new LoginParams(ed.getText().toString().trim(), "666888", "xxaabbc085412556sxxx", 1);

        RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("FunGetOilPrice")
                .gson(new Gson())
                /*.noParams()*/
                // .object(loginParams)
                .typeValue("string", "重庆")
                .build();
//        ParamsBuilder<LoginParams> builder = new ParamsBuilder<LoginParams>().
//                .key("")
//                .build();
        //分类名称
        Call<BaseResp> call = HttpClients.getInstance().memberInfo(requestBody);

        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {


                String data6 = response.body().getData();

                price(data6);
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                Log.e("111", "失败");
            }
        });
    }
    //解析价格从低搞到数据
    public void price(String data)
    {
        JSONObject obj = null;
        try {
            obj = new JSONObject(data);
            String strJsonArray = obj.getString("data");
            JSONArray jsonArray = new JSONArray(strJsonArray);
            List<home_yj>   commodityList = new Gson().fromJson(jsonArray.get(0).toString(), new TypeToken<List<home_yj>>() {}.getType());
            String showapi_res_body = commodityList.get(0).getShowapi_res_body();
            String strJsonArray1 = obj.getString("showapi_res_body");
            JSONArray jsonArray1 = new JSONArray(strJsonArray1);
            List<readd>   list = new Gson().fromJson(jsonArray1.get(0).toString(), new TypeToken<List<readd>>() {}.getType());
            List<readd.ListBean> list1 = list.get(0).getList();

            Toast.makeText(getActivity(),list1.get(0).getP90(),Toast.LENGTH_LONG).show();


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }















}










