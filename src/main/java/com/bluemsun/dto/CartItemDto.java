package com.bluemsun.dto;

import com.bluemsun.entity.GoodsItem;

import java.util.List;

public class CartItemDto {
    private int id;
    private int goodsNum;
    private String goodsName;
    private float goodsPrice;
    private int moneyType;
    private String stuNum;
    private List<CartItemDto> cartItemDtoList;
    private String imgs;
    private GoodsItem goodsItem;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public GoodsItem getGoodsItem() {
        return goodsItem;
    }

    public void setGoodsItem(GoodsItem goodsItem) {
        this.goodsItem = goodsItem;
    }

    public CartItemDto() {
    }

    public int getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(int moneyType) {
        this.moneyType = moneyType;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public float getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(float goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public List<CartItemDto> getCartItemDtoList() {
        return cartItemDtoList;
    }

    public void setCartItemDtoList(List<CartItemDto> cartItemDtoList) {
        this.cartItemDtoList = cartItemDtoList;
    }

    public CartItemDto(int goodsNum, String goodsName, float goodsPrice, String stuNum) {
        this.goodsNum = goodsNum;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.stuNum = stuNum;
    }
}
