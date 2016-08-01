package com.proton.bystone.bean;

/**
 * Created by Administrator on 2016/7/15.
 */
public class JsonResp {
        private int code;
        private String content;
        private String data;
        public void setCode(int code) {
            this.code = code;
        }
        public int getCode() {
            return code;
        }

        public void setContent(String content) {
            this.content = content;
        }
        public String getContent() {
            return content;
        }

        public void setData(String data) {
            this.data = data;
        }
        public String getData() {
            return data;
        }
}
