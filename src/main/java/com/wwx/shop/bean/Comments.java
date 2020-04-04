package com.wwx.shop.bean;


import java.util.Date;

public class Comments {
    private int commentsid;
    private  int userid;
    private String username;
    private int goodsid;
    private String goodsname;
    private int ordereid;
    private String content;
    private int score;
    private String specName;
    private Date time;
    private int rate;

    public int getCommentsid() {
        return commentsid;
    }

    public void setCommentsid(int commentsid) {
        this.commentsid = commentsid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(int goodsid) {
        this.goodsid = goodsid;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public int getOrdereid() {
        return ordereid;
    }

    public void setOrdereid(int ordereid) {
        this.ordereid = ordereid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "commentsid=" + commentsid +
                ", userid=" + userid +
                ", username='" + username + '\'' +
                ", goodsid=" + goodsid +
                ", goodsname='" + goodsname + '\'' +
                ", ordereid=" + ordereid +
                ", content='" + content + '\'' +
                ", score=" + score +
                ", specName='" + specName + '\'' +
                ", time=" + time +
                ", rate=" + rate +
                '}';
    }
}
