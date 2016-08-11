package com.proton.bystone.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/5.
 */
public class readd {

    /**
     * ret_code : 0
     * list : [{"prov":"重庆","p90":"5.55","p0":"5.46","p97":"6.2","ct":"2016-08-05 04:10:14.516","p93":"5.87"}]
     */

    private int ret_code;
    /**
     * prov : 重庆
     * p90 : 5.55
     * p0 : 5.46
     * p97 : 6.2
     * ct : 2016-08-05 04:10:14.516
     * p93 : 5.87
     */

    private List<ListBean> list;

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String prov;
        private String p90;
        private String p0;
        private String p97;
        private String ct;
        private String p93;

        public String getProv() {
            return prov;
        }

        public void setProv(String prov) {
            this.prov = prov;
        }

        public String getP90() {
            return p90;
        }

        public void setP90(String p90) {
            this.p90 = p90;
        }

        public String getP0() {
            return p0;
        }

        public void setP0(String p0) {
            this.p0 = p0;
        }

        public String getP97() {
            return p97;
        }

        public void setP97(String p97) {
            this.p97 = p97;
        }

        public String getCt() {
            return ct;
        }

        public void setCt(String ct) {
            this.ct = ct;
        }

        public String getP93() {
            return p93;
        }

        public void setP93(String p93) {
            this.p93 = p93;
        }
    }
}
