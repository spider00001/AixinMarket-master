package com.bluemsun.dao;

import com.bluemsun.dto.CartItemDto;
import com.bluemsun.entity.GoodsItem;

import java.util.List;

public interface CartItemDao {
    //添加商品条目
    public Integer insertItem(CartItemDto cartItemDto);
    //删除商品条目
    public Integer deleteItem(CartItemDto cartItemDto);
    //修改商品条目
    public Integer changeItem(CartItemDto cartItemDto);
    //根据用户查找条目
    public List<CartItemDto> getItems(CartItemDto cartItemDto);
    //获取总条数
    public Integer countItems(CartItemDto cartItemDto);
    //查询
    public List<CartItemDto> getItem(CartItemDto cartItemDto);
}
