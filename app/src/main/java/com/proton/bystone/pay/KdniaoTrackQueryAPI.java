package com.proton.bystone.pay;

import android.util.Log;

import com.proton.bystone.pay.app.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map; 



public class KdniaoTrackQueryAPI {


	String bh="2154260514062466";
	String ccc="1000742260903";
	String yd="YD";
	String result;
	Map<String, String> params;

	//电商ID
	   private String EBusinessID="1262454";
	   //电商加密私钥，快递鸟提供，注意保管，不要泄漏
		    private String AppKey="81a77140-818e-4f7d-a4b5-bd236d7e0c64";
	  //请求url
			    private String ReqURL="http://api.kdniao.cc/Ebusiness/EbusinessOrderHandle.aspx";

	/**
	 49      * Json方式 查询订单物流轨迹
	 50      * @throws Exception
	 51      */
	public String getOrderTracesByJson(String expCode, String expNo) throws Exception{
		String requestData= "{'OrderCode':'2154260514062466','ShipperCode':'" + yd + "','LogisticCode':'" + ccc + "'}";

		params = new HashMap<String, String>();
		params.put("RequestData", urlEncoder(requestData, "UTF-8"));
		params.put("EBusinessID", EBusinessID);
		params.put("RequestType", "1002");
		String dataSign=encrypt(requestData, AppKey, "UTF-8");
		params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
		params.put("DataType", "2");

		Thread thread=new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				 result=sendPost(ReqURL, params);
			}
		});
		thread.start();


		//根据公司业务处理返回的信息......

		return result;
	}


	/**
	 98      * MD5加密
	 99      * @param str 内容
	 100      * @param charset 编码方式
	 101      * @throws Exception
	 102      */
	@SuppressWarnings("unused")
	private String MD5(String str, String charset) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(str.getBytes(charset));
		byte[] result = md.digest();
		StringBuffer sb = new StringBuffer(32);
		for (int i = 0; i < result.length; i++) {
			int val = result[i] & 0xff;
			if (val <= 0xf) {
				sb.append("0");
			}
			sb.append(Integer.toHexString(val));
		}
		return sb.toString().toLowerCase();
	}

	/**
	 120      * base64编码
	 121      * @param str 内容
	 122      * @param charset 编码方式
	 123      * @throws UnsupportedEncodingException
	 124      */
	private String base64(String str, String charset) throws UnsupportedEncodingException{
		String encoded = base64Encode(str.getBytes(charset));
		return encoded;
	}

	@SuppressWarnings("unused")
	private String urlEncoder(String str, String charset) throws UnsupportedEncodingException{
		String result = URLEncoder.encode(str, charset);
		return result;
	}

	/**
	 137      * 电商Sign签名生成
	 138      * @param content 内容
	 139      * @param keyValue Appkey
	 140      * @param charset 编码方式
	 141      * @throws UnsupportedEncodingException ,Exception
	 142      * @return DataSign签名
	 143      */
	@SuppressWarnings("unused")
	private String encrypt (String content, String keyValue, String charset) throws UnsupportedEncodingException, Exception
	{
		if (keyValue != null)
		{
			return base64(MD5(content + keyValue, charset), charset);
		}
		return base64(MD5(content, charset), charset);
	}



		/**
		 155      * 鍚戞寚瀹?URL 鍙戦€丳OST鏂规硶鐨勮姹?
		 156      * @param url 鍙戦€佽姹傜殑 URL
		 157      * @param params 璇锋眰鐨勫弬鏁伴泦鍚?
		 158      * @return 杩滅▼璧勬簮鐨勫搷搴旂粨鏋?
		 159      */
		@SuppressWarnings("unused")
		private String sendPost(String url, Map<String, String> params) {
			OutputStreamWriter out = null;
			BufferedReader in = null;
			StringBuilder result = new StringBuilder();
			try {
				URL realUrl = new URL(url);
				HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
				// 鍙戦€丳OST璇锋眰蹇呴』璁剧疆濡備笅涓よ
				conn.setDoOutput(true);
				conn.setDoInput(true);
				// POST鏂规硶
				conn.setRequestMethod("POST");
				// 璁剧疆閫氱敤鐨勮姹傚睘鎬?
				conn.setRequestProperty("accept", "**/*//*");
				conn.setRequestProperty("connection", "Keep-Alive");
				conn.setRequestProperty("user-agent",
						"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.connect();
				// 鑾峰彇URLConnection瀵硅薄瀵瑰簲鐨勮緭鍑烘祦
				out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
				// 鍙戦€佽姹傚弬鏁?
				if (params != null) {
					StringBuilder param = new StringBuilder();
					for (Map.Entry<String, String> entry : params.entrySet()) {
						if(param.length()>0){
							param.append("&");
						}
						param.append(entry.getKey());
						param.append("=");
						param.append(entry.getValue());
						//System.out.println(entry.getKey()+":"+entry.getValue());
					}
					//System.out.println("param:"+param.toString());
					out.write(param.toString());
				}
				// flush杈撳嚭娴佺殑缂撳啿
				out.flush();
				// 瀹氫箟BufferedReader杈撳叆娴佹潵璇诲彇URL鐨勫搷搴?
				in = new BufferedReader(
						new InputStreamReader(conn.getInputStream(), "UTF-8"));
				String line;
				while ((line = in.readLine()) != null) {
					result.append(line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			//浣跨敤finally鍧楁潵鍏抽棴杈撳嚭娴併€佽緭鍏ユ祦
			finally{
				try{
					if(out!=null){
						out.close();
					}
					if(in!=null){
						in.close();
					}
				}
				catch(IOException ex){
					ex.printStackTrace();
				}
			}
			return result.toString();
		}


	private static char[] base64EncodeChars = new char[] {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
			'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
			'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
			'w', 'x', 'y', 'z', '0', '1', '2', '3',
			'4', '5', '6', '7', '8', '9', '+', '/' };

	public static String base64Encode(byte[] data) {
		StringBuffer sb = new StringBuffer();
		int len = data.length;
		int i = 0;
		int b1, b2, b3;
		while (i < len) {
			b1 = data[i++] & 0xff;
			if (i == len)
			{
				sb.append(base64EncodeChars[b1 >>> 2]);
				sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
				sb.append("==");
				break;
			}
			b2 = data[i++] & 0xff;
			if (i == len)
			{
				sb.append(base64EncodeChars[b1 >>> 2]);
				sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
				sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
				sb.append("=");
				break;
			}
			b3 = data[i++] & 0xff;
			sb.append(base64EncodeChars[b1 >>> 2]);
			sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
			sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
			sb.append(base64EncodeChars[b3 & 0x3f]);
		}
		return sb.toString();
	}
}

