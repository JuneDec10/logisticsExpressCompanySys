package com.example.springboot.entity.back;

import lombok.Data;

import java.util.List;

@Data
public class MenuInfo {
    private Long id;
    private String name;
    private String routerName;
    private String iconName;
    private Integer sort;
    private Integer isButton;
    private Integer disabled;
    private Long parentId;
    private List<MenuInfo> submenu;

}
