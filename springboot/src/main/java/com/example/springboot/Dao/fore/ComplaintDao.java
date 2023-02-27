package com.example.springboot.Dao.fore;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.fore.Complaint;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ComplaintDao extends BaseMapper<Complaint> {
Complaint findByHomeUser_IdAndWaybillNumber(String waybillNumber);
Long saveComplaint(Complaint complaint);
List<Complaint> listAllByForeUser(Long foreUser);
Long updateComplaint(Complaint complaint);
List<Complaint> listALL();
}
