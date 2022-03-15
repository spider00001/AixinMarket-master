package com.bluemsun.service.impl;

import com.bluemsun.dao.CartItemDao;
import com.bluemsun.dao.GoodsDao;
import com.bluemsun.dto.CartItemDto;
import com.bluemsun.entity.Goods;
import com.bluemsun.entity.GoodsItem;
import com.bluemsun.service.CartService;
import com.bluemsun.util.CutList;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CartServiceImpl implements CartService {
    @Autowired
    GoodsDao goodsDao;

    @Autowired
    CartItemDao cartItemDao;

    private Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Override
    @Transactional
    public Integer buildItem(GoodsItem goodsItem) {
        int flag = 0;
        Goods goods = goodsDao.getGoodByName(goodsItem.getGoodsName());
        try{
            if (goods == null){
                return 4;
            }
            CartItemDto temp = new CartItemDto(goodsItem.getGoodsNum(),goodsItem.getGoodsName(),goods.getPrice(),goodsItem.getStuNum());
            temp.setGoodsItem(goodsItem);
            temp.setCartItemDtoList(cartItemDao.getItem(temp));
            if (temp.getCartItemDtoList().size() == 0){
                CartItemDto cartItemDto = new CartItemDto(goodsItem.getGoodsNum(),goods.getGoodsName(),goods.getPrice(),goodsItem.getStuNum());
                cartItemDao.insertItem(cartItemDto);
                flag = 1;
            }else{
                CartItemDto cartItemDto = temp.getCartItemDtoList().get(0);
                goodsItem.setGoodsNum(cartItemDto.getGoodsNum()+goodsItem.getGoodsNum());
                cartItemDto.setGoodsItem(goodsItem);
                cartItemDao.changeItem(cartItemDto);
                flag = 2;
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("创建购物车商品条目时出现错误："+e.getMessage());
            throw new RuntimeException("创建购物车商品条目时出错："+e.getMessage());
        }
        logger.info("添加商品条目，商品名称为："+goods.getGoodsName());
        return flag;
    }

    @Override
    @Transactional
    public Integer deleteItem(GoodsItem goodsItem) {
        Integer flag = 0;
        try{
            System.out.println(goodsItem.toString());
            CartItemDto cartItemDto = new CartItemDto();
            cartItemDto.setGoodsItem(goodsItem);
            flag = cartItemDao.deleteItem(cartItemDto);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("删除购物车商品条目时出现异常："+e.getMessage());
            throw new RuntimeException("删除购物车商品条目时出现异常："+e.getMessage());
        }
        return flag;
    }

    @Override
    @Transactional
    public Integer changeItem(GoodsItem goodsItem) {
        try{
            CartItemDto cartItemDto = new CartItemDto(goodsItem.getGoodsNum(),null,-1,goodsItem.getStuNum());
            cartItemDto.setGoodsItem(goodsItem);
            Integer flag = cartItemDao.changeItem(cartItemDto);
            return flag;
        }catch (Exception e){
            logger.error("修改购物车商品条目时出现异常："+e.getMessage());
            throw new RuntimeException("修改购物车商品条目时出现异常："+e.getMessage());
        }

    }

    @Override
    @Transactional
    public Map getItems(GoodsItem goodsItem){
        try{
            CartItemDto cartItemDto = new CartItemDto();
            cartItemDto.setGoodsItem(goodsItem);
            PageHelper.startPage(goodsItem.getPageNum(),goodsItem.getPageSize());
            List<CartItemDto> list = cartItemDao.getItems(cartItemDto);
            cartItemDto.getGoodsItem().setGoodsName(null);
            List<CartItemDto> list1 = cartItemDao.getItem(cartItemDto);
            List<CartItemDto> ans = new ArrayList<>();
            if (list1.size() == 0){
                Map map = new HashMap();
                map.put("data",list);
                return map;
            }else {
                for (CartItemDto cart: list){
                    for (CartItemDto cart1: list1){
                        if (cart.getGoodsName().equals(cart1.getGoodsName())){
                            cart.setGoodsNum(cart1.getGoodsNum());
                            cart.setStuNum(cart1.getStuNum());
                        }
                    }
                }
                int count = cartItemDao.countItems(cartItemDto);
                List resp = CutList.cutList(list,goodsItem.getPageNum(),goodsItem.getPageSize());
                Map map = new HashMap();
                map.put("data",ans);
                map.put("total",count);
                map.put("data",resp);
                return map;
            }

        }catch (Exception e){
            logger.error("查询购物车商品条目时出现异常："+e.getMessage());
            throw new RuntimeException("查询购物车商品条目时出现异常："+e.getMessage());
        }
    }
}
