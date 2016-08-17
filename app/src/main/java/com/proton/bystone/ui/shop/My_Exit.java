package com.proton.bystone.ui.shop;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.RecognizeCardsinfoItem;
import com.proton.bystone.bean.RecognizeResp;
import com.proton.bystone.bean.UploadResp;
import com.proton.bystone.cache.LoginManager;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.ui.common.AddCarConfirmActivity;
import com.proton.bystone.ui.login.NewpwdActivity;
import com.proton.bystone.ui.main.MainActivity;
import com.proton.bystone.ui.main.tab.MeFragment;
import com.proton.bystone.ui.shop.phone.MainActivity2;
import com.proton.bystone.utils.T;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;
import com.proton.library.utils.ActivityManager;
import com.proton.library.widget.dialog.KProgressHUD;
import com.proton.library.widget.photopicker.PhotoPickerActivity;
import com.proton.library.widget.photopicker.utils.PhotoPickerIntent;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/27.
 * 可退出界面
 */

@MTFActivityFeature(layout = R.layout.my_exit)
public class My_Exit extends MTFBaseActivity {
    @Bind(R.id.my_exit)
    Button my_exit;
    @Bind(R.id.shs)
    ImageView shs;


    @Bind(R.id.my_picture)
    ImageView my_picture;
    Bitmap    bitmap;
    private KProgressHUD progressHUD;



    @Bind(R.id.shop_setting)
    RelativeLayout shop_setting;
    @Bind(R.id.add_my_car_front_img)
    ImageView frontImg;

    @Bind(R.id.add_my_car_copy_img)
    ImageView copyImg;


    //上传服务器成功后返回的正照和副照的URL地址
    private String frontServerUrl = null;
    private String copyServerUrl  = null;
    ArrayList<RecognizeCardsinfoItem> items = null;//识别结果

    ArrayList<String> frontList = new ArrayList<>();//正照
    private ArrayList<String> copyList = new ArrayList<>(); //副照




    @Override
    public void initialize(Bundle savedInstanceState) {

//密码设置
        shop_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent t=new Intent(My_Exit.this, NewpwdActivity.class);
                startActivity(t);
            }
        });

        my_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               LoginManager.getInstance().delLoginInfo();
               finish();
            }
        });

        my_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent t=new Intent(My_Exit.this, MainActivity2.class);
                startActivity(t);*/

                PhotoPickerIntent intent = new PhotoPickerIntent(context);
                intent.setPhotoCount(1);
                intent.setShowCamera(true);
                startActivityForResult(intent, 9527);

            }
        });

/*        shs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             *//*   Intent t=new Intent(My_Exit.this, MainActivity2.class);
                startActivity(t);*//*

                PhotoPickerIntent intent = new PhotoPickerIntent(context);
                intent.setPhotoCount(1);
                intent.setShowCamera(true);
                startActivityForResult(intent, 9528);

            }
        });*/


        progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("正在识别,请稍后...")
                .setDimAmount(0.4f)
                .setCancellable(false);

        //添加到Activity管理器中
        ActivityManager.getInstance().addActivity(this);
    }

    @Override
    public void backPressed() {
        animFinish();
    }



    @butterknife.OnClick(R.id.m_title_left_btn)
    public void back(View view) {

        animFinish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Intent intent=getIntent();
         bitmap=intent.getParcelableExtra("bitmap");
        String xy = intent.getStringExtra("xy");
        if(!TextUtils.isEmpty(xy)) {
            my_picture.setImageBitmap(bitmap);
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        List<String> photos = null;
        if (resultCode == RESULT_OK && requestCode == 9527) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            }

            frontList.clear();

            if (photos != null) {
                frontList.addAll(photos);
                Picasso.with(context).load(new File(frontList.get(0))).placeholder(R.mipmap.ic_launcher).into(my_picture);
            }

        } /*else if (resultCode == RESULT_OK && requestCode == 9528) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);

            }
            copyList.clear();

            if (photos != null) {
                copyList.addAll(photos);
                Picasso.with(context).load(new File(copyList.get(0))).placeholder(R.mipmap.ic_launcher).into(copyImg);

            }
        }*/
    }


    /**
     * 上传照片识别
     *
     * @param v
     */
    @OnClick(R.id.m_title_right_btn)
    public void recognizePic(View v) {
      if (frontList == null || frontList.size() <= 0) {
            T.s(context, "请选择驾驶证正本照片上传");
            return;
        }

        Log.e("1111","1111");
  /*     if (copyList == null || copyList.size() <= 0) {
            T.s(context, "请选择驾驶证副本照片上传");
            return;
        }*/

       progressHUD.show();//显示等待框,提示正在识别...


        upload();

       /* //识别参数包装
        File file = new File(frontList.get(0));
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestBodyMap.put("key", RequestBody.create(MediaType.parse("text/plain"), "SWtv4pbBpuzGQTDECa6gp"));
        requestBodyMap.put("secret", RequestBody.create(MediaType.parse("text/plain"), "15ef84ef51a2481395ac847a7e5f4745"));
        requestBodyMap.put("typeId", RequestBody.create(MediaType.parse("text/plain"), "6"));
        requestBodyMap.put("format", RequestBody.create(MediaType.parse("text/plain"), "json"));*/
        //requestBodyMap.put("data\"; filename=\""+file.getName()+"", RequestBody.create(MediaType.parse("image/*"), file));


        // creates RequestBody instance from file
       /* RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual filename
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        Call<RecognizeResp> call = HttpClients.getInstance().recognize(requestBodyMap, body);
        call.enqueue(new Callback<RecognizeResp>() {
            @Override
            public void onResponse(Call<RecognizeResp> call, Response<RecognizeResp> response) {

                if (response != null && response.body() != null) {
                    List<RecognizeResp.CardsinfoBean> cardsinfoBeanList = response.body().getCardsinfo();
                    if (cardsinfoBeanList != null && cardsinfoBeanList.size() > 0) {//识别成功
                        progressHUD.setLabel("正在上传到服务器...");
                        items = response.body().getCardsinfo().get(0).getItems();
                        upload();
                    } else {        //识别失败
                        recognizeFailure();
                    }

                } else {
                    recognizeFailure();
                }
            }*/
/*
            @Override
            public void onFailure(Call<RecognizeResp> call, Throwable t) {
                if (progressHUD.isShowing()) progressHUD.dismiss();
                T.s(context, "识别失败");
            }
        });*/
    }


    /**
     * 提示识别失败
     */
    private void recognizeFailure() {

        if (progressHUD.isShowing()) progressHUD.dismiss();

        new AlertDialog.Builder(context).setTitle("识别结果提示")
                .setMessage("您上传的照片无法成功识别，请调整角度重新拍摄高清照片或手动输入行驶证信息")
                .setPositiveButton("重新上传照片", null)
                .setNegativeButton("手动输入", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        upload();
//                        Intent intent = new Intent(context, AddCarConfirmActivity.class);
//                        intent.putParcelableArrayListExtra("items", null);
//                        animStart(intent);
                    }
                })
                .create().show();
    }





    private void upload() {

        File frontfile = new File(frontList.get(0));
        //File copyFile = new File(copyList.get(0));

        //上传头像
        RequestBody requestFrontFile = RequestBody.create(MediaType.parse("multipart/form-data"), frontfile);
        MultipartBody.Part requestFrontBody = MultipartBody.Part.createFormData("front", frontfile.getName(), requestFrontFile);

        List<MultipartBody.Part> parts = new ArrayList<>();
        parts.add(requestFrontBody);

        //上传
        Call<BaseResp> call = HttpClients.getInstance().upload2(parts);
        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
                if (progressHUD.isShowing()) progressHUD.dismiss();
                if (response == null || response.body() == null || response.body().getCode() == 1) {
                    List<UploadResp> uploadRespList = new Gson().fromJson(response.body().getData(), new TypeToken<List<UploadResp>>() {
                    }.getType());
                    frontServerUrl = uploadRespList.get(0).getOriginalUrl();

                    Toast.makeText(My_Exit.this,"上传成功",Toast.LENGTH_SHORT).show();
                    String data=response.body().getData();

                    //拿到fragement
/*                    FragmentManager fm = getSupportFragmentManager();
                    MeFragment me =(MeFragment) fm.findFragmentByTag("me");
                    me.setdata(data);*/

                } else {
                    T.s(context, "上传失败");



                }
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                if (progressHUD.isShowing()) progressHUD.dismiss();
                T.s(context, "上传失败,请稍后重试");
            }
        });
    }

}
