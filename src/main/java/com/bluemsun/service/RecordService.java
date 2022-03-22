package com.bluemsun.service;

import cn.hutool.core.date.DateTime;
import com.bluemsun.dto.GoodsDto;
import com.bluemsun.entity.InvestRecord;
import com.bluemsun.entity.RetrievalRecord;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RecordService {
    //查看进货记录
    public List<RetrievalRecord> getRetrievalRecord(RetrievalRecord retrievalRecord,int pageNum,int pageSize);
    //查看充值记录
    public List<InvestRecord> getInvestRecord(InvestRecord investRecord,int pageNum,int pageSize);
    //还有啥记录？
    public Integer deleteRetrievalRecordById(int id);
    public Integer deleteInvestRecordById(int id);
    public Integer getRetrievalRecordCount(RetrievalRecord retrievalRecord);
    public Integer getInvestRecordCount(InvestRecord investRecord);
    public List<GoodsDto> getOrderDetailByCreateTime(DateTime start, DateTime end, Integer campus);
}
