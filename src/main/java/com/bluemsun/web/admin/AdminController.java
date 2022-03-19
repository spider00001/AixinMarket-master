package com.bluemsun.web.admin;

import com.bluemsun.entity.OrderRecord;
import com.bluemsun.entity.Student;
import com.bluemsun.service.OrderService;
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
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Map adminLogin(HttpServletRequest request,@RequestBody Map<String,String> reqMap){
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
            if (username.equals("hellozizhu2020")&&password.equals("zizhu2020")){
            resMap.put("code",0);
            data.put("id",2020);
            data.put("name","admin");
            resMap.put("data",data);
            session.setAttribute("admin","admin2020");
        }else {
            resMap.put("code",2003);
            resMap.put("msg","用户不存在");
        }
        return resMap;
    }

    @RequestMapping(value = "/charge",method = RequestMethod.POST)
    public Map chargeMoney(HttpServletRequest request,@RequestBody Map<String,String> reqMap){
        Map map = new HashMap();

        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        if (admin==null||!admin.equals("admin2020")){
            map.put("code",1002);
            map.put("msg","用户未登录");
            return map;
        }

        int imburseType = HttpRequestUtil.getInt(reqMap,"imburseType");
        Float fuzhuang = HttpRequestUtil.getFloat(reqMap,"fuzhuang");
        Float riyong = HttpRequestUtil.getFloat(reqMap,"riyong");
        if (imburseType==-1||(fuzhuang==null&&riyong==null)){
            map.put("code",2001);
            map.put("msg","空数据");
            return map;
        }
        try{
            if (fuzhuang==null){
                userService.chargeRiyong(riyong,imburseType);
            }else {
                if (riyong==null){
                    userService.chargeFuzhuang(fuzhuang,imburseType);
                }else {
                    userService.chargeRiyong(riyong,imburseType);
                    userService.chargeFuzhuang(fuzhuang,imburseType);
                }
            }
            map.put("code",0);
            return map;
        }catch (Exception e){
            map.put("code",2004);
            map.put("msg",e.getMessage());
            return map;
        }
    }

    @RequestMapping(value = "/order",method = RequestMethod.GET)
    public Map getOrderRecord(HttpServletRequest request,@RequestParam Map<String,String> reqMap){
        Map map = new HashMap();
        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        if (admin==null||!admin.equals("admin2020")){
            map.put("code",1002);
            map.put("msg","用户未登录");
            return map;
        }

        Integer campus = HttpRequestUtil.getInt(reqMap,"campus");
        Integer state = HttpRequestUtil.getInt(reqMap,"state");
        String name = HttpRequestUtil.getString(reqMap,"name");
        String stuNum = HttpRequestUtil.getString(reqMap,"stuNum");
        int pageSize = HttpRequestUtil.getInt(reqMap,"pageSize");
        int currentPage = HttpRequestUtil.getInt(reqMap,"currentPage");
        if (pageSize==-1||currentPage==-1){
            map.put("code",2001);
            map.put("msg","空数据");
            return map;
        }
        if (campus==-1){
            campus=null;
        }
        if (state==-1){
            state=null;
        }
        Student student = new Student();
        student.setName(name);
        student.setStuNum(stuNum);
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setStatus(state);
        orderRecord.setStudent(student);
        orderRecord.setCampus(campus);
        try{
            int total = orderService.adminGetOrderCount(orderRecord);
            List<OrderRecord> orderRecordList = orderService.getOrderList(orderRecord,currentPage,pageSize);
            Map data = new HashMap();
            data.put("total",total);
            data.put("orderRecordList",orderRecordList);
            map.put("data",data);
            map.put("code",0);
            return map;
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",2004);
            map.put("msg",e.getMessage());
            return map;
        }
    }


    /**
    * 这个是用来干啥的我给忘了。。。
    * */
    @RequestMapping(value = "/orderDelete",method = RequestMethod.GET)
    public Map deleteOrderRecord(HttpServletRequest request,@RequestParam Map<String,String> reqMap){
        Map map = new HashMap();
        return map;
    }

    @RequestMapping(value = "/studentEdit",method = RequestMethod.POST)
    public Map studentEdit(HttpServletRequest request,@RequestBody Map<String,String> reqMap){
        Map map = new HashMap();

        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        if (admin==null||!admin.equals("admin2020")){
            map.put("code",1002);
            map.put("msg","用户未登录");
            return map;
        }

        String contact = HttpRequestUtil.getString(reqMap,"contact");
        Integer campus = HttpRequestUtil.getInt(reqMap,"campus");
        Float balanceRiyong = HttpRequestUtil.getFloat(reqMap,"balanceRiyong");
        Float balanceFuzhuang = HttpRequestUtil.getFloat(reqMap,"balanceFuzhuang");
        Integer studentId = HttpRequestUtil.getInt(reqMap,"studentId");
        if (studentId==-1){
            map.put("code",2001);
            map.put("msg","studentId为空");
            return map;
        }
        if (campus==-1){
            campus=null;
        }
        Student student = new Student();
        student.setContact(contact);
        student.setCampus(campus);
        student.setBalanceRiyong(balanceRiyong);
        student.setBalanceFuzhuang(balanceFuzhuang);
        student.setId(studentId);
        try{
            userService.updateStudentInfo(student);
            map.put("code",0);
        }catch (Exception e){
            map.put("code",2004);
            map.put("msg",e.getMessage());
            return map;
        }
        return map;
    }
}
