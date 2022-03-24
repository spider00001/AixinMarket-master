package com.bluemsun.entity;

public class StudentType {
    private Integer departmentId;
    private Integer SpecialId;
    private String stuNum;
    private String name;
    private Integer state;
    private int grade;
    private Integer imburseType;


    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getSpecialId() {
        return SpecialId;
    }

    public void setSpecialId(Integer specialId) {
        SpecialId = specialId;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getImburseType() {
        return imburseType;
    }

    public void setImburseType(Integer imburseType) {
        this.imburseType = imburseType;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
