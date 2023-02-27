package com.example.springboot.service.back;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.entity.back.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends IService<User> {
User findByUsername(String username);
User findById(Long id);
User findByMobile(String mobile);
List<User> findByUserType(int type);
List<User> findAllUserButCourier(int type);
int updateOne(User user);
int updateCourier(User user);
int logicDeleteOne(User user);
int addOne(User user);
List<User> findAllNextTransPoint(int role,String address);
List<User> findBranchCourier(Long branchId);
List<User> findLikeAddress(String address);

}
