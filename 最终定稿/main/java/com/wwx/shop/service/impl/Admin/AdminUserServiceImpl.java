package com.wwx.shop.service.impl.Admin;

import com.wwx.shop.bean.User;
import com.wwx.shop.dao.impl.Admin.AdminUserDaoImpl;
import com.wwx.shop.service.Admin.AdminUserService;

import java.util.List;

public class AdminUserServiceImpl implements AdminUserService {
private AdminUserDaoImpl adminUser=new AdminUserDaoImpl();
    @Override
    public List<User> quaryallUser() {
        return adminUser.quaryallUser();
    }

    @Override
    public List<User> querySearchUser(String user) {
        return adminUser.querySearchUser(user);
    }

    @Override
    public int deletUser(String id) {
        return adminUser.deletUser(id);
    }
}
