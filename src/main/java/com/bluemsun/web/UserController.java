package com.bluemsun.web;

import com.bluemsun.entity.Seller;
import com.bluemsun.entity.Student;
import com.bluemsun.entity.StudentType;
import com.bluemsun.service.UserService;
import com.bluemsun.util.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private static Seller seller1 = new Seller(534,"净月售货员",1);
    private static Seller seller2 = new Seller(533,"本部售货员",2);

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Map userLogin(HttpServletRequest request,@RequestBody Map<String,String> reqMap){
        Map resMap = new HashMap();
        Map data = new HashMap();
        HttpSession session = request.getSession();
        String username = HttpRequestUtil.getString(reqMap,"username");
        String password = HttpRequestUtil.getString(reqMap,"password");
        if (username==null||password==null){
            resMap.put("code",2001);
            resMap.put("msg","空数据");
            return resMap;
        }
        //售货员登录
        if (username.equals("534")&&password.equals("hellozizhu")){
            session.setAttribute("seller", seller1);
            resMap.put("code",0);
            data.put("power",2);
            data.put("seller", seller1);
            resMap.put("data",data);
            return resMap;
        }
        if (username.equals("533")&&password.equals("hellozizhu")){
            session.setAttribute("seller", seller2);
            resMap.put("code",0);
            data.put("power",2);
            data.put("seller", seller2);
            resMap.put("data",data);
            return resMap;
        }
        //用户登录
        Student student = userService.userLogin(username,password);
        if (student==null){
            resMap.put("code",2003);
            resMap.put("msg","密码错误或用户不存在");
            return resMap;
        }else {
            session.setAttribute("student",student);
            resMap.put("code",0);
            data.put("power",1);
            data.put("student",student);
            resMap.put("data",data);
            return resMap;
//            if (student.getContact()==null){
//                session.setAttribute("flag",student);
//                resMap.put("code",1001);
//                resMap.put("msg","第一次登录");
//                return resMap;
//            }else{
//                session.setAttribute("student",student);
//                resMap.put("code",0);
//                data.put("power",1);
//                data.put("student",student);
//                resMap.put("data",data);
//                return resMap;
//            }
        }
    }


    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public Map userEdit(HttpServletRequest request,@RequestBody Map<String,String> reqMap){
        Map resMap = new HashMap();
        HttpSession session = request.getSession();
        //学生修改自己的信息
        Student student = (Student) session.getAttribute("student");
        if (student==null){
            resMap.put("code",1002);
            resMap.put("msg","用户未登录");
            return resMap;
        }
        int id = HttpRequestUtil.getInt(reqMap,"id");
        if (id == -1){
            resMap.put("code",2001);
            resMap.put("msg","id为空");
            return resMap;
        }
        if (id!=student.getId()){
            resMap.put("code",2002);
            resMap.put("msg","非法访问");
            return resMap;
        }
        String contact = HttpRequestUtil.getString(reqMap,"contact");
        // add
        Integer campus = HttpRequestUtil.getInt(reqMap,"campus");
        student.setContact(contact);
        // add
        student.setCampus(campus);
        Boolean flag = userService.updateStudentInfo(student);
        if (!flag){
            resMap.put("code",2004);
            resMap.put("msg","修改失败");
            return resMap;
        }else {
            session.setAttribute("student",student);
            resMap.put("code",0);
            return resMap;
        }
    }

    @RequestMapping(value = "/editPassword",method = RequestMethod.POST)
    public Map userChangePassword(HttpServletRequest request,@RequestBody Map<String,String> reqMap){
        Map resMap = new HashMap();
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");

        if (student==null){
            resMap.put("code",1002);
            resMap.put("msg","用户未登录");
            return resMap;
        }
        int id = HttpRequestUtil.getInt(reqMap,"id");
        if (id == -1){
            resMap.put("code",2001);
            resMap.put("msg","id为空");
            return resMap;
        }
        if (id!=student.getId()){
            resMap.put("code",2002);
            resMap.put("msg","非法访问");
            return resMap;
        }
        String originPassword = HttpRequestUtil.getString(reqMap,"originPassword");
        if (originPassword==null){
            resMap.put("code",2001);
            resMap.put("msg","初始密码为空");
            return resMap;
        }
        String newPassword = HttpRequestUtil.getString(reqMap,"newPassword");
        if (newPassword==null){
            resMap.put("code",2001);
            resMap.put("msg","新密码为空");
            return resMap;
        }
        if (originPassword == newPassword){
            resMap.put("code",1003);
            resMap.put("msg","密码相同");
            return resMap;
        }
        Boolean flag = userService.changePassword(student,newPassword,originPassword);
        if (!flag){
            resMap.put("code",2004);
            resMap.put("msg","修改失败");
            return resMap;
        }else {
            resMap.put("code",0);
            return resMap;
        }

    }

    @RequestMapping(value = "/getStudentInfo",method = RequestMethod.GET)
    public Map getStudentInfoById(HttpServletRequest request,@RequestParam Map<String,String> reqMap){
        Map resMap = new HashMap();
        Map data = new HashMap();
        HttpSession session = request.getSession();
        int id = HttpRequestUtil.getInt(reqMap,"id");
        if (id == -1){
            resMap.put("code",2001);
            resMap.put("msg","id为空");
            return resMap;
        }
        Student student = (Student) session.getAttribute("student");
        if (student != null&&student.getId()==id){
            resMap.put("code",0);
            data.put("student",student);
            resMap.put("data",data);
            return resMap;
        }else {
            student = userService.getUserById(id);
            if (student!=null){
                resMap.put("code",0);
                data.put("student",student);
                resMap.put("data",data);
                return resMap;
            }else {
                resMap.put("code",2003);
                resMap.put("msg","用户不存在");
                return resMap;
            }
        }
    }

    @RequestMapping(value = "/updateStudentInfo",method = RequestMethod.POST)
    public Map updateStudentInfoFirst(HttpServletRequest request,@RequestBody Map<String,String> reqMap){
        Map map = new HashMap();
        Map data = new HashMap();
        HttpSession session = request.getSession();
        String contact = HttpRequestUtil.getString(reqMap,"contact");
        Integer campus = HttpRequestUtil.getInt(reqMap,"campus");
        String username = HttpRequestUtil.getString(reqMap,"username");
//        String password = HttpRequestUtil.getString(reqMap,"password");
        if (contact==null||username==null||campus==-1){
            map.put("code",2001);
            map.put("msg","空数据");
            return map;
        }
        Student student = (Student) session.getAttribute("flag");
        if (student==null){
            map.put("code",2001);
            map.put("msg","空数据");
            return map;
        }
        if (!student.getStuNum().equals(username)){
            map.put("code",2004);
            map.put("msg","数据异常");
            return map;
        }
        student.setCampus(campus);
        student.setContact(contact);
        try{
            userService.updateStudentInfo(student);
        }catch (Exception e){
            map.put("code",2004);
            map.put("msg","数据异常");
            return map;
        }
        session.removeAttribute("flag");
        session.setAttribute("student",student);
        map.put("code",0);
        data.put("student",student);
        data.put("power",1);
        map.put("data",data);
        return map;
    }


    @RequestMapping(value = "/getStudentList",method = RequestMethod.GET)
    public Map getStudentList(HttpServletRequest request,@RequestParam Map<String,String> reqMap){
        Map resMap = new HashMap();
        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        if (admin==null||!admin.equals("admin2020")){
            resMap.put("code",1002);
            resMap.put("msg","用户未登录");
            return resMap;
        }

        Map data = new HashMap();
        int pageNum = HttpRequestUtil.getInt(reqMap,"currentPage");
        int pageSize = HttpRequestUtil.getInt(reqMap,"pageSize");
        String stuNum = HttpRequestUtil.getString(reqMap,"stuNum");
        String name = HttpRequestUtil.getString(reqMap,"name");
        int grade = HttpRequestUtil.getInt(reqMap,"grade");
        switch (grade) {
            case 1 : grade = 2021;break;
            case 2 : grade = 2020;break;
            case 3 : grade = 2019;break;
            case 4 : grade = 2018;break;
        }
        int departmentId = HttpRequestUtil.getInt(reqMap,"departmentId");
        int specialId = HttpRequestUtil.getInt(reqMap,"specialId");
        int imburseType = HttpRequestUtil.getInt(reqMap,"imburseType");
        int state = HttpRequestUtil.getInt(reqMap,"state");

        //组装studentType
        StudentType studentType = new StudentType();
        studentType.setStuNum(stuNum);
        studentType.setGrade(grade);
        studentType.setName(name);
        if (departmentId!=-1){
            studentType.setDepartmentId(departmentId);
        }else {
            studentType.setDepartmentId(null);
        }
        if (specialId!=-1){
            studentType.setSpecialId(specialId);
        }else {
            studentType.setSpecialId(null);
        }
        if (imburseType!=-1){
            studentType.setImburseType(imburseType);
        }else {
            studentType.setImburseType(null);
        }
        if (state!=-1){
            studentType.setState(state);
        }else {
            studentType.setState(null);
        }
        //studentType组装完成

        //从数据库中取出指定学生
        List<Student> studentList = userService.getStudentList(studentType,pageNum,pageSize);
        if (studentList==null){
            resMap.put("code",2001);
            resMap.put("msg","数据空");
            return resMap;
        }else {
            int count = userService.getStudentCount(studentType);
            data.put("total",count);
            data.put("studentList",studentList);
            resMap.put("code",0);
            resMap.put("data",data);
            return resMap;
        }

    }


    @RequestMapping(value = "/getMapper",method = RequestMethod.GET)
    public Map getStudentInfoMapper(HttpServletRequest request){
        Map resMap = new HashMap();
        Map data = null;

        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        if (admin==null||!admin.equals("admin2020")){
            resMap.put("code",1002);
            resMap.put("msg","用户未登录");
            return resMap;
        }

        data = (Map) session.getAttribute("studentMapper");
        //如果session里没有映射表，则从数据库中取出放入session
        if (data == null){
            data = userService.getMapper();
            if (data == null){
                resMap.put("code",2001);
                resMap.put("msg","数据空");
                return resMap;
            }else {
                resMap.put("code",0);
                resMap.put("data",data);
                return resMap;
            }
        }else {
            resMap.put("code",0);
            resMap.put("data",data);
            return resMap;
        }
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public Map doLogout(HttpServletRequest request){
        Map map = new HashMap();
        HttpSession session = request.getSession();
        session.removeAttribute("student");
        session.removeAttribute("seller");
        session.removeAttribute("admin");
        map.put("code",0);
        return map;
    }

    @RequestMapping(value = "/getStudentOnline",method = RequestMethod.GET)
    public Map dogetSessionStudent(HttpServletRequest request){
        Map map = new HashMap();
        HttpSession session = request.getSession(false);
        Student student = (Student)session.getAttribute("student");
        map.put("code",0);
        map.put("student",student);
        return map;
    }

    @RequestMapping(value = "/getSellerOnline",method = RequestMethod.GET)
    public Map dogetSessionSeller(HttpServletRequest request){
        Map map = new HashMap();
        HttpSession session = request.getSession(false);
        Seller seller = (Seller)session.getAttribute("seller");
        if (seller == null)  {
            map.put("code",1);
        } else {
            map.put("code",0);
            map.put("seller",seller);
        }
        return map;
    }
}
