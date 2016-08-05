package com.proton.bystone.ui.common;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.proton.bystone.R;
import com.proton.bystone.bean.Version;
import com.proton.bystone.ui.main.MainActivity;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;
import com.proton.library.widget.round.RoundCornerProgressBar;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 软件检测更新
 */
@MTFActivityFeature(layout = R.layout.activity_up_version, status_bar_color = R.color.blue)
public class UpVersionActivity extends MTFBaseActivity {

    public static final int FORCE_UPDATE_CANCEL = 0x07;

    @Bind(R.id.progressBar)
    RoundCornerProgressBar progressBar;

    @Bind(R.id.up_version_update_btn)
    Button updateBtn;

    @Bind(R.id.up_version_percent_txt)
    TextView percentTxt;

//    @Bind(R.id.up_version_root)
//    LinearLayout rootLayout;

    private String saveFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/venus.apk";

    //-1:默认状态
    // 0:下载成功
    // 1:正在下载
    // 2:下载错误
    // 3:下载取消
    private int download_status = -1;

    private Version version = null;

    @Override
    public void initialize(Bundle savedInstanceState) {
        version = getIntent().getParcelableExtra("version");
        if (null == version) {
            animFinish();
        }
        percentTxt.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Roboto-Light.ttf"));
    }

    @Override
    public void backPressed() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void doDownload(View view) {
        view.setEnabled(false);

        RequestParams params = new RequestParams("http://sw.bos.baidu.com/sw-search-sp/software/127d5077feac7/2345pic_6.3.1.7488.exe");
        params.setAutoRename(false);
        x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {
                progressBar.setProgress(0f);
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                download_status = 1;
                float percent = (current / (float) total * 100);
                percentTxt.setText("[" + ((int)percent) + "%]");
                progressBar.setProgress(percent);
            }

            @Override
            public void onSuccess(File result) {
                download_status = 0;
                updateBtn.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                percentTxt.setVisibility(View.GONE);
                updateBtn.setText("点击安装");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                download_status = 2;
            }

            @Override
            public void onCancelled(CancelledException cex) {
                download_status = 3;
            }

            @Override
            public void onFinished() {
                download_status = 0;
            }
        });
    }


    @OnClick(R.id.up_version_close_txt)
    public void close(View v) {
        if (version.getWhetherToForce() == 1) {//强制更新
            new AlertDialog.Builder(context).setTitle("提示您")
                    .setMessage("您的版本过旧, 放弃更新将不能正常使用， 确定退出?")
//                    .setView(view)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


//                            tilesFrameLayout.startAnimation();
                            setResult(FORCE_UPDATE_CANCEL);
                            animFinish();
//                            popField.popView(percentTxt);
                        }
                    })
                    .setNegativeButton("取消", null)
                    .create()
                    .show();
        } else {//可选更新
            animStart(MainActivity.class);
        }
    }

    public void startDownload(View view) {
        if (download_status == 0) {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(saveFile)), "application/vnd.android.package-archive");
            startActivityForResult(intent, 9528);
            setResult(FORCE_UPDATE_CANCEL);
            finish();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            percentTxt.setVisibility(View.VISIBLE);
            doDownload(view);
        }
    }
}
