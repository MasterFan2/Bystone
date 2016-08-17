package com.proton.bystone.ui.shop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.proton.bystone.R;
import com.proton.bystone.pay.yinlian.BBaseActivity;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

/**
 * Created by Administrator on 2016/8/15.
 */
@MTFActivityFeature(layout = R.layout.webview)
public class WebActivity extends MTFBaseActivity {
    @Override
    public void initialize(Bundle savedInstanceState) {
        WebView webView=(WebView) findViewById(R.id.webView);
        String url = getIntent().getStringExtra("url");
        Log.e("url",url);
        webView.loadUrl(url);

}

    @Override
    public void backPressed() {

    }
}
