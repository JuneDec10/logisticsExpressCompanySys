package com.example.springboot.Dao.back;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.back.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface UserDao extends BaseMapper<User> {
    //通过用户名查找
    User findByName(String username);

    User findById(Long id);

    User findByMobile(String mobile);

    //通过用户类型查找
    List<User> findAllByUserType(int user_type);

    //更新操作
    int updateOne(User user);

    int updateCourier(User user);

    //逻辑删除操作
    int logicDeleteOne(User user);

//    添加操作
    int addOne(User user);

    List<User> findAllNextTransPoint(@Param("address") String address, @Param("roleId") int roleId);

    List<User> findBranchCourier(Long id);

    List<User> findBut(int userType);

    List<User> findByLikeAddress(String address);

    List<User> findByAddress(@Param("roleId")Integer roleId,@Param("address")String address);


}
