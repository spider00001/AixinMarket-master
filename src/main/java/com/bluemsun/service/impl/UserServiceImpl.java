package com.bluemsun.service.impl;

import com.bluemsun.dao.MapperDao;
import com.bluemsun.dao.RecordsDao;
import com.bluemsun.dao.StudentDao;
import com.bluemsun.entity.*;
import com.bluemsun.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private MapperDao mapperDao;
    @Autowired
    private RecordsDao recordsDao;

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    //各资助对象的属性
    private int tebie = 1;
    private int zhongdian = 2;
    private int yiban = 3;
    private float riyong = 20;
    private float tebie_fuzhuang = 150;
    private float zhongdian_fuzhuang = 130;
    private float yiban_fuzhuang = 100;

    /*
     *用户登录
     *如果正常登录返回用户对象
     *
     */
    //todo md5
    @Override
    @Transactional
    public Student userLogin(String stuNum, String password) {
        Student student = studentDao.getStudentByPassword(stuNum,password);
        if (student == null) {
            logger.info("密码错误或用户不存在");
        } else logger.info("学生登录，学号："+stuNum+"姓名"+student.getName());
        return student;
    }

    //根据学生aixin_student.id获得用户信息
    @Override
    @Transactional
    public Student getUserById(int studentId) {
        return studentDao.getStudentById(studentId);
    }
    @Override
    @Transactional
    public Student getStudentByStuNum(String stuNum){
        return studentDao.getStudentByStuNum(stuNum);
    }

    //学生修改个人信息
    @Override
    @Transactional
    public Boolean updateStudentInfo(Student student) {
//        int flag = studentDao.updateStudent(student);
//        if (flag!=1){
//            logger.error("学生更新个人信息失败，学号为："+student.getStuNum()+"，姓名："+student.getName());
//            throw new RuntimeException("更新个人信息失败");
//        }else {
//            logger.info("学生更新个人信息，学号为："+student.getStuNum()+"，姓名："+student.getName());
//            return true;
//        }

        try{
            studentDao.updateStudent(student);
        }catch (Exception e){
            logger.error("学生更新个人信息失败，学号为："+student.getStuNum()+"，姓名："+student.getName());
            throw new RuntimeException("更新个人信息失败"+e.getMessage());
        }
        logger.info("学生更新个人信息，学号为："+student.getStuNum()+"，姓名："+student.getName());
        return true;
    }

    //学生修改密码
    //todo md5
    @Override
    @Transactional
    public Boolean changePassword(Student student, String newPassword,String originPassword) {
        Student s = studentDao.getStudentById(student.getId());
        if (s.getPassword().equals(newPassword)||!s.getPassword().equals(originPassword)){
            return false;
        }
        student.setPassword(newPassword);
        try{
            studentDao.updateStudent(student);
        }catch (Exception e){
            logger.error("学生修改密码失败，学号为："+student.getStuNum()+"，姓名："+student.getName());
            throw new RuntimeException("修改密码失败"+e.getMessage());
        }
        logger.info("学生修改密码，学号为："+student.getStuNum()+"，姓名："+student.getName());
        return true;
    }

    //获得用户信息映射表
    @Override
    @Transactional
    public Map<String, List> getMapper() {
        Map<String,List> data = new HashMap<>();
        List<Department> departmentList = mapperDao.getDepartmentMap();
        List<Special> specialList = mapperDao.getSpecialMap();
        List<ImburseType> imburseTypeList = mapperDao.getImburseTypeMap();
        data.put("departmentList",departmentList);
        data.put("specialList",specialList);
        data.put("imburseTypeList",imburseTypeList);
        return data;
    }

    //获取学生列表
    @Override
    @Transactional
    public List<Student> getStudentList(StudentType studentType,int pageNum,int pageSize) {
        int pageIndex = (pageNum-1)*pageSize;
        return studentDao.getStudentList(studentType,pageIndex,pageSize);
    }

    //获取全部学生数量
    @Override
    public int getStudentCount(StudentType studentType){
        return studentDao.getStudentCount(studentType);
    }

    //每月更新日用币
    @Override
    @Transactional
//    @Scheduled(cron = "0 0 0 1 * ? *")
    public void updateBalanceRiyong() {
        chargeRiyong(riyong,tebie);
        chargeRiyong(riyong,zhongdian);
        chargeRiyong(riyong,yiban);
    }


    //每学期更新服装币
    @Override
    @Transactional
//    @Scheduled(cron = "0 0 0 1 3,9 ? ")
    public void updateBalanceFuzhuang() {
        chargeFuzhuang(tebie_fuzhuang,tebie);
        chargeFuzhuang(zhongdian_fuzhuang,zhongdian);
        chargeFuzhuang(yiban_fuzhuang,yiban);
    }

    //充值服装币
    @Override
    @Transactional
    public void chargeFuzhuang(float fuzhuang,int imburseType){
        Student student = new Student();
        InvestRecord investRecord = new InvestRecord();
        investRecord.setBalanceRiyong(0f);
        investRecord.setCreateTime(new Date());
        student.setBalanceFuzhuang(fuzhuang);
        investRecord.setImburseType(imburseType);
        investRecord.setBalanceFuzhuang(fuzhuang);
        try{
            studentDao.updateBalance(student,imburseType);
            recordsDao.insertInvestRecord(investRecord);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("充值服装币出错,错误信息为："+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        logger.info("充值服装币，充值金额为："+fuzhuang+"，资助类型为："+imburseType);
    }
    //充值日用币
    @Override
    @Transactional
    public void chargeRiyong(float Riyong,int imburseType){
        Student student = new Student();
        InvestRecord investRecord = new InvestRecord();
        investRecord.setBalanceFuzhuang(0f);
        investRecord.setCreateTime(new Date());
        student.setBalanceRiyong(Riyong);
        investRecord.setImburseType(imburseType);
        investRecord.setBalanceRiyong(Riyong);
        try{
            studentDao.updateBalance(student,imburseType);
            recordsDao.insertInvestRecord(investRecord);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("充值日用币出错,错误信息为："+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        logger.info("充值日用币，充值金额为："+Riyong+"，资助类型为："+imburseType);

    }

    public int getTebie() {
        return tebie;
    }

    public void setTebie(int tebie) {
        this.tebie = tebie;
    }

    public int getZhongdian() {
        return zhongdian;
    }

    public void setZhongdian(int zhongdian) {
        this.zhongdian = zhongdian;
    }

    public int getYiban() {
        return yiban;
    }

    public void setYiban(int yiban) {
        this.yiban = yiban;
    }

    public float getRiyong() {
        return riyong;
    }

    public void setRiyong(float riyong) {
        this.riyong = riyong;
    }

    public float getTebie_fuzhuang() {
        return tebie_fuzhuang;
    }

    public void setTebie_fuzhuang(float tebie_fuzhuang) {
        this.tebie_fuzhuang = tebie_fuzhuang;
    }

    public float getZhongdian_fuzhuang() {
        return zhongdian_fuzhuang;
    }

    public void setZhongdian_fuzhuang(float zhongdian_fuzhuang) {
        this.zhongdian_fuzhuang = zhongdian_fuzhuang;
    }

    public float getYiban_fuzhuang() {
        return yiban_fuzhuang;
    }

    public void setYiban_fuzhuang(float yiban_fuzhuang) {
        this.yiban_fuzhuang = yiban_fuzhuang;
    }
}
