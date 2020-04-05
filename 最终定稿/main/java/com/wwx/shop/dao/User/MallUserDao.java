package com.wwx.shop.dao.User;

import com.wwx.shop.bean.*;

import java.util.List;

public interface MallUserDao {

    int askGoodsMsg(Msg msg);

    int addOrder(Order order);

    List getOrderByState(String baseSql, String state);

    int deleteOrder(String id);

    int settleAccounts(OrderResult order);

    void confirmReceive(String id);

    int sendComment(CommentResult commentResult);

    List<Goods> searchGoods(String keyword);
}
