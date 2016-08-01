package com.proton.bystone.net;

import android.content.Context;

import com.google.gson.Gson;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.MaintainResp;
import com.proton.bystone.bean.JsonResp;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


/**
 * Created by 13510 on 2016/4/1.
 */
public class HttpClients {

    private static final String BASE_URL = "http://192.168.0.119:8081/api/";//开发服务器
    public static  final String PIC_URL  = "http://192.168.0.119";

    private Context mContext;
    private Retrofit retrofit = null;
    private NetInterface netInterface = null;
    private Gson gson;

    private static HttpClients instance;

    private HttpClients() {
    }

    public static HttpClients getInstance() {
        if (instance == null) {
            instance = new HttpClients();
        }
        return instance;
    }

    /**
     * @param context 上下文
     * @return false:初期化失败 true:初期化成功
     */
    public boolean initialize(Context context) {

        mContext = context;
        gson = new Gson();

        // 创建适配器
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory( GsonConverterFactory.create())
                .build();

        netInterface = retrofit.create(NetInterface.class);
        return netInterface != null;
    }


    //接口
    interface NetInterface {
        @Headers({"Content-Type:application/json","Accept:application/json"})
        @POST("MemberInfoInterface")//用户管理相关接口
        Call<BaseResp> memberInfo(@Body RequestBody requestBody);

        @Headers({"Content-Type:application/json","Accept:application/json"})
        @POST("MaintenanceInfoInterface")//保养相关接口
        Call<BaseResp> maintenance(@Body  RequestBody requestBody);

        @Headers({"Content-Type:application/json","Accept:application/json"})
        @POST("CommodityInterface")//商品相关接口
        Call<BaseResp> commodity(@Body  RequestBody requestBody);

        @Headers({"Content-Type:application/json","Accept:application/json"})
        @POST("OrderInfoInterface")//订单管理相关接口
        Call<BaseResp> orderInfo(@Body  RequestBody requestBody);

        @Headers({"Content-Type:application/json","Accept:application/json"})
        @POST("UploadInterFace")//文件上传相关接口
        Call<BaseResp> upload(@Body  RequestBody requestBody);







        @Headers({"Content-Type:application/json","Accept:application/json"})
        @POST("CommodityInterface")
        Call<JsonResp> advertisement(@Body RequestBody requestBody);//广告

        @Headers({"Content-Type:application/json","Accept:application/json"})
        @POST("MemberInfoInterface")
        Call<JsonResp> sendverification(@Body RequestBody requestBody);//注册发送验证码

        @Headers({"Content-Type:application/json","Accept:application/json"})
        @POST("MemberInfoInterface")
        Call<JsonResp> yzsendverification(@Body RequestBody requestBody);//注册发送验证码

        @Headers({"Content-Type:application/json","Accept:application/json"})
        @POST("MemberInfoInterface")
        Call<JsonResp> regist(@Body RequestBody requestBody);//注册

        @Headers({"Content-Type:application/json","Accept:application/json"})
        @POST("CommodityInterface")
        Call<JsonResp> Inventory(@Body RequestBody requestBody);//商品明细

        @Headers({"Content-Type:application/json","Accept:application/json"})
        @POST("CommodityInterface")
        Call<JsonResp> Category_name(@Body RequestBody requestBody);//商品明细

        @Headers({"Content-Type:application/json","Accept:application/json"})
        @POST("MemberInfoInterface")
        Call<JsonResp> login(@Body RequestBody requestBody);//登陆成功跳转主页

        @Headers({"Content-Type:application/json","Accept:application/json"})
        @POST("MemberInfoInterface")
        Call<JsonResp> modified_pwd(@Body RequestBody requestBody);//登陆成功跳转主页

        @Headers({"Content-Type:application/json","Accept:application/json"})
        @POST("CommodityInterface")
        Call<JsonResp> event(@Body RequestBody requestBody);//登陆成功跳转主页

        @Headers({"Content-Type:application/json","Accept:application/json"})
        @POST("CommodityInterface")
        Call<JsonResp> shop_detail(@Body RequestBody requestBody);//商品详情页

        @Headers({"Content-Type:application/json","Accept:application/json"})
        @POST("MemberInfoInterface")
        Call<JsonResp> add_address(@Body RequestBody requestBody);//会员收货地址添加

        @Headers({"Content-Type:application/json","Accept:application/json"})
        @POST("OrderInfoInterface")
        Call<JsonResp> no_all_address(@Body RequestBody requestBody);//查询所有未删除的地址

        @Headers({"Content-Type:application/json","Accept:application/json"})
        @POST("CommodityInterface")
        Call<JsonResp> shop_all_pinglun(@Body RequestBody requestBody);//查询全部评论

    }


    /**
     * 查询所有未删除的地址
     *
     * @param requestBody
     * @return
     */
    public Call<JsonResp> shop_all_pinglun(RequestBody requestBody){
        return netInterface.shop_all_pinglun(requestBody);
    }

    /**
     * 查询所有未删除的地址
     *
     * @param requestBody
     * @return
     */
    public Call<JsonResp> no_all_address(RequestBody requestBody){
        return netInterface.no_all_address(requestBody);
    }


    /**
     * 用户管理相关
     *
     * @param requestBody
     * @return
     */
    public Call<BaseResp> memberInfo(RequestBody requestBody){
        return netInterface.memberInfo(requestBody);
    }

    /**
     * 广告
     * @param requestBody
     * @return
     */
    public Call<JsonResp> advertisement(RequestBody requestBody){
        return netInterface.advertisement(requestBody);
    }

    /**
     * 修改密码
     * @param requestBody
     * @return
     */
    public Call<JsonResp> modified_pwd(RequestBody requestBody){
        return netInterface.modified_pwd(requestBody);
    }


    /**
     * 注册发送验证码
     * @param requestBody
     * @return
     */
    public Call<JsonResp> sendverification(RequestBody requestBody){
        return netInterface.sendverification(requestBody);
    }

    /**
     * 验证验证码，并跳转
     * @param requestBody
     * @return
     */
    public Call<JsonResp> yzsendverification(RequestBody requestBody){
        return netInterface.yzsendverification(requestBody);
    }

    /**
     * 祖册
     * @param requestBody
     * @return
     */
    public Call<JsonResp> regist(RequestBody requestBody){
        return netInterface.regist(requestBody);
    }

    /**
     *
     * @param requestBody
     * @return
     */
    public Call<JsonResp> Inventory(RequestBody requestBody){
        return netInterface.Inventory(requestBody);
    }

    /**
     * 分类名称
     * @param requestBody
     * @return
     */
    public Call<JsonResp> Category_name(RequestBody requestBody){
        return netInterface.Category_name(requestBody);
    }

    /**
     * 活动
     * @param requestBody
     * @return
     */
    public Call<JsonResp> event(RequestBody requestBody){
        return netInterface.event(requestBody);
    }

    /**
     * 商品详情页
     * @param requestBody
     * @return
     */
    public Call<JsonResp> shop_detail(RequestBody requestBody){
        return netInterface.shop_detail(requestBody);
    }

    /**
     * 会员收货地址添加
     * @param requestBody
     * @return
     */
    public Call<JsonResp> add_address(RequestBody requestBody){
        return netInterface.add_address(requestBody);
    }

    /**
     * 分类名称
     * @param requestBody
     * @return
     */
    public Call<JsonResp> login(RequestBody requestBody){
        return netInterface.login(requestBody);
    }





    /**
     * 保养相关
     * @param requestBody
     * @return
     */
    public Call<BaseResp> maintenance(RequestBody requestBody) {
        return netInterface.maintenance(requestBody);
    }

    /**
     * 商品相关
     * @param requestBody
     * @return
     */
    public Call<BaseResp> commodity(RequestBody requestBody) {
        return netInterface.commodity(requestBody);
    }

    /**
     * 订单管理相关
     * @param requestBody
     * @return
     */
    public  Call<BaseResp> orderInfo(RequestBody requestBody) {
       return netInterface.orderInfo(requestBody);
    }

    /**
     * 文件上传相关
     * @param requestBody
     * @return
     */
    public Call<BaseResp> upload(RequestBody requestBody) {
        return netInterface.upload(requestBody);
    }

}
