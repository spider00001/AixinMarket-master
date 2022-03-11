package com.bluemsun.service;

import com.bluemsun.dto.CartItemDto;
import com.bluemsun.entity.GoodsItem;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;


public interface CartService {
    //新建商品条目
    public Integer buildItem(GoodsItem goodsItem);
    //删除商品条目
    public Integer deleteItem(GoodsItem goodsItem);
    //修改商品条目
    public Integer changeItem(GoodsItem goodsItem);
    //获取条目列表
    public Map getItems(GoodsItem goodsItem);

}
