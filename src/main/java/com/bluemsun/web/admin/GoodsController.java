package com.bluemsun.web.admin;

import com.bluemsun.entity.Goods;
import com.bluemsun.service.GoodsService;

import com.bluemsun.util.HttpRequestUtil;
import com.bluemsun.util.PageUtil;
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
@RequestMapping("/goods")
@CrossOrigin
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Map addGoods(HttpServletRequest request, @RequestBody Goods goods){
        Map map = new HashMap();

        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        if (admin==null||!admin.equals("admin2020")){
            map.put("code",1002);
            map.put("msg","用户未登录");
            return map;
        }

        Goods goods1 = goodsService.getGoodsByName(goods.getGoodsName());
        Goods goods2 = goodsService.getGoodsByBarcode(goods.getBarcode());
        //判断是不是有同名商品或同名商品是同一个商品
        if (goods1==null&&goods2==null){
            //没有同名商品可以直接修改
            try{
                goodsService.addGoods(goods);
                map.put("code",0);

            }catch (Exception e){
                map.put("code",2004);
                map.put("msg",e.getMessage());
                return map;
            }
        }else {
            map.put("code",2005);
            map.put("msg","有同名商品");
        }
        return map;

    }
    @RequestMapping(value = "/name",method = RequestMethod.GET)
    public Map getGoodsName(HttpServletRequest request){
        Map map = new HashMap();

        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        if (admin==null||!admin.equals("admin2020")){
            map.put("code",1002);
            map.put("msg","用户未登录");
            return map;
        }

        List goodsList = goodsService.getNameMapper();
        if (goodsList==null){
            map.put("code",2001);
            map.put("msg","获取数据失败");
        }else {
            map.put("code",0);
            Map data = new HashMap();
            data.put("goodsList",goodsList);
            map.put("data",data);
        }
        return map;
    }
    @RequestMapping(value = "/type",method = RequestMethod.GET)
    public Map getGoodsType(HttpServletRequest request){
        Map map = new HashMap();

        List goodsTypeList = goodsService.getTypeMapper();
        if (goodsTypeList==null){
            map.put("code",2001);
            map.put("msg","获取数据失败");
        }else {
            map.put("code",0);
            Map data = new HashMap();
            data.put("goodsTypeList",goodsTypeList);
            map.put("data",data);
        }
        return map;
    }
    @RequestMapping(value = "/addtype",method = RequestMethod.GET)
    public Map addGoodsType(HttpServletRequest request,@RequestParam Map<String,String> reqMap){
        Map map = new HashMap();

        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        if (admin==null||!admin.equals("admin2020")){
            map.put("code",1002);
            map.put("msg","用户未登录");
            return map;
        }

        String classification = HttpRequestUtil.getString(reqMap,"classification");
        if (classification==null){
            map.put("code",1004);
            map.put("msg","空数据");
            return map;
        }
        if (goodsService.addGoodsType(classification)==1){
            map.put("code",0);
        }else {
            map.put("code",2004);
            map.put("msg","操作失败");
        }

        return map;
    }
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Map deleteGoods(HttpServletRequest request,@RequestBody Map<String,String> reqMap){
        Map map = new HashMap();
        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        if (admin==null||!admin.equals("admin2020")){
            map.put("code",1002);
            map.put("msg","用户未登录");
            return map;
        }

        Integer id = HttpRequestUtil.getInt(reqMap,"id");
        if (id==-1){
            map.put("code",1004);
            map.put("msg","空数据");
            return map;
        }

        if (goodsService.deleteGoods(id)!=1){
            map.put("code",2004);
            map.put("msg","操作失败");
        }else {
            map.put("code",0);
        }
        return map;
    }
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Map updateGoods(HttpServletRequest request, @RequestBody Goods goods){
        Map map = new HashMap();

        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        if (admin==null||!admin.equals("admin2020")){
            map.put("code",1002);
            map.put("msg","用户未登录");
            return map;
        }

        Goods goods1 = goodsService.getGoodsByName(goods.getGoodsName());
        Goods goods2 = goodsService.getGoodsByBarcode(goods.getBarcode());
        //判断是不是有同名商品或同名商品是同一个商品
        if (((goods1==null)||(goods.getId().equals(goods1.getId())))
                &&
                ((goods2==null)||(goods.getId().equals(goods2.getId())))){
            //没有同名商品可以直接修改
            try{
                if (goods.getLimitBuyNum()==-1){
                    goods.setLimitBuyType(null);
                }
                goodsService.changeGoods(goods);
                map.put("code",0);

            }catch (Exception e){
                map.put("code",2004);
                map.put("msg",e.getMessage());
                return map;
            }
        }else {
            map.put("code",2005);
            map.put("msg","有同名商品");
        }
        return map;
    }
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Map getGoodsList(HttpServletRequest request,@RequestParam Map<String,String> reqMap){
        Map map = new HashMap();

        HttpSession session = request.getSession();
        String admin = (String) session.getAttribute("admin");
        if (admin==null||!admin.equals("admin2020")){
            map.put("code",1002);
            map.put("msg","用户未登录");
            return map;
        }

        int pageNum = HttpRequestUtil.getInt(reqMap,"currentPage");
        int pageSize = HttpRequestUtil.getInt(reqMap,"pageSize");
        if (pageNum==-1||pageSize==-1){
            map.put("code",1004);
            map.put("msg","数据为空");
            return map;
        }
        String barcode = HttpRequestUtil.getString(reqMap,"barcode");
        String goodsName = HttpRequestUtil.getString(reqMap,"goodsName");
        Boolean moneyType = HttpRequestUtil.getBoolean(reqMap,"moneyType");
        Float highPrice = HttpRequestUtil.getFloat(reqMap,"highPrice");
        Float lowPrice = HttpRequestUtil.getFloat(reqMap,"lowPrice");
        Integer goodsType = HttpRequestUtil.getInt(reqMap,"goodsType");
        if (goodsType==-1){
            goodsType = null;
        }
        Goods goods = new Goods();
        goods.setBarcode(barcode);
        goods.setGoodsName(goodsName);
        goods.setGoodsType(goodsType);
        goods.setMoneyType(moneyType);
        List<Goods> goodsList = goodsService.getGoodsList(goods,highPrice,lowPrice,pageNum,pageSize);
        Integer totalCount = goodsService.getGoodsCount(goods,highPrice,lowPrice);
        if (goodsList==null||totalCount==null){
            map.put("code",2001);
            map.put("msg","数据为空");
        }else {
            map.put("code",0);
            Map data = new HashMap();
            data.put("total",totalCount);
            data.put("goodsList",goodsList);
            map.put("data",data);
        }
        return map;
    }
    @RequestMapping(value = "/byid",method = RequestMethod.GET)
    public Map getGoodById(HttpServletRequest request,@RequestParam("goodId") Integer goodId){
        Map map = new HashMap();
        if (goodId == null) {
            map.put("code",1004);
            map.put("msg","数据为空");
            return map;
        }
        Goods goods = goodsService.getAGoods(goodId);
        Map data = new HashMap();
        data.put("goods",goods);
        map.put("code",0);
        map.put("data",data);
        return map;
    }


}
