package com.proton.bystone.pay;


import org.json.JSONObject;

import com.proton.bystone.R;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * 微信支付
 */

public class PayActivity extends Activity {
	
	private IWXAPI api;
	PayReq req;
	String appid;
	String Partnerid ;
	String Prepayid ;
	String PackageX ;
	String Noncestr ;
	int	Timestamp;
	String sign ;
	int time;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay);


	/*	appid = getIntent().getStringExtra("appid");
		 Partnerid = getIntent().getStringExtra("Partnerid");
		Prepayid = getIntent().getStringExtra("Prepayid");
		PackageX = getIntent().getStringExtra("PackageX");
		 Noncestr = getIntent().getStringExtra("Noncestr");
		 Timestamp = getIntent().getStringExtra("Timestamp");
		 sign = getIntent().getStringExtra("sign");*/



		Intent intent=this.getIntent();
		Bundle bundle=intent.getExtras();
		appid =bundle.getString("appid");
		Partnerid =bundle.getString("Partnerid");
		Prepayid =bundle.getString("Prepayid");
		PackageX =bundle.getString("PackageX");
		Noncestr =bundle.getString("Noncestr");
		Timestamp =bundle.getInt("Timestamp");
		sign =bundle.getString("sign");






		api = WXAPIFactory.createWXAPI(this, appid);
        api.registerApp(appid);
		Button appayBtn = (Button) findViewById(R.id.appay_btn);
		appayBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {


				try{

							req = new PayReq();

					        req.appId			= appid;
					        req.nonceStr		= Noncestr;
					        req.packageValue	= PackageX;
							req.partnerId		= Partnerid;
							req.prepayId		= Prepayid;
					        req.sign			= sign;
							req.timeStamp		= Timestamp+"";



					//	req.extData			= "app data"; // optional

					Toast.makeText(PayActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
							api.sendReq(req);

                    Log.e("aaaa","req.appId   "+req.appId+"      req.partnerId   "+req.partnerId+"       req.prepayId   "+req.prepayId	+"      req.nonceStr   "
							+req.nonceStr	+"      req.sign   "+req.sign+"      req.timeStamp   "+req.timeStamp+"     req.packageValue   "+req.packageValue);


		        }catch(Exception e){
		        	Log.e("PAY_GET", "error"+e.getMessage());
		        	Toast.makeText(PayActivity.this, "error"+e.getMessage(), Toast.LENGTH_SHORT).show();
		        }

			}
		});		

	}


	
}
