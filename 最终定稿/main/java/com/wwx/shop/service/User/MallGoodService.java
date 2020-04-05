package com.wwx.shop.service.User;

import java.util.List;
import java.util.Map;

public interface MallGoodService {
    List getGoodsMsg(String goodsid);

    Map getGoodsComment(String goodsid);
}
