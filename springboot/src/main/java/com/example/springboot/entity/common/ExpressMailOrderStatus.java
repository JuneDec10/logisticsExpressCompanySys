package com.example.springboot.entity.common;

public class ExpressMailOrderStatus {

    public static final int NOTORDER = 8; //未支付

    public static final int HAVEORDER = 0;  //已下单
    public static final int TO_COMPLETE_THE_GOODS = 1;  //网点完成收货

    public static final int THEGOODS = 2;   //运输中
    public static final int INTRANSIT = 3;  //运输已完成
    public static final int COMPLETED = 4;  //运输未完成

    public static final int INTHEDELIVERY = 5;  //送货中
    public static final int DESIGNATE_DISPATCHERS = 6; //指定派送员

    public static final int SIGNFOR = 7;    //签收
}
