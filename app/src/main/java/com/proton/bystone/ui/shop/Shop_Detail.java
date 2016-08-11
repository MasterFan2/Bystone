package com.proton.bystone.ui.shop;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.proton.bystone.bean.Category_nameResp;
import com.proton.bystone.bean.Commodity;
import com.proton.bystone.bean.EventResp;
import com.proton.bystone.bean.Fist;

import com.proton.bystone.bean.JsonResp;
import com.proton.bystone.bean.Shop_Liebiao;
import com.proton.bystone.bean.Shop_RecoResp;
import com.proton.bystone.bean.Shop_Two;
import com.proton.bystone.cache.LoginManager;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.ui.login.SendCallBack;
import com.proton.bystone.ui.login.LoginActivity;
import com.proton.bystone.ui.shopcar.MyShoppingCar;
import com.proton.bystone.ui.shopcar.ShopCarActivity;
import com.proton.bystone.ui.view.RefreshListView;
import com.proton.bystone.utils.LoginUtil;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

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
 * Created by Administrator on 2016/7/19.
 * 商品详情页
 */
@MTFActivityFeature(layout = R.layout.shop_bye)
public class Shop_Detail extends MTFBaseActivity {

    List<Commodity>   commList;
    BitmapUtils utils;
    ViewPager viewpager;
    List<Fist> list_shop_recoResp;
    Shop_Two two;
    ArrayList<String>  list=new ArrayList<String>();
    Button  shpp_bye;
    List<Shop_Two> list_a = new ArrayList<Shop_Two>();
    List<Commodity> commodityList;
    ImageView   shop_cs;

    ImageView   shop_cx;
    ImageView   shop_jj;
    List<String> vc_params = new ArrayList<String>();
    TextView shop_headline;
    TextView shop_member;
    TextView shop_money;
    TextView shop_print;
    TextView shop_prompt;
    String[] ary;
    String vcparams;
    String  N_HYJ;
    String   N_FHYJ;
    String Packing;
    String PadctBrief;
    TextView shop_name;
    TextView shop_model;
    TextView shop_mode2;
    TextView shop_mode3;
    TextView shop_mode4;
    TextView shop_mode5;
    TextView shop_mode6;
    TextView shop_mode7;

    TextView shop_zhi;
    TextView shop_zhi2;
    TextView shop_zhi3;
    TextView shop_zhi4;
    TextView shop_zhi5;
    TextView shop_zhi6;
    TextView shop_zhi7;

    String vccode;

    TextView shop_jianjie;
    Boolean  flag;
    GridView  m_Modem;
    RelativeLayout  shop_chexing;
    RelativeLayout shop_canshu;
    RelativeLayout shop_js;
    RelativeLayout   shop_jainjie;
    Boolean fal=false;
    Boolean cs=false;
    Boolean jj=false;
    String aa = "http://192.168.0.119";

    String CommContent ;
    String NickName ;
    String CommTime ;
    String Path ;

    TextView  shop_see_all;
    List<String> PropertyName = new ArrayList<String>();
    List<String> PtValue = new ArrayList<String>();
    List<String> M_Model = new ArrayList<String>();
    ImageView shop_pingjiaimage1;
    ImageView shop_pingjiaimage2;
    ImageView shop_pingjiaimage3;
    TextView   shop_date;
    TextView   shop_pingjia;
    Commodity commodity;
    @Bind(R.id.shop_shopping_cart)
    Button bt;

    @Bind(R.id.shop_gwu)
    ImageView img;

    RelativeLayout shop_dian;

    @Override
    public void initialize(Bundle savedInstanceState) {
        initview();
    }

    @OnClick(R.id.shop_shopping_cart)
    public void addToShoppingCar(View view) {
        //DataBean
        MyShoppingCar.getShoppingCar().add(null);//add shopping car
//        animStart(ShopCarActivity.class);go to Shopping car
    }


    @OnClick(R.id.Home_fh)
    public void back(View view) {
        animFinish();
    }

    @Override
    public void backPressed() {
       animFinish();
    }

    private void initview() {
        viewpa();

        viewpager = (ViewPager) findViewById(R.id.shop2_vPager);
        shpp_bye = (Button) findViewById(R.id.shop_bye);//点击购买

        shop_dian = (RelativeLayout) findViewById(R.id.shop_dian);
       shop_js = (RelativeLayout) findViewById(R.id.shop_js);

        shop_headline=(TextView)findViewById(R.id.shop_headline);
        //会员价
        shop_member=(TextView)findViewById(R.id.shop_member);
        //原价
        shop_money=(TextView)findViewById(R.id.shop_money);
        //已售出
        shop_print=(TextView)findViewById(R.id.shop_print);
        //提示
        shop_prompt=(TextView)findViewById(R.id.shop_prompt);

        //商品简介
        shop_jianjie=(TextView)findViewById(R.id.shop_jianjie);
        //评价人姓名
        shop_name=(TextView)findViewById(R.id.shop_name);
        shop_date=(TextView)findViewById(R.id.shop_date);//评价日期
        shop_pingjia=(TextView)findViewById(R.id.shop_pingjia);//评价

        shop_pingjiaimage1=(ImageView)findViewById(R.id.shop_pingjiaimage1);//评价图1
        shop_pingjiaimage2=(ImageView)findViewById(R.id.shop_pingjiaimage2);//评价图1
        shop_pingjiaimage3=(ImageView)findViewById(R.id.shop_pingjiaimage3);//评价图1

        //参数
       shop_cs=(ImageView)findViewById(R.id.shop_cs);
        //车型
       shop_cx=(ImageView)findViewById(R.id.shop_cx);
        //简介
        shop_jj=(ImageView)findViewById(R.id.shop_jj);

       shop_canshu=(RelativeLayout)findViewById(R.id.shop_canshu);
        shop_chexing=(RelativeLayout)findViewById(R.id.shop_chexing);
        shop_jainjie=(RelativeLayout)findViewById(R.id.shop_jainjie);
         shop_see_all=(TextView)findViewById(R.id.shop_see_all);//查看全部评论



        //键
        shop_model=(TextView)findViewById(R.id.shop_model);
        shop_mode2=(TextView)findViewById(R.id.shop_mode2);
        shop_mode3=(TextView)findViewById(R.id.shop_mode3);
        shop_mode4=(TextView)findViewById(R.id.shop_mode4);
        shop_mode5=(TextView)findViewById(R.id.shop_mode5);
        shop_mode6=(TextView)findViewById(R.id.shop_mode6);
        shop_mode7=(TextView)findViewById(R.id.shop_mode7);


        shop_zhi=(TextView)findViewById(R.id.shop_zhi);
        shop_zhi2=(TextView)findViewById(R.id.shop_zhi2);
        shop_zhi3=(TextView)findViewById(R.id.shop_zhi3);
        shop_zhi4=(TextView)findViewById(R.id.shop_zhi4);
        shop_zhi5=(TextView)findViewById(R.id.shop_zhi5);
        shop_zhi6=(TextView)findViewById(R.id.shop_zhi6);
        shop_zhi7=(TextView)findViewById(R.id.shop_zhi7);

        m_Modem=(GridView)findViewById(R.id.M_Model);
     //   m_Modem.setAdapter(new JuleBuhomeadapter89());
        Listener();


    }
    public void get_data()
    {
        shop_headline.setText(commodityList.get(0).getVC_Name());//Viewpager
        shop_member.setText(commodityList.get(0).getN_HYJ());
        shop_money.setText(commodityList.get(0).getN_FHYJ());
        shop_print.setText(commodityList.get(0).getPacking());
        shop_prompt.setText(commodityList.get(0).getPadctBrief());

        shop_money.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );

        shop_model.setText(PropertyName.get(0));
        shop_mode2.setText(PropertyName.get(1));
        shop_mode3.setText(PropertyName.get(2));
        shop_mode4.setText(PropertyName.get(3));
        shop_mode5.setText(PropertyName.get(4));
        shop_mode6.setText(PropertyName.get(5));
        shop_mode7.setText(PropertyName.get(6));

        shop_zhi.setText(PtValue.get(0));
        shop_zhi2.setText(PtValue.get(1));
        shop_zhi3.setText(PtValue.get(2));
        shop_zhi4.setText(PtValue.get(3));
        shop_zhi5.setText(PtValue.get(4));
        shop_zhi6.setText(PtValue.get(5));
        shop_zhi7.setText(PtValue.get(6));

//商品简介
        shop_jianjie.setText(vcparams);

        shop_name.setText(NickName);   //评价人姓名
        shop_date.setText(CommTime);//日期
        shop_pingjia.setText(CommContent);

        BitmapUtils   bit=new BitmapUtils(this);
       /* bit.display(shop_pingjiaimage1, aa+ary[0] );
        bit.display(shop_pingjiaimage2, aa+ary[1] );
        bit.display(shop_pingjiaimage3, aa+ary[2] );
*/
    }

    public void Listener()
    {

        shop_see_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到查看全部评论
                Intent t= new Intent(Shop_Detail.this,All_Pl.class);
                startActivity(t);
            }
        });
        //立即购买
        shpp_bye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Boolean check=LoginUtil.checkLogin(Shop_Detail.this);
                boolean login = LoginManager.getInstance().isLogin();
                if(!login) {
                    animStartForResult(9528, LoginActivity.class);
                }else{

                    //跳转订单确认
                    Intent t= new Intent(Shop_Detail.this,Shop_Ok.class);
                    t.putExtra("vcparams",vcparams);
                    t.putExtra("shop_member",N_HYJ);
                    t.putExtra("shop_money",N_FHYJ);
                    t.putExtra("shop_prompt",PadctBrief);
                    t.putExtra("list",list.get(0));
                    Log.e("listlist",list.get(0));

                    t.putExtra("I_Company",commodity.getI_Company());//商家编号
                    t.putExtra("VC_XH",commodity.getVC_XH());//商家规格型号
                    t.putExtra("getVC_Name",commodity.getVC_Name());//商品名称
                    t.putExtra("getVC_Code",commodity.getVC_Code());//商品编号


                    startActivity(t);
                }/*else{
                    //跳转到登陆页
                    Intent t= new Intent(Shop_Detail.this,LoginActivity.class);
                    t.putExtra("landing","log");
                    startActivity(t);
                }*/


            }
        });
//添加购物车
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyShoppingCar.getShoppingCar().add(commodityList.get(0));

            }
        });
//跳转购物车
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // String lista = getIntent().getStringExtra("list");

                //跳转到登陆页
                Intent t= new Intent(Shop_Detail.this,ShopCarActivity.class);
                startActivity(t);
            }
        });


        shop_cs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cs==false) {
                    shop_cs.setImageResource(R.mipmap.icon_menu);
                    shop_canshu.setVisibility(View.VISIBLE);
                    cs=true;
                }else{
                    shop_cs.setImageResource(R.mipmap.icon_right);
                    shop_canshu.setVisibility(View.GONE);
                    cs=false;
                }
            }
        });



        shop_cx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cs==false) {
                    shop_cx.setImageResource(R.mipmap.icon_menu);
                    shop_chexing.setVisibility(View.VISIBLE);
                    cs=true;
                }else{
                    shop_cx.setImageResource(R.mipmap.icon_right);
                    shop_chexing.setVisibility(View.GONE);
                    cs=false;
                }


            }
        });

        shop_jj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(jj==false) {
                    shop_jj.setImageResource(R.mipmap.icon_menu);
                    shop_jainjie.setVisibility(View.VISIBLE);
                    jj=true;
                }else{
                    shop_jj.setImageResource(R.mipmap.icon_right);
                    shop_jainjie.setVisibility(View.GONE);
                    jj=false;
                }


            }
        });


        shop_dian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cs==false) {
                    shop_cs.setImageResource(R.mipmap.icon_menu);
                    shop_canshu.setVisibility(View.VISIBLE);
                    cs=true;
                }else{
                    shop_cs.setImageResource(R.mipmap.icon_right);
                    shop_canshu.setVisibility(View.GONE);
                    cs=false;
                }

            }
        });

        shop_js.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jj==false) {
                    shop_jj.setImageResource(R.mipmap.icon_menu);
                    shop_jainjie.setVisibility(View.VISIBLE);
                    jj=true;
                }else{
                    shop_jj.setImageResource(R.mipmap.icon_right);
                    shop_jainjie.setVisibility(View.GONE);
                    jj=false;
                }
            }
        });


    }

    public void viewpa()
    {
        JsonDemo bean = new JsonDemo();
        bean.key = "pbevyvHkf1sFtyGL35gFfQ==";
        bean.methodName = "GetCommodityDetail";
        ArrayList<String> list=new ArrayList<String>();
       vccode = getIntent().getStringExtra("vccode");

       list.add("{Type:'String'");
        list.add("Value:"+vccode+"}");

        Log.e("list",list+"");

        /*bean.para = "[{Type:'string',Value:'14629770639'}]";*/
  //      bean.para=list+"";

      /*  list.add("{Type:'string'");
        list.add("Value:"+"2016052317205144748}");*/
        //list.add("Value:"+"2016052317205143311}");

       // Log.e("list",list+"");
        bean.para = list+"";
        String json = new GsonBuilder().disableHtmlEscaping().create().toJson(bean);

        String path = "http://192.168.0.119:8081/api/CommodityInterface";

        sendJson(json, path, new SendCallBack() {
            @Override
            public void onSuccess(String res) {


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
             //   Log.e("o", "没找到");
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                // scb.onSuccess(arg0.result);
                String result = arg0.result;
                jiexi(result);


                Log.e("拿到结果",result);

            }

        });
    }



    public void jiexi(String result) {

        try {
            JSONObject obj = new JSONObject(result);
            String strJsonArray = obj.getString("data");
            JSONArray jsonArray = new JSONArray(strJsonArray);
           commodityList = new Gson().fromJson(jsonArray.get(0).toString(), new TypeToken<List<Commodity>>() {}.getType());


        } catch (JSONException e) {
            e.printStackTrace();
        }


        Gson g = new Gson();
        Fist duanxin = g.fromJson(result, Fist.class);
        commodity = g.fromJson(result, Commodity.class);


       try {
            //ConstantMy.MJSON为dota对应的字符串。就是那3个数组类型的数组
            JSONArray jsonArray = new JSONArray(duanxin.getData());
            JSONArray jsonArray1 = jsonArray.getJSONArray(0);
            JSONArray jsonArray2 = jsonArray.getJSONArray(1);
            JSONArray jsonArray3 = jsonArray.getJSONArray(2);
            JSONArray jsonArray4 = jsonArray.getJSONArray(3);
            JSONArray jsonArray5 = jsonArray.getJSONArray(4);

            Shop_RecoResp one = new Shop_RecoResp();//第一个实体
            for (int i = 0; i < jsonArray1.length(); i++) {

                JSONObject jsonObject = jsonArray1.getJSONObject(i);
                vcparams = (String) jsonObject.get("VC_Params");
               //会员价
                N_HYJ = (String) jsonObject.get("N_HYJ");
                //原价
                N_FHYJ = (String) jsonObject.get("N_FHYJ");
                //已售出
                Packing = (String) jsonObject.get("Packing");
                //提示
                PadctBrief = (String) jsonObject.get("PadctBrief");

              String  Type_CODE = (String) jsonObject.get("Type_CODE");
                String  VC_Code = (String) jsonObject.get("VC_Code");
                String  VC_Name = (String) jsonObject.get("VC_Name");
                String  I_Company = (String) jsonObject.get("I_Company");
                String  VC_Rule = (String) jsonObject.get("VC_Rule");
                String  VC_XH = (String) jsonObject.get("VC_XH");
                String  VC_PP = (String) jsonObject.get("VC_PP");
                int  SaleNum = (int) jsonObject.get("SaleNum");
                String  Pc_Code = (String) jsonObject.get("Pc_Code");
                String  N_JXS = (String) jsonObject.get("N_JXS");
                String  N_HYJ = (String) jsonObject.get("N_HYJ");
                String  N_FHYJ = (String) jsonObject.get("N_FHYJ");
                int  Stock = (int) jsonObject.get("Stock");
                String  Ps_Name = (String) jsonObject.get("Ps_Name");
                String  VC_Url = (String) jsonObject.get("VC_Url");
                int  PostagePrice = (int) jsonObject.get("PostagePrice");

             /*   commodity.setN_HYJ(N_HYJ);
                commodity.setN_FHYJ(N_FHYJ);
                commodity.setVC_Params(vcparams);
                commodity.setPacking(Packing);
                commodity.setPadctBrief(PadctBrief);
                commodity.setVC_Code(VC_Code);
                commodity.setVC_Name(VC_Name);
                commodity.setI_Company(I_Company);
                commodity.setVC_Rule(VC_Rule);
                commodity.setVC_XH(VC_XH);
                commodity.setVC_PP(VC_PP);
                commodity.setSaleNum(SaleNum);
                commodity.setPc_Code(Pc_Code);
                commodity.setN_JXS(N_JXS);
                commodity.setN_HYJ(N_HYJ);
                commodity.setN_FHYJ(N_FHYJ);
                commodity.setStock(Stock);
                commodity.setPs_Name(Ps_Name);
                commodity.setVC_Url(VC_Url);
                commodity.setPostagePrice(PostagePrice);*/


            }







           Shop_Liebiao three = new Shop_Liebiao();//第一个实体
           for (int i = 0; i < jsonArray4.length(); i++) {

               JSONObject jsonObject = jsonArray4.getJSONObject(i);
               //商品品牌
                String  propertyname = (String) jsonObject.get("PropertyName");
                //商品职，车市通
                String  ptvalue = (String) jsonObject.get("PtValue");

                PropertyName.add(propertyname);
                PtValue.add(ptvalue);

           }

           for (int i = 0; i< jsonArray5.length(); i++) {

               JSONObject jsonObject = jsonArray5.getJSONObject(i);
               String m_model = (String) jsonObject.get("M_Model");
               M_Model.add(m_model);
           }

           two = new Shop_Two();
            for (int i = 0; i< jsonArray2.length(); i++) {

                JSONObject jsonObject = jsonArray2.getJSONObject(i);
                String vc_url = (String) jsonObject.get("VC_Url");
                list.add(vc_url);
            }

           for (int i = 0; i< jsonArray3.length(); i++) {

               JSONObject jsonObject = jsonArray3.getJSONObject(i);
                CommContent = (String) jsonObject.get("CommContent");
                NickName = (String) jsonObject.get("NickName");
                CommTime = (String) jsonObject.get("CommTime");
                Path = (String) jsonObject.get("Path");

           }

         /*  ary = Path.split(",");
           for (int i = 0; i < ary.length; i++) {
             //  Log.e("111111111111",ary[i]+"");
           }*/


           //加载各种数据
           get_data();

        //   Log.e("ary",ary+"");


           viewpager.setAdapter(new MyPagerAdapter());
       } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    class MyPagerAdapter extends PagerAdapter
    {
        public MyPagerAdapter() {
            utils = new BitmapUtils(Shop_Detail.this);
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView image = new ImageView(Shop_Detail.this);
            //image.setImageResource(R.mipmap.icon_pwd);
            image.setScaleType(ImageView.ScaleType.FIT_XY);

            utils.display(image, aa  + list.get(position));

            container.addView(image);
            return image;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
        }

        @OnClick(R.id.Home_fh)
        public void back(View view) {
            animFinish();
        }


    }


class JuleBuhomeadapter89 extends BaseAdapter {

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return M_Model == null ? 0 : M_Model.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return M_Model.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        TextView tv = new TextView(Shop_Detail.this);
        tv.setText(M_Model.get(position));
        return tv;


    }

    @OnClick(R.id.Home_fh)
    public void back(View view) {
        animFinish();
    }


}











}



