
package com.wwx.shop.service.Admin;


import com.wwx.shop.bean.Type;

import java.util.List;

public interface TypeService {


    List<Type> getType();
    int addType(Type type);

    void deleteType(String id);
}
