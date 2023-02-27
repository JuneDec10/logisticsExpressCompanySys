package com.example.springboot.service.fore.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.Dao.fore.ComplaintDao;
import com.example.springboot.entity.fore.Complaint;
import com.example.springboot.service.fore.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintServiceImpl extends ServiceImpl<ComplaintDao, Complaint> implements ComplaintService {

    @Autowired
    private ComplaintDao complaintDao;

    public  Complaint findByHomeUser_IdAndWaybillNumber(String waybillNumber) {
return complaintDao.findByHomeUser_IdAndWaybillNumber(waybillNumber);
}

    @Override
    public Long saveComplaint(Complaint complaint) {
        return complaintDao.saveComplaint(complaint);
    }

    @Override
    public List<Complaint> listComplaint(Long userId) {
        return complaintDao.listAllByForeUser(userId);
    }

    @Override
    public Long updateComplaint(Complaint complaint) {
        complaint.setDeleted(1);
        complaint.setStatus(null);
        return complaintDao.updateComplaint(complaint);
    }

    @Override
    public List<Complaint> listAll() {
        return complaintDao.listALL();
    }

    @Override
    public Long updateStatus( Long id,Integer status) {
        Complaint complaint = new Complaint();
        complaint.setId(id);
        complaint.setDeleted(null);
        complaint.setStatus(status);
        return complaintDao.updateComplaint(complaint);
    }
}
