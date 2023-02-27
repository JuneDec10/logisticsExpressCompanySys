package com.example.springboot.service.back.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.Dao.back.MenuDao;
import com.example.springboot.entity.back.Menu;
import com.example.springboot.entity.back.MenuInfo;
import com.example.springboot.service.back.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {
    @Autowired
    private MenuDao menuDao;
    public List<MenuInfo> getMenuInfo(Integer roleId) {
return menuDao.listMenuInfo(roleId);
    }

    @Override
    public List<Menu> listAll() {
        return menuDao.listAll();
    }

    @Override
    public List<MenuInfo> getAllRoleMenuList() {
        return menuDao.getAllRoleMenuList();
    }
}
