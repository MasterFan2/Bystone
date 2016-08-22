package com.proton.bystone.ui.common;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.CarInfo;
import com.proton.bystone.bean.LoginParams;
import com.proton.bystone.bean.RecognizeCardsinfoItem;
import com.proton.bystone.bean.RecognizeResp;
import com.proton.bystone.bean.ReservationParam;
import com.proton.bystone.bean.UploadParams;
import com.proton.bystone.bean.UploadResp;
import com.proton.bystone.config.Config;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.utils.L;
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
 * 添加爱车。
 */
@MTFActivityFeature(layout = R.layout.activity_add_my_car)
public class AddMyCarActivity extends MTFBaseActivity {

    ArrayList<RecognizeCardsinfoItem> items = null;//识别结果
    private KProgressHUD progressHUD;              //等待窗口

    //上传服务器成功后返回的正照和副照的URL地址
    private String frontServerUrl = null;
    private String copyServerUrl  = null;

    @Bind(R.id.add_my_car_camera_img1)
    ImageView cameraFrontImg;

    @Bind(R.id.add_my_car_camera_img2)
    ImageView cameraCopyImg;

    @Bind(R.id.add_my_car_front_img)
    ImageView frontImg;

    @Bind(R.id.add_my_car_copy_img)
    ImageView copyImg;

    @Bind(R.id.add_my_car_front_layout)
    RelativeLayout frontLayout;

    @Bind(R.id.add_my_car_copy_layout)
    RelativeLayout copyLayout;

    @Override
    public void initialize(Bundle savedInstanceState) {
        progressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("正在识别,请稍后...")
                .setDimAmount(0.4f)
                .setCancellable(false);

        //添加到Activity管理器中
        ActivityManager.getInstance().addActivity(this);
    }

    /**
     * 选择正照
     */
    @OnClick(R.id.add_my_car_camera_img1)
    public void cameraFrontClick() {
        PhotoPickerIntent intent = new PhotoPickerIntent(context);
        intent.setPhotoCount(1);
        intent.setShowCamera(true);
        startActivityForResult(intent, 9527);
    }

    /**
     * 选择副照
     */
    @OnClick(R.id.add_my_car_camera_img2)
    public void cameraCopyClick() {
        PhotoPickerIntent intent = new PhotoPickerIntent(context);
        intent.setPhotoCount(1);
        intent.setShowCamera(true);
        startActivityForResult(intent, 9528);
    }

    @Override
    public void backPressed() {
        animFinish();
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
        if (copyList == null || copyList.size() <= 0) {
            T.s(context, "请选择驾驶证副本照片上传");
            return;
        }

        progressHUD.show();//显示等待框,提示正在识别...

        //识别参数包装
        File file = new File(frontList.get(0));
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestBodyMap.put("key", RequestBody.create(MediaType.parse("text/plain"), "SWtv4pbBpuzGQTDECa6gp"));
        requestBodyMap.put("secret", RequestBody.create(MediaType.parse("text/plain"), "15ef84ef51a2481395ac847a7e5f4745"));
        requestBodyMap.put("typeId", RequestBody.create(MediaType.parse("text/plain"), "6"));
        requestBodyMap.put("format", RequestBody.create(MediaType.parse("text/plain"), "json"));
        //requestBodyMap.put("data\"; filename=\""+file.getName()+"", RequestBody.create(MediaType.parse("image/*"), file));


        // creates RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
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
            }

            @Override
            public void onFailure(Call<RecognizeResp> call, Throwable t) {
                if (progressHUD.isShowing()) progressHUD.dismiss();
                T.s(context, "识别失败");
            }
        });
    }

    /**
     * 上传行驶证照片
     */
    private void upload() {

        File frontfile = new File(frontList.get(0));
        File copyFile = new File(copyList.get(0));

        //包装正件照/副件照
        RequestBody requestFrontFile = RequestBody.create(MediaType.parse("multipart/form-data"), frontfile);
        MultipartBody.Part requestFrontBody = MultipartBody.Part.createFormData("front", frontfile.getName(), requestFrontFile);

        RequestBody requestCopyFile = RequestBody.create(MediaType.parse("multipart/form-data"), copyFile);
        MultipartBody.Part requestCopyBody = MultipartBody.Part.createFormData("copy", copyFile.getName(), requestCopyFile);


        List<MultipartBody.Part> parts = new ArrayList<>();
        parts.add(requestFrontBody);
        parts.add(requestCopyBody);

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
                    copyServerUrl = uploadRespList.get(1).getOriginalUrl();

                    Intent intent = new Intent(context, AddCarConfirmActivity.class);
                    intent.putParcelableArrayListExtra("items", items);
                    intent.putExtra("front", frontServerUrl).putExtra("copy", copyServerUrl);
                    animStart(intent);
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

                        progressHUD.setLabel("正在上传...");
                        progressHUD.show();
                        upload();
                    }
                })
                .create().show();
    }

    private ArrayList<String> frontList = new ArrayList<>();//正照
    private ArrayList<String> copyList = new ArrayList<>(); //副照

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
                frontLayout.setVisibility(View.GONE);
                Picasso.with(context).load(new File(frontList.get(0))).placeholder(R.mipmap.ic_launcher).into(frontImg);
            }

        } else if (resultCode == RESULT_OK && requestCode == 9528) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            }
            copyList.clear();

            if (photos != null) {
                copyList.addAll(photos);
                copyLayout.setVisibility(View.GONE);
                Picasso.with(context).load(new File(copyList.get(0))).placeholder(R.mipmap.ic_launcher).into(copyImg);
            }
        }
    }

    @OnClick(R.id.m_title_left_btn)
    public void back(View view) {
        animFinish();
    }
}
