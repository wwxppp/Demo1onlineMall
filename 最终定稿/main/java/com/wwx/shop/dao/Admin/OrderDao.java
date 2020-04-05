package com.wwx.shop.dao.Admin;

import com.wwx.shop.bean.Order;
import com.wwx.shop.bean.Spec;

import java.util.List;

public interface OrderDao {
    Integer queryTotalCount(String s, List<Object> params);

    List<Order> queryPageOrders(String s, List<Object> params);

    Order order(String id);

  int queryGoodsId(String goods);

    int quertSpecId(String spec, Integer a);

    List<Spec> getSpec(int id);

    int changeOrder(Order orderParam);

    void deleteOrder(String id);
}

