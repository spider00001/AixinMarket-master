package com.bluemsun.dto;

public class GoodsDto {
    private String goodsName;
    private Integer goodsNum;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    @Override
    public String toString() {
        return "GoodsDto{" +
                "goodsName='" + goodsName + '\'' +
                ", goodsNum=" + goodsNum +
                '}';
    }
}
