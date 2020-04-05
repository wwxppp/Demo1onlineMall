
package com.wwx.shop.dao.Admin;



import com.wwx.shop.bean.Goods;
import com.wwx.shop.bean.Spec;

import java.util.List;

public interface GoodsDao {

    List<Goods> getGoodByType(String id);

    int addGoods(Goods goods);

    int queryLastId();

    void addSpeces(List<Spec> specList, int goodsId);

    Goods getGoodsInfo(String id);

    List<Spec> getSpec(String id);

    int updateGoods(Goods goods);

    int addSpec(Spec spec);

    int deleteGoods(String id);
}
