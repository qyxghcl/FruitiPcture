package com.example.fruitpictures.bean;

/**
 * Created by hwj on 2017/1/28.
 */

public class Fruit {
    private String name;
    private int iamgerId;

    public Fruit(String name, int iamgerId) {
        this.name = name;
        this.iamgerId = iamgerId;
    }

    public String getName() {
        return name;
    }

    public int getIamgerId() {
        return iamgerId;
    }
}
