package com.example.springboot.Dao.back;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.back.Role;
import com.example.springboot.entity.back.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleDao extends BaseMapper<Role> {
List<Role> getRoleList();
Long updateRoleMenu(@Param("roleId")Integer roleId,@Param("menuId") Long menuId);
List<RoleMenu> listRoleMenu(Integer roleId);
Integer insertForeach(List<RoleMenu> list);
int deleteForeach(List<RoleMenu> list);
Long updateStatus(Integer id);
}
