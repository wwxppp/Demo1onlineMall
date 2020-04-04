package com.wwx.shop.dao.Admin;

import com.wwx.shop.bean.Admin;
import com.wwx.shop.bean.Msg;

import java.util.List;

public interface AdminDao {

    int login(Admin admin);

    List<Admin> queryAllAdmins();

    int addAdminss(Admin admin);

    int updateAdminss(Admin admin);

    List<Admin> getAdminsInfo(String id);

    int deleteAdmins(String id);

    List<Admin> querySearchAdmins(Admin admin);

    int changePwd(Admin admin);

    List<Msg> noReplyMsg();

    List<Msg> repliedMsg();

    int reply(Msg msg);
}
