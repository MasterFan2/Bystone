package com.proton.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.proton.library.R;

/**
 * 公共title
 * create by masterFan on 20160802
 */
public class MTFTitleView extends RelativeLayout {

    private TextView leftBtn;

    private TextView rightBtn;

    private TextView contentTxt;

    private View titleView;

    public MTFTitleView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public MTFTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public MTFTitleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    /**
     * initialize
     *
     * @param attrs
     * @param defStyle
     */
    private void init(Context mContext, AttributeSet attrs, int defStyle) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.mtf_title_layout, this);

        leftBtn = (TextView) view.findViewById(R.id.m_title_left_btn);
        rightBtn = (TextView) view.findViewById(R.id.m_title_right_btn);
        contentTxt = (TextView) view.findViewById(R.id.m_title_context_txt);
//        MTViewUtils.inject(this, view);

        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MasterTitleView, defStyle, 0);

        String strRightButtonText = a.getString(R.styleable.MasterTitleView_rightButtonText);
        String strTitleText = a.getString(R.styleable.MasterTitleView_titleText);
        String strLeftText = a.getString(R.styleable.MasterTitleView_leftButtonText);

        boolean leftButtonHide = a.getBoolean(R.styleable.MasterTitleView_leftButtonHide, false);
        boolean rightButtonHide = a.getBoolean(R.styleable.MasterTitleView_rightButtonHide, false);

        int titleTextColor = a.getColor(R.styleable.MasterTitleView_titleTxtColor, -1);
        int titleBackgroundColor = a.getColor(R.styleable.MasterTitleView_titleBackgroundColor, -1);

        //left
        int leftBtnColor = a.getColor(R.styleable.MasterTitleView_leftButtonColor, -1);
        if(leftBtnColor != -1) leftBtn.setTextColor(leftBtnColor);
        if(TextUtils.isEmpty(strLeftText)) {
            leftBtn.setText(strLeftText);
        }

        //right button  [设置的时候 以图片优先]
        int rightButtonIcon      = a.getResourceId(R.styleable.MasterTitleView_rightButtonIcon, -1);//图片
        int rightButtonTextColor = a.getResourceId(R.styleable.MasterTitleView_rightButtonTextColor, -1);//
        if(rightButtonIcon != -1){
            rightBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(rightButtonIcon), null);
        } else if (!TextUtils.isEmpty(strRightButtonText)) {
            rightBtn.setText(strRightButtonText);
            rightBtn.setTextColor(getResources().getColor(rightButtonTextColor));
        } else {
            rightBtn.setVisibility(View.INVISIBLE);
        }

        if(titleBackgroundColor != -1)
            view.findViewById(R.id.title_root_view).setBackgroundColor(titleBackgroundColor);
//        else                              view.findViewById(R.id.title_root_view).setBackgroundColor(titleBackgroundColor);

        //left
        int leftResourceId = a.getResourceId(R.styleable.MasterTitleView_leftButtonResource, -1);
        if(-1 != leftResourceId){
            //leftBtn.setImageResource(leftResourceId);
        }

        if(titleTextColor != -1)
            contentTxt.setTextColor(titleTextColor);
        //
        if (leftButtonHide) hideLeftBtn();
        if (rightButtonHide) hideRightBtn();

        //
        setRightBtnText(strRightButtonText);
        setTitleText(strTitleText);

        //
        a.recycle();
    }

    public void setLeftBtnClickListener(OnClickListener listener) {
        leftBtn.setOnClickListener(listener);
    }

    public void setRightBtnClickListener(OnClickListener listener) {
        rightBtn.setOnClickListener(listener);
    }

    public void setRightBtnText(String text) {
        rightBtn.setText(text);
    }

    public void setTitleText(String text) {
        contentTxt.setText(text);
    }

    public void hideRightBtn() {
        rightBtn.setEnabled(false);
        rightBtn.setVisibility(View.INVISIBLE);
    }

    public  void setLeftBtnText(String txt) {
        leftBtn.setText(txt);
    }

    public void showRightBtn() {
        rightBtn.setEnabled(true);
        rightBtn.setVisibility(View.VISIBLE);
    }


    public void hideLeftBtn() {
        leftBtn.setVisibility(View.INVISIBLE);
    }
}