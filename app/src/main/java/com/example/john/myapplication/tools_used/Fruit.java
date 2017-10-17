package com.example.john.myapplication.tools_used;

/**
 * Created by John on 2017/10/17.
 */

public class Fruit {

    private String name;

    private int imgId;

    public Fruit(String name,int imgId){
        this.name=name;
        this.imgId=imgId;
    }
    public String getName(){
        return name;
    }
    public int getImgId(){
        return imgId;
    }
}
