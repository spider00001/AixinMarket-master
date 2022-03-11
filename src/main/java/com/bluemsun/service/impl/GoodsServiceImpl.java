package com.bluemsun.service.impl;

import com.bluemsun.dao.GoodsDao;
import com.bluemsun.dao.MapperDao;
import com.bluemsun.entity.Goods;
import com.bluemsun.entity.GoodsType;
import com.bluemsun.service.GoodsService;
import com.bluemsun.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private MapperDao mapperDao;

    private Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);

    @Override
    @Transactional
    public Integer addGoods(Goods goods) {
        Integer flag = null;
        try{
            flag = goodsDao.insertGoods(goods);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("添加商品时出现错误，："+e.getMessage());
            throw new RuntimeException("添加商品时出现错误，："+e.getMessage());
        }
        logger.info("添加商品，id为："+goods.getId());
        return flag;
    }

    @Override
    @Transactional
    public Goods getAGoods(int id) {
        Goods flag = null;
        try{
            flag = goodsDao.getGoodById(id);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("通过id获取商品时出现异常 ："+e.getMessage());
            throw new RuntimeException("通过id获取商品时出现异常 ："+e.getMessage());
        }
        return flag;
    }

    @Override
    @Transactional
    public Goods getGoodsByBarcode(String barcode) {
        Goods flag = null;
        try {
            flag = goodsDao.getGoodByBarcode(barcode);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("通过barcode获取商品时出现异常 ："+e.getMessage());
            throw new RuntimeException("通过barcode获取商品时出现异常 ："+e.getMessage());
        }
        return flag;
    }

    @Override
    @Transactional
    public Goods getGoodsByName(String name){
        Goods flag = null;
        try {
            flag = goodsDao.getGoodByName(name);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("通过name获取商品时出现异常 ："+e.getMessage());
            throw new RuntimeException("通过name获取商品时出现异常 ："+e.getMessage());
        }
        return flag;
    }

    @Override
    @Transactional
    public List<Goods> getGoodsList(Goods goods,Float high,Float low,int pageNum, int pageSize) {
        int pageIndex = PageUtil.getRowIndex(pageNum,pageSize);
        List flag = null;
        try{
            flag = goodsDao.getGoodList(goods,high,low,pageIndex,pageSize);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("商品查询时发生异常 ："+e.getMessage());
            throw new RuntimeException("商品查询时发生异常 ："+e.getMessage());
        }
        return flag;
    }

    @Override
    @Transactional
    public Integer getGoodsCount(Goods goods,Float high,Float low){
        Integer flag = null;
        try{
            flag = goodsDao.getGoodCount(goods,high,low);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("商品Count查询时发生异常 ："+e.getMessage());
            throw new RuntimeException("商品Count查询时发生异常 ："+e.getMessage());
        }
        return flag;
    }

    @Override
    @Transactional
    public Integer changeGoods(Goods goods) {
        Integer flag = null;
        try{
            flag = goodsDao.updateGoods(goods);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("修改商品属性时发生异常 ："+e.getMessage());
            throw new RuntimeException("修改商品属性时发生异常 ："+e.getMessage());
        }
        logger.info("修改商品属性，id为："+goods.getId());
        return flag;
    }

    @Override
    @Transactional
    public Integer deleteGoods(int id) {
        Integer flag = null;
        try{
            flag = goodsDao.deleteGoods(id);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("删除商品时发生异常 ："+e.getMessage());
            throw new RuntimeException("删除商品时发生异常 ："+e.getMessage());
        }
        logger.info("删除一个商品，商品原id为："+id);
        return flag;
    }
    //添加商品类型
    @Override
    @Transactional
    public Integer addGoodsType(String classification){
        GoodsType goodsType = new GoodsType();
        goodsType.setClassification(classification);
        try{
            mapperDao.insertGoodsType(goodsType);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("添加商品类型时发生异常 ："+e.getMessage());
            throw new RuntimeException("添加商品类型时发生异常 ："+e.getMessage());
        }
        logger.info("添加一个商品类型，类型为："+classification);
        return 1;
    }
    //删除商品类型
    @Override
    @Transactional
    public Integer deleteGoodsType(int id){
        logger.info("删除一个商品类型，原id为："+id);
        return mapperDao.deleteGoodsType(id);
    }

    @Override
    public List<GoodsType> getTypeMapper(){
        return mapperDao.getGoodsTypeMap();
    }

    @Override
    public List getNameMapper(){
        return mapperDao.getGoodsNameMap();
    }
}
