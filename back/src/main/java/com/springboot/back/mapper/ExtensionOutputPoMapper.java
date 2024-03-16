package com.springboot.back.mapper;

import com.springboot.back.mapper.po.ExtensionOutputPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtensionOutputPoMapper extends JpaRepository<ExtensionOutputPo, Long> {

    ExtensionOutputPo findByApplicationIdAndFieldName(Long applicationId, String fieldName);
}
