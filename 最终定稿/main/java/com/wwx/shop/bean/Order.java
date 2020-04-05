package com.wwx.shop.bean;

import java.util.Date;

public class Order {
    private Integer id;

    private Integer userId;

    private Integer goodsDetailId;

    private String goods;

    private String spec;

    private Integer goodsNum;

    private Double amount;

    private Integer stateId;

    private String state;

    private User user;
    private String token;
    private int num;
    private Date createtime;

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGoodsDetailId() {
        return goodsDetailId;
    }

    public void setGoodsDetailId(Integer goodsDetailId) {
        this.goodsDetailId = goodsDetailId;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getStateId() {
        return stateId;
    }

    /**
     *  dbutils在赋值时通过反射--- setFiled
     *  进一步优化：0，1，2，3  magic number 枚举  常量
     *  PageContext.PAGE_SCOPE  REQUEST_SCOPE   jsp
     * @param stateId
     */
    public void setStateId(Integer stateId) {
        //dbutils给stateId赋值，调用该方法
        this.stateId = stateId;
        //switch：
        if(this.stateId == 0){
            setState("未付款");
        }else if(this.stateId == 1){
            setState("未发货");
        }else if(this.stateId == 2){
            setState("已发货");
        } else if(this.stateId == 3){
            setState("已到货");
        }
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", goodsDetailId=" + goodsDetailId +
                ", goods='" + goods + '\'' +
                ", spec='" + spec + '\'' +
                ", goodsNum=" + goodsNum +
                ", amount=" + amount +
                ", stateId=" + stateId +
                ", state='" + state + '\'' +
                ", user=" + user +
                '}';
    }
}
