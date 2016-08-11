package com.proton.bystone.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/5.
 */
public class home_yj {
    /**
     * showapi_res_code : 0
     * showapi_res_error :
     * showapi_res_body : {"ret_code":0,"list":[{"prov":"重庆","p90":"5.55","p0":"5.46","p97":"6.2","ct":"2016-08-05 04:10:14.516","p93":"5.87"}]}
     */

    private int showapi_res_code;
    private String showapi_res_error;
    private String showapi_res_body;

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(String showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }
}
