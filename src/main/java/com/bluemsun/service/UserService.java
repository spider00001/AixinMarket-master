package com.bluemsun.service;

import com.bluemsun.entity.Student;
import com.bluemsun.entity.StudentType;

import java.util.List;
import java.util.Map;

public interface UserService {
    /*
    *用户登录
    *如果正常登录返回用户对象
    *如果为第一次登录返回空对象
     */
    public Student userLogin(String stuNum,String password);
    //根据学生aixin_student.id获得用户信息
    public Student getUserById(int studentId);
    public Student getStudentByStuNum(String stuNum);
    //学生修改个人信息
    public Boolean updateStudentInfo(Student student);
    //学生修改密码
    public Boolean changePassword(Student student,String newPassword,String originPassword);
    //获得用户信息映射表
    public Map<String,List> getMapper();
    //获取学生列表
    public List<Student> getStudentList(StudentType studentType,int pageNum,int pageSize);
    //获取全部学生数量
    public int getStudentCount(StudentType studentType);
    //每月更新日用币
    public void updateBalanceRiyong();
    //每学期更新服装币
    public void updateBalanceFuzhuang();
    //充值服装币
    public void chargeFuzhuang(float fuzhuang,int imburseType);
    //充值日用币
    public void chargeRiyong(float Riyong,int imburseType);
}
