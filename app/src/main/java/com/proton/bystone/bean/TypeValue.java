package com.proton.bystone.bean;

/**
 * Created by MasterFan on 2016/7/14.
 */
public class TypeValue {
    private String Type;
    private Object Value;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Object getValue() {
        return Value;
    }

    public void setValue(Object value) {
        Value = value;
    }

    public TypeValue() { }

    public TypeValue(String type, Object value) {
        Type = type;
        Value = value;
    }
}
