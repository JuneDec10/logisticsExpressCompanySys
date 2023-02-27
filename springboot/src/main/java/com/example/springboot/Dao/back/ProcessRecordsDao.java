package com.example.springboot.Dao.back;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.back.ProcessRecords;
import com.example.springboot.entity.fore.ExpressMailOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface ProcessRecordsDao extends BaseMapper<ProcessRecords> {

    Long save(ProcessRecords processRecords);
List<ProcessRecords> listAll(@Param("id") Long id,@Param("status") int status,@Param("orderStatus") int orderStatus);
ProcessRecords find(ProcessRecords processRecords);
Long update(ProcessRecords processRecords);
List<ProcessRecords> listAllById(Long id);

}
