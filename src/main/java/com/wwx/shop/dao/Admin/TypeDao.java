/**
 * User: zsquirrel
 * Date: 2020/3/31
 * Time: 10:17 上午
 */
package com.wwx.shop.dao.Admin;



import com.wwx.shop.bean.Type;

import java.util.List;

public interface TypeDao {

    List<Type> getType();
    int addType(Type type);
    int queryType(Type type);

    void deletType(String id);
}
