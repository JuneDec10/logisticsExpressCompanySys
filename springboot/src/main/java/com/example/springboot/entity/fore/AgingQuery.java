package com.example.springboot.entity.fore;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.springboot.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("")
public class AgingQuery extends BaseEntity {
    @TableField(value = "origin")
    private String origin;    //始发地

    @TableField(value = "destination")
    private String destination; //目的地

    @TableField(value = "eta")
    private Integer eta;   //预计到达时间

    @TableField(value = "price_per_kilogram")
    private Double pricePerKilogram;   //每千克价格

    @TableField(value = "origin_price")
    private Double originPrice;
}
