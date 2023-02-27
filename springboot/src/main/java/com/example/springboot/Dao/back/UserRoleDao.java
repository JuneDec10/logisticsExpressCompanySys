package com.example.springboot.Dao.back;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.back.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleDao extends BaseMapper<Role> {

}
