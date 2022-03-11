package com.bluemsun.dto;

import java.util.List;

public class OrderRecordDto {
    private int studentId;
    private float totalRiyong;
    private float totalFuzhuang;
    private Integer campus;
    private List<OrderDetailDto> orderDetailList;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public float getTotalRiyong() {
        return totalRiyong;
    }

    public void setTotalRiyong(float totalRiyong) {
        this.totalRiyong = totalRiyong;
    }

    public float getTotalFuzhuang() {
        return totalFuzhuang;
    }

    public void setTotalFuzhuang(float totalFuzhuang) {
        this.totalFuzhuang = totalFuzhuang;
    }

    public Integer isCampus() {
        return campus;
    }

    public void setCampus(Integer campus) {
        this.campus = campus;
    }

    public List<OrderDetailDto> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetailDto> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }
}
