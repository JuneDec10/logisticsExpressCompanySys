package com.example.springboot.service.back;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.entity.back.Menu;
import com.example.springboot.entity.back.MenuInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MenuService extends IService<Menu> {

   List<MenuInfo> getMenuInfo(Integer roleId);
   List<Menu> listAll();
   List<MenuInfo> getAllRoleMenuList();
}
