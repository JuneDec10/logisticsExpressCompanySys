package com.example.springboot.Dao.fore;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.fore.ForeUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ForeUserDao extends BaseMapper<ForeUser> {

    //通过用户名查找
    ForeUser findByName(String name);

//    通过用户手机号查找
    ForeUser findByMobile(String mobile);

    ForeUser findById(Long id);

    Long updateStatusOrDeleted(@Param("id") Long id,@Param("status")Integer status,@Param("deleted")Integer deleted);
}
