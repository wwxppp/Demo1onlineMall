package com.wwx.shop.service.impl.Admin;

import com.alibaba.druid.util.StringUtils;
import com.wwx.shop.bean.*;
import com.wwx.shop.dao.Admin.OrderDao;
import com.wwx.shop.dao.impl.Admin.GoodsDaoImpl;
import com.wwx.shop.dao.impl.Admin.OrderDaoImpl;
import com.wwx.shop.service.Admin.OrderService;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    OrderDao orderDao = new OrderDaoImpl();

    /**
     * 查询订单
     * @param orderParam
     * @return
     */
    @Override
    public OrderResult ordersByPage(OrderParam orderParam) {

        String suffix = "";
        List<Object> params = new ArrayList<>();
        //当状态码是-1的时候应该是全部选择出来
        if(orderParam.getState() != -1){
            suffix = suffix + " and stateId = ?";
            params.add(orderParam.getState());//stateId 此时对应的值
        }
//金额
        if(!StringUtils.isEmpty(orderParam.getMoneyLimit1())){
            suffix = suffix + " and amount <= ?";
            params.add(orderParam.getMoneyLimit1());
        }
        if(!StringUtils.isEmpty(orderParam.getMoneyLimit2())){
            suffix += " and amount >= ?";
            params.add(orderParam.getMoneyLimit2());
        }
        //模糊查询 商品
        if(!StringUtils.isEmpty(orderParam.getGoods())){
            suffix += " and goods like ?";
            params.add("%" + orderParam.getGoods() + "%");
        }
        if(!StringUtils.isEmpty(orderParam.getAddress())){
            suffix += " and address like ?";
            params.add("%" + orderParam.getAddress() + "%");
        }
        if(!StringUtils.isEmpty(orderParam.getName())){
            suffix += " and name like ?";
            params.add("%" + orderParam.getName() + "%");
        }
        if(!StringUtils.isEmpty(orderParam.getId())){
            suffix += " and id = ?";
            params.add(orderParam.getId());
        }
        String totalSql = "select count(id) from orders where history = 1 ";//baseurl只查询用户可见的订单
        Integer count = orderDao.queryTotalCount(totalSql + suffix, params);
        String pageSql = " limit ? offset ?";
        params.add(orderParam.getPagesize());
        params.add((orderParam.getCurrentPage() - 1) * orderParam.getPagesize());
        String ordersSql = "select * from orders where history = 1 ";
        //sql语句在service层组装好，对应的值也通过params传过去
        List<Order> orders = orderDao.queryPageOrders(ordersSql + suffix + pageSql, params);
        OrderResult orderResult = new OrderResult();
        orderResult.setTotal(count);//返回的头
        orderResult.setOrders(orders);
        return orderResult;
    }

    /**
     * 在这一步开始封装数据
     * 这个商品的spec list查询方法已经在goodsdao中实现了
     * states是写死的数据
     * @param id
     * @return
     */
    @Override
    public OrderResult order(String id) {
        OrderResult result=new OrderResult();

        //获取states列表
        List<States>  states=new ArrayList<States>();
        states.add(new States(0,"未付款"));
        states.add(new States(1,"未发货"));
        states.add(new States(2,"已发货"));
        states.add(new States(3,"已完成订单"));
        result.setStates(states);
        //把松散的数据封装到一个order中
        Order order1=orderDao.order(id);
        result.setId(order1.getId());
        result.setAmount(order1.getAmount());
        result.setNum(order1.getGoodsNum());
        result.setGoods(order1.getGoods());
        result.setGoodsDetailId(order1.getGoodsDetailId());
        result.setState(order1.getStateId());
        //查询商品


        //获取到spec目录
       int a=orderDao.queryGoodsId(result.getGoods());
       // result.setSpecs(orderDao.getSpec(a));
        //通过specname 和goodsid 来确定 specid；
       int b=orderDao.quertSpecId(order1.getSpec(),a);
     int goodsId=orderDao.queryGoodsId(order1.getGoods()) ;
        List<Spec> list=orderDao.getSpec(goodsId);
       result.setSpec(list);
        //获取目前的状态码
        CurState curState=new CurState();
        curState.setId(order1.getStateId());
        result.setCurState(curState);
        //获取当前的specid
        CurSpec curSpec=new CurSpec();
        curSpec.setId(b);
        result.setCurSpec(curSpec);


        return result;
    }

    @Override
    public int changeOrder(Order orderParam) {
        return orderDao.changeOrder(orderParam);
    }

    /**
     * 删除指定的订单
     * @param id
     */
    @Override
    public void deleteOrder(String id) {
        orderDao.deleteOrder(id);
    }
}

