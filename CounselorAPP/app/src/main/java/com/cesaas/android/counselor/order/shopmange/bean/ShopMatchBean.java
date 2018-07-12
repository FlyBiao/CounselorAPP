package com.cesaas.android.counselor.order.shopmange.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/5/16 16:20
 * Version 1.0
 */

public class ShopMatchBean implements Serializable {
    private int Id;
    private int Focus;
    private int IsFocus;
    private int Like;
    private int IsLike;
    private int Evaluate;
    private String ImageUrl;
    private String Title;
    private String Stature;
    private String Band;
    private String FAB;


    public List<Goods> Goods;

    public String getFAB() {
        return FAB;
    }

    public void setFAB(String FAB) {
        this.FAB = FAB;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getFocus() {
        return Focus;
    }

    public void setFocus(int focus) {
        Focus = focus;
    }

    public int getIsFocus() {
        return IsFocus;
    }

    public void setIsFocus(int isFocus) {
        IsFocus = isFocus;
    }

    public int getLike() {
        return Like;
    }

    public void setLike(int like) {
        Like = like;
    }

    public int getIsLike() {
        return IsLike;
    }

    public void setIsLike(int isLike) {
        IsLike = isLike;
    }

    public int getEvaluate() {
        return Evaluate;
    }

    public void setEvaluate(int evaluate) {
        Evaluate = evaluate;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getStature() {
        return Stature;
    }

    public void setStature(String stature) {
        Stature = stature;
    }

    public String getBand() {
        return Band;
    }

    public void setBand(String band) {
        Band = band;
    }
}
