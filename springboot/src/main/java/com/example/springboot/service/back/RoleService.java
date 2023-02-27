package com.example.springboot.service.back;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.entity.back.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService extends IService<Role> {
    List<Role> getRoleList();
    Long logicDelete(Integer roleId,Long menuId);
    Integer insertMany(Integer roleId,String idStr);
    Long deleteRole(Integer roleId);
}
