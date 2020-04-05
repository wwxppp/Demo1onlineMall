/**
 * User: zsquirrel
 * Date: 2020/3/31
 * Time: 10:14 上午
 */
package com.wwx.shop.service.impl.Admin;



import com.wwx.shop.bean.Type;
import com.wwx.shop.dao.Admin.TypeDao;
import com.wwx.shop.dao.impl.Admin.TypeDaoImpl;
import com.wwx.shop.service.Admin.TypeService;

import java.util.List;

public class TypeServiceImpl implements TypeService {
TypeDao typeDao=new TypeDaoImpl();

    @Override
    public List<Type> getType() {
        return typeDao.getType();
    }

    /**
     * 要验证品类是否已经存在，应该在当前该目录校验
     * @param type
     * @return
     */
    @Override
    public int addType(Type type) {
        int r=-1;
        r=typeDao.queryType(type);
if (r==0 ){
    return 300;
}
        return typeDao.addType(type);
    }

    @Override
    public void deleteType(String id) {
        typeDao.deletType(id);
    }
}
