package com.bluemsun.dao;

import com.bluemsun.entity.WareHouse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WareHouseDao {
    //添加商品到仓库中
    Integer insertGoodToWareHouse(WareHouse wareHouse);
    //修改仓库中的商品信息
    Integer updateWareHouseById(@Param("warehouse") WareHouse wareHouse);
    //查看商品校区库
    List<WareHouse> getWareHouseList(@Param("campus")Integer campus,
                                     @Param("goodsName") String goodsName,
                                     @Param("highCount") Integer highCount,
                                     @Param("lowCount") Integer lowCount,
                                     @Param("pageIndex") Integer pageIndex,
                                     @Param("pageSize") Integer pageSize);
    List<WareHouse> studentGetWareHouseList(@Param("warehouse") WareHouse wareHouse,
                                            @Param("pageIndex") int pageIndex,
                                            @Param("pageSize") int pageSize,
                                            @Param("priceRank") Boolean priceRank);

    Integer getWareHouseCount(@Param("warehouse") WareHouse wareHouse);
    //用商品id和校区获取到商品信息
    WareHouse getWareHouseByGoodId(@Param("warehouse") WareHouse wareHouse);
    //通过条形码获得商品信息
    WareHouse getWareHouseByBarcode(@Param("barcode") String barcode,@Param("campus") int campus);
    //通过warehouseId获取商品信息
    WareHouse getWareHouseById(@Param("wid")int wid);
    //删除仓库中的商品by id
    Integer deleteWareHouseById(int id);

}
