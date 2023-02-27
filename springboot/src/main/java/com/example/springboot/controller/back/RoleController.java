package com.example.springboot.controller.back;

import com.example.springboot.common.CodeMsg;
import com.example.springboot.common.Result;
import com.example.springboot.entity.back.Menu;
import com.example.springboot.entity.back.Role;
import com.example.springboot.service.back.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("/list")
    @ResponseBody
    public Result<List<Role>> list(){
        System.out.println(roleService.getRoleList());
        return Result.success(roleService.getRoleList());
    }

    @PostMapping("/deleteRoleMenu")
    @ResponseBody
    public Result<Boolean> deleteRoleMenu(@RequestBody Map<String,Object> map){
        Integer roleId = (Integer) map.get("roleId");
        Long menuId = Long.valueOf(map.get("menuId").toString());
        if(roleService.logicDelete(roleId,menuId)!=null){
            return Result.success(true);
        }return Result.error(CodeMsg.ADMIN_MENU_DELETE_ERROR);
    }

    @PostMapping("/insertMany")
    @ResponseBody
    public Result<Boolean> insertMany(@RequestBody Map<String,Object> map ){
        Integer roleId = (Integer) map.get("roleId");
        String idStr = map.get("idStr").toString();
        Integer record = roleService.insertMany(roleId,idStr);
        if(record!=null){
    return Result.success(true);
}else
    return Result.error(CodeMsg.ADMIN_MENU_ADD_ERROR);
    }

    @PostMapping("/logicDelete")
    @ResponseBody
    public Result<Boolean> logicDeleteRole(@RequestBody Map<String,Object> map ){
        Integer roleId = (Integer) map.get("roleId");
        System.out.println("delete:"+roleId);
        if(roleService.deleteRole(roleId)!=null){
            return Result.success(true);
        }else
            return Result.error(CodeMsg.ADMIN_ROLE_DELETE_ERROR);
    }
}
