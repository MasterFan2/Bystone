package com.proton.bystone.bean;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MasterFan on 2016/8/2.
 */
public class RecognizeResp {

    private MessageBean message;
    private List<CardsinfoBean> cardsinfo;

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public List<CardsinfoBean> getCardsinfo() {
        return cardsinfo;
    }

    public void setCardsinfo(List<CardsinfoBean> cardsinfo) {
        this.cardsinfo = cardsinfo;
    }

    public static class MessageBean {
        private int status;
        private String value;

        protected MessageBean(Parcel in) {
            status = in.readInt();
            value = in.readString();
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class CardsinfoBean {
        private String type;
        /**
         * desc : 保留
         * content :
         */

        private ArrayList<RecognizeCardsinfoItem> items;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public ArrayList<RecognizeCardsinfoItem> getItems() {
            return items;
        }

        public void setItems(ArrayList<RecognizeCardsinfoItem> items) {
            this.items = items;
        }

        public static class ItemsBean {
            private String desc;
            private String content;

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }

    public RecognizeResp() {
    }
}
