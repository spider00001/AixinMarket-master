package com.bluemsun.web.admin;

import com.bluemsun.entity.Goods;
import com.bluemsun.entity.RetrievalRecord;
import com.bluemsun.service.RecordService;
import com.bluemsun.util.HttpRequestUtil;
//import org.omg.CORBA.MARSHAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/record")
@ResponseBody
@CrossOrigin
public class InvestAndRetrievalController {

    @Autowired
    private RecordService recordService;
//    @RequestMapping(value = "/invest",method = RequestMethod.GET)
//    public Map getInvestRecord(){
//        Map map = new HashMap();
//        return map;
//    }

    //获取进货记录
    @RequestMapping(value = "/retrieval",method = RequestMethod.GET)
    public Map getRetrievalRecord(HttpServletRequest request,@RequestParam Map<String,String> reqMap){
        Map map = new HashMap();
        Map data = new HashMap();
        String goodName = HttpRequestUtil.getString(reqMap,"goodName");
        Integer campus = HttpRequestUtil.getInt(reqMap,"campus");
        int currentPage = HttpRequestUtil.getInt(reqMap,"currentPage");
        int pageSize = HttpRequestUtil.getInt(reqMap,"pageSize");
        if (currentPage==-1||pageSize==-1){
            map.put("code",1004);
            map.put("msg","数据为空");
            return map;
        }
        if (campus==-1){
            campus = null;
        }
        Goods goods = new Goods();
        goods.setGoodsName(goodName);
        RetrievalRecord retrievalRecord = new RetrievalRecord();
        retrievalRecord.setGoods(goods);
        retrievalRecord.setCampus(campus);
        List<RetrievalRecord> recordList = recordService.getRetrievalRecord(retrievalRecord,currentPage,pageSize);
        Integer total = recordService.getRetrievalRecordCount(retrievalRecord);
        if (recordList==null||total==null){
            map.put("code",2004);
                map.put("msg","无符合条件的记录");
        }else {
            map.put("code",0);
            data.put("recordList",recordList);
            data.put("total",total);
            map.put("data",data);
        }
        return map;
    }

    @RequestMapping(value = "/retrievalDelete",method = RequestMethod.POST)
    public Map doDeleteWareHouse(@RequestBody Map<String,String> reqMap){
        Map map = new HashMap();
        int retrievalId = HttpRequestUtil.getInt(reqMap,"retrievalId");
        if (retrievalId==-1){
            map.put("code",1004);
            map.put("msg","数据为空");
            return map;
        }
        try{
            recordService.deleteRetrievalRecordById(retrievalId);
            map.put("code",0);
        }catch (Exception e){
            map.put("code",2004);
            map.put("msg","删除进货记录出错");
            return map;
        }
        return map;
    }
}
