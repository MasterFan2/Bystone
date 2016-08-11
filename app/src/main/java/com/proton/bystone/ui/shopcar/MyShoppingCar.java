package com.proton.bystone.ui.shopcar;

import com.proton.bystone.bean.Commodity;
import com.proton.bystone.bean.DataBean;

import java.util.ArrayList;

/**
 * 购物车
 * Created by MasterFan on 2016/7/26.
 */
public class MyShoppingCar {

    private static MyShoppingCar ourInstance;       //
    private static ArrayList<Commodity> commodities;//保存商品

    /**
     * 实例方法
     * @return
     */
    public synchronized static MyShoppingCar getShoppingCar() {
        if (ourInstance == null) ourInstance = new MyShoppingCar();
        if (commodities == null) commodities = new ArrayList<>();
        return ourInstance;
    }

    //default constructor
    private MyShoppingCar() {}

    /**
     * 添加商品到购物车
     * @param commodity 添加的商品
     */
    public synchronized void add(Commodity commodity) {

        if (commodity == null) return;//数据为空直接结束

        commodity.setChecked(true);//设置默认选中状态

        if (commodities .size() == 0) {//第一次向购物车添加数据
            commodity.increment();
            commodities.add(commodity);
        }else {//购物车中有商品， 遍历购物车， 有重复的商品则数量加1， 否则是新商品， 直接添加到购物车中
            Commodity findCommodity = null;
            for (Commodity tempCommodity : commodities) {
                if(tempCommodity.getVC_Code().equals(commodity.getVC_Code())) {
                    findCommodity = tempCommodity;
                    break;
                }
            }

            if (findCommodity != null){//购物车中已经存在相同的商品
                findCommodity.increment();
            }else {                   //准备添加的商品在购物车中不存在， 直接添加 到购物车
                commodity.increment();
                commodities.add(commodity);
            }
        }
    }

    /**
     * 删除购物车中的商品
     * @param commodity
     * @return
     */
    public boolean remove(DataBean commodity) {
        return commodities.remove(commodity);
    }

    /**
     * 清除购物车商品
     */
    public void clear(){
        commodities.clear();
    }

    /**
     * 获取商品列表
     * @return
     */
    public ArrayList<Commodity> getCommodities() {
        if (getCount() > 0)
            return commodities;
        else
            return null;
    }

    /**
     * 获取购物车数量
     * @return
     */
    public int getCount() {
        return commodities == null ? 0 : commodities.size();
    }

    /**
     * 销毁购物车
     */
    public void terminate() {
        if (commodities != null && commodities.size() > 0)
            commodities.clear();
        commodities = null;
    }
}
