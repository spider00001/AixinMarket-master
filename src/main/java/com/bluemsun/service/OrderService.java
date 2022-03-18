package com.bluemsun.service;

import com.bluemsun.dto.OrderRecordDto;
import com.bluemsun.entity.OrderDetail;
import com.bluemsun.entity.OrderRecord;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {
    //新建商品订单
    public Integer buildOrder(OrderRecordDto orderRecord,int status);
    //修改订单属性
    public Integer changeOrder(OrderRecord orderRecord);
    //新增订单商品
    public Integer addOrderGood(int orderId, OrderDetail orderDetail);
    //删除订单商品
    public Integer deleteOrderGood(int orderId,int did);
    //确认订单
    public Integer confirmOrder(OrderRecord orderRecord);
    //通过属性查看订单
    public List<OrderRecord> getOrderList(OrderRecord orderRecord,int pageNum,int pageSize);
    public Integer getOrderCount(OrderRecord orderRecord);
    //通过id查询订单
    public OrderRecord getOrder(int id);
    //修改订单详情
    public Integer changeOrderDetail(int orderId,OrderDetail orderDetail);
    //取消订单
    public Integer cancelOrder(int uid, int oid, HttpServletRequest request);
    //管理员看订单数量
    public Integer adminGetOrderCount(OrderRecord orderRecord);
    //管理员删除订单
    public Integer adminDeleteOrder(int orderId);

}
