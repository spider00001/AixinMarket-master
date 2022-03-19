package com.bluemsun.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class OrderRecord {
    private Integer id;
    private Student student;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date createTime;
    private Integer status;//1 待确认，2 待取货，3 交易成功，4 交易失败
    private Float totalFuzhuang;
    private Float totalRiyong;
    private Integer campus;
    private List<OrderDetail> orderDetailList;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Float getTotalFuzhuang() {
        return totalFuzhuang;
    }

    public void setTotalFuzhuang(Float totalFuzhuang) {
        this.totalFuzhuang = totalFuzhuang;
    }

    public Float getTotalRiyong() {
        return totalRiyong;
    }

    public void setTotalRiyong(Float totalRiyong) {
        this.totalRiyong = totalRiyong;
    }

    public Integer getCampus() {
        return campus;
    }

    public void setCampus(Integer campus) {
        this.campus = campus;
    }

    @Override
    public String toString() {
        return "OrderRecord{" +
                "id=" + id +
                ", student=" + student +
                ", createTime=" + createTime +
                ", status=" + status +
                ", totalFuzhuang=" + totalFuzhuang +
                ", totalRiyong=" + totalRiyong +
                ", campus=" + campus +
                ", orderDetailList=" + orderDetailList +
                '}';
    }
}
