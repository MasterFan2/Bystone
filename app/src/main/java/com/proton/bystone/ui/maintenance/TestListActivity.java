package com.proton.bystone.ui.maintenance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.proton.bystone.R;
import com.proton.bystone.bean.BaseResp;
import com.proton.bystone.bean.TestChildBean;
import com.proton.bystone.bean.TestParentBean;
import com.proton.bystone.net.HttpClients;
import com.proton.bystone.net.ParamsBuilder;
import com.proton.bystone.utils.L;
import com.proton.bystone.utils.T;
import com.proton.library.ui.MTFBaseActivity;
import com.proton.library.ui.annotation.MTFActivityFeature;
import com.proton.library.widget.MyExpandListView;
import com.proton.library.widget.MyListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 检查单
 */
@MTFActivityFeature(layout = R.layout.activity_test_list )
public class TestListActivity extends MTFBaseActivity {

    @Bind(R.id.test_list_expandable_list_view)
    MyExpandListView expandListView;
    private MyAdapter adapter;
    private List<TestParentBean> parents = null;
    private Map<String, List<TestChildBean>> map = null;

    @Bind(R.id.test_list_suggest_list_view)
    MyListView       suggestListView;

    @Bind(R.id.test_list_other_list_view)
    MyListView       otherListView;

    @Bind(R.id.test_list_confirm_btn)
    Button           confirmBtn;


    String code ;
    @Override
    public void initialize(Bundle savedInstanceState) {
        adapter = new MyAdapter();
        expandListView.setAdapter(adapter);
        expandListView.setGroupIndicator(null);

         code = getIntent().getStringExtra("code");

        getData();
    }

    private void getData() {
        parents = new ArrayList<>();
        parents.add(new TestParentBean("机油"));
        parents.add(new TestParentBean("机油滤芯"));
        parents.add(new TestParentBean("空气滤芯"));

        map = new HashMap<>();
        ArrayList<TestChildBean> childBeenList = new ArrayList<>();

        TestChildBean childBean = new TestChildBean("车事通机油", "￥432.00");
        childBeenList.add(childBean);

        childBean = new TestChildBean("车事通SNow-40机油", "￥402.00");
        childBeenList.add(childBean);
        map.put("机油", childBeenList);

        childBeenList = new ArrayList<>();
        childBean = new TestChildBean("机油滤芯ST-007", "￥592.00");
        childBeenList.add(childBean);
        map.put("机油滤芯", childBeenList);

        childBeenList = new ArrayList<>();
        childBean = new TestChildBean("车事通空气油SM-998", "￥600.00");
        childBeenList.add(childBean);
        map.put("空气滤芯", childBeenList);

        adapter.notifyDataSetChanged();
        for (int i = 0; i < parents.size(); i++) {
            expandListView.expandGroup(i);
        }

        //提交预定信息
        final RequestBody requestBody = new ParamsBuilder<>()
                .key("pbevyvHkf1sFtyGL35gFfQ==")
                .methodName("MaintenanceDetectionOfSingle")
                .gson(new Gson())
                .typeValue("string", code)
                .build();
        Call<BaseResp> call = HttpClients.getInstance().maintenance(requestBody);
        call.enqueue(new Callback<BaseResp>() {
            @Override
            public void onResponse(Call<BaseResp> call, Response<BaseResp> response) {
//                List<CarModel> carModels = new Gson().fromJson(response.body().getData(), new TypeToken<List<CarModel>>() {}.getType());
                if (response.body().getCode() == 1) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().getData());
//                        orderBaseInfos   = new Gson().fromJson(jsonArray.get(0).toString(), new TypeToken<List<OrderBaseInfo>>() {}.getType());
//                        orderStateInfos = new Gson().fromJson(jsonArray.get(1).toString(), new TypeToken<List<OrderStateInfo>>() {}.getType());
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    T.s(context, "预约成功");
                }else {
                    T.s(context, "预约失败");
                }
            }

            @Override
            public void onFailure(Call<BaseResp> call, Throwable t) {
                L.e("getThirdLevelYear::" + t.getMessage());
            }
        });
    }

    class MyAdapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            return parents == null ? 0 : parents.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            String key = parents.get(groupPosition).getName();
            return map == null ? 0 : map.get(key).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return null;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            final ParentHolder holder;
            final TestParentBean parentBean = parents.get(groupPosition);
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_expand_parent, parent, false);
                holder = new ParentHolder(convertView);
                convertView.setTag(holder);
            }else {
                holder = (ParentHolder) convertView.getTag();
            }

            //
            holder.tv.setText(parentBean.getName());
            if (parentBean.isChecked()) {
                holder.cbView.setBackgroundResource(R.mipmap.icon_checkbox_sel);
            }else  {
                holder.cbView.setBackgroundResource(R.mipmap.icon_checkbox_nor);
            }

            holder.rootLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final boolean checked = parentBean.isChecked();
                    final List<TestChildBean> list = map.get(parentBean.getName());
                    if (checked)  { //父标签选中  所有子标签选中
                        final int size = list.size();
                        for (int i = 0; i < size; i++) {
                            TestChildBean childBean = list.get(i);
                            childBean.setChecked(false);
                        }
                        parentBean.setChecked(false);
                    } else {
                        final int size = list.size();
                        for (int i = 0; i < size; i++) {
                            TestChildBean childBean = list.get(i);
                            childBean.setChecked(true);
                        }
                        parentBean.setChecked(true);
                    }
                    adapter.notifyDataSetChanged();
                }
            });
            //
            return convertView;
        }

        @Override
        public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
            final ChildHolder holder;
            final String key = parents.get(groupPosition).getName();
            final List<TestChildBean> childList = map.get(key);
            final TestChildBean childBean = childList.get(childPosition);
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_expand_child, parent, false);
                holder = new ChildHolder(convertView);
                convertView.setTag(holder);
            }else {
                holder = (ChildHolder) convertView.getTag();
            }

            //
            holder.nameTxt.setText(childBean.getName());
            holder.priceTxt.setText(childBean.getPrice());
            if (childBean.isChecked()) {
                holder.rbView.setBackgroundResource(R.mipmap.icon_radio_blue_sel);
            }else  {
                holder.rbView.setBackgroundResource(R.mipmap.icon_radio_blue_nor);
            }

            holder.rootLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //选中 / 取消选中
                    if (childBean.isChecked()) childBean.setChecked(false);
                    else                       childBean.setChecked(true);

                    int count = 0;
                    final int size = childList.size();
                    for (int i = 0; i < size; i++) {
                        if (childList.get(i).isChecked()) {
                            count ++ ;
                        }
                    }

                    if (count >= size) {//所有选中 ， 父标签选中、取消
                        parents.get(groupPosition).setChecked(true);
                    } else {
                        parents.get(groupPosition).setChecked(false);
                    }

                    adapter.notifyDataSetChanged();
                }
            });
            //
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

        class ParentHolder {

            @Bind(R.id.item_test_list_expand_parent_root)
            LinearLayout rootLayout;

            @Bind(R.id.item_test_list_expand_parent_view)
            View cbView;

            @Bind(R.id.item_test_list_expand_name_parent_txt)
            TextView tv;
            public ParentHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

        class ChildHolder {

            @Bind(R.id.item_test_list_expand_child_root)
            LinearLayout rootLayout;

            @Bind(R.id.item_test_list_expand_child_view)
            View rbView;

            @Bind(R.id.item_test_list_expand_child_name_txt)
            TextView    nameTxt;

            @Bind(R.id.item_test_list_expand_child_price_txt)
            TextView    priceTxt;

            public ChildHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    @Override
    public void backPressed() {
        animFinish();
    }

    @OnClick(R.id.m_title_left_btn)
    public void back(View view) {
        animFinish();
    }
}
