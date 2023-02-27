package com.example.springboot.Dao.back;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.back.Menu;
import com.example.springboot.entity.back.MenuInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface MenuDao extends BaseMapper<Menu> {
List<MenuInfo> listMenuInfo(Integer roleId);
List<Menu> listAll();
List<MenuInfo> getAllRoleMenuList();
}
