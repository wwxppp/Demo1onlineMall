package com.wwx.shop.dao.User;

import com.wwx.shop.bean.Msg;
import com.wwx.shop.bean.User;

import java.util.List;
import java.util.Map;

public interface MallGoodDao {
    List<Msg> getGoodsMsg(String goodsid);

    User queryUser(int userid);

    List<Map> getGoodsComments(String goodsid);
}
