
package com.wwx.shop.service.Admin;



import com.wwx.shop.bean.Goods;
import com.wwx.shop.bean.Spec;

import java.util.List;

public interface GoodsService {

    List<Goods> getGoodsByType(String id);
    int addGoods(Goods goods);

    Goods getGoodsInfo(String id);

    List<Spec> getSpec(String id);

    int updateGoods(Goods goods);



    int addSpec(Spec spec);

    int deleteGoods(String id);
}
