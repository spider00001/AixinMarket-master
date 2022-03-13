package com.bluemsun.web;


import com.bluemsun.dto.CartItemDto;
import com.bluemsun.entity.GoodsItem;
import com.bluemsun.entity.Student;
import com.bluemsun.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@ResponseBody
@RequestMapping("/cart")
@CrossOrigin
public class CartController {
    @Autowired
    private CartService cartService;

    @RequestMapping(value = "/addItem",method = RequestMethod.POST)
    public Map addItem(HttpServletRequest request, @RequestBody GoodsItem goodsItem){
        Map map = new HashMap();
        HttpSession session = request.getSession();

        Student student = (Student) session.getAttribute("student");
        int flag = 0;
        if (student==null){
            map.put("code",1002);
            map.put("msg","用户未登录");
            return map;
        } else {
            flag = cartService.buildItem(goodsItem);
                if (flag == 1){
                    map.put("code","2004");
                    map.put("msg","添加成功");
                }
                if (flag == 2){
                    map.put("code","2004");
                    map.put("msg","已添加购物车");
                }
                if (flag == 3){
                    map.put("code","2004");
                    map.put("msg","添加失败");
                }
                if (flag == 4){
                    map.put("code","2004");
                    map.put("msg","商品已下架");
                }
            return map;
        }
    }

    @RequestMapping(value = "/changeItem",method = RequestMethod.POST)
    public Map changeItem(HttpServletRequest request, @RequestBody GoodsItem goodsItem){
        Map map = new HashMap();
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        Integer flag = cartService.changeItem(goodsItem);
        if (flag == 1){
            map.put("code","2004");
            map.put("msg","修改成功");
        }else if (flag == 0){
            map.put("code","2004");
            map.put("msg","修改失败");
        }
        return map;
    }

    @RequestMapping(value = "/deleteItem",method = RequestMethod.POST)
    public Map deleteItem(HttpServletRequest request, @RequestBody GoodsItem goodsItem){
        Map map = new HashMap();
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        Integer flag = cartService.deleteItem(goodsItem);
        System.out.println(flag);
        if (flag == 1){
            map.put("code","2004");
            map.put("msg","删除成功");
        }
        if (flag == 0){
            map.put("code","2004");
            map.put("msg","删除失败");
        }
        return map;
    }

    @RequestMapping(value = "/getItems",method = RequestMethod.GET)
    public Map getItems(HttpServletRequest request, @RequestBody GoodsItem goodsItem){
        Map map = new HashMap();
        List<CartItemDto> list = new ArrayList<>();
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        if(student == null){
            map.put("code","1002");
            map.put("msg","用户未登录");
        }else {
            goodsItem.setStuNum(student.getName());
            map = cartService.getItems(goodsItem);
            list = (List)map.get("data");
            if (list.size() == 0){
                map.put("code","1004");
                map.put("msg","购物车为空");
            }else {
                map.put("code","2004");
                map.put("msg","查询成功");
                map.put("pageNum",goodsItem.getPageNum());
                map.put("pageSize",goodsItem.getPageSize());
            }
        }
        return map;
    }
}
