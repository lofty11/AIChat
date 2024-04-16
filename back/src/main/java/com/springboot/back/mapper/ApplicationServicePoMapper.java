package com.springboot.back.mapper;

import com.springboot.back.mapper.po.ApplicationServicePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ApplicationServicePoMapper extends JpaRepository<ApplicationServicePo, Long> {

    ApplicationServicePo findByApiName(String apiName);

    ApplicationServicePo findByApiNameAndApiCode(String apiName, String apiCode);

    @Query("SELECT a.id, a.apiName FROM ApplicationServicePo a")
    List<Object[]> findAllIdsAndNames();

}
