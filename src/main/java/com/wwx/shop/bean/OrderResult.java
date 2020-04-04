package com.wwx.shop.bean;

import java.util.List;

public class OrderResult {
    private Integer total;
    private List<Order> orders;
    private List<Spec> specs;
    private List<States>states;
    private int id;
    private double amount;
    private int num;
    private int goodsDetailId;
    private int state;
    private String goods;
    private CurState curState;
    private CurSpec curSpec;
private List<Order> cartList;

    public List<Order> getCartList() {
        return cartList;
    }

    public void setCartList(List<Order> cartList) {
        this.cartList = cartList;
    }

    public CurState getCurState() {
        return curState;
    }

    public void setCurState(CurState curState) {
        this.curState = curState;
    }

    public CurSpec getCurSpec() {
        return curSpec;
    }

    public void setCurSpec(CurSpec curSpec) {
        this.curSpec = curSpec;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getGoodsDetailId() {
        return goodsDetailId;
    }

    public void setGoodsDetailId(int goodsDetailId) {
        this.goodsDetailId = goodsDetailId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public List<States> getStates() {
        return states;
    }

    public void setStates(List<States> states) {
        this.states = states;
    }

    public List<Spec> getSpecs() {
        return specs;
    }

    public void setSpecs(List<Spec> specs) {
        this.specs = specs;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "OrderResult{" +
                "total=" + total +
                ", orders=" + orders +
                ", specs=" + specs +
                ", states=" + states +
                ", id=" + id +
                ", amount=" + amount +
                ", num=" + num +
                ", goodsDetailId=" + goodsDetailId +
                ", state=" + state +
                ", goods='" + goods + '\'' +
                ", curState=" + curState +
                ", curSpec=" + curSpec +
                '}';
    }
}

