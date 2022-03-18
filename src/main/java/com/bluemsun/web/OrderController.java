package com.bluemsun.web;

import com.bluemsun.dto.OrderRecordDto;
import com.bluemsun.entity.OrderRecord;
import com.bluemsun.entity.Student;
import com.bluemsun.service.OrderService;
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
@RequestMapping("/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;



    @RequestMapping(value = "/student",method = RequestMethod.POST)
    public Map studentOrder(HttpServletRequest request,@RequestBody OrderRecordDto orderRecordDto){
        Map map = new HashMap();
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        if (student==null){
            map.put("code",1002);
            map.put("msg","用户未登录");
            return map;
        }
        if (student.getId()!=orderRecordDto.getStudentId()){
            map.put("code",2002);
            map.put("msg","非法访问");
            return map;
        }
        int flag = 0;
        flag = orderService.buildOrder(orderRecordDto,1);
        switch (flag){
            case 0 :
                map.put("code",2004);
                map.put("msg","插入失败");
                break;
            case 1 :
                map.put("code",0);
                student.setBalanceFuzhuang(student.getBalanceFuzhuang() - orderRecordDto.getTotalFuzhuang());
                student.setBalanceRiyong(student.getBalanceRiyong() - orderRecordDto.getTotalRiyong());
                session.removeAttribute("student");
                session.setAttribute("student",student);
                map.put("studentTest",student);
                break;
            case 2 :
                map.put("code",2004);
                map.put("msg","余额不足");
                break;
            case 3 :
                map.put("code",2004);
                map.put("msg","存在限购商品");
                break;
            case 4 :
                map.put("code",2004);
                map.put("msg","商品余量不足");
                break;
            case 5 :
                map.put("code",2004);
                map.put("msg","商品不存在");
                break;
            case 6 :
                map.put("code",2004);
                map.put("msg","用户不存在");
                break;
        }
        return map;
    }

    @RequestMapping(value = "/cancel",method = RequestMethod.POST)
    public Map studentOrder(HttpServletRequest request,@RequestBody Map<String,String> reqMap){
        Map map = new HashMap();
//        HttpSession session = request.getSession();
//        Student student = (Student) session.getAttribute("student");
//        if (student==null){
//            map.put("code",1002);
//            map.put("msg","用户未登录");
//            return map;
//        }
        int uid = HttpRequestUtil.getInt(reqMap,"uid");
        int orderId = HttpRequestUtil.getInt(reqMap,"orderId");
        if (orderId==-1||uid==-1){
            map.put("code",1004);
            map.put("msg","数据为空");
            return map;
        }
        if(1==orderService.cancelOrder(uid, orderId,request)){
            map.put("code",0);
            map.put("msg","删除成功");

            return map;
        }else{
            map.put("code",2004);
            map.put("msg","取消失败");
            return map;
        }
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Map getStudentOrderList(HttpServletRequest request,@RequestParam Map<String,String> reqMap){
        Map map = new HashMap();
        Integer studentId = HttpRequestUtil.getInt(reqMap,"studentId");
        Integer status = HttpRequestUtil.getInt(reqMap,"status");
        int currentPage = HttpRequestUtil.getInt(reqMap,"currentPage");
        int pageSize = HttpRequestUtil.getInt(reqMap,"pageSize");
        Integer campus = HttpRequestUtil.getInt(reqMap,"campus");
        if (pageSize==-1||currentPage==-1){
            map.put("code",1004);
            map.put("msg","数据为空");
            return map;
        }
        if (studentId == -1){
            studentId = null;
        }
        if (status == -1){
            status = null;
        }
        if (campus == -1){
            campus = null;
        }
        Student student = new Student();
        student.setId(studentId);
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setStatus(status);
        orderRecord.setCampus(campus);
        orderRecord.setStudent(student);
        List<OrderRecord> orderRecordList = orderService.getOrderList(orderRecord,currentPage,pageSize);
        Integer total = orderService.getOrderCount(orderRecord);
        if (total!=null||orderRecordList!=null){
            Map data = new HashMap();
            data.put("total",total);
            data.put("orderRecordList",orderRecordList);
            map.put("code",0);
            map.put("data",data);
        }else {
            map.put("code",2004);
            map.put("msg","获取失败");
        }
        return map;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Map doUpdateOrder(HttpServletRequest request,@RequestBody Map<String,String> reqMap){
        Map map = new HashMap();
        int orderRecordId = HttpRequestUtil.getInt(reqMap,"orderRecordId");
        int status = HttpRequestUtil.getInt(reqMap,"status");
        if (orderRecordId==-1||status<0||status>4){
            map.put("code",1004);
            map.put("msg","数据为空");
            return map;
        }
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setStatus(status);
        orderRecord.setId(orderRecordId);
        if (orderService.changeOrder(orderRecord)!=1){
            map.put("code",2004);
            map.put("msg","获取失败");
        }else {
            map.put("code",0);
        }
        return map;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Map studentOrderDelete(HttpServletRequest request,@RequestBody Map<String,String> reqMap){
        Map map = new HashMap();
        Integer orderId = HttpRequestUtil.getInt(reqMap,"orderId");
        if (orderId==-1){
            map.put("code",1004);
            map.put("msg","数据为空");
            return map;
        }
        try{
            orderService.adminDeleteOrder(orderId);
            map.put("code",0);
        }catch (Exception e){
            map.put("code",2004);
            map.put("msg",e.getMessage());
            return map;
        }
        return map;
    }
}
