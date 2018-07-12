package com.cesaas.android.counselor.order.boss.bean;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created at 2018/1/20 11:40
 * Version 1.0
 */

public class SalesProportionBean implements Serializable {
    private String Name;
    private int number1;
    private int number2;
    private int number3;
    private int number4;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getNumber1() {
        return number1;
    }

    public void setNumber1(int number1) {
        this.number1 = number1;
    }

    public int getNumber2() {
        return number2;
    }

    public void setNumber2(int number2) {
        this.number2 = number2;
    }

    public int getNumber3() {
        return number3;
    }

    public void setNumber3(int number3) {
        this.number3 = number3;
    }

    public int getNumber4() {
        return number4;
    }

    public void setNumber4(int number4) {
        this.number4 = number4;
    }
}
