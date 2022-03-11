package com.bluemsun.web.seller;

import com.bluemsun.dto.OrderRecordDto;
import com.bluemsun.entity.Seller;
import com.bluemsun.entity.Student;
import com.bluemsun.entity.WareHouse;
import com.bluemsun.service.OrderService;
import com.bluemsun.service.UserService;
import com.bluemsun.service.WareHouseService;
import com.bluemsun.util.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/seller")
@ResponseBody
@CrossOrigin
public class SellerController {
    @Autowired
    private WareHouseService wareHouseService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;


    @RequestMapping(value = "/getwarehouse",method = RequestMethod.GET)
    public Map getWareHouseByBarcode(HttpServletRequest request,@RequestParam Map<String,String> reqMap){
        Map map = new HashMap();

        HttpSession session = request.getSession();
        Seller seller = (Seller) session.getAttribute("seller");
        if (seller ==null){
            map.put("code",1002);
            map.put("msg","用户未登录");
            return map;
        }

        String barcode = HttpRequestUtil.getString(reqMap,"barcode");
        int campus = HttpRequestUtil.getInt(reqMap,"campus");
        if (barcode==null||campus==-1){
            map.put("code",1004);
            map.put("msg","数据为空");
            return map;
        }
        WareHouse wareHouse = wareHouseService.getWareHouse(barcode,campus);
        if (wareHouse==null){
            map.put("code",2004);
            map.put("msg","获取失败");
        }else {
            Map data = new HashMap();
            data.put("wareHouse",wareHouse);
            map.put("code",0);
            map.put("data",data);
        }
        return map;
    }

    @RequestMapping(value = "/settlement",method = RequestMethod.POST)
    public Map studentOrder(HttpServletRequest request,@RequestBody OrderRecordDto orderRecordDto){
        Map map = new HashMap();
        HttpSession session = request.getSession();
        Seller seller = (Seller) session.getAttribute("seller");
        if (seller ==null){
            map.put("code",1002);
            map.put("msg","用户未登录");
            return map;
        }
        int flag = 0;
        flag = orderService.buildOrder(orderRecordDto,3);
        switch (flag){
            case 0 :
                map.put("code",2004);
                map.put("msg","插入失败");
                break;
            case 1 :
                map.put("code",0);
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

    @RequestMapping(value = "/studentinfo",method = RequestMethod.GET)
    public Map getStudentInfo(HttpServletRequest request,@RequestParam Map<String,String> reqMap){
        Map map = new HashMap();

        HttpSession session = request.getSession();
        Seller seller = (Seller) session.getAttribute("seller");
        if (seller ==null){
            map.put("code",1002);
            map.put("msg","用户未登录");
            return map;
        }

        String stuNum = HttpRequestUtil.getString(reqMap,"stuNum");
        if (stuNum==null){
            map.put("code",1004);
            map.put("msg","数据为空");
            return map;
        }
        Student student = userService.getStudentByStuNum(stuNum);
        if (student==null){
            map.put("code",2004);
            map.put("msg","获取失败");
        }else {
            Map data = new HashMap();
            data.put("student",student);
            map.put("code",0);
            map.put("data",data);
        }
        return map;
    }
}
