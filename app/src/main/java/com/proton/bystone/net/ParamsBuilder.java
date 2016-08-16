package com.proton.bystone.net;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.proton.bystone.bean.Base;
import com.proton.bystone.bean.TypeValue;
import com.proton.bystone.exception.BuildException;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 参数构建
 * Created by MasterFan on 2016/7/15.
 */
public class ParamsBuilder<E> {

    private static ParamsBuilder instance;
    private ArrayList<TypeValue> typeValues = new ArrayList<>();
    private ArrayList objList = new ArrayList<>();
    private Base base = new Base();
    private Gson gson ;
    private boolean notParams = false;

    /**
     * default constructor
     */
    public ParamsBuilder() {
       // gson     = new Gson();
    }

    /**
     *
     * @return
     */
    public static ParamsBuilder getInstance() {
        if (instance == null) {
            instance = new ParamsBuilder();
        }
        return instance;
    }

    /**
     * set Gson
     * @param gson
     * @return
     */
    public ParamsBuilder gson(Gson gson) {
        this.gson = gson;
        return this;
    }

    public ParamsBuilder noParams() {
        this.notParams = true;
        return this;
    }

    /**
     * set key
     * @param key
     * @return
     */
    public ParamsBuilder key(String key) {
        base.setKey(key);
        return this;
    }

    /**
     * set method name
     * @param methodName
     * @return
     */
    public ParamsBuilder methodName(String methodName) {
        base.setMethodName(methodName);
        return this;
    }

    /**
     * 添加 类型/值  对  一般选择其中一种 [typeValue(String type, Object value) 或  Object(E obj)]
     * @param type
     * @param value
     * @return
     */
    public ParamsBuilder typeValue(String type, Object value) {
        typeValues.add(new TypeValue(type, value));
        return this;
    }

    /**
     * 添加参数对象  一般选择其中一种 [typeValue(String type, Object value) 或  Object(E obj)]
     * @param obj
     * @return
     */
    public ParamsBuilder object(Object obj) {
        if (obj == null) throw new BuildException("obj can't be null.");
        objList.add(obj);
        return this;
    }

    /**
     * 构建参数
     * @return
     */
    public RequestBody build(){

        if (gson == null) {
            throw new BuildException("gson not set!");
        }

        if (TextUtils.isEmpty(base.getKey()) || TextUtils.isEmpty(base.getMethodName())) {
            throw new BuildException("key or methodName is null");
        }

        if (objList != null && objList.size() > 0){//有对象参数 ， 添加到TypeValue中
            for (Object e : objList) {
                try {
                    String str = gson.toJson(e);
                    typeValues.add(new TypeValue("string", str));
                }catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        if (notParams) {//没有参数
            base.setPara("");
            String strBase = gson.toJson(base);
            return RequestBody.create(MediaType.parse("application/json;charset=utf-8"), strBase);

        }else {        //有参数
            if (typeValues != null && typeValues.size() > 0 ){
                String para = gson.toJson(typeValues);
                base.setPara(para);
                String result = gson.toJson(base);
                return RequestBody.create(MediaType.parse("application/json;charset=utf-8"), result);
            } else {
                throw new BuildException("typeValues not set!");
            }
        }
    }
}
