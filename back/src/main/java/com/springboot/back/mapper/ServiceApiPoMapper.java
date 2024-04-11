package com.springboot.back.mapper;

import com.springboot.back.mapper.po.ServiceApiPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceApiPoMapper extends JpaRepository<ServiceApiPo, Long> {
}
