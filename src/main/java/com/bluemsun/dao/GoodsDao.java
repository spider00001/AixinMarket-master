package com.bluemsun.dao;

import com.bluemsun.entity.Goods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface GoodsDao {
    //添加商品
    public Integer insertGoods(Goods goods);
    //删除商品
    public Integer deleteGoods(Integer id);
    //修改商品信息
    public Integer updateGoods(@Param("goods") Goods goods);
    //根据商品等类型查找商品
    public List<Goods> getGoodList(@Param("goods") Goods goods,
                            @Param("highPrice") Float highPrice,
                            @Param("lowPrice") Float lowPrice,
                            @Param("pageIndex") int pageIndex,
                            @Param("pageSize") int pageSize);
    //根据商品等类型查找商品的数量
    public Integer getGoodCount(@Param("goods") Goods goods,
                         @Param("highPrice") Float highPrice,
                         @Param("lowPrice") Float lowPrice);

    public Goods getGoodById(int id);
    public Goods getGoodByBarcode(String barcode);
    public Goods getGoodByName(String name);


}
