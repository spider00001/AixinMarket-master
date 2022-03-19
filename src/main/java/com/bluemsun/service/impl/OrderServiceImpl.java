package com.bluemsun.service.impl;

import com.bluemsun.dao.*;
import com.bluemsun.dto.CartItemDto;
import com.bluemsun.dto.OrderDetailDto;
import com.bluemsun.dto.OrderRecordDto;
import com.bluemsun.entity.*;
import com.bluemsun.service.OrderService;
import com.bluemsun.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRecordDao orderRecordDao;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private WareHouseDao wareHouseDao;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    CartItemDao cartItemDao;

    private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    //新建商品订单
    @Override
    @Transactional
    public Integer buildOrder(OrderRecordDto orderRecordDto,int status){
        List<OrderDetail> orderDetailList = new ArrayList<>();
        //先看余额够不够，不够直接退出
        Student student = null;
        try{
            student = studentDao.getStudentById(orderRecordDto.getStudentId());
        }catch (Exception e){
            e.printStackTrace();
            logger.error("新建订单时获取学生信息时出错："+e.getMessage());
            throw new RuntimeException("新建订单时获取学生信息时出错："+e.getMessage());
        }
        if (student==null){
            return 6;//学生不存在
        }
        if (student.getBalanceFuzhuang()<orderRecordDto.getTotalFuzhuang()||student.getBalanceRiyong()<orderRecordDto.getTotalRiyong()){
            return 2;//代币数量不足
        }
        //然后看触不触发限购,商品余量够不够
        for (OrderDetailDto detail:orderRecordDto.getOrderDetailList()) {
            OrderDetail orderDetail = new OrderDetail();
            int goodsId = detail.getGoodsId();
            Goods g = goodsDao.getGoodById(goodsId);
            if(g==null){
                return 5;//商品不存在
            }
            if (g.getLimitBuyNum()!=-1&&g.getLimitBuyNum()!=null){
                //商品限购
                if (g.getLimitBuyType()){
                    //学期限购
                    Calendar cale = Calendar.getInstance();
                    int month = cale.get(Calendar.MONTH)+1;
                    if (month>=3&&month<9){
                        month = 3;
                    }else {
                        month = 9;
                    }
                    cale.set(Calendar.MONTH,month-1);
                    cale.set(Calendar.DATE,1);
                    Date latterDate = cale.getTime();
                    Date date = new Date();
                    Integer count = orderRecordDao.getOrderGoodsSum(orderRecordDto.getStudentId(),latterDate,date);
                    if(count==null){
                        count=0;
                    }
                    if (count>g.getLimitBuyNum()){
                        return 3;//限购，购买失败
                    }
                }else {
                    //每月限购
                    Calendar cale = Calendar.getInstance();
                    cale.set(Calendar.DATE,1);
                    Date latterDate = cale.getTime();
                    Date date = new Date();
                    Integer count = orderRecordDao.getOrderGoodsSum(orderRecordDto.getStudentId(),latterDate,date);
                    if (count==null){
                        count=0;
                    }
                    if (count>g.getLimitBuyNum()){
                        return 3;//限购，购买失败
                    }
                }
            }
            //商品余量
            WareHouse wareHouse = new WareHouse();
            Goods goods = new Goods();
            goods.setId(detail.getGoodsId());
            wareHouse.setGoodsId(goods);
            wareHouse.setCampus(orderRecordDto.isCampus());

            //删除购物车商品
            CartItemDto cartItemDto = new CartItemDto();
            GoodsItem goodsItem = new GoodsItem();
            goodsItem.setStuNum(student.getName());
            goodsItem.setGoodsName(g.getGoodsName());
            cartItemDto.setGoodsItem(goodsItem);
            cartItemDao.deleteItem(cartItemDto);

            try{
                wareHouse = wareHouseDao.getWareHouseByGoodId(wareHouse);
            }catch (Exception e){
                e.printStackTrace();
                logger.error("新建订单时获取商品余量时出错："+e.getMessage());
                throw new RuntimeException("新建订单时获取商品余量时出错："+e.getMessage());
            }
            if (wareHouse.getNum()<detail.getOrderNum()){
                return 4;//库存不足
            }
            orderDetail.setOrderNum(detail.getOrderNum());
            orderDetail.setGoods(g);
            orderDetailList.add(orderDetail);
        }
        //都通过后插入数据库，学生减少一定的代币
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setStatus(status);
        orderRecord.setCampus(orderRecordDto.isCampus());
        orderRecord.setStudent(student);
        orderRecord.setCreateTime(new Date());
        orderRecord.setTotalFuzhuang(orderRecordDto.getTotalFuzhuang());
        orderRecord.setTotalRiyong(orderRecordDto.getTotalRiyong());
        orderRecord.setOrderDetailList(orderDetailList);
        try{
            //学生减少一定量的代币
//            float balance = student.getBalanceFuzhuang();
//            balance = balance - (float) orderRecordDto.getTotalFuzhuang();
//            student.setBalanceFuzhuang(balance);
            student.setBalanceFuzhuang(student.getBalanceFuzhuang() - orderRecordDto.getTotalFuzhuang());
//            balance = balance - (float) orderRecordDto.getTotalRiyong();
//            student.setBalanceRiyong(balance);
            student.setBalanceRiyong(student.getBalanceRiyong() - orderRecordDto.getTotalRiyong());
            studentDao.updateStudent(student);
            orderRecordDao.insertOrder(orderRecord);
            orderRecordDao.insertOrderDetailList(orderRecord);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("新建订单时将订单插入数据库时出错："+e.getMessage());
            throw new RuntimeException("新建订单时将订单插入数据库时出错："+e.getMessage());
        }

        //log info
        if (status==1){
            logger.info("学生购买商品，学生id："+orderRecordDto.getStudentId()+
                        "，总日用币："+orderRecordDto.getTotalRiyong()+
                        "，总服装币："+orderRecordDto.getTotalFuzhuang()+
                        "，购买的商品"+ StringUtils.collectionToCommaDelimitedString(orderRecordDto.getOrderDetailList()));
        }
        if (status==3){
            logger.info("销售员购买商品，学生id："+orderRecordDto.getStudentId()+
                        "，总日用币："+orderRecordDto.getTotalRiyong()+
                        "，总服装币："+orderRecordDto.getTotalFuzhuang()+
                        "，购买的商品"+ StringUtils.collectionToCommaDelimitedString(orderRecordDto.getOrderDetailList()));
        }
        return 1;//成功
    }
    //修改订单属性
    @Override
    @Transactional
    public Integer changeOrder(OrderRecord orderRecord){
        Integer flag = null;
        try{
            flag = orderRecordDao.updateOrderRecord(orderRecord);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("修改订单时出错："+e.getMessage());
            throw new RuntimeException("修改订单时出错："+e.getMessage());
        }
        logger.info("修改订单属性，订单id："+orderRecord.getId()+
                    "，订单校区"+orderRecord.getCampus()+
                    "，订单属性"+orderRecord.getStatus());
        //因为报了空指针，所以删掉这条
//                "，学生id"+orderRecord.getStudent().getId()
        return flag;
    }
    //修改订单详情属性
    @Override
    @Transactional
    public Integer changeOrderDetail(int orderId,OrderDetail orderDetail){
        //先看之前的和现在的区别，现在的购买量大于之前的就是新增，小于就是删除
        Integer flag = null;

        return flag;
    }
    //新增订单商品
    @Override
    @Transactional
    public Integer addOrderGood(int orderId, OrderDetail orderDetail){
        //先看余额够不够，再修改属性
        OrderRecord orderRecord = orderRecordDao.selectOrderById(orderId);
        return null;
    }
    //删除订单商品
    @Override
    @Transactional
    public Integer deleteOrderGood(int orderId,int did){
        OrderRecord orderRecord = orderRecordDao.selectOrderById(orderId);
        if (orderRecord==null){
            return 2;//orderId不存在
        }
        OrderDetail orderDetail = null;
        for (OrderDetail detail:orderRecord.getOrderDetailList()){
            if (detail.getId()==did){
                orderDetail=detail;
            }
        }
        if (orderDetail==null){
            return 3;//did不存在
        }
        Goods goods = orderDetail.getGoods();
        float price = goods.getPrice()*orderDetail.getOrderNum();
        WareHouse wareHouse = new WareHouse();
        wareHouse.setGoodsId(goods);
        wareHouse.setCampus(orderRecord.getCampus());
        wareHouse = wareHouseDao.getWareHouseByGoodId(wareHouse);
        Student student = orderRecord.getStudent();
        if (goods.getMoneyType() == 1){
            //服装币
            student.setBalanceFuzhuang(student.getBalanceFuzhuang()+price);
            orderRecord.setTotalFuzhuang(orderRecord.getTotalFuzhuang()-price);
            wareHouse.setNum(wareHouse.getNum()+orderDetail.getOrderNum());
        }else {
            //日用
            student.setBalanceRiyong(student.getBalanceRiyong()+price);
            orderRecord.setTotalRiyong(orderRecord.getTotalRiyong()-price);
            wareHouse.setNum(wareHouse.getNum()+orderDetail.getOrderNum());
        }
        try {
            studentDao.updateStudent(student);
            wareHouseDao.updateWareHouseById(wareHouse);
            orderRecordDao.updateOrderRecord(orderRecord);
            orderRecordDao.deleteOrderDetail(did);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new RuntimeException();
        }
        return 1;
    }
    //确认订单
    @Override
    @Transactional
    public Integer confirmOrder(OrderRecord orderRecord){
        return null;
    }

    //通过属性查看订单
    @Override
    @Transactional
    public List getOrderList(OrderRecord orderRecord,int pageNum,int pageSize){
        List flag = new LinkedList();
        int pageIndex = PageUtil.getRowIndex(pageNum,pageSize);
        try{
            List<OrderRecord> orderRecords = orderRecordDao.selectOrder(orderRecord,pageIndex,pageSize);
            for (OrderRecord o : orderRecords) {
                System.out.println("`````"+o+"`````");
                Student student = studentDao.getStudentByInfoStudentId(o.getStudent().getId());
                System.out.println("========= "+student+" ======");
                List<OrderDetail> orderDetailList = orderRecordDao.selectOrderDetailsOfOrderRecord(o.getId());
                System.out.println("=========------ "+orderDetailList+" -----======");
                o.setStudent(student);
                o.setOrderDetailList(orderDetailList);

                flag.add(o);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查看订单时出错："+e.getMessage());
            throw new RuntimeException("查看订单时出错："+e.getMessage());
        }
        return flag;
    }

    @Override
    public Integer getOrderCount(OrderRecord orderRecord){
        Integer flag = null;
        try{
            flag = orderRecordDao.getOrderRecordCount(orderRecord);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查看订单Count时出错："+e.getMessage());
            throw new RuntimeException("查看订单Count时出错："+e.getMessage());
        }
        return flag;
    }
    //通过id查询订单
    @Override
    @Transactional
    public OrderRecord getOrder(int id){
        OrderRecord flag = null;
        try{
            flag = orderRecordDao.selectOrderById(id);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查看订单ById时出错："+e.getMessage());
            throw new RuntimeException("查看订单ById时出错："+e.getMessage());
        }
        return flag;
    }
    //取消订单
    @Override
    @Transactional
    public Integer cancelOrder(int uid, int oid, HttpServletRequest request){
        try{
            OrderRecord orderRecord = orderRecordDao.selectOrderById(oid);
            Student student = studentDao.getStudentById(uid);
            student.setBalanceRiyong(student.getBalanceRiyong() + orderRecord.getTotalRiyong());
            student.setBalanceFuzhuang(student.getBalanceFuzhuang() + orderRecord.getTotalFuzhuang());
            studentDao.updateStudent(student);
            HttpSession session = request.getSession();
            session.removeAttribute("student");
            session.setAttribute("student",student);
            for (OrderDetail detail:orderRecord.getOrderDetailList()){
                Goods goods = detail.getGoods();
                WareHouse wareHouse = new WareHouse();
                wareHouse.setGoodsId(goods);
                wareHouse.setCampus(orderRecord.getCampus());
                wareHouse = wareHouseDao.getWareHouseByGoodId(wareHouse);
                wareHouse.setNum(wareHouse.getNum()+detail.getOrderNum());
                wareHouseDao.updateWareHouseById(wareHouse);
            }
            orderRecord.setStatus(4);
            orderRecordDao.updateOrderRecord(orderRecord);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("删除订单时出错："+e.getMessage());
            throw new RuntimeException("删除订单时出错："+e.getMessage());
        }
        logger.info("删除订单，用户id："+uid+"，订单id:"+oid);
        return 1;
    }

    //管理员看订单数量
    @Override
    public Integer adminGetOrderCount(OrderRecord orderRecord){
        Integer flag = null;
        try{
            flag = orderRecordDao.getOrderRecordCount(orderRecord);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("管理员查看订单数量时出错："+e.getMessage());
            throw new RuntimeException("管理员查看订单数量时出错："+e.getMessage());
        }
        return flag;
    }

    @Override
    @Transactional
    public Integer adminDeleteOrder(int orderId){
        Integer flag = null;
        try{
            OrderRecord orderRecord = orderRecordDao.selectOrderById(orderId);
            for (OrderDetail detail:orderRecord.getOrderDetailList()){
                orderRecordDao.deleteOrderDetail(detail.getId());
            }
            flag = orderRecordDao.deleteOrder(orderId);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("管理员删除订单时出错"+e.getMessage());
            throw new RuntimeException("管理员删除订单时出错"+e.getMessage());
        }
        logger.info("管理员删除一个订单，原有订单id："+orderId);
        return flag;


    }
}
