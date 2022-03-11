package com.bluemsun.entity;

public class Goods {
    //商品id
    private Integer id;
    //条形码
    private String barcode;
    //商品名称
    private String goodsName;
    //商品描述
    private String specs;
    //货币类型 false是日用币，true是服装币
    private Boolean moneyType;
    //商品类型
    private Integer goodsType;
    private String goodsTypeName;
    //商品价格
    private Float price;
    //商品限购数量 -1表示不限购
    private Integer limitBuyNum;
    //商品限购类型 false是每月限购，true是每学期限购
    private Boolean limitBuyType;
    //商品图片地址
    private String images;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public Boolean getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(Boolean moneyType) {
        this.moneyType = moneyType;
    }

    public Boolean getLimitBuyType() {
        return limitBuyType;
    }

    public void setLimitBuyType(Boolean limitBuyType) {
        this.limitBuyType = limitBuyType;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getLimitBuyNum() {
        return limitBuyNum;
    }

    public void setLimitBuyNum(Integer limitBuyNum) {
        this.limitBuyNum = limitBuyNum;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
