package com.bluemsun.dao;

import com.bluemsun.entity.*;

import java.util.List;

public interface MapperDao {
    /*
    *获得所有映射信息的dao
     */
    //获得学院映射表
    public List<Department> getDepartmentMap();
    //获得专业映射表
    public List<Special> getSpecialMap();
    //获得资助类型映射表
    public List<ImburseType> getImburseTypeMap();
    //获得商品属性映射表
    public List<GoodsType> getGoodsTypeMap();
    //获得商品名称映射表
    public List<Goods> getGoodsNameMap();
    //增删商品属性
    public Integer insertGoodsType(GoodsType goodsType);
    public Integer deleteGoodsType(int id);

}
