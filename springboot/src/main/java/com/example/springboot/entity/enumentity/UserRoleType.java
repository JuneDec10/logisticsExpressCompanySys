package com.example.springboot.entity.enumentity;


import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

@Getter
public enum UserRoleType {
    ADMIN(0,"管理员"),
    BRANCHES(1,"网点"),
    TRANSPORT(2,"运输点"),
    COURIER(3,"快递员");
    @EnumValue
    private int code;
    @JsonValue
    private String name;

    UserRoleType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }


    @JsonCreator
    public static UserRoleType jacksonInstance(final JsonNode jsonNode){
        String code = jsonNode.asText();
        UserRoleType[] values = UserRoleType.values();
        for (UserRoleType branchtype:values
        ) {
            if(branchtype.getCode().equals(code)){
                return branchtype;
            }

        }return null;
    }

}
