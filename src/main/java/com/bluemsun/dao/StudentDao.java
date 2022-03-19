package com.bluemsun.dao;

import com.bluemsun.entity.Student;
import com.bluemsun.entity.StudentType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentDao {
    //修改学生信息byid
    public int updateStudent(@Param("student")Student student);
    //修改所有学生的货币数量
    public int updateBalance(@Param("student")Student student,@Param("imburseType")int imburseType);
    //通过密码获得学生信息
    public Student getStudentByPassword(@Param("studentNum")String stuNum,
                                        @Param("password") String password);
    //通过学生表id获得学生信息（anxin_student.id）
    public Student getStudentById(@Param("id")int id);
    public Student getStudentByInfoStudentId(@Param("id") int id);
    public Student getStudentByStuNum(@Param("stuNum")String stuNum);
    //通过学生类型模糊搜索学生
    public List<Student> getStudentList(@Param("studentType") StudentType studentType,
                                        @Param("pageIndex") int pageIndex,
                                        @Param("pageSize") int pageSize);
    public int getStudentCount(@Param("studentType") StudentType studentType);
}
