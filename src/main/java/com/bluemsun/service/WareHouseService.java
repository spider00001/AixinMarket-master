package com.bluemsun.service;

import com.bluemsun.entity.WareHouse;

import java.util.List;

public interface WareHouseService {
    //进货
    public Integer doCheckIn(WareHouse wareHouse);
    //商品list
    public List<WareHouse> getWareHouseList(WareHouse wareHouse,Integer high,Integer low,int pageNum,int pageSize);
    //学生获得商品
    public List<WareHouse> getWareHouseList(WareHouse wareHouse,int pageNum,int pageSize,Boolean priceRank);

//    public List<WareHouse> getWareHouseList(int goodsType, int moneyType, int campus, int currentPage, int pageNum, int pageSize, boolean priceRank,String goodsName);

    public Integer getWareHouseCount(WareHouse wareHouse);
    //修改校区库
    public Integer changeWareHouse(WareHouse wareHouse);
    //删除校区库内一条数据
    public Integer deleteWareHouse(int id);
    //通过条形码获得一个商品
    public WareHouse getWareHouse(String barcode,int campus);
    //通过id获取一个商品
    public WareHouse getWareHouse(int wId);
}
