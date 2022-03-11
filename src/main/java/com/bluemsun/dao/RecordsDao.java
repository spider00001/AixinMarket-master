package com.bluemsun.dao;

import com.bluemsun.entity.InvestRecord;
import com.bluemsun.entity.RetrievalRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecordsDao {
    //添加充值记录
    public Integer insertInvestRecord( InvestRecord investRecord);
    //添加入库记录
    public Integer insertRetrievalRecord(RetrievalRecord retrievalRecord);
    //获得充值记录
    public List<InvestRecord> getInvestRecordList(@Param("investRecord") InvestRecord investRecord,
                                                  @Param("pageIndex") int pageIndex,
                                                  @Param("pageSize") int pageSize);
    public Integer getInvestCount(@Param("investRecord") InvestRecord investRecord);
    public Integer getRetrievalCount(@Param("retrievalRecord") RetrievalRecord retrievalRecord);
    //获得入库记录
    public List<RetrievalRecord> getRetrievalRecordList(@Param("retrievalRecord") RetrievalRecord retrievalRecord,
                                                        @Param("pageIndex") int pageIndex,
                                                        @Param("pageSize") int pageSize);
    //删除充值记录
    public Integer deleteInvestRecord(int id);
    //删除入库记录
    public Integer deleteRetrievalRecord(int id);
}
