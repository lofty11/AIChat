package com.springboot.back.mapper;

import com.springboot.back.mapper.po.ExtensionInputPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtensionInputPoMapper extends JpaRepository<ExtensionInputPo, Long> {

    ExtensionInputPo findByApplicationIdAndFieldName(Long applicationId, String fieldName);
}
