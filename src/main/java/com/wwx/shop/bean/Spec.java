
package com.wwx.shop.bean;

/**
 * 商品的规格
 */
public class Spec {

    private Integer id;

    private String specName;

    private Integer stockNum;

    private Double unitPrice;
    private Integer goodsId;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public Integer getStockNum() {
        return stockNum;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "Spec{" +
                "id=" + id +
                ", specName='" + specName + '\'' +
                ", stockNum=" + stockNum +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
