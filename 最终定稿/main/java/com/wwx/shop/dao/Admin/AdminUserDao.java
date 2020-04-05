package com.wwx.shop.dao.Admin;

import com.wwx.shop.bean.User;

import java.util.List;

public interface AdminUserDao {

    List<User> quaryallUser();

    List<User> querySearchUser(String user);

    int deletUser(String id);
}
