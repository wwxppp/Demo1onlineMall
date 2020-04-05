package com.wwx.shop.service.Admin;

import com.wwx.shop.bean.Order;
import com.wwx.shop.bean.OrderParam;
import com.wwx.shop.bean.OrderResult;

public interface OrderService {
    OrderResult ordersByPage(OrderParam orderParam);

    OrderResult order(String id);

    int changeOrder(Order orderParam);

    void deleteOrder(String id);
}
