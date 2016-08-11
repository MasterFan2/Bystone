package com.proton.bystone.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/11.
 * //物流订单
 */
public class Jwul {


    /**
     * EBusinessID : 1262454
     * ShipperCode : YD
     * Success : true
     * LogisticCode : 1000742260903
     * State : 3
     * Traces : [{"AcceptTime":"2016-08-08 19:48:42","AcceptStation":"到达：江苏海门市公司 已收件"},{"AcceptTime":"2016-08-08 20:50:32","AcceptStation":"到达：江苏海门市公司 发往：重庆市重庆公司"},{"AcceptTime":"2016-08-08 22:52:40","AcceptStation":"到达：江苏南通分拨中心 上级站点：江苏海门市公司"},{"AcceptTime":"2016-08-08 22:54:41","AcceptStation":"到达：江苏南通分拨中心 发往：江苏苏州分拨中心"},{"AcceptTime":"2016-08-09 02:14:06","AcceptStation":"到达：江苏苏州分拨中心"},{"AcceptTime":"2016-08-09 02:17:00","AcceptStation":"到达：江苏苏州分拨中心 发往：重庆分拨中心"},{"AcceptTime":"2016-08-10 06:44:27","AcceptStation":"到达：重庆分拨中心 上级站点：江苏苏州分拨中心"},{"AcceptTime":"2016-08-10 06:54:41","AcceptStation":"到达：重庆分拨中心 发往：重庆江北区一公司"},{"AcceptTime":"2016-08-10 12:57:07","AcceptStation":"到达：重庆江北区一公司 上级站点：重庆分拨中心 发往："},{"AcceptTime":"2016-08-10 14:26:34","AcceptStation":"到达：重庆江北区一公司 发往：重庆江北区一公司南桥寺分部"},{"AcceptTime":"2016-08-10 15:14:32","AcceptStation":"到达：重庆江北区一公司南桥寺分部 上级站点：重庆江北区一公司 发往："},{"AcceptTime":"2016-08-10 19:35:26","AcceptStation":"到达：重庆江北区一公司南桥寺分部 指定：董证(15178828966) 派送"},{"AcceptTime":"2016-08-10 20:42:05","AcceptStation":"到达：重庆江北区一公司南桥寺分部 由 已签收 签收"}]
     */

    private String EBusinessID;
    private String ShipperCode;
    private boolean Success;
    private String LogisticCode;
    private String State;
    /**
     * AcceptTime : 2016-08-08 19:48:42
     * AcceptStation : 到达：江苏海门市公司 已收件
     */

    private List<TracesBean> Traces;

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public String getShipperCode() {
        return ShipperCode;
    }

    public void setShipperCode(String ShipperCode) {
        this.ShipperCode = ShipperCode;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setLogisticCode(String LogisticCode) {
        this.LogisticCode = LogisticCode;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public List<TracesBean> getTraces() {
        return Traces;
    }

    public void setTraces(List<TracesBean> Traces) {
        this.Traces = Traces;
    }

    public static class TracesBean {
        private String AcceptTime;
        private String AcceptStation;

        public String getAcceptTime() {
            return AcceptTime;
        }

        public void setAcceptTime(String AcceptTime) {
            this.AcceptTime = AcceptTime;
        }

        public String getAcceptStation() {
            return AcceptStation;
        }

        public void setAcceptStation(String AcceptStation) {
            this.AcceptStation = AcceptStation;
        }
    }
}
