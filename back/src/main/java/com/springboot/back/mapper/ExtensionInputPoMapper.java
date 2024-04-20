package com.springboot.back.mapper;

import com.springboot.back.mapper.po.ExtensionInputPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtensionInputPoMapper extends JpaRepository<ExtensionInputPo, Long> {

    ExtensionInputPo findByApplicationIdAndFieldName(Long applicationId, String fieldName);

    List<ExtensionInputPo> findByApplicationId(Long applicationId);

    @Query("SELECT max(a.id) FROM ExtensionInputPo a")
    Long findMaxId();
}
