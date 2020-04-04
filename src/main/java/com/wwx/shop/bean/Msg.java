package com.wwx.shop.bean;

import java.util.Date;

public class Msg {
    private int id;
    private int userid;
    private int goodsid;

    private int goodsId;

    private String content;
    private int state;
    private Date createtime;
    private Date replytime;
    private Goods goods;
    private User user;
    private  String replyContent;
    private  String token;
    private String msg;

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getReplytime() {
        return replytime;
    }

    public void setReplytime(Date replytime) {
        this.replytime = replytime;
    }



    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(int goodsid) {
        this.goodsid = goodsid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "id=" + id +
                ", userid=" + userid +
                ", goodsid=" + goodsid +
                ", content='" + content + '\'' +
                ", state=" + state +
                ", createtime=" + createtime +
                ", replytime=" + replytime +
                ", goods=" + goods +
                ", user=" + user +
                ", replyContent='" + replyContent + '\'' +
                '}';
    }
}
