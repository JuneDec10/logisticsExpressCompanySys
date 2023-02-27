package com.example.springboot.service.fore;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.entity.fore.Complaint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ComplaintService extends IService<Complaint> {
    Complaint findByHomeUser_IdAndWaybillNumber(String waybillNumber);
    Long saveComplaint(Complaint complaint);
    List<Complaint> listComplaint(Long userId);
    Long updateComplaint(Complaint complaint);
    List<Complaint> listAll();
    Long updateStatus(Long id,Integer status);
}
