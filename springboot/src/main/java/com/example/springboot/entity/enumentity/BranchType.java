package com.example.springboot.entity.enumentity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;


public enum BranchType {
    INDEPENDENT(0,"独立网点"),
    CONTRACTED(1,"承包区网点");
@EnumValue
    private Integer code;
@JsonValue
    private String name;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    BranchType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    @JsonCreator
    public static BranchType jacksonInstance(final JsonNode jsonNode){
        String code = jsonNode.asText();
        BranchType[] values = BranchType.values();
        for (BranchType branchtype:values
             ) {
            if(branchtype.getCode().equals(code)){
                return branchtype;
            }

        }return null;
    }
}
