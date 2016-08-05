package com.proton.bystone.wxapi;


import com.proton.bystone.R;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		Log.e("aaavvv","88888");
		api = WXAPIFactory.createWXAPI(this, "wx80cb910cfa56f7a6");
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {


		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX)
		{

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示");
			builder.setMessage(getString(R.string.pay_result_callback_msg,                                             String.valueOf(resp.errCode)));
			builder.show();

		}
	}
}