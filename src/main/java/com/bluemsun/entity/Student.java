package com.bluemsun.entity;


import java.util.Date;

public class Student {
    //aixin_student.id
    private Integer id;
    //学号
    private String stuNum;
    //姓名
    private String name;
    //学院名
    private String departmentName;
    //专业名
    private String specialName;
    //年级
    private String grade;
    //资助类型
    private String imburseTypeName;
    //日用币数量
    private Float balanceRiyong;
    //服装币数量
    private Float balanceFuzhuang;
    //密码
    private String password;
    //创建时间
    private Date creatTime;
    //修改时间
    private Date updateTime;
    //联系方式
    private String contact;
    //
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

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getSpecialName() {
        return specialName;
    }

    public void setSpecialName(String specialName) {
        this.specialName = specialName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getImburseTypeName() {
        return imburseTypeName;
    }

    public void setImburseTypeName(String imburseTypeName) {
        this.imburseTypeName = imburseTypeName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
