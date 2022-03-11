package com.bluemsun.service.impl;

import com.bluemsun.dao.RecordsDao;
import com.bluemsun.entity.InvestRecord;
import com.bluemsun.entity.RetrievalRecord;
import com.bluemsun.service.RecordService;
import com.bluemsun.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordsDao recordsDao;

    private Logger logger = LoggerFactory.getLogger(RecordServiceImpl.class);

    @Override
    @Transactional
    public List<RetrievalRecord> getRetrievalRecord(RetrievalRecord retrievalRecord, int pageNum, int pageSize) {
        int pageIndex = PageUtil.getRowIndex(pageNum,pageSize);
        List flag = null;
        try{
            flag = recordsDao.getRetrievalRecordList(retrievalRecord,pageIndex,pageSize);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获得进货记录时出现异常："+e.getMessage());
            throw new RuntimeException("获得进货记录时出现异常："+e.getMessage());
        }
        return flag;
    }

    @Override
    @Transactional
    public Integer deleteRetrievalRecordById(int id){
        Integer flag = null;
        try{
            flag = recordsDao.deleteRetrievalRecord(id);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("删除一条进货记录时出现异常："+e.getMessage());
            throw new RuntimeException("删除一条进货记录时出现异常："+e.getMessage());
        }
        logger.info("删除一条进货记录，id为:"+id);
        return flag;
    }

    @Override
    @Transactional
    public Integer deleteInvestRecordById(int id){
        Integer flag = null;
        try{
            flag = recordsDao.deleteInvestRecord(id);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("删除一条充值记录时出现异常："+e.getMessage());
            throw new RuntimeException("删除一条充值记录时出现异常："+e.getMessage());
        }
        logger.info("删除一条充值记录，原id："+id);
        return flag;
    }

    @Override
    @Transactional
    public List<InvestRecord> getInvestRecord(InvestRecord investRecord, int pageNum, int pageSize) {
        int pageIndex = PageUtil.getRowIndex(pageNum,pageSize);
        List flag = null;
        try{
            flag = recordsDao.getInvestRecordList(investRecord,pageIndex,pageSize);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获得充值记录时出现异常："+e.getMessage());
            throw new RuntimeException("获得充值记录时出现异常："+e.getMessage());
        }
        return flag;
    }

    @Override
    @Transactional
    public Integer getRetrievalRecordCount(RetrievalRecord retrievalRecord){
        Integer flag = null;
        try{
            flag = recordsDao.getRetrievalCount(retrievalRecord);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获得进货记录Count时出现异常："+e.getMessage());
            throw new RuntimeException("获得进货记录Count时出现异常："+e.getMessage());
        }
        return flag;
    }
    @Override
    @Transactional
    public Integer getInvestRecordCount(InvestRecord investRecord){
        Integer flag = null;
        try{
            flag = recordsDao.getInvestCount(investRecord);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获得充值记录Count时出现异常："+e.getMessage());
            throw new RuntimeException("获得充值记录Count时出现异常："+e.getMessage());
        }
        return flag;
    }
}
