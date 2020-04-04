package com.wwx.shop.service.Admin;

import com.wwx.shop.bean.User;

import java.util.List;

public interface AdminUserService {

    List<User> quaryallUser();

    List<User> querySearchUser(String user);

    int deletUser(String id);
}
