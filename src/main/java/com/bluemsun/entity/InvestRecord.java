package com.bluemsun.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class InvestRecord {
    /*
    * 充值记录
     */
    //aixin_invest_record.id
    private Integer id;
    //资助类型
    private Integer imburseType;
    //充值日用币数量
    private Float balanceRiyong;
    //充值服装币数量
    private Float balanceFuzhuang;
    //充值时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getImburseType() {
        return imburseType;
    }

    public void setImburseType(Integer imburseType) {
        this.imburseType = imburseType;
    }

    public Float getBalanceRiyong() {
        return balanceRiyong;
    }

    public void setBalanceRiyong(Float balanceRiyong) {
        this.balanceRiyong = balanceRiyong;
    }

    public Float getBalanceFuzhuang() {
        return balanceFuzhuang;
    }

    public void setBalanceFuzhuang(Float balanceFuzhuang) {
        this.balanceFuzhuang = balanceFuzhuang;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
