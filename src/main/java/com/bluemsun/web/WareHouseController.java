package com.bluemsun.web;

import com.bluemsun.entity.Goods;
import com.bluemsun.entity.WareHouse;
import com.bluemsun.service.WareHouseService;
import com.bluemsun.util.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ware")
@ResponseBody
@CrossOrigin
public class WareHouseController {

    @Autowired
    private WareHouseService wareHouseService;

    @RequestMapping(value = "/checkin",method = RequestMethod.POST)
    public Map getCheckIn(HttpServletRequest request,@RequestBody Map<String,String> reqMap){
        Map map = new HashMap();
        Integer goodId = HttpRequestUtil.getInt(reqMap,"goodId");
        Integer num = HttpRequestUtil.getInt(reqMap,"num");
        Integer campus = HttpRequestUtil.getInt(reqMap,"campus");
        if (num==-1||goodId==-1||campus==-1){
            map.put("code",1004);
            map.put("msg","数据为空");
            return map;
        }
        WareHouse wareHouse = new WareHouse();
        wareHouse.setNum(num);
        wareHouse.setCampus(campus);
        Goods goods = new Goods();
        goods.setId(goodId);
        wareHouse.setGoodsId(goods);
        if (wareHouseService.doCheckIn(wareHouse)!=1){
            map.put("code",2004);
            map.put("msg","修改失败");
        }else {
            map.put("code",0);
        }
        return map;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Map doWarehouseUpdate(HttpServletRequest request,@RequestBody Map<String,String> reqMap){
        Map map = new HashMap();
        Integer wareHouseId = HttpRequestUtil.getInt(reqMap,"wareHouseId");
        Integer num = HttpRequestUtil.getInt(reqMap,"num");
        if (wareHouseId==-1||num==-1){
            map.put("code",1004);
            map.put("msg","数据为空");
            return map;
        }
        WareHouse wareHouse = new WareHouse();
        wareHouse.setId(wareHouseId);
        wareHouse.setNum(num);
        if (wareHouseService.changeWareHouse(wareHouse)!=1){
            map.put("code",2004);
            map.put("msg","修改失败");
        }else {
            map.put("code",0);
        }
        return map;
    }


    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Map getWareHouseList(HttpServletRequest request,@RequestParam Map<String,String> reqMap){
        Map map = new HashMap();
        Integer pageSize = HttpRequestUtil.getInt(reqMap,"pageSize");
        Integer currentPage = HttpRequestUtil.getInt(reqMap,"currentPage");
        if (pageSize==-1||currentPage==-1){
            map.put("code",1004);
            map.put("msg","数据为空");
            return map;
        }
        String goodsName = HttpRequestUtil.getString(reqMap,"goodsName");
        Integer campus = HttpRequestUtil.getInt(reqMap,"campus");
        Integer highCount = HttpRequestUtil.getInt(reqMap,"highCount");
        Integer lowCount = HttpRequestUtil.getInt(reqMap,"lowCount");
        if (highCount==-1){
            highCount=null;
        }
        if (lowCount==-1){
            lowCount=null;
        }
        if (campus==-1){
            campus=null;
        }
        Goods goods = new Goods();
        goods.setGoodsName(goodsName);
        WareHouse wareHouse = new WareHouse();
        wareHouse.setCampus(campus);
        wareHouse.setGoodsId(goods);
        List<WareHouse> wareHouseList = wareHouseService.getWareHouseList(wareHouse,highCount,lowCount,currentPage,pageSize);
        Integer total = wareHouseService.getWareHouseCount(wareHouse);
        if (wareHouseList==null||total==null){
            map.put("code",2004);
            map.put("msg","修改失败");
        }else {
            map.put("code",0);
            Map data = new HashMap();
            data.put("wareHouseList",wareHouseList);
            data.put("total",total);
            map.put("data",data);
        }
        return map;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Map doDeleteWareHouse(HttpServletRequest request,@RequestBody Map<String,String> reqMap){
        Map map = new HashMap();
        int wareHouseId = HttpRequestUtil.getInt(reqMap,"wareHouseId");
        if (wareHouseId==-1){
            map.put("code",1004);
            map.put("msg","数据为空");
            return map;
        }
        if (wareHouseService.deleteWareHouse(wareHouseId)!=1){
            map.put("code",2004);
            map.put("msg","修改失败");
        }else {
            map.put("code",0);
        }
        return map;
    }

    @RequestMapping(value = "/student",method = RequestMethod.GET)
    public Map studentGetWareHouseList(HttpServletRequest request,@RequestParam Map<String,String> reqMap){
        Map map = new HashMap();
        Integer pageSize = HttpRequestUtil.getInt(reqMap,"pageSize");
        Integer currentPage = HttpRequestUtil.getInt(reqMap,"currentPage");
        Integer goodsType = HttpRequestUtil.getInt(reqMap,"goodsType");
        Integer campus = HttpRequestUtil.getInt(reqMap,"campus");
        if (pageSize==-1||currentPage==-1||campus==-1){
            map.put("code",1004);
            map.put("msg","数据为空");
            return map;
        }
        if (goodsType==-1){
            goodsType=null;
        }
        String goodsName = HttpRequestUtil.getString(reqMap,"goodsName");
        Integer moneyType = HttpRequestUtil.getInt(reqMap,"moneyType");
        if (moneyType == -1) {
            moneyType = null;
        }

        Boolean priceRank = HttpRequestUtil.getBoolean(reqMap,"priceRank");
        WareHouse wareHouse = new WareHouse();
        Goods goods = new Goods();
        goods.setGoodsName(goodsName);
        goods.setMoneyType(moneyType);
        goods.setGoodsType(goodsType);
        wareHouse.setGoodsId(goods);
        wareHouse.setCampus(campus);
        List<WareHouse> wareHouseList = wareHouseService.getWareHouseList(wareHouse,currentPage,pageSize,priceRank);
        Integer total = wareHouseService.getWareHouseCount(wareHouse);
        if (wareHouseList==null){
            map.put("code",2004);
            map.put("msg","数据为空");
        }else {
            map.put("code",0);
            Map data = new HashMap();
            data.put("wareHouseList",wareHouseList);
            data.put("total",total);
            map.put("data",data);

        }
        return map;
    }

    @RequestMapping(value = "/ByIds",method = RequestMethod.GET)
    public Map studentGetWareHouseById(HttpServletRequest request,@RequestParam Map<String,String> reqMap){
        Map map = new HashMap();
        Integer wid = HttpRequestUtil.getInt(reqMap,"wId");
        if (wid == null) {
            map.put("code",1004);
            map.put("msg","数据为空");
            return map;
        }
        WareHouse wareHouse = wareHouseService.getWareHouse(wid);
        Map data = new HashMap();
        data.put("wareHouse",wareHouse);
        map.put("code",0);
        map.put("data",data);
        return map;
    }

}
