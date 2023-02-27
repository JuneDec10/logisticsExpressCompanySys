package com.example.springboot.Dao.fore;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.back.User;
import com.example.springboot.entity.common.ExpressMailOrderStatus;
import com.example.springboot.entity.fore.ExpressMailOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExpressMailOrderDao extends BaseMapper<ExpressMailOrder> {
int addOne(ExpressMailOrder expressMailOrder);
List<ExpressMailOrder> listAll();
List<ExpressMailOrder> listByStatus(@Param("branch_id") Long branch_id,@Param("expressMailOrderStatus") int expressMailOrderStatus);
ExpressMailOrder findById(Long id);
ExpressMailOrder findByWaybillNumber(@Param("waybillNumber") String waybillNumber);
Long updateOrderStatus(ExpressMailOrder expressMailOrder);
List<ExpressMailOrder> listByIdOrMobile(@Param("express") ExpressMailOrder express,@Param("setIdOrStatus")Integer setIdOrStatus);
Long updateAbolished(@Param("abolished")Integer abolished,@Param("id") Long id);
}
