package com.wwx.shop.bean;

public class States {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "states{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public States(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public States() {
    }
}
