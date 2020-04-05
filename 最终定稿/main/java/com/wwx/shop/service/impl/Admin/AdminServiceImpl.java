package com.wwx.shop.service.impl.Admin;

import com.wwx.shop.bean.Admin;
import com.wwx.shop.bean.Msg;
import com.wwx.shop.dao.Admin.AdminDao;
import com.wwx.shop.dao.impl.Admin.AdminDaoImpl;
import com.wwx.shop.service.Admin.AdminService;

import java.util.List;

public class AdminServiceImpl implements AdminService {

    private AdminDao adminDao = new AdminDaoImpl();

    @Override
    public int login(Admin admin) {
        return adminDao.login(admin);
    }

    @Override
    public List<Admin> queryAllAdmins() {
        return adminDao.queryAllAdmins();
    }

    @Override
    public int addAdminss(Admin admin) {
        return adminDao.addAdminss(admin);
    }

    @Override
    public int updateAdminss(Admin admin) {
        return adminDao.updateAdminss(admin);
    }

    @Override
    public List<Admin> getAdminsInfo(String id) {
        return adminDao.getAdminsInfo(id);
    }

    @Override
    public int deleteAdmins(String id) {
        return adminDao.deleteAdmins(id);
    }

    @Override
    public List<Admin> querySearchAdmins(Admin admin) {
        return adminDao.querySearchAdmins(admin);
    }

    /**
     * 修改密码的方法
     * @param admin
     * @return
     */
    @Override
    public int changePwd(Admin admin) {

        return adminDao.changePwd(admin);
    }

    @Override
    public List<Msg> noReplyMsg() {
        return adminDao.noReplyMsg();
    }

    @Override
    public List<Msg> repliedMsg() {
        return adminDao.repliedMsg();
    }

    @Override
    public int reply(Msg msg) {
        return adminDao.reply(msg);
    }
}
