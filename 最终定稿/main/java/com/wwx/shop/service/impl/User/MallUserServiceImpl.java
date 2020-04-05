package com.wwx.shop.service.impl.User;


import com.wwx.shop.bean.*;
import com.wwx.shop.dao.User.MallUserDao;
import com.wwx.shop.dao.impl.User.MallUserDaoImpl;
import com.wwx.shop.service.User.MallUserService;

import java.util.List;

public class MallUserServiceImpl implements MallUserService {
    MallUserDao mallUserDao=new MallUserDaoImpl();
    @Override
    public int askGoodsMsg(Msg msg) {
        return mallUserDao.askGoodsMsg(msg);
    }

    /**
     * 添加订单
     * @param order
     */
    @Override
    public int addOrder(Order order) {
        return mallUserDao.addOrder(order);
    }

    @Override
    public List getOrderByState(String request, String response) {
        String state=request;
        String token=response;


        return mallUserDao. getOrderByState(state,token);
    }

    @Override
    public int deleteOrder(String id) {
        return mallUserDao.deleteOrder(id);
    }

    @Override
    public int settleAccounts(OrderResult order) {
        return mallUserDao.settleAccounts(order);
    }

    @Override
    public void confirmReceive(String id) {
        mallUserDao.confirmReceive(id);
    }

    @Override
    public int sendComment(CommentResult commentResult) {
        return mallUserDao.sendComment(commentResult);
    }

    @Override
    public List<Goods> searchGoods(String keyword) {
        return mallUserDao.searchGoods(keyword);
    }
}
