package com.bluemsun.service;

import com.bluemsun.entity.Goods;
import com.bluemsun.entity.GoodsType;

import java.util.List;


public interface GoodsService {
    //添加新商品
    public Integer addGoods(Goods goods);
    //查看商品by id
    public Goods getAGoods(int id);
    //查看商品通过属性
    public List<Goods> getGoodsList(Goods goods,Float high,Float low,int pageNum,int pageSize);
    //获得商品数量
    public Integer getGoodsCount(Goods goods,Float high,Float low);
    //修改商品信息
    public Integer changeGoods(Goods goods);
    //删除商品
    public Integer deleteGoods(int id);
    //添加商品类型
    public Integer addGoodsType(String classification);
    //删除商品类型
    public Integer deleteGoodsType(int id);
    public Goods getGoodsByBarcode(String barcode);
    public List<GoodsType> getTypeMapper();
    public List getNameMapper();
    public Goods getGoodsByName(String name);
}
