package com.springboot.back.mapper;

import com.springboot.back.mapper.po.ApplicationServicePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationServicePoMapper extends JpaRepository<ApplicationServicePo, Long> {

    ApplicationServicePo findByApiName(String apiName);
}
