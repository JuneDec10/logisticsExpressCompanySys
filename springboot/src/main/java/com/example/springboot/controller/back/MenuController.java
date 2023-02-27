package com.example.springboot.controller.back;

import com.example.springboot.common.Result;
import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.entity.back.Menu;
import com.example.springboot.entity.back.MenuInfo;
import com.example.springboot.service.back.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PostMapping("/getRoleMenuList")
    @ResponseBody
    public Result<List<MenuInfo>> getRoleMenuList(@RequestBody Map<String,Object> map){
        Integer roleId = (Integer) map.get("roleId");
        return Result.success(menuService.getMenuInfo(roleId));
    }

    @PostMapping("/list")
    @ResponseBody
    public Result<List<Menu>> list(){
        return Result.success(menuService.listAll());
    }


    @PostMapping("/getAllRoleMenuList")
    @ResponseBody
    public Result<List<MenuInfo>> getAllRoleMenuList(){
        System.out.println(menuService.getAllRoleMenuList());
        return Result.success(menuService.getAllRoleMenuList());
    }

}
