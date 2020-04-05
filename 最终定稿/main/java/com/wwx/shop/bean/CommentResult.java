package com.wwx.shop.bean;

public class CommentResult {

    private String token;
    private int orderId;
    private int goodsId;
    private int goodsDetailId;
    private String content;
    private int score;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getGoodsDetailId() {
        return goodsDetailId;
    }

    public void setGoodsDetailId(int goodsDetailId) {
        this.goodsDetailId = goodsDetailId;
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

    @Override
    public String toString() {
        return "CommentResult{" +
                "token=" + token +
                ", orderId=" + orderId +
                ", goodsId=" + goodsId +
                ", goodsDetailId=" + goodsDetailId +
                ", content='" + content + '\'' +
                ", score=" + score +
                '}';
    }
}
