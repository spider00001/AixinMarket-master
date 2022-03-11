package com.bluemsun.entity;

import java.util.Date;

public class WareHouse {
    /*
    * 仓库类
    * 总库和校区仓库共用一个类
    * 总仓库无校区属性
    * */
    //仓库id
    private Integer id;
    //仓库物品
    private Goods goods;
    //仓库物品数量
    private Integer num;
    //校区
    private Integer campus;

    public Integer getCampus() {
        return campus;
    }

    public void setCampus(Integer campus) {
        this.campus = campus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoodsId(Goods goods) {
        this.goods = goods;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

}
