package com.example.springboot.service.back.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.Dao.back.RoleDao;
import com.example.springboot.Dao.back.UserDao;
import com.example.springboot.entity.back.Role;
import com.example.springboot.entity.back.RoleMenu;
import com.example.springboot.service.back.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    @Override
    public List<Role> getRoleList() {
        return roleDao.getRoleList();
    }

    @Override
    public Long logicDelete(Integer roleId, Long menuId) {
        return roleDao.updateRoleMenu(roleId,menuId);
    }

    @Override
    public Integer insertMany(Integer roleId, String idStr) {
        String[] menus = idStr.split(",");
        String str = "";
        List<RoleMenu> newRoleMenus = new ArrayList<>();
        List<RoleMenu> oldRoleMenus = roleDao.listRoleMenu(roleId);
        int i=0;
        for(;i<oldRoleMenus.size()-1;i++){
            str = str+oldRoleMenus.get(i).getMenuId()+",";
        }
        str = str+oldRoleMenus.get(i).getMenuId();
        for(String s:menus){
            if(!str.contains(s)){
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(Long.parseLong(s));
                newRoleMenus.add(roleMenu);
            }
        }
        List<RoleMenu> deleteRoleMenus = new ArrayList<>();
        String[] oldStr = str.split(",");
        for(String s:oldStr){
            if(!idStr.contains(s)){
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(Long.parseLong(s));
                deleteRoleMenus.add(roleMenu);
            }
        }
if(newRoleMenus.isEmpty()&&deleteRoleMenus.isEmpty()){
    return null;
}else if(newRoleMenus.isEmpty()&&(!deleteRoleMenus.isEmpty())){
    return roleDao.deleteForeach(deleteRoleMenus);
}else if((!newRoleMenus.isEmpty())&&deleteRoleMenus.isEmpty()){
    return roleDao.insertForeach(newRoleMenus);
}else{
    int record1 = roleDao.deleteForeach(deleteRoleMenus);
    int record2 = roleDao.insertForeach(newRoleMenus);
    if(record1<0||record2<0){
        return null;
    }return 1;
}
    }

    @Override
    public Long deleteRole(Integer roleId) {
        if(!userDao.findAllByUserType(roleId).isEmpty()){
            System.out.println("roleType:"+userDao.findAllByUserType(roleId));
            return null;
        }else
        return roleDao.updateStatus(roleId);
    }
}
