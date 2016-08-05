package com.proton.bystone.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MasterFan on 2016/8/2.
 */
public class RecognizeCardsinfoItem implements Parcelable {

    private String desc;
    private String content;

    public RecognizeCardsinfoItem() { }

    public RecognizeCardsinfoItem(String desc, String content) {

        this.desc = desc;
        this.content = content;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.desc);
        dest.writeString(this.content);
    }

    protected RecognizeCardsinfoItem(Parcel in) {
        this.desc = in.readString();
        this.content = in.readString();
    }

    public static final Parcelable.Creator<RecognizeCardsinfoItem> CREATOR = new Parcelable.Creator<RecognizeCardsinfoItem>() {
        @Override
        public RecognizeCardsinfoItem createFromParcel(Parcel source) {
            return new RecognizeCardsinfoItem(source);
        }

        @Override
        public RecognizeCardsinfoItem[] newArray(int size) {
            return new RecognizeCardsinfoItem[size];
        }
    };
}
