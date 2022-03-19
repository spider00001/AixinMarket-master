package com.bluemsun.dao;

import com.bluemsun.entity.OrderDetail;
import com.bluemsun.entity.OrderRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderRecordDao {
    //添加订单信息
    public Integer insertOrder(OrderRecord orderRecord);
    //添加订单详情
    public Integer insertOrderDetailList(@Param("orderRecord") OrderRecord orderRecord);
    //修改订单信息
    public Integer updateOrderRecord(@Param("orderRecord") OrderRecord orderRecord);
    //修改一个订单详情
    public Integer updateOrderDetail(@Param("orderDetail") OrderDetail orderDetail);
    //根据id查询订单
    public OrderRecord selectOrderById(int id);
    //查询订单 (分页用)
    public List<OrderRecord> selectOrder(@Param("orderRecord") OrderRecord orderRecord,
                                   @Param("pageIndex") int pageIndex,
                                   @Param("pageSize") int pageSize);
    public List<OrderDetail> selectOrderDetailsOfOrderRecord(@Param("id") Integer id);

    public OrderRecord getOrderRecordById(@Param("id") int id);

    //根据其他属性查询订单
    public List<OrderRecord> selectOrderRecord(@Param("orderRecord") OrderRecord orderRecord,
                                               @Param("pageIndex") int pageIndex,
                                               @Param("pageSize") int pageSize,
                                               @Param("orderRecordId") int orderRecordId);
    //获得订单总数量
    public Integer getOrderRecordCount(@Param("orderRecord") OrderRecord orderRecord);
    //获得管理员看订单的数量
    public Integer getOrderCount(@Param("orderRecord") OrderRecord orderRecord);
    //查询指定日期前购买商品数量
    public Integer getOrderGoodsSum(@Param("uid") int uid,
                                      @Param("highDate") Date date1,
                                      @Param("downDate") Date date2);
    //删除订单
    public Integer deleteOrder(int id);
    //删除一个订单详情
    public Integer deleteOrderDetail(int id);

}
