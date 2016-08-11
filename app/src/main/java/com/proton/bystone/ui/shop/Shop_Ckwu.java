package com.proton.bystone.ui.shop;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proton.bystone.R;
import com.proton.bystone.bean.Jwul;
import com.proton.bystone.bean.Wldh;
import com.proton.bystone.pay.KdniaoTrackQueryAPI;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;

import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Created by Administrator on 2016/8/9.
 */
@MTFActivityFeature(layout = R.layout.shop_ck)
public class Shop_Ckwu extends MTFBaseActivity {

    String expressId;
    String   ExpressSimple;
    List<Jwul> jbnum;
    ListView  shop_listvie;
    Wldh   widj;
    TextView tv;
    TextView   shop_y;
    List<Jwul.TracesBean> traces;
    String orderTracesByJson;
    OutputStreamWriter out = null;
    BufferedReader in = null;
    StringBuilder result = new StringBuilder();
    @Override
    public void initialize(Bundle savedInstanceState) {

        expressId = getIntent().getStringExtra("ExpressId");//物流单号
        ExpressSimple = getIntent().getStringExtra("ExpressSimple");//快递公司编号
        Log.e("expressSimple",ExpressSimple+expressId);
        Listener();
    }

    @Override
    public void backPressed() {
       animFinish();
    }


    public void Listener()
    {

       tv =(TextView) findViewById(R.id.shop_id);//韵达
       shop_y =(TextView) findViewById(R.id.shop_y);//运单编号
        shop_listvie =(ListView) findViewById(R.id.shop_listvie);//listview


        String bh="2154260514062466";
        String ccc="1000742260903";
        String yd="YD";


        KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI();
        try {

            Log.e("result","1111");
            String result = api.getOrderTracesByJson(ExpressSimple,expressId);
            Log.e("result2222",result);

            jiexi(result);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void jiexi(String result)
    {
         jbnum=new Gson().fromJson(result, new TypeToken<List<Jwul>>() {}.getType());
         tv.setText(jbnum.get(0).getShipperCode());
         shop_y.setText(jbnum.get(0).getLogisticCode());
         traces = jbnum.get(0).getTraces();


       shop_listvie.setAdapter(new GridViewadapter2());


    }
    class GridViewadapter2 extends BaseAdapter
    {


        @Override
        public int getCount() {
            // TODO Auto-generated method stub

            return  traces == null ? 0 : traces.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return traces.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            viewHolder1 holder;
            if (convertView == null) {
                convertView = View.inflate(Shop_Ckwu.this, R.layout.shlistview, null);
                holder = new viewHolder1();
                holder.AcceptTime = (TextView) convertView.findViewById(R.id.shop_wc);
                holder.AcceptStation = (TextView) convertView.findViewById(R.id.shop_data);

                convertView.setTag(holder);
            }else{
                holder = (viewHolder1) convertView.getTag();
            }

            holder.AcceptTime.setText(traces.get(position).getAcceptStation());
            holder.AcceptStation.setText(traces.get(position).getAcceptTime());

            return convertView;
        }
    }


    class viewHolder1
    {
        TextView AcceptTime;
        TextView AcceptStation;
    }
}
