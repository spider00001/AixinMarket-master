package com.bluemsun.service.impl;

import com.bluemsun.dao.RecordsDao;
import com.bluemsun.dao.WareHouseDao;
import com.bluemsun.entity.RetrievalRecord;
import com.bluemsun.entity.WareHouse;
import com.bluemsun.service.WareHouseService;
import com.bluemsun.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class WareHouseServiceImpl implements WareHouseService {
    @Autowired
    private WareHouseDao wareHouseDao;
    @Autowired
    private RecordsDao recordsDao;

    private Logger logger = LoggerFactory.getLogger(WareHouseServiceImpl.class);

    @Override
    @Transactional
    public Integer doCheckIn(WareHouse wareHouse) {
        //分两种情况，一是仓库中没有该商品，另外一种是仓库中有该商品
        WareHouse house = null;
        RetrievalRecord retrievalRecord = new RetrievalRecord();
        try{
            house = wareHouseDao.getWareHouseByGoodId(wareHouse);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("进货时获取数据出现异常："+e.getMessage());
            throw new RuntimeException("进货时获取数据出现异常："+e.getMessage());
        }
        if (house!=null){
            //仓库中有该商品，先获得数据库中仓库数据，然后增加数据
            retrievalRecord.setCampus(wareHouse.getCampus());
            retrievalRecord.setCreateTime(new Date());
            retrievalRecord.setGoods(wareHouse.getGoods());
            retrievalRecord.setNum(wareHouse.getNum());
            wareHouse.setNum(house.getNum()+wareHouse.getNum());
            wareHouse.setId(house.getId());
            try{
                wareHouseDao.updateWareHouseById(wareHouse);
                recordsDao.insertRetrievalRecord(retrievalRecord);
            }catch (Exception e){
                e.printStackTrace();
                logger.error("进货时仓库里有货物，修改数据时出现错误："+e.getMessage());
                throw new RuntimeException("进货时仓库里有货物，修改数据时出现错误："+e.getMessage());
            }
        }else {
            //仓库中没有该商品，新建一条数据
            retrievalRecord.setCampus(wareHouse.getCampus());
            retrievalRecord.setCreateTime(new Date());
            retrievalRecord.setGoods(wareHouse.getGoods());
            retrievalRecord.setNum(wareHouse.getNum());
            try{
                wareHouseDao.insertGoodToWareHouse(wareHouse);
                recordsDao.insertRetrievalRecord(retrievalRecord);
            }catch (Exception e){
                e.printStackTrace();
                logger.error("进货时仓库里没有该货物，添加数据时出现错误："+e.getMessage());
                throw new RuntimeException("进货时仓库里没有该货物，添加数据时出现错误："+e.getMessage());
            }
        }
        logger.info("添加商品库存，id为："+wareHouse.getId()+
                    "，商品id为："+wareHouse.getGoods().getId()+
                    "，校区为："+wareHouse.getCampus()+
                    "，数量为："+wareHouse.getNum());
        return 1;
    }

    @Override
    @Transactional
    public List<WareHouse> getWareHouseList(WareHouse wareHouse,Integer high,Integer low,int pageNum, int pageSize) {
        List flag = null;
        int pageIndex = PageUtil.getRowIndex(pageNum,pageSize);
        try{
            flag = wareHouseDao.getWareHouseList(wareHouse.getCampus(),wareHouse.getGoods().getGoodsName(),high,low,pageIndex,pageSize);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获取仓库信息时出错："+e.getMessage());
            throw new RuntimeException("获取仓库信息时出错："+e.getMessage());
        }
        return flag;
    }

    @Override
    @Transactional
    public List<WareHouse> getWareHouseList(WareHouse wareHouse,int pageNum,int pageSize,Boolean priceRank){
        List flag = null;
        int pageIndex = PageUtil.getRowIndex(pageNum,pageSize);
        try{
            flag = wareHouseDao.studentGetWareHouseList(wareHouse,pageIndex,pageSize,priceRank);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获取仓库信息时出错："+e.getMessage());
            throw new RuntimeException("获取仓库信息时出错："+e.getMessage());
        }
        return flag;
    }

    @Override
    public Integer getWareHouseCount(WareHouse wareHouse){
        Integer flag;
        try{
            flag = wareHouseDao.getWareHouseCount(wareHouse);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获取仓库count时出错："+e.getMessage());
            throw new RuntimeException("获取仓库count时出错："+e.getMessage());
        }
        return flag;
    }

    @Override
    @Transactional
    public Integer changeWareHouse(WareHouse wareHouse) {
        Integer flag = null;
        try{
            flag = wareHouseDao.updateWareHouseById(wareHouse);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("修改仓库信息时出错："+e.getMessage());
            throw new RuntimeException("修改仓库信息时出错："+e.getMessage());
        }
        logger.info("修改仓库信息，id为："+wareHouse.getId()+
                "，商品id为："+wareHouse.getGoods().getId()+
                "，校区为："+wareHouse.getCampus()+
                "，数量为："+wareHouse.getNum());
        return flag;
    }

    @Override
    @Transactional
    public Integer deleteWareHouse(int id) {
        Integer flag = null;
        try{
            flag = wareHouseDao.deleteWareHouseById(id);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("删除一条仓库信息时出错："+e.getMessage());
            throw new RuntimeException("删除一条仓库信息时出错："+e.getMessage());
        }
        logger.info("删除一条仓库信息：原id为"+id);
        return flag;
    }

    @Override
    @Transactional
    public WareHouse getWareHouse(String barcode,int campus){
        WareHouse flag = null;
        try{
            flag = wareHouseDao.getWareHouseByBarcode(barcode,campus);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("销售员获取仓库信息时出错："+e.getMessage());
            throw new RuntimeException("销售员获取仓库信息时出错："+e.getMessage());
        }
        return flag;
    }

    public WareHouse getWareHouse(int wId){
        WareHouse flag = null;
        try{
            flag = wareHouseDao.getWareHouseById(wId);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("销售员获取仓库信息时出错："+e.getMessage());
            throw new RuntimeException("销售员获取仓库信息时出错："+e.getMessage());
        }
        return flag;
    }
}
