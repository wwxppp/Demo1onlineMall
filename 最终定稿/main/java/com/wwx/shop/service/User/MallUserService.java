package com.wwx.shop.service.User;

import com.wwx.shop.bean.*;

import java.util.List;

public interface MallUserService {
    int askGoodsMsg(Msg msg);

    int addOrder(Order order);

    List getOrderByState(String request, String response);

    int deleteOrder(String id);

    int settleAccounts(OrderResult order);

    void confirmReceive(String id);

    int sendComment(CommentResult commentResult);

    List<Goods> searchGoods(String keyword);
}
