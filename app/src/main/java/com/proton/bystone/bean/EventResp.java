package com.proton.bystone.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/7/19.
 * 今日特价
 */
public class EventResp implements Serializable {


    /**
     * EventName : 今日特价
     * data : [{"Title":"今日特价","N_HYJ":"178.0000","N_FHYJ":"238.0000","VC_Code":"2016052317205143311","VC_Name":"黑金合成汽机油","PadctBrief":"","VC_Rule":"","VC_XH":"","VC_PP":"","VC_Url":"/Uplaod/Attachment/20160713/20160713145112344.jpg"},{"Title":"今日特价","N_HYJ":"2.0000","N_FHYJ":"0.0100","VC_Code":"2016052317205144748","VC_Name":"空气滤芯","PadctBrief":"","VC_Rule":"","VC_XH":"","VC_PP":"","VC_Url":"/Uplaod/Attachment/20160713/20160713145112344.jpg"},{"Title":"今日特价","N_HYJ":"798.0000","N_FHYJ":"1069.0000","VC_Code":"2016052317205144726","VC_Name":"黑金全合成汽机油","PadctBrief":"让你的爱车每年少做一次保养","VC_Rule":"4L","VC_XH":"SN-430","VC_PP":"车事通","VC_Url":"/Uplaod/Attachment/20160713/20160713145112344.jpg"}]
     */

    private String EventName;
    /**
     * Title : 今日特价
     * N_HYJ : 178.0000
     * N_FHYJ : 238.0000
     * VC_Code : 2016052317205143311
     * VC_Name : 黑金合成汽机油
     * PadctBrief :
     * VC_Rule :
     * VC_XH :
     * VC_PP :
     * VC_Url : /Uplaod/Attachment/20160713/20160713145112344.jpg
     */

    private List<DataBean> data;

    public EventResp(String eventName, List<DataBean> data) {
        EventName = eventName;
        this.data = data;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String EventName) {
        this.EventName = EventName;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }
}
